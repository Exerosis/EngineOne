package me.engineone.thraxpvp;

import org.bukkit.entity.Player;

import java.net.URL;
import java.util.function.Predicate;

public interface ThraxArenaPacket {
    Predicate<Player> players();

    String worldName();

    URL worldURL();
}