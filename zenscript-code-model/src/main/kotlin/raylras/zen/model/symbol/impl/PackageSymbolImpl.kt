package raylras.zen.model.symbol.impl

import org.antlr.v4.runtime.tree.ParseTree
import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.CompilationUnit
import raylras.zen.model.isZsUnit
import raylras.zen.model.symbol.PackageSymbol
import raylras.zen.model.symbol.ParseTreeLocatable
import raylras.zen.model.symbol.Symbol
import raylras.zen.model.type.VoidType
import raylras.zen.util.BASE_COLUMN
import raylras.zen.util.BASE_LINE
import raylras.zen.util.TextRange
import raylras.zen.util.textRange

fun createPackageSymbol(env: CompilationEnvironment): PackageSymbol {
    class PackageSymbolImpl(override val simpleName: String) : PackageSymbol {
        override val subpackages: Sequence<PackageSymbol>
            get() = _subpackages.values.asSequence()

        val _subpackages = HashMap<String, PackageSymbol>()

        override val members: Sequence<Symbol>
            get() = _members.values.asSequence()

        val _members = HashMap<String, Symbol>()

        override val type = VoidType

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
    return object : PackageSymbol, ParseTreeLocatable {
        override val subpackages
            get() = _subpackages.values.asSequence()

        private val _subpackages = emptyMap<String, PackageSymbol>()

        override val members = unit.staticSymbols

        override val simpleName
            get() = unit.simpleName

        override val type = VoidType

        override fun getSymbols(env: CompilationEnvironment?): Sequence<Symbol> {
            return subpackages + members
        }

        override val cst: ParseTree
            get() = unit.parseTree

        override val unit: CompilationUnit
            get() = unit

        override val textRange: TextRange
            get() = unit.parseTree.textRange

        override val simpleNameTextRange: TextRange
            get() = TextRange(BASE_LINE, BASE_COLUMN, BASE_LINE, BASE_COLUMN)

        override fun toString() = simpleName
    }
}
