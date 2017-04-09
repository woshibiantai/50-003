// Cohort Question 5

import java.util.Random;

import org.junit.*;

public class BoundedBufferTest {	
	private static final long LOCKUP_DETECT_TIMEOUT = 1000;

    @Test
    public void testPutAfterFull() throws InterruptedException {
		final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);

		Runnable putting = new Runnable() {
			@Override
			public void run() {
				try {
					bb.put((new Random()).nextInt());
				} catch (InterruptedException success) {}
			}
		};

        Thread[] threads = new Thread[30];

        try {
            for (int i = 0; i < 30; i++) {
                threads[i] = new Thread (putting);
                threads[i].start();
            }

            Thread.sleep(LOCKUP_DETECT_TIMEOUT);

            for (int i = 0; i < 30; i++) {
                threads[i].interrupt();
            }

            for (int i = 0; i < 30; i++) {
                threads[i].join(LOCKUP_DETECT_TIMEOUT);
                Assert.assertFalse(threads[i].isAlive()); //the taker should not be alive for some time
            }


        } catch (Exception unexpected) {
            Assert.assertTrue(false);
        }
    }


	@Test
	public void testPutAndTake() throws InterruptedException {
        // concurrently put and take. should end up empty.
        final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);

        Runnable putting = new Runnable () {
            public void run() {
                try {
                    bb.put((new Random()).nextInt());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable taking = new Runnable () {
            public void run() {
                try {
                    int unused = bb.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread[] threads = new Thread[10];

        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread (putting);
            threads[i+5] = new Thread (taking);
            threads[i].start();
            threads[i+5].start();
        }

        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }

        Assert.assertFalse(bb.isFull());
        Assert.assertTrue(bb.isEmpty());

    }
	
	@Test
	public void testIsEmptyWhenConstructed () {
		BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		Assert.assertTrue(bb.isEmpty());
		Assert.assertFalse(bb.isFull());
	}
	
	@Test
	public void testIsFullAfterPuts () throws InterruptedException {
		final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		
		Runnable task = new Runnable () {
			public void run() {
				try {
					bb.put((new Random()).nextInt());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}				
			}
		};

		Thread[] threads = new Thread[10];
		
		for (int i = 0; i < 10; i++) {
			threads[i] = new Thread (task);
			threads[i].start();
		}

		for (int i = 0; i < 10; i++) {
			threads[i].join();
		}

		Assert.assertTrue(bb.isFull());
		Assert.assertFalse(bb.isEmpty());
	}
	
	@Test
	public void testTakeBlocksWhenEmpty () {
		final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		Thread taker = new Thread() {
			public void run() {
				try {
					int unused = bb.take();
					Assert.assertTrue(false);
				} catch (InterruptedException success) {} //if interrupted, the exception is caught here
			}
		};
		
		try {
			taker.start();
			Thread.sleep(LOCKUP_DETECT_TIMEOUT);
			taker.interrupt();
			taker.join(LOCKUP_DETECT_TIMEOUT);
			Assert.assertFalse(taker.isAlive()); //the taker should not be alive for some time
		} catch (Exception unexpected) {
			Assert.assertTrue(false);
		}
	}
}
