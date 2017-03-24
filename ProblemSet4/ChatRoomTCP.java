import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatRoomTCP {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket client;

        try {
            server = new ServerSocket(4321);
            System.out.println("Chat room started!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                assert server != null;
                client = server.accept();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            Messages message = new Messages(client);
            message.start();
        }
    }
}

class Messages extends Thread {
    private Socket client = null;

    Messages(Socket client) {
        this.client = client;
    }

    public void run() {
        try {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));

            String message;
            while (!(message = input.readLine()).equals("")) {
                System.out.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {

        }


    }
}
