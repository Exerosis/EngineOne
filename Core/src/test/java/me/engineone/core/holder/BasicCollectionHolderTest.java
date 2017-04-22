package me.engineone.core.holder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by BinaryBench on 4/22/2017.
 */
public class BasicCollectionHolderTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void listeners() throws Exception {
        ConsumerCallCounter<String> holderAddCounter = new ConsumerCallCounter<>();
        ConsumerCallCounter<String> holderRemoveCounter = new ConsumerCallCounter<>();

        ConsumerCallCounter<String> mutableAddCounter = new ConsumerCallCounter<>();
        ConsumerCallCounter<String> mutableRemoveCounter = new ConsumerCallCounter<>();
        Predicate<String> mutablePredicate = s -> s.toLowerCase().startsWith("a");

        ConsumerCallCounter<String> mutable2AddCounter = new ConsumerCallCounter<>();
        ConsumerCallCounter<String> mutable2RemoveCounter = new ConsumerCallCounter<>();
        Predicate<String> mutable2Predicate = s -> s.toLowerCase().startsWith("apple");


        CollectionHolder<String> holder = new BasicCollectionHolder<>();
        MutableHolder<String> mutableHolder = holder.partition(mutablePredicate);
        MutableHolder<String> mutable2Holder = mutableHolder.partition(mutable2Predicate);

        holder.addAddListener(holderAddCounter);
        holder.addRemoveListener(holderRemoveCounter);

        mutableHolder.addAddListener(s -> assertTrue(mutablePredicate.test(s)));
        mutableHolder.addAddListener(mutableAddCounter);
        mutableHolder.addRemoveListener(mutableRemoveCounter);

        mutable2Holder.addAddListener(s -> assertTrue(mutable2Predicate.test(s)));
        mutable2Holder.addAddListener(mutable2AddCounter);
        mutable2Holder.addRemoveListener(mutable2RemoveCounter);

        List<String> testData = new ArrayList<String>(Arrays.asList(
                "Potato",
                "Apple",
                "Mushroom",
                "Apricot",
                "Dogfood",
                "Apple Pie"
        ));

        holder.addAll(testData);
        //FixMe
        testData.forEach(holder::remove);

        assertEquals(holderAddCounter.count(), count(testData, s -> true));
        assertEquals(holderAddCounter.count(), holderRemoveCounter.count());
        assertEquals(mutableAddCounter.count(), count(testData, mutablePredicate));
        assertEquals(mutableAddCounter.count(), mutableRemoveCounter.count());
        assertEquals(mutable2AddCounter.count(), count(testData, mutable2Predicate));
        assertEquals(mutable2AddCounter.count(), mutable2RemoveCounter.count());

    }

    public <T> int count(Iterable<T> data, Predicate<T> predicate) {
        int counter = 0;
        for (T datum : data) {
            if (predicate.test(datum))
                counter++;
        }
        return counter;
    }

    public static void print() {
        //System.out.printf("Holder:\t %s\nMutable:\t %s\nDifference:\t %s\n", Lists.newArrayList(holder), Lists.newArrayList(mutable), Lists.newArrayList(difference));
    }
}