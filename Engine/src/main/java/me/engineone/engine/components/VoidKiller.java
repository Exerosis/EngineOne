package me.engineone.engine.components;

import com.google.common.collect.ImmutableList;
import me.engineone.core.component.Component;
import me.engineone.core.holder.Holder;
import me.engineone.engine.utilites.PlayerUtil;
import me.engineone.engine.utilites.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Iterator;

/**
 * Created by BinaryBench on 5/23/2017.
 */
public class VoidKiller extends Component {

    private int id = -1;
    private int height;


    public VoidKiller(Iterable<Player> players) {
        this(players, 0);
    }

    public VoidKiller(Iterable<Player> players, int height) {
        this.height = height;

        onEnable(() -> {
            this.id = Bukkit.getScheduler().runTaskTimer(ServerUtil.getPlugin(), () -> {
                ImmutableList.copyOf(players).forEach(player -> {
                    if (player.getLocation().getBlockY() < this.height)
                        PlayerUtil.killPlayer(player);
                });
            }, 4L, 4L).getTaskId();
        });

        onDisable(() -> {
            if (this.id != -1) {
                Bukkit.getScheduler().cancelTask(this.id);
                this.id = -1;
            }
        });
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
