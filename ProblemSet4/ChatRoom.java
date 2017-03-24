import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ChatRoom {
    public static void main(String args[]) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(9876);
        byte[] receiveData = new byte[1024];

        System.out.println("Expecting Message Any Minute Now");
        while(true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            byte[] data = new byte[receivePacket.getLength()];
            System.arraycopy(receivePacket.getData(),receivePacket.getOffset(),data,0,receivePacket.getLength());
//            String sentence = new String( receivePacket.getData());
            String sentence = new String(data);
            System.out.println(sentence);
        }
    }
}
