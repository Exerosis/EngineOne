package me.engineone.engine.utilites;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public final class MathUtil {
    private MathUtil() {
    }

    public static double distance(Player one, Player two) {
        return distance(one.getLocation(), two.getLocation());
    }

    public static double distance(Location one, Location two) {
        return one.distance(two);
    }

    public static double distance(Vector one, Vector two) {
        return one.distance(two);
    }
}
