package assignment;

public class FastPass
{
    private Attraction passAttraction;

    /**
     * empty constructor
     * make a FastPass object (a piece of paper, effectively)
     */
    public FastPass()
    {

    }

    /**
     * constructor
     * makes an attraction specific FastPass
     * @param passAttraction the attraction the FastPass is attached to
     */
    public FastPass(Attraction passAttraction)
    {
        this.passAttraction = passAttraction;
    }
}
