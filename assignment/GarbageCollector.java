package assignment;

public class GarbageCollector implements Comparable<GarbageCollector>{
    private int id;
    private int totalTimeWaited;
    private int numberOfRidesRidden;

    /**
     * constructor
     * GarbageCollector gets individual People (Persons) information as its being collected
     * @param id an id that could apply to a Person
     * @param totalTimeWaited the total amount of time the Person waited
     * @param numberOfRidesRidden the total amount of rides the Person rode on
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GarbageCollector that = (GarbageCollector) o;

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
}
