package raylras.zen.model.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.CompilationUnit;
import raylras.zen.model.Compilations;
import raylras.zen.model.parser.ZenScriptParser.*;
import raylras.zen.model.resolve.FormalParameterResolver;
import raylras.zen.model.resolve.ModifierResolver;
import raylras.zen.model.resolve.SymbolResolver;
import raylras.zen.model.resolve.TypeResolver;
import raylras.zen.model.symbol.Symbol.Modifier;
import raylras.zen.model.type.*;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.PathUtil;
import raylras.zen.util.Range;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class SymbolFactory {

    private SymbolFactory() {
    }

    public static ImportSymbol createImportSymbol(ParseTree nameCst, ImportDeclarationContext cst, CompilationUnit unit) {
        class ImportSymbolImpl implements ImportSymbol, ParseTreeLocatable {
            @Override
            public String getQualifiedName() {
                return cst.qualifiedName().getText();
            }

            @Override
            public String getName() {
                return CSTNodes.getText(nameCst);
            }

            @Override
            public Kind getKind() {
                return Kind.IMPORT;
            }

            @Override
            public Type getType() {
                return VoidType.INSTANCE;
            }

            @Override
            public Modifier getModifier() {
                return Modifier.NONE;
            }

            @Override
            public ParseTree getCst() {
                return cst;
            }

            @Override
            public CompilationUnit getUnit() {
                return unit;
            }

            @Override
            public Range getRange() {
                return Range.of(cst);
            }

            @Override
            public Range getSelectionRange() {
                return Range.of(nameCst);
            }

            @Override
            public Collection<Symbol> getSymbols() {
                return SymbolResolver.lookupSymbol(cst.qualifiedName(), unit);
            }
        }
        return new ImportSymbolImpl();
    }

    public static ClassSymbol createClassSymbol(ParseTree nameCst, ClassDeclarationContext cst, CompilationUnit unit) {
        class ClassSymbolImpl implements ClassSymbol, ParseTreeLocatable {
            private final ClassType classType = new ClassType(this);

            @Override
            public String getQualifiedName() {
                if (unit.isGenerated()) {
                    return unit.getQualifiedName();
                } else {
                    return unit.getQualifiedName() + '.' + getName();
                }
            }

            @Override
            public List<Symbol> getDeclaredMembers() {
                return unit.getScope(cst)
                        .map(scope -> scope.getSymbols().stream()
                                .filter(ParseTreeLocatable.class::isInstance)
                                .toList())
                        .orElseGet(Collections::emptyList);
            }

            @Override
            public List<ClassSymbol> getInterfaces() {
                if (cst.qualifiedNameList() == null) {
                    return Collections.emptyList();
                }
                return cst.qualifiedNameList().qualifiedName().stream()
                        .map(name -> SymbolResolver.lookupClass(name, unit))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList();
            }

            @Override
            public ClassType getType() {
                return classType;
            }

            @Override
            public List<Symbol> getSymbols() {
                return getDeclaredMembers();
            }

            @Override
            public String getName() {
                return CSTNodes.getText(nameCst);
            }

            @Override
            public Kind getKind() {
                return Kind.CLASS;
            }

            @Override
            public Modifier getModifier() {
                return Modifier.IMPLICIT_STATIC;
            }

            @Override
            public ParseTree getCst() {
                return cst;
            }

            @Override
            public CompilationUnit getUnit() {
                return unit;
            }

            @Override
            public Range getRange() {
                return Range.of(cst);
            }

            @Override
            public Range getSelectionRange() {
                return Range.of(nameCst);
            }
        }
        return new ClassSymbolImpl();
    }

    public static VariableSymbol createVariableSymbol(ParseTree nameCst, ParseTree cst, CompilationUnit unit) {
        class VariableSymbolImpl implements VariableSymbol, ParseTreeLocatable {
            @Override
            public String getName() {
                return CSTNodes.getText(nameCst);
            }

            @Override
            public Kind getKind() {
                return Kind.VARIABLE;
            }

            @Override
            public Type getType() {
                return TypeResolver.getType(cst, unit).orElse(AnyType.INSTANCE);
            }

            @Override
            public Modifier getModifier() {
                return ModifierResolver.getModifier(cst);
            }

            @Override
            public ParseTree getCst() {
                return cst;
            }

            @Override
            public CompilationUnit getUnit() {
                return unit;
            }

            @Override
            public Range getRange() {
                return Range.of(cst);
            }

            @Override
            public Range getSelectionRange() {
                return Range.of(nameCst);
            }
        }
        return new VariableSymbolImpl();
    }

    public static VariableSymbol createVariableSymbol(String name, Type type, Modifier modifier) {
        class VariableSymbolImpl implements VariableSymbol {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public Kind getKind() {
                return Kind.VARIABLE;
            }

            @Override
            public Type getType() {
                return type;
            }

            @Override
            public Modifier getModifier() {
                return modifier;
            }
        }
        return new VariableSymbolImpl();
    }

    public static FunctionSymbol createFunctionSymbol(ParseTree nameCst, ParseTree cst, CompilationUnit unit) {
        class FunctionSymbolImpl implements FunctionSymbol, ParseTreeLocatable {
            @Override
            public FunctionType getType() {
                return TypeResolver.getType(cst, unit)
                        .filter(FunctionType.class::isInstance)
                        .map(FunctionType.class::cast)
                        .orElse(new FunctionType(AnyType.INSTANCE));
            }

            @Override
            public List<ParameterSymbol> getParameterList() {
                return FormalParameterResolver.getFormalParameterList(cst, unit);
            }

            @Override
            public Type getReturnType() {
                return getType().returnType();
            }

            @Override
            public String getName() {
                return CSTNodes.getText(nameCst);
            }

            @Override
            public Kind getKind() {
                return Kind.FUNCTION;
            }

            @Override
            public Modifier getModifier() {
                return ModifierResolver.getModifier(cst);
            }

            @Override
            public ParseTree getCst() {
                return cst;
            }

            @Override
            public CompilationUnit getUnit() {
                return unit;
            }

            @Override
            public Range getRange() {
                return Range.of(cst);
            }

            @Override
            public Range getSelectionRange() {
                return Range.of(nameCst);
            }
        }

        return new FunctionSymbolImpl();
    }

    public static FunctionSymbol createFunctionSymbol(String name, Type returnType, List<ParameterSymbol> params) {
        class FunctionSymbolImpl implements FunctionSymbol {
            private final FunctionType functionType = new FunctionType(returnType, params.stream().map(Symbol::getType).collect(Collectors.toList()));

            @Override
            public FunctionType getType() {
                return functionType;
            }

            @Override
            public List<ParameterSymbol> getParameterList() {
                return params;
            }

            @Override
            public Type getReturnType() {
                return returnType;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Kind getKind() {
                return Kind.FUNCTION;
            }

            @Override
            public Modifier getModifier() {
                return Modifier.NONE;
            }
        }
        return new FunctionSymbolImpl();
    }

    public static OperatorFunctionSymbol createOperatorFunctionSymbol(OperatorContext opCst, OperatorFunctionDeclarationContext cst, CompilationUnit unit) {
        class OperatorFunctionSymbolImpl implements OperatorFunctionSymbol, ParseTreeLocatable {
            @Override
            public Operator getOperator() {
                int paramSize = cst.formalParameterList().formalParameter().size();
                return Operator.of(getName(), paramSize).orElse(Operator.ERROR);
            }

            @Override
            public FunctionType getType() {
                return TypeResolver.getType(cst, unit)
                        .filter(FunctionType.class::isInstance)
                        .map(FunctionType.class::cast)
                        .orElse(new FunctionType(AnyType.INSTANCE));
            }

            @Override
            public List<ParameterSymbol> getParameterList() {
                return FormalParameterResolver.getFormalParameterList(cst, unit);
            }

            @Override
            public Type getReturnType() {
                return getType().returnType();
            }

            @Override
            public String getName() {
                return opCst.getText();
            }

            @Override
            public Kind getKind() {
                return Kind.OPERATOR;
            }

            @Override
            public Modifier getModifier() {
                return Modifier.NONE;
            }

            @Override
            public ParseTree getCst() {
                return cst;
            }

            @Override
            public CompilationUnit getUnit() {
                return unit;
            }

            @Override
            public Range getRange() {
                return Range.of(cst);
            }

            @Override
            public Range getSelectionRange() {
                return Range.of(opCst);
            }
        }
        return new OperatorFunctionSymbolImpl();
    }

    public static OperatorFunctionSymbol createOperatorFunctionSymbol(Operator operator, Type returnType, List<ParameterSymbol> params) {
        class OperatorFunctionSymbolImpl implements OperatorFunctionSymbol {
            private final FunctionType functionType = new FunctionType(returnType, params.stream().map(Symbol::getType).collect(Collectors.toList()));

            @Override
            public Operator getOperator() {
                return operator;
            }

            @Override
            public FunctionType getType() {
                return functionType;
            }

            @Override
            public List<ParameterSymbol> getParameterList() {
                return params;
            }

            @Override
            public Type getReturnType() {
                return returnType;
            }

            @Override
            public String getName() {
                return operator.getLiteral();
            }

            @Override
            public Kind getKind() {
                return Kind.OPERATOR;
            }

            @Override
            public Modifier getModifier() {
                return Modifier.NONE;
            }
        }
        return new OperatorFunctionSymbolImpl();
    }

    public static ParameterSymbol createParameterSymbol(ParseTree nameCst, FormalParameterContext cst, CompilationUnit unit) {
        class ParameterSymbolImpl implements ParameterSymbol, ParseTreeLocatable {
            @Override
            public boolean isOptional() {
                return cst.defaultValue() != null;
            }

            @Override
            public boolean isVararg() {
                return cst.varargsPrefix() != null;
            }

            @Override
            public String getName() {
                return CSTNodes.getText(nameCst);
            }

            @Override
            public Kind getKind() {
                return Kind.PARAMETER;
            }

            @Override
            public Type getType() {
                return TypeResolver.getType(cst, unit).orElse(AnyType.INSTANCE);
            }

            @Override
            public Modifier getModifier() {
                return Modifier.IMPLICIT_VAR;
            }

            @Override
            public ParseTree getCst() {
                return cst;
            }

            @Override
            public CompilationUnit getUnit() {
                return unit;
            }

            @Override
            public Range getRange() {
                return Range.of(cst);
            }

            @Override
            public Range getSelectionRange() {
                return Range.of(nameCst);
            }
        }
        return new ParameterSymbolImpl();
    }

    public static ExpandFunctionSymbol createExpandFunctionSymbol(ParseTree nameCst, ExpandFunctionDeclarationContext cst, CompilationUnit unit) {
        class ExpandFunctionSymbolImpl implements ExpandFunctionSymbol, ParseTreeLocatable {
            @Override
            public List<ParameterSymbol> getParameterList() {
                return FormalParameterResolver.getFormalParameterList(cst, unit);
            }

            @Override
            public Type getReturnType() {
                return getType().returnType();
            }

            @Override
            public Type getExpandingType() {
                return TypeResolver.getType(cst.typeLiteral(), unit).orElse(null);
            }

            @Override
            public String getName() {
                return CSTNodes.getText(nameCst);
            }

            @Override
            public Kind getKind() {
                return Kind.FUNCTION;
            }

            @Override
            public Modifier getModifier() {
                return Modifier.EXPAND;
            }

            @Override
            public FunctionType getType() {
                return TypeResolver.getType(cst, unit)
                        .filter(FunctionType.class::isInstance)
                        .map(FunctionType.class::cast)
                        .orElse(new FunctionType(AnyType.INSTANCE));
            }

            @Override
            public ParseTree getCst() {
                return cst;
            }

            @Override
            public CompilationUnit getUnit() {
                return unit;
            }

            @Override
            public Range getRange() {
                return Range.of(cst);
            }

            @Override
            public Range getSelectionRange() {
                return Range.of(nameCst);
            }
        }
        return new ExpandFunctionSymbolImpl();
    }

    public static ParameterSymbol createParameterSymbol(String name, Type type, boolean optional, boolean vararg) {
        class ParameterSymbolImpl implements ParameterSymbol {
            @Override
            public boolean isOptional() {
                return optional;
            }

            @Override
            public boolean isVararg() {
                return vararg;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Kind getKind() {
                return Kind.PARAMETER;
            }

            @Override
            public Type getType() {
                return type;
            }

            @Override
            public Modifier getModifier() {
                return Modifier.IMPLICIT_VAR;
            }
        }
        return new ParameterSymbolImpl();
    }

    public static ThisSymbol createThisSymbol(Supplier<Type> typeSupplier) {
        class ThisSymbolImpl implements ThisSymbol {
            @Override
            public String getName() {
                return "this";
            }

            @Override
            public Kind getKind() {
                return Kind.VARIABLE;
            }

            @Override
            public Type getType() {
                return typeSupplier.get();
            }

            @Override
            public Modifier getModifier() {
                return Modifier.IMPLICIT_VAL;
            }
        }
        return new ThisSymbolImpl();
    }

    public static ConstructorSymbol createConstructorSymbol(ParseTree nameCst, ParseTree cst, CompilationUnit unit, ClassSymbol declaringClass) {
        class ConstructorSymbolImpl implements ConstructorSymbol, ParseTreeLocatable {
            @Override
            public List<ParameterSymbol> getParameterList() {
                return FormalParameterResolver.getFormalParameterList(cst, unit);
            }

            @Override
            public Type getReturnType() {
                return getType().returnType();
            }

            @Override
            public ParseTree getCst() {
                return cst;
            }

            @Override
            public CompilationUnit getUnit() {
                return unit;
            }

            @Override
            public Range getRange() {
                return Range.of(cst);
            }

            @Override
            public Range getSelectionRange() {
                return Range.of(nameCst);
            }

            @Override
            public String getName() {
                return getReturnType().toString();
            }

            @Override
            public Kind getKind() {
                return Kind.FUNCTION;
            }

            @Override
            public FunctionType getType() {
                return TypeResolver.getType(cst, unit)
                        .filter(FunctionType.class::isInstance)
                        .map(FunctionType.class::cast)
                        .orElse(new FunctionType(AnyType.INSTANCE));
            }

            @Override
            public Modifier getModifier() {
                return Modifier.NONE;
            }

            @Override
            public ClassSymbol getDeclaringClass() {
                return declaringClass;
            }
        }
        return new ConstructorSymbolImpl();
    }

    public static PackageSymbol createPackageSymbol(Path path, CompilationEnvironment env) {
        class PackageSymbolImpl implements PackageSymbol, Locatable {
            @Override
            public String getName() {
                return PathUtil.getFileNameWithoutSuffix(path);
            }

            @Override
            public Kind getKind() {
                return Kind.PACKAGE;
            }

            @Override
            public Type getType() {
                return VoidType.INSTANCE;
            }

            @Override
            public Modifier getModifier() {
                return Modifier.NONE;
            }

            @Override
            public String getQualifiedName() {
                return Compilations.extractClassName(env.relativize(path));
            }

            @Override
            public Collection<Symbol> getSymbols() {
                // TODO: getSymbols
                return null;
            }

            @Override
            public Path getPath() {
                return path;
            }

            @Override
            public String getUri() {
                return path.toUri().toString();
            }

            @Override
            public Range getRange() {
                return Range.NO_RANGE;
            }

            @Override
            public Range getSelectionRange() {
                return Range.NO_RANGE;
            }
        }
        return new PackageSymbolImpl();
    }

    public static SymbolsBuilder builtinSymbols() {
        return new SymbolsBuilder();
    }

    public static class SymbolsBuilder {
        private final List<Symbol> symbols = new ArrayList<>();

        public SymbolsBuilder variable(String name, Type type, Modifier modifier) {
            symbols.add(createVariableSymbol(name, type, modifier));
            return this;
        }

        public SymbolsBuilder function(String name, Type returnType, UnaryOperator<ParameterBuilder> paramsSupplier) {
            symbols.add(createFunctionSymbol(name, returnType, paramsSupplier.apply(new ParameterBuilder()).build()));
            return this;
        }

        public SymbolsBuilder operator(Operator operator, Type returnType, UnaryOperator<ParameterBuilder> paramsSupplier) {
            symbols.add(createOperatorFunctionSymbol(operator, returnType, paramsSupplier.apply(new ParameterBuilder()).build()));
            return this;
        }

        public SymbolsBuilder add(List<Symbol> members) {
            this.symbols.addAll(members);
            return this;
        }

        public List<Symbol> build() {
            return symbols;
        }
    }

    public static class ParameterBuilder {
        private final List<ParameterSymbol> parameters = new ArrayList<>();

        public ParameterBuilder parameter(String name, Type type, boolean optional, boolean vararg) {
            parameters.add(createParameterSymbol(name, type, optional, vararg));
            return this;
        }

        public ParameterBuilder parameter(String name, Type type) {
            return parameter(name, type, false, false);
        }

        public List<ParameterSymbol> build() {
            return parameters;
        }
    }

}
