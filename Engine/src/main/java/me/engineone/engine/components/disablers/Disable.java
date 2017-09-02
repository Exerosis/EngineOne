package me.engineone.engine.components.disablers;

import me.engineone.core.component.AddToListComponent;
import me.engineone.core.component.ParentComponent;
import me.engineone.core.enableable.Enableable;
import me.engineone.core.holder.MutableHolder;
import me.engineone.core.holder.MutateHolder;
import me.engineone.engine.components.event.EventComponent;
import me.engineone.engine.utilites.BlockUtil;
import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public class Disable {

    // ---=== COMPONENTS ===---

    // Block Break
    public static EventComponent blockBreak() {
        return blockBreakBlockFilter(player -> true, block -> true);
    }

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
    public static EventComponent blockPlace() {
        return blockPlaceBlockFilter(player -> true, block -> true);
    }

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
                        attackers.test((Player) ((EntityDamageByEntityEvent) event).getDamager())
        );
    }

    //     Fall
    public static EventComponent fallDamage(Predicate<Player> players) {
        return damage(players, EntityDamageEvent.DamageCause.FALL);
    }

    // Hunger
    public static ParentComponent hunger(MutateHolder<Player> players) {
        return hunger(players, 20);
    }

    public static ParentComponent hunger(MutateHolder<Player> players, int foodLevel) {
        ParentComponent parentComponent = new ParentComponent();
        parentComponent.addChild(hungerChange(players));
        parentComponent.addChild(new AddToListComponent<>(players.getAddListeners(), player -> player.setFoodLevel(foodLevel)));
        parentComponent.onEnable(() -> players.forEach(player -> player.setFoodLevel(foodLevel)));
        return parentComponent;
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

    // Falling Blocks
    public static EventComponent fallingBlocks(Predicate<World> worldPredicate) {
        return EventComponent.listen(EntityChangeBlockEvent.class, event -> {
            if (event.getEntityType().equals(EntityType.FALLING_BLOCK)) {

                if (!worldPredicate.test(event.getBlock().getWorld()))
                    return;

                if (event.getEntity() instanceof FallingBlock) {
                    FallingBlock block = (FallingBlock) event.getEntity();

                    block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getMaterial());
                }
                event.getEntity().remove();
                event.setCancelled(true);
            }
        });
    }

    // ---=== LISTENERS ===---

    // WorldObservable

    //     GameRule
    public static Consumer<World> gameRule(String gameRule, String value) {
        return world -> world.setGameRuleValue(gameRule, value);
    }

    public static Consumer<World> gameRule(String gameRule) {
        return gameRule(gameRule, "false");
    }

    public static Consumer<World> time() {
        return gameRule("doDaylightCycle");
    }

    public static Consumer<World> time(long time) {
        Consumer<World> stopTime = time();
        return world -> {
            world.setTime(time);
            stopTime.accept(world);
        };
    }

    public static Consumer<World> entityDrops() {
        return gameRule("doEntityDrops");
    }

    public static Consumer<World> fireSpread() {
        return gameRule("doFireTick");
    }

    public static Consumer<World> mobLoot() {
        return gameRule("doMobLoot");
    }

    public static Consumer<World> mobSpawning() {
        return gameRule("doMobSpawning");
    }

    public static Consumer<World> mobGriefing() {
        return gameRule("mobGriefing");
    }

    public static Consumer<World> naturalRegeneration() {
        return gameRule("naturalRegeneration");
    }

    public static Consumer<World> randomTickSpeed() {
        return gameRule("randomTickSpeed", "0");
    }

    public static Consumer<World> deathMessages() {
        return gameRule("showDeathMessages");
    }

    public static Enableable movement(MutableHolder<Player> players) {
        throw new UnsupportedOperationException("Method not yet implemented.");
    }
}
