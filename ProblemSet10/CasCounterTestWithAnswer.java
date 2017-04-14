// Cohort Question 5

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class BarrierTimer implements Runnable {
    private boolean started;
    private long startTime, endTime;

    public synchronized void run() {
        long t = System.nanoTime();
        if (!started) {
            started = true;
            startTime = t;
        } else
            endTime = t;
    }

    public synchronized void clear() {
        started = false;
    }

    public synchronized long getTime() {
        return endTime - startTime;
    }
}

public class CasCounterTestWithAnswer {
    private BarrierTimer timer = new BarrierTimer();
    protected static final ExecutorService pool = Executors.newCachedThreadPool();
    //TODO: set up the data object here
    protected AtomicInteger atomicInteger = new AtomicInteger(0);
    protected final int nTrials, nThreads;
    protected CyclicBarrier barrier;
    protected final int nIncrements = 10000;
    
    public CasCounterTestWithAnswer(int nThreads, int trials) {
    	this.nThreads = nThreads;
    	this.nTrials = trials;
        barrier = new CyclicBarrier(nThreads + 1, timer);
    }

    private void test() {
        try {
            timer.clear();
            for (int i = 0; i < nThreads; i++) {
                pool.execute(new Runnable () {
					public void run() {
			            try {
							barrier.await();						
							for (int i = 0; i < nIncrements; i++) {
								//TODO: perform the data operation
                                atomicInteger.incrementAndGet();
							}
							barrier.await();
						} catch (InterruptedException | BrokenBarrierException e) {
							e.printStackTrace();
						}
					}                	
                });
            }
            barrier.await();
            barrier.await();
            System.out.print("Total Time: " + timer.getTime());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        int tpt = 100000; 
        for (int nThreads = 32; nThreads <= 100; nThreads += 10) {
        	CasCounterTestWithAnswer t = new CasCounterTestWithAnswer(nThreads, tpt);
                System.out.print("number of threads: " + nThreads + "\t");
                t.test();
                System.out.println();
                Thread.sleep(1000);
        }
        CasCounterTestWithAnswer.pool.shutdown();
    }
}

class LockCounter {
    private int value;

    public synchronized int getValue() {
        return value;
    }

    public synchronized int increment() {
        return value++;
    }
}

class LockCounterTest {
    private BarrierTimer timer = new BarrierTimer();
    protected static final ExecutorService pool = Executors.newCachedThreadPool();
    //TODO: set up the data object here
    protected int integer = 0;
    protected final int nTrials, nThreads;
    protected CyclicBarrier barrier;
    protected final int nIncrements = 10000;

    public LockCounterTest(int nThreads, int trials) {
        this.nThreads = nThreads;
        this.nTrials = trials;
        barrier = new CyclicBarrier(nThreads + 1, timer);
    }

    private void test() {
        try {
            timer.clear();
            for (int i = 0; i < nThreads; i++) {
                pool.execute(new Runnable () {
                    public void run() {
                        try {
                            barrier.await();
                            for (int i = 0; i < nIncrements; i++) {
                                //TODO: perform the data operation
                                integer++;
                            }
                            barrier.await();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            barrier.await();
            barrier.await();
            System.out.print("Total Time: " + timer.getTime());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        int tpt = 100000;
        for (int nThreads = 32; nThreads <= 100; nThreads += 10) {
            LockCounterTest t = new LockCounterTest(nThreads, tpt);
            System.out.print("number of threads: " + nThreads + "\t");
            t.test();
            System.out.println();
            Thread.sleep(1000);
        }
        LockCounterTest.pool.shutdown();
    }
}