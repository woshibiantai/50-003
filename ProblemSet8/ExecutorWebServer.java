import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
 
public class ExecutorWebServer {
	private static int numOfCPU = Runtime.getRuntime().availableProcessors();
	private static double utilisation = 0.75;
	private static double WCratio = 5;
	private static final int NTHREADS = (int) (numOfCPU*utilisation*(1+WCratio));
	private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);
	
    public static void main(String[] args) throws Exception {
		ServerSocket socket = new ServerSocket(4321, 1000);
		System.out.println("M = " + NTHREADS);

		while (true) {
			final Socket connection = socket.accept();
			Runnable task = new Runnable () {
				public void run() {
					try {
						handleRequest(connection);
					} catch (IOException e) {
						System.out.println("Could not handle request: IO Exception");
					}
				}
			};
			
			exec.execute(task);
		}
    }

	protected static void handleRequest(Socket connection) throws IOException {
		// Completed factorisation request
		PrintWriter out = new PrintWriter(connection.getOutputStream(),true);
		BufferedReader in =
				new BufferedReader(
				new InputStreamReader(connection.getInputStream()));
		BigInteger input = new BigInteger(in.readLine());
		BigInteger answer = factor(input);
		out.println(answer);
		out.flush();
		out.close();
		in.close();
		connection.close();
	}

	private static BigInteger factor(BigInteger n) {
		BigInteger i = new BigInteger("2");
		BigInteger zero = new BigInteger("0");

		while (i.compareTo(n) < 0) {
			if (n.remainder(i).compareTo(zero) == 0) {
				return i;
			}

			i = i.add(new BigInteger("1"));
		}

		assert(false);
		return null;
	}
}