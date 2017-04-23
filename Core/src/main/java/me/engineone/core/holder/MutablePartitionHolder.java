package me.engineone.core.holder;

import me.engineone.core.holder.helpers.WrappedFilterConsumer;
import me.engineone.core.holder.helpers.MutateListenableList;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MutablePartitionHolder<T> implements MutateListenableHolder<T>, PartitionHolder<T> {

    private List<Consumer<T>> addListeners;
    private List<Consumer<T>> removeListeners;

    private MutateListenableHolder<T> parent;
    private Predicate<T> filter;


    public MutablePartitionHolder(MutateListenableHolder<T> parent, Predicate<T> filter) {
        this.parent = parent;
        this.filter = filter;

        addListeners = new PartitionList(getParent().getAddListeners(), filter);
        removeListeners = new PartitionList(getParent().getRemoveListeners(), filter);
    }

    @Override
    public Predicate<T> getFilter() {
        return filter;
    }

    @Override
    public MutateListenableHolder<T> getParent() {
        return parent;
    }

    @Override
    public List<Consumer<T>> getAddListeners() {
        return addListeners;
    }

    @Override
    public List<Consumer<T>> getRemoveListeners() {
        return removeListeners;
    }

    class PartitionList extends MutateListenableList<Consumer<T>> {
        List<Consumer<T>> parent;
        Predicate<T> filter;

        public PartitionList(List<Consumer<T>> parent, Predicate<T> filter) {
            this.parent = parent;
            this.filter = filter;
        }

        @Override
        public boolean add(Consumer<T> tConsumer) {

            return parent.add(new WrappedFilterConsumer<>(
                    this,
                    tConsumer,
                    filter));
        }

        @Override
        public boolean remove(Object o) {

            if (!(o instanceof Consumer))
                return false;

            Consumer consumer = new WrappedFilterConsumer(this, (Consumer) o, null);
            return parent.remove(consumer);
        }
    }

}