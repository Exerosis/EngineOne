package me.engineone.engine.components;

import me.engineone.core.component.ParentComponent;
import me.engineone.core.holder.CollectionHolder;
import me.engineone.engine.components.disablers.*;
import org.bukkit.entity.Player;

public class PreGameComponent extends ParentComponent {

    public PreGameComponent(CollectionHolder<Player> players) {
        addChild(Disable.blockBreak(players));
        addChild(Disable.blockPlace(players));
        addChild(Disable.dropItem(players));
        addChild(Disable.itemPickup(players));

        addChild(Disable.hunger(players));
        addChild(new MovementDisabledComponent(players));

        addChild(Disable.pvp(players));
        addChild(Disable.damage(players));
    }

    @Override
    public void disable() {
        super.disable();
    }
}