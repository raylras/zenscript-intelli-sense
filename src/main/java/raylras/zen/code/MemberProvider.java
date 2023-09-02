package raylras.zen.code;

import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public interface MemberProvider {

    List<Symbol> getMembers();

    MemberProvider EMPTY = List::of;

    default Symbol getMember(int index) {
        return getMembers().get(index);
    }

    static MemberProvider of(List<Symbol> members) {
        return () -> members;
    }

    default MemberProvider filter(Predicate<Symbol> predicate) {
        return () -> getMembers().stream().filter(predicate).toList();
    }

    default MemberProvider limit(long maxSize) {
        return () -> getMembers().stream().limit(maxSize).toList();
    }

    default MemberProvider merge(MemberProvider others) {
        return () -> {
            List<Symbol> list = new ArrayList<>(this.getMembers());
            list.addAll(others.getMembers());
            return list;
        };
    }

    default int size() {
        return getMembers().size();
    }

    default MemberProvider withExpandMembers(CompilationEnvironment env) {
        if (this instanceof Type type) {
            return merge(() -> env.getExpandMembers(type));
        } else {
            return this;
        }
    }

}
