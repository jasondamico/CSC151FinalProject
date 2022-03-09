package assignment;

import java.util.HashMap;

public class Park {
    private HashMap<String, Attraction> attractions;
    private String name;

    public Park() {
        this.name = "";
        this.attractions = new HashMap<String, Attraction>();
    }

    public Park(String name) {
        attractions = new HashMap<String, Attraction>();
        this.setName(name);
    }
    
    public Park(String name, Attraction[] attractionsArray){
        this.setName(name);
        for(int i = 0; i < attractionsArray.length; i++){
            this.attractions.put(attractionsArray[i].getName(), attractionsArray[i]);
        }
    }
    
    public void addAttraction(String name, Attraction attraction){
        attractions.put(name, attraction);
    }
    
//     public ArrayList<Attraction> rankByPopularity(){
//         ArrayList<Attraction> toReturn = new ArrayList<Attraction>();
//         Collections.addAll(attractions.values(), toReturn);
//         for(int i = 0; i < toReturn.size(); i++){
            
//         }
//     }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
