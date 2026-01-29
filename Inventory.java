import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

class Inventory{
    HashMap<String,Item> inventory;
    
    Inventory() {
        inventory = new HashMap<String,Item>();
        inventory.put("Apple",new Apple());
        inventory.put("Wood",new Wood());
        inventory.put("Sticks",new Sticks());
        inventory.put("Logs",new Logs());
        inventory.put("Berries",new Berries());
        inventory.put("Poisonous_Berries",new Poisonous_Berries());
        inventory.put("Vines",new Vines());
        inventory.put("Stone",new Stone());
        inventory.put("Axe",new Axe());
        inventory.put("Bow",new Bow());
        inventory.put("Arrows",new Arrows());
        inventory.put("Poison_Arrows",new Poison_Arrows());
    }
    
    void set(String key, int value) {
        inventory.get(key).set(value);
    }
    
    Item get(String key){
        return inventory.get(key);
    }
    
    ArrayList<String> getFiltered(itemType type,boolean contain){
        ArrayList<String> filtered = new ArrayList<String>();
        for (Map.Entry<String,Item> entry: inventory.entrySet()) {
            if ((entry.getValue().type == type)||(entry.getValue().type==itemType.Weapon&&type==itemType.Craftable)) {
                if (contain == true) {
                    if (contains(entry.getKey())) {
                        filtered.add(entry.getKey());
                    }
                } else {
                    filtered.add(entry.getKey());
                }
            }
        }
        return filtered;
        
        /*for (String itemName: getItemList()) {
            if (get(itemName).type.equals(type)) {
                filtered.add(itemName);
            }
        }
        return filtered;*/
    }
    
    ArrayList<String> getItemList() {
        ArrayList<String> list = new ArrayList<String>();
        for (String entry: inventory.keySet()) {
            if (contains(entry)) {
                list.add(entry);
            }
        }
        return list;
    }
    
    boolean canCraft(String itemName) {
        boolean craftable = true;
        
        Craftable item = (Craftable) inventory.get(itemName);
        HashMap<String,Integer> recipie = item.recipie();
        for (Map.Entry<String,Integer> entry:recipie.entrySet()) {
            if (inventory.get(entry.getKey()).units < entry.getValue()) {
                craftable = false;
            }
        }
        
        return craftable;
    }
    
    ArrayList<String> getCraftable() {
        //ArrayList<String> filtered = new ArrayList<String>();
        /*filtered.add("Sticks");
        filtered.add("Logs");
        filtered.add("Axe");
        filtered.add("Bow");
        filtered.add("Arrows");
        filtered.add("Poison_Arrows");
        */
        /*for (int i=0; i<filtered.size();i++) {
            if (canCraft(filtered.get(i)) == false) {
                filtered.remove(filtered.get(i));
            }
        }*/
        
        //return filtered.stream().filter(x-> canCraft(x)==true).collect();
    
        /*for (String key:inventory.keySet()) {
            if (inventory.get(key).type == itemType.Craftable) {
                if (canCraft(key)) {
                    filtered.add(key);
                }
            }
        }
        return filtered;*/
        
        ArrayList<String> filtered = new ArrayList<String>();
        for (String key:getFiltered(itemType.Craftable,false)) {
            if (canCraft(key)){
                filtered.add(key);
            }
        }
        return filtered;
    }
    
    boolean contains(String key){
        if (inventory.get(key).units > 0) {
            return true;
        }
        
        return false;
    }
    
    HashMap<String,Item> getInventory() {return inventory;}
}