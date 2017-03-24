import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by woshibiantai on 25/2/17.
 */
public class TicTacToePlayer {
    public static void main(String[] args) {
        try {
            Socket client = new Socket("localhost", 4321);
            System.out.println("Joined game!");

            BufferedReader input = new BufferedReader(
                    new InputStreamReader(
                            client.getInputStream()));
            PrintWriter output = new PrintWriter(
                    client.getOutputStream(), true);
            Scanner stdIn = new Scanner(System.in);


            try {
                while (!(input.readLine()).equals("end")) {
                    output.println(stdIn.nextLine());
                    output.flush();
                }
            } catch (NullPointerException e) {
                System.out.println("You lost :(");
            }

            input.close();
            output.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
