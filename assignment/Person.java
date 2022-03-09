package assignment;

import java.util.ArrayList;
import java.util.Stack;

public class Person {
    // PIVs

    private int id;
    private int durationOfStay;
    private boolean wantsPopularRides;
    private boolean wantsMostRides;
    private ArrayList<Attraction> ridesVisited;
    private Stack<FastPass> fastPasses;

    public Person() {
        this.setId(0);
        this.setTimeToStay(10);
        this.setWantsMostRides(false);
        this.setWantsPopularRides(false);

        this.ridesVisited = new ArrayList<>();
        this.fastPasses = new Stack<>();
    }

    public Person(int id, int durationOfStay, boolean wantsPopularRides, boolean wantsMostRides) {
        this.id = id;
        this.durationOfStay = durationOfStay;
        this.wantsPopularRides = wantsPopularRides;
        this.wantsMostRides = wantsMostRides;

        this.ridesVisited = new ArrayList<>();
        this.fastPasses = new Stack<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDurationOfStay() {
        return this.durationOfStay;
    }

    public void setDurationOfStay(int durationOfStay) {
        this.durationOfStay = durationOfStay;
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

    public FastPass getNextFastPass() {
        return this.fastPasses.pop();
    }
}
