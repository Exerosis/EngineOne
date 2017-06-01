package me.engineone.core.holder.single;

import me.engineone.core.holder.ConsumerCallCounter;
import me.engineone.core.holder.ConsumerWasCalled;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by BinaryBench on 6/1/2017.
 */
public class BasicSingleHolderTest {

    private BasicSingleHolder<String> single;

    @Before
    public void setUp() throws Exception {
        single = new BasicSingleHolder<>();
    }

    @Test
    public void basic() {

        checkSingle(single);

    }

    public static void checkSingle(SingleMutableHolder<String> single) {

        // Add & Remove
        ConsumerCallCounter<String> addCounter = new ConsumerCallCounter<>();
        ConsumerCallCounter<String> removeCounter = new ConsumerCallCounter<>();
        ConsumerCallCounter<String> addedCounter = new ConsumerCallCounter<>();
        ConsumerCallCounter<String> removedCounter = new ConsumerCallCounter<>();

        single.onAdd(addCounter);
        single.onRemove(removeCounter);
        single.onAdded(addedCounter);
        single.onRemoved(removedCounter);

        single.setValue("Potato-checkAddAndRemove");
        single.setValue(null);

        single.unregisterAdd(addCounter);
        single.unregisterRemove(removeCounter);
        single.unregisterAdded(addedCounter);
        single.unregisterRemoved(removedCounter);

        single.setValue("Mushroom-checkAddAndRemove");
        single.setValue(null);

        assertEquals(single.getClass().getSimpleName() + " is not registering/unregistering add listeners properly", 1, addCounter.count());
        assertEquals(single.getClass().getSimpleName() + " is not registering/unregistering remove listeners properly", 1, removeCounter.count());
        assertEquals(single.getClass().getSimpleName() + " is not registering/unregistering added listeners properly", 1, addedCounter.count());
        assertEquals(single.getClass().getSimpleName() + " is not registering/unregistering removed listeners properly", 1, removedCounter.count());


        // Check preserve order
        ConsumerWasCalled<String> addWasCalled = new ConsumerWasCalled<>();
        ConsumerWasCalled<String> removeWasCalled = new ConsumerWasCalled<>();
        ConsumerWasCalled<String> addedWasCalled = new ConsumerWasCalled<>();
        ConsumerWasCalled<String> removedWasCalled = new ConsumerWasCalled<>();


        single.onAdd(addWasCalled);
        single.onAdd(s -> assertTrue(single.getClass().getSimpleName() + " does not preserve add Listeners order correctly", addWasCalled.wasCalled()));

        single.onAdded(addedWasCalled);
        single.onAdded(s -> assertTrue(single.getClass().getSimpleName() + " does not preserve added Listeners order correctly", addedWasCalled.wasCalled()));

        single.onRemove(removeWasCalled);
        single.onRemove(s -> assertTrue(single.getClass().getSimpleName() + " does not preserve remove Listeners order correctly", removeWasCalled.wasCalled()));

        single.onRemoved(removedWasCalled);
        single.onRemoved(s -> assertTrue(single.getClass().getSimpleName() + " does not preserve removed Listeners order correctly", removedWasCalled.wasCalled()));

        single.setValue("Preserve order check");
        single.setValue(null);


        // Check priority
        addWasCalled.reset();
        removeWasCalled.reset();
        single.onAdd(s -> assertTrue(single.getClass().getSimpleName() + " does not preserve add Listeners order correctly", addWasCalled.wasCalled()));
        single.getAddListeners().add(0, addWasCalled);
        single.onRemove(s -> assertTrue(single.getClass().getSimpleName() + " does not preserve remove Listeners order correctly", addWasCalled.wasCalled()));
        single.getRemoveListeners().add(0, removeWasCalled);
        single.setValue("Priority Check");
        single.setValue("Priority Check");

    }

}