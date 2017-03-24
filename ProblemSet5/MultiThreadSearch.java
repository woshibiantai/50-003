import java.util.Arrays;
public class MultiThreadSearch {
    public static boolean found = false;
    public static Thread[] threads = new Thread[2];

    public static void main(String[] args) {
        int[] integerArray = {2,43,66,723,4,32,6,7,4354,99,2,43,66,723,4,32,6,7,4354,99,2,43,66,723,4,32,6,7,4354,99,2,43,66,723,4,32,6,7,4354,99,2,43,66,723,4,32,6,7,4354,99,2,43,66,723,4,32,6,7,4354,99,2,43,66,723,4,32,6,7,4354,99,2,43,66,723,4,32,6,7,4354,99,2,43,66,723,4,32,6,7,4354,99,2,43,66,723,4,32,6,7,4354,99,2,43,66,723,4,32,6,7,4354,99,2,43,66,723,4,32,6,7,4354,99,2,43,66,723,4,32,6,7,4354,99,2,43,66,723,4,32,6,7,4354,99,2,43,66,723,4,32,6,7,4354,99,2,43,66,723,4,32,6,7,4354,99};
        int desired = 5;
        int midpoint = integerArray.length;
        Search t1 = new Search(Arrays.copyOfRange(integerArray,0,midpoint),desired);
        Search t2 = new Search(Arrays.copyOfRange(integerArray,midpoint,integerArray.length),desired);
        threads[0] = (t1);
        threads[1] = (t2);

        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();

            if (found) {
                System.out.println("Found in integer array: " + desired);
            } else {
                System.out.println("Not found in integer arrauy: " + desired);
            }
        } catch (Exception e) {
                System.out.println("Exception caught");
        }
    }
}

class Search extends Thread {
    private int[] array;
    private int desired;

    public Search(int[] array, int desired) {
        this.array = array;
        this.desired = desired;
    }

    public void run() {
        for (int i : array) {

            if(this.isInterrupted()){break;}

            if (i == desired) {
                MultiThreadSearch.found = true;
                for (Thread t : MultiThreadSearch.threads) {
                    t.interrupt();
                }
            }
        } 
     }
}
