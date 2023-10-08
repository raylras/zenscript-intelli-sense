package raylras.zen.dap.jdi;

import io.reactivex.rxjava3.functions.Predicate;

public class ObservableUtils {

    public static  <T> Predicate<T> safeFilter(Predicate<T> predicate) {
        return (t) -> {
            try {
                return predicate.test(t);
            } catch (Throwable ignored) {
                return false;
            }
        };
    }
}
