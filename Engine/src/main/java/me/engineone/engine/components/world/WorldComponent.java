package me.engineone.engine.components.world;

import me.engineone.core.component.CollectionHolderComponent;
import me.engineone.core.component.SingleHolderComponent;
import me.engineone.core.observable.Observable;
import me.engineone.engine.utilites.ServerUtil;
import me.engineone.engine.utilites.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 5/2/2017.
 */
public class WorldComponent extends SingleHolderComponent<World> {

    private File saveFile;
    private Object idObject;


    public WorldComponent(Observable<File> fileObservable) {

        addChild(Observable.observe(fileObservable, file -> {
            this.saveFile = file;

            if (idObject != null)
                throw new IllegalStateException("IdObject is not null! (this probably means WorldComponent was enabled twice before being disabled)");
            idObject = new Object();

            WorldUtil.deleteWorld(getWorldName(), aBoolean -> {
                WorldUtil.createWorld(saveFile, getWorldName(), world -> {
                    if (world != null)
                        setValue(world);
                });
            });

        }));

        onDisable(() -> {

            if (idObject != null) {

                World world = getWorld();
                if (world != null) {
                    setValue(null);
                    final String name = getWorldName();

                    Bukkit.getScheduler().runTaskLater(ServerUtil.getPlugin(), () -> WorldUtil.deleteWorld(name), 20L * 5);
                }
            }

            idObject = null;
            saveFile = null;

        });

    }

    public String getWorldName() {
        return saveFile.getName() + "-" + idObject.hashCode();
    }

    public World getWorld() {
        return WorldUtil.getWorld(getWorldName());
    }

    public List<Consumer<World>> getLoadListeners() {
        return getAddListeners();
    }

    public List<Consumer<World>> getUnloadListeners() {
        return getRemoveListeners();
    }

    public void onLoad(Consumer<World> listener) {
        getLoadListeners().add(listener);
    }

    public void onUnload(Consumer<World> listener) {
        getUnloadListeners().add(listener);
    }

}
