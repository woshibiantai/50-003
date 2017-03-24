/**
 * Created by woshibiantai on 16/3/17.
 */


import java.util.concurrent.atomic.AtomicInteger;

//is this class thread-safe?
public class RangeSafe {
    public final AtomicInteger lower = new AtomicInteger(0);
    public final AtomicInteger upper = new AtomicInteger(0);
    //invariant: lower <= upper

    public synchronized void setLower(int i) {
        synchronized (upper) {
            if (i > upper.get()) {
                throw new IllegalArgumentException("Can't set lower to " + i + " > upper");
            }
        }
         synchronized (lower) {
             lower.set(i);
         }
    }

    public synchronized void setUpper(int i) {
        synchronized (lower) {
            if (i < lower.get()) {
                throw new IllegalArgumentException ("Can't set upper to " + i + " < lower");
            }
        }

        synchronized (upper) {
            upper.set(i);
        }
    }

    public boolean isInRange(int i) {
        return (i >= lower.get()) && i <= upper.get();
    }

    public boolean isValid() {
        return lower.get() <= upper.get();
    }
}
