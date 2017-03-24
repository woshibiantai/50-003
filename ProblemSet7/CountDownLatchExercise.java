import java.util.concurrent.CountDownLatch;

public class CountDownLatchExercise {

    public static void main(String[] args) {
        int limit = 7;
        int numOfSearchers = 10;
        final CountDownLatch latch = new CountDownLatch(limit);
        String[] grades = {"A","A","A","F","A","F","F","A","A","F","A","A","F","A","A","F","F","F","F","F","F",
                "A","A","A","F","A","F","F","A","A","F","A","A","F","A","A","F","F","F","F","F","F","A","A","A","F","A","F","F","A","A","F","A","A","F","A","A","F","F","F","F","F","F","A","A","A","F","A","F","F","A","A","F","A","A","F","A","A","F","F","F","F","F","F"};
        int breakpoint= grades.length/numOfSearchers;

        Searcher[] s = new Searcher[numOfSearchers];
        for (int i = 0; i < numOfSearchers; i++) {
            if (i == numOfSearchers - 1) {
                s[i] = new Searcher(i*breakpoint,grades.length,grades,latch);
            } else {
                s[i] = new Searcher(i*breakpoint,(i+1)*breakpoint,grades,latch);
            }
            s[i].start();
        }

        try {
            latch.await();
            System.out.println("Found " + limit + " Fs");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < numOfSearchers; i++) {
            try {
                s[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

class Searcher extends Thread {
    int start, end;
    String[] grades;
    CountDownLatch latch;

    Searcher(int start, int end, String[] grades, CountDownLatch latch) {
        this.start = start;
        this.end = end;
        this.latch = latch;
        this.grades = grades;
    }

    public void run() {
        if (latch.getCount() == 0) {
            return;
        } else {
            for (int i = start; i < end; i++) {
                if (grades[i].equals("F")) {
                    System.out.println("Found F");
                    latch.countDown();
                }
            }
        }
    }
}