package me.engineone.engine.holder.event;

import me.engineone.engine.holder.PlayerHolder;
import org.bukkit.entity.Player;

public class PlayerExitHolderEvent extends PlayerHolderEvent {

    public PlayerExitHolderEvent(PlayerHolder holder, Player player) {
        super(holder, player);
    }

}
