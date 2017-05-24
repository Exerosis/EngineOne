package me.engineone.engine.components.party;

import me.engineone.core.holder.BasicCollectionHolder;
import me.engineone.engine.predicate.PlayerPredicate;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * Created by Max604 on 5/1/17.
 */
public class Party extends BasicCollectionHolder<Player> implements PlayerPredicate {

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
