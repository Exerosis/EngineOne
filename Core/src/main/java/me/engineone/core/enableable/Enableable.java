package me.engineone.core.enableable;

public interface Enableable {
    Enableable enable();

    Enableable disable();

    boolean isEnabled();
}
