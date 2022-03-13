package assignment;

public class WaitTime {
    // PRIVATE INSTANCE VARIABLES
    private int startWait;
    private int endWait;

    public WaitTime() {
        this.setStartWait(0);
        this.setEndWait(0);
    }

    public WaitTime(int startWait) {
        this.setStartWait(startWait);
        this.setEndWait(0);
    }

    public int getStartWait() {
        return startWait;
    }

    public void setStartWait(int startWait) {
        this.startWait = startWait;
    }

    public void setStartWait(Time time) {
        this.startWait = time.getCurrentTime();
    }

    public int getEndWait() {
        return endWait;
    }

    public void setEndWait(int endWait) {
        this.endWait = endWait;
    }

    public void setEndWait(Time time) {
        this.endWait = time.getCurrentTime();
    }

    public int getWaitDuration() {
        return this.getEndWait() - this.getStartWait();
    }
}
