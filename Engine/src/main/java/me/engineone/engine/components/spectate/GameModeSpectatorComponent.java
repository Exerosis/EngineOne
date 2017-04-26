package me.engineone.engine.components.spectate;

import me.engineone.core.component.CollectionHolderComponent;
import me.engineone.engine.utilites.PlayerUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.function.Consumer;


public class GameModeSpectatorComponent extends CollectionHolderComponent<Player> {

    public GameModeSpectatorComponent() {
        onAdd(this::enableSpectate);
        onRemove(this::disableSpectate);
    }

    public void enableSpectate(Player player) {
        PlayerUtil.resetPlayer(player);
        player.setGameMode(GameMode.SPECTATOR);
    }

    public void disableSpectate(Player player) {

    }

}