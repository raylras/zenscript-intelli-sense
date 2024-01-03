package raylras.zen.model.symbol

import org.antlr.v4.runtime.ParserRuleContext
import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.CompilationUnit
import raylras.zen.model.isDzsUnit
import raylras.zen.model.isZsUnit
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.resolve.*
import raylras.zen.model.symbol.Modifiable.Modifier
import raylras.zen.model.type.*
import raylras.zen.util.textRange

fun symbolSequence(action: SymbolBuilder.() -> Unit): Sequence<Symbol> {
    val builder = SymbolBuilder()
    builder.action()
    return builder.symbols.asSequence()
}

class SymbolBuilder {
    val symbols = ArrayList<Symbol>()
    fun variable(simpleName: String, type: Type, modifier: Modifier = Modifier.IMPLICIT_VAL): SymbolBuilder {
        symbols.add(createVariableSymbol(simpleName, type, modifier))
        return this
    }

    fun function(simpleName: String, returnType: Type, action: ParameterBuilder.() -> Unit = {}): SymbolBuilder {
        val builder = ParameterBuilder()
        builder.action()
        symbols.add(createFunctionSymbol(simpleName, returnType, builder.parameters))
        return this
    }

    fun operator(operator: Operator, returnType: Type, action: ParameterBuilder.() -> Unit = {}): SymbolBuilder {
        val builder = ParameterBuilder()
        builder.action()
        symbols.add(createOperatorFunctionSymbol(operator, returnType, builder.parameters))
        return this
    }
}

class ParameterBuilder {
    val parameters = ArrayList<ParameterSymbol>()
    fun parameter(simpleName: String, type: Type, optional: Boolean = false, vararg: Boolean = false): ParameterBuilder {
        parameters.add(createParameterSymbol(simpleName, type, optional, vararg))
        return this
    }
}

fun createImportSymbol(
    simpleNameCtx: ParserRuleContext?,
    cst: ImportDeclarationContext?,
    unit: CompilationUnit,
    block: (ImportSymbol) -> Unit
) {
    return when {
        simpleNameCtx == null -> {}

        cst == null -> {}

        else -> {
            val symbol = createImportSymbolInternal(simpleNameCtx, cst, unit)
            block.invoke(symbol)
        }
    }
}

private fun createImportSymbolInternal(
    simpleNameCtx: ParserRuleContext,
    cst: ImportDeclarationContext,
    unit: CompilationUnit
): ImportSymbol {
    return object : ImportSymbol, ParseTreeLocatable {
        override val qualifiedName by lazy { cst.qualifiedName().text }

        override val targets get() = lookupSymbol(cst.qualifiedName(), unit)

        override val simpleName by lazy { simpleNameCtx.text }

        override val type = VoidType

        override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> = targets.asSequence()

        override val cst = cst

        override val unit = unit

        override val textRange by lazy { cst.textRange }

        override val selectionTextRange by lazy { simpleNameCtx.textRange }

        override fun toString() = qualifiedName
    }
}

fun createClassSymbol(
    simpleNameCtx: ParserRuleContext?,
    cst: ClassDeclarationContext?,
    unit: CompilationUnit,
    block: (ClassSymbol) -> Unit
) {
    return when {
        simpleNameCtx == null -> {}

        cst == null -> {}

        cst.classBody() == null -> {}

        else -> {
            val symbol = createClassSymbolInternal(simpleNameCtx, cst, unit)
            block.invoke(symbol)
        }
    }
}

private fun createClassSymbolInternal(
    simpleNameCtx: ParserRuleContext,
    cst: ClassDeclarationContext,
    unit: CompilationUnit
): ClassSymbol {
    return object : ClassSymbol, ParseTreeLocatable {
        override val qualifiedName by lazy {
            if (unit.isDzsUnit) {
                unit.qualifiedName
            } else {
                unit.qualifiedName + '.' + simpleName
            }
        }

        override val declaredMembers: Sequence<Symbol> by lazy {
            unit.scopeMap[cst]?.getSymbols()
                ?.filter { it is ParseTreeLocatable }
                ?: emptySequence()
        }

        override val interfaces: Sequence<ClassSymbol>
            get() {
                return cst.qualifiedName()
                    ?.map { lookupClass(it, unit) }
                    ?.flatten()
                    ?.asSequence()
                    ?: emptySequence()
            }

        override val type by lazy { ClassType(this) }

        override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
            return declaredMembers + type.getExpands(env)
        }

        override val simpleName by lazy { simpleNameCtx.text }

        override val cst = cst

        override val unit = unit

        override val textRange by lazy { cst.textRange }

        override val selectionTextRange by lazy { simpleNameCtx.textRange }

        override fun toString() = qualifiedName
    }
}

