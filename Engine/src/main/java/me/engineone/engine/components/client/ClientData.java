package me.engineone.engine.components.client;

import lombok.Data;

import java.util.UUID;

@Data
public class ClientData {

    private final UUID uuid;
    private String name;

    private ClientRank rank;

    /**
     * Client constructor.
     *
     * @param uuid    the UUID
     * @param name    the name
     * @param rank    the rank
     */
    public ClientData(UUID uuid, String name, ClientRank rank) {
        this.uuid = uuid;
        this.name = name;
        this.rank = rank;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientRank getRank() {
        return rank;
    }

    public void setRank(ClientRank rank) {
        this.rank = rank;
    }
}
