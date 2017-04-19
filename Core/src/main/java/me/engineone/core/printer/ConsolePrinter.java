package me.engineone.core.printer;

/**
 * Created by Exerosis.
 */
public class ConsolePrinter extends DestinationPrinter {

    @Override
    protected void _print(Object message) {
        System.out.println(message);
    }
}
