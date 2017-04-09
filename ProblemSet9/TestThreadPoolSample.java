/// Cohort Question 6

import junit.framework.TestCase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TestThreadPoolSample extends TestCase {

    public void testPoolExpansion() throws InterruptedException {
        int max_pool_size = 10;
        ExecutorService exec = Executors.newFixedThreadPool(max_pool_size);

        //todo: insert your code here to complete the test case

        for (int i = 0; i < 30; i++) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Running");

                    int numThreads = 0;
                    if (exec instanceof ThreadPoolExecutor) {
                        numThreads = ((ThreadPoolExecutor) exec).getActiveCount();
                    }

                    System.out.println("Number of active threads: " + numThreads);
                }
            });
        }

            //hint: you can use the following code to get the number of active threads in a thread pool
        /*int numThreads = 0;
        if (exec instanceof ThreadPoolExecutor) {
        	numThreads = ((ThreadPoolExecutor) exec).getActiveCount();
        }*/

        exec.shutdownNow();

    }
}
