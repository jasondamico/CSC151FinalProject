package assignment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
    // TODO: Add currently in regular line, fast line

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

        this.setWaitTime(Simulations.currentTime.getCurrentTime());
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

    public int setWaitTime(int currentTime) {
        int lineWait = (this.currentlyInLine / this.getCapacity()) * this.getDuration();
        // int untilRideIsDone = this.getUntilRideDone();
        // this.waitTime = untilRideIsDone + lineWait;
        // return waitTime;

        this.waitTime = lineWait;
        return lineWait;
    }
    
    public int getWaitTime() {
    	return this.waitTime;
    }

    public int getUntilRideDone() {
        int endTime = this.getDuration() + this.getRideStartTime();

        return endTime - Simulations.currentTime.getCurrentTime();
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
    
}
