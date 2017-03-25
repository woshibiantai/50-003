import java.util.concurrent.CountDownLatch;

public class ExamExample {
    public static void main (String[] args) {
        int numOfStudents = 5;
        CountDownLatch ready = new CountDownLatch(numOfStudents);
        CountDownLatch startPaper = new CountDownLatch(1);
        CountDownLatch endPaper = new CountDownLatch(numOfStudents);
        Students[] students = new Students[numOfStudents];


        for (int i = 0; i < numOfStudents; i++) { 
            students[i] = new Students(ready, startPaper, endPaper);
        }

        System.out.println("Examiner: Welcome to the exam hall.");
        Examiner examiner = new Examiner(ready, startPaper, endPaper);
        examiner.start();

        for (int i = 0; i < numOfStudents; i++) {
            students[i].start();
        }

    }        
}

class Examiner extends Thread {
    CountDownLatch ready, startPaper, endPaper;

    Examiner(CountDownLatch ready, CountDownLatch startPaper, CountDownLatch endPaper) {
        this.ready = ready;
        this.startPaper = startPaper;
        this.endPaper = endPaper;
    }

    public void run() {
        try {
            ready.await();    
        } catch (InterruptedException e) {
            System.out.println("One or more students fainted from stress");
        }

        System.out.println("Examiner: You may begin.");
        startPaper.countDown();

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("*examiner waits and yawns*");

        try {
            endPaper.await();    
        } catch (InterruptedException e) {
            System.out.println("Earthquake! Exam did not end.");
        }
        
        System.out.println("*examiner collects last paper and leaves the exam hall*");
    }
}

class Students extends Thread {
    CountDownLatch ready, startPaper, endPaper;

    Students(CountDownLatch ready, CountDownLatch startPaper, CountDownLatch endPaper) {
        this.ready = ready;
        this.startPaper = startPaper;
        this.endPaper = endPaper;
    }

    public void run() {
        System.out.println("*student gets ready*");
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ready.countDown();

        System.out.println("*student looks up in anticipation*");
        try {
            startPaper.await();     
        } catch (InterruptedException e) {
            System.out.println("April fools! Today's not the paper.");
        }

        System.out.println("*student starts writing furiously*");
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("*student heaves a sigh of relief and submits paper*");
        endPaper.countDown();

        System.out.println("*student leaves exam hall*");
    }
}