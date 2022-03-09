package assignment;

import org.w3c.dom.Attr;

public class Main
{

    public static void main(String[] args)
    {
        int duration = 480; //8 hours
        int timePassed = 0; //goes up by 5 per step
        int population = 10; //editable - how many visitors the park is getting

        int fastPassUsage = 0; //for finding average fastpass usage

        Attraction att1 = new Attraction("The Stack Cyclone", 1, 5, 10);
        Attraction att2 = new Attraction("The Queue Coaster", 5, 8, 5);
        Attraction att3 = new Attraction("Linked List Log Ride", 3, 6, 8);
        Attraction att4 = new Attraction("The Tree Tower", 4, 16, 3);

        Attraction[] listofRides = {att1, att2, att3, att4};
        Park codeLand = new Park("Code Land!", listofRides);

        Person one = new Person(1, 120, 480, 60, true, false); //1 hour balk time

        if (timePassed < duration)
        {
            //wait times change/calculations
            //go on rides

            timePassed += 5;
        }
        //Duration
        //Population
        //stepping


    }
}
