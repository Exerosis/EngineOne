package me.engineone.core.listenable;

import me.engineone.core.holder.ConsumerCallCounter;
import me.engineone.core.holder.RunnableCallCounter;
import me.engineone.core.holder.RunnableWasCalled;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by BinaryBench on 4/22/2017.
 */
public class BasicPriorityRunnableListenableTest {

    BasicPriorityRunnableListenable listenable;

    @Before
    public void setUp() throws Exception {
        listenable = new BasicPriorityRunnableListenable();
    }

    @Test
    public void listeners() {
        RunnableWasCalled runnable = new RunnableWasCalled();
        listenable.add(runnable);
        listenable.run();
        assertTrue("Listeners not getting fired!", runnable.wasCalled());
    }

    @Test
    public void priority() throws Exception {
        RunnableWasCalled first = new RunnableWasCalled();
        Runnable second = () -> assertTrue("Priorities are not run in the correct order!", first.wasCalled());

        listenable.add(first, 0.9f);
        listenable.add(second, 1.0f);

        listenable.run();
    }

}