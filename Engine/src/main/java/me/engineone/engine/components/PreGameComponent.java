package me.engineone.engine.components;

import me.engineone.core.component.ParentComponent;
import me.engineone.core.holder.CollectionHolder;
import me.engineone.engine.components.disablers.*;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

public class PreGameComponent extends ParentComponent {

    public PreGameComponent(CollectionHolder<Player> players) {
        addChild(Disablers.blockBreak(players));
        addChild(Disablers.blockPlace(players));
        addChild(Disablers.dropItem(players));
        addChild(Disablers.itemPickup(players));

        addChild(Disablers.hunger(players));
        addChild(new MovementDisabledComponent(players));

        addChild(Disablers.pvp(players));
        addChild(Disablers.damage(players));
    }

    @Override
    public void disable() {
        super.disable();
    }
}