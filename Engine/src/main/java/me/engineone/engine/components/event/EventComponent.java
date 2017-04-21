package me.engineone.engine.components.event;

import me.engineone.core.listenable.BasicEventListenable;
import me.engineone.core.listenable.EventListenable;
import me.engineone.core.listenable.Listenable;
import net.jodah.typetools.TypeResolver;
import me.engineone.engine.components.base.ListenerComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class EventComponent<T extends Event> extends ListenerComponent implements EventListenable<T> {

    private final EventListenable<T> listenable = new BasicEventListenable<>();
    private final Class<T> type;
    private final EventPriority priority;

    public EventComponent(Class<T> type, EventPriority priority) {
        this.type = type;
        this.priority = priority;
    }

    @Override
    public EventComponent<T> addListener(Consumer<T> listener) {
        getListenable().addListener(listener);
        return this;
    }
    @Override
    public EventComponent<T> removeListener(Consumer<T> listener) {
        getListenable().removeListener(listener);
        return this;
    }
    @Override
    public boolean isRegistered(Consumer<T> listener) {
        return getListenable().isRegistered(listener);
    }
    @Override
    public void accept(T t) {
        getListenable().accept(t);
    }
    public EventListenable<T> getListenable() {
        return listenable;
    }

    @Override
    public void enable() {
        Bukkit.getPluginManager().registerEvent(type, this, priority, (listener, event) -> {
            listenable.accept((T) event);
        }, Bukkit.getPluginManager().getPlugins()[0]);

        super.enable();
    }

    @Override
    public void disable() {
        unregisterListener(this);
        super.disable();
    }

    public static <T extends Event> EventComponent<T> listen(Class<T> type) {
        return listen(type, null, null);
    }

    public static <T extends Event> EventComponent<T> listen(Class<T> type, Consumer<T> listener) {
        return listen(type, null, listener);
    }

    public static <T extends Event> EventComponent<T> listen(Class<T> type, EventPriority priority) {
        return listen(type, priority, null);
    }

    public static <T extends Event> EventComponent<T> listen(Class<T> type, EventPriority priority, Consumer<T> listener) {

        if (priority == null)
            priority = EventPriority.NORMAL;

        EventComponent<T> event = new EventComponent<>(type, priority);
        if (!Event.class.isAssignableFrom(type))
            throw new IllegalArgumentException("Sorry, couldn't resolve Event through method that returns functional interfaces.\n" +
                    "Please either use listen(Consumer<T> listener, Predicate<T> filter, Class<T> type) or use direct method references or lambdas");
        if (listener != null)
            event.addListener(listener);
        return event;
    }


    public static <T extends Event> EventComponent<T> listen(Consumer<T> listener) {
        return listen((Class<T>) TypeResolver.resolveRawArgument(Consumer.class, listener.getClass()), null, listener);
    }

    public static <T extends Event> EventComponent<T> listen(Consumer<T> listener, EventPriority priority) {
        return listen((Class<T>) TypeResolver.resolveRawArgument(Consumer.class, listener.getClass()), priority, listener);
    }
}