package me.engineone.engine.components.disablers;

import me.engineone.core.component.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.function.Predicate;

public class MobSpawningDisabledComponent extends Component {

    private final Predicate<World> worlds;

    public MobSpawningDisabledComponent() {
        this(worlds -> true);
    }

    public MobSpawningDisabledComponent(Predicate<World> worlds) {
        this.worlds = worlds;
    }

    public void onEnable() {
        Bukkit.getWorlds().forEach(world -> {
            if (worlds.test(world)) world.setGameRuleValue("doMobSpawning", "false");
        });
    }

    public void onDisable() {
        Bukkit.getWorlds().forEach(world -> {
            if (worlds.test(world)) world.setGameRuleValue("doMobSpawning", "true");
        });
    }

}