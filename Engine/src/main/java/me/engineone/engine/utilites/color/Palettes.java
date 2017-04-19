package me.engineone.engine.utilites.color;

import me.engineone.engine.utilites.Colored;
import org.bukkit.ChatColor;

public enum Palettes {
    BLUE, GREEN, PURPLE, YELLOW, RED, AQUA;


    public Palette accent(ChatColor accent, boolean dark) {
        switch (this) {
            case BLUE:
                return new Palette(ChatColor.DARK_BLUE, ChatColor.BLUE, accent, dark);
            case GREEN:
                return new Palette(ChatColor.DARK_GREEN, ChatColor.GREEN, accent, dark);
            case PURPLE:
                return new Palette(ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE, accent, dark);
            case YELLOW:
                return new Palette(ChatColor.GOLD, ChatColor.YELLOW, accent, dark);
            case RED:
                return new Palette(ChatColor.DARK_RED, ChatColor.RED, accent, dark);
            case AQUA:
                return new Palette(ChatColor.DARK_AQUA, ChatColor.AQUA, accent, dark);
            default:
                return new Palette(ChatColor.DARK_AQUA, ChatColor.AQUA, accent, dark);
        }
    }


    public static String primary(Colored palette) {
        return palette.getColor().getPrimary().toString();
    }

    public static String ta(Colored palette) {
        return palette.getColor().getAccent().toString();
    }

    public static String secondary(Colored palette) {
        return palette.getColor().getSecondary().toString();
    }

    public static String darkShade(Colored palette) {
        return palette.getColor().getDarkShader().toString();
    }

    public static String lightShade(Colored palette) {
        return palette.getColor().getLightShader().toString();
    }


    public static String primary(Palette palette) {
        return palette.getPrimary().toString();
    }

    public static String accent(Palette palette) {
        return palette.getAccent().toString();
    }

    public static String secondary(Palette palette) {
        return palette.getSecondary().toString();
    }

    public static String darkShade(Palette palette) {
        return palette.getDarkShader().toString();
    }

    public static String lightShade(Palette palette) {
        return palette.getLightShader().toString();
    }

    public static String bold() {
        return ChatColor.BOLD.toString();
    }

    public static String reset() {
        return ChatColor.RESET.toString();
    }
}
