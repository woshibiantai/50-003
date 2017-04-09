/// Cohort Question 1

import javax.xml.soap.Node;
import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.*;

public class GDesktop {
	private final static int BOUND = 20;
	private final static int N_CONSUMERS = 4;
	
	public static void startIndexing (File[] roots) {
		BlockingQueue<File> queue = new LinkedBlockingQueue<File>(BOUND);
		ExecutorService exec = Executors.newCachedThreadPool();
		FileFilter filter = new FileFilter() {
			public boolean accept(File file) {return true;}
		};
		
		for (File root : roots) {
			(new FileCrawler(queue, filter, root, exec)).start();
		}
		
		for (int i = 0; i < N_CONSUMERS; i++) {
			(new Indexer(queue)).start();
		}
	}
}

class FileCrawler extends Thread {
	private final BlockingQueue<File> fileQueue; 
	private final FileFilter fileFilter; 	
	private final File root;
	private ExecutorService exec = null;

	FileCrawler (BlockingQueue<File> queue, FileFilter filter, File root, ExecutorService exec) {
		this.fileQueue = queue;
		this.fileFilter = filter;
		this.root = root;
		this.exec = exec;
	}
	
	public void run() {
		try {
			crawl(root, exec);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void crawl(File root, Executor exec) throws InterruptedException {
		File[] entries = root.listFiles(fileFilter);
		
		if (entries != null) {
			for (File entry : entries) {
				if (entry.isDirectory()) {
					exec.execute(new Runnable() {
						@Override
						public void run() {
							try {
								crawl(entry, exec);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					});
				}
				else {
					fileQueue.put(entry);	
				}
			}
		}
	}
}

class Indexer extends Thread {
	private final BlockingQueue<File> queue;
	
	public Indexer (BlockingQueue<File> queue) {
		this.queue = queue;
	}
	
	public void run() {
		try {
			while (true) {
				indexFile(queue.take());
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void indexFile(File file) {
		// TODO Auto-generated method stub	
	}
}