package me.engineone.example;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by BinaryBench on 4/21/2017.
 */
public class Main {


    public static void main(String[] args) {
        List<Runnable> list = new ArrayList<>();

        Runnable runnable = () -> System.out.println("Potato");

        list.add(new Runnable() {
            @Override
            public void run() {
                runnable.run();
            }
        });

        list.remove(new Runnable() {
            @Override
            public void run() {
                runnable.run();
            }
        });

        System.out.println(list);
    }

    public static void print() {
        //System.out.printf("Holder:\t %s\nMutable:\t %s\nDifference:\t %s\n", Lists.newArrayList(holder), Lists.newArrayList(mutable), Lists.newArrayList(difference));
    }
}
