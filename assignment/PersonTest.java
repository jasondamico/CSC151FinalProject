package assignment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PersonTest {

	@Test
	void testPerson() {
		Person p = new Person();
		assertFalse(p == (null));
	}
	
	@Test void testPerson1() {
		Person p = new Person(1);
		assertFalse(p == (null));
	}
	
	@Test void testPerson2() {
		Person p = new Person(1, 60, 420, 90, false, true);
		assertFalse(p == null);
	}
	
	@Test void test
	
	

}
