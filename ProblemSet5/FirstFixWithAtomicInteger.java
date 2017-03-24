import java.util.concurrent.atomic.AtomicInteger;

public class FirstFixWithAtomicInteger {
    public static AtomicInteger count = new AtomicInteger(0);

    public static void main (String[] args) {
        // int numberOfThreads = Integer.parseInt(args[0]);
        int numberOfThreads = 1000;
        AtomicA[] threads = new AtomicA[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new AtomicA();
            threads[i].start();
        }

        try {
            for (int i = 0; i < numberOfThreads; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            System.out.println("Unfinished threads");
        }

        System.out.println("The result is...");
        System.out.println(count);
    }
}

class AtomicA extends Thread {
    public void run() {
        FirstFixWithAtomicInteger.count.incrementAndGet();
    }
}