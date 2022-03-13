package raylras.zen.ast.type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class TypeFunctionTest {

    TypeFunction test1 = new TypeFunction(TypeInt.INSTANCE, TypeFloat.INSTANCE, TypeBool.INSTANCE);
    TypeFunction test2 = new TypeFunction(TypeVoid.INSTANCE, TypeByte.INSTANCE);
    TypeFunction test3 = new TypeFunction(new TypeArray(TypeDouble.INSTANCE), new TypeList(TypeShort.INSTANCE));
    TypeFunction test4 = new TypeFunction(new TypeFunction(TypeVoid.INSTANCE), new TypeMap(TypeString.INSTANCE, TypeString.INSTANCE));

    @Test
    void getTypeName() {
        Assertions.assertEquals("function(float,bool)int", test1.getTypeName());
        Assertions.assertEquals("function(byte)void", test2.toString());
        Assertions.assertEquals("function([short])double[]", test3.toString());
        Assertions.assertEquals("function(string[string])function()void", test4.toString()); // what the...

    }

    @Test
    void getReturnType() {
        Assertions.assertEquals("int", test1.getReturnType().getTypeName());
        Assertions.assertEquals("void", test2.getReturnType().getTypeName());
        Assertions.assertEquals("double[]", test3.getReturnType().getTypeName());
    }

    @Test
    void getArgumentTypes() {
        Assertions.assertEquals(Arrays.asList(TypeFloat.INSTANCE, TypeBool.INSTANCE), test1.getParameterTypes());
        Assertions.assertEquals(Arrays.asList(TypeByte.INSTANCE), test2.getParameterTypes());
        Assertions.assertEquals(Arrays.asList(new TypeList(TypeShort.INSTANCE)), test3.getParameterTypes());
    }

}