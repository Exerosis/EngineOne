package me.engineone.engine.components.enders;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import me.engineone.core.completeable.ParentPhase;
import me.engineone.core.completeable.Phase;
import me.engineone.core.holder.CollectionHolder;
import me.engineone.core.holder.MutateHolder;
import me.engineone.engine.utilites.ServerUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by BinaryBench on 5/3/2017.
 */
public class LMSVictoryCondition extends ParentPhase {

    private List<Player> rank = new ArrayList<>();

    private MutateHolder<Player> players;
    private int endPlayersAmount;
    private MutateHolder<Player> broadcast;

    public LMSVictoryCondition(MutateHolder<Player> players, MutateHolder<Player> broadcast) {
        this(players, 1, broadcast);
    }

    public LMSVictoryCondition(MutateHolder<Player> players, int endPlayersAmount, MutateHolder<Player> broadcast) {
        this.players = players;
        this.endPlayersAmount = endPlayersAmount;
        this.broadcast = broadcast;

        players.onAdded(player -> {
            rank.remove(player);
            rank.add(player);
            checkEnd(players.size());
        });

        onEnable(() -> {
            checkEnd(getPlayers().size());
        });

        onDisable(this::sendWinners);
    }

    public void checkEnd(int playerCount) {
        if (playerCount <= getEndPlayersAmount())
            complete();
    }

    public List<Player> getRank()
    {
        return rank;
    }

    public MutateHolder<Player> getPlayers() {
        return players;
    }

    public int getEndPlayersAmount()
    {
        return endPlayersAmount;
    }

    public MutateHolder<Player> getBroadcast()
    {
        return broadcast;
    }

    public void sendWinners() {
        int counter = 0;
        int score = 0;

        List<Player> players = Lists.newArrayList(getPlayers());

        players.removeAll(getRank());

        ServerUtil.broadcast("----------\n ", getBroadcast());

        for (Player player : players) {
            counter++;
            score = 1;
            print(score, player);
            //if (counter >= 3)
            //break;
        }

        if (counter < 3) {
            List<Player> reversed = new ArrayList<>(getRank());
            Collections.reverse(reversed);

            for (Player player : reversed) {
                counter++;
                score++;
                print(score, player);
                if (counter >= 3)
                    break;
            }
        }

        ServerUtil.broadcast("\n----------", getBroadcast());
    }

    private void print(int rank, Player player) {
        ServerUtil.broadcast(rank + ". " + ChatColor.YELLOW + player.getName(), getBroadcast());
    }


}