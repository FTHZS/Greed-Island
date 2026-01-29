import java.util.HashMap;

/*enum Item {
    Apple(itemType.Edible,10),
    Wood(itemType.Resource),
    Sticks(itemType.Craftable),
    Logs(itemType.Craftable),
    Berries(itemType.Edible,5),
    Poisonous_Berries(itemType.Edible,5),
    Vines(itemType.Resource),
    Stone(itemType.Resource),
    Axe(itemType.Craftable),
    Bow(itemType.Craftable),
    Arrows(itemType.Craftable),
    Poison_Arrows(itemType.Craftable);
    
    itemType type;
    int units;
    
    int energyUnits;
    
    Item(itemType type){
        this.type = type;
        this.units = 0;
    }
    
    Item(itemType type, int energyUnits){
        this.type = itemType.Edible;
        this.energyUnits = energyUnits;
    }
    
    void set(int units) {
        this.units=units;
    }
    
    private HashMap<String,Integer> recipie(String item) {
        HashMap<String,Integer> recipi = new HashMap<String,Integer>();
        if (type != itemType.Craftable) {
            return recipi;
        }
        
        switch (item) {
            case "Sticks":
                recipi.put("Wood",3);
                break;
        }
        return recipi;
    }
    
    boolean canCraft(String item) {
        return true;
    }
    
    void craft(Inventory inventory,String itemName) {
        HashMap<String,Integer> recipi = recipie(itemName);
        for (String item: recipi.keySet()) {
            inventory.set(item,inventory.get(item).units - 1);
        }
        
        inventory.set(itemName,inventory.get(itemName).units + 1);
    }
    
}*/

abstract class Item {
    itemType type;
    int units;
        
    Item(){
        this.units = 0;
    }
    
    void set(int units) {
        this.units=units;
    }
    
    itemType getType() {
        return this.type;
    }
    
    /*private HashMap<String,Integer> recipie(String item) {
        HashMap<String,Integer> recipi = new HashMap<String,Integer>();
        if (type != itemType.Craftable) {
            return recipi;
        }
        
        switch (item) {
            case "Sticks":
                recipi.put("Wood",3);
                break;
        }
        return recipi;
    }*/
    
    /*void craft(Inventory inventory,String itemName) {
        HashMap<String,Integer> recipi = recipie(itemName);
        for (String item: recipi.keySet()) {
            inventory.set(item,inventory.get(item).units - 1);
        }
        
        inventory.set(itemName,inventory.get(itemName).units + 1);
    }*/
}