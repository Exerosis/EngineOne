package me.engineone.core.component;

import me.engineone.core.holder.CollectionHolder;
import me.engineone.core.listenable.BasicPriorityEventListenable;
import me.engineone.core.listenable.PriorityEventListenable;

import java.util.Collection;
import java.util.HashSet;

public class CollectionHolderComponent<T> extends Component implements CollectionHolder<T> {

    private final PriorityEventListenable<T> removeListenable = new BasicPriorityEventListenable<>();
    private final PriorityEventListenable<T> addListenable = new BasicPriorityEventListenable<>();
    private final Collection<T> contents;

    public CollectionHolderComponent() {
        this(new HashSet<>());
    }

    public CollectionHolderComponent(Collection<T> contents) {
        this.contents = contents;
    }

    @Override
    public Collection<T> getContents() {
        return contents;
    }

    @Override
    public PriorityEventListenable<T> getAddListenable() {
        return addListenable;
    }

    @Override
    public PriorityEventListenable<T> getRemoveListenable() {
        return removeListenable;
    }
}