import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class test extends Thread {
	TrackerFixed tracker;
	
	public test (TrackerFixed tra) {
		this.tracker = tra;
	}
	
	public void run () {
		TrackerFixed.MutablePoint loc = tracker.getLocation("somestring");
		loc.x = -1212000;
	}
}

//is this class thread-safe?
public class TrackerFixed {

	//@guarded by ???
	private final ConcurrentHashMap<String, MutablePoint> locations;
	
	//the reference locations, is it going to be an escape?
	public TrackerFixed(Map<String, MutablePoint> locations) {

		this.locations = concurrentInitialiser(locations);
	}

	private ConcurrentHashMap<String, MutablePoint> concurrentInitialiser(Map<String, MutablePoint> originalMap) {
		ConcurrentHashMap<String, MutablePoint> outputMap = new ConcurrentHashMap<>();

		for (String key : originalMap.keySet()) {
			MutablePoint originalValue = originalMap.get(key);
			MutablePoint value = new MutablePoint(originalValue.x, originalValue.y);
			outputMap.put(key,value);
		}

		return outputMap;
	}
	
	//is this an escape?
	private Map<String, MutablePoint> getLocations () {
		return concurrentInitialiser(locations);
	}
	
	//is this an escape?
	public MutablePoint getLocation(String id) {
		MutablePoint loc = concurrentInitialiser(locations).get(id);
		return loc;
	}
	
	private synchronized void setLocation (String id, int x, int y) {
		MutablePoint loc = concurrentInitialiser(locations).get(id);
		
		if (loc == null) {
			throw new IllegalArgumentException ("No such ID: " + id);			
		}
		
		loc.x = x;
		loc.y = y;
	}
	
	//this class is not thread-safe (why?) and keep it unmodified.
	class MutablePoint {
		public int x, y;

		public MutablePoint (int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public MutablePoint (MutablePoint p) {
			this.x = p.x;
			this.y = p.y;
		}
	}
}
