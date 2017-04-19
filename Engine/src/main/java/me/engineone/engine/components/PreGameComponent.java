package me.engineone.engine.components;

import me.engineone.core.component.ParentComponent;
import me.engineone.engine.components.disablers.*;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

public class PreGameComponent extends ParentComponent {

    public PreGameComponent(Predicate<Player> players) {
        addChild(new BlockBreakDisabledComponent(players));
        addChild(new BlockPlaceDisabledComponent(players));
        addChild(new DropItemDisabledComponent(players));
        addChild(new HungerDisabledComponent(players));
        addChild(new MovementDisabledComponent(players));
        addChild(new PickUpDisabledComponent(players));
        addChild(new PvEDisabledComponent(players));
        addChild(new PvPDisabledComponent(players));
    }

    @Override
    public void disable() {
        super.disable();
    }
}