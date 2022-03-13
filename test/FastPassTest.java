package test;

import static org.junit.Assert.*;

import org.junit.Test;

import assignment.Attraction;
import assignment.FastPass;


public class FastPassTest {
    @Test
    public void constructor() {
        FastPass fp = new FastPass();
        assertNull(fp.getAttraction());
    }

    @Test
    public void paramConstructor() {
        Attraction atr = new Attraction("test", 1, 2, 3);

        FastPass fp = new FastPass(atr);
        assertEquals(fp.getAttraction(), atr);
    }
}
