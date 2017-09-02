package me.engineone.thraxpvp;

import me.engineone.core.holder.MutateHolder;
import me.engineone.engine.Arena;
import me.engineone.engine.components.spectate.GameModeSpectatorComponent;
import me.engineone.engine.components.world.WorldComponent;
import me.engineone.thraxpvp.teamdeathmatch.EndPhase;
import me.engineone.thraxpvp.teamdeathmatch.GamePhase;
import me.engineone.thraxpvp.teamdeathmatch.PreGamePhase;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

import static me.engineone.engine.components.disablers.Disable.*;

public class ThraxBaseGamemode extends Arena {

    public ThraxBaseGamemode(WorldComponent worldComponent, MutateHolder<Player> players) {
        RedissonClient redisson = Redisson.create();
        GameModeSpectatorComponent spectatorComponent = new GameModeSpectatorComponent();
        MutateHolder<Player> gamePlayers = players.difference(spectatorComponent);

        //--World Protection--
        addChild(
                damage(players),
                bowShoot(players),
                dropItem(players),
                itemPickup(players),
                fallDamage(players),
                pvp(players),
                blockBreak(player -> !player.getGameMode().equals(GameMode.CREATIVE) && players.test(player)),
                blockPlace(player -> !player.getGameMode().equals(GameMode.CREATIVE) && players.test(player)),
                hunger(players)
        );

        //TODO make these all children instead.

        //--Phases--
        PreGamePhase preGamePhase = new PreGamePhase(gamePlayers);
        GamePhase gamePhase = new GamePhase(spectatorComponent, gamePlayers);
        EndPhase endPhase = new EndPhase();

        spectatorComponent.onAdd(player -> {
            //TODO announce the player is dead!
        });

        //--Hooks--
        onEnable(() -> {
            World world = worldComponent.getWorld();
            players.forEach(player -> player.teleport(world.getSpawnLocation().add(0, 2, 0)));
            fireSpread().accept(world);
            mobSpawning().accept(world);
            mobGriefing().accept(world);
            naturalRegeneration().accept(world);
            randomTickSpeed().accept(world);
        }, preGamePhase::enable);

        onDisable(() -> {

        }, preGamePhase::disable, gamePhase::disable, endPhase::disable);

        preGamePhase.onComplete(gamePhase::enable);
        gamePhase.onComplete(endPhase::enable);
    }
}
