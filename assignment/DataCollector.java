/**
 * We affirm that we have carried out my academic endeavors with full academic honesty. Signed, Jason D'Amico, Maggie Kelley and Hawkeye Nadel
 */

package assignment;

/*
The DataCollector class effectively holds a Person's information as it is being collected, so it doesn't get lost
 */
public class DataCollector implements Comparable<DataCollector>{
	private int id;
	private int totalTimeWaited;
	private int numberOfRidesRidden;
	
    /**
     * constructor
     * DataCollector gets individual People (Persons) information as its being collected
     * @param id an id that could apply to a Person
     * @param totalTimeWaited the total amount of time the Person waited
     * @param numberOfRidesRidden the total amount of rides the Person rode on
     */
	public DataCollector(int id, int totalTimeWaited, int numberOfRidesRidden) {
		this.id = id;
		this.totalTimeWaited = totalTimeWaited;
		this.numberOfRidesRidden = numberOfRidesRidden;
	}
	
	
	public String toString() {
		return this.id + ", " + this.totalTimeWaited;
	}
	
	/**
	 * compareTo Compares two DataCollector objects based on the person's ID they're keeping track of
	 * @return 0 if the ID's are equal, 1 if this's ID is greater than other's, and -1 if this's ID is less than other's
	 */
	public int compareTo(DataCollector other) {
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
	
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        DataCollector that = (DataCollector) o;

	        if (id != that.id) return false;
	        if (totalTimeWaited != that.totalTimeWaited) return false;
	        return numberOfRidesRidden == that.numberOfRidesRidden;
	    }

	    @Override
	    public int hashCode() {
	        int result = id;
	        result = 31 * result + totalTimeWaited;
	        result = 31 * result + numberOfRidesRidden;
	        return result;
	    }

	    public static void main(String[] args)
	    {
	        DataCollector randy = new DataCollector(0, 10, 3);
	        DataCollector andy = new DataCollector(1, 20, 2);

	        if (randy.compareTo(andy) != 0)
	        {
	            System.out.println("Randy and Andy aren't the same person");
	        }
	        else
	        {
	            System.out.println("Randy and Andy ARE the same person!");
	        }
	    }
	
}
