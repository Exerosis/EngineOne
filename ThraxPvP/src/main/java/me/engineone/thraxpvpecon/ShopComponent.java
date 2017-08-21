package me.engineone.thraxpvpecon;

//ShopComponent: Creates an Item GUI to Give Players Resources

import me.engineone.core.component.ParentComponent;
import me.engineone.core.holder.CollectionHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.redisson.Redisson;
import org.redisson.api.RListMultimap;
import org.redisson.api.RedissonClient;

import static me.engineone.core.Extensions.range;
import static me.engineone.engine.components.event.EventComponent.listen;
import static me.engineone.thraxpvpecon.EconController.msgFormat;

public class ShopComponent extends ParentComponent{

    //Redis
    private final RedissonClient redisson;
    private final RListMultimap redisplayermoneyList;

    //Main Initializer
    public ShopComponent(){

        //Redis
        redisson = Redisson.create();
        redisplayermoneyList = redisson.getListMultimap("player-econdata");

        //Create ShopGUI Elements
        ItemStack glassDisplay = new ItemStack(Material.STAINED_GLASS_PANE);
        glassDisplay.setData(new MaterialData((byte) 5));
        CollectionHolder<ItemStack> glassCollection = null;
        range(0, 53, (consumer) -> glassCollection.add(glassDisplay));
        ItemStack[] glassWindow = (ItemStack[]) glassCollection.toArray();

        ItemStack shopItem = new ItemStack(Material.EMERALD);
        ItemMeta shopMeta = shopItem.getItemMeta();
        shopMeta.setDisplayName("Shop");
        shopItem.setItemMeta(shopMeta);

        ItemStack backItem = new ItemStack(Material.NETHER_WARTS);
        ItemMeta backMeta = backItem.getItemMeta();
        backMeta.setDisplayName("Go Back");
        backItem.setItemMeta(backMeta);

        ItemStack kitsItem = new ItemStack(Material.IRON_SWORD);
        ItemMeta kitsMeta = kitsItem.getItemMeta();
        kitsMeta.setDisplayName("Kits");
        kitsItem.setItemMeta(kitsMeta);

        ItemStack resourcesItem = new ItemStack(Material.IRON_INGOT);
        ItemMeta resourcesMeta = resourcesItem.getItemMeta();
        resourcesMeta.setDisplayName("Resources Supply");
        resourcesItem.setItemMeta(resourcesMeta);

        ItemStack lootItem = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta lootMeta = lootItem.getItemMeta();
        lootMeta.setDisplayName("LootCrate Key");
        lootItem.setItemMeta(lootMeta);

        ItemStack lightkitItem = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta lightkitMeta = lightkitItem.getItemMeta();
        lightkitMeta.setDisplayName("Lite Kit");
        lightkitItem.setItemMeta(lightkitMeta);

        ItemStack tankkitItem = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta tankkitMeta = tankkitItem.getItemMeta();
        tankkitMeta.setDisplayName("Tank Kit");
        tankkitItem.setItemMeta(tankkitMeta);

        ItemStack inviskitItem = new ItemStack(Material.POTION);
        inviskitItem.setData(new MaterialData((byte)8206));
        ItemMeta inviskitMeta = inviskitItem.getItemMeta();
        inviskitMeta.setDisplayName("Invis Kit");
        inviskitItem.setItemMeta(inviskitMeta);

        //Kits
        ItemStack swiftness = new ItemStack(Material.POTION);
        swiftness.setData(new MaterialData((byte)8226));
        ItemStack[] liteKit = new ItemStack[]{
            new ItemStack(Material.DIAMOND_SWORD),
            new ItemStack(Material.BOW),
            new ItemStack(Material.COOKED_BEEF, 16),
            new ItemStack(Material.WATER_BUCKET),
            new ItemStack(Material.FLINT_AND_STEEL),
            swiftness,
            new ItemStack(Material.IRON_HELMET),
            new ItemStack(Material.IRON_CHESTPLATE),
            new ItemStack(Material.IRON_LEGGINGS),
            new ItemStack(Material.IRON_BOOTS),
            new ItemStack(Material.ARROW, 64)
        };

        ItemStack health = new ItemStack(Material.POTION, 4);
        health.setData(new MaterialData((byte)8229));
        ItemStack[] tankKit = new ItemStack[]{
            new ItemStack(Material.IRON_SWORD),
            new ItemStack(Material.BOW),
            new ItemStack(Material.COOKED_BEEF, 64),
            health,
            new ItemStack(Material.DIAMOND_HELMET),
            new ItemStack(Material.DIAMOND_CHESTPLATE),
            new ItemStack(Material.DIAMOND_LEGGINGS),
            new ItemStack(Material.DIAMOND_BOOTS),
            new ItemStack(Material.ARROW, 64)
        };

        ItemStack[] invisKit = new ItemStack[]{
            new ItemStack(Material.DIAMOND_SWORD),
            new ItemStack(Material.COOKED_BEEF, 8),
            new ItemStack(Material.FLINT_AND_STEEL),
            new ItemStack(Material.LAVA_BUCKET, 2),
            inviskitItem,
            inviskitItem,
            inviskitItem
        };

        ItemStack[] resourcesSupply = new ItemStack[]{
            new ItemStack(Material.COOKED_BEEF, 64),
            new ItemStack(Material.COOKED_CHICKEN, 32),
            new ItemStack(Material.BREAD, 32),
            new ItemStack(Material.COAL_BLOCK, 16),
            new ItemStack(Material.IRON_BLOCK, 8),
            new ItemStack(Material.GOLD_BLOCK, 4),
            new ItemStack(Material.DIAMOND, 3),
            new ItemStack(Material.EMERALD_BLOCK)
        };

        //Listen for the Shop Device to be Accessed and Open the GUI
        addChild(listen(PlayerInteractEvent.class,
            (PlayerInteractEvent playerInteractEvent) -> {
                if(playerInteractEvent.hasItem()) {
                    if (playerInteractEvent.getItem().equals(shopItem)){
                        if(playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_AIR)){
                            Inventory gui = Bukkit.createInventory(playerInteractEvent.getPlayer(), InventoryType.CHEST, "Shop");
                            gui.setContents(glassWindow);
                            gui.setItem(0, shopItem);
                            gui.setItem(8, backItem);
                            gui.setItem(20, kitsItem);
                            gui.setItem(22, resourcesItem);
                            gui.setItem(24, lootItem);
                            playerInteractEvent.getPlayer().openInventory(gui);
                        }
                    }
                }
            }
        ));

