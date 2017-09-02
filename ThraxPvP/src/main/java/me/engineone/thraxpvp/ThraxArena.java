package me.engineone.thraxpvp;

import me.engineone.core.completeable.ParentPhase;
import me.engineone.core.holder.MutableHolder;
import me.engineone.core.holder.MutateHolder;
import me.engineone.engine.Arena;
import me.engineone.engine.components.world.WorldComponent;
import me.engineone.engine.holder.OnlinePlayerHolder;
import org.bukkit.entity.Player;

import java.io.File;

public class ThraxArena extends Arena {
    public static final File WORLDS_DIRECTORY = new File("Worlds");

    public ThraxArena(MutableHolder<Player> spectators, ThraxArenaPacket packet) {
        //--Holders--
        MutateHolder<Player> allPlayers = new OnlinePlayerHolder();

        MutateHolder<Player> gamePlayers = new StaticPartitionHolder<>(allPlayers, packet.players());

        //--Phases--
        ParentPhase joinPhase = new ParentPhase();
        ParentPhase gamePhase = new ParentPhase();
        ParentPhase endPhase = new ParentPhase();

        //--Components--
        WorldComponent worldComponent = addChild(new WorldComponent(WORLDS_DIRECTORY, packet.worldName(), packet.worldURL()));

        //--Hooks--
        worldComponent.onLoad(gamePhase::enable);
        gamePhase.onComplete(endPhase::enable);
        endPhase.onComplete(worldComponent::disable);
        worldComponent.onLoad(this::disable);
        onDisable(endPhase::disable, gamePhase::disable);

        allPlayers.onAdd(player -> {
            if (!joinPhase.isEnabled() || !packet.players().test(player))
                spectators.add(player);
        });

        //--Gamemode--


        //--Base Gamemode--
        addChild(new ThraxBaseGamemode(worldComponent, gamePlayers)).onComplete(this::complete);
    }
}