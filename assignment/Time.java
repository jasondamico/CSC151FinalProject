package assignment;

public class Time
{
    private int currentTime;

    public Time()
    {
        this.currentTime = 0;
    }

    public int getCurrentTime() //person & attraction use this
    {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) //main uses this
    {
        this.currentTime = currentTime;
    }
}
