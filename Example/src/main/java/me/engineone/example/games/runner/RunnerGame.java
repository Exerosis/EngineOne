package me.engineone.example.games.runner;

import me.engineone.core.completeable.ParentPhase;
import me.engineone.core.holder.MutateHolder;
import me.engineone.engine.components.disablers.Disablers;
import me.engineone.engine.components.enders.LMSVictoryCondition;
import me.engineone.engine.components.spectate.GameModeSpectatorComponent;
import me.engineone.engine.components.world.WorldComponent;
import me.engineone.example.games.world.GameWorldComponent;
import org.bukkit.entity.Player;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by BinaryBench on 5/3/2017.
 */
public class RunnerGame extends ParentPhase {
    public static final String NAME = "Runner";

    public RunnerGame(MutateHolder<Player> allPlayers, ScheduledExecutorService scheduler) {
        super();

        MutateHolder<Player> spectators = new GameModeSpectatorComponent();
        MutateHolder<Player> gamePlayers = allPlayers.difference(spectators);

        WorldComponent worldComponent = new GameWorldComponent(NAME);

        worldComponent.getLoadListeners().add(Disablers.mobGriefing());
        worldComponent.getLoadListeners().add(Disablers.mobSpawning());
        worldComponent.getLoadListeners().add(Disablers.fireSpread());


        LMSVictoryCondition gamePhase = new LMSVictoryCondition(gamePlayers, allPlayers);




    }
}
