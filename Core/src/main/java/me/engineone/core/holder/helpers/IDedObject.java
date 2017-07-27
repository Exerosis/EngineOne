package me.engineone.core.holder.helpers;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by BinaryBench on 4/23/2017.
 */
public abstract class IDedObject<T> {

    private T wrapped;
    private Object id;

    public IDedObject(Object id, T wrapped) {
        this.wrapped = wrapped;
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IDedObject))
            return false;

        IDedObject other = (IDedObject) obj;

        return wrapped.equals(other.wrapped) && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode() & wrapped.hashCode();
    }

    public T getWrapped() {
        return wrapped;
    }

    public Object getId() {
        return id;
    }

    protected abstract T getSelf();
}