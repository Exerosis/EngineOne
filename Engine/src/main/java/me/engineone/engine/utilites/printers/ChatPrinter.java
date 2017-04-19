package me.engineone.engine.utilites.printers;

import me.engineone.core.printer.DestinationPrinter;
import org.bukkit.Bukkit;

/**
 * Created by Exerosis.
 */
public class ChatPrinter extends DestinationPrinter {

    @Override
    protected void _print(Object message) {
        Bukkit.broadcastMessage(message.toString());
    }
}
