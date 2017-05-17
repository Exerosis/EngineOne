package me.engineone.engine.alpha;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class Builder<Product> implements Buildable {
    private List<Consumer<Product>> listeners = new ArrayList<>();

    public Builder<Product> onBuild(Consumer<Product> listener) {
        listeners.add(listener);
        return this;
    }

    public List<Consumer<Product>> getBuildListeners() {
        return listeners;
    }

    protected abstract Product getProduct();

    @Override
    public void build() {
        listeners.forEach(listener -> listener.accept(getProduct()));
    }
}
