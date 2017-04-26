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
    public void basic() throws Exception {
        checkMutateListenableHolder(holder);
    }

    @Test
    public void difference() throws Exception {
        CollectionHolder<String> otherHolder = new BasicCollectionHolder<>();

        holder.add("Potato");
        holder.add("Apple");
        otherHolder.add("Potato");

        MutateHolder<String> difference = holder.difference(otherHolder);
        difference.onAdded(s -> assertTrue("Item added to difference that's not the difference.", holder.test(s) && !otherHolder.test(s)));
        difference.onRemoved(s -> assertTrue("Item added to difference that's not the difference.", !holder.test(s) || otherHolder.test(s)));

        assertEquals("MutableHolder.difference() did not split the holder correctly.",1, difference.size());
        holder.add("OtherPotato");
        otherHolder.add("OtherPotato");
        otherHolder.remove("OtherPotato");
        assertEquals("MutableHolder.difference() did not handle adding and removing correctly.",2, difference.size());

        checkMutateListenableHolder(difference, holder);
    }


    @Test
    public void union() throws Exception {
        CollectionHolder<String> otherHolder = new BasicCollectionHolder<>();

        holder.add("Potato");
        holder.add("Apple");
        otherHolder.add("Mushroom");

        MutateHolder<String> union = holder.union(otherHolder);
        union.onAdded(s -> assertTrue("Item added to union that's not the union.", holder.test(s) || otherHolder.test(s)));
        union.onRemoved(s -> assertTrue("Item removed from union that's in union.", !holder.test(s) && !otherHolder.test(s)));

        assertEquals("MutableHolder.union() did not add the holders correctly.",3, union.size());
        holder.add("OtherPotato");
        otherHolder.add("OtherPotato");
        otherHolder.remove("OtherPotato");
        assertEquals("MutableHolder.union() did not handle adding and removing correctly.",4, union.size());

        checkMutateListenableHolder(union, holder);
    }


    public void checkMutateListenableHolder(MutableHolder<String> holder) {
        checkMutateListenableHolder(holder, holder);
    }

    public void checkMutateListenableHolder(MutateHolder<String> holder, Mutable<String> master) {

        // Add & Remove
        ConsumerCallCounter<String> addCounter = new ConsumerCallCounter<>();
        ConsumerCallCounter<String> removeCounter = new ConsumerCallCounter<>();
        ConsumerCallCounter<String> addedCounter = new ConsumerCallCounter<>();
        ConsumerCallCounter<String> removedCounter = new ConsumerCallCounter<>();

        holder.onAdd(addCounter);
        holder.onRemove(removeCounter);
        holder.onAdded(addedCounter);
        holder.onRemoved(removedCounter);

        master.add("Potato-checkAddAndRemove");
        master.remove("Potato-checkAddAndRemove");

        holder.unregisterAdd(addCounter);
        holder.unregisterRemove(removeCounter);
        holder.unregisterAdded(addedCounter);
        holder.unregisterRemoved(removedCounter);

        master.add("Mushroom-checkAddAndRemove");
        master.remove("Mushroom-checkAddAndRemove");

        assertEquals(holder.getClass().getSimpleName() + " is not registering/unregistering add listeners properly", 1, addCounter.count());
        assertEquals(holder.getClass().getSimpleName() + " is not registering/unregistering remove listeners properly", 1, removeCounter.count());
        assertEquals(holder.getClass().getSimpleName() + " is not registering/unregistering added listeners properly", 1, addedCounter.count());
        assertEquals(holder.getClass().getSimpleName() + " is not registering/unregistering removed listeners properly", 1, removedCounter.count());


        // Check preserve order
        ConsumerWasCalled<String> addWasCalled = new ConsumerWasCalled<>();
        ConsumerWasCalled<String> removeWasCalled = new ConsumerWasCalled<>();
        ConsumerWasCalled<String> addedWasCalled = new ConsumerWasCalled<>();
        ConsumerWasCalled<String> removedWasCalled = new ConsumerWasCalled<>();


        holder.onAdd(addWasCalled);
        holder.onAdd(s -> assertTrue(holder.getClass().getSimpleName() + " does not preserve add Listeners order correctly", addWasCalled.wasCalled()));

        holder.onAdded(addedWasCalled);
        holder.onAdded(s -> assertTrue(holder.getClass().getSimpleName() + " does not preserve added Listeners order correctly", addedWasCalled.wasCalled()));

        holder.onRemove(removeWasCalled);
        holder.onRemove(s -> assertTrue(holder.getClass().getSimpleName() + " does not preserve remove Listeners order correctly", removeWasCalled.wasCalled()));

        holder.onRemoved(removedWasCalled);
        holder.onRemoved(s -> assertTrue(holder.getClass().getSimpleName() + " does not preserve removed Listeners order correctly", removedWasCalled.wasCalled()));

        master.add("Preserve order check");
        master.remove("Preserve order check");


        // Check priority
        addWasCalled.reset();
        removeWasCalled.reset();
        holder.onAdd(s -> assertTrue(holder.getClass().getSimpleName() + " does not preserve add Listeners order correctly", addWasCalled.wasCalled()));
        holder.getAddListeners().add(0, addWasCalled);
        holder.onRemove(s -> assertTrue(holder.getClass().getSimpleName() + " does not preserve remove Listeners order correctly", addWasCalled.wasCalled()));
        holder.getRemoveListeners().add(0, removeWasCalled);
        master.add("Priority Check");
        master.remove("Priority Check");

    }
}