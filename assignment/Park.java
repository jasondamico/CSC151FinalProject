
package assignment;

import java.util.ArrayList;
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
        this.doneForDay = new ArrayList<Person>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * pickAttraction
     * @param p 
     * @param attractions 
     */
    public Attraction pickAttraction(Person p, ArrayList<Attraction> attractions) {
    	if(p.hasFastPass()) {
    		if()
    	}
    	else {
    		
    	}
    }
    
    
}
