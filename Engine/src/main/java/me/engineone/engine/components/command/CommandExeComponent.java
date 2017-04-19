package me.engineone.engine.components.command;

import me.engineone.engine.components.base.ListenerComponent;
import me.engineone.engine.components.client.ClientGetComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandExeComponent extends ListenerComponent {

    private final CommandLoadComponent component;
    private final ClientGetComponent clientGetComponent;

    public CommandExeComponent(CommandLoadComponent component, ClientGetComponent clientGetComponent) {
        this.component = component;
        this.clientGetComponent = clientGetComponent;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String commandName = event.getMessage().substring(1);
        String[] args = null;

        if (commandName.contains(" ")) {
            commandName = commandName.split(" ")[0];
            args = event.getMessage().substring(event.getMessage().indexOf(' ') + 1).split(" ");
        }

        ICommand command = component.getCommand(commandName.toLowerCase());
        if (command != null) {
            event.setCancelled(true);
            if (!clientGetComponent.getClient(player).getRank().has(command.getRequiredRank())) {
                player.sendMessage(ChatColor.RED + "{1}You can't do this!");
                return;
            }


            command.execute(player, args);
        }
    }

}
