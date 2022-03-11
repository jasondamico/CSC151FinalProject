
package assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class Park {
    private ArrayList<Attraction> attractions;
    private ArrayList<Person> doneForDay;
    private String name;

    public Park() {
        this.name = "";
        this.attractions = new ArrayList<Attraction>();
        this.doneForDay = new ArrayList<Person>();
    }

    public Park(String name) {
        attractions = new ArrayList<Attraction>();
        this.setName(name);
        this.doneForDay = new ArrayList<Person>();
    }
    
    public Park(String name, ArrayList<Attraction> attractions){
        this.setName(name);
        this.attractions = new ArrayList<Attraction>();

        for(int i = 0; i < attractions.size(); i++) {
        	this.attractions.add(attractions.get(i));
        }

        // Sort in descending order (by popularity)
        Collections.sort(this.attractions);
        Collections.reverse(this.attractions);

        this.doneForDay = new ArrayList<Person>();
    }
    
    public ArrayList<Attraction> getAttractions(){
    	ArrayList<Attraction> toReturn = new ArrayList<Attraction>();
    	for(int i = 0; i < this.attractions.size(); i++) {
    		toReturn.add(this.attractions.get(i));
    	}
    	return toReturn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
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
    			if(p.getMinStay() >= Simulations.currentTime.getCurrentTime()) {
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
    			if(p.getMinStay() >= Simulations.currentTime.getCurrentTime()) {
    				//person hasn't stayed late enough, they suck it up and go on the ride they most desire
    				return attractions.get(0);
    			}
    			return null;
    		}
    		else if(p.isWantsMostRides()) {
    			if(this.getMinWaitTime(attractions).getWaitTime() > p.getBalkPoint()) {
    				if(p.getMinStay() >= Simulations.currentTime.getCurrentTime()){
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

	public ArrayList<Person> getDoneForDay() {
		return this.doneForDay;
	}

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
    
    public static void main(String[] args) {
        ArrayList<Attraction> atr = new ArrayList<>();

        atr.add(new Attraction("test1", 10, 9, 8));
        atr.add(new Attraction("test2", 3, 2, 1));
        atr.add(new Attraction("test3", 6, 3, 4));

        Park p = new Park("Test park", atr);

        System.out.println(p);
    }
    
}
