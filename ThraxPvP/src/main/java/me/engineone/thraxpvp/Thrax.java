package me.engineone.thraxpvp;

import me.engineone.engine.Arena;
import me.engineone.engine.components.world.WorldComponent;
import me.engineone.engine.holder.OnlinePlayerHolder;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static me.engineone.engine.components.event.EventComponent.listen;

public class Thrax extends JavaPlugin {
    private final WorldComponent worldComponent;

    public Thrax() {
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
        Arena arena = new ThraxBaseGamemode(worldComponent, new OnlinePlayerHolder());

        listen(BlockDamageEvent.class, event -> {}).enable();
        //Hooks
//        worldComponent.onLoad(arena::enable);
//        worldComponent.onUnload(arena::disable, worldComponent::enable);
//        arena.onComplete(worldComponent::disable);
//
//        worldComponent.enable();
    }

    @Override
    public void onDisable() {
        //TODO might make it impossible to disable the plugin :(
        worldComponent.disable();
    }
}