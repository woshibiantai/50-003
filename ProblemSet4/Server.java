import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4321);

        ArrayList<Socket> clientSockets = new ArrayList<>();

        System.out.println("(... expecting connection ...)");
        System.out.println("(for 5s only)");

        long startTime = System.currentTimeMillis();
        long currentTime = startTime;

        int counter = 0;
        while (currentTime - startTime <= 5000) {
            counter++;
            Socket clientSocket = serverSocket.accept();
            clientSockets.add(clientSocket);
            System.out.println("Client " + counter + " has connected.");
            currentTime = System.currentTimeMillis();
        }
        System.out.println("(... connection established ...)");

        String stringInput;
        int i = 0;

        while (true) {
            Socket currentSocket = clientSockets.get(i);
            PrintWriter out =
                    new PrintWriter(currentSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(currentSocket.getInputStream()));
            BufferedReader stdIn =
                    new BufferedReader(
                            new InputStreamReader(System.in));

            stringInput = in.readLine();

            if (stringInput.equals("Bye")) {
                out.println(stringInput);
                currentSocket.close();
                clientSockets.remove(currentSocket);
                if (clientSockets.size() == 0) {
                    serverSocket.close();
                    break;
                }
                out.close();
                in.close();
            }

            System.out.println("Client " + i + " says: " + stringInput);
            out.println(stdIn.readLine());
            out.flush();

            if (i == clientSockets.size() - 1) {
                i = 0;
            } else {
                i++;
            }
        }
    }
}