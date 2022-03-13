package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import assignment.Time;
import assignment.WaitTime;

public class WaitTimeTest {
    static Time t = new Time();
    static final int CURRENT_TIME = 45;

    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
        t.setCurrentTime(CURRENT_TIME);
    }

	@Test
    public void setStartTimeFromTime() {
        WaitTime wt = new WaitTime(t);

        assertEquals(CURRENT_TIME, wt.getStartWait());
    }

    @Test
    public void setEndTimeFromTime() {
        WaitTime wt = new WaitTime();
        wt.setEndWait(t);

        assertEquals(CURRENT_TIME, wt.getEndWait());
    }
}
