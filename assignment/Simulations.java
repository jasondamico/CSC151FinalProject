package assignment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Simulations
{
    public static Time currentTime = new Time();
    public static int parkHours = 40; //8 hours

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
    	
        int fastPassUsage = 0; //for finding average fastpass usage
        int population = 10;

        Attraction att1 = new Attraction("The Stack Cyclone", 1, 5, 10);
        Attraction att2 = new Attraction("The Queue Coaster", 5, 8, 5);
        Attraction att3 = new Attraction("Linked List Log Ride", 3, 6, 8);
        Attraction att4 = new Attraction("The Tree Tower", 4, 16, 5);

        ArrayList<Attraction> listofRides = new ArrayList<>();
        listofRides.add(att1);
        listofRides.add(att2);
        listofRides.add(att3);
        listofRides.add(att4);

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

        for (int i = 0; i < doneForDay.size(); i++) {
            Person p = doneForDay.get(i);

            while (p.peekLastWaitTime() != null) {
                WaitTime rideWaitTime = p.popLastWaitTime();
                int waitTime = rideWaitTime.getEndWait() - rideWaitTime.getStartWait();

                totalTimeWaited += waitTime;
            }
        }
        
        System.out.println(totalTimeWaited);
        System.out.println(doneForDay.size());
        System.out.println(totalTimeWaited / doneForDay.size());
    }
}
