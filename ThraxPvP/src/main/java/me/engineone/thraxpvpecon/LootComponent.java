package me.engineone.thraxpvpecon;

import me.engineone.core.component.ParentComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RListMultimap;
import org.redisson.api.RedissonClient;

import static me.engineone.engine.components.event.EventComponent.listen;
import static me.engineone.thraxpvpecon.EconController.msgFormat;

//LootComponent: Handles LootCrate Interaction

public class LootComponent extends ParentComponent{

    //Redis
    private final RedissonClient redisson;
    private final RList cratelocationList;
    private final RListMultimap playermoneyList;

    //Elements of the LootCrate
    private final ItemStack lootItem;
    private final ItemMeta lootMeta;

    //Loot ItemStack
    private final ItemStack[] lootItems;

    private final Location crateLocation;

    //Main Initializer
    public LootComponent(){

        //Redis
        redisson = Redisson.create();
        cratelocationList = redisson.getList("lootcrate-loc");
        playermoneyList = redisson.getListMultimap("player-econdata");


        //Define Key Item
        lootItem = new ItemStack(Material.TRIPWIRE_HOOK);
        lootMeta = lootItem.getItemMeta();
        lootMeta.setDisplayName("LootCrate Key");
        lootItem.setItemMeta(lootMeta);

        //Define Crate Location
        crateLocation = new Location(Bukkit.getWorld("test"),
        (double)cratelocationList.get(0), (double)cratelocationList.get(1), (double)cratelocationList.get(2));

        //Define Loot Items
        lootItems = new ItemStack[]{
            new ItemStack(Material.DIAMOND, 3),
            new ItemStack(Material.GOLDEN_APPLE),
            new ItemStack(Material.COOKED_BEEF, 16)
        };

        //Spawn in CrateOpener
        onEnable(() -> crateLocation.getWorld().getBlockAt(crateLocation).setType(Material.CHEST));

        //Listen for Interaction (Opening of the Crate)
        addChild(listen(PlayerInteractEvent.class,
            (PlayerInteractEvent playerInteractEvent) -> {
                if(playerInteractEvent.getClickedBlock().getLocation().equals(crateLocation)){
                    if(playerInteractEvent.hasItem()){
                        if(playerInteractEvent.getItem().equals(lootItem)){
                            Player interactPlayer = playerInteractEvent.getPlayer();
                            interactPlayer.getInventory().remove(lootItem);
                            interactPlayer.getInventory().addItem(lootItems);
                            playermoneyList.get(interactPlayer.getUniqueId()).set(0,
                            (double)playermoneyList.get(interactPlayer.getUniqueId()).get(0) + 400.0);
                            interactPlayer.playSound(interactPlayer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                            interactPlayer.sendMessage(msgFormat + "Your LootCrate unlocked some nice loot and $400!");
                        }
                    }
                }
            }
        ));
    }
}
