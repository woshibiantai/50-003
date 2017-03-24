public class LockStaticVariablesFixed {
    public static int saving = 5000;
    public static int cash = 0;
    
    public static void main(String args[]){     
        int numberofThreads = 10000;
        Withdraw[] threads = new Withdraw[numberofThreads];
    
        Object lock = new Object();
        
        for (int i = 0; i < numberofThreads; i++) {
            threads[i] = new Withdraw(lock);
            threads[i].start();
        }
        
        try {
            for (int i = 0; i < numberofThreads; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            System.out.println("some thread is not finished");
        }
        
        System.out.print("The result is: " + LockStaticVariablesFixed.cash);
    }
}

class Withdraw extends Thread {   
    private Object lock;

    public Withdraw(Object lock) {
        this.lock = lock;
    }

    public void run () {
        synchronized(lock) {
            if (LockStaticVariablesFixed.saving >= 1000) {
            System.out.println("I am doing something.");            
            LockStaticVariablesFixed.saving = LockStaticVariablesFixed.saving - 1000;
            LockStaticVariablesFixed.cash = LockStaticVariablesFixed.cash + 1000;
            }
        }
    }
}