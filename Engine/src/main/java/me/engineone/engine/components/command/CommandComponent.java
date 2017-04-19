package me.engineone.engine.components.command;

import me.engineone.engine.components.base.ListenerComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

//TODO addChild arg listeners
public class CommandComponent extends ListenerComponent {
    Map<Predicate<String>, BiConsumer<Player, String[]>> playerCommands = new HashMap<>();
    Map<Predicate<String>, Consumer<String[]>> consoleCommands = new HashMap<>();

    public CommandComponent() {
    }

    public Predicate<String> onPlayerCommand(Predicate<String> filter, BiConsumer<Player, String[]> listener) {
        if (playerCommands.containsKey(filter))
            playerCommands.remove(filter);
        else
            playerCommands.put(filter, listener);
        return filter;
    }

    public Predicate<String> onConsoleCommand(Predicate<String> filter, Consumer<String[]> listener) {
        if (consoleCommands.containsKey(filter))
            consoleCommands.remove(filter);
        else
            consoleCommands.put(filter, listener);
        return filter;
    }

    @EventHandler
    private void onCommand(PlayerCommandPreprocessEvent event) {
        playerCommands.forEach((key, value) -> {
            if (key.test(event.getMessage().substring(1)))
                value.accept(event.getPlayer(), event.getMessage().split(" "));
        });
    }
}
