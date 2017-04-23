package me.engineone.core.holder.liveholders;

import me.engineone.core.holder.helpers.IDedFilterConsumer;
import me.engineone.core.holder.helpers.MutateListenableList;
import me.engineone.core.holder.*;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/22/2017.
 */
public class MutateUnionHolder<T> implements MutateHolder<T> {


    private List<Consumer<T>> addListeners;
    private List<Consumer<T>> removeListeners;

    private MutateHolder<T> primary;
    private Holder<T> secondary;

    public MutateUnionHolder(MutateHolder<T> primary, Holder<T> secondary) {
        this.primary = primary;
        this.secondary = secondary;

        if (getSecondary() instanceof MutateHolder) {
            addListeners = new UnionList(getPrimary().getAddListeners(), ((MutateHolder<T>) getSecondary()).getAddListeners());
            removeListeners = new UnionList(getPrimary().getRemoveListeners(), ((MutateHolder<T>) getSecondary()).getRemoveListeners());
        } else {
            addListeners = new UnionList(getPrimary().getAddListeners(), null);
            removeListeners = new UnionList(getPrimary().getRemoveListeners(), null);
        }
    }

    public MutateHolder<T> getPrimary() {
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

        private IDedFilterConsumer<T> getSecondaryIDedFilter(Consumer<T> tConsumer) {
            return new IDedFilterConsumer<>(
                    this,
                    tConsumer,
                    element-> !getPrimary().test(element));
        }

        public IDedFilterConsumer<T> getPrimaryIDedFilter(Consumer<T> tConsumer) {
            return new IDedFilterConsumer<>(
                    this,
                    tConsumer,
                    element -> !getSecondary().test(element));
        }

        private int getActualPrimaryIndex(Consumer<T> tConsumer) {
            return primary.indexOf(getPrimaryIDedFilter(tConsumer));
        }
        private int getActualSecondaryIndex(Consumer<T> tConsumer) {
            return secondary.indexOf(getSecondaryIDedFilter(tConsumer));
        }


        @Override
        protected void adding(Consumer<T> element) {
            primary.add(getPrimaryIDedFilter(element));
            if (secondary != null)
                secondary.add(getSecondaryIDedFilter(element));
        }

        @Override
        protected void adding(int index, Consumer<T> element) {
            primary.add(getActualPrimaryIndex(get(index)), getPrimaryIDedFilter(element));
            if (secondary != null)
                secondary.add(getActualSecondaryIndex(get(index)), getSecondaryIDedFilter(element));
        }

        @Override
        protected void removing(int index) {
            primary.remove(getActualPrimaryIndex(get(index)));
            if (secondary != null)
                secondary.remove(getActualSecondaryIndex(get(index)));
        }

        @Override
        protected void removing(Consumer<T> element) {
            primary.remove(getPrimaryIDedFilter(element));
            if (secondary != null)
                secondary.remove(getSecondaryIDedFilter(element));
        }
    }

}