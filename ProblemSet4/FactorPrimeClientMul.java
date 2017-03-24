import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

public class FactorPrimeClientMul {
    public static void main(String[] args) throws Exception {
        FactorPrime factorPrime = new FactorPrime();
        String hostName = "localhost";
        int portNumber = 4321;

        Socket echoSocket = new Socket();
        SocketAddress sockaddr = new InetSocketAddress(hostName, portNumber);
        echoSocket.connect(sockaddr, 100);
        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
        BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(echoSocket.getInputStream()));

        System.out.println("starting");
        long startTime = System.currentTimeMillis();
        BigInteger number = new BigInteger(in.readLine());
        System.out.println("number " + number);
        BigInteger start = new BigInteger(in.readLine());
        BigInteger stop = new BigInteger(in.readLine());

        System.out.println("Factorising " + number + " from " + start + " to " + stop);

        ArrayList<BigInteger> primeFactors = factorPrime.trialDivision(number,start,stop);

        for (BigInteger i: primeFactors) {
            System.out.println(i);
            out.println(i);
        }
        out.flush();
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime-startTime));
    }
}

//class FactorPrime {
//
//    public ArrayList<BigInteger> trialDivision(BigInteger n, BigInteger start, BigInteger stop) {
//        ArrayList<BigInteger> primeFactors = new ArrayList<>();
//
//        for (BigInteger i = start; i.compareTo(stop) < 0 ; i = i.add(BigInteger.ONE)) {
//            while (n.mod(i).equals(BigInteger.ZERO)) {
//                if (!primeFactors.contains(i)) {
//                    primeFactors.add(i);}
//                n = n.divide(i);
//            }
//        }
//
//        return primeFactors;
//    }
//}