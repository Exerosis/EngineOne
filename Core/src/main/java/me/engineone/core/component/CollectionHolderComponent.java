package me.engineone.core.component;

import me.engineone.core.holder.CollectionHolder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class CollectionHolderComponent<T> extends Component implements CollectionHolder<T> {
    private final Set<Consumer<T>> removeListeners = new HashSet<>();
    private final Set<Consumer<T>> addListeners = new HashSet<>();
    private final Collection<T> contents;

    public CollectionHolderComponent() {
        this(new HashSet<>());
    }

    public CollectionHolderComponent(Collection<T> contents) {
        this.contents = contents;
    }

    @Override
    public Set<Consumer<T>> getRemoveListeners() {
        return removeListeners;
    }

    @Override
    public Set<Consumer<T>> getAddListeners() {
        return addListeners;
    }

    @Override
    public Collection<T> getContents() {
        return contents;
    }
}