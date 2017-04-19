package me.engineone.engine.components.client;

import me.engineone.core.component.Component;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClientGetComponent extends Component {

    private final ConcurrentHashMap<UUID, ClientData> clients = new ConcurrentHashMap<>();

    public void addClient(ClientData clientData) {
        clients.put(clientData.getUuid(), clientData);
    }

    public void removeClient(ClientData clientData) {
        clients.remove(clientData.getUuid());
    }

    public ClientData getClient(Player player) {
        return clients.get(player.getUniqueId());
    }

}
