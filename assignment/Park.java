package assignment;

public class Park {
    private Attraction[] attractions;   // Change to hash map
    private String name;

    public Park() {

    }

    public Park(String name, Attraction[] attractions) {
        this.setAttractions(attractions);
        this.setName(name);
    }

    public Attraction[] getAttractions() {
        return attractions;
    }

    public void setAttractions(Attraction[] attractions) {
        this.attractions = attractions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
