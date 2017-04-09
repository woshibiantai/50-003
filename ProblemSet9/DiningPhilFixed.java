// Cohort Question 4
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by woshibiantai on 7/4/17.
 */
public class DiningPhilFixed {
    private static int N = 5;

    public static void main (String[] args) throws Exception {
        PhilosopherFixed[] phils = new PhilosopherFixed[N];
        ForkFixed[] forks = new ForkFixed[N];
        Semaphore eater = new Semaphore(N-1);

        for (int i = 0; i < N; i++) {
            forks[i] = new ForkFixed(i);
        }

        for (int i = 0; i < N; i++) {
            phils[i] = new PhilosopherFixed (i, forks[i], forks[(i+N-1)%N], eater);
            phils[i].start();
        }
    }
}


class PhilosopherFixed extends Thread {
    private final int index;
    private final ForkFixed left;
    private final ForkFixed right;
    private Semaphore eater = null;

    public PhilosopherFixed (int index, ForkFixed left, ForkFixed right, Semaphore eater) {
        this.index = index;
        this.left = left;
        this.right = right;
        this.eater = eater;
    }

    public void run() {
        Random randomGenerator = new Random();
        try {
            while (true) {
                Thread.sleep(randomGenerator.nextInt(100)); //not sleeping but thinking
                System.out.println("Phil " + index + " finishes thinking.");
                eater.acquire();
                left.pickup();
                System.out.println("Phil " + index + " picks up left fork.");
                Thread.sleep(3000); // Sleeping here forces every thread to pick up the left work together. See if system still deadlocks
                right.pickup();
                System.out.println("Phil " + index + " picks up right fork.");
                Thread.sleep(randomGenerator.nextInt(100)); //eating
                System.out.println("Phil " + index + " finishes eating.");
                left.putdown();
                System.out.println("Phil " + index + " puts down left fork.");
                right.putdown();
                System.out.println("Phil " + index + " puts down right fork.");
                eater.release();
            }
        } catch (InterruptedException e) {
            System.out.println("Don't disturb me while I am sleeping, well, thinking.");
        }
    }
}

class ForkFixed {
    private final int index;
    private boolean isAvailable = true;

    public ForkFixed (int index) {
        this.index = index;
    }

    public synchronized void pickup () throws InterruptedException {
        while (!isAvailable) {
            wait();
        }

        isAvailable = false;
        notifyAll();
    }

    public synchronized void putdown() throws InterruptedException {
        while (isAvailable) {
            wait();
        }

        isAvailable = true;
        notifyAll();
    }

    public String toString () {
        if (isAvailable) {
            return "Fork " + index + " is available.";
        }
        else {
            return "Fork " + index + " is NOT available.";
        }
    }
}