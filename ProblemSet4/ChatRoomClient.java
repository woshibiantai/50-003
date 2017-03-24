import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ChatRoomClient {
    public static void main(String args[]) throws Exception {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] sendData;
        System.out.println("What's your name? ");
        String name = inFromUser.readLine();
        System.out.println("Hello " + name + ", welcome to the chatroom.");
        String sentence = name + " has joined the chat!";
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
        clientSocket.send(sendPacket);


        while (true) {
            System.out.print(">: ");
            String input = inFromUser.readLine();
            sentence = name + ": " + input;
            sendData = sentence.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
            clientSocket.send(sendPacket);

            if (input.equals("bye")) {
                sentence = name + " has left the chat.";
                sendData = sentence.getBytes();
                sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
                clientSocket.send(sendPacket);
                clientSocket.close();
                break;
            }
        }
    }
}
