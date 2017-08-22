package me.engineone.thraxpvp;

import me.engineone.engine.Arena;
import me.engineone.engine.components.world.WorldComponent;
import me.engineone.engine.holder.OnlinePlayerHolder;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ThraxPvP extends JavaPlugin {
    private final WorldComponent worldComponent;

    public ThraxPvP() {
        //Components
        System.out.println("Created");
        URL url = null;
        try {
            url = new URL("https://www.dropbox.com/s/0lb4b4wklcu6hs6/Clay%20Plaza.zip?dl=1");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        worldComponent = new WorldComponent(new File("Worlds"), "ArenaWorld", url);
    }

    @Override
    public void onEnable() {
        Arena arena = new ThraxPvPArena(worldComponent, new OnlinePlayerHolder());

        //Hooks
        worldComponent.onLoad(arena::enable);
        worldComponent.onUnload(arena::disable, worldComponent::enable);
        arena.onComplete(worldComponent::disable);

        worldComponent.enable();
    }

    @Override
    public void onDisable() {
        //TODO might make it impossible to disable the plugin :(
        worldComponent.disable();
    }
}