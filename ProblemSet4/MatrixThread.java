import java.util.ArrayList;
import java.util.Arrays;

public class MatrixThread extends Thread{
    public static void main(String[] args) {
        int[][] first = {{3,4,5,7},
                        {5,3,6,4},
                        {6,3,2,6},
                        {4,5,1,6}};
        int[][] second = {{5,4,3,1},
                        {7,4,2,7},
                        {7,3,6,4},
                        {8,6,4,5}};

        int midpoint = first.length/2;
        int[][] output = new int[first.length][first.length];

        MatrixThread thread1 = new MatrixThread(first,second,0,midpoint,output);
        MatrixThread thread2 = new MatrixThread(first,second,midpoint,first.length,output);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();

        } catch (InterruptedException e) {
            System.out.println("A thread didn't finish!");
        }
    }

    //  m represents the number of rows, and n the number of columns
    private int m, n, start, stop;
    private int[][] matrix1;
//  Matrix 1 is in the shape of:
//    [ ][ ][ ][ ][ ]
//    [ ][ ][ ][ ][ ]
//    m x n matrix

    private int[][] matrix2;
//  Matrix 2 is in the shape of:
//    [ ][ ][ ][ ][ ]
//    [ ][ ][ ][ ][ ]
//    [ ][ ][ ][ ][ ]
//    [ ][ ][ ][ ][ ]
//    n X n matrix

    private int[][] finalArray;

    public MatrixThread(int[][] matrix1, int[][] matrix2, int start, int stop, int[][] finalArray) {
        this.matrix1 = matrix1;
        this.m = matrix1.length;
        this.n = matrix2.length;
        this.start = start;
        this.stop = stop;
        this.matrix2 = matrix2;
        this.finalArray = finalArray;
    }


    public int[][] multiply(int[][] matrix1, int[][]matrix2, int start, int stop, int[][] finalArray) {
        int n = matrix2.length;
        for (int i = start; i < stop; i++) {
            for (int j = 0; j < n; j++) {
                finalArray[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    finalArray[i][j] += matrix1[i][k]*matrix2[k][j];
                }
            }
        }
        return finalArray;
    }

    public String stringForm(int[][] input) {
        int m = input.length;
        int n = input[0].length;
        String outputString = "";
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                outputString += input[i][j] + " ";
            }
            outputString += "\n";
        }
        return outputString;
    }

    public void run() {
        System.out.println(stringForm(multiply(matrix1,matrix2,start,stop,finalArray)));
    }
}