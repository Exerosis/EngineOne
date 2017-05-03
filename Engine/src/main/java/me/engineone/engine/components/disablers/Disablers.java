package me.engineone.engine.components.disablers;

import me.engineone.core.component.AddToListComponent;
import me.engineone.core.component.ParentComponent;
import me.engineone.core.holder.CollectionHolder;
import me.engineone.engine.components.event.EventComponent;
import me.engineone.engine.utilites.BlockUtil;
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

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public class Disablers {

    // ---=== COMPONENTS ===---

    // Block Break
    public static EventComponent blockBreak(Predicate<Player> players) {
        return blockBreakBlockFilter(players, block -> true);
    }
    public static EventComponent blockBreak(Predicate<Player> players, Predicate<MaterialData> filter) {
        return blockBreakBlockFilter(players, block -> filter.test(BlockUtil.toMaterialData(block)));
    }
    public static EventComponent blockBreakBlockFilter(Predicate<Player> players, Predicate<Block> filter) {
        return EventComponent.listen(BlockBreakEvent.class, event -> {
            if (players.test(event.getPlayer()) && filter.test(event.getBlock()))
                event.setCancelled(true);
        });
    }
    // Block Place
    public static EventComponent blockPlace(Predicate<Player> players) {
        return blockPlaceBlockFilter(players, block -> true);
    }
    public static EventComponent blockPlace(Predicate<Player> players, Predicate<MaterialData> filter) {
        return blockPlaceBlockFilter(players, block -> filter.test(BlockUtil.toMaterialData(block)));
    }
    public static EventComponent blockPlaceBlockFilter(Predicate<Player> players, Predicate<Block> filter) {
        return EventComponent.listen(BlockPlaceEvent.class, event -> {
            if (players.test(event.getPlayer()) && filter.test(event.getBlock()))
                event.setCancelled(true);
        });
    }

    // Pick Up Item
    public static EventComponent itemPickup(Predicate<Player> players) {
        return itemPickupItemFilter(players, item -> true);
    }
    public static EventComponent itemPickup(Predicate<Player> players, Predicate<MaterialData> filter) {
        return itemPickupItemStackFilter(players, itemStack -> filter.test(itemStack.getData()));
    }
    public static EventComponent itemPickupItemStackFilter(Predicate<Player> players, Predicate<ItemStack> filter) {
        return itemPickupItemFilter(players, item -> filter.test(item.getItemStack()));
    }
    public static EventComponent itemPickupItemFilter(Predicate<Player> players, Predicate<Item> filter) {
        return EventComponent.listen(PlayerPickupItemEvent.class, event -> {
            if (players.test(event.getPlayer()) && filter.test(event.getItem()))
                event.setCancelled(true);
        });
    }
    // Drop Item
    public static EventComponent dropItem(Predicate<Player> players) {
        return dropItemItemFilter(players, item -> true);
    }
    public static EventComponent dropItem(Predicate<Player> players, Predicate<MaterialData> filter) {
        return dropItemItemStackFilter(players, itemStack -> filter.test(itemStack.getData()));
    }
    public static EventComponent dropItemItemStackFilter(Predicate<Player> players, Predicate<ItemStack> filter) {
        return dropItemItemFilter(players, item -> filter.test(item.getItemStack()));
    }
    public static EventComponent dropItemItemFilter(Predicate<Player> players, Predicate<Item> filter) {
        return EventComponent.listen(PlayerDropItemEvent.class, event -> {
            if (players.test(event.getPlayer()) && filter.test(event.getItemDrop()))
                event.setCancelled(true);
        });
    }

    // Damage
    public static EventComponent damage(Predicate<Player> players) {
        return damage(players, event -> true);
    }
    public static EventComponent damage(Predicate<Player> players, EntityDamageEvent.DamageCause cause) {
        return damage(players, event -> event.getCause().equals(cause));
    }
    public static EventComponent damage(Predicate<Player> players, Predicate<EntityDamageEvent> filter) {
        return EventComponent.listen(EntityDamageEvent.class, event -> {
            if (
                    filter.test(event) &&
                    event.getEntity() instanceof Player &&
                    players.test((Player) event.getEntity()))
                event.setCancelled(true);
        });
    }
    //     PvP
    public static EventComponent pvp(Predicate<Player> players) {
        return pvp(players, player -> true);
    }
    public static EventComponent pvp(Predicate<Player> players, Predicate<Player> attackers) {
        return damage(players, event ->
                    event instanceof EntityDamageByEntityEvent &&
                    ((EntityDamageByEntityEvent) event).getDamager() instanceof Player &&
                    attackers.test((Player)((EntityDamageByEntityEvent) event).getDamager())
        );
    }
    //     Fall
    public static EventComponent fallDamage(Predicate<Player> players) {
        return damage(players, EntityDamageEvent.DamageCause.FALL);
    }

    // Hunger
    public static ParentComponent hunger(CollectionHolder<Player> players) {
        return hunger(players, 20);
    }
    public static ParentComponent hunger(CollectionHolder<Player> players, int foodLevel) {

        return new ParentComponent(
                hungerChange(players),
                new AddToListComponent<>(players.getAddListeners(), player -> player.setFoodLevel(foodLevel))
        ).onEnable(() -> players.forEach(player -> player.setFoodLevel(foodLevel)));
    }

    //     Hunger Change
    public static EventComponent hungerChange(Predicate<Player> players) {
        return EventComponent.listen(FoodLevelChangeEvent.class, event -> {
            if (event.getEntity() instanceof Player && players.test((Player) event.getEntity()))
                event.setCancelled(true);
        });
    }

    // Bow
    public static EventComponent bowShoot(Predicate<Player> players) {
        return EventComponent.listen(EntityShootBowEvent.class, event -> {
            if (event.getEntity() instanceof Player && players.test((Player) event.getEntity()))
                event.setCancelled(true);
        });
    }


    // ---=== LISTENERS ===---

    // World

    //     GameRule
    public static void gameRule(List<Consumer<World>> worldListeners, String gameRule, String value) {
        worldListeners.add(world -> world.setGameRuleValue(gameRule, value));
    }
    public static void gameRule(List<Consumer<World>> worldListeners, String gameRule) {
        gameRule(worldListeners, gameRule, "false");
    }
    public static void time(List<Consumer<World>> worldListeners) {
        gameRule(worldListeners, "doDaylightCycle");
    }
    public static void time(List<Consumer<World>> worldListeners, long time) {
        worldListeners.add(world -> world.setTime(time));
        time(worldListeners);
    }
    public static void entityDrops(List<Consumer<World>> worldListeners) {
        gameRule(worldListeners, "doEntityDrops");
    }
    public static void fireSpread(List<Consumer<World>> worldListeners) {
        gameRule(worldListeners, "doFireTick");
    }
    public static void mobLoot(List<Consumer<World>> worldListeners) {
        gameRule(worldListeners, "doMobLoot");
    }
    public static void mobSpawning(List<Consumer<World>> worldListeners) {
        gameRule(worldListeners, "doMobSpawning");
    }
    public static void mobGriefing(List<Consumer<World>> worldListeners) {
        gameRule(worldListeners, "mobGriefing");
    }
    public static void naturalRegeneration(List<Consumer<World>> worldListeners) {
        gameRule(worldListeners, "naturalRegeneration");
    }
    public static void randomTickSpeed(List<Consumer<World>> worldListeners) {
        gameRule(worldListeners, "randomTickSpeed", "0");
    }
    public static void deathMessages(List<Consumer<World>> worldListeners) {
        gameRule(worldListeners, "showDeathMessages");
    }

}
