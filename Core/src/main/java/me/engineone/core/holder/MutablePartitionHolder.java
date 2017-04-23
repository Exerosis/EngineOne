package me.engineone.core.holder;

import me.engineone.core.holder.helpers.IDedFilterConsumer;
import me.engineone.core.holder.helpers.MutateListenableList;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MutablePartitionHolder<T> implements MutateHolder<T>, PartitionHolder<T> {

    private List<Consumer<T>> addListeners;
    private List<Consumer<T>> removeListeners;

    private MutateHolder<T> parent;
    private Predicate<T> filter;


    public MutablePartitionHolder(MutateHolder<T> parent, Predicate<T> filter) {
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
    public MutateHolder<T> getParent() {
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

        private IDedFilterConsumer<T> getIDedFilter(Consumer<T> tConsumer) {
            return new IDedFilterConsumer<>(
                    this,
                    tConsumer,
                    filter);
        }

        private int getActualIndex(Consumer<T> tConsumer) {
            return parent.indexOf(new IDedFilterConsumer<T>(this, tConsumer));
        }

        @Override
        protected void adding(Consumer<T> element) {
            parent.add(getIDedFilter(element));
        }

        @Override
        protected void adding(int index, Consumer<T> element) {
            parent.add(getActualIndex(get(index)), getIDedFilter(element));
        }

        @Override
        protected void removing(int index) {
            parent.remove(getActualIndex(get(index)));
        }

        @Override
        protected void removing(Consumer<T> element) {
            parent.remove(getIDedFilter(element));
        }

    }

}