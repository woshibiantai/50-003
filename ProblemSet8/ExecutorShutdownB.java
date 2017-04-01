import com.sun.corba.se.spi.orbutil.threadpool.WorkQueue;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.concurrent.*;

public class ExecutorShutdownB {
    private static final BlockingQueue workQueue = new ArrayBlockingQueue<Runnable>(5);
    private static final ExecutorService exec = new ThreadPoolExecutor(5,5,10, TimeUnit.SECONDS,workQueue);

    public static void main(String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(4321);

        while (true) {
            try {
                final Socket connection = socket.accept();
                Runnable task = new Runnable () {
                    public void run() {
                        handleRequest(connection);
                    }
                };

                exec.execute(task);
            } catch (RejectedExecutionException e) {
                System.out.println("LOG: task submission is rejected.");
                break;
            }
        }
    }

    public static void stop() {
        System.out.println("Shutting down");
        exec.shutdownNow();
    }

    protected static void handleRequest(Socket connection) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
            String input = in.readLine();
            if (input.equals("shutdown")) {
                stop();
            } else {
                BigInteger number = new BigInteger(input);
                BigInteger result = factor(number);
                out.println(result);
            }
            out.flush();
            in.close();
            out.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Something went wrong with the connection");
        }
    }

    private static BigInteger factor(BigInteger n) {
        BigInteger i = new BigInteger("2");
        BigInteger zero = new BigInteger("0");

        while (i.compareTo(n) < 0) {
            if (n.remainder(i).compareTo(zero) == 0) {
                return i;
            }

            i = i.add(new BigInteger("1"));
        }

        assert(false);
        return null;
    }
}