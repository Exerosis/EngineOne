package me.engineone.example.games.world;

import me.engineone.core.observable.StaticObservable;
import me.engineone.engine.components.world.WorldComponent;
import me.engineone.engine.utilites.*;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BinaryBench on 5/3/2017.
 */
public class GameWorldComponent extends WorldComponent {

    public GameWorldComponent(String gameName) {
        super(new StaticObservable<>(() -> getSaveFile(gameName)));
    }

    private static File getSaveFile(String gameName) {

        File dataFolder = ServerUtil.getPlugin().getDataFolder();
        if (!dataFolder.isDirectory()) {
            System.err.println("Could not find plugin data folder!");
            return null;
        }

        File worldsFolder = new File(dataFolder, "Worlds");

        if (!worldsFolder.isDirectory())
        {
            System.err.println("Worlds folder not found! Creating one...");
            //noinspection ResultOfMethodCallIgnored
            worldsFolder.mkdirs();
            return null;
        }

        List<File> possibleWorlds = new ArrayList<>();
        File[] files = worldsFolder.listFiles();

        if (files == null)
            files = new File[]{};

        for (File file : files)
        {
            if (WorldUtil.isWorld(file))
            {
                File configurationFile = new File(file, "mapdata.yml");

                if (!configurationFile.exists())
                {
                    System.err.println("World " + file.getName() + " does not have a mapdata.yml file!");
                    continue;
                }

                YamlConfiguration mapdata = YamlConfiguration.loadConfiguration(configurationFile);

                if (ListUtil.containsIgnoreCase(mapdata.getStringList("Games"), gameName))
                    possibleWorlds.add(file);

            }
        }

        if (possibleWorlds.isEmpty())
        {
            System.err.println("No worlds found for game: " + gameName);
            return null;
        }

        return RandomUtil.randomElement(possibleWorlds);
    }

}
