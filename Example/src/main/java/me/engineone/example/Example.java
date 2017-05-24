package me.engineone.example;

import me.engineone.core.completeable.Phase;
import me.engineone.core.holder.MutateHolder;
import me.engineone.engine.components.enders.PlayerCountWinCondition;
import me.engineone.engine.holder.OnlinePlayerHolder;
import me.engineone.example.games.runner.RunnerGame;
import me.engineone.example.games.world.LobbyWorldComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by BinaryBench on 4/19/2017.
 */
public class Example extends JavaPlugin {


    private MutateHolder<Player> onlinePlayers;
    private ScheduledExecutorService scheduler;
    private LobbyWorldComponent lobbyWorld;

    @Override
    public void onEnable() {
        System.out.println("EngineOne example plugin is alive and kicking!");

        onlinePlayers = new OnlinePlayerHolder();
        scheduler = Executors.newScheduledThreadPool(10);
        lobbyWorld = new LobbyWorldComponent();
        lobbyWorld.enable();

        startGame();

    }

    public void startGame() {
        Phase game = new RunnerGame(onlinePlayers, scheduler, lobbyWorld);
        game.enable();
        game.onComplete(() -> {
            if (game.isEnabled())
                game.disable();
            startGame();
        });
    }

}
