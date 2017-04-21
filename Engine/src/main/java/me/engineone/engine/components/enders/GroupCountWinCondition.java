package me.engineone.engine.components.enders;

import me.engineone.core.component.CollectionHolderComponent;
import me.engineone.core.holder.Holder;
import me.engineone.core.holder.LiveHolder;
import me.engineone.core.holder.MutableHolder;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GroupCountWinCondition extends CollectionHolderComponent<Holder<Player>> {
    private final Set<LiveHolder<Player>> winners = new HashSet<>();
    private final Set<LiveHolder<Player>> losers = new HashSet<>();

    public GroupCountWinCondition(MutableHolder<Player> players, Predicate<Holder> lost, int count, Consumer<Collection<Holder<Player>>> listener) {

        addEnable(() -> {
            players.addAddListener(
                player -> losers.forEach(group -> {
                    if (!lost.test(group)) {
                        winners.add(group);
                        losers.remove(group);
                    }
                })
            );

            players.addRemoveListener(player -> winners.forEach(group -> {
                if (lost.test(group)) {
                    losers.add(group);
                    winners.remove(group);
                    if (winners.size() <= count)
                        listener.accept(getWinners());
                }
            }));
        });

        addDisable(() -> listener.accept(getWinners()));

        addAddListener(group -> {
            LiveHolder<Player> holder = group.difference(players);
            if (lost.test(holder))
                losers.add(holder);
            else
                winners.add(holder);
        });
    }

    public Set<Holder<Player>> getLosers() {
        return losers.stream().map(LiveHolder::getPrimary).collect(Collectors.toSet());
    }

    public Set<Holder<Player>> getWinners() {
        return winners.stream().map(LiveHolder::getPrimary).collect(Collectors.toSet());
    }
}
