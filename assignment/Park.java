
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

    public String toString() {
        String toReturn = "Welcome to " + this.getName() + "! Our attractions are as follows:\n\n";
        
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
