import java.util.HashMap;
import java.util.Map;

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
    
    static void displayIndex() {
        System.out.println("--Item Index--");
        
        Inventory inventory = new Inventory();
        for (Map.Entry<String, Item>entry : inventory.inventory.entrySet()) {
            String information = entry.getKey()+" ("+entry.getValue().type.toString()+") : ";
            switch (entry.getValue().type) {
                case itemType.Resource:
                    information += "used for crafting other materials";
                    break;
                case itemType.Edible:
                    information += "Restores "+((Edible)(entry.getValue())).getHungerUnits() + " hunger.";
                    break;
                case itemType.Craftable:
                    information += "Requires ";
                    for (Map.Entry<String,Integer>e :((Craftable)(entry.getValue())).recipie().entrySet()) {
                        information += e.getKey()+"x" + e.getValue()+ " ";
                    }
                    information += " to craft "+entry.getKey()+"x"+((Craftable)(entry.getValue())).getCraftUnits();
                    break;
                case itemType.Weapon:
                    information += "Requires ";
                    for (Map.Entry<String,Integer>e :((Craftable)(entry.getValue())).recipie().entrySet()) {
                        information += e.getKey()+"x" + e.getValue()+ " ";
                    }
                    information += " to craft "+entry.getKey()+"x"+((Craftable)(entry.getValue())).getCraftUnits();
                    
                    information += ". Can be used to deal "+((Weapon)(entry.getValue())).getAttackUnits() + " damage ";
                    if (entry.getKey() == "Bow") {
                        information += ", it must be loaded with Arrowsx1 or Poison_Arrowsx1";
                    } else if (entry.getKey() == "Arrow") {
                        information += ", it can only be used with a Bow.";
                    } else if (entry.getKey()== "Poison_Arrows") {
                        information += ", and can inflict 'Poisoned'. it can only be used with a Bow.";
                    }
                    break;
            }
            System.out.println(information+"\n");
        }
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