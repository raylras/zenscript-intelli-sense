package raylras.zen.code.type;

import java.util.List;
import java.util.stream.Collectors;

public class UnionType extends Type {

    private final List<Type> typeList;

    public UnionType(List<Type> typeList) {
        this.typeList = typeList;
    }

    public List<Type> getTypeList() {
        return typeList;
    }

    @Override
    public String toString() {
        return typeList.stream().map(Object::toString).collect(Collectors.joining(" | "));
    }

}
