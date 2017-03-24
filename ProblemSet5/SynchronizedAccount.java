import java.util.ArrayList;
import java.util.Random;

/**
 * Created by woshibiantai on 4/3/17.
 */
public class SynchronizedAccount extends Thread {

    private static int balance;

    public static void main(String[] args) {
        balance = 100;

        int accountHolders = 5;
        ArrayList<SynchronizedAccount> holders = new ArrayList<>(accountHolders);

        for (int i = 0; i < accountHolders; i++) {
            SynchronizedAccount p = new SynchronizedAccount();
            holders.add(p);
            p.start();
        }

//        SIMULTANEOUS POWER

        for (int i = 0; i < 10; i++) {
            Random r = new Random();
            int action = r.nextInt(3);
            int person = r.nextInt(accountHolders);

            new Thread(() -> {
                if (action == 0) {
                    System.out.println("Check balance: " + holders.get(person).checkBalance());
                } else if (action == 1) {
                    holders.get(person).withdraw(r.nextInt(100));
                } else {
                    holders.get(person).deposit(r.nextInt(100));
                }
            }).start();
        }

        try {
            for (int i = 0; i < accountHolders; i++) {
                holders.get(i).join();
            }
        } catch (InterruptedException e) {
            System.out.println("Some threads are not finished");
        }
    }

    private synchronized void deposit(int amount) {
        this.balance += amount;
        System.out.println("Deposited: " + amount + ", balance: " + checkBalance());
    }

    private synchronized boolean withdraw(int amount) {
        if (this.balance < amount) {
            System.out.println("Cannot withdraw " + amount + " (insufficient funds)");
            return false;
        } else {
            this.balance -= amount;
            System.out.println("Withdrew: " + amount + ", balance: " + checkBalance());
            return true;
        }
    }

    private int checkBalance() {
        return this.balance;
    }


}