package me.engineone.engine.predicate;

import org.bukkit.entity.Player;

import java.util.function.Predicate;

public class VoidLevelPredicate implements Predicate<Player> {

    private final int voidLevel;

    public VoidLevelPredicate(int voidLevel) {
        this.voidLevel = voidLevel;
    }

    @Override
    public boolean test(Player player) {
        return player.getLocation().getY() <= voidLevel;
    }

}
