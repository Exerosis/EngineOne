package me.engineone.engine.components.disablers;

import me.engineone.core.component.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.function.Predicate;

public class FireSpreadDisabledComponent extends Component {

    private final Predicate<World> worlds;

    public FireSpreadDisabledComponent() {
        this(worlds -> true);
    }

    public FireSpreadDisabledComponent(Predicate<World> worlds) {
        this.worlds = worlds;
    }

    public void onEnable() {
        Bukkit.getWorlds().forEach(world -> {
            if (worlds.test(world)) world.setGameRuleValue("doFireTick", "false");
        });
    }

    public void onDisable() {
        Bukkit.getWorlds().forEach(world -> {
            if (worlds.test(world)) world.setGameRuleValue("doFireTick", "true");
        });
    }

}