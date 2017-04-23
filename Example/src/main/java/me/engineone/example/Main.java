package me.engineone.example;

import me.engineone.core.holder.BasicCollectionHolder;
import me.engineone.core.mutable.MutateListenable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SimpleTimeZone;


/**
 * Created by BinaryBench on 4/21/2017.
 */
public class Main {


    public static void main(String[] args) {

        BasicCollectionHolder<String> allPlayers = new BasicCollectionHolder<>();
        BasicCollectionHolder<String> spectatePlayers = new BasicCollectionHolder<>();
        MutateListenable<String> gamePlayers = allPlayers.difference(spectatePlayers);
        gamePlayers.onAdd(s -> System.out.format("Game received: %s\n", s));
        gamePlayers.onRemove(s -> System.out.format("Game lost: %s\n", s));

        allPlayers.add("player1");
        allPlayers.add("player2");

        allPlayers.getAddListeners().add(0, spectatePlayers::add);

        allPlayers.add("player3");
        allPlayers.add("player4");

    }

    public static void print() {
        //System.out.printf("Holder:\t %s\nMutable:\t %s\nDifference:\t %s\n", Lists.newArrayList(holder), Lists.newArrayList(mutable), Lists.newArrayList(difference));
    }
}
