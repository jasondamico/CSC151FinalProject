/**
 * We affirm that we have carried out my academic endeavors with full academic honesty. Signed, Jason D'Amico, Maggie Kelley and Hawkeye Nadel
 */

package assignment;

/*
The WaitTime Class makes a WaitTime object
getWaitDuration uses the instance variables of startWait and endWait which are representative of the time in the
day it is when the person starts/stops waiting, and does the math to figure out the wait of the ride they waited for
 */
public class WaitTime {
    // PRIVATE INSTANCE VARIABLES
    private int startWait;
    private int endWait;
    
    /**
     * empty constructor
     * sets both startWait and endWait to 0
     */
    public WaitTime() {
        this.setStartWait(0);
        this.setEndWait(0);
    }

    /**
     * constructor
     * @param startWait a time that the wait started at as an int
     */
    public WaitTime(int startWait) {
        this.setStartWait(startWait);
        this.setEndWait(0);
    }

    /**
     * constructor
     * @param time a time that the wait started at as a Time object
     */
    public WaitTime(Time time) {
        this.setStartWait(time);
        this.setEndWait(0);
    }
    
    public int getStartWait() {
        return startWait;
    }

    /**
     * setStartWait (int)
     * @param startWait int to set the time the wait started at to
     */
    public void setStartWait(int startWait) {
        this.startWait = startWait;
    }

    /**
     * setStartWait (Time)
     * @param time Time to set the time the wait started at to
     */
    public void setStartWait(Time time) {
        this.startWait = time.getCurrentTime();
    }

    public int getEndWait() {
        return endWait;
    }

    /**
     * setEndWait(int)
     * @param endWait int to set as the time the wait ended at
     */
    public void setEndWait(int endWait) {
        this.endWait = endWait;
    }

    /**
     * setEndWait(Time)
     * @param time Time to set as the time the wait ended at
     */
    public void setEndWait(Time time) {
        this.endWait = time.getCurrentTime();
    }
    
    /**
     * getWaitDuration
     * calculates how long the wait was
     * @return int of how long the wait took in minutes
     */
    public int getWaitDuration() {
        return this.getEndWait() - this.getStartWait();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WaitTime waitTime = (WaitTime) o;

        if (startWait != waitTime.startWait) return false;
        return endWait == waitTime.endWait;
    }

    @Override
    public int hashCode() {
        int result = startWait;
        result = 31 * result + endWait;
        return result;
    }

    @Override
    public String toString()
    {
        return "Wait Started at: " + startWait + "\nWait Ended at: " + endWait;

    }

    public static void main(String[] args)
    {
        WaitTime waiting = new WaitTime();
        Time current = new Time();
        current.setCurrentTime(10);
        waiting.setStartWait(current);
        current.setCurrentTime(current.getCurrentTime() + 50); //waited 50 minutes
        waiting.setEndWait(current);

        System.out.println(waiting);
        System.out.println("\ni waited for " + waiting.getWaitDuration() + " minutes!");


    }
}
