package me.engineone.engine.components.forcefield;

import me.engineone.core.component.Component;
import me.engineone.core.holder.CollectionHolder;
import me.engineone.engine.components.scheduler.SchedulerComponent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.function.Predicate;

public class ForceFieldComponent extends Component implements Runnable {

    private final CollectionHolder<Player> players;
    private final Predicate<Entity> entities;
    private final int range;
    private final SchedulerComponent schedulerComponent;

    public ForceFieldComponent(CollectionHolder<Player> players, Predicate<Entity> entities, int range, SchedulerComponent schedulerComponent) {
        this.entities = entities;
        this.players = players;
        this.range = range;
        this.schedulerComponent = schedulerComponent;
    }

    public void onEnable() {
        schedulerComponent.task(this, 20, Integer.MAX_VALUE);
    }

    public void onDisable() {
        schedulerComponent.cancelTask(this);
    }

    @Override
    public void run() {
        players.forEach(player -> {
            player.getNearbyEntities(range, range, range).forEach(entity -> {
                if (entities.test(entity))
                    entity.setVelocity(getLaunchVector(player, entity));
            });
        });
    }

    private Vector getLaunchVector(Player player, Entity entity) {
        return entity.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(0.7).setY(0.1);
    }
}
