public class GrabStocks {
    public static void main(String[] args) {
        StockGrabber stockGrabber = new StockGrabber();

        StockObserver stockObserver1 = new StockObserver(stockGrabber);

        stockGrabber.setIBMPrice(197.00);
        stockGrabber.setAAPLPrice(677.60);
        stockGrabber.setGOOGPrice(676.40);

        StockObserver stockObserver2 = new StockObserver(stockGrabber);

        stockGrabber.setIBMPrice(197.00);
        stockGrabber.setAAPLPrice(677.60);
        stockGrabber.setGOOGPrice(676.40);

        stockGrabber.unregister(stockObserver2);

        stockGrabber.setIBMPrice(197.00);
        stockGrabber.setAAPLPrice(677.60);
        stockGrabber.setGOOGPrice(676.40);

        System.out.println("multi-threading method");
        Runnable wantsIBM = new StockManagement(stockGrabber, "IBM", 197.00);
        Runnable wantsAAPL = new StockManagement(stockGrabber, "AAPL", 677.60);
        Runnable wantsGOOG = new StockManagement(stockGrabber, "GOOG", 676.40);

        new Thread(wantsIBM).start();
        new Thread(wantsAAPL).start();
        new Thread(wantsGOOG).start();
    }
}