fun createVariableSymbol(
    simpleNameCtx: SimpleNameContext?,
    cst: ParserRuleContext,
    unit: CompilationUnit,
    block: (VariableSymbol) -> Unit
) {
    return when {
        simpleNameCtx == null -> {}

        else -> {
            val symbol = createVariableSymbolInternal(simpleNameCtx, cst, unit)
            block.invoke(symbol)
        }
    }
}

private fun createVariableSymbolInternal(
    simpleNameCtx: SimpleNameContext,
    cst: ParserRuleContext,
    unit: CompilationUnit
): VariableSymbol {
    return object : VariableSymbol, ParseTreeLocatable {
        override val simpleName by lazy { simpleNameCtx.text }

        override val type by lazy { getType(cst, unit) }

        override val modifier by lazy { getModifier(cst) }

        override val cst = cst

        override val unit = unit

        override val textRange by lazy { cst.textRange }

        override val selectionTextRange by lazy { simpleNameCtx.textRange }

        override fun toString() = simpleName
    }
}

fun createVariableSymbol(simpleName: String, type: Type, modifier: Modifier): VariableSymbol {
    return object : VariableSymbol {
        override val simpleName = simpleName

        override val type = type

        override val modifier = modifier
    }
}

fun createFunctionSymbol(
    simpleNameCtx: SimpleNameContext?,
    cst: FunctionDeclarationContext,
    unit: CompilationUnit,
    block: (FunctionSymbol) -> Unit
) {
    val symbol = createFunctionSymbolInternal(simpleNameCtx, cst, unit)
    block.invoke(symbol)
}

fun createFunctionSymbolInternal(
    simpleNameCtx: SimpleNameContext?,
    cst: FunctionDeclarationContext,
    unit: CompilationUnit
): FunctionSymbol {
    return object : FunctionSymbol, ParseTreeLocatable {
        override val type by lazy {
            getType(cst, unit)
                .takeIf { it is FunctionType }
                ?.let { it as FunctionType }
                ?: FunctionType(AnyType)
        }

        override val parameters: List<ParameterSymbol> by lazy { getParameters(cst, unit) }

        override val returnType by lazy { type.returnType }

        override val simpleName by lazy { simpleNameCtx?.text ?: "" }

        override val modifier by lazy { getModifier(cst) }

        override val cst = cst

        override val unit = unit

        override val textRange by lazy { cst.textRange }

        override val selectionTextRange by lazy { simpleNameCtx?.textRange ?: cst.FUNCTION().textRange }

        override fun toString() = simpleName
    }
}

fun createFunctionSymbol(simpleName: String, returnType: Type, params: List<ParameterSymbol>): FunctionSymbol {
    return object : FunctionSymbol {
        override val type by lazy { FunctionType(returnType, params.map { it.type }) }

        override val parameters = params

        override val returnType = returnType

        override val simpleName = simpleName

        override val modifier = Modifier.ERROR

        override fun toString() = simpleName
    }
}

fun createOperatorFunctionSymbol(
    simpleNameCtx: ParserRuleContext?,
    cst: OperatorFunctionDeclarationContext,
    unit: CompilationUnit,
    block: (OperatorFunctionSymbol) -> Unit
) {
    return when {
        simpleNameCtx == null -> {}

        else -> {
            val symbol = createOperatorFunctionSymbolInternal(simpleNameCtx, cst, unit)
            block.invoke(symbol)
        }
    }
}

