package me.engineone.engine.predicate;

import me.engineone.core.holder.CollectionHolder;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

public class TeamDeathPredicate implements Predicate<CollectionHolder<Player>> {

    @Override
    public boolean test(CollectionHolder<Player> team) {
        return team.size() == 0;
    }

}
