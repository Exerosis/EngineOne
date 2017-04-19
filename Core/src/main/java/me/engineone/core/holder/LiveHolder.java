package me.engineone.core.holder;

public interface LiveHolder<T> extends Holder<T> {
    Holder<T> getPrimary();

    Holder<T> getSecondary();
}
