import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by woshibiantai on 12/2/17.
 */
public class HomeworkQuestion2 {
    public static void main(String[] args) {
        HomeworkQuestion2 newGame = new HomeworkQuestion2();
        newGame.playingGame();
    }

    // what's within the cell
    int empty = 0;
    int cross = 1;
    int circle = 2;

    // details for board referencing
    int columns = 3, rows = 3;
    int tile = columns * rows;
    int[] board = new int[columns * rows];

    // states for playing
    int currentState;
    int currentTurn;
    int playing = 0, draw = 1, crossWon = 2, circleWon = 3;

    Scanner SCinput = new Scanner(System.in);


    public void startGame() {
        for (int i = 0; i < tile; i++) {
            board[i] = empty;
        }
        currentState = 0;
        currentTurn = cross;
        printBoard();
    }

    public void gameMoves(int player) {
        boolean myTurn = true;

        do {

            try {
                if (player == cross) {
                    System.out.println("Player X please choose a cell (1-9): ");
                } else {
                    System.out.println("Player O please choose a cell (1-9): ");
                }

                int input = SCinput.nextInt() - 1;
                if (input >= 0 && input <= 8 && board[input] == empty) {
                    board[input] = player;
                    myTurn = false;
                    printBoard();
                } else if (input < 0 || input > 8) {
                    System.out.println((input+1) + " is an invalid input! Please enter value between 1 to 9");
                } else if (board[input] != empty) {
                    System.out.println("Tile " + (input+1) + " is already taken! Please enter another value");
                } else {
                    System.out.println("Invalid input! Please enter value between 1 to 9");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter value between 1 to 9");
                SCinput.next();
            }


        } while (myTurn);
    }

    public void printBoard() {
        int counter = 1;
        System.out.println();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (board[counter-1] == empty) System.out.print(counter);
                else if (board[counter-1] == cross) System.out.print("X");
                else System.out.print("O");

                if (c < columns - 1) System.out.print(" | ");
                counter++;
            }
            System.out.println();
            if (r < rows - 1) System.out.println("----------");
        }
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

    public void playingGame() {
        System.out.println("Let's play Tic Tac Toe!");
        System.out.println("Player X gets to start first. ");
        System.out.println();
        startGame();

        do {
            gameMoves(currentTurn);
            checkState(currentTurn);

            if (currentState == crossWon) System.out.println("Congrats Player X! You've won!");
            else if (currentState == circleWon) System.out.println("Congrats Player O! You've won!");
            else if (currentState == draw) System.out.println("It's a draw!");

            if (currentTurn == cross) currentTurn = circle;
            else if (currentTurn == circle) currentTurn = cross;
        } while (currentState == playing);
    }
}
