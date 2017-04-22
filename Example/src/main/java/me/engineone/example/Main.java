package me.engineone.example;

import com.google.common.collect.Lists;
import me.engineone.core.holder.BasicCollectionHolder;
import me.engineone.core.holder.CollectionHolder;
import me.engineone.core.holder.LiveHolder;
import me.engineone.core.holder.MutableHolder;


/**
 * Created by BinaryBench on 4/21/2017.
 */
public class Main {


    public static void main(String[] args) {
        CollectionHolder<String> holder = new BasicCollectionHolder<>();
        MutableHolder<String> mutableHolder = holder.partition(s -> s.toLowerCase().startsWith("a"));

        holder.addAddListener(s -> System.out.format("Holder received: %s", s));
        holder.addAddListener(s -> System.out.format("Holder received: %s", s));


        holder.add("Potato");

    }

    public static void print() {
        //System.out.printf("Holder:\t %s\nMutable:\t %s\nDifference:\t %s\n", Lists.newArrayList(holder), Lists.newArrayList(mutable), Lists.newArrayList(difference));
    }
}
