package me.engineone.core.utilites;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public final class Swi {
    private Swi() {
        String test = "";
        Swi.tch(test, ca -> {
            ca.se(String.class, () -> {

            });
            ca.se(ArrayList.class, () -> {

            });
            ca.se(List.class, () -> {

            });
            ca.se(Integer.class, () -> {

            });
        }, (Object object, Class<?> clazz) -> clazz.isInstance(object));
    }

    public static <T> void tch(T input, Consumer<Ca<T>> closure) {
        tch(input, closure, Object::equals);
    }

    public static <A, B> void tch(A input, Consumer<Ca<B>> closure, BiPredicate<A, B> evaluator) {

    }


    public static class Ca<A> {
        public void se(A value, Runnable se) {

        }

    }

}