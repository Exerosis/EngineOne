package me.engineone.engine.displayables.alpha.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class PageDisplayable {
    private BiConsumer<Page, Player> description;

    public PageDisplayable like(BiConsumer<Page, Player> description) {
        this.description = description;
        return this;
    }


    private void display(Player player) {
        List<Element> elements = new ArrayList<>();
        Page page = new Page() {
            @Override
            public Element element() {
                Element element = new Element() {
                };
                elements.add(element);
                return element;
            }

            @Override
            public Page setTitle(CharSequence title) {
                update(player, String.valueOf(title));
                return this;
            }
        };
        description.accept(page, player);
        //TODO Enable stuff here.
    }


    private static Class<?> getNmsClass(String nmsClassName) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().substring(23) + "." + nmsClassName);
    }

    public static void update(final Player p, String title) {
        try {
            Object handle = p.getClass().getMethod("getHandle").invoke(p);
            Object message = getNmsClass("ChatMessage").getConstructor(String.class, Object[].class).newInstance(title, new Object[0]);
            Object container = handle.getClass().getField("activeContainer").get(handle);
            Object windowId = container.getClass().getField("windowId").get(container);
            Object packet = getNmsClass("PacketPlayOutOpenWindow").getConstructor(Integer.TYPE, String.class, getNmsClass("IChatBaseComponent"), Integer.TYPE).newInstance(windowId, "minecraft:chest", message, p.getOpenInventory().getTopInventory().getSize());
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNmsClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        p.updateInventory();
    }
}