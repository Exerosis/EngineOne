package me.engineone.engine.utilites;

import java.util.Optional;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class Switch {
    public static void newSwitch(Object object, Consumer<Object>... cases) {
        for (Consumer<Object> caze : cases)
            caze.accept(object);
    }

    public static <T> Consumer<T> newCase(Consumer<T> caze, Class<T> clazz) {
        return object -> Optional.of(object).filter(clazz::isInstance).map(clazz::cast).ifPresent(caze);
    }
}