private fun createOperatorFunctionSymbolInternal(
    simpleNameCtx: ParserRuleContext,
    cst: OperatorFunctionDeclarationContext,
    unit: CompilationUnit
): OperatorFunctionSymbol {
    return object : OperatorFunctionSymbol, ParseTreeLocatable {
        override val operator by lazy {
            Operator.of(
                simpleName,
                cst.formalParameter()?.size ?: -1
            )
        }

        override val type by lazy {
            getType(cst, unit)
                .takeIf { it is FunctionType }
                ?.let { it as FunctionType }
                ?: FunctionType(AnyType)
        }

        override val parameters by lazy { getParameters(cst, unit) }

        override val returnType by lazy { type.returnType }

        override val simpleName by lazy { simpleNameCtx.text }

        override val cst = cst

        override val unit = unit

        override val textRange by lazy { cst.textRange }

        override val selectionTextRange by lazy { simpleNameCtx.textRange }

        override fun toString() = simpleName
    }
}

fun createOperatorFunctionSymbol(
    operator: Operator,
    returnType: Type,
    params: List<ParameterSymbol> = emptyList()
): OperatorFunctionSymbol {
    return object : OperatorFunctionSymbol {
        override val operator = operator

        override val type by lazy { FunctionType(returnType, params.map { it.type }) }

        override val parameters = params

        override val returnType = returnType

        override val simpleName by lazy { operator.literal }

        override fun toString() = simpleName
    }
}

fun createParameterSymbol(
    simpleNameCtx: ParserRuleContext?,
    cst: FormalParameterContext,
    unit: CompilationUnit,
    block: (ParameterSymbol) -> Unit
) {
    return when {
        simpleNameCtx == null -> {}

        else -> {
            val symbol = createParameterSymbolInternal(simpleNameCtx, cst, unit)
            block.invoke(symbol)
        }
    }
}

private fun createParameterSymbolInternal(
    simpleNameCtx: ParserRuleContext,
    ctx: FormalParameterContext,
    unit: CompilationUnit
): ParameterSymbol {
    return object : ParameterSymbol, ParseTreeLocatable {
        override val isOptional by lazy { ctx.defaultValue() != null }

        override val isVararg by lazy { ctx.varargsPrefix() != null }

        override val simpleName by lazy { simpleNameCtx.text }

        override val type by lazy { getType(ctx, unit) }

        override val modifier = Modifier.IMPLICIT_VAL

        override val cst = ctx

        override val unit = unit

        override val textRange by lazy { ctx.textRange }

        override val selectionTextRange by lazy { simpleNameCtx.textRange }

        override fun toString() = simpleName
    }
}

fun createParameterSymbol(name: String, type: Type, optional: Boolean = false, vararg: Boolean = false): ParameterSymbol {
    return object : ParameterSymbol {
        override val isOptional = optional

        override val isVararg = vararg

        override val simpleName = name

        override val type = type

        override val modifier = Modifier.IMPLICIT_VAL

        override fun toString() = simpleName
    }
}

fun createExpandFunctionSymbol(
    simpleNameCtx: ParserRuleContext?,
    ctx: ExpandFunctionDeclarationContext,
    unit: CompilationUnit,
    block: (ExpandFunctionSymbol) -> Unit
) {
    return when {
        simpleNameCtx == null -> {}

        ctx.typeLiteral() == null -> {}

        ctx.functionBody() == null -> {}

        else -> {
            val symbol = createExpandFunctionSymbolInternal(simpleNameCtx, ctx, unit)
            block.invoke(symbol)
        }
    }
}

private fun createExpandFunctionSymbolInternal(
    simpleNameCtx: ParserRuleContext,
    cst: ExpandFunctionDeclarationContext,
    unit: CompilationUnit
): ExpandFunctionSymbol {
    return object : ExpandFunctionSymbol, ParseTreeLocatable {
        override val parameters by lazy { getParameters(cst, unit) }

        override val returnType by lazy { type.returnType }

        override val expandingType by lazy { getType(cst.typeLiteral(), unit) }

        override val simpleName by lazy { simpleNameCtx.text }

        override val type by lazy {
            getType(cst, unit)
                .takeIf { it is FunctionType }
                ?.let { it as FunctionType }
                ?: FunctionType(AnyType)
        }

        override val cst = cst

        override val unit = unit

        override val textRange by lazy { cst.textRange }

        override val selectionTextRange by lazy { simpleNameCtx.textRange }

        override fun toString() = simpleName
    }
}

