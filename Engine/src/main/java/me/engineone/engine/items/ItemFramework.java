package me.engineone.engine.items;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ItemFramework {

    public static CustomItem like(BiConsumer<Player, Item> description) {
        Item item = new Item() {
            public Material type;
            private final List<Consumer<ItemStack>> listeners = new ArrayList<>();
            private ItemStack stack = null;
            private int amount = 1;

            @Override
            public void onUpdate(Consumer<ItemStack> listener) {
                listeners.add(listener);
            }

            private Item update() {
                if (stack != null)
                    listeners.forEach(listener -> listener.accept(stack));
                return this;
            }

            @Override
            public Item lore(Iterable<String> lines) {
                if (stack != null) {
                    ItemMeta meta = stack.getItemMeta();
                    meta.setLore(Lists.newArrayList(lines));
                    stack.setItemMeta(meta);
                }
                return update();
            }

            @Override
            public Item flags(Iterable<ItemFlag> flags) {
                if (stack != null) {
                    ItemMeta meta = stack.getItemMeta();
                    flags.forEach(meta::addItemFlags);
                    stack.setItemMeta(meta);
                }
                return update();
            }

            @Override
            public Item enchantments(Iterable<ItemEnchantment> enchantments) {
                return update();
            }

            @Override
            public Item name(String name) {
                if (stack != null) {
                    ItemMeta meta = stack.getItemMeta();
                    meta.setDisplayName(name);
                    stack.setItemMeta(meta);
                }
                return update();
            }

            @Override
            public Item amount(int amount) {
                if (stack != null)
                    stack.setAmount(amount);
                return this;
            }

            @Override
            public Item type(Material type) {
                if (this.type == null) {
                    stack = new ItemStack(type, amount);
                    enable();
                }
                this.type = type;
                return update();
            }

            @Override
            public Item data(byte data) {
                if (stack != null)
                    stack.setData(new MaterialData(type, data));
                return update();
            }
        };

        return (player, slot) -> item.onUpdate(itemStack -> player.getInventory().setItem(slot, itemStack));
    }

    public interface CustomItem {
        void giveTo(Player player, int slot);
    }
}