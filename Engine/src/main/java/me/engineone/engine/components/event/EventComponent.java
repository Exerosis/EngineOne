package me.engineone.engine.components.event;

import net.jodah.typetools.TypeResolver;
import me.engineone.engine.components.base.ListenerComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class EventComponent<T extends Event> extends ListenerComponent {
    private final HashSet<Consumer<T>> listeners = new HashSet<>();
    private final Predicate<T> filter;
    private final Class<T> type;

    public EventComponent(Class<T> type) {
        this(event -> true, type);
    }

    public EventComponent(Consumer<T> listener, Class<T> type) {
        this(listener, event -> true, type);
    }

    public EventComponent(Predicate<T> filter, Class<T> type) {
        this(null, filter, type);
    }

    public EventComponent(Consumer<T> listener, Predicate<T> filter, Class<T> type) {
        this.type = type;
        this.filter = filter;
        if (listener != null)
            listeners.add(listener);
    }

    public Consumer<T> onEvent(Consumer<T> listener) {
        if (listeners.contains(listener))
            listeners.remove(listener);
        else
            listeners.add(listener);
        return listener;
    }

    @Override
    public void enable() {
        Bukkit.getPluginManager().registerEvent(type, this, EventPriority.NORMAL, (listener, event) -> {
            if (filter.test((T) event))
                try {
                    listeners.forEach(consumer -> consumer.accept((T) event));
                } catch (Exception ignored) {
                }
        }, Bukkit.getPluginManager().getPlugins()[0]);
        super.enable();
    }

    @Override
    public void disable() {
        unregisterListener(this);
        super.disable();
    }


    public static <T extends Event> EventComponent<T> listen(Class<T> type) {
        return listen(event -> true, type);
    }

    public static <T extends Event> EventComponent<T> listen(Predicate<T> filter, Class<T> type) {
        return listen(null, filter, type);
    }

    public static <T extends Event> EventComponent<T> listen(Consumer<T> listener, Class<T> type) {
        return listen(listener, event -> true, type);
    }

    public static <T extends Event> EventComponent<T> listen(Consumer<T> listener, Predicate<T> filter, Class<T> type) {
        if (!Event.class.isAssignableFrom(type))
            throw new IllegalArgumentException("Sorry, couldn't resolve Event through method that returns functional interfaces.\n" +
                    "Please either use listen(Consumer<T> listener, Predicate<T> filter, Class<T> type) or use direct method references or lambdas");
        return new EventComponent<T>(listener, filter, type);
    }


    public static <T extends Event> EventComponent<T> listen() {
        return listen(null, event -> true);
    }

    public static <T extends Event> EventComponent<T> listen(Predicate<T> filter) {
        return listen(null, filter);
    }

    public static <T extends Event> EventComponent<T> listen(Consumer<T> listener) {
        return listen(listener, event -> true);
    }

    public static <T extends Event> EventComponent<T> listen(Consumer<T> listener, Predicate<T> filter) {
        return listen(listener, filter, (Class<T>) TypeResolver.resolveRawArgument(Consumer.class, listener.getClass()));
    }
}