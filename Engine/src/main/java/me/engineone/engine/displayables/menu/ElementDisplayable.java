package me.engineone.engine.displayables.menu;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ElementDisplayable implements Element {
    private final List<String> lore = new ArrayList<String>() {
        @Override
        public void add(int index, String element) {
            if (index >= 0 && index < size())
                super.add(index, element);
            else
                for (int i = 0; i < index - size(); i++)
                    super.add("");
            super.add(element);
        }
    };
    private final List<Runnable> onClickListeners = new ArrayList<>();
    private final List<Runnable> onUpdateListeners = new ArrayList<>();
    private String title;
    private Material material;
    private int number;

    @Override
    public Element number(int number) {
        this.number = number;
        return this;
    }

    @Override
    public Element title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public Element icon(Material material) {
        this.material = material;
        return this;
    }

    @Override
    public Element text(int index, String text) {
        lore.add(index, text);
        return this;
    }

    @Override
    public Element page(Consumer<Page> description) {
        return this;
    }

    @Override
    public Element onClick(Runnable listener) {
        onClickListeners.add(listener);
        return this;
    }

    public void click() {
        onClickListeners.forEach(Runnable::run);
    }

    public void update() {
        onUpdateListeners.forEach(Runnable::run);
    }

    public void onUpdate(Runnable listener) {
        onUpdateListeners.add(listener);
    }

    public ItemStack toItemStack() {
        ItemStack item = new ItemStack(material, number);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(title);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}