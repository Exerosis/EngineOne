package me.engineone.core.holder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import me.engineone.core.mutable.Mutable;
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

    private CollectionHolder<String> holder;
    private List<String> testData;

    @Before
    public void setUp() throws Exception {
        holder = new BasicCollectionHolder<>();
        testData = new ArrayList<>(Arrays.asList(
                "Potato",
                "Apple",
                "Mushroom",
                "Apricot",
                "Dogfood",
                "Apple Pie"
        ));
    }

    @Test
    public void partitions() throws Exception {
        checkPartitions(holder);
        checkPartitions(holder.partition(s -> true), holder);
    }

    @Test
    public void difference() throws Exception {
        CollectionHolder<String> otherHolder = new BasicCollectionHolder<>();

        holder.add("Potato");
        holder.add("Apple");
        otherHolder.add("Potato");

        MutateListenableHolder<String> difference = holder.difference(otherHolder);
        difference.onAdd(s -> assertTrue(holder.test(s) && !otherHolder.test(s)));
        difference.onRemove(s -> assertTrue(!holder.test(s) || otherHolder.test(s)));

        assertEquals("MutableHolder.difference() did not split the holder correctly.",1, difference.size());
        holder.add("OtherPotato");
        otherHolder.add("OtherPotato");
        otherHolder.remove("OtherPotato");
        assertEquals("MutableHolder.difference() did not handle adding and removing correctly.",2, difference.size());

        checkAddAndRemove(difference, holder);
        checkPartitions(difference, holder);
    }


    @Test
    public void union() throws Exception {
        CollectionHolder<String> otherHolder = new BasicCollectionHolder<>();

        holder.add("Potato");
        holder.add("Apple");
        otherHolder.add("Mushroom");

        MutateListenableHolder<String> union = holder.union(otherHolder);
        union.onAdd(s -> assertTrue(holder.test(s) || otherHolder.test(s)));
        union.onRemove(s -> assertTrue(!holder.test(s) && !otherHolder.test(s)));

        assertEquals("MutableHolder.union() did not add the holders correctly.",3, union.size());
        holder.add("OtherPotato");
        otherHolder.add("OtherPotato");
        otherHolder.remove("OtherPotato");

        assertEquals("MutableHolder.union() did not handle adding and removing correctly.",4, union.size());

        checkAddAndRemove(union, holder);
        checkPartitions(union, holder);
    }


    public void checkAddAndRemove(MutateListenableHolder<String> holder, Mutable<String> master) {

        ConsumerCallCounter<String> addCounter = new ConsumerCallCounter<>();
        ConsumerCallCounter<String> removeCounter = new ConsumerCallCounter<>();

        holder.onAdd(addCounter);
        holder.onRemove(removeCounter);

        master.add("Potato-checkAddAndRemove");
        master.remove("Potato-checkAddAndRemove");

        holder.unregisterAdd(addCounter);
        holder.unregisterRemove(removeCounter);

        master.add("Mushroom-checkAddAndRemove");
        master.remove("Mushroom-checkAddAndRemove");

        assertEquals(holder.getClass().getSimpleName() + " is not registering/unregistering add listeners properly", 1, addCounter.count());
        assertEquals(holder.getClass().getSimpleName() + " is not registering/unregistering remove listeners properly", 1, removeCounter.count());
    }



    public void checkPartitions(MutableHolder<String> holder) {
        checkPartitions(holder, holder);
    }


    public void checkPartitions(MutateListenableHolder<String> holder, Mutable<String> master) {

        ConsumerCallCounter<String> addCounter = new ConsumerCallCounter<>();
        ConsumerCallCounter<String> removeCounter = new ConsumerCallCounter<>();
        Predicate<String> predicate = s -> s.toLowerCase().startsWith("a");

        MutateListenableHolder<String> partition = holder.partition(predicate);

        partition.onAdd(s -> {
            assertTrue(holder.getClass().getSimpleName() + ".partition() allowed '" + s + "' in, a string that does not fit in the predicate. : " + (holder == this.holder), predicate.test(s));
        });
        partition.onAdd(addCounter);
        partition.onRemove(removeCounter);

        List<String> testData = new ArrayList<>(Arrays.asList(
                "Potato-checkPartitions",
                "Apple-checkPartitions",
                "Mushroom-checkPartitions",
                "Apricot-checkPartitions",
                "Dogfood-checkPartitions",
                "Apple Pie-checkPartitions"
        ));

        master.add(testData);
        //FixMe
        testData.forEach(master::remove);

        assertEquals(addCounter.count(), count(testData, predicate));
        assertEquals(addCounter.count(), removeCounter.count());

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