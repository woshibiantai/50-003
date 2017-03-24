import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class FactorPrimeServerMul {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4321);

        ArrayList<Socket> clientSockets = new ArrayList<>();

        String toFactorize = "4294967297";

        BigInteger number = new BigInteger(toFactorize);
        System.out.println("Factorising " + number + "...");

        System.out.println("(... expecting connection ...)");

//        Accepting connections for 10s
        int i = 0;
        while (true) {
            try {
                serverSocket.setSoTimeout(10000);
                Socket clientSocket = serverSocket.accept();
                clientSockets.add(clientSocket);
                System.out.println("Client " + i + " has connected.");
                i++;
            } catch (SocketTimeoutException e) {
                break;
            }
        }
        System.out.println("(... connection established ...)");

//        Sending data out to the clients
        int clientSize = clientSockets.size();
        BigInteger step = number.divide(BigInteger.valueOf(clientSize));

        for (int j = 0; j < clientSize; j++) {
            Socket currentSocket = clientSockets.get(j);
            PrintWriter out =
                    new PrintWriter(currentSocket.getOutputStream(), true);

            out.println(number);
            if (j == 0) {
                out.println(2);
            } else {
                out.println(step.multiply(BigInteger.valueOf(j)));
            }
            if (j == clientSize - 1) {
                out.println(number);
            } else {
                out.println(step.multiply(BigInteger.valueOf(j + 1)));
            }
        }

//      Getting data from clients to output
        System.out.println("Finding prime factors... ");
        long startTime = System.currentTimeMillis();
        i = 0;
        while (true) {
            Socket currentSocket = clientSockets.get(i);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(currentSocket.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Client " + i + " has found: ");
                System.out.println(line);
            }

            if (i == clientSize - 1) {
                break;
            } else {
                i++;
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime-startTime));
    }
}
