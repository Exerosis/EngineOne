package me.engineone.example.games.runner;

import com.comphenix.protocol.PacketType;
import me.engineone.core.completeable.ParentPhase;
import me.engineone.core.component.AddToListComponent;
import me.engineone.core.component.Component;
import me.engineone.core.holder.MutateHolder;
import me.engineone.engine.components.VoidKiller;
import me.engineone.engine.components.disablers.Disablers;
import me.engineone.engine.components.enders.LMSVictoryCondition;
import me.engineone.engine.components.event.EventComponent;
import me.engineone.engine.components.spectate.GameModeSpectatorComponent;
import me.engineone.engine.components.world.WorldComponent;
import me.engineone.engine.utilites.ServerUtil;
import me.engineone.engine.components.countdown.CountdownPhase;
import me.engineone.engine.components.countdown.HolderCountCountdownPhase;
import me.engineone.example.games.runner.components.RunnerComponent;
import me.engineone.example.games.world.GameWorldComponent;
import me.engineone.example.games.world.LobbyWorldComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.spigotmc.event.entity.EntityMountEvent;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by BinaryBench on 5/3/2017.
 */
public class RunnerGame extends ParentPhase {
    public static final String NAME = "Runner";

    public RunnerGame(MutateHolder<Player> allPlayers, ScheduledExecutorService scheduler, LobbyWorldComponent lobbyWorldComponent) {
        super();

        // Spectators
        GameModeSpectatorComponent spectators = new GameModeSpectatorComponent();
        onEnable(spectators::clear);

        MutateHolder<Player> gamePlayers = allPlayers.difference(spectators);

        // World
        WorldComponent worldComponent = new GameWorldComponent(NAME);
        addChild(worldComponent);

        worldComponent.onLoad(Disablers.mobGriefing());
        worldComponent.onLoad(Disablers.mobSpawning());
        worldComponent.onLoad(Disablers.fireSpread());

        // Lobby
        HolderCountCountdownPhase lobbyPhase = new HolderCountCountdownPhase(scheduler, 10, 1, 1, allPlayers);
        lobbyPhase.onCount(count -> {
            if (count <= 5 || count%10 == 0)
                ServerUtil.broadcast(ChatColor.GREEN + "Lobby ending in " + ChatColor.DARK_GREEN + count + ChatColor.GREEN + " seconds!", allPlayers);
        });
        lobbyPhase.onWaiting((playerCount, startThresh) ->
            ServerUtil.broadcast(ChatColor.GREEN + "Waiting for players... (" + playerCount + "/" + startThresh + ")!", allPlayers)
        );

        lobbyPhase.onEnable(() -> {
            allPlayers.forEach(lobbyWorldComponent::spawnPlayer);
        });

        lobbyPhase.addChild(
                Disablers.blockPlace(allPlayers),
                Disablers.blockPlace(allPlayers),

                Disablers.dropItem(allPlayers),
                Disablers.itemPickup(allPlayers),

                Disablers.hunger(allPlayers),
                Disablers.damage(allPlayers),

                new AddToListComponent<>(allPlayers.getAddListeners(), lobbyWorldComponent::spawnPlayer)
        );

        Component joinSpectate = new AddToListComponent<>(allPlayers.getAddListeners(), spectators::add);
        Component deathSpectate = EventComponent.listen(PlayerDeathEvent.class, event -> spectators.add(event.getEntity()));



        // Pre Game
        CountdownPhase preGamePhase = new CountdownPhase(scheduler, 5);

        preGamePhase.onCount(count ->
            ServerUtil.broadcast(ChatColor.GREEN + "Game starting in " + ChatColor.DARK_GREEN + count + ChatColor.GREEN + " seconds!", allPlayers)
        );

        preGamePhase.addChild(
                Disablers.blockPlace(allPlayers),
                Disablers.blockBreak(allPlayers),

                Disablers.dropItem(allPlayers),
                Disablers.itemPickup(allPlayers),

                Disablers.hunger(allPlayers),
                Disablers.damage(allPlayers),

                joinSpectate,
                deathSpectate
        );

        preGamePhase.onEnable(() -> allPlayers.forEach(player ->
            player.teleport(worldComponent.getWorld().getSpawnLocation())
        ));


        // Game
        LMSVictoryCondition gamePhase = new LMSVictoryCondition(gamePlayers, allPlayers);
        gamePhase.addChild(
                Disablers.blockPlace(allPlayers),
                Disablers.blockBreak(allPlayers),

                Disablers.dropItem(allPlayers),
                Disablers.itemPickup(allPlayers),

                Disablers.hunger(allPlayers),
                Disablers.damage(allPlayers),

                joinSpectate,
                deathSpectate,

                new RunnerComponent(gamePlayers, scheduler),
                Disablers.fallingBlocks(worldComponent),

                new VoidKiller(gamePlayers)
        );

        gamePhase.addChild(EventComponent.listen(EntityMountEvent.class, event -> {
            if (event.getEntity() instanceof Player && gamePlayers.test((Player) event.getEntity())) {
                event.setCancelled(true);
            }
        }));


        // Post Game
        CountdownPhase postGamePhase = new CountdownPhase(scheduler, 5);
        postGamePhase.onCount(count -> allPlayers.forEach(player -> player.sendMessage(ChatColor.GREEN + "Game ending in " + ChatColor.DARK_GREEN + count + ChatColor.GREEN + " seconds!")));
        postGamePhase.addChild(
                Disablers.blockPlace(allPlayers),
                Disablers.blockBreak(allPlayers),

                Disablers.dropItem(allPlayers),
                Disablers.itemPickup(allPlayers),

                Disablers.hunger(allPlayers),
                Disablers.damage(allPlayers),

                Disablers.fallingBlocks(worldComponent),

                joinSpectate,
                deathSpectate
        );

        onEnable(() -> {
            //noinspection Convert2MethodRef
            lobbyPhase.enable();
        });
        lobbyPhase.onComplete(() -> {
            lobbyPhase.disable();
            preGamePhase.enable();
        });
        preGamePhase.onComplete(() -> {
            preGamePhase.disable();
            gamePhase.enable();
        });
        gamePhase.onComplete(() -> {
            gamePhase.disable();
            postGamePhase.enable();
        });
        postGamePhase.onComplete(() -> {
            postGamePhase.disable();
            complete();
        });

        onDisable(() -> {
            lobbyPhase.disable();
            preGamePhase.disable();
            gamePhase.disable();
            postGamePhase.disable();
        });

    }
}
