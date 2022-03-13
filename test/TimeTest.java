package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import assignment.Time;

public class TimeTest {
    @Test
    public void setTime() {
        Time t = new Time();
        t.setCurrentTime(5);
        
        assertEquals(5, t.getCurrentTime());
    }

    @Test
    public void incrementTime() {
        Time t = new Time();
        t.setCurrentTime(5);
        t.setCurrentTime(t.getCurrentTime() + 5);        
        assertEquals(10, t.getCurrentTime());

        t.setCurrentTime(t.getCurrentTime() + 5);
        assertEquals(15, t.getCurrentTime());
    }

    @Test
    public void setInvalidTime() {
        Time t = new Time();
        boolean exceptionThrown = false;

        try {
            t.setCurrentTime(-5);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        
        assertTrue(exceptionThrown);
    }
}