        //Handle Interaction Inside the Shop
        addChild(listen(InventoryClickEvent.class,
            (InventoryClickEvent inventoryClickEvent) -> {

                Inventory kitsGui = Bukkit.createInventory(inventoryClickEvent.getWhoClicked(), InventoryType.CHEST, "Kits");
                kitsGui.setContents(glassWindow);
                kitsGui.setItem(0, shopItem);
                kitsGui.setItem(8, backItem);
                kitsGui.setItem(20, lightkitItem);
                kitsGui.setItem(22, tankkitItem);
                kitsGui.setItem(24, inviskitItem);

                if(inventoryClickEvent.getClickedInventory().getName().equalsIgnoreCase("Shop")){
                    if(inventoryClickEvent.getSlot() == 8 || inventoryClickEvent.getSlot() == 21 || inventoryClickEvent.getSlot() == 23){
                        if(inventoryClickEvent.getSlot() == 8)
                            inventoryClickEvent.getWhoClicked().closeInventory();
                        if(inventoryClickEvent.getSlot() == 20)
                            inventoryClickEvent.getWhoClicked().openInventory(kitsGui);
                        if(inventoryClickEvent.getSlot() == 22) {
                            inventoryClickEvent.getWhoClicked().closeInventory();
                            inventoryClickEvent.getInventory().addItem(resourcesSupply);
                            redisplayermoneyList.get(inventoryClickEvent.getWhoClicked().getUniqueId()).set(0,
                            (double)redisplayermoneyList.get(inventoryClickEvent.getWhoClicked().getUniqueId()).get(0) - 800.0);
                            inventoryClickEvent.getWhoClicked().sendMessage(msgFormat + "You purchased a Resource Supply for $800.");
                        }
                        if(inventoryClickEvent.getSlot() == 24){
                            inventoryClickEvent.getWhoClicked().closeInventory();
                            redisplayermoneyList.get(inventoryClickEvent.getWhoClicked().getUniqueId()).set(0,
                            (double)redisplayermoneyList.get(inventoryClickEvent.getWhoClicked().getUniqueId()).get(0) - 400.0);
                            inventoryClickEvent.getInventory().addItem(lootItem);
                            inventoryClickEvent.getWhoClicked().sendMessage(msgFormat + "You purchased a LootCrate Key for $400.");
                        }
                    } else
                        inventoryClickEvent.setCancelled(true);
                }
                if(inventoryClickEvent.getClickedInventory().getName().equalsIgnoreCase("Kits")){
                    if(inventoryClickEvent.getSlot() == 8 || inventoryClickEvent.getSlot() == 20 || inventoryClickEvent.getSlot() == 22 || inventoryClickEvent.getSlot() == 24){
                        if(inventoryClickEvent.getSlot() == 8)
                            inventoryClickEvent.getWhoClicked().closeInventory();
                        if(inventoryClickEvent.getSlot() == 20){
                            inventoryClickEvent.getWhoClicked().closeInventory();
                            inventoryClickEvent.getInventory().addItem(liteKit);
                            redisplayermoneyList.get(inventoryClickEvent.getWhoClicked().getUniqueId()).set(0,
                            (double)redisplayermoneyList.get(inventoryClickEvent.getWhoClicked().getUniqueId()).get(0) - 100.0);
                            inventoryClickEvent.getWhoClicked().sendMessage(msgFormat + "You purchased the Lite Kit for $100.");
                        }
                        if(inventoryClickEvent.getSlot() == 22){
                            inventoryClickEvent.getWhoClicked().closeInventory();
                            inventoryClickEvent.getInventory().addItem(tankKit);
                            redisplayermoneyList.get(inventoryClickEvent.getWhoClicked().getUniqueId()).set(0,
                            (double)redisplayermoneyList.get(inventoryClickEvent.getWhoClicked().getUniqueId()).get(0) - 80.0);
                            inventoryClickEvent.getWhoClicked().sendMessage(msgFormat + "You purchased the Tank Kit for $100.");
                        }
                        if(inventoryClickEvent.getSlot() == 24){
                            inventoryClickEvent.getWhoClicked().closeInventory();
                            inventoryClickEvent.getInventory().addItem(invisKit);
                            redisplayermoneyList.get(inventoryClickEvent.getWhoClicked().getUniqueId()).set(0,
                            (double)redisplayermoneyList.get(inventoryClickEvent.getWhoClicked().getUniqueId()).get(0) - 40.0);
                            inventoryClickEvent.getWhoClicked().sendMessage(msgFormat + "You purchased the Invis Kit for $40.");
                        }
                    } else
                        inventoryClickEvent.setCancelled(true);
                }
            }
        ));
    }
}
