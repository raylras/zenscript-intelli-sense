package raylras.zen.service;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.symbol.*;
import raylras.zen.code.type.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Provide support to resolve types
 */
public class TypeService {

    private void mergeOverloadFunctions(List<FunctionSymbol> childFunctions, List<FunctionSymbol> parentFunctions) {

        Map<String, FunctionSymbol> mergingFunction = childFunctions.stream()
            .collect(Collectors.toMap(
                it -> it.getType().getParamsSignature(),
                Function.identity()
            ));
        for (FunctionSymbol parentFunction : parentFunctions) {
            String signature = parentFunction.getType().getParamsSignature();
            if (mergingFunction.containsKey(signature)) {
                continue;
            }
            mergingFunction.put(signature, parentFunction);
            childFunctions.add(parentFunction);
        }
    }

    /**
     * get all direct members ( constructor, function, field)
     */
    public List<Symbol> getAllDirectMembers(ClassSymbol classSymbol) {
        // TODO: Cache results for performance
        List<Symbol> result = new ArrayList<>();

        Map<String, List<FunctionSymbol>> functions = new HashMap<>();
        Map<String, VariableSymbol> variables = new HashMap<>();

        for (Symbol member : classSymbol.getMembers()) {
            ZenSymbolKind kind = member.getKind();
            if (kind == ZenSymbolKind.FIELD) {
                variables.put(member.getName(), (VariableSymbol) member);
            } else if (kind == ZenSymbolKind.FUNCTION) {
                if (member.isHidden()) {
                    continue;
                }
                functions.computeIfAbsent(member.getName(), it -> new ArrayList<>())
                    .add((FunctionSymbol) member);
            } else if (kind == ZenSymbolKind.CONSTRUCTOR) {
                // Constructor does not extend
                result.add(member);
            }
        }


        for (ClassSymbol parent : classSymbol.getParents()) {

            Map<String, List<FunctionSymbol>> parentFunctions = new HashMap<>();
            for (Symbol member : getAllDirectMembers(parent)) {
                ZenSymbolKind kind = member.getKind();
                if (kind == ZenSymbolKind.FIELD) {
                    variables.putIfAbsent(member.getName(), (VariableSymbol) member);
                } else if (kind == ZenSymbolKind.FUNCTION) {
                    parentFunctions.computeIfAbsent(member.getName(), it -> new ArrayList<>())
                        .add((FunctionSymbol) member);
                }
            }

            // merge functions
            for (Map.Entry<String, List<FunctionSymbol>> entry : parentFunctions.entrySet()) {
                String name = entry.getKey();
                if (!functions.containsKey(name)) {
                    functions.put(name, entry.getValue());
                    continue;
                }
                mergeOverloadFunctions(functions.get(name), entry.getValue());

            }

        }


        for (List<FunctionSymbol> item : functions.values()) {
            result.addAll(item);
        }

        result.addAll(variables.values());

        return result;

    }


    public List<Symbol> getAllMembers(ClassSymbol classSymbol) {
        // TODO: extend functions
        return getAllDirectMembers(classSymbol);
    }


    public List<Type> getCasters(ClassSymbol classSymbol) {
        List<Type> result = new ArrayList<>();
        for (Symbol member : classSymbol.getMembers()) {
            if (member.getKind() != ZenSymbolKind.OPERATOR) {
                continue;
            }
            FunctionSymbol op = (FunctionSymbol) member;
            if (op.getOperatorType() != OperatorType.CASTER) {
                continue;
            }
            result.add(op.getReturnType());
        }

        for (ClassSymbol parent : classSymbol.getParents()) {
            List<Type> parentCasters = getCasters(parent);
            result.addAll(parentCasters);
        }
        return result;
    }


