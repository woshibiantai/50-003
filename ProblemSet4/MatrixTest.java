public class MatrixTest {
    public static void main(String[] args) {
        int[] rows = {1,10,50,100,500,1000};
        int[] threads = {1,2,3,4,5,6,7,8,9,10};

        System.out.println("          Rows");
        System.out.println("1 | 10| 50|100| 500 | 1000");
        System.out.println("--------------------------");

        for (int thread : threads) {
            for (int row : rows) {
                long startTime = System.currentTimeMillis();
                MatrixThreadArray.testing(row,thread);
                long endTime = System.currentTimeMillis();;
                System.out.print((endTime-startTime) + " | ");
            }
            System.out.println("Threads: " + thread);
        }
    }
}
