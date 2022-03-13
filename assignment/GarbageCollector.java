package assignment;

public class GarbageCollector implements Comparable<GarbageCollector>{
	private int id;
	private int totalTimeWaited;
	private int numberOfRidesRidden;
	
	//his name is randy
	public GarbageCollector(int id, int totalTimeWaited, int numberOfRidesRidden) {
		this.id = id;
		this.totalTimeWaited = totalTimeWaited;
		this.numberOfRidesRidden = numberOfRidesRidden;
	}
	
	public String toString() {
		return this.id + ", " + this.totalTimeWaited;
	}
	
	public int compareTo(GarbageCollector other) {
		if(this.getId() == other.getId()) {
			return 0;
		}
		else if(this.getId() < other.getId()) {
			return -1;
		}
		else {
			return 1;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTotalTimeWaited() {
		return totalTimeWaited;
	}

	public void setTotalTimeWaited(int totalTimeWaited) {
		this.totalTimeWaited = totalTimeWaited;
	}
	
	public int getNumberOfRidesRidden() {
		return this.numberOfRidesRidden;
	}
	
	
}
