package me.engineone.core.holder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Lists;
import me.engineone.core.holder.liveholders.MutableDifferenceHolder;
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
    }

    @Test
    public void difference() throws Exception {
        CollectionHolder<String> otherHolder = new BasicCollectionHolder<>();

        holder.add("Potato");
        holder.add("Apple");
        otherHolder.add("Potato");

        MutableDifferenceHolder<String> difference = holder.difference(otherHolder);
        difference.addAddListener(s -> assertTrue(holder.test(s) && !otherHolder.test(s)));
        difference.addRemoveListener(s -> assertTrue(!holder.test(s) || otherHolder.test(s)));

        assertEquals("MutableHolder.Difference did not split the holder correctly.",1, difference.size());
        holder.add("OtherPotato");
        otherHolder.add("OtherPotato");
        otherHolder.remove("OtherPotato");

        checkPartitions(difference, holder);
    }


    public void checkPartitions(MutableHolder<String> holder) {
        checkPartitions(holder, holder);
    }

    public void checkPartitions(MutateListenableHolder<String> holder, Mutable<String> master) {

        ConsumerCallCounter<String> addCounter = new ConsumerCallCounter<>();
        ConsumerCallCounter<String> removeCounter = new ConsumerCallCounter<>();
        Predicate<String> predicate = s -> s.toLowerCase().startsWith("a");

        MutateListenableHolder<String> partition = holder.partition(predicate);

        partition.addAddListener(s -> {
            assertTrue(holder.getClass().getSimpleName() + ".partition() allowed '" + s + "' in, a string that does not fit in the predicate. : " + (holder == this.holder), predicate.test(s));
        });
        partition.addAddListener(addCounter);
        partition.addRemoveListener(removeCounter);

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