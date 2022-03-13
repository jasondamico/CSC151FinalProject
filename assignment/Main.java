package assignment;

import java.util.ArrayList;

/*
The Main Class is meant to make running the simulations more compact. 
Having this class allows us to pick which simulation to run.
 */
public class Main {
    public static void main(String[] args) {
    	Attraction att1 = new Attraction("The Stack Cyclone", 1, 5, 10);
        Attraction att2 = new Attraction("The Queue Coaster", 5, 8, 5);
        Attraction att3 = new Attraction("Linked List Log Ride", 3, 6, 8);
        Attraction att4 = new Attraction("The Tree Tower", 4, 16, 5);
        Attraction att5 = new Attraction("The Node Nightmare", 2, 10, 5);
        Attraction att6 = new Attraction("Collection Coaster", 1, 4, 5);
        Attraction att7 = new Attraction("The Graph Grabber", 3, 10, 10);

        ArrayList<Attraction> listOfRides = new ArrayList<>();
        listOfRides.add(att1);
        listOfRides.add(att2);
        listOfRides.add(att3);
        listOfRides.add(att4);
        listOfRides.add(att5);
        listOfRides.add(att6);
        listOfRides.add(att7);
    	
        //run one simulation at a time
        Simulations.noFastPassSimulation(listOfRides, 100, 10, "codeLand");
        //Simulations.FastPassSimulation(0.25, listOfRides, 100, 10, "codeLand");
        
        
    }
}
