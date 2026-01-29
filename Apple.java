import java.util.HashMap;


class Apple extends Item implements Edible {
    final int hungerUnits;
    
    Apple(){
        super();
        type = itemType.Edible;
        
        this.hungerUnits = 50;
    }
    
    public int getHungerUnits() {
        return hungerUnits;
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
        
        this.hungerUnits = 25;
    }
    
    public int getHungerUnits() {
        return hungerUnits;
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
        
        this.hungerUnits = 25;
    }
    
    public int getHungerUnits() {
        return hungerUnits;
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
    final int craftUnits;
    
    Sticks() {
        super();
        type = itemType.Craftable;
        
        recipie = new HashMap<String,Integer>();
        recipie.put("Wood",3);
        craftUnits = 5;
    }
    
    @Override
    public HashMap<String,Integer> recipie() {
        return recipie;
    }
    
    @Override
    public int getCraftUnits() {
        return craftUnits;
    }
}

class Logs extends Item implements Craftable {
    HashMap<String,Integer> recipie;
    final int craftUnits;
    
    Logs() {
        super();
        type = itemType.Craftable;
        
        recipie = new HashMap<String,Integer>();
        recipie.put("Wood",3);
        craftUnits = 5;
    }
    
    @Override
    public HashMap<String,Integer> recipie() {
        return recipie;
    }
    
    @Override
    public int getCraftUnits() {
        return craftUnits;
    }
}

class Arrows extends Item implements Craftable,Weapon {
    HashMap<String,Integer> recipie;
    final int attackUnits;
    final int craftUnits;
    
    Arrows() {
        super();
        type = itemType.Weapon;
        
        this.attackUnits = 20;
        recipie = new HashMap<String,Integer>();
        recipie.put("Sticks",3);
        recipie.put("Stone",3);
        craftUnits = 3;
    }
    
    @Override
    public HashMap<String,Integer> recipie() {
        return recipie;
    }
    
    @Override
    public int getAttackUnits() {
        return attackUnits;
    }
    
    @Override
    public int getCraftUnits() {
        return craftUnits;
    }
}

class Poison_Arrows extends Item implements Craftable,Weapon {
    HashMap<String,Integer> recipie;
    final int attackUnits;
    final int craftUnits;
    
    Poison_Arrows() {
        super();
        type = itemType.Weapon;
        
        this.attackUnits = 25;
        recipie = new HashMap<String,Integer>();
        recipie.put("Sticks",3);
        recipie.put("Stone",3);
        recipie.put("Poisonous_Berries",3);
        craftUnits = 3;
    }
    
    @Override
    public HashMap<String,Integer> recipie() {
        return recipie;
    }
    
    @Override
    public int getAttackUnits() {
        return attackUnits;
    }
    
    @Override
    public int getCraftUnits() {
        return craftUnits;
    }
}

class Axe extends Item implements Craftable, Weapon {
    HashMap<String,Integer> recipie;
    final int attackUnits;
    final int craftUnits;
    
    Axe() {
        super();
        type = itemType.Weapon;
        
        this.attackUnits = 50;
        recipie = new HashMap<String,Integer>();
        recipie.put("Sticks",2);
        recipie.put("Stone",4);
        craftUnits = 1;
    }
    
    @Override
    public HashMap<String,Integer> recipie() {
        return recipie;
    }
    
    @Override
    public int getAttackUnits() {
        return attackUnits;
    }
    
    @Override
    public int getCraftUnits() {
        return craftUnits;
    }
}

class Bow extends Item implements Craftable, Weapon {
    HashMap<String,Integer> recipie;
    final int attackUnits;
    final int craftUnits;
    
    Bow() {
        super();
        type = itemType.Weapon;
        
        this.attackUnits = 50;
        recipie = new HashMap<String,Integer>();
        recipie.put("Sticks",3);
        recipie.put("Vines",3);
        craftUnits = 1;
    }
    
    @Override
    public HashMap<String,Integer> recipie() {
        return recipie;
    }
    
    @Override
    public int getAttackUnits() {
        return attackUnits;
    }
    
    @Override
    public int getCraftUnits() {
        return craftUnits;
    }
}