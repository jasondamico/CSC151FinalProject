/**
 * We affirm that we have carried out my academic endeavors with full academic honesty. Signed, Jason D'Amico, Maggie Kelley and Hawkeye Nadel
 */

package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import assignment.Attraction;
import assignment.FastPass;
import assignment.Person;
import assignment.Time;
import assignment.WaitTime;

public class PersonTest {
	Time t = new Time();

	@Test
	public void testPerson() {
		Person p = new Person();
		assertFalse(p == (null));
	}
	
	@Test
	public void testPerson1() {
		Person p = new Person(1);
		assertFalse(p == (null));
	}
	
	@Test
	public void testPerson2() {
		Person p = new Person(1, 60, 420, 90, false, true);
		assertFalse(p == null);
	}
	
	@Test 
	public void addSingleFastPass() {
		Person p = new Person(1);
		FastPass fp = new FastPass();

		assertFalse(p.hasFastPass());

		p.addFastPass(fp);
		assertEquals(1, p.numFastPasses());
		assertTrue(p.hasFastPass());

		assertEquals(p.removeFastPass(), fp);
		assertFalse(p.hasFastPass());
		
		assertEquals(0, p.numFastPasses());
	}

	@Test 
	public void addMultipleFastPass() {
		Person p = new Person(1);
		FastPass fp1 = new FastPass();
		FastPass fp2 = new FastPass();
		FastPass fp3 = new FastPass();
		p.addFastPass(fp1);
		p.addFastPass(fp2);
		p.addFastPass(fp3);
		
		assertEquals(3, p.numFastPasses());
		assertEquals(p.removeFastPass(), fp1);
		assertEquals(2, p.numFastPasses());
	}

	@Test
	public void removeFastPassFromEmpty() {
		Person p = new Person(1);
		
		assertNull(p.removeFastPass());
	}
	
	@Test 
	public void peekLastWaitTime() {
		Person p = new Person(1);
		WaitTime wt = new WaitTime(4);

		p.addWaitTime(wt);
		assertEquals(wt, p.peekLastWaitTime());
	}

	@Test 
	public void popLastWaitTime() {
		Person p = new Person(1);
		WaitTime wt = new WaitTime(4);

		p.addWaitTime(wt);
		WaitTime popped = p.popLastWaitTime();

		assertEquals(wt, popped);
		assertNull(p.peekLastWaitTime());
	}

	@Test 
	public void popLastWaitTimeFromEmpty() {
		Person p = new Person(1);

		assertNull(p.popLastWaitTime());
	}

	@Test 
	public void ridesVisited() {
		Person p = new Person(1);

		Attraction[] attractions = {
			new Attraction("a1", 5, 4, 3),
			new Attraction("a2", 5, 4, 3),
			new Attraction("a3", 5, 4, 3)
		};

		for (Attraction a : attractions) {
			p.addRideVisited(a);
		}

		ArrayList<Attraction> ridesVisted = p.getRidesRidden();

		for (int i = 0; i < ridesVisted.size(); i++) {
			assertEquals(attractions[i], ridesVisted.get(i));
		}
	}

	@Test 
	public void hasFastPass() {
		Person p = new Person(1);

		assertFalse(p.hasFastPass());
		p.addFastPass(new FastPass());
		assertTrue(p.hasFastPass());
		p.removeFastPass();
		assertFalse(p.hasFastPass());
	}
}
