package raylras.zen.code.symbol;

import com.google.common.collect.ImmutableList;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.data.Declarator;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.*;
import raylras.zen.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class NativeClassSymbol extends ClassSymbol {


    public static final VariableSymbol FIELD_ARRAY_LENGTH = new NativeField("length", IntType.INSTANCE);


    public static FunctionSymbol functionListRemove(ListType listType) {
        return new NativeFunction("remove",
            new FunctionType(Collections.singletonList(listType.elementType), VoidType.INSTANCE),
            Collections.singletonList("element"));
    }

    public static FunctionSymbol singleReturnFunction(String name, Type returnType) {
        return new NativeFunction(name,
            new FunctionType(Collections.emptyList(), returnType),
            Collections.emptyList());
    }

    public static VariableSymbol fieldNative(String name, Type keyType) {
        return new NativeField("key", keyType);
    }


    public static final ClassSymbol STRING = new NativeClassSymbol(StringType.INSTANCE, "string");
    public static final ClassSymbol INT = new NativeClassSymbol(IntType.INSTANCE, "int");
    public static final ClassSymbol BYTE = new NativeClassSymbol(ByteType.INSTANCE, "byte");
    public static final ClassSymbol SHORT = new NativeClassSymbol(ShortType.INSTANCE, "short");
    public static final ClassSymbol LONG = new NativeClassSymbol(LongType.INSTANCE, "long");
    public static final ClassSymbol FLOAT = new NativeClassSymbol(FloatType.INSTANCE, "float");
    public static final ClassSymbol DOUBLE = new NativeClassSymbol(DoubleType.INSTANCE, "double");
    public static final ClassSymbol BOOLEAN = new NativeClassSymbol(BoolType.INSTANCE, "boolean");
    public static final ClassSymbol INT_RANGE = new NativeClassSymbol(IntRangeType.INSTANCE, "stanhebben.zenscript.value.IntRange");

    private static final List<Symbol> intRangeMembers = ImmutableList.of(
        new NativeField("from", IntType.INSTANCE),
        new NativeField("to", IntType.INSTANCE)

    );

    public NativeClassSymbol(Type type, String name) {
        super(type, name, StringUtils.getSimpleName(name));
    }


    @Override
    public ParseTree getOwner() {
        throw new IllegalStateException();
    }

    @Override
    public CompilationUnit getUnit() {
        throw new IllegalStateException();
    }

    @Override
    public Scope getEnclosingScope() {
        return Scope.EMPTY;
    }

    @Override
    public ZenSymbolKind getKind() {
        return ZenSymbolKind.NATIVE_CLASS;
    }

    @Override
    public List<Symbol> getMembers() {
        if (getType() == IntRangeType.INSTANCE) {
            return intRangeMembers;
        }
        return Collections.emptyList();
    }

    @Override
    public boolean isLibrarySymbol() {
        return false;
    }

    public static class NativeFunction extends FunctionSymbol {

        private final String name;
        private final FunctionType type;
        private final List<String> paramNames;

        public NativeFunction(String name, FunctionType type, List<String> paramNames) {
            super(null, null, false);
            this.name = name;
            this.type = type;
            this.paramNames = paramNames;
        }


        @Override
        public ParseTree getOwner() {
            throw new IllegalStateException();
        }

        @Override
        public CompilationUnit getUnit() {
            throw new IllegalStateException();
        }

        @Override
        public Scope getEnclosingScope() {
            return Scope.EMPTY;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public FunctionType getType() {
            return type;
        }

        @Override
        public ZenSymbolKind getKind() {
            return ZenSymbolKind.FUNCTION;
        }

        @Override
        public Map<String, String> getAnnotations() {
            return Collections.emptyMap();
        }

        @Override
        public Type getReturnType() {
            return this.type.returnType;
        }

        @Override
        public List<String> getParamNames() {
            return paramNames;
        }

        @Override
        public boolean isLibrarySymbol() {
            return false;
        }
    }

    public static class NativeField extends VariableSymbol {
        private final String name;
        private final Type type;

        public NativeField(String name, Type type) {
            super(null, null);
            this.name = name;
            this.type = type;
        }


        @Override
        public ParseTree getOwner() {
            throw new IllegalStateException();
        }

        @Override
        public CompilationUnit getUnit() {
            throw new IllegalStateException();
        }

        @Override
        public Scope getEnclosingScope() {
            return Scope.EMPTY;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public ZenSymbolKind getKind() {
            return ZenSymbolKind.FIELD;
        }

        @Override
        public Type getType() {
            return this.type;
        }

        @Override
        public Declarator getDeclarator() {
            return Declarator.VAL;
        }


        @Override
        public boolean isLibrarySymbol() {
            return false;
        }
    }
}
