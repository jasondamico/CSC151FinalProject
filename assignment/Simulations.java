
package assignment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Simulations
{
    public static Time currentTime = new Time();
    public static int parkHours = 480; //8 hours
    public final static double FAST_PASS_PERCENT = 0.25;

    private int population;

    public int getPopulation()
    {
        return population;
    }

    public void setPopulation(int population)
    {
        this.population = population;
    }

    public static void noFastPassSimulation()
    {
    	currentTime.setCurrentTime(0);
        int fastPassUsage = 0; //for finding average fastpass usage
        int population = 300;

        Attraction att1 = new Attraction("The Stack Cyclone", 1, 5, 10);
        Attraction att2 = new Attraction("The Queue Coaster", 5, 8, 5);
        Attraction att3 = new Attraction("Linked List Log Ride", 3, 6, 8);
        Attraction att4 = new Attraction("The Tree Tower", 4, 16, 5);
        Attraction att5 = new Attraction("The Node Nightmare", 2, 10, 5);
        Attraction att6 = new Attraction("Collection Coaster", 1, 4, 5);
        Attraction att7 = new Attraction("The Graph Grabber", 3, 10, 10);

        ArrayList<Attraction> listofRides = new ArrayList<>();
        listofRides.add(att1);
        listofRides.add(att2);
        listofRides.add(att3);
        listofRides.add(att4);
        listofRides.add(att5);
        listofRides.add(att6);
        listofRides.add(att7);

        Park codeLand = new Park("Code Land!", listofRides);
        
        // Adding initial people to park
        ArrayList<Person> customers = new ArrayList<>();
        for (int i = 0; i < population; i++)
        {
            Person guy = new Person(i);
            customers.add(guy);

            Attraction personsChoice = codeLand.pickAttraction(guy, codeLand.getAttractions());
            
            if (personsChoice != null) {
                personsChoice.addPersonToLine(guy, guy.hasFastPass());
            } else {
                // Attraction is null, removes person from park
                codeLand.addToDoneForDay(guy);
            }
        }

        while (currentTime.getCurrentTime() <= parkHours+10)
        {
            for (Attraction ride: listofRides)
            {
                if (!ride.isCurrentlyRunning() && ride.getCurrentlyInLine() > 0)
                {
                    ride.startRide(); //puts people on the ride to capacity
                    ride.setRideStartTime(currentTime.getCurrentTime());
                }

                // Check ride
                Person[] peopleOffRide = ride.checkRuntime(currentTime.getCurrentTime());
                int i = 0;
                boolean hasMoreRiders = true;

                while (i < peopleOffRide.length && hasMoreRiders) {
                    Person guy = peopleOffRide[i];

                    if (peopleOffRide != null && guy != null) {    
                        Attraction personsChoice = codeLand.pickAttraction(guy, codeLand.getAttractions());
    
                        if (personsChoice != null) {
                            personsChoice.addPersonToLine(guy, guy.hasFastPass());
                        } else {
                            // Attraction is null, removes person from park
                            codeLand.addToDoneForDay(guy);
                        }
                    }

                    i++;
                }
            }

            System.out.println(codeLand);

            currentTime.setCurrentTime(currentTime.getCurrentTime() + 5); //5 minutes pass
        }
        
        //Close all rides
        for(int i = 0; i < codeLand.getAttractions().size(); i++) {
        	Attraction currentAttraction = codeLand.getAttractions().get(i);
        	ArrayList<Person> disgruntledPeople = currentAttraction.closeAttraction();
        	for(int j = 0; j < disgruntledPeople.size(); j++) {
        		codeLand.addToDoneForDay(disgruntledPeople.get(j));
        	}
        }
        

        ArrayList<Person> doneForDay = codeLand.getDoneForDay();
        int totalTimeWaited = 0;
        ArrayList<GarbageCollector> fineAdditions = new ArrayList<>();
        for (int i = 0; i < doneForDay.size(); i++) {
            Person p = doneForDay.get(i);
            int timeWaited = 0;

            // Continue processing wait times for a person until there are none left
            while (p.peekLastWaitTime() != null) {
                int waitTimeDuration = p.popLastWaitTime().getWaitDuration();
                
                if(waitTimeDuration > 0) {
                	timeWaited += waitTimeDuration;
                }
                if(waitTimeDuration == 0 && p.getRidesRidden().size() != 0) {
                	timeWaited += p.getRidesRidden().get(0).getDuration();
                }
                totalTimeWaited += waitTimeDuration;
            }
            fineAdditions.add(new GarbageCollector(p.getId(), timeWaited, p.getRidesRidden().size()));
        }
        
        System.out.println("Total time waited:" + " " + totalTimeWaited);
        System.out.println("Total number of people: " + doneForDay.size());
        System.out.println("Average time waited: " + (totalTimeWaited / doneForDay.size()));
        System.out.println();
        
//        Collections.sort(fineAdditions);
//        for(int i = 0; i < fineAdditions.size(); i++) {
//        	System.out.println(fineAdditions.get(i).getTotalTimeWaited());
//        	//System.out.println(customers.get(fineAdditions.get(i).getId()));
//        	//System.out.println();
//        }
//        
//        System.out.println();
//        
//        for(int i = 0; i < fineAdditions.size(); i++) {
//        	System.out.println(fineAdditions.get(i).getNumberOfRidesRidden());
//        }
    }
    
    public static void FastPassSimulation() {
    	
        int fastPassUsage = 0; //for finding average fastpass usage
        int population = 300;

        Attraction att1 = new Attraction("The Stack Cyclone", 1, 5, 10);
        Attraction att2 = new Attraction("The Queue Coaster", 5, 8, 5);
        Attraction att3 = new Attraction("Linked List Log Ride", 3, 6, 8);
        Attraction att4 = new Attraction("The Tree Tower", 4, 16, 5);
        Attraction att5 = new Attraction("The Node Nightmare", 2, 10, 5);
        Attraction att6 = new Attraction("Collection Coaster", 1, 4, 5);
        Attraction att7 = new Attraction("The Graph Grabber", 3, 10, 10);

        ArrayList<Attraction> listofRides = new ArrayList<>();
        listofRides.add(att1);
        listofRides.add(att2);
        listofRides.add(att3);
        listofRides.add(att4);
        listofRides.add(att5);
        listofRides.add(att6);
        listofRides.add(att7);
        Park codeLand = new Park("code Land!", listofRides);
        int howManyPremiumUsers = (int) (population*FAST_PASS_PERCENT);
        // Adding initial people to park
        ArrayList<Person> customers = new ArrayList<>();
        for (int i = 0; i < population; i++)
        {
            Person guy = new Person(i);
            customers.add(guy);
            if(i <= howManyPremiumUsers) {
            	guy.setIsPremium(true);
            	guy.addFastPass(new FastPass());
            }

            Attraction personsChoice = codeLand.pickAttraction(guy, codeLand.getAttractions());
            
            if (personsChoice != null) {
                personsChoice.addPersonToLine(guy, guy.hasFastPass());
            } else {
                // Attraction is null, removes person from park
                codeLand.addToDoneForDay(guy);
            }
        }

        while (currentTime.getCurrentTime() <= parkHours+10)
        {
            for (Attraction ride: listofRides)
            {
                if (!ride.isCurrentlyRunning() && ride.getCurrentlyInLine() > 0)
                {
                    ride.startRide(); //puts people on the ride to capacity
                    ride.setRideStartTime(currentTime.getCurrentTime());
                }

                // Check ride
                Person[] peopleOffRide = ride.checkRuntime(currentTime.getCurrentTime());
                int i = 0;
                boolean hasMoreRiders = true;

                while (i < peopleOffRide.length && hasMoreRiders) {
                    Person guy = peopleOffRide[i];
                    if(guy != null && guy.getIsPremium() == true) {
                    	guy.addFastPass(new FastPass());
                    }

                    if (peopleOffRide != null && guy != null) {    
                        Attraction personsChoice = codeLand.pickAttraction(guy, codeLand.getAttractions());
    
                        if (personsChoice != null) {
                            personsChoice.addPersonToLine(guy, guy.hasFastPass());
                        } else {
                            // Attraction is null, removes person from park
                            codeLand.addToDoneForDay(guy);
                        }
                    }

                    i++;
                }
            }

            System.out.println(codeLand);

            currentTime.setCurrentTime(currentTime.getCurrentTime() + 5); //5 minutes pass
        }
        
        //Close all rides
        for(int i = 0; i < codeLand.getAttractions().size(); i++) {
        	Attraction currentAttraction = codeLand.getAttractions().get(i);
        	ArrayList<Person> disgruntledPeople = currentAttraction.closeAttraction();
        	for(int j = 0; j < disgruntledPeople.size(); j++) {
        		codeLand.addToDoneForDay(disgruntledPeople.get(j));
        	}
        }
        

        ArrayList<Person> doneForDay = codeLand.getDoneForDay();
        int totalTimeWaited = 0;
        ArrayList<GarbageCollector> fineAdditions = new ArrayList<>();
        for (int i = 0; i < doneForDay.size(); i++) {
            Person p = doneForDay.get(i);
            int timeWaited = 0;
            // Continue processing wait times for a person until there are none left
            while (p.peekLastWaitTime() != null) {
                int waitTimeDuration = p.popLastWaitTime().getWaitDuration();
                
                if(waitTimeDuration > 0) {
                	timeWaited += waitTimeDuration;
                }
                if(waitTimeDuration == 0 && p.getRidesRidden().size() != 0) {
                	timeWaited += p.getRidesRidden().get(0).getDuration();
                }
                totalTimeWaited += waitTimeDuration;
            }
            fineAdditions.add(new GarbageCollector(p.getId(), timeWaited, p.getRidesRidden().size()));
        }
        
        System.out.println("Total time waited:" + " " + totalTimeWaited);
        System.out.println("Total number of people: " + doneForDay.size());
        System.out.println("Average time waited: " + (totalTimeWaited / doneForDay.size()));
        System.out.println();
        
        Collections.sort(fineAdditions);
//        for(int i = 0; i < fineAdditions.size(); i++) {
//        	
//        	System.out.println(fineAdditions.get(i).getTotalTimeWaited());
//        	//System.out.println(customers.get(fineAdditions.get(i).getId()));
//        	//System.out.println();
//        }
//        
//        System.out.println();
//        
//        for(int i = 0; i < fineAdditions.size(); i++) {
//        	System.out.println(fineAdditions.get(i).getNumberOfRidesRidden());
//        }
    }
}
