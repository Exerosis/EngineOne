package me.engineone.engine.items;

import me.engineone.core.component.Component;
import me.engineone.core.holder.MutateHolder;
import me.engineone.core.observable.Observable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public abstract class Item extends Component {
    //--Lore--
    public Item lore(String... lines) {
        lore(Arrays.asList(lines));
        return this;
    }

    public Item lore(MutateHolder<String> lines) {
        List<String> list = new ArrayList<>();
        lines.onAdded(line -> {
            list.add(line);
            lore(list);
        });
        lines.onRemoved(line -> {
            list.remove(line);
            lore(list);
        });
        return this;
    }

    public abstract void onUpdate(Consumer<ItemStack> listener);

    public abstract Item lore(Iterable<String> lines);

    //--Flags--
    public Item flags(ItemFlag... flags) {
        flags(Arrays.asList(flags));
        return this;
    }

    public Item flags(MutateHolder<ItemFlag> flags) {
        List<ItemFlag> list = new ArrayList<>();
        flags.onAdded(line -> {
            list.add(line);
            flags(list);
        });
        flags.onRemoved(line -> {
            list.remove(line);
            flags(list);
        });
        return this;
    }

    public abstract Item flags(Iterable<ItemFlag> flags);

    //--Enchantments--
    public Item enchantments(ItemEnchantment... enchantments) {
        enchantments(Arrays.asList(enchantments));
        return this;
    }

    public Item enchantments(MutateHolder<ItemEnchantment> enchantments) {
        List<ItemEnchantment> list = new ArrayList<>();
        enchantments.onAdded(line -> {
            list.add(line);
            enchantments(list);
        });
        enchantments.onRemoved(line -> {
            list.remove(line);
            enchantments(list);
        });
        return this;
    }

    public abstract Item enchantments(Iterable<ItemEnchantment> enchantments);

    //--Name--
    public abstract Item name(String name);

    public Item name(Observable<String> name) {
        name.get(this::name);
        return this;
    }

    //--Amount--
    public abstract Item amount(int amount);

    public Item amount(Observable<Integer> amount) {
        amount.get(this::amount);
        return this;
    }

    //--Type--
    public abstract Item type(Material type);

    public Item type(Observable<Material> type) {
        type.get(this::type);
        return this;
    }

    //--Data--
    public abstract Item data(byte data);

    public Item data(Observable<Byte> data) {
        data.get(this::data);
        return this;
    }
}