fun createThisSymbol(typeSupplier: () -> Type): ThisSymbol {
    return object : ThisSymbol {
        override val simpleName = "this"

        override val type by lazy { typeSupplier() }

        override fun toString() = simpleName
    }
}

fun createConstructorSymbol(
    cst: ConstructorDeclarationContext,
    unit: CompilationUnit,
    declaringClass: ClassSymbol?,
    block: (ConstructorSymbol) -> Unit
) {
    return when {
        declaringClass == null -> {}

        cst.constructorBody() == null -> {}

        else -> {
            val symbol = createConstructorSymbol(cst, unit, declaringClass)
            block.invoke(symbol)
        }
    }
}

private fun createConstructorSymbol(
    cst: ConstructorDeclarationContext,
    unit: CompilationUnit,
    declaringClass: ClassSymbol
): ConstructorSymbol {
    return object : ConstructorSymbol, ParseTreeLocatable {
        override val declaringClass = declaringClass

        override val parameters: List<ParameterSymbol> by lazy { getParameters(cst, unit) }

        override val returnType by lazy { type.returnType }

        override val simpleName by lazy { cst.ZEN_CONSTRUCTOR().text }

        override val type by lazy {
            getType(cst, unit)
                .takeIf { it is FunctionType }
                ?.let { it as FunctionType }
                ?: FunctionType(AnyType)
        }

        override val cst = cst

        override val unit= unit

        override val textRange by lazy { cst.textRange }

        override val selectionTextRange by lazy { cst.ZEN_CONSTRUCTOR().textRange }

        override fun toString() = simpleName
    }
}

fun createPackageSymbol(env: CompilationEnvironment): PackageSymbol {
    class PackageSymbolImpl(override val simpleName: String) : PackageSymbol {
        override val subpackages: Sequence<PackageSymbol>
            get() = _subpackages.values.asSequence()

        val _subpackages = HashMap<String, PackageSymbol>()

        override val members: Sequence<Symbol>
            get() = _members.values.asSequence()

        val _members = HashMap<String, Symbol>()

        override val type= VoidType

        override fun toString() = simpleName

        override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
            val symbols = ArrayList<Symbol>(_subpackages.size + _members.size)
            symbols.addAll(_subpackages.values)
            symbols.addAll(_members.values)
            return symbols.asSequence()
        }

        fun getOrCreatePackage(qualifiedName: String): PackageSymbolImpl {
            val components = qualifiedName.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var node = this
            for (i in 0 until components.size - 1) {
                val component = components[i]
                node = node.getOrCreateSubpackage(component)
            }
            return node
        }

        private fun getOrCreateSubpackage(simpleName: String): PackageSymbolImpl {
            var child = _subpackages[simpleName]
            if (child == null) {
                child = PackageSymbolImpl(simpleName)
                _subpackages[simpleName] = child
            }
            return child as PackageSymbolImpl
        }
    }

    val root = PackageSymbolImpl("<ROOT>")
    env.classes
        .filter { it.qualifiedName.contains(".") }
        .forEach {
            val packageNode = root.getOrCreatePackage(it.qualifiedName)
            packageNode._members[it.simpleName] = it
        }
    env.units
        .filter { it.isZsUnit }
        .forEach {
            val packageNode = root.getOrCreatePackage(it.qualifiedName)
            packageNode._members[it.simpleName] = createPackageSymbol(it)
        }
    return root
}

fun createPackageSymbol(unit: CompilationUnit): PackageSymbol {
    return object : PackageSymbol {
        override val subpackages
            get() = _subpackages.values.asSequence()

        private val _subpackages = emptyMap<String, PackageSymbol>()

        override val members = unit.topLevelSymbols

        override val simpleName
            get() = unit.simpleName

        override val type = VoidType

        override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
            return subpackages + members
        }

        override fun toString() = simpleName
    }
}
