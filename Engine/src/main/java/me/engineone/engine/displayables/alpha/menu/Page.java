package me.engineone.engine.displayables.alpha.menu;

import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.engineone.engine.displayables.alpha.Displayable;

public interface Page extends Displayable {
    Element element();

    Page setTitle(CharSequence title);

    Page setTitle(WrappedChatComponent title);

    Page setRows(int rows);

    enum Type {
        CRAFTING, FURNACE, CONTAINER, CHEST, DISPENSER, ENCHANTING, BREWING, VILLAGER, BEACON, ANVIL, HOPPER, DROPPER, SHULKER, HORSE;
    }
}
