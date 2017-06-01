package me.engineone.engine.components;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import me.engineone.core.component.Component;
import me.engineone.core.holder.Holder;
import me.engineone.engine.utilites.PlayerUtil;
import me.engineone.engine.utilites.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by BinaryBench on 5/23/2017.
 */
public class VoidKiller extends Component {

    private int id = -1;

    public VoidKiller(@NonNull Iterable<Player> players) {
        this(players, null, null);
    }

    public VoidKiller(@NonNull Iterable<Player> players, int height) {
        this(players, null, () -> height);
    }

    public VoidKiller(@NonNull Iterable<Player> players, Predicate<World> worldPredicate) {
        this(players, worldPredicate, null);
    }

    public VoidKiller(@NonNull Iterable<Player> players, Supplier<Integer> heightSupplier) {
        this(players, null, heightSupplier);
    }

    public VoidKiller(@NonNull Iterable<Player> players, Predicate<World> worldPredicate, Supplier<Integer> heightSupplier) {

        final Predicate<World> finalWorldPredicate = worldPredicate == null ? world -> true : worldPredicate;
        final Supplier<Integer> finalHeightSupplier = worldPredicate == null ? () -> 0 : heightSupplier;


        onEnable(() -> {
            this.id = Bukkit.getScheduler().runTaskTimer(ServerUtil.getPlugin(), () -> {
                ImmutableList.copyOf(players).forEach(player -> {

                    if (finalWorldPredicate.test(player.getWorld()) && player.getLocation().getBlockY() < finalHeightSupplier.get())
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
}
