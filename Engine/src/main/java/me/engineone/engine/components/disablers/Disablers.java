package me.engineone.engine.components.disablers;

import me.engineone.core.component.Component;
import me.engineone.core.component.ParentComponent;
import me.engineone.core.holder.CollectionHolder;
import me.engineone.engine.components.event.EventComponent;
import me.engineone.engine.utilites.BlockUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    // Pick Up Item
    static EventComponent itemPickup(Predicate<Player> players) {
        return itemPickupItemFilter(players, item -> true);
    }
    static EventComponent itemPickup(Predicate<Player> players, Predicate<MaterialData> filter) {
        return itemPickupItemStackFilter(players, itemStack -> filter.test(itemStack.getData()));
    }
    static EventComponent itemPickupItemStackFilter(Predicate<Player> players, Predicate<ItemStack> filter) {
        return itemPickupItemFilter(players, item -> filter.test(item.getItemStack()));
    }
    static EventComponent itemPickupItemFilter(Predicate<Player> players, Predicate<Item> filter) {
        return EventComponent.listen(PlayerPickupItemEvent.class, event -> {
            if (players.test(event.getPlayer()) && filter.test(event.getItem()))
                event.setCancelled(true);
        });
    }
    // Drop Item
    static EventComponent dropItem(Predicate<Player> players) {
        return dropItemItemFilter(players, item -> true);
    }
    static EventComponent dropItem(Predicate<Player> players, Predicate<MaterialData> filter) {
        return dropItemItemStackFilter(players, itemStack -> filter.test(itemStack.getData()));
    }
    static EventComponent dropItemItemStackFilter(Predicate<Player> players, Predicate<ItemStack> filter) {
        return dropItemItemFilter(players, item -> filter.test(item.getItemStack()));
    }
    static EventComponent dropItemItemFilter(Predicate<Player> players, Predicate<Item> filter) {
        return EventComponent.listen(PlayerDropItemEvent.class, event -> {
            if (players.test(event.getPlayer()) && filter.test(event.getItemDrop()))
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

    // Hunger
    static EventComponent hunger(CollectionHolder<Player> players) {
        return hunger(players, 20);
    }
    static EventComponent hunger(CollectionHolder<Player> players, int foodLevel) {
        return (EventComponent)
                hungerChange(players)
                .registerToListenable(players.getAddListenable(), player -> player.setFoodLevel(foodLevel))
                .addEnable(() -> players.forEach(player -> player.setFoodLevel(foodLevel)));
    }
    //     Hunger Change
    static EventComponent hungerChange(Predicate<Player> players) {
        return EventComponent.listen(FoodLevelChangeEvent.class, event -> {
            if (event.getEntity() instanceof Player && players.test((Player) event.getEntity()))
                event.setCancelled(true);
        });
    }

    // Bow
    static EventComponent bowShoot(Predicate<Player> players) {
        return EventComponent.listen(EntityShootBowEvent.class, event -> {
            if (event.getEntity() instanceof Player && players.test((Player) event.getEntity()))
                event.setCancelled(true);
        });
    }
}
