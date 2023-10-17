package raylras.zen.model.type;

import java.util.Comparator;

public enum SubtypeResult {

    MISMATCH,
    CASTER,
    INHERIT,
    SELF;

    public int getPriority() {
        return ordinal();
    }

    public boolean matched() {
        return this.getPriority() > MISMATCH.getPriority();
    }

    public static SubtypeResult lower(SubtypeResult a, SubtypeResult b) {
        return (a.getPriority() <= b.getPriority()) ? a : b;
    }

    public static final Comparator<SubtypeResult> PRIORITY_COMPARATOR = Comparator.comparingInt(SubtypeResult::getPriority);

}
