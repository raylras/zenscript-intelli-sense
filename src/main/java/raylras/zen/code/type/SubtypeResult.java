package raylras.zen.code.type;

import java.util.Comparator;

public enum SubtypeResult {

    MISMATCH(Integer.MAX_VALUE),
    CASTER(3),
    INHERIT(2),
    SELF(1);

    public final int priority;

    SubtypeResult(int priority) {
        this.priority = priority;
    }

    public boolean matched() {
        return this.priority < MISMATCH.priority;
    }

    public static SubtypeResult higher(SubtypeResult a, SubtypeResult b) {
        return (a.priority >= b.priority) ? a : b;
    }

    public static final Comparator<SubtypeResult> PRIORITY_COMPARATOR = Comparator.comparingInt(o -> o.priority);

}
