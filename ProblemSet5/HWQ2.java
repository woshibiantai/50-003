import java.util.ArrayList;

/**
 * Created by woshibiantai on 4/3/17.
 */
public class HWQ2 {
    public static void main(String[] args) {
        StringBuffer a = new StringBuffer("A");
        int numOfThreads = 3;

        ArrayList<Manipulation> threads = new ArrayList<>(numOfThreads);

        for (int i = 0; i < numOfThreads; i++) {
            Manipulation m = new Manipulation(a);
            threads.add(m);
            m.start();
        }

        for (int i = 0; i < numOfThreads; i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                System.out.println("Some threads didn't finish.");
            }
        }
    }

}

class Manipulation extends Thread {
    StringBuffer alphabet;

    Manipulation(StringBuffer alphabet) {
        this.alphabet = alphabet;
    }


    @Override
    public void run() {
        synchronized (alphabet) {
            for (int i = 0; i < 100; i++) {
                System.out.println(alphabet);
            }
            char c = alphabet.charAt(0);
            c++;
            alphabet.replace(0,1,Character.toString(c));
        }
    }
}
