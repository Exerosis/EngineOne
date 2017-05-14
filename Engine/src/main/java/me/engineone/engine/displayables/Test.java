package me.engineone.engine.displayables;

import org.bukkit.Material;

public class Test {

    public static void main(String[] args) {
        Window window = new Window();
        window.request().menu().like((main, player) -> {
            main.element().
                    icon(Material.ACACIA_DOOR).
                    text(0, "Line1").
                    title("Test Item");

            main.layout();
        });
    }
}
