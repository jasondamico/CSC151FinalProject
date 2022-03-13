package assignment;

import java.util.Stack;

/*
The FastPass class creates and object FastPass which is simply an object that allows a person to skip to the front of a line,
sometimes a fastpass can only be used on a specific ride.
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FastPass fastPass = (FastPass) o;

        return passAttraction != null ? passAttraction.equals(fastPass.passAttraction) : fastPass.passAttraction == null;
    }

    @Override
    public int hashCode() {
        return passAttraction != null ? passAttraction.hashCode() : 0;
    }

    @Override
    public String toString()
    {
        if (passAttraction != null)
        {
            return "FastPass for " + passAttraction.getName();
        }
        else
        {
            return "FastPass";
        }
    }

    public static void main(String[] args)
    {
        Stack passes = new Stack();
        Attraction helloWorld = new Attraction("helloWorld", 5, 10, 5);
        FastPass pass1 = new FastPass();
        FastPass pass2 = new FastPass(helloWorld);
        FastPass pass3 = new FastPass();

        passes.add(pass1);
        passes.add(pass2);
        passes.add(pass3);

        System.out.println(passes);
    }
}
