import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class FactorPrimeServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4321);

        ArrayList<Socket> clientSockets = new ArrayList<>();

//        System.out.println("What would you like to factorize? ");
//        BufferedReader toFactorize =
//                new BufferedReader(
//                        new InputStreamReader(System.in));
        String toFactorize = "4294967297";

//        BigInteger number = new BigInteger(toFactorize.readLine());
        BigInteger number = new BigInteger(toFactorize);
        System.out.println("Factorising " + number + "...");

        int clientSize = 3;
        BigInteger step = number.divide(BigInteger.valueOf(clientSize));

        System.out.println("(... expecting connection ...)");

        int i = 0;
        while (i < clientSize) {
            Socket clientSocket = serverSocket.accept();
            clientSockets.add(clientSocket);
            System.out.println("Client " + i + " has connected.");

            Socket currentSocket = clientSockets.get(i);
            PrintWriter out =
                    new PrintWriter(currentSocket.getOutputStream(), true);

            out.println(number);
            if (i == 0) {out.println(2);}
            else {out.println(step.multiply(BigInteger.valueOf(i)));}
            if (i == clientSize - 1) {out.println(number);}
            else {out.println(step.multiply(BigInteger.valueOf(i+1)));}

            i++;
        }

        System.out.println("The prime factors are: ");
        i = 0;
        while (true) {
            Socket currentSocket = clientSockets.get(i);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(currentSocket.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }



            if (i == clientSize - 1) {
                break;
            } else {
                i++;
            }
        }

        serverSocket.close();
        for (Socket s: clientSockets) {
            s.close();
        }
    }
}
