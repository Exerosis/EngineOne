package me.engineone.engine.utilites;

import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

import static com.comphenix.protocol.ProtocolLibrary.getProtocolManager;

public final class PacketUtils {
    private PacketUtils() {

    }

    public static void sendSilently(Player player, PacketContainer packet) {
        try {
            getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException ignored) {
        }
    }

}
