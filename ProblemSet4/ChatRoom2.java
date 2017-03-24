import jdk.nashorn.internal.ir.WhileNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created by woshibiantai on 25/2/17.
 */

public class ChatRoom2 {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket client = null;
        ArrayList<Socket> clientDatabase = new ArrayList<>();

        try {
            server = new ServerSocket(4321);
            server.setSoTimeout(10000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                System.out.println("Awaiting connections...");
                client = server.accept();
                clientDatabase.add(client);
            } catch (SocketTimeoutException e) {
                System.out.println("Chat room started!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (Socket c: clientDatabase) {
            try {
                Messages m = new Messages(c);
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(c.getInputStream()));
                m.start();
                String message;
                while (!(message = input.readLine()).equals("")) {
                    System.out.println(message);
                }
            } catch (SocketException e) {
                System.out.println("time out");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            long startTime = System.currentTimeMillis();
            long currentTime = startTime;
            while (currentTime - startTime <= 5000) {
                currentTime = System.currentTimeMillis();
            }
//            client.close();

//        } catch (IOException e) {
//            e.printStackTrace();
        } catch (NullPointerException e) {

        }
    }
}
