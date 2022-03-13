package assignment;

import java.util.Stack;

/*
The FastPass class creates and object FastPass which is simply an object that allows a person to join the FastPass queue,
sometimes a FastPass can only be used on a specific ride.
 */
public class FastPass {
    private String attractionName;
    
    /**
     * empty constructor
     * make a FastPass object (a piece of paper, effectively) that works at any Attraction
     */
    public FastPass() {
    	this.attractionName = "UNIVERSAL";
    }

    /**
     * constructor
     * makes an attraction specific FastPass
     * @param attractionName the attraction the FastPass is attached to
     */
    public FastPass(String attractionName) {
        this.attractionName = attractionName;
    }
    
    public String getAttractionName() {
    	return attractionName;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FastPass fastPass = (FastPass) o;

        return attractionName != null ? attractionName.equals(fastPass.attractionName) : fastPass.attractionName == null;
    }

    @Override
    public int hashCode() {
        return attractionName != null ? attractionName.hashCode() : 0;
    }

    @Override
    public String toString()
    {
        if (attractionName != null)
        {
            return "FastPass for " + attractionName;
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
        FastPass pass2 = new FastPass(helloWorld.getName());
        FastPass pass3 = new FastPass();

        passes.add(pass1);
        passes.add(pass2);
        passes.add(pass3);

        System.out.println(passes);
    }
}
