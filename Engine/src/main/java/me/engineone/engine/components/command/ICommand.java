package me.engineone.engine.components.command;

import me.engineone.engine.components.client.ClientRank;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface ICommand {

    /**
     * Get the command required rank.
     *
     * @return the required rank
     *
     */
    ClientRank getRequiredRank();

    /**
     * Get the command aliases.
     *
     * @return Collection<String>
     */
    Collection<String> getAliases();

    /**
     * Execute the command.
     *
     * @param player
     * @param args
     */
    void execute(Player player, String[] args);

}
