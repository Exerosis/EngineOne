package me.engineone.engine.components.world;

import me.engineone.core.component.ParentComponent;
import me.engineone.engine.components.event.EventComponent;
import me.engineone.engine.components.scheduler.Scheduler;
import me.engineone.engine.utilites.ArchiveUtils;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.world.WorldLoadEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.io.File.separator;

public class WorldComponent extends ParentComponent {
    private final List<Runnable> loadListeners = new ArrayList<>();
    private final List<Runnable> unloadListeners = new ArrayList<>();
    private World world;

    public WorldComponent(File worldsDirectory, String name) {
        this(worldsDirectory, name, null);
    }

    public WorldComponent(File worldsDirectory, String name, URL backupURL) {
        onEnable(() -> {
            System.out.println("Attempting to load world.");
            if (worldsDirectory.isFile())
                throw new RuntimeException("Cannot load worlds from a file, please specify a directory instead.");
            if (!worldsDirectory.exists() && !worldsDirectory.mkdirs())
                throw new RuntimeException("Cannot locate or create a worlds directory at the given location.");
            File worldDirectory = new File(worldsDirectory.getPath() + separator + name);
            if (!worldDirectory.exists()) {
                if (backupURL == null)
                    throw new RuntimeException("Cannot locate or download a world directory.");
                System.err.println("Failed to find world directory, attempting download.");
                try {
                    ArchiveUtils.downloadFile(backupURL, worldsDirectory);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to download world zip.");
                }
                if (!worldDirectory.exists())
                    throw new RuntimeException("Failed to download world zip.");
            }
            System.out.println("Copying world directory.");
            try {
                FileUtils.copyDirectory(worldDirectory, Bukkit.getWorldContainer(), false);
            } catch (IOException e) {
                throw new RuntimeException("Failed to copy world directory.");
            }

            WorldCreator worldCreator = new WorldCreator(name);
            worldCreator.type(WorldType.FLAT);
            world = Bukkit.createWorld(worldCreator);
            System.out.println("Loading world.");
        });

        onDisable(() -> {
            System.out.println("Attempting to unload world.");

            //Remove players
            for (World backupWorld : Bukkit.getWorlds())
                if (backupWorld != world) {
                    for (Player player : world.getPlayers())
                        player.teleport(backupWorld.getSpawnLocation().add(0, 4, 0));
                    return;
                }

            //Wait 1 second before unloading the world.
            Scheduler.every().second().synchronously().forNext(1).times().run(() -> {
                if (!Bukkit.unloadWorld(world, false))
                    System.err.println("Failed to unload world, memory leak warning.");
                else
                    Scheduler.every(4).seconds().forNext(1).times().run(() -> {
                        try {
                            System.out.println("Attempting to remove world folder.");
                            FileUtils.deleteDirectory(world.getWorldFolder());
                        } catch (IOException e) {
                            System.err.println("Failed to remove world folder, this is likely to cause world load failures.");
                        }
                        System.out.println("World folder removed.");
                        world = null;
                        unloadListeners.forEach(Runnable::run);
                    });
            });
        });

        addChild(EventComponent.listen(WorldLoadEvent.class, event -> loadListeners.forEach(Runnable::run)));
    }

    public WorldComponent onLoad(Runnable... listeners) {
        if (listeners.length > 1)
            getLoadListeners().addAll(Arrays.asList(listeners));
        else
            getLoadListeners().add(listeners[0]);
        return this;
    }

    public WorldComponent onUnload(Runnable... listeners) {
        if (listeners.length > 1)
            getUnloadListeners().addAll(Arrays.asList(listeners));
        else
            getUnloadListeners().add(listeners[0]);
        return this;
    }

    public WorldComponent unregisterLoad(Runnable... listeners) {
        if (listeners.length > 1)
            getLoadListeners().removeAll(Arrays.asList(listeners));
        else
            getLoadListeners().remove(listeners[0]);
        return this;
    }

    public WorldComponent unregisterUnload(Runnable... listeners) {
        if (listeners.length > 1)
            getUnloadListeners().removeAll(Arrays.asList(listeners));
        else
            getUnloadListeners().remove(listeners[0]);
        return this;
    }

    public World getWorld() {
        return world;
    }

    public List<Runnable> getLoadListeners() {
        return loadListeners;
    }

    public List<Runnable> getUnloadListeners() {
        return unloadListeners;
    }
}
