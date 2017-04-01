import java.io.*;
import java.math.BigInteger;
import java.net.*;

public class LifeCycleWebClient {
    public static void main(String[] args) throws Exception {
        int numberOfClients = 100; //vary this number here
        long startTime = System.currentTimeMillis();
        BigInteger n = new BigInteger("4294967297");
        //BigInteger n = new BigInteger("239839672845043");
        Thread[] clients = new Thread[numberOfClients];

        clients[0] = new Thread(new LifeCycleClient(n,true));

        for (int i = 1; i < numberOfClients; i++) {
            clients[i] = new Thread(new LifeCycleClient(n,false));
        }

        for (int i = 0; i < numberOfClients; i++) {
            clients[i].start();
        }

        for (int i = 0; i < numberOfClients; i++) {
            clients[i].join();
        }
        System.out.println("Spent time: " + (System.currentTimeMillis() - startTime));
    }
}

class LifeCycleClient implements Runnable {
    private final BigInteger n;
    private boolean shutdown;

    public LifeCycleClient(BigInteger n, boolean shutdown) {
        this.n = n;
        this.shutdown = shutdown;
    }

    public void run() {
        String hostName = "localhost";
        int portNumber = 4321;

        try {
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
            if (shutdown) {
                System.out.println("thread called to shut down");
                out.println("shutdown");
            } else {
                out.println(n.toString());
            }
            out.flush();
            in.readLine();
            out.close();
            in.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Server got problem");
        }
    }
}
