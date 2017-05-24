package me.engineone.engine.components;

import me.engineone.core.component.Component;
import me.engineone.core.holder.MutateHolder;
import org.bukkit.entity.Player;

/**
 * Created by BinaryBench on 5/23/2017.
 */
public class JoinMessageComponent extends Component {
    public JoinMessageComponent(MutateHolder<Player> players, String message) {
        onEnable(() -> players.forEach(player -> player.sendMessage(message)));
        players.onAdded(player -> player.sendMessage(message));
    }
}
