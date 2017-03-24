import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class UDPServerExample {
	public static void main(String args[]) throws Exception       {          
		DatagramSocket serverSocket = new DatagramSocket(9876);             
		byte[] receiveData = new byte[1024];             
		byte[] sendData = new byte[1024];
		ArrayList<DatagramPacket> clientPackets = new ArrayList<DatagramPacket>();

		while(true) {                   
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			System.out.println("Expecting Message Any Minute Now");                   
			serverSocket.receive(receivePacket);
			clientPackets.add(receivePacket);
			String sentence = new String( receivePacket.getData());                   
			System.out.println("RECEIVED: " + sentence);   

			for (DatagramPacket packets: clientPackets) {
				System.out.println("hello");
				InetAddress IPAddress = packets.getAddress();                   
				int port = packets.getPort();                   
				String capitalizedSentence = sentence.toUpperCase();                   
				sendData = capitalizedSentence.getBytes();                   
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);                   
				serverSocket.send(sendPacket);             
			}                
		}       
	}
}
