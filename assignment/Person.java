package assignment;

import java.util.ArrayList;
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
    private ArrayList<Attraction> ridesVisited;
    private ArrayList<FastPass> fastPasses;
    private int balkPoint;

    private final int NO_BALKPOINT = 10000;
    private final int MAX_STAY = 480;
    private final int MIN_STAY = 120;
    private final int STAY_RANGE = 360;
    
    public Person() {
        this.setId(0);
        this.setMinStay(0);
        this.setMaxStay(10);
        this.setBalkPoint(NO_BALKPOINT);
        this.setWantsMostRides(false);
        this.setWantsPopularRides(false);
        
        this.ridesVisited = new ArrayList<>();
        this.fastPasses = new ArrayList<>();
        this.balkPoint = 0;
    }

    public Person(int id) {
        this.id = id;

        this.setMinStay((int) (STAY_RANGE * Math.random()) + MIN_STAY);
        this.setMaxStay(MAX_STAY);
        this.setWantsMostRides((Math.random() * 2 > 1) ? true : false);
        this.setWantsPopularRides((Math.random() * 2 > 1) ? true : false);
        this.setBalkPoint(NO_BALKPOINT);
        this.ridesVisited = new ArrayList<>();
        this.fastPasses = new ArrayList<>();
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

    public FastPass removeFastPass() {
        if (this.fastPasses.size() > 0) {
            return this.fastPasses.remove(fastPasses.size() - 1);
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
