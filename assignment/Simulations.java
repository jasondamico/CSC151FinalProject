/**
 * We affirm that we have carried out my academic endeavors with full academic honesty. Signed, Jason D'Amico, Maggie Kelley and Hawkeye Nadel
 */

package assignment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/*
The Simulations Class holds all the simulations that we want to run through and their details
This is where everything else is used to get results.
 */
public class Simulations
{
    public static Time currentTime = new Time();
    public static int parkHours = 480; //8 hours

    /**
     * noFastPassSimulation runs a simulation where nobody has any FastPasses
     * @param ridesInPark list of rides in park
     * @param initialPopulation how many people there are to start off
     * @param newArrivalsPerHalfHour how many new arrivals per half hour
     * @param parkName park's name
     * Time complexity: O(n^m), where n = the duration of the simulation/5 minute steps, and m = the number of attractions in the park,
     * as every 5 minute step, every attraction is iterated through
     */
    public static void noFastPassSimulation(ArrayList<Attraction> ridesInPark, int initialPopulation, int newArrivalsPerHalfHour, String parkName)
    {
    	currentTime.setCurrentTime(0);
        
        Park codeLand = new Park(parkName, ridesInPark);
        
        // Adding initial people to park
        ArrayList<Person> customers = new ArrayList<>();
        for (int i = 0; i < initialPopulation; i++)
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
        	if(currentTime.getCurrentTime()%30 == 0) {
        		ArrayList<Person> newArrivals = new ArrayList<>();
        		for(int i = 0; i < newArrivalsPerHalfHour; i++) {
        			newArrivals.add(new Person(initialPopulation));
        			initialPopulation++;
        		}
        		for(int i = 0; i < newArrivals.size(); i++) {
        			Person guy = newArrivals.get(i);
        			Attraction personsChoice = codeLand.pickAttraction(guy, codeLand.getAttractions());
                    
                    if (personsChoice != null) {
                        personsChoice.addPersonToLine(guy, guy.hasFastPass());
                    } else {
                        // Attraction is null, removes person from park
                        codeLand.addToDoneForDay(guy);
                    }
        		}
        	}
            for (Attraction ride: ridesInPark)
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
            while (p.peekLastWaitTime() != null) {
                WaitTime rideWaitTime = p.popLastWaitTime();
                int waitTime = rideWaitTime.getEndWait() - rideWaitTime.getStartWait();
                if(waitTime > 0) {
                	timeWaited += waitTime;
                }
                if(waitTime == 0 && p.getRidesRidden().size() != 0) {
                	timeWaited += p.getRidesRidden().get(0).getDuration();
                }
                totalTimeWaited += waitTime;
            }
            fineAdditions.add(new GarbageCollector(p.getId(), timeWaited, p.getRidesRidden().size()));
        }
        
        System.out.println("Total time waited:" + " " + totalTimeWaited);
        System.out.println("Total number of people: " + doneForDay.size());
        System.out.println("Average time waited: " + (totalTimeWaited / doneForDay.size()));
        System.out.println();
        
        //uncomment to dump all Persons data
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
    
    /**
     * noFastPassSimulation runs a simulation where nobody has any FastPasses
     * @param fastPassPercent a double that represents the percent of premium users, for example: 0.25 = 25% of the population will be premium
     * @param ridesInPark list of rides in park
     * @param initialPopulation how many people there are to start off
     * @param newArrivalsPerHalfHour how many new arrivals per half hour
     * @param parkName park's name
     * Time complexity: O(n^m), where n = the duration of the simulation/5 minute steps, and m = the number of attractions in the park,
     * as every 5 minute step, every attraction is iterated through
     */
    public static void FastPassSimulation(double fastPassPercent, ArrayList<Attraction> ridesInPark, int initialPopulation, 
    		int newArrivalsPerHalfHour, String parkName) {
    	currentTime.setCurrentTime(0);
        
        Park codeLand = new Park(parkName, ridesInPark);
        int howManyPremiumUsers = (int) (initialPopulation*fastPassPercent);
        int howManyPremiumUsersPerHalfHour = (int) (howManyPremiumUsers*fastPassPercent);
        // Adding initial people to park
        ArrayList<Person> customers = new ArrayList<>();
        for (int i = 0; i < initialPopulation; i++)
        {
            Person guy = new Person(i);
            customers.add(guy);
            if(i <= howManyPremiumUsers) {
            	guy.setIsPremium(true);
            	guy.addFastPass(new FastPass("UNIVERSAL"));
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
        	if(currentTime.getCurrentTime()%30 == 0) {
        		ArrayList<Person> newArrivals = new ArrayList<>();
        		for(int i = 0; i < newArrivalsPerHalfHour; i++) {
        			newArrivals.add(new Person(initialPopulation));
        			initialPopulation++;
        		}
        		for(int i = 0; i < newArrivals.size(); i++) {
        			Person guy = newArrivals.get(i);
        			Attraction personsChoice = codeLand.pickAttraction(guy, codeLand.getAttractions());
                    
                    if (personsChoice != null) {
                        personsChoice.addPersonToLine(guy, guy.hasFastPass());
                    } else {
                        // Attraction is null, removes person from park
                        codeLand.addToDoneForDay(guy);
                    }
        		}
        	}
            for (Attraction ride: ridesInPark)
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
            while (p.peekLastWaitTime() != null) {
                WaitTime rideWaitTime = p.popLastWaitTime();
                int waitTime = rideWaitTime.getEndWait() - rideWaitTime.getStartWait();
                if(waitTime > 0) {
                	timeWaited += waitTime;
                }
                if(waitTime == 0 && p.getRidesRidden().size() != 0) {
                	timeWaited += p.getRidesRidden().get(0).getDuration();
                }
                totalTimeWaited += waitTime;
            }
            fineAdditions.add(new GarbageCollector(p.getId(), timeWaited, p.getRidesRidden().size()));
        }
        
        System.out.println("Total time waited:" + " " + totalTimeWaited);
        System.out.println("Total number of people: " + doneForDay.size());
        System.out.println("Average time waited: " + (totalTimeWaited / doneForDay.size()));
        System.out.println();
        
        //uncomment to dump every person's raw data
//        Collections.sort(fineAdditions);
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
