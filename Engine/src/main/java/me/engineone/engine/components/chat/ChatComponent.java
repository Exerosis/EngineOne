package me.engineone.engine.components.chat;


import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import me.engineone.core.holder.Holder;
import me.engineone.engine.components.base.ListenerHolderComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static me.engineone.core.Extensions.function;

public class ChatComponent extends ListenerHolderComponent<Holder<Player>> {
    private final Map<Player, Holder<Player>> selected = new HashMap<>();
    private final Multimap<Player, Holder<Player>> muted = HashMultimap.create();
    private final BiFunction<Player, Holder<Player>, String> formatter;

    public ChatComponent(BiFunction<Player, Holder<Player>, String> formatter) {
        this.formatter = formatter;
    }

    public boolean mute(Player player, Holder<Player> channel) {
        if (muted.containsEntry(player, channel)) {
            muted.remove(player, channel);
            return false;
        }
        muted.get(player).add(channel);
        return true;
    }

    public void select(Player player, Holder<Player> channel) {
        selected.put(player, channel);
    }

    public void unselect(Player player, Holder<Player> channel) {
        selected.remove(player, channel);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        if (!selected.containsKey(event.getPlayer()))
            return;
        Holder<Player> channel = selected.get(event.getPlayer());
        channel.forEach(player -> {
            if (!muted.containsEntry(player, channel))
                player.sendMessage(formatter.andThen(function(String::format, new Object[]{event.getPlayer().getName(), event.getMessage()})).apply(player, channel));
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (selected.containsKey(event.getPlayer()))
            selected.remove(event.getPlayer());
    }
}