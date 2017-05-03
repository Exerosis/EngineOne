package me.engineone.engine.utilites;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/26/2017.
 */
public class WorldUtil {

    private static final ArrayList<String> IGNORE_FILES = new ArrayList<>(Arrays.asList("uid.dat", "session.dat", "playerdata", "stats"));

    private WorldUtil() {}

    /**
     * @param worldName The name of the world.
     * @return The file located at {@link Bukkit#getWorldContainer()}/
     *         {@code worldName}
     */
    public static File getWorldFile(String worldName) {
        return new File(Bukkit.getWorldContainer(), worldName);
    }

    /**
     * @param world the world
     * @return The file located at {@link Bukkit#getWorldContainer()}/
     *         {@code worldName}
     */
    public static File getWorldFile(World world) {
        return new File(Bukkit.getWorldContainer(), world.getName());
    }

    public static boolean isWorld(File file) {
        return new File(file, "level.dat").exists();
    }

    /**
     * @param worldName The name of the world.
     * @return The world named {@code worldName}, null if there is no world
     *         loaded by that name.
     */
    public static boolean isWorldLoaded(String worldName) {
        return getWorld(worldName) != null;
    }

    /**
     * @param worldName The name of the world.
     * @return The world named {@code worldName}, null if there is no world
     *         loaded by that name.
     */
    public static World getWorld(String worldName) {
        return Bukkit.getServer().getWorld(worldName);
    }

    /**
     * @param worldName The name of the world.
     * @return {@code true} if there is a named {@code worldName} in the
     *         worlds folder.
     */
    public static boolean hasWorldFile(String worldName) {
        return getWorldFile(worldName).exists();
    }

    /**
     *
     * Unloads then deletes {@code worldName}.
     *
     * @param worldName
     *              The name of the world to be deleted
     * @return {@code true} if the world is successfully unloaded. (Note: this
     *         method will return {@code true} even if it fails to delete the
     *         world files after it is unloaded)
     * @see #deleteWorld(String, Consumer)
     */
    public static boolean deleteWorld(final String worldName)
    {
        return deleteWorld(worldName, null);
    }

    /**
     *
     * Unloads then deletes {@code worldName} then calls {@code callback}
     *
     * @param worldName
     *              The name of the world to be deleted
     * @param callback
     *              A runnable that will be executed when the world is
     *              unloaded and deleted
     * @return true if the world is successfully unloaded. (Note: it will
     *         return true even if it fails to delete the world files after it
     *         is unloaded)
     */
    public static boolean deleteWorld(final String worldName, final Consumer<Boolean> callback) {

        World world = getWorld(worldName);

        if (world == null) {

            File file = getWorldFile(worldName);

            if (!file.exists()) {
                System.out.print("World " + worldName + " not found!");
                callback.accept(true);
                return true;
            }

            System.out.print("The world " + worldName + " is not loaded!");

            Bukkit.getScheduler().runTaskAsynchronously(ServerUtil.getPlugin(), () -> {
                try {
                    FileUtils.deleteDirectory(file);
                    System.out.print("Deleted the files found at: " + file.getPath() + " though.");

                    if (callback != null)
                        Bukkit.getScheduler().runTask(ServerUtil.getPlugin(), () -> callback.accept(true));

                } catch (IOException e) {
                    e.printStackTrace();
                    if (callback != null)
                        Bukkit.getScheduler().runTask(ServerUtil.getPlugin(), () -> callback.accept(false));
                }
            });
            return true;
        }
        return deleteWorld(world, callback);
    }

    /**
     *
     * Unload then deletes {@code worldName}.
     *
     * @param world
     *              The name of the world to be deleted
     * @return {@code true} if the world is successfully unloaded. (Note: this
     *         method will return {@code true} even if it fails to delete the
     *         world files after it is unloaded)
     * @see #deleteWorld(String, Consumer)
     */
    public static boolean deleteWorld(World world)
    {
        return deleteWorld(world, null);
    }

