/**
 * We affirm that we have carried out my academic endeavors with full academic honesty. Signed, Jason D'Amico, Maggie Kelley and Hawkeye Nadel
 */

package assignment;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;
import java.lang.Math;

/*
The Person Class is meant to create an object (person) who will go on Attractions within the Park
The Person waits in the lines, and goes on rides.
Each Person has their own preferences of what rides to go on, how long they are willing to wait in a line,
and how long they will stay in the park.
 */
public class Person {
    // PIVs

    private int id;
    //private int durationOfStay;
    private int minStay;
    private int maxStay;
    private boolean wantsPopularRides;
    private boolean wantsMostRides;
    private int arrivalTime = 0;
    private ArrayList<Attraction> ridesVisited;
    private ArrayList<FastPass> fastPasses;
    private int balkPoint;
    private Stack<WaitTime> waitTimes;
    private boolean isPremium;

    private final int NO_BALKPOINT = 10000;
    private final int MIN_STAY = 60;

    /**
     * empty constructor
     */
    public Person()
    {
        this.setId(0);
        this.setMinStay(0);
        this.setMaxStay(10);
        this.setBalkPoint(NO_BALKPOINT);
        this.setWantsMostRides(false);
        this.setWantsPopularRides(false);
        this.arrivalTime = Simulations.currentTime.getCurrentTime();
        this.minStay = this.arrivalTime;
        this.maxStay = Simulations.parkHours;

        this.ridesVisited = new ArrayList<>();
        this.fastPasses = new ArrayList<>();
        this.waitTimes = new Stack<>();
        this.balkPoint = 0;
    }

    /**
     * constructor
     * randomizes everything but id
     * @param id int person id
     */
    public Person(int id) {
        this.id = id;

        this.arrivalTime = Simulations.currentTime.getCurrentTime();

        if(this.arrivalTime+MIN_STAY >= Simulations.parkHours) {
            this.setMinStay(Simulations.parkHours);
            this.setMaxStay(Simulations.parkHours);
        }
        else {
            // Calculating hours left to stay past minimum stay time
            int hoursLeft = Simulations.parkHours - Simulations.currentTime.getCurrentTime() - MIN_STAY;

            // Min stay time is at least 60 minutes after opening, could be maximum of closing time
            int minStayTime = (int) (hoursLeft * Math.random()) + this.arrivalTime  + MIN_STAY;
            if(minStay > Simulations.parkHours) {
                minStay = Simulations.parkHours;
            }
            this.setMinStay(minStayTime);

            // Max stay will be when the park closes (i.e., everyone leaves when park closes)
            this.setMaxStay(Simulations.parkHours);
        }

        this.setWantsMostRides((Math.random() * 2 > 1) ? true : false);
        this.setWantsPopularRides((Math.random() * 2 > 1) ? true : false);
        this.setBalkPoint((int)(Math.random()*240) + 120);
        this.ridesVisited = new ArrayList<>();
        this.fastPasses = new ArrayList<>();
        this.waitTimes = new Stack<>();
    }

