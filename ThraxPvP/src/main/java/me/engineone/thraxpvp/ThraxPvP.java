package me.engineone.thraxpvp;

import org.bukkit.plugin.java.JavaPlugin;

public class ThraxPvP extends JavaPlugin {
    private final ThraxPvPArena arena;

    public ThraxPvP() {
        arena = new ThraxPvPArena();
    }

    @Override
    public void onEnable() {
        arena.enable();
        arena.onComplete(() -> {
            arena.disable();
            arena.enable();
        });
    }

    @Override
    public void onDisable() {
        arena.disable();
    }
}