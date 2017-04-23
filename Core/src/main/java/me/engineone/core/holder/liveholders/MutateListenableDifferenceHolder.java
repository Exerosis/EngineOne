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
public class MutateListenableDifferenceHolder<T> implements MutateListenableHolder<T> {

    private List<Consumer<T>> addListeners;
    private List<Consumer<T>> removeListeners;


    private MutateListenableHolder<T> primary;
    private Holder<T> secondary;

    public MutateListenableDifferenceHolder(MutateListenableHolder<T> primary, Holder<T> secondary) {
        this.primary = primary;
        this.secondary = secondary;

        if (getSecondary() instanceof MutateListenableHolder) {
            addListeners = new DifferenceList(getPrimary().getAddListeners(), ((MutateListenableHolder<T>) getSecondary()).getRemoveListeners());
            removeListeners = new DifferenceList(getPrimary().getRemoveListeners(), ((MutateListenableHolder<T>) getSecondary()).getAddListeners());
        } else {
            addListeners = new DifferenceList(getPrimary().getAddListeners(), null);
            removeListeners = new DifferenceList(getPrimary().getRemoveListeners(), null);
        }

    }

    @Override
    public List<Consumer<T>> getAddListeners() {
        return addListeners;
    }

    @Override
    public List<Consumer<T>> getRemoveListeners() {
        return removeListeners;
    }

    public MutateListenableHolder<T> getPrimary() {
        return primary;
    }

    public Holder<T> getSecondary() {
        return secondary;
    }

    @Override
    public boolean test(T element) {
        return !getPrimary().test(element) && getPrimary().test(element);
    }

    @Override
    public int size() {
        int size = getPrimary().size();
        for (T t : getPrimary())
            if (getSecondary().test(t))
                size--;
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return Iterators.difference(getPrimary().iterator(), getSecondary());
    }

    class DifferenceList extends MutateListenableList<Consumer<T>> {
        List<Consumer<T>> primary;
        List<Consumer<T>> secondary;

        public DifferenceList(List<Consumer<T>> primary, List<Consumer<T>> secondary) {
            this.primary = primary;
            this.secondary = secondary;
        }

        @Override
        public boolean add(Consumer<T> tConsumer) {
            if (secondary != null) {
                secondary.add(new WrappedFilterConsumer<>(
                        MutateListenableDifferenceHolder.this,
                        tConsumer,
                        element-> getPrimary().test(element)));
            }

            return primary.add(new WrappedFilterConsumer<>(
                    MutateListenableDifferenceHolder.this,
                    tConsumer,
                    element -> !getSecondary().test(element)));
        }
        @Override
        public boolean remove(Object o) {

            if (!(o instanceof Consumer))
                return false;

            Consumer consumer = new WrappedFilterConsumer(MutateListenableDifferenceHolder.this, (Consumer) o, null);
            if (secondary != null)
                secondary.remove(consumer);
            return primary.remove(consumer);
        }
    }
}
