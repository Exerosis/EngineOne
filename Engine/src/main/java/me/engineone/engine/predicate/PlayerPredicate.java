package me.engineone.engine.predicate;

import org.bukkit.entity.Player;

import java.util.function.Predicate;

public interface PlayerPredicate extends Predicate<Player> {
    default PlayerPredicate withHealth(Predicate<Double> health) {
        return player -> test(player) && health.test(player.getHealth());
    }

    default PlayerPredicate withHunger(Predicate<Integer> hunger) {
        return player -> test(player) && hunger.test(player.getFoodLevel());
    }

    default PlayerPredicate withElevation(Predicate<Double> elevation) {
        return player -> test(player) && elevation.test(player.getLocation().getY());
    }
}