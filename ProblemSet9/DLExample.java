/// Cohort Question 3
/*
    DEADLOCK EXPLANATION:
    e.g. Taxi t executes the method taxi.setLocation(location)
    Dispatcher dispatcher executes the method dispatcher.getImage()

    1. t acquires the lock for itself to execute setLocation()
    2. dispatcher acquires the lock for itself to execute getImage()
    3. t tries to acquire the lock for dispatcher to run dispatcher.notifyAvailable(this)
    4. dispatcher tries to acquire the lock for t to run image.drawMarker(t.getLocation())
    5. Both will fail to acquire the locks that each other is holding. Hence, a deadlock occurs
*/

import java.util.HashSet;
import java.util.Set;

public class DLExample {

}

class Taxi {
    private Point location, destination;
    private final Dispatcher dispatcher;

    public Taxi(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

	public synchronized Point getLocation() {
        return location;
    }

    public synchronized void setLocation(Point location) {
        this.location = location;
        if (location.equals(destination))
            dispatcher.notifyAvailable(this);
    }

    public synchronized Point getDestination() {
        return destination;
    }
}

class Dispatcher {
    private final Set<Taxi> taxis;
    private final Set<Taxi> availableTaxis;

    public Dispatcher() {
        taxis = new HashSet<Taxi>();
        availableTaxis = new HashSet<Taxi>();
    }

    public synchronized void notifyAvailable(Taxi taxi) {
        availableTaxis.add(taxi);
    }

    public synchronized Image getImage() {
        Image image = new Image();
        for (Taxi t : taxis)
            image.drawMarker(t.getLocation());
        return image;
    }
}

class Image {
    public void drawMarker(Point p) {
    }
}

class Point {
	
}

