package assignment;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Random;
import java.util.Stack;
import java.lang.Math;

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

    private final int NO_BALKPOINT = 10000;
    private final int MIN_STAY = 60;

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
            this.setMinStay(minStayTime);

            // Max stay will be when the park closes (i.e., everyone leaves when park closes)
            this.setMaxStay(Simulations.parkHours);
        }

        this.setWantsMostRides((Math.random() * 2 > 1) ? true : false);
        this.setWantsPopularRides((Math.random() * 2 > 1) ? true : false);
        this.setBalkPoint((int)(Math.random()*240));
        this.ridesVisited = new ArrayList<>();
        this.fastPasses = new ArrayList<>();
        this.waitTimes = new Stack<>();
    }

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

    public boolean isWantsPopularRides() {
        return wantsPopularRides;
    }

    public void setWantsPopularRides(boolean wantsPopularRides) {
        this.wantsPopularRides = wantsPopularRides;
    }

    public boolean isWantsMostRides() {
        return wantsMostRides;
    }

    public void setWantsMostRides(boolean wantsMostRides) {
        this.wantsMostRides = wantsMostRides;
    }

    public void addRideVisited(Attraction attraction) {
        this.ridesVisited.add(attraction);
    }

    public void addFastPass(FastPass fp) {
        this.fastPasses.add(fp);
    }

    public void addWaitTime(WaitTime waitTime) {
        this.waitTimes.add(waitTime);
    }

    public WaitTime peekLastWaitTime() {
        try {
            return this.waitTimes.peek();
        } catch (EmptyStackException e) {
            return null;
        }
    }

    public WaitTime popLastWaitTime() {
        try {
            return this.waitTimes.pop();
        } catch (EmptyStackException e) {
            return null;
        }
    }

    public FastPass removeFastPass() {
        if (this.fastPasses.size() > 0) {
        	FastPass toBeUsed = fastPasses.get(0);
            this.fastPasses.remove(fastPasses.get(0));
            return toBeUsed;
        } else {
            return null;
        }
    }

    public int numFastPasses() {
        return this.fastPasses.size();
    }

    public Attraction pickAttraction(){
        return null;
    }

    @Override
    public String toString() {
        String toReturn = "PERSON ID: " + this.getId() + "\n";
        toReturn += "RIDES VISITED: ";

        for (int i = 0; i < this.ridesVisited.size(); i++) {
            Attraction a = this.ridesVisited.get(i);

            if (i != this.ridesVisited.size() - 1) {
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

        return toReturn;
    }

    public boolean hasFastPass() {
        if(numFastPasses() > 0) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(new Person(i));
        }
    }
}
