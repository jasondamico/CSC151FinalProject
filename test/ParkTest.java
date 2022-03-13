package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import assignment.Attraction;
import assignment.Park;
import assignment.Person;


public class ParkTest {
    @Test
    public void constructor() {
        Park p = new Park();
        assertEquals(p.getName(), "");
    }

    @Test
    public void stringConstructor() {
        Park p = new Park("my park");
        assertEquals(p.getName(), "my park");
    }

    @Test
    public void attractionsConstructor() {
        Attraction[] attractions = {
			new Attraction("a1", 5, 4, 3),
			new Attraction("a2", 5, 4, 3),
			new Attraction("a3", 5, 4, 3)
		};

        ArrayList<Attraction> attractionsArrayList = new ArrayList<>();

        Collections.addAll(attractionsArrayList, attractions);

        Park p = new Park("my park", attractionsArrayList);
        
        ArrayList<Attraction> parkAttractions =  p.getAttractions();

        for (int i = 0; i < parkAttractions.size(); i++) {
            Attraction a = parkAttractions.get(i);

            boolean attractionFound = false;

            // Search for item in original array, as the values are sorted after they are initialized in the Park object
            for (int j = 0; j < attractions.length; j++) {
                Attraction a2 = attractions[j];

                attractionFound = attractionFound || a.equals(a2);
            }

            assertTrue(attractionFound);
        }
    }

    @Test
    public void getMinWaitTime() {
        Attraction atr1 = new Attraction("atr1", 5, 5, 5);

        for (int i = 0; i < 15; i++) {
            atr1.addPersonToLine(new Person(i), false);
        }

        Attraction atr2 = new Attraction("atr1", 5, 5, 5);

        for (int i = 0; i < 16; i++) {
            atr2.addPersonToLine(new Person(i), false);
        }

        Attraction atr3 = new Attraction("atr1", 5, 5, 5);

        for (int i = 0; i < 10; i++) {
            atr3.addPersonToLine(new Person(i), false);
        }

        ArrayList<Attraction> attractions = new ArrayList<>(Arrays.asList((new Attraction[] {atr1, atr2, atr3})));

        assertEquals(atr3, Park.getMinWaitTime(attractions));
    }

    @Test
    public void getMaxWaitTime() {
        Attraction atr1 = new Attraction("atr1", 5, 5, 5);

        for (int i = 0; i < 15; i++) {
            atr1.addPersonToLine(new Person(i), false);
        }

        Attraction atr2 = new Attraction("atr1", 5, 5, 5);

        for (int i = 0; i < 27; i++) {
            atr2.addPersonToLine(new Person(i), false);
        }

        Attraction atr3 = new Attraction("atr1", 5, 5, 5);

        for (int i = 0; i < 10; i++) {
            atr3.addPersonToLine(new Person(i), false);
        }

        ArrayList<Attraction> attractions = new ArrayList<>(Arrays.asList((new Attraction[] {atr1, atr2, atr3})));

        assertEquals(atr2, Park.getMaxWaitTime(attractions));
    }

    @Test
    public void pickAttraction() {
        
    }
}
