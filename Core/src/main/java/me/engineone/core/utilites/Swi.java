package me.engineone.core.utilites;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public final class Swi {
    private Swi() {
        Swi.tch("test", ca -> {
            ca.se("test", () -> {

            });
            ca.se("test2", () -> {

            });
            ca.se("test3", () -> {

            });
            ca.se("test4", () -> {

            });
        }, String::contains);
    }

    public static <T> void tch(T input, Consumer<Ca<T>> closure) {
        tch(input, closure, Object::equals);
    }

    public static <A, B> void tch(A input, Consumer<Ca<B>> closure, BiPredicate<A, B> evaluator) {
        Ca<B> cases = new Ca<>();
        closure.accept(cases);
        if (evaluator != null)
            cases.cases.forEach((value, se) -> {
                if (evaluator.test(input, value))
                    se.run();
            });
        else
            cases.cases.forEach((value, se) -> {
                if (input.equals(value))
                    se.run();
            });
    }

    public static class Ca<A> {
        protected final Map<A, Runnable> cases = new HashMap<>();

        public void se(A value, Runnable se) {
            cases.put(value, se);
        }
    }
}