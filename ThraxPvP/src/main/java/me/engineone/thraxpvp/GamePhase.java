package me.engineone.thraxpvp;

import me.engineone.core.holder.Holder;
import me.engineone.engine.Arena;
import me.engineone.engine.components.death.event.PlayerDeathEvent;
import org.bukkit.entity.Player;

import static me.engineone.engine.components.event.EventComponent.listen;

public class GamePhase extends Arena {
    public GamePhase(Holder<Player> players) {
        addChild(listen(PlayerDeathEvent.class, event -> {

        }));
    }
}