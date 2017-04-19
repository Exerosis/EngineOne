package me.engineone.engine.components.client;

import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
public enum ClientRank {

    OWNER("INSIGHTS", ChatColor.BLUE),
    DEV("DEV", ChatColor.DARK_RED),
    ADMIN("ADMIN", ChatColor.RED),
    MOD("MOD", ChatColor.DARK_GREEN),
    HELPER("HELPER", ChatColor.YELLOW),
    PRO("PRO", ChatColor.GOLD),
    MVP("MVP", ChatColor.AQUA),
    VIP("VIP", ChatColor.GREEN),
    DEFAULT("DEFAULT", ChatColor.GRAY);

    private final String displayName;
    private final ChatColor displayColor;

    ClientRank(String displayName, ChatColor displayColor) {
        this.displayName = displayName;
        this.displayColor = displayColor;
    }

    public boolean has(ClientRank clientRank) {
        return compareTo(clientRank) <= 0;
    }

    public static ClientRank fromString(String value) {
        for (ClientRank clientRank : ClientRank.values()) {
            if (clientRank.toString().equalsIgnoreCase(value)) {
                return clientRank;
            }
        }
        return null;
    }

}
