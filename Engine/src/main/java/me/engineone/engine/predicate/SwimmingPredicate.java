package me.engineone.engine.predicate;

import org.bukkit.entity.Player;

import java.util.function.Predicate;

public class SwimmingPredicate implements Predicate<Player> {

    @Override
    public boolean test(Player player) {
        return player.getLocation().getBlock().isLiquid();
    }

}