    /**
     * constructor
     * @param id person's number id
     * @param minStay minimum time they will stay for (in minutes)
     * @param maxStay maximum time they will stay for (in minutes)
     * @param balkPoint the longest time they'll wait for (in minutes)
     * @param wantsPopularRides True/False if the person wants the most popular rides
     * @param wantsMostRides True/False if the person wants the most rides
     */
    public Person(int id, int minStay, int maxStay, int balkPoint, boolean wantsPopularRides, boolean wantsMostRides) {
        this.id = id;
        this.minStay = minStay;
        this.maxStay = maxStay;
        this.balkPoint = balkPoint;
        this.wantsPopularRides = wantsPopularRides;
        this.wantsMostRides = wantsMostRides;

        this.ridesVisited = new ArrayList<>();
        this.fastPasses = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//     public int getDurationOfStay() {
//         return this.durationOfStay;
//     }

//     public void setDurationOfStay(int durationOfStay) {
//         this.durationOfStay = durationOfStay;
//     }

    public int getMinStay(){
        return this.minStay;
    }

    public void setMinStay(int minStay){
        this.minStay = minStay;
    }

    public int getMaxStay(){
        return this.maxStay;
    }

    public void setMaxStay(int maxStay){
        this.maxStay = maxStay;
    }

    public int getBalkPoint(){
        return this.balkPoint;
    }

    public void setBalkPoint(int balkPoint){
        this.balkPoint = balkPoint;
    }

    /**
     * isWantsPopularRides
     * @return boolean if they want the most popular rides (True/False)
     */
    public boolean isWantsPopularRides() {
        return wantsPopularRides;
    }

    public void setWantsPopularRides(boolean wantsPopularRides) {
        this.wantsPopularRides = wantsPopularRides;
    }

    /**
     * isWantsMostRides
     * @return boolean if they want the most rides/disregard populartity, think about wait times (True/False)
     */
    public boolean isWantsMostRides() {
        return wantsMostRides;
    }

    public void setWantsMostRides(boolean wantsMostRides) {
        this.wantsMostRides = wantsMostRides;
    }

    /**
     * addRideVisited
     * adds the ride the person went on to their list of rides they've visted
     * @param attraction Attraction the person went on
     */
    public void addRideVisited(Attraction attraction) {
        this.ridesVisited.add(attraction);
    }

    /**
     * addFastPass
     * give the person a fastpass
     * @param fp FastPass to give to the person
     */
    public void addFastPass(FastPass fp) {
        this.fastPasses.add(fp);
    }

    /**
     * addWaitTime
     * adds new WaitTime to stack of all the waits the person as been through
     * @param waitTime the WaitTime they just went through
     */
    public void addWaitTime(WaitTime waitTime)
    {
        this.waitTimes.add(waitTime);
    }

    /**
     * peekLastWaitTime
     * checks the most recent WaitTime
     * @return most recent WaitTime
     */
    public WaitTime peekLastWaitTime() {
        try {
            return this.waitTimes.peek();
        } catch (EmptyStackException e) {
            return null;
        }
    }

    /**
     * popLastWaitTime
     * removes the most recent WaitTime from the person's list of waits
     * @return the most recent WaitTime
     */
    public WaitTime popLastWaitTime() {
        try {
            return this.waitTimes.pop();
        } catch (EmptyStackException e) {
            return null;
        }
    }

    /**
     * removeFastPass
     * takes away a fastpass after it is used
     * @return FastPass to be removed
     */
    public FastPass removeFastPass() {
        if (this.fastPasses.size() > 0) {
            FastPass toBeUsed = fastPasses.get(0);
            this.fastPasses.remove(fastPasses.get(0));
            return toBeUsed;
        } else {
            return null;
        }
    }

    /**
     * numFastPasses
     * @return int of fastpasses a person has
     */
    public int numFastPasses() {
        return this.fastPasses.size();
    }

    @Override
    public String toString() {
        String toReturn = "PERSON ID: " + this.getId() + "\n";
        toReturn += "RIDES VISITED: ";

        ArrayList<Attraction> ridesRidden = this.getRidesRidden();

        for (int i = 0; i < ridesRidden.size(); i++) {
            Attraction a = ridesRidden.get(i);

            if (i != ridesRidden.size() - 1) {
                toReturn += a.getName() + ", ";
            } else {
                toReturn += a.getName();
            }
        }

        toReturn += "\nMin leave time: " + this.getMinStay();
        toReturn += "\nMax leave time: " + this.getMaxStay();
        toReturn += "\nNumber of fast passes: " + this.numFastPasses();
        toReturn += "\nWants popular rides: " + this.isWantsPopularRides();
        toReturn += "\nWants most rides: " + this.isWantsMostRides();
        toReturn += "\nBalk point: " + this.balkPoint;

        return toReturn;
    }

    /**
     * hasFastPass
     * @return boolean (true/false) if a person has fastpasses at all
     */
    public boolean hasFastPass() {
        if(numFastPasses() > 0) {
            return true;
        }
        return false;
    }

    public boolean getIsPremium() {
        return this.isPremium;
    }

    public void setIsPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }

    /**
     * getRidesRidden
     * @return ArrayList of rides the person rode
     */
    public ArrayList<Attraction> getRidesRidden(){
        return new ArrayList<Attraction>(this.ridesVisited);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (minStay != person.minStay) return false;
        if (maxStay != person.maxStay) return false;
        if (wantsPopularRides != person.wantsPopularRides) return false;
        if (wantsMostRides != person.wantsMostRides) return false;
        if (arrivalTime != person.arrivalTime) return false;
        if (balkPoint != person.balkPoint) return false;
        if (isPremium != person.isPremium) return false;
        if (NO_BALKPOINT != person.NO_BALKPOINT) return false;
        if (MIN_STAY != person.MIN_STAY) return false;
        if (ridesVisited != null ? !ridesVisited.equals(person.ridesVisited) : person.ridesVisited != null)
            return false;
        if (fastPasses != null ? !fastPasses.equals(person.fastPasses) : person.fastPasses != null) return false;
        return waitTimes != null ? waitTimes.equals(person.waitTimes) : person.waitTimes == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + minStay;
        result = 31 * result + maxStay;
        result = 31 * result + (wantsPopularRides ? 1 : 0);
        result = 31 * result + (wantsMostRides ? 1 : 0);
        result = 31 * result + arrivalTime;
        result = 31 * result + (ridesVisited != null ? ridesVisited.hashCode() : 0);
        result = 31 * result + (fastPasses != null ? fastPasses.hashCode() : 0);
        result = 31 * result + balkPoint;
        result = 31 * result + (waitTimes != null ? waitTimes.hashCode() : 0);
        result = 31 * result + (isPremium ? 1 : 0);
        result = 31 * result + NO_BALKPOINT;
        result = 31 * result + MIN_STAY;
        return result;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(new Person(i));
        }
    }
}
