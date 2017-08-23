package me.engineone.thraxpvp;

import me.engineone.core.holder.MutableHolder;
import me.engineone.core.holder.MutablePartitionHolder;
import me.engineone.engine.Arena;
import me.engineone.engine.ScoreboardWrapper;
import me.engineone.engine.components.party.PartyComponent;
import me.engineone.engine.components.world.WorldComponent;
import me.engineone.engine.holder.PlayerHolder;
import me.engineone.engine.utilites.PartitionUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

import java.io.File;
import java.util.Arrays;

import static me.engineone.engine.components.disablers.Disable.*;

public class ThraxPvPArena extends Arena {

    public ThraxPvPArena() {
        RedissonClient redisson = Redisson.create();
        MutableHolder<Player> players = new PlayerHolder();

        //--World Protection--
        WorldComponent worldComponent = new WorldComponent(new File("Arena"));
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
        worldComponent.getLoadListeners().addAll(Arrays.asList(
                fireSpread(),
                mobSpawning(),
                mobGriefing(),
                naturalRegeneration(),
                randomTickSpeed()
        ));

        ScoreboardWrapper scoreboard = new ScoreboardWrapper();

        //--Phases--
        JoinPhase joinPhase = new JoinPhase(worldComponent);
        GamePhase gamePhase = new GamePhase();
        EndPhase endPhase = new EndPhase();

        onEnable(joinPhase::enable);
        onDisable(joinPhase::disable, gamePhase::disable, endPhase::disable);

        joinPhase.onComplete(gamePhase::enable);
        gamePhase.onComplete(endPhase::enable);

        //--Hooks--
        onEnable(() -> {

        });

        onDisable(() -> {

        });
    }
}
