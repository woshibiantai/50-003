import javax.naming.ldap.SortKey;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by woshibiantai on 25/2/17.
 */
public class TicTacToe {
    public static void main(String[] args) throws IOException {
        ServerSocket server = null;
        Socket client = null;
        ArrayList<Socket> clientDatabase = new ArrayList<>(2);
        TicTacToe ticTacToe = null;
        BufferedReader input = null;
        PrintWriter output = null;

        try {
            server = new ServerSocket(4321);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Waiting for players to join...");

        for (int i = 1; i < 3; i++) {
            try {
                client = server.accept();
                clientDatabase.add(client);
                System.out.println("Player " + i + " joined");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Players ready!");
        int turn = 0;
        int[] board = new int[3 * 3];
        turn = 1;

        while (true) {
            try {
                input = new BufferedReader(
                        new InputStreamReader(
                                clientDatabase.get(turn).getInputStream()));
                output = new PrintWriter(clientDatabase.get(turn).getOutputStream(), true);

                output.println(" ");
                ticTacToe = new TicTacToe(input, output, board, turn+1, true);

                ticTacToe.playingGame();
                board = ticTacToe.getBoard();

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (ticTacToe.gameEnd()) {
                output.println("end");
                break;
            }

            if (!ticTacToe.getMyTurn()) {
                System.out.println("turn changed");
                if (turn == 0) {
                    turn = 1;
                } else {
                    turn = 0;
                }
            }
        }
        output.close();
        input.close();
        client.close();
        server.close();
    }

    // what's within the cell
    private int empty = 0;
    private int cross = 1;
    private int circle = 2;

    // details for board referencing
    private int columns = 3, rows = 3;
    private int tile = columns * rows;
    private int[] board = new int[columns * rows];

    // states for playing
    private int currentState;
    private int currentTurn;
    private int playing = 0, draw = 1, crossWon = 2, circleWon = 3;

    private BufferedReader in;
    private PrintWriter output;
    boolean myTurn;
    boolean gamePlay;

//    private Scanner SCinput = new Scanner(System.in);

    public TicTacToe(BufferedReader input, PrintWriter output, int[] board, int player, boolean myTurn) {
        this.in = input;
        this.output = output;
        this.board = board;
        this.currentTurn = player;
        this.myTurn = myTurn;
    }

    public void startGame() {
        for (int i = 0; i < tile; i++) {
            board[i] = empty;
        }
        currentState = 0;
        currentTurn = cross;
        System.out.println(printBoard());
    }

    public void gameMoves(int player) {
        try {
            if (player == cross) {
                System.out.println("Player X please choose a cell (1-9): ");
            } else {
                System.out.println(("Player O please choose a cell (1-9): "));
            }

            int input = Integer.parseInt(in.readLine()) - 1;
            if (input >= 0 && input <= 8 && board[input] == empty) {
                board[input] = player;
                myTurn = false;
                System.out.println(printBoard());
            } else if (input < 0 || input > 8) {
                System.out.println((input+1) + " is an invalid input! Please enter value between 1 to 9");
            } else if (board[input] != empty) {
                System.out.println("Tile " + (input+1) + " is already taken! Please enter another value");
            } else {
                System.out.println("Invalid input! Please enter value between 1 to 9");
            }
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Invalid input! Please enter value between 1 to 9");
//                SCinput.next();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getMyTurn() {
        return myTurn;
    }

    public String printBoard() {
        String output = "";
        int counter = 1;
        output += "\n";
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (board[counter-1] == empty) output += (counter);
                else if (board[counter-1] == cross) output +=("X");
                else output += ("O");

                if (c < columns - 1) output+= (" | ");
                counter++;
            }
            output += "\n";
            if (r < rows - 1) output+= ("----------\n");
        }

        return output;
    }

    public void checkState(int currentTurn) {
        if (itsADraw()) currentState = draw;
        else if (someoneWon(currentTurn)) {
            if (currentTurn == cross) currentState = crossWon;
            else if (currentTurn == circle) currentState = circleWon;
        } else currentState = playing;
    }

    public boolean itsADraw() {
        for (int i = 0; i < tile; i++) {
            if (board[i] == empty) return false;
        }
        return true;
    }

    public boolean someoneWon(int currentTurn) {
        return (board[0] == currentTurn
                && board[1] == currentTurn
                && board[2] == currentTurn
                || board[3] == currentTurn
                && board[4] == currentTurn
                && board[5] == currentTurn
                || board[6] == currentTurn
                && board[7] == currentTurn
                && board[8] == currentTurn
                || board[0] == currentTurn
                && board[3] == currentTurn
                && board[6] == currentTurn
                || board[1] == currentTurn
                && board[4] == currentTurn
                && board[7] == currentTurn
                || board[2] == currentTurn
                && board[5] == currentTurn
                && board[8] == currentTurn
                || board[0] == currentTurn
                && board[4] == currentTurn
                && board[8] == currentTurn
                || board[2] == currentTurn
                && board[4] == currentTurn
                && board[6] == currentTurn
        );
    }

    public int[] getBoard() {
        return board;
    }


    public void playingGame() {
//        startGame();

        gameMoves(currentTurn);
        checkState(currentTurn);

        if (currentState == crossWon) {
            System.out.println("Player X won!");
            setGame(true);
        }
        else if (currentState == circleWon) {
            System.out.println("Player O won!");
            setGame(true);
        }
        else if (currentState == draw) {
            System.out.println("It's a draw!");
            setGame(true);
        }

        if (currentTurn == cross) currentTurn = circle;
        else if (currentTurn == circle) currentTurn = cross;
    }

    public void setGame(boolean result) {
        this.gamePlay = result;
    }

    public boolean gameEnd() {
        return gamePlay;
    }
}
