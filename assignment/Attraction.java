
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
    private int waitTime;

    /** Constructor of Attraction
     * @param name Name of the attraction
     * @param popularityScore 1-5 stars, how popular the ride is (5 best, 1 worst)
     * @param capacity how many people the ride can fit at a time
     * @param duration how long the ride runs for
     */
    public Attraction(String name, int popularityScore, int capacity, int duration) {
        this.name = name;
        this.popularityScore = popularityScore;
        this.capacity = capacity;
        this.duration = duration;

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
        this.popularityScore = popularityScore;
    }

    public int getCapacity() {
        return capacity;
    }

    private void setCapacity(int capacity) {
        this.capacity = capacity;
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

    public int getDuration() {
        return duration;
    }

    private void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRideStartTime() {
        return rideStartTime;
    }

    public void setRideStartTime(int rideStartTime) {
        this.rideStartTime = rideStartTime;
    }

    public int getCurrentlyInLine() {
        return currentlyInLine;
    }

    public void setCurrentlyInLine(int currentlyInLine) {
        this.currentlyInLine = currentlyInLine;
    }

    /**
     * addPersonToLine adds given person to line, and whether or not they have a fast pass
     * @param person The given person
     * @param hasFastPass whether or not the person has a fastpass
     */
    public void addPersonToLine(Person person, boolean hasFastPass) {
        if ((hasFastPass) && (person.peekFastPassToUse().getAttractionName().equals("UNIVERSAL") || person.peekFastPassToUse().getAttractionName().equals(this.name))) {
            this.fast.offer(person);

            // Pops FastPass
            person.removeFastPass();
        } else {
            this.regular.offer(person);
        }

        // Initialize waitTime value for person
        person.addWaitTime(new WaitTime(Simulations.currentTime.getCurrentTime()));

        this.currentlyInLine++;

        this.setWaitTime(Simulations.currentTime.getCurrentTime());
    }

    /**
     * numPeopleOnRide
     * @return the number of people currently on the ride
     */
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

    /**
     * compareTo Compares this attraction and a given Attraction based off of popularity scores
     * @return 0 if given Attraction's popularity score equals this's popularity score, 
     * 1 if this's popularity score is greater than given attraction's, -1 if this's popularity score is less than the given attraction's
     */
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
     * startRide Gets people out of the queues and onto the ride, and starts running it
     * Precondition: Attraction is not currently running.
     * Time complexity: O(n), where n = the capacity of the ride
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
                // First fill half the ride with people out of the fast lane
                // Then fill the rest of the ride with as many people out of the regular lane as possible
                // If the regular lane is empty and the required number of fast past people are already on, the rest are allowed to be fast pass users
                if (this.fast.peek() != null && seatsFilled < this.capacity*.66) {
                    p = this.fast.remove(); 
                }
                
                else if(this.regular.peek() != null){
                    p = this.regular.remove();   
                }
                
                else {
                	p = this.fast.remove();
                }

                // Set ending wait time of rider leaving queue
                p.peekLastWaitTime().setEndWait(Simulations.currentTime.getCurrentTime());

                // Put person on ride
                this.onRide[seatsFilled] = p;

                p.addRideVisited(this);

                seatsFilled++;
                this.currentlyInLine--;

                // Update wait time of ride
                this.setWaitTime(Simulations.currentTime.getCurrentTime());
            } else {
                // Assertion: both queues are empty
                queuesEmpty = true;
            }
        }

        if (seatsFilled > 0) {
            this.setCurrentlyRunning(true);
        } else {
            this.setCurrentlyRunning(false);
        }
    }

    /**
     * checkRuntime
     * Checks if the ride has finished running
     * @param currentTime
     * @return The people leaving the ride if finished, or an empty list of people if not
     */
    public Person[] checkRuntime(int currentTime) {
        if (currentTime == this.getRideStartTime() + this.getDuration()) {
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
     * setWaitTime
     * sets the current wait time for the ride
     * @param currentTime
     * @return current wait time for ride
     */
    public int setWaitTime(int currentTime) {
        int lineWait = this.getDuration() + (this.currentlyInLine / this.getCapacity()) * this.getDuration();
        // int untilRideIsDone = this.getUntilRideDone();
        // this.waitTime = untilRideIsDone + lineWait;
        // return waitTime;

        this.waitTime = lineWait;
        return lineWait;
    }
    
    public int getWaitTime() {
    	return this.waitTime;
    }

    /**
     * getUntilRideDone
     * @return how much time is left until the ride is done running
     */
    public int getUntilRideDone() {
        int endTime = this.getDuration() + this.getRideStartTime();

        return endTime - Simulations.currentTime.getCurrentTime();
    }

    /**
     * closeAttraction
     * When the attraction needs to close, take everyone off the ride and out of line
     * @return ArrayList of people leaving the line & the ride
     * Time complexity: O(n) where n = the number of people in line for the ride and on the ride
     */
    public ArrayList<Person> closeAttraction() {
        // People who were in the lines
    	ArrayList<Person> peopleLeavingRide = new ArrayList<>(this.regular);
        peopleLeavingRide.addAll(this.fast);
        
        for(int i = 0; i < peopleLeavingRide.size(); i++) {
        	if(peopleLeavingRide.get(i).peekLastWaitTime() != null) {
        		peopleLeavingRide.get(i).peekLastWaitTime().setEndWait(Simulations.currentTime.getCurrentTime());
        	}
        	
        }
        
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
        toReturn += "Ride currently running: " + this.isCurrentlyRunning() + "\n";
        if(this.isCurrentlyRunning()) {
            toReturn += "Time until ride finished: "  + this.getUntilRideDone() + "\n";
        }
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
