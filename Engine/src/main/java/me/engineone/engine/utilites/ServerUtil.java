package me.engineone.engine.utilites;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by BinaryBench on 4/23/2017.
 */
public class ServerUtil {

    private static Plugin plugin;

    public static void init(Plugin plugin) {
        ServerUtil.plugin = plugin;
    }

    public static void shutdown(String shutdownMessage) {
        shutdown(shutdownMessage, "Bye bye");
    }

    public static void shutdown(String shutdownMessage, String kickMessage) {
        for (Player player : Bukkit.getOnlinePlayers())
            player.kickPlayer(kickMessage);

        System.err.println(" ");
        System.err.println(shutdownMessage);
        System.err.println(" ");

        Bukkit.shutdown();
    }

    public static void broadcast(String message, Iterable<Player> players) {
        System.out.println(message);
        for (Player player : players)
            player.sendMessage(message);
    }

    public static void broadcast(String message) {
        Bukkit.broadcastMessage(message);
    }

    public static Player getPlayer(String name) {
        return Bukkit.getPlayer(name);
    }

    public static Player getPlayer(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    public static boolean isOnline(UUID uuid) {
        return getPlayer(uuid) != null;
    }

    public static UUID getPlayersUUID(String name) {
        return getOfflinePlayer(name).getUniqueId();
    }

    @SuppressWarnings("deprecation")
    public static OfflinePlayer getOfflinePlayer(String name) {
        return Bukkit.getOfflinePlayer(name);
    }

    public static OfflinePlayer getOfflinePlayer(UUID uuid) {
        return Bukkit.getOfflinePlayer(uuid);
    }

    public static List<String> getOnlinePlayerNames()  {
        return getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
    }

    public static Collection<? extends Player> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers();
    }

    public static Plugin getPlugin() {
        if (plugin == null)
            plugin = Bukkit.getPluginManager().getPlugins()[0];
        return plugin;
    }

    public static void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, getPlugin());
    }

    public static void unregisterListener(Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    public static void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }
}
