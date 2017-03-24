import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class FactorizerUser {
	public static void main (String[] args) {
		CachedFactorizerThreadSafe factorizer = new CachedFactorizerThreadSafe();
		ArrayList<Thread> threadArrayList = new ArrayList<>();

		int numOfThreads = 100;

		for (int i = 0; i < numOfThreads; i++) {
			Thread thread = new Thread (new MyRunnable(factorizer));
			threadArrayList.add(thread);
			thread.start();
		}
		
		try {
			for (int i = 0; i < numOfThreads; i++) {
				threadArrayList.get(i).join();
			}
		}
		catch (Exception e) {
			System.out.println("Some threads didn't finish.");
		}

		System.out.println("hits: " + factorizer.getHits());
		System.out.println("hit ratio: " + factorizer.getCacheHitRatio());
	}
}

class MyRunnable implements Runnable {
	private CachedFactorizerThreadSafe factorizer;

	MyRunnable(CachedFactorizerThreadSafe factorizer) {
		this.factorizer = factorizer;
	}

	public void run () {
		Random random = new Random ();
		int input = random.nextInt(100);
		System.out.println(input + ": " + factorizer.service(input));
	}
}

public class CachedFactorizerThreadSafe {
	private int lastNumber = 101;
	private List<Integer> lastFactors;
	private long hits;
	private long cacheHits;
	private final Object lock = new Object();
	
	long getHits() {
		return hits;
	}
	
	double getCacheHitRatio() {
		return (double) cacheHits/ (double) hits;
	}

	public List<Integer> service(int input) {
		List<Integer> factors = null;

		synchronized (lock) {
			++hits;
			if (input == lastNumber) {
				++cacheHits;
				factors = new ArrayList<Integer>(lastFactors);
			}
		}

		if (factors == null) {
			factors = factor(input);
			synchronized (lock) {
				lastNumber = input;
				lastFactors = new ArrayList<Integer>(factors);
			}
		}
		
		return factors;
	}
	
	public List<Integer> factor(int n) {		
		List<Integer> factors = new ArrayList<Integer>();
		for (int i = 2; i <= n; i++) {
			while (n % i == 0) {
		        factors.add(i);
		        n /= i;
		    }
		}
		
		return factors;
	}
}