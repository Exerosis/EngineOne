package me.engineone.engine.components.party;

import me.engineone.engine.holder.PlayerHolder;

import java.util.Set;

/**
 * Created by Max604 on 5/1/17.
 */
public class Party extends PlayerHolder {

  private String host;
  private Set<String> members;

  public Party(String host, Set<String> members) {
    this.host = host;
    this.members = members;
  }

  public void addMember(String player) {
    if (!members.contains(player)) {
      members.add(player);
    }
  }

  public void removeMember(String player) {
    if (members.contains(player)) {
      members.remove(player);
    }
  }

  public void sendMessagetoAll(String message) {
    for (String member : members) {
      // if (member is on network) {
      Object partyMember; // person on network
      //sendMessage
      // }
    }
  }

}
