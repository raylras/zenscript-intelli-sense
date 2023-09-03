package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.code.resolve.FormalParameterResolver;
import raylras.zen.code.resolve.ModifierResolver;
import raylras.zen.code.resolve.SymbolResolver;
import raylras.zen.code.resolve.TypeResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.AnyType;
import raylras.zen.code.type.ClassType;
import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.Type;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.Operators;
import raylras.zen.util.PackageTree;
import raylras.zen.util.Range;

import java.nio.file.Path;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class SymbolFactory {

    private SymbolFactory() {
    }

    public static ImportSymbol createImportSymbol(ParseTree nameCst, ImportDeclarationContext cst, CompilationUnit unit) {
        class ImportSymbolImpl implements ImportSymbol, Locatable {
            private final String name = CSTNodes.getText(nameCst);
            private final Range range = Range.of(cst);
            private final Range selectionRange = Range.of(nameCst);

            @Override
            public String getQualifiedName() {
                return cst.qualifiedName().getText();
            }

            @Override
            public Collection<Symbol> getTargets() {
                return SymbolResolver.getSymbol(cst.qualifiedName(), unit);
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Kind getKind() {
                return Kind.IMPORT;
            }

            @Override
            public Type getType() {
                // TODO: import static members
                return unit.getEnv().getClassTypeMap().get(getQualifiedName());
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
                return range;
            }

            @Override
            public Range getSelectionRange() {
                return selectionRange;
            }
        }
        return new ImportSymbolImpl();
    }

    public static ClassSymbol createClassSymbol(ParseTree nameCst, ClassDeclarationContext cst, CompilationUnit unit) {
        class ClassSymbolImpl implements ClassSymbol, Locatable {
            private final String name = CSTNodes.getText(nameCst);
            private final Range range = Range.of(cst);
            private final Range selectionRange = Range.of(nameCst);
            private final ClassType classType = new ClassType(this);

            @Override
            public String getQualifiedName() {
                String packageName = unit.getPackage();
                return packageName.isEmpty() ? name : packageName + "." + name;
            }

            @Override
            public List<Symbol> getDeclaredMembers() {
                return unit.getScope(cst).getSymbols();
            }

            @Override
            public List<Symbol> getMembers() {
                List<Symbol> symbols = new ArrayList<>(getDeclaredMembers());
                for (ClassType superClass : getInterfaces()) {
                    symbols.addAll(superClass.getSymbols());
                }
                return symbols;
            }

            @Override
            public List<ClassType> getInterfaces() {
                if (cst.qualifiedNameList() == null) {
                    return Collections.emptyList();
                }
                Map<String, ClassType> classTypeMap = unit.getEnv().getClassTypeMap();
                Scope scope = unit.lookupScope(cst);
                return cst.qualifiedNameList().qualifiedName().stream()
                        .map(CSTNodes::getText)
                        .map(interfaceName -> scope.lookupSymbol(ImportSymbol.class, interfaceName))
                        .filter(Objects::nonNull)
                        .map(ImportSymbol::getQualifiedName)
                        .map(classTypeMap::get)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
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
                return name;
            }

            @Override
            public Kind getKind() {
                return Kind.CLASS;
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
                return range;
            }

            @Override
            public Range getSelectionRange() {
                return selectionRange;
            }
        }
        return new ClassSymbolImpl();
    }

    public static VariableSymbol createVariableSymbol(ParseTree nameCst, ParseTree cst, CompilationUnit unit) {
        class VariableSymbolImpl implements VariableSymbol, Locatable {
            private final String name = CSTNodes.getText(nameCst);
            private final Modifier modifier = ModifierResolver.getModifier(cst);
            private final Range range = Range.of(cst);
            private final Range selectionRange = Range.of(nameCst);

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
                return TypeResolver.getType(cst, unit);
            }

            @Override
            public Modifier getModifier() {
                return modifier;
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
                return range;
            }

            @Override
            public Range getSelectionRange() {
                return selectionRange;
            }
        }
        return new VariableSymbolImpl();
    }

    public static VariableSymbol createVariableSymbol(String name, Type type, Symbol.Modifier modifier) {
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
        class FunctionSymbolImpl implements FunctionSymbol, Locatable {
            private final String name = CSTNodes.getText(nameCst);
            private final Modifier modifier = ModifierResolver.getModifier(cst);
            private final Range range = Range.of(cst);
            private final Range selectionRange = Range.of(nameCst);

            @Override
            public FunctionType getType() {
                Type type = TypeResolver.getType(cst, unit);
                return (type instanceof FunctionType) ? (FunctionType) type : new FunctionType(AnyType.INSTANCE);
            }

            @Override
            public List<ParameterSymbol> getParameterList() {
                return FormalParameterResolver.getFormalParameterList(cst, unit);
            }

            @Override
            public Type getReturnType() {
                return getType().getReturnType();
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Symbol.Kind getKind() {
                return Symbol.Kind.FUNCTION;
            }

            @Override
            public Symbol.Modifier getModifier() {
                return modifier;
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
                return range;
            }

            @Override
            public Range getSelectionRange() {
                return selectionRange;
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
                return Symbol.Modifier.NONE;
            }
        }
        return new FunctionSymbolImpl();
    }

    public static OperatorFunctionSymbol createOperatorFunctionSymbol(OperatorContext opCst, OperatorFunctionDeclarationContext cst, CompilationUnit unit) {
        class OperatorFunctionSymbolImpl implements OperatorFunctionSymbol, Locatable {
            private final Operator operator = Operators.of(opCst.getText(), cst.formalParameterList().formalParameter().size());
            private final Range range = Range.of(cst);
            private final Range selectionRange = Range.of(opCst);

            @Override
            public Operator getOperator() {
                return operator;
            }

            @Override
            public FunctionType getType() {
                Type type = TypeResolver.getType(cst, unit);
                return (type instanceof FunctionType) ? (FunctionType) type : new FunctionType(AnyType.INSTANCE);
            }

            @Override
            public List<ParameterSymbol> getParameterList() {
                return FormalParameterResolver.getFormalParameterList(cst, unit);
            }

            @Override
            public Type getReturnType() {
                return getType().getReturnType();
            }

            @Override
            public String getName() {
                return operator.getLiteral();
            }

            @Override
            public Kind getKind() {
                return Kind.FUNCTION;
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
                return range;
            }

            @Override
            public Range getSelectionRange() {
                return selectionRange;
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
                return Kind.FUNCTION;
            }

            @Override
            public Modifier getModifier() {
                return Modifier.NONE;
            }
        }
        return new OperatorFunctionSymbolImpl();
    }

    public static ParameterSymbol createParameterSymbol(ParseTree nameCst, FormalParameterContext cst, CompilationUnit unit) {
        class ParameterSymbolImpl implements ParameterSymbol, Locatable {
            private final String name = CSTNodes.getText(nameCst);
            private final Range range = Range.of(cst);
            private final Range selectionRange = Range.of(nameCst);

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
                return name;
            }

            @Override
            public Kind getKind() {
                return Kind.PARAMETER;
            }

            @Override
            public Type getType() {
                return TypeResolver.getType(cst, unit);
            }

            @Override
            public Modifier getModifier() {
                return Symbol.Modifier.NONE;
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
                return range;
            }

            @Override
            public Range getSelectionRange() {
                return selectionRange;
            }
        }
        return new ParameterSymbolImpl();
    }

    public static ExpandFunctionSymbol createExpandFunctionSymbol(ParseTree nameCst, ExpandFunctionDeclarationContext cst, CompilationUnit unit) {
        class ExpandFunctionSymbolImpl implements ExpandFunctionSymbol, Locatable {
            private final String name = CSTNodes.getText(nameCst);
            private final Range range = Range.of(cst);
            private final Range selectionRange = Range.of(nameCst);

            @Override
            public List<ParameterSymbol> getParameterList() {
                return FormalParameterResolver.getFormalParameterList(cst, unit);
            }

            @Override
            public Type getReturnType() {
                return getType().getReturnType();
            }

            @Override
            public Type getOwner() {
                return TypeResolver.getType(cst.typeLiteral(), unit);
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
                return Modifier.EXPAND;
            }

            @Override
            public FunctionType getType() {
                Type type = TypeResolver.getType(cst, unit);
                return (type instanceof FunctionType) ? (FunctionType) type : new FunctionType(AnyType.INSTANCE);
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
                return range;
            }

            @Override
            public Range getSelectionRange() {
                return selectionRange;
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
                return Modifier.NONE;
            }
        }
        return new ParameterSymbolImpl();
    }

    public static <T> PackageSymbol createPackageSymbol(String name, String qualifiedName, PackageTree<ClassSymbol> packageTree, CompilationEnvironment environment, boolean isGenerated) {

        class PackageSymbolImpl implements PackageSymbol, UriLocatable {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Kind getKind() {
                return Kind.PACKAGE;
            }

            @Override
            public Type getType() {
                return AnyType.INSTANCE;
            }

            @Override
            public Modifier getModifier() {
                return Modifier.NONE;
            }

            @Override
            public String getUri() {
                Path root;
                if (isGenerated) {
                    root = environment.getGeneratedRoot();
                } else {
                    root = environment.getRoot();
                }
                if (packageTree.hasElement()) {
                    String relativePath = qualifiedName.replace(".", "/");
                    if (isGenerated) {
                        relativePath += ".dzs";
                    } else {
                        relativePath += ".zs";
                    }
                    Path file = root.resolve(relativePath).normalize();
                    return file.toUri().toString();
                } else {
                    Path dir = root.resolve(qualifiedName.replace(".", "/")).normalize();
                    return dir.toUri().toString();
                }
            }

            @Override
            public String getQualifiedName() {
                return qualifiedName;
            }

            @Override
            public List<Symbol> getSymbols() {
                List<Symbol> result = new ArrayList<>();
                for (Map.Entry<String, PackageTree<ClassSymbol>> entry : packageTree.getSubTrees().entrySet()) {
                    PackageTree<ClassSymbol> tree = entry.getValue();
                    if (tree.hasElement()) {
                        result.add(tree.getElement());
                    } else {
                        String name = entry.getKey();
                        PackageSymbol packageSymbol = createPackageSymbol(name, qualifiedName + "." + name, entry.getValue(), environment, isGenerated);
                        result.add(packageSymbol);
                    }
                }
                return result;
            }
        }

        return new PackageSymbolImpl();
    }


    public static SymbolsBuilder builtinSymbols() {
        return new SymbolsBuilder();
    }

    public static class SymbolsBuilder {
        private final List<Symbol> symbols = new ArrayList<>();

        public SymbolsBuilder variable(String name, Type type, Symbol.Modifier modifier) {
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
