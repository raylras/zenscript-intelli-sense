package raylras.zen.model.type

enum class CastingKind : Comparable<CastingKind> {
    MISMATCH,
    CASTER,
    INHERIT,
    SELF;

    inline val priority get() = ordinal

    fun matched(): Boolean {
        return this.priority > MISMATCH.priority
    }

    companion object {
        fun lower(a: CastingKind, b: CastingKind): CastingKind {
            return if (a.priority <= b.priority) a else b
        }

        fun higher(a: CastingKind, b: CastingKind): CastingKind {
            return if (a.priority >= b.priority) a else b
        }
    }
}
