package me.engineone.engine.components.disablers;

        import me.engineone.core.component.Component;
        import me.engineone.engine.components.event.EventComponent;
        import org.bukkit.block.Block;
        import org.bukkit.entity.Player;
        import org.bukkit.event.block.BlockBreakEvent;
        import org.bukkit.material.MaterialData;

        import java.util.function.Predicate;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public class Disablers {


    static EventComponent blockBreakEvent(Predicate<Player> players, Predicate<Block> filter) {
        return EventComponent.listen(BlockBreakEvent.class, event -> {
            if (players.test(event.getPlayer()) && filter.test(event.getBlock()))
                event.setCancelled(true);
        });
    }

}
