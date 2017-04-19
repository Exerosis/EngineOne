package me.engineone.core;

import java.util.Set;

@SuppressWarnings("unchecked")
public interface Parent<A> {
    Set<A> getChildren();

    default <B extends A> B addChild(B child) {
        getChildren().add(child);
        return child;
    }

    default <B extends A> B[] addChild(B... children) {
        for (B child : children)
            addChild(child);

        return children;
    }

    default boolean removeChild(A child) {
        return getChildren().remove(child);
    }

    default boolean removeChild(A... children) {
        boolean result = false;
        for (A child : children)
            result = result || removeChild(child);
        return result;
    }
}