package assignment;

public class Main
{
    public static Time currentTime = new Time();

    public static void main(String[] args)
    {
        int duration = 480; //8 hours

        int fastPassUsage = 0; //for finding average fastpass usage

        Attraction att1 = new Attraction("The Stack Cyclone", 1, 5, 10);
        Attraction att2 = new Attraction("The Queue Coaster", 5, 8, 5);
        Attraction att3 = new Attraction("Linked List Log Ride", 3, 6, 8);
        Attraction att4 = new Attraction("The Tree Tower", 4, 16, 5);

        Attraction[] listofRides = {att1, att2, att3, att4};
        Park codeLand = new Park("Code Land!", listofRides);

        Person test = new Person();


        while (currentTime.getCurrentTime() <= duration)
        {
            //people enter park - list of people (random number)
             /*
                check each person in the list of entering people
                ============================================
                if (codeLand.pickAttraction() == null) {
                        - add to people who left arrayList
                }
                else {
                        codeLand.pickAttraction.enqueue(person being checked)
                }
                 */

            for (Attraction ride: listofRides)
            {
                if (!ride.isCurrentlyRunning() && ride.getCurrentlyInLine() > 0)
                {
                    ride.startRide(); //puts people on the ride to capacity
                    ride.setRideStartTime(currentTime.getCurrentTime());
                }
                //pop all people off the ride
                //pick attraction for people who could be leaving
            }

            //check if ride finished
            //assign people getting off a new ride (pickAttraction)

            //
            //use the list of people in the park, grab the people and have them pick their attraction
            //if they're already in a line and aren't boarding then they'll
            //arrayofpeople who just got off ride, use pick attraction (or leave)



            //arraylist of people who are in the park

            //wait times change/calculations
            //go on rides
            currentTime.setCurrentTime(currentTime.getCurrentTime() + 5); //5 minutes pass
        }

        //Population
        //stepping


    }
}
