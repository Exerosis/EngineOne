package me.engineone.core.holder.liveholders;

import me.engineone.core.holder.Holder;
import me.engineone.core.holder.MutateHolder;
import me.engineone.core.holder.helpers.IDedFilterConsumer;
import me.engineone.core.holder.helpers.MutateListenableList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/25/2017.
 */
public class MutateOperatorHolder<T> extends OperatorHolder<T> implements MutateHolder<T> {

    private List<Consumer<T>> addListeners;
    private List<Consumer<T>> removeListeners;

    private List<Consumer<T>> addedListeners;
    private List<Consumer<T>> removedListeners;

    public MutateOperatorHolder(MutateHolder<T> primary, Holder<T> secondary, BiPredicate<Boolean, Boolean> operator) {
        super(primary, secondary, operator);
        this.addListeners = new OperatorList(true, false);
        this.removeListeners = new OperatorList(false, false);

        this.addedListeners = new OperatorList(true, true);
        this.removedListeners = new OperatorList(false, true);
    }

    @Override
    public MutateHolder<T> getPrimary() {
        return (MutateHolder<T>) super.getPrimary();
    }
    @Override
    public List<Consumer<T>> getAddListeners() {
        return addListeners;
    }
    @Override
    public List<Consumer<T>> getRemoveListeners() {
        return removeListeners;
    }
    @Override
    public List<Consumer<T>> getAddedListeners() {
        return addedListeners;
    }
    @Override
    public List<Consumer<T>> getRemovedListeners() {
        return removedListeners;
    }

    class OperatorList extends MutateListenableList<Consumer<T>> {
        // Listeners, primary, add
        private List<OperatorListData> listenersList;
        private boolean add;
        private boolean post;

        public OperatorList(boolean add, boolean post) {
            this.add = add;
            this.post = post;
            listenersList = new ArrayList<>();

            if (!post) {
                listenersList.add(new OperatorListData(getPrimary().getAddListeners(), true, true));
                listenersList.add(new OperatorListData(getPrimary().getRemoveListeners(), true, false));

                if (getSecondary() instanceof MutateHolder) {
                    listenersList.add(new OperatorListData(((MutateHolder<T>) getSecondary()).getAddListeners(), false, true));
                    listenersList.add(new OperatorListData(((MutateHolder<T>) getSecondary()).getRemoveListeners(), false, false));
                }
            } else {
                listenersList.add(new OperatorListData(getPrimary().getAddedListeners(), true, true));
                listenersList.add(new OperatorListData(getPrimary().getRemovedListeners(), true, false));

                if (getSecondary() instanceof MutateHolder) {
                    listenersList.add(new OperatorListData(((MutateHolder<T>) getSecondary()).getAddedListeners(), false, true));
                    listenersList.add(new OperatorListData(((MutateHolder<T>) getSecondary()).getRemovedListeners(), false, false));
                }
            }

        }

        public IDedFilterConsumer<T> getIDedFilter(Consumer<T> tConsumer) {
            return new IDedFilterConsumer<>(
                    this,
                    tConsumer);
        }

        public IDedFilterConsumer<T> getIDedFilter(Consumer<T> tConsumer, Boolean inPrimary, Boolean inSecondary) {
            return new IDedFilterConsumer<>(
                    this,
                    tConsumer,
                    element -> {

                        if (!post) {

                            boolean primary = inPrimary != null ? inPrimary : getPrimary().test(element);
                            boolean secondary = inSecondary != null ? inSecondary : getSecondary().test(element);

                            boolean testBefore = getOperator().test(getPrimary().test(element), getSecondary().test(element));
                            boolean testAfter = getOperator().test(primary, secondary);

                            return add == testAfter && testBefore != testAfter;
                        } else {

                            boolean primary = inPrimary != null ? !inPrimary : getPrimary().test(element);
                            boolean secondary = inSecondary != null ? !inSecondary : getSecondary().test(element);

                            boolean testBefore = getOperator().test(primary, secondary);
                            boolean testAfter = getOperator().test(getPrimary().test(element), getSecondary().test(element));

                            return add == testAfter && testBefore != testAfter;
                        }
                    });
        }

        private int getActualIndex(List<Consumer<T>> listeners, Consumer<T> tConsumer) {
            return listeners.indexOf(getIDedFilter(tConsumer));
        }

        @Override
        protected void adding(Consumer<T> element) {
            for (OperatorListData data : listenersList) {
                data.getListeners().add(getIDedFilter(element, data.isPrimary() ? data.isAdd() : null, !data.isPrimary() ? data.isAdd() : null));
            }
        }

        @Override
        protected void adding(int index, Consumer<T> element) {
            for (OperatorListData data : listenersList) {
                data.getListeners().add(getActualIndex(data.getListeners(), get(index)), getIDedFilter(element, data.isPrimary() ? data.isAdd() : null, !data.isPrimary() ? data.isAdd() : null));
            }
        }

        @Override
        protected void removing(int index) {
            for (OperatorListData data : listenersList)
                data.getListeners().remove(getActualIndex(data.getListeners(), get(index)));
        }

        @Override
        protected void removing(Consumer<T> element) {
            for (OperatorListData data : listenersList)
                data.getListeners().remove(getIDedFilter(element));
        }
    }

    private class OperatorListData {
        private List<Consumer<T>> listeners;
        private Boolean primary;
        private Boolean add;

        OperatorListData(List<Consumer<T>> listeners, Boolean isPrimary, Boolean isAdd) {
            this.listeners = listeners;
            this.primary = isPrimary;
            this.add = isAdd;
        }

        List<Consumer<T>> getListeners() {
            return listeners;
        }

        Boolean isPrimary() {
            return primary;
        }

        Boolean isAdd() {
            return add;
        }
    }
}
