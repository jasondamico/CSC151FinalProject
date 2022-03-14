/**
 * We affirm that we have carried out my academic endeavors with full academic honesty. Signed, Jason D'Amico, Maggie Kelley and Hawkeye Nadel
 */

package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Test;

import assignment.Attraction;
import assignment.FastPass;
import assignment.Person;
import assignment.Simulations;

public class AttractionTest {
    @After
	public void tearDown() throws Exception {
        Simulations.currentTime.setCurrentTime(0);
	}

    @Test
    public void constructor() {
        Attraction atr = new Attraction("atr1", 5, 10, 5);

        assertEquals("atr1", atr.getName());
        assertEquals(5, atr.getPopularityScore());
        assertEquals(10, atr.getCapacity());
        assertEquals(5, atr.getDuration());
    }

    @Test
    public void badPopularityScore() {
        boolean exceptionThrown = false;

        try {
            Attraction atr = new Attraction("atr1", 6, 10, 5);
        } catch (IllegalArgumentException e) {
            exceptionThrown = (e.getMessage().equals("Popularity score must be between 1 and 5."));
        }

        assertTrue(exceptionThrown);
    }

    @Test
    public void badCapacity() {
        boolean exceptionThrown = false;

        try {
            Attraction atr = new Attraction("atr1", 5, -3, 5);
        } catch (IllegalArgumentException e) {
            exceptionThrown = (e.getMessage().equals("Capacity must be greater than or equal to 1."));
        }

        try {
            Attraction atr = new Attraction("atr1", 5, 0, 5);
        } catch (IllegalArgumentException e) {
            exceptionThrown = exceptionThrown && (e.getMessage().equals("Capacity must be greater than or equal to 1."));
        }

        assertTrue(exceptionThrown);
    }
    
