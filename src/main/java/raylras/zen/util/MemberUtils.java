package raylras.zen.util;

import com.google.common.collect.Lists;
import org.antlr.v4.runtime.ParserRuleContext;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.data.Declarator;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.*;
import raylras.zen.code.type.*;
import raylras.zen.code.type.resolve.ExpressionTypeResolver;
import raylras.zen.code.type.resolve.NameResolver;
import raylras.zen.service.EnvironmentService;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class MemberUtils {

    public static Tuple<Boolean, Type> resolveQualifierTarget(CompilationUnit unit, ZenScriptParser.ExpressionContext qualifier) {

        if (qualifier == null) {
            return Tuple.of(false, null);
        }
        Scope scope = unit.lookupScope(qualifier);
        String name = qualifier.getText();
        Symbol symbol = unit.lookupSymbol(Symbol.class, scope, name, true);

        if (symbol != null) {
            return Tuple.of(symbol.getKind().isClass(), symbol.getType());
        }

        // not a symbol
        return Tuple.of(false, new ExpressionTypeResolver(unit).resolve(qualifier));

    }

    public static List<Symbol> findImportedElement(EnvironmentService env, String name) {
        Symbol found = env.findSymbol(Symbol.class, name);
        if (found != null) {
            return Collections.singletonList(found);
        }

        // split the last name to handle method overloads
        List<String> prefixNames = Lists.newArrayList(name.split("\\."));
        String lastName = prefixNames.remove(prefixNames.size() - 1);


        Tuple<Type, Boolean> result = findImportedElementType(env, prefixNames);

        List<Symbol> ret = new ArrayList<>();
        iterateMembers(env, result.first, result.second, symbol -> {
            if (Objects.equals(name, symbol.getName())) {
                ret.add(symbol);
            }
        });
        return ret;
    }

    private static Tuple<Type, Boolean> findImportedElementType(EnvironmentService env, List<String> names) {
        StringBuilder nameSoFar = new StringBuilder();
        Type type = null;
        boolean isFirst = true;
        boolean isTypeStatic = false;
        for (String part : names) {
            if (type == null) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    nameSoFar.append('.');
                }
                nameSoFar.append(part);
                Symbol found = env.findSymbol(Symbol.class, nameSoFar.toString());
                if (found instanceof ClassSymbol) {
                    type = found.getType();
                    isTypeStatic = true;
                    continue;
                }
                if (found instanceof VariableSymbol) {
                    type = found.getType();
                    isTypeStatic = false;
                }
            } else {
                nameSoFar.append('.').append(part);
                Symbol found = findMember(env, type, isTypeStatic, part);

                if (found == null) {
                    type = new ErrorType("Could not find package or class: " + nameSoFar);
                    break;
                }

                type = found.getType();
                isTypeStatic = false;
            }

            if (type == null) {
                type = new ErrorType("Could not find package: " + nameSoFar);
            }
        }
        return Tuple.of(type, isTypeStatic);
    }


    public static Tuple<String, Collection<String>> findPackages(CompilationUnit unit, String name) {

        String result = null;
        Set<String> childPackages = new HashSet<>();
        EnvironmentService env = unit.environment();

        if (name.startsWith("scripts")) {
            String selfPackageName = unit.packageName();
            for (String packageName : env.scriptService().allPackageNames()) {
                if (packageName.startsWith(selfPackageName)) {
                    continue;
                }
                if (packageName.equals(name)) {
                    result = packageName;
                } else if (packageName.startsWith(name)) {
                    String subName = packageName.substring(name.length() + 1);
                    childPackages.add(StringUtils.getBeforeFirstDot(subName));
                }
            }
        } else {
            for (String packageName : env.libraryService().allPackageNames()) {
                if (packageName.equals(name)) {
                    result = packageName;
                } else if (packageName.startsWith(name)) {
                    String subName = packageName.substring(name.length() + 1);
                    childPackages.add(StringUtils.getBeforeFirstDot(subName));
                }
            }
        }

        return Tuple.of(result, childPackages);

    }

    private static void iterateNativeMembers(EnvironmentService environmentService, Type
        type, Consumer<Symbol> consumer) {
        if (type.getKind() == Type.Kind.CLASS) {
            return;
        }

        switch (type.getKind()) {
            case INT_RANGE:
                NativeClassSymbol.INT_RANGE.getMembers().forEach(consumer);
                break;
            case MAP_ENTRY:
                MapEntryType mapEntryType = (MapEntryType) type;
                consumer.accept(NativeClassSymbol.fieldNative("key", mapEntryType.keyType));
                consumer.accept(NativeClassSymbol.fieldNative("value", mapEntryType.valueType));
                break;
            case NUMBER:
            case STRING:
            case BOOL:
                environmentService.libraryService().getNativeMembers(((NamedType) type).getName()).forEach(consumer);
                break;
            case ARRAY:
                consumer.accept(NativeClassSymbol.FIELD_ARRAY_LENGTH);
                break;
            case LIST:
                consumer.accept(NativeClassSymbol.FIELD_ARRAY_LENGTH);
                consumer.accept(NativeClassSymbol.functionListRemove((ListType) type));
                break;
            case MAP:
                MapType mapType = (MapType) type;
                consumer.accept(NativeClassSymbol.FIELD_ARRAY_LENGTH);
                consumer.accept(NativeClassSymbol.singleReturnFunction("keys", new ArrayType(mapType.keyType)));
                consumer.accept(NativeClassSymbol.singleReturnFunction("keySet", new ArrayType(mapType.keyType)));
                consumer.accept(NativeClassSymbol.singleReturnFunction("values", new ArrayType(mapType.valueType)));
                consumer.accept(NativeClassSymbol.singleReturnFunction("valueSet", new ArrayType(mapType.valueType)));
                consumer.accept(NativeClassSymbol.singleReturnFunction("entries", new ArrayType(mapType.getEntryType())));
                consumer.accept(NativeClassSymbol.singleReturnFunction("entrySet", new ArrayType(mapType.getEntryType())));
                break;


        }
        environmentService.getExpandFunctions(type.toString()).forEach(consumer);
    }

    public static void iterateMembers(EnvironmentService environmentService, Type type, boolean isStatic, Consumer<
        Symbol> consumer) {
        if (type == null) {
            return;
        }
        if (isStatic) {
            if (type.getKind() != Type.Kind.CLASS) {
                return;
            }

            for (Symbol member : ((ClassType) type).getSymbol().getMembers()) {
                if (member.isDeclaredBy(Declarator.STATIC)) {
                    consumer.accept(member);
                }
            }
        }
        if (type.getKind() == Type.Kind.CLASS) {
            environmentService.typeService()
                .getAllMembers(((ClassType) type).getSymbol())
                .forEach(it -> {
                    if (!it.isDeclaredBy(Declarator.STATIC)) {
                        consumer.accept(it);
                    }
                });
            environmentService.getExpandFunctions(type.toString()).forEach(consumer);
        } else {
            iterateNativeMembers(environmentService, type, consumer);
        }


    }

    public static Symbol findMember(EnvironmentService environmentService, Type type, boolean isStatic, String name) {
        if (isStatic) {
            if (type.getKind() != Type.Kind.CLASS) {
                return null;
            }

            for (Symbol member : ((ClassType) type).getSymbol().getMembers()) {
                if (member.isDeclaredBy(Declarator.STATIC) && Objects.equals(member.getName(), name)) {
                    return member;
                }
            }
            return null;
        }
        if (type.getKind() == Type.Kind.CLASS) {
            return environmentService.typeService()
                .findMember(Symbol.class, ((ClassType) type).getSymbol(), name);
            // TODO: support member access operator override
        } else {
            AtomicReference<Symbol> result = new AtomicReference<>();
            iterateNativeMembers(environmentService, type, it -> {
                if (Objects.equals(it.getName(), name)) {
                    result.set(it);
                }
            });
            return result.get();
        }


    }

}