    public FunctionSymbol getOperatorOf(ClassSymbol classSymbol, OperatorType operatorType) {
        for (Symbol member : classSymbol.getMembers()) {
            if (member.getKind() != ZenSymbolKind.OPERATOR) {
                continue;
            }
            FunctionSymbol op = (FunctionSymbol) member;
            if (op.getOperatorType() == operatorType) {
                return op;
            }
        }
        for (ClassSymbol parent : classSymbol.getParents()) {
            FunctionSymbol found = getOperatorOf(parent, operatorType);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    public boolean isNullable(Type targetType) {
        Type.Kind targetKind = targetType.getKind();
        if (targetKind == Type.Kind.NUMBER) {
            return false;
        }
        return true;
    }

    public boolean hasExpandCaster(Type targetType, Type sourceType) {
        return false;
    }

    private final Map<Type, Set<Type>> internalCasters = ImmutableMap.<Type, Set<Type>>builder()
        .put(IntType.INSTANCE, ImmutableSet.of(ByteType.INSTANCE, ShortType.INSTANCE, LongType.INSTANCE, FloatType.INSTANCE, DoubleType.INSTANCE, StringType.INSTANCE))
        .put(ByteType.INSTANCE, ImmutableSet.of(ShortType.INSTANCE, IntType.INSTANCE, LongType.INSTANCE, FloatType.INSTANCE, DoubleType.INSTANCE, StringType.INSTANCE))
        .put(ShortType.INSTANCE, ImmutableSet.of(ByteType.INSTANCE, IntType.INSTANCE, LongType.INSTANCE, FloatType.INSTANCE, DoubleType.INSTANCE, StringType.INSTANCE))
        .put(LongType.INSTANCE, ImmutableSet.of(ByteType.INSTANCE, ShortType.INSTANCE, IntType.INSTANCE, FloatType.INSTANCE, DoubleType.INSTANCE, StringType.INSTANCE))
        .put(FloatType.INSTANCE, ImmutableSet.of(ByteType.INSTANCE, ShortType.INSTANCE, IntType.INSTANCE, LongType.INSTANCE, DoubleType.INSTANCE, StringType.INSTANCE))
        .put(DoubleType.INSTANCE, ImmutableSet.of(ByteType.INSTANCE, ShortType.INSTANCE, IntType.INSTANCE, LongType.INSTANCE, FloatType.INSTANCE, StringType.INSTANCE))
        .put(StringType.INSTANCE, ImmutableSet.of(BoolType.INSTANCE, ByteType.INSTANCE, ShortType.INSTANCE, IntType.INSTANCE, LongType.INSTANCE, FloatType.INSTANCE))
        .put(BoolType.INSTANCE, ImmutableSet.of(StringType.INSTANCE))
        .build();

    private boolean hasInternalCaster(Type targetType, Type sourceType) {
        return internalCasters.getOrDefault(sourceType, Collections.emptySet()).contains(targetType);
    }

    public boolean hasCaster(Type targetType, Type sourceType) {
        if (sourceType.getKind() != Type.Kind.CLASS) {
            return false;
        }

        ClassSymbol sourceSymbol = ((ClassType) sourceType).getSymbol();

        List<Type> casters = getCasters(sourceSymbol);

        return casters.contains(targetType);

    }

    public boolean isSameType(ClassType first, ClassType second) {
        return first.getName().equals(second.getName());
    }

    public boolean isAssignableTo(ClassType currentType, ClassType targetType) {
        if (isSameType(currentType, targetType)) {
            return true;
        }
        // not library, do not have extends
        if (!currentType.isLibraryClass() || !targetType.isLibraryClass()) {
            return false;
        }

        ArrayDeque<ClassSymbol> searchQueue = new ArrayDeque<>();
        ClassSymbol currentSymbol = currentType.getSymbol();
        String targetName = targetType.getName();

        for (ClassSymbol parent : currentSymbol.getParents()) {
            searchQueue.push(parent);
        }

        while (!searchQueue.isEmpty()) {
            ClassSymbol symbol = searchQueue.pop();

            if (symbol.getQualifiedName().equals(targetName)) {
                return true;
            }

            for (ClassSymbol parent : symbol.getParents()) {
                searchQueue.push(parent);
            }
        }

        return false;

    }

    /**
     * check whether sourceType can be cast into target type
     */
    public boolean canCast(Type targetType, Type sourceType) {
        if (sourceType.equals(targetType)) {
            return true;
        }

        Type.Kind sourceKind = sourceType.getKind();
        Type.Kind targetKind = targetType.getKind();

        // do not convert void
        if (targetKind == Type.Kind.VOID || sourceKind == Type.Kind.VOID) {
            return false;
        }

        // do not convert functions
        if (sourceKind == Type.Kind.FUNCTION || targetKind == Type.Kind.FUNCTION) {
            return false;
        }

        // do not convert ZenClass
        if (sourceKind == Type.Kind.CLASS && !((ClassType) sourceType).isLibraryClass()) {
            return false;
        }
        if (targetKind == Type.Kind.CLASS && !((ClassType) targetType).isLibraryClass()) {
            return false;
        }

        // any should always be able to convert
        if (sourceKind == Type.Kind.ANY || targetKind == Type.Kind.ANY) {
            return true;
        }

        // handle nullable value convert
        if (sourceKind == Type.Kind.NULL) {
            return isNullable(targetType);
        }

        if (sourceKind == Type.Kind.CLASS && targetKind == Type.Kind.CLASS) {
            // this is library class, consider extends
            if (isAssignableTo((ClassType) sourceType, (ClassType) targetType)) {
                return true;
            }
        }

        // consider predefined casters
        if (hasInternalCaster(targetType, sourceType) || hasCaster(targetType, sourceType)) {
            return true;
        }

        // consider expand casters
        if (hasExpandCaster(targetType, sourceType)) {
            return true;
        }

        // special for collections
        if (sourceKind == Type.Kind.MAP) {
            if (targetKind != Type.Kind.MAP) {
                return false;
            }

            MapType sourceMapType = (MapType) sourceType;
            // only when target is map and source is any can cast implicitly
            return sourceMapType.keyType.getKind() == Type.Kind.ANY &&
                sourceMapType.valueType.getKind() == Type.Kind.ANY;
        }

        if (sourceKind == Type.Kind.LIST || sourceKind == Type.Kind.ARRAY) {
            // list to list is not supported
            if (targetKind != Type.Kind.ARRAY) {
                return false;
            }

            Type sourceElementType = sourceType instanceof ArrayType ? ((ArrayType) sourceType).elementType : ((ListType) sourceType).elementType;
            Type targetElementType = ((ArrayType) targetType).elementType;

            return canCast(targetElementType, sourceElementType);
        }

        return false;

    }


}
