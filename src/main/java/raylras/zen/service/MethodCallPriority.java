package raylras.zen.service;

public enum MethodCallPriority {
    INVALID(-1),
    LOW(0),
    MEDIUM(1),
    HIGH(2);
    private final int priority;

    MethodCallPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public static MethodCallPriority max(MethodCallPriority a, MethodCallPriority b) {
        return a.priority > b.priority ? a : b;
    }

    public static MethodCallPriority min(MethodCallPriority a, MethodCallPriority b) {
        return a.priority < b.priority ? a : b;
    }

}
