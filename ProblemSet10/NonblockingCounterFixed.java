// Cohort Question 6

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class NonblockingCounterFixed {
    private AtomicStampedReference<AtomicInteger> value =
            new AtomicStampedReference<>(new AtomicInteger(),0);

    public int getValue() {
        return value.getReference().get();
    }

    public int increment() {
        AtomicInteger oldValue;
        AtomicInteger newValue;
        int oldStamp;

        do{
          oldValue = value.getReference();
            oldStamp = value.getStamp();
            newValue = new AtomicInteger(oldValue.get());
            newValue.incrementAndGet();
        } while (!value.compareAndSet(oldValue, newValue, oldStamp, oldStamp + 1));
        return oldValue.get() + 1;
    }
}
