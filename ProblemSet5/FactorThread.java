import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by woshibiantai on 4/3/17.
 */

public class FactorThread {
    public static ArrayList<Factorisation> threads = new ArrayList<>();
    public static ArrayList<BigInteger> output = new ArrayList<>(2);

    public static void main(String[] args) {

        BigInteger number = new BigInteger("4294967297");
        int numOfThreads = 100;
        BigInteger step = number.divide(BigInteger.valueOf(numOfThreads));

        BigInteger start, stop;

        for (int i = 0; i < numOfThreads; i++) {

            if (i == 0) {
                start = BigInteger.valueOf(2);
            } else {
                start = step.multiply(BigInteger.valueOf(i));
            }
            if (i == numOfThreads - 1) {
                stop = number;
            } else {
                stop = step.multiply(BigInteger.valueOf(i + 1));
            }

            Factorisation t = new Factorisation(number,start,stop);
            threads.add(t);
            t.start();
        }

        try {
            for (int i = 0; i < numOfThreads; i++) {
                threads.get(i).join();
            }

            System.out.println(output);
        }
        catch (Exception e) {
            System.out.println("Some threads didn't finish.");
        }

    }
}

class Factorisation extends Thread {
    private BigInteger n, start, stop;

    Factorisation(BigInteger n, BigInteger start, BigInteger stop) {
        this.n = n;
        this.start = start;
        this.stop = stop;
    }

    @Override
    public void run() {
        ArrayList<BigInteger> result = trialDivision(n,start,stop);

        for (BigInteger i: result) {
            FactorThread.output.add(i);
            System.out.println("Found: " + i);
        }

//        System.out.println(this.isInterrupted());
        if (FactorThread.output.size()==2 && !this.isInterrupted()) {
            for (Factorisation thr : FactorThread.threads) {
                thr.interrupt();
            }
        }
    }


    public ArrayList<BigInteger> trialDivision(BigInteger n, BigInteger start, BigInteger stop) {
        ArrayList<BigInteger> primeFactors = new ArrayList<>();

        for (BigInteger i = start; i.compareTo(stop) < 0 ; i = i.add(BigInteger.ONE)) {
            if(this.isInterrupted()){break;}
            while (n.mod(i).equals(BigInteger.ZERO)) {
                if(this.isInterrupted()){break;}
                primeFactors.add(i);
                n = n.divide(i);
            }
        }

        return primeFactors;
    }
}