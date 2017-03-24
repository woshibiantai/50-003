import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by woshibiantai on 25/2/17.
 */

public class FileTransfer {
    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(4321);

        int i = 1;
        while (true) {
            try {
                System.out.println("server started");
                Socket client = server.accept();
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(
                                client.getInputStream()));
                PrintWriter output = new PrintWriter(client.getOutputStream(), true);

                PrintWriter outToFile = new PrintWriter(
                        new FileWriter(
                                new File("output"+i+".txt")), true);

                FileReading fileReader = new FileReading(client,input,output,outToFile);
                fileReader.start();
                i++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class FileReading extends Thread{
    Socket client;
    BufferedReader input;
    PrintWriter output;
    PrintWriter outToFile;

    public FileReading(Socket client, BufferedReader input, PrintWriter output, PrintWriter outToFile) {
        this.client = client;
        this.input = input;
        this.output = output;
        this.outToFile = outToFile;
    }

    public void run() {

        try {
            String inputLine;
            while ((inputLine = input.readLine()) != null) {
                output.println("Line downloaded.");
                System.out.println("Downloaded: " + inputLine);
                outToFile.println(inputLine);
                System.out.println("Recorded to file.");
            }
            System.out.println("Download complete");
            input.close();
            outToFile.close();
            output.close();
            client.close();

        } catch (NullPointerException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}