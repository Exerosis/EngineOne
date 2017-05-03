package me.engineone.engine.components.world;

import me.engineone.core.component.CollectionHolderComponent;
import me.engineone.core.component.Component;
import me.engineone.engine.utilites.WorldUtil;
import org.bukkit.World;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 5/2/2017.
 */
public class WorldComponent extends CollectionHolderComponent<World> {

    private File saveFile;
    private Object idObject;

    public WorldComponent(File saveFile) {
        this.saveFile = saveFile;

        onEnable(() -> {
            if (idObject != null)
                throw new IllegalStateException("IdObject is not null! (this probably means WorldComponent was enabled twice before being disabled)");
            idObject = new Object();

            WorldUtil.deleteWorld(getWorldName(), aBoolean -> {
                WorldUtil.createWorld(saveFile, getWorldName(), world -> {
                    if (world != null)
                        add(world);
                });
            });


        });

        onDisable(() -> {
            if (idObject == null)
                throw new IllegalStateException("IdObject is null! (this probably means WorldComponent was disabled before being enabled)");
            World world = WorldUtil.getWorld(getWorldName());
            if (world != null)
                remove(world);
            WorldUtil.deleteWorld(getWorldName());
            idObject = null;
        });

    }

    public String getWorldName() {
        return saveFile.getName() + "-" + idObject.hashCode();
    }

    public List<Consumer<World>> getOnLoadListeners() {
        return getAddListeners();
    }

    public List<Consumer<World>> getOnUnloadListeners() {
        return getRemoveListeners();
    }
}
