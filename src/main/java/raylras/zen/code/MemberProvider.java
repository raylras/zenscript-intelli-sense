package raylras.zen.code;

import raylras.zen.code.symbol.Symbol;

import java.util.List;
import java.util.function.Predicate;

public interface MemberProvider {

    List<Symbol> getMembers();

    MemberProvider EMPTY = List::of;

    default Symbol getMember(int index) {
        return getMembers().get(index);
    }

    default MemberProvider of(List<Symbol> members) {
        return () -> members;
    }

    default MemberProvider filter(Predicate<Symbol> predicate) {
        return () -> getMembers().stream().filter(predicate).toList();
    }

    default MemberProvider limit(long maxSize) {
        return () -> getMembers().stream().limit(maxSize).toList();
    }

    default int size() {
        return getMembers().size();
    }

}