    @Test
    public void badDuration() {
        boolean exceptionThrown = false;

        try {
            Attraction atr = new Attraction("atr1", 5, 1, 0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = (e.getMessage().equals("Duration must be greater than or equal to 1."));
        }

        try {
            Attraction atr = new Attraction("atr1", 5, 1, -3);
        } catch (IllegalArgumentException e) {
            exceptionThrown = exceptionThrown && (e.getMessage().equals("Duration must be greater than or equal to 1."));
        }

        assertTrue(exceptionThrown);
    }

    @Test
    public void waitTime() {
        Attraction atr = new Attraction("atr1", 5, 5, 5);

        assertEquals(atr.getWaitTime(), 0);

        int i = 0;

        for (int j = 0; j < 4; j++) {
            atr.addPersonToLine(new Person(i), false);
            
            i++;
        }

        assertEquals(atr.getWaitTime(), 0);

        atr.addPersonToLine(new Person(i++), false);
        assertEquals(atr.getWaitTime(), 5);

        for (int j = 0; j < 10; j++) {
            atr.addPersonToLine(new Person(i), false);
            
            i++;
        }

        assertEquals(atr.getWaitTime(), 15);
    }

    @Test
    public void startRide() {
        Attraction atr = new Attraction("atr1", 5, 10, 5);

        Person[] people = new Person[15];

        for (int i = 0; i < 15; i++) {
            Person p = new Person(i);
            atr.addPersonToLine(p, false);
            people[i] = p;
        }

        assertEquals(15, atr.getCurrentlyInLine());
        assertEquals(5, atr.getWaitTime());
        assertFalse(atr.isCurrentlyRunning());
        atr.startRide();
        assertEquals(5, atr.getCurrentlyInLine());
        assertEquals(5, atr.getWaitTime());
        assertTrue(atr.isCurrentlyRunning());

        for (int i = 0; i < 5; i++) {
            Person onRide = atr.getOnRide()[i];
            assertEquals(people[i], onRide);
        }
    }

    @Test
    public void checkRuntime() {
        // Test from startRide1

        Attraction atr = new Attraction("atr1", 5, 10, 5);

        Person[] people = new Person[15];

        for (int i = 0; i < 15; i++) {
            Person p = new Person(i);
            atr.addPersonToLine(p, false);
            people[i] = p;
        }

        atr.startRide();

        for (int i = 0; i < 5; i++) {
            Person onRide = atr.getOnRide()[i];
            assertEquals(people[i], onRide);
        }

        Simulations.currentTime.setCurrentTime(5);
        Person[] offRide = atr.checkRuntime();
        Person[] expectedOffRide = Arrays.copyOfRange(people, 0, 10);

        assertArrayEquals(expectedOffRide, offRide);
        atr.startRide();
        assertEquals(5, atr.numPeopleOnRide());
        assertEquals(0, atr.getCurrentlyInLine());
        assertEquals(5, atr.getWaitTime());
    }

    @Test
    public void checkRuntimeUnstarted() {
        // Test from startRide1

        Attraction atr = new Attraction("atr1", 5, 10, 5);

        Person[] people = new Person[15];

        for (int i = 0; i < 15; i++) {
            Person p = new Person(i);
            atr.addPersonToLine(p, false);
            people[i] = p;
        }

        assertArrayEquals(new Person[0], atr.checkRuntime());
    }

    @Test
    public void checkRuntimeUnfinished() {
        // Test from startRide1

        Attraction atr = new Attraction("atr1", 5, 10, 5);

        Person[] people = new Person[15];

        for (int i = 0; i < 15; i++) {
            Person p = new Person(i);
            atr.addPersonToLine(p, false);
            people[i] = p;
        }

        atr.startRide();

        assertArrayEquals(new Person[0], atr.checkRuntime());
    }

    @Test
    public void checkRuntimePastStoppingPoint() {
        Attraction atr = new Attraction("atr1", 5, 10, 8);

        Person[] people = new Person[15];

        for (int i = 0; i < 15; i++) {
            Person p = new Person(i);
            atr.addPersonToLine(p, false);
            people[i] = p;
        }

        assertEquals(8, atr.getWaitTime());
        atr.startRide();

        assertEquals(8, atr.getWaitTime());
        Simulations.currentTime.setCurrentTime(3);
        assertEquals(5, atr.getWaitTime());

        Simulations.currentTime.setCurrentTime(10);

        Person[] offRide = atr.checkRuntime();

        assertArrayEquals(Arrays.copyOfRange(people, 0, 10), offRide);

        assertEquals(atr.getWaitTime(), 0);
    }
    
    @Test
    public void closeAttraction() {
        Attraction atr = new Attraction("atr1", 5, 10, 8);

        Person[] people = new Person[15];

        for (int i = 0; i < 15; i++) {
            Person p = new Person(i);
            atr.addPersonToLine(p, ((int) Math.random() * 2 == 1 ? true: false));
            people[i] = p;
        }

        atr.startRide();

        ArrayList<Person> offRide = atr.closeAttraction();

        boolean allPeopleFound = true;

        for (int i = 0; i < offRide.size(); i++) {
            boolean personFound = false;
            Person offRideP = offRide.get(i);
            int j = 0;

            while (j < people.length && !personFound) {
                personFound = offRideP.equals(people[j]);

                j++;
            }

            allPeopleFound = personFound;

            assertTrue(allPeopleFound);
        }
    }

    @Test
    public void fastFirst() {
        Attraction atr = new Attraction("atr1", 5, 5, 5);

        Person[] fastPeople = new Person[5];
        int numFastPeople = 0;
        int i = 0;

        while (numFastPeople < 5) {
            Person p = new Person(i);

            boolean hasFastPass = ((int) (Math.random() * 2) == 1) ? true: false;

            if (hasFastPass) {
                p.addFastPass(new FastPass());
                fastPeople[numFastPeople] = p;
                numFastPeople++;
            }

            atr.addPersonToLine(p, hasFastPass);

            i++;
        }

        atr.startRide();
        Simulations.currentTime.setCurrentTime(5);
        Person[] offRide = atr.checkRuntime();

        boolean hasFastPass = true;

        // 66% of fast pass people are always added to ride
        for (int j = 0; j < (int) (5 * 0.66); j++) {
            hasFastPass = hasFastPass && offRide[j] == fastPeople[j];
        }

        assertTrue(hasFastPass);
    }
}
