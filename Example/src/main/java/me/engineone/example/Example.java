package me.engineone.example;

import me.engineone.core.holder.MutateHolder;
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


    private RunnerGame game;

    @Override
    public void onEnable() {
        System.out.println("EngineOne example plugin is alive and kicking!");

        MutateHolder<Player> onlinePlayers = new OnlinePlayerHolder();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
        LobbyWorldComponent lobbyWorld = new LobbyWorldComponent();
        lobbyWorld.enable();


        this.game = new RunnerGame(onlinePlayers, scheduler, lobbyWorld);

        game.onComplete(() -> {
            System.err.println("PreDisable: " + game.isComplete());
            game.disable();
            System.err.println("PreEnable: " + game.isComplete());
            game.enable();
            System.err.println("PostEnable: " + game.isComplete());
        });
        game.enable();
    }

}
