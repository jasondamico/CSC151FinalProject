package assignment;

public class GarbageCollector implements Comparable<GarbageCollector>{
	private int id;
	private int totalTimeWaited;
	
	public GarbageCollector(int id, int totalTimeWaited) {
		this.id = id;
		this.totalTimeWaited = totalTimeWaited;
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
}
