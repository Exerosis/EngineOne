package me.engineone.example;

import com.google.common.collect.Iterators;
import me.engineone.core.holder.BasicCollectionHolder;
import me.engineone.core.holder.MutateHolder;
import me.engineone.core.mutable.MutateListenable;

/**
 * Created by BinaryBench on 4/21/2017.
 */
public class Main {


    public static void main(String[] args) {

        BasicCollectionHolder<String> allPlayers = new BasicCollectionHolder<>();
        BasicCollectionHolder<String> spectatePlayers = new BasicCollectionHolder<>();
        MutateHolder<String> gamePlayers = allPlayers.difference(spectatePlayers);

        gamePlayers.getAddedListeners().add(s -> System.out.printf("%s added to GamePlayers\n", s));
        gamePlayers.getRemovedListeners().add(s -> System.out.printf("%s removed from GamePlayers\n", s));

        allPlayers.add("player1");

        allPlayers.getAddListeners().add(0, spectatePlayers::add);

        System.out.println();
        allPlayers.add("player2");

        for (String player : gamePlayers) {
            System.out.println("Player: " + player);
        }


    }
}
