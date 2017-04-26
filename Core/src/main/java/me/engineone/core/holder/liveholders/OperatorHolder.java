package me.engineone.core.holder.liveholders;

import com.google.common.collect.Iterators;
import me.engineone.core.holder.Holder;

import java.util.Iterator;
import java.util.function.BiPredicate;

/**
 * Created by BinaryBench on 4/25/2017.
 */
public class OperatorHolder<T> implements Holder<T> {

    private Holder<T> primary;
    private Holder<T> secondary;
    private BiPredicate<Boolean, Boolean> operator;


    public OperatorHolder(Holder<T> primary, Holder<T> secondary, BiPredicate<Boolean, Boolean> operator) {
        this.primary = primary;
        this.secondary = secondary;
        this.operator = operator;
    }

    public Holder<T> getPrimary() {
        return primary;
    }

    public Holder<T> getSecondary() {
        return secondary;
    }

    @Override
    public boolean test(T element) {
        return operator.test(getPrimary().test(element), getSecondary().test(element));
    }

    @Override
    public int size() {
        return Iterators.size(iterator());
    }

    @Override
    public Iterator<T> iterator() {

        Iterator<T> iterator = new Iterator<T>() {
            private Iterator<T> noDups = Iterators.concat(getPrimary().iterator(), Iterators.filter(getSecondary().iterator(), input -> !getPrimary().test(input)));
            private T next;

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public T next() {
                T next = this.next;
                this.next = null;
                while (noDups.hasNext()) {
                    T dupNext = noDups.next();
                    if (test(dupNext)) {
                        this.next = dupNext;
                        break;
                    }
                }
                return next;
            }
        };
        iterator.next();
        return iterator;
    }

    public BiPredicate<Boolean, Boolean> getOperator() {
        return operator;
    }
}