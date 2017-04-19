package me.engineone.engine.components.client;

import me.engineone.engine.components.base.ListenerComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ClientLoadComponent extends ListenerComponent {

    private final ClientGetComponent component;

    public ClientLoadComponent(ClientGetComponent component) {
        this.component = component;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        //TODO: Idk I just made it like this since we aren't going to use MongoDB anymore and don't know how to use redis
        component.addClient(new ClientData(player.getUniqueId(), player.getName(), (player.isOp() ? ClientRank.ADMIN : ClientRank.DEFAULT)));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ClientData clientData = component.getClient(player);

        if (clientData != null) {
            component.removeClient(clientData);
        }
    }

}
