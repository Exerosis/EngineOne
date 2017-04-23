package me.engineone.core.holder.liveholders;

import me.engineone.core.holder.helpers.WrappedFilterConsumer;
import me.engineone.core.holder.helpers.MutateListenableList;
import me.engineone.core.holder.*;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/22/2017.
 */
public class MutateListenableUnionHolder<T> implements MutateListenableHolder<T> {


    private List<Consumer<T>> addListeners;
    private List<Consumer<T>> removeListeners;

    private MutateListenableHolder<T> primary;
    private Holder<T> secondary;

    public MutateListenableUnionHolder(MutateListenableHolder<T> primary, Holder<T> secondary) {
        this.primary = primary;
        this.secondary = secondary;

        if (getSecondary() instanceof MutateListenableHolder) {
            addListeners = new UnionList(getPrimary().getAddListeners(), ((MutateListenableHolder<T>) getSecondary()).getAddListeners());
            removeListeners = new UnionList(getPrimary().getRemoveListeners(), ((MutateListenableHolder<T>) getSecondary()).getRemoveListeners());
        } else {
            addListeners = new UnionList(getPrimary().getAddListeners(), null);
            removeListeners = new UnionList(getPrimary().getRemoveListeners(), null);
        }
    }

    public MutateListenableHolder<T> getPrimary() {
        return primary;
    }

    public Holder<T> getSecondary() {
        return secondary;
    }

    @Override
    public boolean test(T element) {
        return getPrimary().test(element) || getSecondary().test(element);
    }

    @Override
    public int size() {
        int size = getSecondary().size();
        for (T t : getPrimary())
            if (!getSecondary().test(t))
                size++;
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return Iterators.union(getPrimary().iterator(), getSecondary().iterator());
    }

    @Override
    public List<Consumer<T>> getAddListeners() {
        return addListeners;
    }

    @Override
    public List<Consumer<T>> getRemoveListeners() {
        return removeListeners;
    }

    class UnionList extends MutateListenableList<Consumer<T>> {
        List<Consumer<T>> primary;
        List<Consumer<T>> secondary;

        public UnionList(List<Consumer<T>> primary, List<Consumer<T>> secondary) {
            this.primary = primary;
            this.secondary = secondary;
        }

        @Override
        public boolean add(Consumer<T> tConsumer) {
            if (secondary != null) {
                secondary.add(new WrappedFilterConsumer<>(
                        MutateListenableUnionHolder.this,
                        tConsumer,
                        element-> !getPrimary().test(element)));
            }

            return primary.add(new WrappedFilterConsumer<>(
                    MutateListenableUnionHolder.this,
                    tConsumer,
                    element -> !getSecondary().test(element)));
        }

        @Override
        public boolean remove(Object o) {

            if (!(o instanceof Consumer))
                return false;

            Consumer consumer = new WrappedFilterConsumer(MutateListenableUnionHolder.this, (Consumer) o, null);
            if (secondary != null)
                secondary.remove(consumer);
            return primary.remove(consumer);
        }
    }


}