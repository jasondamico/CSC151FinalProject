package assignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/*
The Attraction Class creates a ride, given the information of its name, popularity (1-5 stars),
how many people the ride can hold (capacity), and how long it takes for the ride to run fully (duration).

Every park should have a list of attractions, this is where they get made.
 */
public class Attraction implements Comparable<Attraction> {

    // PRIVATE INSTANCE VARIABLES

    private String name;
    private Queue<Person> regular;
    private Queue<Person> fast;
    private int popularityScore;
    private int capacity;
    private Person[] onRide;
    private boolean currentlyRunning;
    private int duration;
    private int rideStartTime;
    private int currentlyInLine;
    // TODO: Add currently in regular line, fast line

    /** Constructor of Attraction
     * @param name Name of the attraction
     * @param popularityScore 1-5 stars, how popular the ride is (5 best, 1 worst)
     * @param capacity how many people the ride can fit at a time
     * @param duration how long the ride runs for
     */
    public Attraction(String name, int popularityScore, int capacity, int duration) {
        this.setName(name);
        this.setPopularityScore(popularityScore);
        this.setCapacity(capacity);
        this.setDuration(duration);

        // Values initialized to empty values, do not take argument values
        this.regular = new LinkedList<>();
        this.fast = new LinkedList<>();
        this.onRide = new Person[capacity];
        this.currentlyRunning = false;
        this.rideStartTime = -1;
        this.currentlyInLine = 0;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public int getPopularityScore() {
        return popularityScore;
    }

    private void setPopularityScore(int popularityScore) {
        if (popularityScore >= 1 && popularityScore <= 5) {
            this.popularityScore = popularityScore;
        } else {
            throw new IllegalArgumentException("Popularity score must be between 1 and 5.");
        }
    }

    public int getCapacity() {
        return capacity;
    }

    private void setCapacity(int capacity) {
        if (capacity > 0) {
            this.capacity = capacity;
        } else {
            throw new IllegalArgumentException("Capacity must be greater than or equal to 1.");
        }
    }

    public int getDuration() {
        return duration;
    }

    private void setDuration(int duration) {
        if (duration > 0) {
            this.duration = duration;
        } else {
            throw new IllegalArgumentException("Duration must be greater than or equal to 1.");
        }
    }

    /**
     * isCurrentlyRunning
     * @return if the ride is running or not (True/False)
     */
    public boolean isCurrentlyRunning() {
        return currentlyRunning;
    }

    private void setCurrentlyRunning(boolean currentlyRunning) {
        this.currentlyRunning = currentlyRunning;
    }

    /**
     * getRideStartTime
     * @return time the ride started at
     */
    public int getRideStartTime() {
        return rideStartTime;
    }

    private void setRideStartTime(int rideStartTime) {
        if (rideStartTime >= 0) {
            this.rideStartTime = rideStartTime;
        } else {
            throw new IllegalArgumentException("Ride start time must be greater than or equal to 0.");
        }
    }

    public int getCurrentlyInLine() {
        return currentlyInLine;
    }

    public void setCurrentlyInLine(int currentlyInLine) {
        if (currentlyInLine >= 0) {
            this.currentlyInLine = currentlyInLine;
        } else {
            throw new IllegalArgumentException("Number of people currently in line must be greater than or equal to 0.");
        }
    }

    public void addPersonToLine(Person person, boolean hasFastPass) {
        if (hasFastPass) {
            this.fast.offer(person);

            // Pops FastPass
            person.removeFastPass();
        } else {
            this.regular.offer(person);
        }

        // Initialize waitTime value for person
        person.addWaitTime(new WaitTime(Simulations.currentTime.getCurrentTime()));

        this.currentlyInLine++;
    }

    /**
     * Returns an array of all of the people on the ride (potentially with empty seats).
     * @return An array of current riders of the ride.
     */
    public Person[] getOnRide() {
        return onRide.clone();
    }

    public int numPeopleOnRide() {
        int peopleOnRide = 0;

        for (Person p : this.onRide) {
            if (p != null) {
                peopleOnRide++;
            } else {
                return peopleOnRide;
            }
        }

        return peopleOnRide;
    }

    public int compareTo(Attraction otherAttraction) {
        if (this.getPopularityScore() > otherAttraction.getPopularityScore()) {
            return 1;
        } else if (this.getPopularityScore() < otherAttraction.getPopularityScore()) {
            return -1;
        } else {
            // Assertion: the two popularity scores are equal
            return 0;
        }
    }

    /**
     * startRide
     * Precondition: Attraction is not currently running.
     */
    public void startRide() {
        int seatsFilled = 0;
        boolean queuesEmpty = false;

        // Loop until all seats filled
        while (seatsFilled < capacity && !queuesEmpty) {
            // Checking to see if either of the lines have people in them
            if (this.fast.peek() != null || this.regular.peek() != null) {
                Person p;

                // Initializing person
                if (this.fast.peek() != null) {
                    p = this.fast.remove();
                } else {
                    p = this.regular.remove();
                }

                // Set ending wait time of rider leaving queue
                p.peekLastWaitTime().setEndWait(Simulations.currentTime.getCurrentTime());

                // Put person on ride
                this.onRide[seatsFilled] = p;

                p.addRideVisited(this);

                seatsFilled++;
                this.currentlyInLine--;
            } else {
                // Assertion: both queues are empty
                queuesEmpty = true;
            }
        }

        if (seatsFilled > 0) {
            this.setCurrentlyRunning(true);
            this.setRideStartTime(Simulations.currentTime.getCurrentTime());
        } else {
            this.setCurrentlyRunning(false);
        }
    }

    /**
     * checkRuntime
     * Checks if the ride has finished running
     * @return The people leaving the ride if finished, or an empty list of people if not
     */
    public Person[] checkRuntime() {
        if (Simulations.currentTime.getCurrentTime() >= this.getRideStartTime() + this.getDuration()) {
            // Assertion: ride has finished
            Person[] leavingRiders = this.onRide;
            this.onRide = new Person[capacity];

            this.setCurrentlyRunning(false);
            return leavingRiders;
        } else {
            // Assertion: ride has not finished
            return new Person[0];
        }
    }

    /**
     * getWaitTime
     * Returns the current wait time for the ride based on the current time.
     * @return
     */
    public int getWaitTime() {
        int lineWait = (this.currentlyInLine / this.getCapacity()) * this.getDuration();
        
        if (this.isCurrentlyRunning()) {
            return lineWait + this.getUntilRideDone();
        }

        return lineWait;
    }

    /**
     * getUntilRideDone
     * @return how much time is left until the ride is done running
     */
    public int getUntilRideDone() {
        int endTime = this.getDuration() + this.getRideStartTime();

        int calc = endTime - Simulations.currentTime.getCurrentTime();

        // Returns 0 if the end time is less than the current time, i.e., if the ride end time has already passed
        return Math.max(calc, 0);
    }


    /**
     * closeAttraction
     * When the attraction needs to close, take everyone off the ride and out of line
     * @return ArrayList of people leaving the line & the ride
     */
    public ArrayList<Person> closeAttraction() {
        // People who were in the lines
        ArrayList<Person> peopleLeavingRide = new ArrayList<>(this.regular);
        peopleLeavingRide.addAll(this.fast);

        // Add all people who were on the ride
        for (int i = 0; i < this.onRide.length; i++) {
            Person p = this.onRide[i];

            if (p == null) {
                break;
            } else {
                peopleLeavingRide.add(p);
            }
        }

        return peopleLeavingRide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attraction that = (Attraction) o;

        if (popularityScore != that.popularityScore) return false;
        if (capacity != that.capacity) return false;
        if (currentlyRunning != that.currentlyRunning) return false;
        if (duration != that.duration) return false;
        if (rideStartTime != that.rideStartTime) return false;
        if (currentlyInLine != that.currentlyInLine) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (regular != null ? !regular.equals(that.regular) : that.regular != null) return false;
        if (fast != null ? !fast.equals(that.fast) : that.fast != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(onRide, that.onRide);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (regular != null ? regular.hashCode() : 0);
        result = 31 * result + (fast != null ? fast.hashCode() : 0);
        result = 31 * result + popularityScore;
        result = 31 * result + capacity;
        result = 31 * result + Arrays.hashCode(onRide);
        result = 31 * result + (currentlyRunning ? 1 : 0);
        result = 31 * result + duration;
        result = 31 * result + rideStartTime;
        result = 31 * result + currentlyInLine;
        return result;
    }

    @Override
    public String toString() {
        String toReturn = this.getName() + ":\n========================\n";
        toReturn += "Capacity: " + this.getCapacity() + "\n";
        toReturn += "Wait time: " + this.getWaitTime() + "\n";
        toReturn += "Popularity score: " + this.getPopularityScore() + "\n";
        toReturn += "Ride duration: " + this.getDuration() + "\n";

        toReturn += "People currently on ride (" + this.numPeopleOnRide() + "):\n";

        for (int i = 0; i < this.numPeopleOnRide(); i++) {
            Person p = this.onRide[i];
            toReturn += "\t" + p.toString().replaceAll("\n", "\n\t") + "\n\n";
        }

        toReturn += "Total People in queue: " + this.getCurrentlyInLine() + "\n\n";

        return toReturn;
    }

    public static void main(String[] args) {
        System.out.println(new Attraction("Foo", 10, 15, 5));
    }


}
