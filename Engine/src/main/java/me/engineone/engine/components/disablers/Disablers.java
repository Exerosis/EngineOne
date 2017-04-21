package me.engineone.engine.components.disablers;

import me.engineone.engine.components.event.EventComponent;
import me.engineone.engine.utilites.BlockUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.material.MaterialData;

import java.util.function.Predicate;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public class Disablers {

    // Block Break
    static EventComponent blockBreak(Predicate<Player> players) {
        return blockBreakBlockFilter(players, block -> true);
    }
    static EventComponent blockBreak(Predicate<Player> players, Predicate<MaterialData> filter) {
        return blockBreakBlockFilter(players, block -> filter.test(BlockUtil.toMaterialData(block)));
    }
    static EventComponent blockBreakBlockFilter(Predicate<Player> players, Predicate<Block> filter) {
        return EventComponent.listen(BlockBreakEvent.class, event -> {
            if (players.test(event.getPlayer()) && filter.test(event.getBlock()))
                event.setCancelled(true);
        });
    }

    // Block Place
    static EventComponent blockPlace(Predicate<Player> players) {
        return blockPlaceBlockFilter(players, block -> true);
    }
    static EventComponent blockPlace(Predicate<Player> players, Predicate<MaterialData> filter) {
        return blockPlaceBlockFilter(players, block -> filter.test(BlockUtil.toMaterialData(block)));
    }
    static EventComponent blockPlaceBlockFilter(Predicate<Player> players, Predicate<Block> filter) {
        return EventComponent.listen(BlockPlaceEvent.class, event -> {
            if (players.test(event.getPlayer()) && filter.test(event.getBlock()))
                event.setCancelled(true);
        });
    }

    // Damage
    static EventComponent damage(Predicate<Player> players) {
        return damage(players, event -> true);
    }
    static EventComponent damage(Predicate<Player> players, EntityDamageEvent.DamageCause cause) {
        return damage(players, event -> event.getCause().equals(cause));
    }
    static EventComponent damage(Predicate<Player> players, Predicate<EntityDamageEvent> filter) {
        return EventComponent.listen(EntityDamageEvent.class, event -> {
            if (
                    filter.test(event) &&
                    event.getEntity() instanceof Player &&
                    players.test((Player) event.getEntity()))
                event.setCancelled(true);
        });
    }
    //     PvP
    static EventComponent pvp(Predicate<Player> players) {
        return pvp(players, player -> true);
    }
    static EventComponent pvp(Predicate<Player> players, Predicate<Player> attackers) {
        return damage(players, event ->
                    event instanceof EntityDamageByEntityEvent &&
                    ((EntityDamageByEntityEvent) event).getDamager() instanceof Player &&
                    attackers.test((Player)((EntityDamageByEntityEvent) event).getDamager())
        );
    }
    //     Fall
    static EventComponent fallDamage(Predicate<Player> players) {
        return damage(players, EntityDamageEvent.DamageCause.FALL);
    }
}
