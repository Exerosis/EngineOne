package me.engineone.core.component;

import me.engineone.core.holder.CollectionHolder;
import me.engineone.core.listenable.BasicPriorityListenable;
import me.engineone.core.listenable.PriorityListenable;

import java.util.Collection;
import java.util.HashSet;

public class CollectionHolderComponent<T> extends Component implements CollectionHolder<T> {

    private final PriorityListenable<T> removeListenable = new BasicPriorityListenable<>();
    private final PriorityListenable<T> addListenable = new BasicPriorityListenable<>();
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
    public PriorityListenable<T> getAddListenable() {
        return addListenable;
    }

    @Override
    public PriorityListenable<T> getRemoveListenable() {
        return removeListenable;
    }
}