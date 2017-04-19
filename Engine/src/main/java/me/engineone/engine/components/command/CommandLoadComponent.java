package me.engineone.engine.components.command;

import me.engineone.core.component.Component;
import me.engineone.engine.components.client.ClientGetComponent;

import java.util.Map;
import java.util.TreeMap;

public class CommandLoadComponent extends Component {
    private final Map<String, ICommand> commands = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public CommandLoadComponent(ClientGetComponent clientGetComponent) {
        //addChild commands here
    }

    public void addCommand(ICommand command) {
        command.getAliases().forEach(alias -> commands.put(alias.toLowerCase(), command));
    }

    public void removeCommand(ICommand command) {
        command.getAliases().forEach(alias -> commands.remove(alias.toLowerCase()));
    }

    public ICommand getCommand(String command) {
        return commands.get(command);
    }

    public Map<String, ICommand> getCommands() {
        return commands;
    }
}
