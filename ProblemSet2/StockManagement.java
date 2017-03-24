public class StockManagement implements Runnable {
    private String stocks;
    private double price;
    private long sleepTime;

    private iSubject stockGrabber;

    public StockManagement(iSubject stockGrabber, String newStocks, double newPrice) {
        this.stockGrabber = stockGrabber;
        stocks = newStocks;
        price = newPrice;
        sleepTime = 2000;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (stocks == "IBM"){
            ((StockGrabber) stockGrabber).setIBMPrice(price);
        }
        if (stocks == "AAPL") {
            ((StockGrabber) stockGrabber).setAAPLPrice(price);
        }
        if (stocks == "GGOG"){
            ((StockGrabber) stockGrabber).setGOOGPrice(price);
        }
    }
}
