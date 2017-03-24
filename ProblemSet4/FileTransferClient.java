import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by woshibiantai on 25/2/17.
 */
public class FileTransferClient {
    public static void main(String[] args) {
        try {
            String inputFile = "input.txt";
//            String inputFile = args[0];
            Socket client = new Socket("localhost", 4321);
            client.setSoTimeout(2000);
            BufferedReader input = new BufferedReader(
                    new FileReader(inputFile));
            PrintWriter output = new PrintWriter(
                    client.getOutputStream(), true);
            BufferedReader response = new BufferedReader(
                    new InputStreamReader(
                            client.getInputStream()));

            String inputLine;
            while (!(inputLine = input.readLine()).equals("")) {
                System.out.println("Sending: " + inputLine);
                output.println(inputLine);

                try {
                    response.readLine();
                    System.out.println("From server: " + response.readLine());
                } catch (SocketTimeoutException e) {
                    System.out.println("Send failed. Trying again...");
                    output.println(inputLine);
                }
            }

            output.close();
            input.close();
            response.close();
            client.close();
        } catch (NullPointerException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
