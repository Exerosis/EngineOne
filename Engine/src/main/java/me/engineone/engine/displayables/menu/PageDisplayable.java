package me.engineone.engine.displayables.menu;

import me.engineone.core.component.Component;
import me.engineone.engine.components.scheduler.SchedulerComponent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public class PageDisplayable extends Component implements Page {
    private final List<ElementDisplayable> elements = new ArrayList<>();
    private String title;
    private Inventory inventory;

    @Override
    public Element element() {
        new SchedulerComponent().every().day().run(() -> {

        }).forNext(10).times().synchronously();
        ElementDisplayable element = new ElementDisplayable();
        elements.add(element);
        return element;
    }

    @Override
    public Page layout(BiConsumer<Inventory, List<Element>> layout) {

        return this;
    }

    @Override
    public Page title(String title) {
        this.title = title;
        return this;
    }


    public Inventory toInventory(){
        layout(inventory)
        return inventory;
    }
}