package me.engineone.engine.displayables;

import me.engineone.core.holder.MutableHolder;
import me.engineone.engine.displayables.menu.Page;
import me.engineone.engine.displayables.menu.PageDisplayable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Window {
    private final List<WindowRequest> request = new ArrayList<>();
    private MutableHolder<Player> players;

    public Window(MutableHolder<Player> players) {
        this.players = players;
        players.onAdd(player -> {

        });
    }

    public Window() {

    }

    public MenuRequest menu() {
        return new MenuRequest();
    }


    private interface WindowRequest {
    }

    //--Requests--

    public class MenuRequest implements WindowRequest {
        private BiConsumer<Page, Player> description;

        MenuRequest() {
            PageDisplayable displayable = new PageDisplayable();
            players.onAdd(player -> {
                if (description == null)
                    throw new IllegalArgumentException("Description Must Not Be Null!");
                description.accept(displayable, player);

                Bukkit.createInventory(null, 18, displayable)



                displayable.enable();
            });
            players.onRemove(player -> {
                displayable.disable();
            });
        }

        public MenuRequest like(BiConsumer<Page, Player> description) {
            this.description = description;
            return this;
        }
    }
}
