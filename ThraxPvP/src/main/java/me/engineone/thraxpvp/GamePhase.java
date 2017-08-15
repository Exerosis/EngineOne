package me.engineone.thraxpvp;

import me.engineone.core.completeable.ParentPhase;
import me.engineone.core.holder.MutateHolder;
import me.engineone.engine.components.scheduler.Scheduler;
import me.engineone.engine.components.spectate.GameModeSpectatorComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import static me.engineone.engine.components.event.EventComponent.listen;

public class GamePhase extends ParentPhase {
    public GamePhase(GameModeSpectatorComponent spectatorComponent, MutateHolder<Player> players) {
        //Win Cons
        addChild(listen(PlayerDeathEvent.class, event -> {
            if (!spectatorComponent.contains(event.getEntity()))
                spectatorComponent.add(event.getEntity());
            if (players.size() <= 1)
                complete();
        }));

        addChild(Scheduler.every(30).minutes().forNext(1).times().synchronously().run(this::complete));

        onEnable(() -> players.forEach(player -> player.getInventory().addItem(new ItemStack(Material.IRON_SWORD))));
    }
}