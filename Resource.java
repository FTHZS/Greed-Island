import java.util.HashMap;

interface Resource {
    //itemType type = itemType.Resource;
}

interface Edible {
    
    //itemType type = itemType.Edible;
    int getHungerUnits();
    void eat();
}

interface Craftable {
    //HashMap<String,Integer> recipie = new HashMap<String,Integer>();
    
    //itemType type = itemType.Craftable;
    int getCraftUnits();
    HashMap<String,Integer> recipie();
}

interface Weapon {
    //int attackUnits = 0;
    
    //itemType type = itemType.Weapon;
    int getAttackUnits();
}