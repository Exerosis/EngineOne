package me.engineone.engine.displayables.menu;

import org.bukkit.Material;

import java.util.function.Consumer;

public interface Element {
    Element number(int number);

    Element title(String title);

    Element icon(Material material);

    Element text(int index, String text);

    Element page(Consumer<Page> description);

    Element onClick(Runnable listener);

    Element onShiftClick(Runnable listener);
}