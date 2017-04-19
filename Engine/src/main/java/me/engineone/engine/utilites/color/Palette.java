package me.engineone.engine.utilites.color;

import org.bukkit.ChatColor;

public class Palette {
    private final ChatColor primary;
    private final ChatColor secondary;
    private final ChatColor accent;
    private final ChatColor darkShader;
    private final ChatColor lightShader;

    public Palette(ChatColor primary, ChatColor secondary, ChatColor accent, boolean dark) {
        this.primary = primary;
        this.secondary = secondary;
        this.accent = accent;
        darkShader = dark ? ChatColor.DARK_GRAY : ChatColor.GRAY;
        lightShader = dark ? ChatColor.GRAY : ChatColor.WHITE;
    }

    public ChatColor getPrimary() {
        return primary;
    }

    public ChatColor getAccent() {
        return accent;
    }

    public ChatColor getSecondary() {
        return secondary;
    }

    public ChatColor getDarkShader() {
        return darkShader;
    }

    public ChatColor getLightShader() {
        return lightShader;
    }
}