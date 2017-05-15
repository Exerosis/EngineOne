package me.engineone.example.games.world;

import me.engineone.core.holder.CollectionHolder;
import me.engineone.engine.components.disablers.Disablers;
import me.engineone.engine.components.world.WorldComponent;
import me.engineone.engine.holder.WorldPlayerHolder;
import me.engineone.engine.utilites.PlayerUtil;
import me.engineone.engine.utilites.RandomUtil;
import me.engineone.engine.utilites.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;

/**
 * Created by BinaryBench on 5/3/2017.
 */
public class LobbyWorldComponent extends WorldComponent implements Runnable {

    private int id = -1;
    private CollectionHolder<Player> players;

    public LobbyWorldComponent() {
        super(new File(ServerUtil.getPlugin().getDataFolder(), "LobbyWorld"));

        players = new WorldPlayerHolder(this);

        addChild(
                Disablers.damage(players),
                Disablers.blockBreak(player -> !player.getGameMode().equals(GameMode.CREATIVE) && players.test(player)),
                Disablers.blockPlace(player -> !player.getGameMode().equals(GameMode.CREATIVE) && players.test(player)),
                Disablers.hunger(players)
        );

        getOnUnloadListeners().addAll(Arrays.asList(
                Disablers.fireSpread(),
                Disablers.mobSpawning(),
                Disablers.mobGriefing(),
                Disablers.naturalRegeneration(),
                Disablers.randomTickSpeed()
        ));

        onEnable(() -> {
            this.id = Bukkit.getScheduler().runTaskTimer(ServerUtil.getPlugin(), this, 4L, 4L).getTaskId();
        });

        onDisable(() -> {
            if (this.id != -1) {
                Bukkit.getScheduler().cancelTask(this.id);
                this.id = -1;
            }
        });
    }

    @Override
    public void run() {
        for (Player player : getPlayers()) {
            Location loc = player.getLocation();
            if (loc.getBlockY() < 50) {
                spawnPlayer(player);
            }
        }
    }
    public void spawnPlayer(Player player)
    {
        PlayerUtil.resetPlayer(player);
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(getSpawn());
    }


    public Location getSpawn()
    {
        double x = RandomUtil.randomDouble(-1, 2);
        double y = 80;
        double z = RandomUtil.randomDouble(-1, 2);

        return new Location(getWorld(), x, y, z);
    }

    public CollectionHolder<Player> getPlayers() {
        return players;
    }
}
