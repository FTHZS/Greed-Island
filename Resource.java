import java.util.HashMap;

interface Resource {
    //itemType type = itemType.Resource;
}

interface Edible {
    //int hungerUnits = 0;
    
    //itemType type = itemType.Edible;
    
    void eat();
}

interface Craftable {
    //HashMap<String,Integer> recipie = new HashMap<String,Integer>();
    
    //itemType type = itemType.Craftable;
    
    HashMap<String,Integer> recipie();
}

interface Weapon {
    //int attackUnits = 0;
    
    //itemType type = itemType.Weapon;
    
}