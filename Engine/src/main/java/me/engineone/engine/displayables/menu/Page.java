package me.engineone.engine.displayables.menu;

import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.function.BiConsumer;

public interface Page {
    Element element();

    //TODO Figure out Layouts.
    Page layout(BiConsumer<Inventory, List<Element>> layout);

    Page title(String title);
}