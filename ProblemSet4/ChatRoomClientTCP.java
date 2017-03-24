import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class ChatRoomClientTCP {
    public static void main(String[] args) throws Exception {
        String hostName = "localhost";
        int portNumber = 4321;

        Socket echoSocket = new Socket();
        SocketAddress sockaddr = new InetSocketAddress(hostName, portNumber);
        echoSocket.connect(sockaddr, 100);

        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
        BufferedReader stdIn =
                new BufferedReader(
                        new InputStreamReader(System.in));

        System.out.println("What's your name?");
        String name = stdIn.readLine();
        System.out.println("Welcome to the chat, " + name);
        if (echoSocket.isConnected()) {
            out.println(name + " has joined the chat!");
        }

        System.out.print(">: ");
        String userInput;
        while (!(userInput = stdIn.readLine()).equals("")) {
            System.out.print(">: ");
            out.println(name + ": " + userInput);
            out.flush();

            if (userInput.equals("bye")) {
                out.println(name + " has left the chat.");
                stdIn.close();
                out.close();
                echoSocket.close();
                break;
            }
        }

    }
}
