package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser.ClassDeclarationContext;
import raylras.zen.code.parser.ZenScriptParser.FormalParameterContext;
import raylras.zen.code.parser.ZenScriptParser.ImportDeclarationContext;
import raylras.zen.code.parser.ZenScriptParser.OperatorFunctionDeclarationContext;
import raylras.zen.code.resolve.FormalParameterResolver;
import raylras.zen.code.resolve.ModifierResolver;
import raylras.zen.code.resolve.TypeResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.*;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.Ranges;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SymbolFactory {

    private SymbolFactory() {}

    public static ImportSymbol createImportSymbol(String name, ImportDeclarationContext cst, CompilationUnit unit) {
        Class<?>[] interfaces = {Locatable.class};
        return createInstance(ImportSymbol.class, interfaces, (proxy, method, args) -> {
            ImportSymbol symbol = (ImportSymbol) proxy;
            switch (method.getName()) {
                // ImportSymbol methods
                case "getQualifiedName": {
                    return cst.qualifiedName().getText();
                }

                // Symbol methods
                case "getName": {
                    return name;
                }
                case "getKind": {
                    return Symbol.Kind.IMPORT;
                }
                case "getType": {
                    return VoidType.INSTANCE;
                }
                case "getModifier": {
                    return Symbol.Modifier.NONE;
                }
                case "isModifiedBy": {
                    return symbol.getModifier() == args[0];
                }

                // Locatable methods
                case "getCst": {
                    return cst;
                }
                case "getUnit": {
                    return unit;
                }
                case "getRange": {
                    return Ranges.of(cst);
                }

                default: {
                    return method.invoke(new Object(), args);
                }
            }
        });
    }

    public static ClassSymbol createClassSymbol(String name, ClassDeclarationContext cst, CompilationUnit unit) {
        Class<?>[] interfaces = {Locatable.class};
        return createInstance(ClassSymbol.class, interfaces, (proxy, method, args) -> {
            ClassSymbol symbol = (ClassSymbol) proxy;
            switch (method.getName()) {
                // ClassSymbol methods
                case "getQualifiedName": {
                    String packageName = unit.getPackage();
                    return packageName.isEmpty() ? name : packageName + "." + name;
                }
                case "getDeclaredMembers": {
                    return unit.getScope(cst).getSymbols();
                }
                case "getMembers": {
                    List<Symbol> symbols = new ArrayList<>(symbol.getDeclaredMembers());
                    for (ClassType superClass : symbol.getInterfaces()) {
                        symbols.addAll(superClass.getMembers());
                    }
                    return symbols;
                }
                case "getInterfaces": {
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

                // Symbol methods
                case "getName": {
                    return name;
                }
                case "getKind": {
                    return Symbol.Kind.CLASS;
                }
                case "getType": {
                    return new ClassType((ClassSymbol) proxy);
                }
                case "getModifier": {
                    return ModifierResolver.getModifier(cst);
                }
                case "isModifiedBy": {
                    return symbol.getModifier() == args[0];
                }

                // Locatable methods
                case "getCst": {
                    return cst;
                }
                case "getUnit": {
                    return unit;
                }
                case "getRange": {
                    return Ranges.of(cst);
                }

                default: {
                    return method.invoke(new Object(), args);
                }
            }
        });
    }

    public static VariableSymbol createVariableSymbol(String name, ParseTree cst, CompilationUnit unit) {
        Class<?>[] interfaces = {Locatable.class};
        return createInstance(VariableSymbol.class, interfaces, (proxy, method, args) -> {
            VariableSymbol symbol = (VariableSymbol) proxy;
            switch (method.getName()) {
                // Symbol methods
                case "getName": {
                    return name;
                }
                case "getKind": {
                    return Symbol.Kind.VARIABLE;
                }
                case "getType": {
                    return TypeResolver.getType(cst, unit);
                }
                case "getModifier": {
                    return ModifierResolver.getModifier(cst);
                }
                case "isModifiedBy": {
                    return symbol.getModifier() == args[0];
                }

                // Locatable methods
                case "getCst": {
                    return cst;
                }
                case "getUnit": {
                    return unit;
                }
                case "getRange": {
                    return Ranges.of(cst);
                }

                default: {
                    return method.invoke(new Object(), args);
                }
            }
        });
    }

    public static VariableSymbol createVariableSymbol(String name, Type type, Symbol.Modifier modifier) {
        Class<?>[] interfaces = {};
        return createInstance(VariableSymbol.class, interfaces, (proxy, method, args) -> {
            VariableSymbol symbol = (VariableSymbol) proxy;
            switch (method.getName()) {
                // Symbol methods
                case "getName": {
                    return name;
                }
                case "getKind": {
                    return Symbol.Kind.VARIABLE;
                }
                case "getType": {
                    return type;
                }
                case "getModifier": {
                    return modifier;
                }
                case "isModifiedBy": {
                    return symbol.getModifier() == args[0];
                }

                default: {
                    return method.invoke(new Object(), args);
                }
            }
        });
    }

    public static FunctionSymbol createFunctionSymbol(String name, ParseTree cst, CompilationUnit unit) {
        Class<?>[] interfaces = {Locatable.class};
        return createInstance(FunctionSymbol.class, interfaces, (proxy, method, args) -> {
            FunctionSymbol symbol = (FunctionSymbol) proxy;
            switch (method.getName()) {
                // FunctionSymbol methods
                case "getParameterList": {
                    return FormalParameterResolver.getFormalParameterList(cst, unit);
                }
                case "getReturnType": {
                    Type type = TypeResolver.getType(cst, unit);
                    if (type instanceof FunctionType) {
                        return ((FunctionType) type).getReturnType();
                    } else {
                        return AnyType.INSTANCE;
                    }
                }

                // Symbol methods
                case "getName": {
                    return name;
                }
                case "getKind": {
                    return Symbol.Kind.VARIABLE;
                }
                case "getType": {
                    return TypeResolver.getType(cst, unit);
                }
                case "getModifier": {
                    return ModifierResolver.getModifier(cst);
                }
                case "isModifiedBy": {
                    return symbol.getModifier() == args[0];
                }

                // Locatable methods
                case "getCst": {
                    return cst;
                }
                case "getUnit": {
                    return unit;
                }
                case "getRange": {
                    return Ranges.of(cst);
                }

                default: {
                    return method.invoke(new Object(), args);
                }
            }
        });
    }

    public static FunctionSymbol createFunctionSymbol(String name, Type returnType, List<Symbol> params) {
        Class<?>[] interfaces = {};
        return createInstance(FunctionSymbol.class, interfaces, (proxy, method, args) -> {
            FunctionSymbol symbol = (FunctionSymbol) proxy;
            switch (method.getName()) {
                // FunctionSymbol methods
                case "getParameterList": {
                    return params;
                }
                case "getReturnType": {
                    return returnType;
                }

                // Symbol methods
                case "getName": {
                    return name;
                }
                case "getKind": {
                    return Symbol.Kind.VARIABLE;
                }
                case "getType": {
                    return params.stream()
                            .map(Symbol::getType)
                            .map(Type::toString)
                            .collect(Collectors.joining(",", "function(", ")" + returnType));
                }
                case "getModifier": {
                    return Symbol.Modifier.NONE;
                }
                case "isModifiedBy": {
                    return symbol.getModifier() == args[0];
                }

                default: {
                    return method.invoke(new Object(), args);
                }
            }
        });
    }

    public static OperatorFunctionSymbol createOperatorFunctionSymbol(String name, OperatorFunctionDeclarationContext cst, CompilationUnit unit) {
        Class<?>[] interfaces = {Locatable.class};
        return createInstance(OperatorFunctionSymbol.class, interfaces, (proxy, method, args) -> {
            OperatorFunctionSymbol symbol = (OperatorFunctionSymbol) proxy;
            switch (method.getName()) {
                // OperatorFunctionSymbol methods
                case "getOperator": {
                    return OperatorFunctionSymbol.Operator.ofLiteral(name);
                }
                case "getParameterList": {
                    return FormalParameterResolver.getFormalParameterList(cst, unit);
                }
                case "getReturnType": {
                    Type type = TypeResolver.getType(cst, unit);
                    if (type instanceof FunctionType) {
                        return ((FunctionType) type).getReturnType();
                    } else {
                        return AnyType.INSTANCE;
                    }
                }

                // Symbol methods
                case "getName": {
                    return name;
                }
                case "getKind": {
                    return Symbol.Kind.VARIABLE;
                }
                case "getType": {
                    return TypeResolver.getType(cst, unit);
                }
                case "getModifier": {
                    return Symbol.Modifier.NONE;
                }
                case "isModifiedBy": {
                    return symbol.getModifier() == args[0];
                }

                // Locatable methods
                case "getCst": {
                    return cst;
                }
                case "getUnit": {
                    return unit;
                }
                case "getRange": {
                    return Ranges.of(cst);
                }

                default: {
                    return method.invoke(new Object(), args);
                }
            }
        });
    }

    public static ParameterSymbol createParameterSymbol(String name, FormalParameterContext cst, CompilationUnit unit) {
        Class<?>[] interfaces = {Locatable.class};
        return createInstance(ParameterSymbol.class, interfaces, (proxy, method, args) -> {
            ParameterSymbol symbol = (ParameterSymbol) proxy;
            switch (method.getName()) {
                // ParameterSymbol methods
                case "isOptional": {
                    return cst.defaultValue() != null;
                }
                case "isVararg": {
                    return cst.varargsPrefix() != null;
                }

                // Symbol methods
                case "getName": {
                    return name;
                }
                case "getKind": {
                    return Symbol.Kind.PARAMETER;
                }
                case "getType": {
                    return TypeResolver.getType(cst, unit);
                }
                case "getModifier": {
                    return Symbol.Modifier.NONE;
                }
                case "isModifiedBy": {
                    return symbol.getModifier() == args[0];
                }

                default: {
                    return method.invoke(new Object(), args);
                }
            }
        });
    }

    public static ParameterSymbol createParameterSymbol(String name, Type type, boolean optional, boolean vararg) {
        Class<?>[] interfaces = {};
        return createInstance(ParameterSymbol.class, interfaces, (proxy, method, args) -> {
            ParameterSymbol symbol = (ParameterSymbol) proxy;
            switch (method.getName()) {
                // ParameterSymbol methods
                case "isOptional": {
                    return optional;
                }
                case "isVararg": {
                    return vararg;
                }

                // Symbol methods
                case "getName": {
                    return name;
                }
                case "getKind": {
                    return Symbol.Kind.PARAMETER;
                }
                case "getType": {
                    return type;
                }
                case "getModifier": {
                    return Symbol.Modifier.NONE;
                }
                case "isModifiedBy": {
                    return symbol.getModifier() == args[0];
                }

                default: {
                    return method.invoke(new Object(), args);
                }
            }
        });
    }

    public static MemberBuilder members() {
        return new MemberBuilder();
    }

    private static <T extends Symbol> T createInstance(Class<T> clazz, Class<?>[] interfaces, InvocationHandler handler) {
        ClassLoader classLoader = SymbolFactory.class.getClassLoader();
        ArrayList<Class<?>> interfaceList = new ArrayList<>(interfaces.length + 1);
        interfaceList.add(clazz);
        interfaceList.addAll(Arrays.asList(interfaces));
        Object instance = Proxy.newProxyInstance(classLoader, interfaceList.toArray(new Class[0]), handler);
        return clazz.cast(instance);
    }

    public static class MemberBuilder {
        private final List<Symbol> members = new ArrayList<>();

        public MemberBuilder variable(String name, Type type, Symbol.Modifier modifier) {
            members.add(createVariableSymbol(name, type, modifier));
            return this;
        }

        public MemberBuilder function(String name, Type returnType, Function<MemberBuilder, MemberBuilder> paramsSupplier) {
            members.add(createFunctionSymbol(name, returnType, paramsSupplier.apply(new MemberBuilder()).build()));
            return this;
        }

        public MemberBuilder parameter(String name, Type type, boolean optional, boolean vararg) {
            members.add(createParameterSymbol(name, type, optional, vararg));
            return this;
        }

        public List<Symbol> build() {
            return members;
        }
    }

}
