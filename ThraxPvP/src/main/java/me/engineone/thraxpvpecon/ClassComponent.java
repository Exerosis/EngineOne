package me.engineone.thraxpvpecon;

import me.engineone.core.component.ParentComponent;
import me.engineone.core.holder.CollectionHolder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RListMultimap;
import org.redisson.api.RedissonClient;

import java.util.ArrayList;

import static me.engineone.core.Extensions.range;
import static me.engineone.engine.components.event.EventComponent.listen;
import static me.engineone.thraxpvpecon.EconController.msgFormat;

//ClassComponent: Creates a NPC GUI to hand out classes for Players

public class ClassComponent extends ParentComponent{

    //ClassShop Location
    private final Location npcLocation;
    private final Location[] npcIndividualLocations;

    //NPC Entities
    private final CollectionHolder<LivingEntity> npcCreatures;

    //Redis
    private final RedissonClient redisson;
    private final RList redisSpawnList;
    private final RList redisClass1PlayersList;
    private final RList redisClass2PlayersList;
    private final RList redisClass3PlayersList;
    private final RList redisClass4PlayersList;
    private final RListMultimap redisPlayerMoneyList;

    //Init
    public ClassComponent(){

        //Redis
        redisson = Redisson.create();
        redisSpawnList = redisson.getList("npc-loc");
        redisClass1PlayersList = redisson.getList("class-1-users");
        redisClass2PlayersList = redisson.getList("class-2-users");
        redisClass3PlayersList = redisson.getList("class-3-users");
        redisClass4PlayersList = redisson.getList("class-4-users");
        redisPlayerMoneyList = redisson.getListMultimap("player-econdata");

        //Spawn Variables
        npcLocation = new Location(Bukkit.getWorld("test"), (double)redisSpawnList.get(0),
        (double)redisSpawnList.get(1), (double)redisSpawnList.get(2));
        npcIndividualLocations = new Location[]{
            npcLocation,
            npcLocation.add(0, 0, 4),
            npcLocation.add(0, 0, 8),
            npcLocation.add(0, 0, 12)
        };
        npcCreatures = (CollectionHolder) new ArrayList<LivingEntity>();

        //Spawn NPC's
        onEnable(
            () -> range(0, 4, (consumer) -> npcCreatures.add((LivingEntity) Bukkit.getWorld("test").spawnEntity(npcIndividualLocations[npcCreatures.size()], EntityType.WITHER_SKELETON)))
        );
        npcCreatures.onAdd(
            (consumer) -> npcCreatures.forEach(
                npc -> {
                    npc.setSilent(true);
                    npc.setInvulnerable(true);
                    npc.setAI(false);
                    npc.setCustomNameVisible(true);
                    if(npcCreatures.size() == 1)
                        npc.setCustomName("Class #1");
                    else if(npcCreatures.size() == 2)
                        npc.setCustomName("Class #2");
                    else if(npcCreatures.size() == 3)
                        npc.setCustomName("Class #3");
                    else if(npcCreatures.size() == 4)
                        npc.setCustomName("Class #4");
                }
            )
        );

        //Listen for interaction with the NPC's and Create the ClassGUI
        addChild(listen(PlayerInteractEntityEvent.class,
            (PlayerInteractEntityEvent playerInteractEntityEvent) -> {
                Player guiPlayer = playerInteractEntityEvent.getPlayer();
                String classType = "";
                if(npcCreatures.contains(playerInteractEntityEvent.getRightClicked())) {
                    if (playerInteractEntityEvent.getRightClicked().equals(npcCreatures.toArray()[0]))
                        classType = "#1";
                    else if (playerInteractEntityEvent.getRightClicked().equals(npcCreatures.toArray()[1]))
                        classType = "#2";
                    else if (playerInteractEntityEvent.getRightClicked().equals(npcCreatures.toArray()[2]))
                        classType = "#3";
                    else if (playerInteractEntityEvent.getRightClicked().equals(npcCreatures.toArray()[3]))
                        classType = "#4";
                    Inventory gui = Bukkit.createInventory(guiPlayer, InventoryType.CHEST, "Obtain Class " + classType + "?");

                    ItemStack glassdisplayItem = new ItemStack(Material.STAINED_GLASS_PANE);
                    glassdisplayItem.setData(new MaterialData(Material.STAINED_GLASS_PANE, (byte) 5));
                    CollectionHolder<ItemStack> glassCollection = null;
                    range(0, 53, (consumer) -> glassCollection.add(glassdisplayItem));
                    gui.setContents((ItemStack[]) glassCollection.toArray());

                    ItemStack backItem = new ItemStack(Material.NETHER_WARTS);
                    ItemMeta backMeta = backItem.getItemMeta();
                    backMeta.setDisplayName("Go Back");
                    backItem.setItemMeta(backMeta);
                    gui.setItem(8, backItem);

                    ItemStack classskullDisplayer = new ItemStack(Material.SKULL_ITEM);
                    SkullMeta skullMeta = (SkullMeta) classskullDisplayer.getItemMeta();
                    skullMeta.setDisplayName("Class " + classType);
                    skullMeta.setOwner("MHF_WSkeleton");
                    classskullDisplayer.setItemMeta(skullMeta);
                    gui.setItem(0, classskullDisplayer);

                    ItemStack buyItem = new ItemStack(Material.SLIME_BALL);
                    ItemMeta buyMeta = buyItem.getItemMeta();
                    buyMeta.setDisplayName("Buy Class?");
                    buyItem.setItemMeta(buyMeta);
                    gui.setItem(21, buyItem);

                    ItemStack sellItem = new ItemStack(Material.TNT);
                    ItemMeta sellMeta = sellItem.getItemMeta();
                    sellMeta.setDisplayName("Sell Class?");
                    sellItem.setItemMeta(sellMeta);
                    gui.setItem(23, sellItem);

                    guiPlayer.openInventory(gui);
                }
            }
        ));

        //Handle GUI
        addChild(listen(InventoryClickEvent.class,
            (InventoryClickEvent clickEvent) -> {
                if(clickEvent.getClickedInventory().getName().equalsIgnoreCase("Obtain Class #1?")
                || clickEvent.getClickedInventory().getName().equalsIgnoreCase("Obtain Class #2?")
                || clickEvent.getClickedInventory().getName().equalsIgnoreCase("Obtain Class #3?")
                || clickEvent.getClickedInventory().getName().equalsIgnoreCase("Obtain Class #4?")){
                    if(clickEvent.getClickedInventory().getName().equalsIgnoreCase("Obtain Class #1?")) {
                        if (clickEvent.getSlot() == 21) {
                            redisPlayerMoneyList.get(clickEvent.getWhoClicked().getUniqueId()).set(0,
                            (double)redisPlayerMoneyList.get(clickEvent.getWhoClicked().getUniqueId()).get(0) - 200.0);
                            redisClass1PlayersList.add(clickEvent.getWhoClicked().getUniqueId());
                            clickEvent.getWhoClicked().sendMessage(msgFormat + "You now have obtained Class #1.");
                            clickEvent.getWhoClicked().closeInventory();
                        } else if (clickEvent.getSlot() == 23) {
                            redisPlayerMoneyList.get(clickEvent.getWhoClicked().getUniqueId()).set(0,
                            (double)redisPlayerMoneyList.get(clickEvent.getWhoClicked().getUniqueId()).get(0) + 200.0);
                            redisClass1PlayersList.remove(clickEvent.getWhoClicked().getUniqueId());
                            clickEvent.getWhoClicked().sendMessage(msgFormat + "You sold your current class of Class #1.");
                            clickEvent.getWhoClicked().closeInventory();
                        }
                    }
                    if(clickEvent.getClickedInventory().getName().equalsIgnoreCase("Obtain Class #2?")) {
                        if (clickEvent.getSlot() == 21) {
                            redisPlayerMoneyList.get(clickEvent.getWhoClicked().getUniqueId()).set(0,
                            (double)redisPlayerMoneyList.get(clickEvent.getWhoClicked().getUniqueId()).get(0) - 200.0);
                            redisClass2PlayersList.add(clickEvent.getWhoClicked().getUniqueId());
                            clickEvent.getWhoClicked().sendMessage(msgFormat + "You now have obtained Class #2.");
                            clickEvent.getWhoClicked().closeInventory();
                        } else if (clickEvent.getSlot() == 23) {
                            redisPlayerMoneyList.get(clickEvent.getWhoClicked().getUniqueId()).set(0,
                            (double)redisPlayerMoneyList.get(clickEvent.getWhoClicked().getUniqueId()).get(0) + 200.0);
                            redisClass2PlayersList.remove(clickEvent.getWhoClicked().getUniqueId());
                            clickEvent.getWhoClicked().sendMessage(msgFormat + "You sold your current class of Class #2.");
                            clickEvent.getWhoClicked().closeInventory();
                        }
                    }
                    if(clickEvent.getClickedInventory().getName().equalsIgnoreCase("Obtain Class #3?")) {
                        if (clickEvent.getSlot() == 21) {
                            redisPlayerMoneyList.get(clickEvent.getWhoClicked().getUniqueId()).set(0,
                            (double)redisPlayerMoneyList.get(clickEvent.getWhoClicked().getUniqueId()).get(0) - 200.0);
                            redisClass3PlayersList.add(clickEvent.getWhoClicked().getUniqueId());
                            clickEvent.getWhoClicked().sendMessage(msgFormat + "You now have obtained Class #3.");
                            clickEvent.getWhoClicked().closeInventory();
                        } else if (clickEvent.getSlot() == 23) {
                            redisPlayerMoneyList.get(clickEvent.getWhoClicked().getUniqueId()).set(0,
                            (double)redisPlayerMoneyList.get(clickEvent.getWhoClicked().getUniqueId()).get(0) + 200.0);
                            redisClass3PlayersList.remove(clickEvent.getWhoClicked().getUniqueId());
                            clickEvent.getWhoClicked().sendMessage(msgFormat + "You sold your current class of Class #3.");
                            clickEvent.getWhoClicked().closeInventory();
                        }
                    }
                    if(clickEvent.getClickedInventory().getName().equalsIgnoreCase("Obtain Class #4?")) {
                        if (clickEvent.getSlot() == 21) {
                            redisPlayerMoneyList.get(clickEvent.getWhoClicked().getUniqueId()).set(0,
                            (double)redisPlayerMoneyList.get(clickEvent.getWhoClicked().getUniqueId()).get(0) - 200.0);
                            redisClass4PlayersList.add(clickEvent.getWhoClicked().getUniqueId());
                            clickEvent.getWhoClicked().sendMessage(msgFormat + "You now have obtained Class #4.");
                            clickEvent.getWhoClicked().closeInventory();
                        } else if (clickEvent.getSlot() == 23) {
                            redisPlayerMoneyList.get(clickEvent.getWhoClicked().getUniqueId()).set(0,
                            (double)redisPlayerMoneyList.get(clickEvent.getWhoClicked().getUniqueId()).get(0) + 200.0);
                            redisClass4PlayersList.remove(clickEvent.getWhoClicked().getUniqueId());
                            clickEvent.getWhoClicked().sendMessage(msgFormat + "You sold your current class of Class #4.");
                            clickEvent.getWhoClicked().closeInventory();
                        }
                    }
                    if(clickEvent.getSlot() == 8)
                        clickEvent.getWhoClicked().closeInventory();
                    if(!(clickEvent.getSlot() == 8 || clickEvent.getSlot() == 21 || clickEvent.getSlot() == 23))
                        clickEvent.setCancelled(true);
                }
            }
        ));
    }
}
