package me.engineone.engine.utilites;

import org.bukkit.entity.Player;

import java.util.Comparator;

import static me.engineone.engine.utilites.RandomUtil.randomBoolean;

public final class TieBreakers {
    public static final Comparator<Player> HIGHEST_PLAYER = Comparator.comparingDouble(p -> p.getLocation().getY());

    private TieBreakers() {
    }

    public static <T> Comparator<T> random() {
        return Comparator.comparing(object -> randomBoolean());
    }


}
