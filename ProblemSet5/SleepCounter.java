import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by woshibiantai on 4/3/17.
 */
public class SleepCounter {
    public static void main(String[] args) {
        // One thread for reading input and one thread for counting. Reading thread interrupts the counting thread.
        Counter c = new Counter();
        InputReader reading = new InputReader(c);

        c.start();
        reading.start();

        try {
            c.join();
            reading.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class Counter extends Thread {

    public void run() {
        counting();
    }

    private void counting() {
        int count = 0;

        while (true) {
            if (this.isInterrupted()) {
                break;
            }

            System.out.println(count);
            count++;
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

class InputReader extends Thread{

    Counter c;

    InputReader(Counter c) {
        this.c = c;
    }

    public void run() {
        reading();
    }

    private void reading() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = "";

        while (true) {
            try {
                input = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (input.equals("0")) {
                c.interrupt();
                break;
            }

            System.out.println("Hello. Input 0 to stop counting.");
        }

    }
}