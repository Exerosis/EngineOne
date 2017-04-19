package me.engineone.engine.holder.event;

import me.engineone.engine.holder.PlayerHolder;
import org.bukkit.entity.Player;

public class PlayerEnterHolderEvent extends PlayerHolderEvent {

    public PlayerEnterHolderEvent(PlayerHolder holder, Player player) {
        super(holder, player);
    }

}
