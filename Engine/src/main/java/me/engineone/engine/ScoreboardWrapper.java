package me.engineone.engine;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;

import static me.engineone.engine.utilites.PacketUtils.sendSilently;

public class ScoreboardWrapper {
    ProtocolManager manager = ProtocolLibrary.getProtocolManager();

    public void showScoreboard(Player player, String name) {
        PacketContainer packet = manager.createPacket(PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE);
        packet.getBytes().write(0, (byte) 2);
        packet.getStrings().write(0, name);
        sendSilently(player, packet);
    }

    public void addLine(Player player, String line, String value) {
        PacketContainer packet = manager.createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);
        packet.getStrings().write(0, line);
        packet.getBytes().write(0, (byte) 0);

        packet.getStrings().write(2, value.substring(0, 15));
        packet.getStrings().write(1, value.substring(16, 31));
        packet.getStrings().write(3, value.substring(32, 47));
        sendSilently(player, packet);
    }

    public void editLine(Player player, String line, String value) {
        PacketContainer packet = manager.createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);
        packet.getStrings().write(0, line);
        packet.getBytes().write(0, (byte) 1);

        packet.getStrings().write(2, value.substring(0, 15));
        packet.getStrings().write(1, value.substring(16, 31));
        packet.getStrings().write(3, value.substring(32, 47));
        sendSilently(player, packet);
    }
}