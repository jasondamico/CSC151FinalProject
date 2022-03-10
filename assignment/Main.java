package assignment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main
{
    public static Time currentTime = new Time();
    public static int parkHours = 480; //8 hours

    private int population;

    public int getPopulation()
    {
        return population;
    }

    public void setPopulation(int population)
    {
        this.population = population;
    }

    public static void main(String[] args)
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

        ArrayList<Person> customers = new ArrayList<>();
        for (int i = 0; i < population; i++)
        {
            Person guy = new Person(i);
            customers.add(guy);

            codeLand.pickAttraction(guy, codeLand.getAttractions());
        }

        while (currentTime.getCurrentTime() <= parkHours)
        {
            for (Attraction ride: listofRides)
            {
                if (!ride.isCurrentlyRunning() && ride.getCurrentlyInLine() > 0)
                {
                    ride.startRide(); //puts people on the ride to capacity
                    ride.setRideStartTime(currentTime.getCurrentTime());
                }
                for (Person guy : ride.checkRuntime(currentTime.getCurrentTime()))
                {
                    codeLand.pickAttraction(guy, codeLand.getAttractions()); //removes people from park is this is null
                }
            }
            currentTime.setCurrentTime(currentTime.getCurrentTime() + 5); //5 minutes pass
        }


    }
}
