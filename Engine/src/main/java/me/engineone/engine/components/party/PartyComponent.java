package me.engineone.engine.components.party;

import me.engineone.core.component.Component;

import java.util.Set;

/**
 * Created by Max604 on 5/1/17.
 */
public class PartyComponent extends Component {

  Set<Party> parties;

  public PartyComponent(Set<Party> parties) {
    this.parties = parties;
  }

  public void createParty() {

  }

  public void removeParty() {

  }
}
