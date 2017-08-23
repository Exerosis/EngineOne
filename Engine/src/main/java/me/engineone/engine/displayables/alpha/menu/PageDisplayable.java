package me.engineone.engine.displayables.alpha.menu;

import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

public class PageDisplayable {
    private BiConsumer<Page, Player> description;

    public PageDisplayable like(BiConsumer<Page, Player> description) {
        this.description = description;
        return this;
    }


    private void display(Player player) {
        PageImplementation page = new PageImplementation(player);
        description.accept(page, player);
        page.enable();
    }
}