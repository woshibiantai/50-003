import java.util.ArrayList;
import java.util.Arrays;

public class MatrixThreadArray extends Thread {
    private int start,stop;
    private int[][] matrix1, matrix2, finalMatrix;

    public MatrixThreadArray(int[][] matrix1, int[][]matrix2, int[][] finalMatrix, int start, int stop) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.finalMatrix = finalMatrix;
        this.start = start;
        this.stop = stop;
    }

    public int[][] multiply(int[][] matrix1, int[][] matrix2, int[][] finalMatrix, int start, int stop) {
        int n = matrix2.length;

        for (int i = start; i < stop; i++) {
            for (int  j = 0; j < n; j++) {
                finalMatrix[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    finalMatrix[i][j] += matrix1[i][k]*matrix2[k][j];
                }
            }
        }

        return finalMatrix;
    }

    public void run() {
        multiply(matrix1,matrix2,finalMatrix,start,stop);
    }

    public static void testing(int dim, int numOfThreads) {
        int[][] first = new int[dim][dim];
        int[][] second = new int[dim][dim];
        int[][] output = new int[dim][dim];

        int step = dim/numOfThreads;

        ArrayList<MatrixThreadArray> threads = new ArrayList<>(numOfThreads);

        for (int i = 0; i < numOfThreads; i++) {
            if (i == numOfThreads-1) {
                threads.add(new MatrixThreadArray(first,second,output,(step*i),dim));
            } else {
                threads.add(new MatrixThreadArray(first,second,output,(step*i),(step*(i+1))));
            }
        }

        for (MatrixThreadArray t : threads) {
            t.start();
        }

        try {
            for (MatrixThreadArray t : threads) {
                t.join();
            }
        } catch (InterruptedException e) {
            System.out.println("A thread didn't finish!");
        }
    }

//    public static void main(String[] args) {
//        int dim = 1000;
//        int numOfThreads = 2;
//
//        int[][] first = new int[dim][dim];
//        int[][] second = new int[dim][dim];
//        int[][] output = new int[dim][dim];
//
//        int step = dim/numOfThreads;
//
//        ArrayList<MatrixThreadArray> threads = new ArrayList<>(numOfThreads);
//
//        for (int i = 0; i < numOfThreads; i++) {
//            if (i == numOfThreads-1) {
//                threads.add(new MatrixThreadArray(first,second,output,(step*i),dim));
//            } else {
//                threads.add(new MatrixThreadArray(first,second,output,(step*i),(step*(i+1))));
//            }
//        }
//
//        long startTime = System.currentTimeMillis();
//
//        for (MatrixThreadArray t : threads) {
//            t.start();
//        }
//
//        try {
//            for (MatrixThreadArray t : threads) {
//                t.join();
//            }
//        } catch (InterruptedException e) {
//            System.out.println("A thread didn't finish!");
//        }
//
//        for (int[] i : output) {
//            System.out.println(Arrays.toString(i));
//        }
//
//        long endTime = System.currentTimeMillis();
//        System.out.println("Time taken: " + (endTime-startTime));
//    }
}
