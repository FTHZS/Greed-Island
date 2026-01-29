import java.util.HashMap;


class Apple extends Item implements Edible {
    final int hungerUnits;
    
    Apple(){
        super();
        type = itemType.Edible;
        
        this.hungerUnits = 5;
    }
    
    public void eat() {
        units -= 1;
    }
}

class Berries extends Item implements Edible {
    final int hungerUnits;
    
    Berries(){
        super();
        type = itemType.Edible;
        
        this.hungerUnits = 3;
    }
    
    public void eat() {
        units -= 1;
    }
}

class Poisonous_Berries extends Item implements Edible {
    final int hungerUnits;
    
    Poisonous_Berries(){
        super();
        type = itemType.Edible;
        
        this.hungerUnits = 3;
    }
    
    public void eat() {
        units -= 1;
    }
}

class Wood extends Item implements Resource {
        
    Wood() {
        super();
        type = itemType.Resource;
        
    }
}

class Stone extends Item implements Resource {
    
    Stone() {
        super();
        type = itemType.Resource;
        
    }
}

class Vines extends Item implements Resource {
    
    Vines() {
        super();
        type = itemType.Resource;
        
    }
}

class Sticks extends Item implements Craftable {
    HashMap<String,Integer> recipie;
    
    Sticks() {
        super();
        type = itemType.Craftable;
        
        recipie = new HashMap<String,Integer>();
        recipie.put("Wood",1);
    }
    
    @Override
    public HashMap<String,Integer> recipie() {
        return recipie;
    }
}

class Logs extends Item implements Craftable {
    HashMap<String,Integer> recipie;
    
    Logs() {
        super();
        type = itemType.Craftable;
        
        recipie = new HashMap<String,Integer>();
        recipie.put("Wood",1);
    }
    
    @Override
    public HashMap<String,Integer> recipie() {
        return recipie;
    }
}

class Arrows extends Item implements Craftable,Weapon {
    HashMap<String,Integer> recipie;
    final int attackUnits;
    
    Arrows() {
        super();
        type = itemType.Weapon;
        
        this.attackUnits = 5;
        recipie = new HashMap<String,Integer>();
        recipie.put("Sticks",1);
        recipie.put("Stone",1);
    }
    
    @Override
    public HashMap<String,Integer> recipie() {
        return recipie;
    }
}

class Poison_Arrows extends Item implements Craftable,Weapon {
    HashMap<String,Integer> recipie;
    final int attackUnits;
    
    Poison_Arrows() {
        super();
        type = itemType.Weapon;
        
        this.attackUnits = 5;
        recipie = new HashMap<String,Integer>();
        recipie.put("Sticks",1);
        recipie.put("Stone",1);
        recipie.put("Poisonous_Berries",1);
    }
    
    @Override
    public HashMap<String,Integer> recipie() {
        return recipie;
    }
}

class Axe extends Item implements Craftable, Weapon {
    HashMap<String,Integer> recipie;
    final int attackUnits;
    
    Axe() {
        super();
        type = itemType.Weapon;
        
        this.attackUnits = 5;
        recipie = new HashMap<String,Integer>();
        recipie.put("Sticks",5);
        recipie.put("Stone",5);
    }
    
    @Override
    public HashMap<String,Integer> recipie() {
        return recipie;
    }
}

class Bow extends Item implements Craftable, Weapon {
    HashMap<String,Integer> recipie;
    final int attackUnits;
    
    Bow() {
        super();
        type = itemType.Weapon;
        
        this.attackUnits = 5;
        recipie = new HashMap<String,Integer>();
        recipie.put("Sticks",5);
        recipie.put("Vines",5);
    }
    
    @Override
    public HashMap<String,Integer> recipie() {
        return recipie;
    }
}