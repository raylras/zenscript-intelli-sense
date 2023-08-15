package raylras.zen.code.type;

import java.util.Comparator;

public enum SubtypeResult {

    MISMATCH(0),
    SELF(1),
    INHERIT(2),
    CASTER(3);

    public final int priority;

    SubtypeResult(int priority) {
        this.priority = priority;
    }

    public boolean matched() {
        return this.priority > MISMATCH.priority;
    }

    public SubtypeResult and(SubtypeResult that) {
        return (this.priority <= that.priority) ? this : that;
    }

    public static final Comparator<SubtypeResult> PRIORITY_COMPARATOR = Comparator.comparingInt(o -> o.priority);

}
