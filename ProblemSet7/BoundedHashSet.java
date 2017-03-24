/**
 * Created by woshibiantai on 23/3/17.
 */
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedHashSet<T> {
    private final Set<T> set;
    private Semaphore sem;

    public BoundedHashSet (int bound) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        sem = new Semaphore(bound, true);
    }

    public boolean add(T o) throws InterruptedException {
        sem.acquire();  // increasing counter for bound (since you're adding)
        boolean alreadyAdded = false;
        try {
            alreadyAdded = set.add(o);
            return alreadyAdded;
        } finally {
            if (!alreadyAdded) {
                sem.release();  // if it already exists, decrease counter for bound since you're not adding it
            }
        }
    }

    public boolean remove (Object o) throws InterruptedException {
        boolean alreadyRemoved = set.remove(o);
        if (alreadyRemoved) {
            sem.release();  // able to remove object, can decrease counter for bound
        }
        return alreadyRemoved;
    }
}
