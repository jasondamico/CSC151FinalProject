package assignment;

import java.util.ArrayList;
import java.util.Collections;

/*
The Park Class creates the Park our customers are visting! Every park should have a list of Attractions, and a name.
Everything that happens within a park.
 */
public class Park {
    private ArrayList<Attraction> attractions;
    private ArrayList<Person> doneForDay;
    private String name;

    /**
     * empty constructor
     */
    public Park() {
        this.name = "";
        this.attractions = new ArrayList<Attraction>();
        this.doneForDay = new ArrayList<Person>();
    }

    /**
     * constructor
     * empty list of attractions & empty list of people who are done for the day
     * @param name String name of the park
     */
    public Park(String name) {
        attractions = new ArrayList<Attraction>();
        this.setName(name);
        this.doneForDay = new ArrayList<Person>();
    }

    /**
     * constructor
     * @param name String name of the park
     * @param attractions ArrayList of Attractions in the park
     */
    public Park(String name, ArrayList<Attraction> attractions){
        this.setName(name);
        this.setAttractions(attractions);

        this.doneForDay = new ArrayList<Person>();
    }

    /**
     * getAttractions
     * @return ArrayList of the attractions in the park
     */
    public ArrayList<Attraction> getAttractions(){
        ArrayList<Attraction> toReturn = new ArrayList<Attraction>();
        for(int i = 0; i < this.attractions.size(); i++) {
            toReturn.add(this.attractions.get(i));
        }
        return toReturn;

    /**
     * Sets the passed ArrayList of attractions to the object's instance variable, sorts them in descending order of popularity.
     * @param attractions An unsorted ArrayList of attractions.
     */
    public void setAttractions(ArrayList<Attraction> attractions) {
        this.attractions = new ArrayList<Attraction>(attractions);

        // Sort in descending order (by popularity)
        Collections.sort(this.attractions);
        Collections.reverse(this.attractions);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * getMinWaitTime
     * @param attractions ArrayList of attractions
     * @return Attraction with the lowest wait time
     */
    public Attraction getMinWaitTime(ArrayList<Attraction> attractions) {
        if(attractions.isEmpty()) {
            return null;
        }
        Attraction toReturn = attractions.get(0);
        for(int i = 0; i < attractions.size(); i ++) {
            if(attractions.get(i).getWaitTime() < toReturn.getWaitTime()) {
                toReturn = attractions.get(i);
            }
        }
        return toReturn;
    }

    /**
     * getMaxWaitTime
     * @param attractions list of all the attractions
     * @return Attraction with the highest wait time
     */
    public Attraction getMaxWaitTime(ArrayList<Attraction> attractions) {
        if(attractions.isEmpty()) {
            return null;
        }
        Attraction toReturn = attractions.get(0);
        for(int i = 0; i < attractions.size(); i ++) {
            if(attractions.get(i).getWaitTime() > toReturn.getWaitTime()) {
                toReturn = attractions.get(i);
            }
        }
        return toReturn;
    }

    /**
     * getThreeMostPopular
     * @return ArrayList of the 3 most popular attractions
     */
    private ArrayList<Attraction> getThreeMostPopular(){
        ArrayList<Attraction> toReturn = new ArrayList<Attraction>();
        for(int i = 0; i < 3; i++) {
            toReturn.add(this.attractions.get(i));
        }
        return toReturn;
    }

    /**
     * pickAttraction
     * @param p
     * @param attractions
     * @return Attraction to queue person p into, or null for the person to leave the park
     */
    public Attraction pickAttraction(Person p, ArrayList<Attraction> attractions) {
        if(Simulations.currentTime.getCurrentTime() >= Simulations.parkHours) {
            return null;
        }
        if(p.hasFastPass()) {
            if(p.isWantsPopularRides()) {
                //assertion: attractions is sorted greatest to least by popularity
                return attractions.get(0);
            }
            if(p.isWantsMostRides()) {
                return this.getMaxWaitTime(attractions);
            }
        }
        else {
            if(p.isWantsPopularRides() && p.isWantsMostRides()) {
                Attraction mostPopularShortestWaitTime = this.getMinWaitTime(this.getThreeMostPopular());
                if(mostPopularShortestWaitTime.getWaitTime() > p.getBalkPoint()) {
                    for(int i = 0; i < attractions.size(); i++) {
                        if(attractions.get(i).getWaitTime() < p.getBalkPoint()) {
                            return attractions.get(i);
                        }
                    }
                }
                else {
                    return mostPopularShortestWaitTime;
                }
                if(p.getMinStay() > Simulations.currentTime.getCurrentTime()) {
                    //assertion: attractions is sorted greatest to least by popularity
                    return mostPopularShortestWaitTime;
                }
                return null;
            }
            else if(p.isWantsPopularRides()) {
                for(int i = 0; i < attractions.size(); i++) {
                    if(attractions.get(i).getWaitTime() < p.getBalkPoint()) {
                        return attractions.get(i);
                    }
                }
                if(p.getMinStay() > Simulations.currentTime.getCurrentTime()) {
                    //person hasn't stayed late enough, they suck it up and go on the ride they most desire
                    return attractions.get(0);
                }
                return null;
            }
            else if(p.isWantsMostRides()) {
                if(this.getMinWaitTime(attractions).getWaitTime() > p.getBalkPoint()) {
                    if(p.getMinStay() > Simulations.currentTime.getCurrentTime()){
                        return this.getMinWaitTime(attractions);
                    }
                    return null;
                }
                else {
                    return this.getMinWaitTime(attractions);
                }
            }
        }
        return attractions.get((int) Math.random() * attractions.size());
//    	return null;
    }

    /**
     * getDoneForDay
     * @return ArrayList of all the people who are done riding rides for the day
     */
    public ArrayList<Person> getDoneForDay() {
        return this.doneForDay;
    }

    /**
     * addToDoneForDay
     * @param p Person to add to the doneForDay ArrayList
     */
    public void addToDoneForDay(Person p) {
        this.doneForDay.add(p);
    }

    public String toString() {
        String toReturn = "Welcome to " + this.getName() + "! TIME: " + Simulations.currentTime.getCurrentTime() + "\nOur attractions are as follows:\n\n";

        for (Attraction atr : this.attractions) {
            toReturn += atr + "\n";
        }

        return toReturn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Park park = (Park) o;

        if (attractions != null ? !attractions.equals(park.attractions) : park.attractions != null) return false;
        if (doneForDay != null ? !doneForDay.equals(park.doneForDay) : park.doneForDay != null) return false;
        return name != null ? name.equals(park.name) : park.name == null;
    }

    @Override
    public int hashCode() {
        int result = attractions != null ? attractions.hashCode() : 0;
        result = 31 * result + (doneForDay != null ? doneForDay.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public static void main(String[] args) {
        ArrayList<Attraction> atr = new ArrayList<>();

        atr.add(new Attraction("test1", 10, 9, 8));
        atr.add(new Attraction("test2", 3, 2, 1));
        atr.add(new Attraction("test3", 6, 3, 4));

        Park p = new Park("Test park", atr);

        System.out.println(p);
    }

}
