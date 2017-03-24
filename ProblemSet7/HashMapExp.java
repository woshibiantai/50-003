import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by woshibiantai on 20/3/17.
 */
public class HashMapExp {

    public static void main(String[] args) {
        HashMapExp h = new HashMapExp();
        int numOfTests = 10;
        long conTime = 0;
        long syncTime = 0;

        for (int i = 0; i < numOfTests; i++) {
            conTime += h.ConcurrentHashMapTesting();
            syncTime += h.SynchronizedMapTesting();
        }

        System.out.println("Average time for concurrent hashmap: " + (conTime/numOfTests));
        System.out.println("Average time for synchronized map: " + (syncTime/numOfTests));
    }

    public long ConcurrentHashMapTesting() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        WorkerThread w = null;
        ArrayList<WorkerThread> workerThreads = new ArrayList<>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            w  = new WorkerThread(map);
            workerThreads.add(w);
            w.start();
        }

        for (int i = 0; i < 10; i++) {
            try {
                w.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Concurrent Hashmap: " + (System.currentTimeMillis() - start));
        return (System.currentTimeMillis() - start);
    }

    public long SynchronizedMapTesting() {
        Map<String, Integer> map = Collections.synchronizedMap(new HashMap<String, Integer>());
        WorkerThread w = null;
        ArrayList<WorkerThread> workerThreads = new ArrayList<>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            w = new WorkerThread(map);
            workerThreads.add(w);
            w.start();
        }

        for (int i = 0; i < 10; i++) {
            try {
                w.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Synchronized map: " + (System.currentTimeMillis() - start));
        return (System.currentTimeMillis() - start);
    }
}