    /**
     *
     * Unload then deletes {@code worldName}.
     *
     * @param world
     *              world to be deleted
     * @param callback
     *              A runnable that will be executed when the world is
     *              unloaded and deleted
     * @return true if the world is successfully unloaded. (Note: it will
     *         return true even if it fails to delete the world files after it
     *         is unloaded)
     */
    public static boolean deleteWorld(final World world, final Consumer<Boolean> callback) {

        final File file = world.getWorldFolder();

        world.setAutoSave(false);

        if (WorldUtil.unloadWorld(world, false, (aBoolean) -> {
            Bukkit.getScheduler().runTaskAsynchronously(ServerUtil.getPlugin(), () -> {
                if (aBoolean)
                    System.out.println(String.format("Successfully finished unloading %s", world.getName()));
                else
                    System.err.println(String.format("Failed to unload %s", world.getName()));

                try {
                    FileUtils.deleteDirectory(file);
                    System.out.println(String.format("Successfully deleted %s", world.getName()));
                    if (callback != null)
                        Bukkit.getScheduler().runTask(ServerUtil.getPlugin(), () -> callback.accept(true));
                } catch (Exception e) {
                    System.err.println(String.format("FAILED TO DELETE %s!", world.getName()));
                    e.printStackTrace();
                    if (callback != null)
                        Bukkit.getScheduler().runTask(ServerUtil.getPlugin(), () -> callback.accept(false));
                }

            });
        })) {
            System.err.println(String.format("Successfully started to unload %s", world.getName()));
            return true;
        } else {
            System.err.println(String.format("Bukkit cowardly refused to unload the world: %s", world.getName()));
            if (callback != null)
                callback.accept(false);
            return false;
        }
    }

    public static boolean createWorld(File srcFile, String worldName) {
        return createWorld(srcFile, new WorldCreator(worldName), null);
    }

    public static boolean createWorld(File srcFile, String worldName, Consumer<World> callback) {
        return createWorld(srcFile, new WorldCreator(worldName), callback);
    }

    /**
     *
     * Copys the world from the {@code srcFile} to the worlds folder, then
     * loads to world.
     *
     * @param srcFile the file the world will be copied from.
     * @param worldCreator
     *              The world Creator
     * @return true if the world is attempted to load, false otherwise. (Note: the world can still fail to load even
     * if this method returns true)
     */
    public static boolean createWorld(File srcFile, WorldCreator worldCreator, Consumer<World> callback) {

        String worldName = worldCreator.name();

        if (!srcFile.exists()) {
            System.err.print("There is no file at: " + srcFile.getPath() + " So it can not be loaded into the server");
            if (callback != null)
                callback.accept(null);
            return false;
        } else if (!isWorld(srcFile)) {
            System.err.print("The File at " + srcFile.getPath() + " is not a world file!");
            if (callback != null)
                callback.accept(null);
            return false;
        }

        File destFile = getWorldFile(worldName);
        Runnable copyAndLoad = () -> Bukkit.getScheduler().runTaskAsynchronously(ServerUtil.getPlugin(), () -> {
            copyWorld(srcFile, destFile);
            if (callback != null)
                Bukkit.getScheduler().runTask(ServerUtil.getPlugin(), () -> {
                    callback.accept(Bukkit.createWorld(worldCreator));
                });
        });

        if (destFile.exists()) {
            World currentWorld = WorldUtil.getWorld(worldName);

            if (currentWorld != null) {
                System.err.print("The world " + worldName + " already exists!");
                if (callback != null)
                    callback.accept(null);
                return false;
            }

            System.out.print("There found a file at: " + destFile.getPath() + ", deleting...");

            Bukkit.getScheduler().runTaskAsynchronously(ServerUtil.getPlugin(), () -> {
                try {
                    FileUtils.deleteDirectory(destFile);
                } catch (IOException e) {
                    System.err.print("Unable to delete: " + destFile.getPath() + ".");
                    e.printStackTrace();
                }
                copyAndLoad.run();
            });
            return true;
        }
        copyAndLoad.run();
        return true;
    }

    public static boolean copyWorld(File source, File target) {
        try {
            FileUtils.copyDirectory(source, target, file -> !IGNORE_FILES.contains(file.getName()));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean unloadWorld(World world, boolean save, Consumer<Boolean> callback) {
        if (world == null)
            return true;

        for (Player player:world.getPlayers())
            player.kickPlayer("#BlameBukkit");

        if (!Bukkit.unloadWorld(world, save)) {
            callback.accept(false);
            return false;
        }
        Bukkit.getScheduler().runTaskLater(ServerUtil.getPlugin(), () -> callback.accept(true), 60L);
        return true;
    }

}
