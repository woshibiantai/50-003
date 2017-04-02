import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by woshibiantai on 27/3/17.
 */
public class ThreadPerTaskWebServer {
    public static void main(String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(4321);
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = () -> {
                try {
                    handleRequest(connection);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            new Thread(task).start();
        }
    }

    static void handleRequest (Socket connection) throws Exception {
        //todo
        PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
        BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
        BigInteger input = new BigInteger(in.readLine());
        BigInteger answer = factor(input);
        out.println(answer);
        out.flush();
        out.close();
        in.close();
        connection.close();
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