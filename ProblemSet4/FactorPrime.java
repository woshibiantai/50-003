import java.math.BigInteger;
import java.util.ArrayList;

public class FactorPrime {

    public ArrayList<Integer> trialDivisionInt(int number) {
        ArrayList<Integer> primeFactors = new ArrayList<>();
        int n = number;
        for (int i = 2; i < n; i++) {
            while (n % i == 0) {
                if (!primeFactors.contains(i))  {
                    primeFactors.add(i);}
                n = n/i;
            }
        }

        return primeFactors;
    }

    public ArrayList<Integer> trialDivisionBigInt(BigInteger n) {
        ArrayList<Integer> primeFactors = new ArrayList<>();
        BigInteger start = BigInteger.valueOf(2);

        for (BigInteger i = start; i.compareTo(n) < 0 ; i = i.add(BigInteger.ONE)) {
            while (n.mod(i) == (BigInteger.ZERO)) {
                if (!primeFactors.contains(i.intValue()))  {
                    primeFactors.add(i.intValue());
                }
                n = n.divide(i);
            }
        }

        return primeFactors;
    }

    public ArrayList<BigInteger> trialDivision(BigInteger n, BigInteger start, BigInteger stop) {
        ArrayList<BigInteger> primeFactors = new ArrayList<>();

        for (BigInteger i = start; i.compareTo(stop) < 0 ; i = i.add(BigInteger.ONE)) {
            while (n.mod(i).equals(BigInteger.ZERO)) {
                if (!primeFactors.contains(i)) {
                    primeFactors.add(i);}
                n = n.divide(i);
            }
        }

        return primeFactors;
    }

}
