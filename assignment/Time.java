/**
 * We affirm that we have carried out my academic endeavors with full academic honesty. Signed, Jason D'Amico, Maggie Kelley and Hawkeye Nadel
 */

package assignment;

/*
The Time Object holds the overall current time in the park, and is referenced throughout various other classes
 */
public class Time
{
    private int currentTime;

    /**
     * constructor
     * the time starts at 0
     */
    public Time()
    {
        this.currentTime = 0;
    }

    /**
     * getCurrentTime
     * shows the current time (in minutes)
     * @return
     */
    public int getCurrentTime() //person & attraction use this
    {
        return currentTime;
    }

    /**
     * setCurrentTime
     * changes the current time
     * @param currentTime the time passed in minutes
     */
    public void setCurrentTime(int currentTime) //main uses this
    {
        if (currentTime >= 0) {
            this.currentTime = currentTime;
        } else {
            throw new IllegalArgumentException("A positive time value must be passed.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Time time = (Time) o;

        return currentTime == time.currentTime;
    }

    @Override
    public int hashCode() {
        return currentTime;
    }

    @Override
    public String toString() {
        return "" + currentTime;
    }

    public static void main(String[] args)
    {
        Time minutes = new Time();
        minutes.setCurrentTime(10);
        System.out.println("the time is:\n" + minutes); //10
        minutes.currentTime += 5; //5 minutes pass
        System.out.println("what time is it now?:\n" + minutes); //15
    }
}
