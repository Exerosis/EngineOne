package me.engineone.engine.holder;

import me.engineone.core.holder.CollectionHolder;
import me.engineone.engine.predicate.PlayerPredicate;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class PlayerHolder implements CollectionHolder<Player>, PlayerPredicate {
    private final Set<Consumer<Player>> removeListeners = new HashSet<>();
    private final Set<Consumer<Player>> addListeners = new HashSet<>();
    private final Set<Player> contents = new HashSet<>();

    @Override
    public Set<Consumer<Player>> getRemoveListeners() {
        return removeListeners;
    }

    @Override
    public Set<Consumer<Player>> getAddListeners() {
        return addListeners;
    }

    @Override
    public Collection<Player> getContents() {
        return contents;
    }
}