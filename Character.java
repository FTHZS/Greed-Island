import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class Character { 
    String Name;
    HashMap<String,Integer> Traits;
    HashMap<String,Boolean> Status;
    HashMap<String,Integer> Influences;
    Inventory inventory;
    int Health;
    int HungerLevel;
    //int ThirstLevel;
    int EnergyLevel;
    String currentLocation;
    RarityPool decisionProbabilityModel;
    ArrayList<String> Truces;
    
    final int decisionFrequency;
    //int[] SleepDetails;
    //Decision[] decisionList;
    
    //static RarityPool randomInt;
    //static HashMap<String,Integer> decisionProbability;
    /*static {
        //randomInt = new RarityPool(0,100);
        
        decisionProbability = new HashMap<String,Integer>();
        
        decisionProbability.put("Sleep",0);
        decisionProbability.put("Travel",0);
        decisionProbability.put("Observe",0);
        decisionProbability.put("Eat",0);
        decisionProbability.put("Drink",0);
        decisionProbability.put("Give",0);
    }*/
    
    Character(String Name){
        this.Name = Name;
        Health = 100;
        HungerLevel = 100;
        //ThirstLevel = 100;
        EnergyLevel = 1000;
        currentLocation = "Bay";
        Truces = new ArrayList<String>();
        decisionFrequency = 50;//+RarityPool.randInt(;
        
        Status = new HashMap<String,Boolean>();
        Traits = new HashMap<String,Integer>();
        inventory = new Inventory();
        decisionProbabilityModel = new RarityPool();
        Influences = new HashMap<String,Integer>();
        //SleepDetails = new int[]{0,0};
        
        /*Traits.put("Bravery",0);
        Traits.put("Strength",0);
        Traits.put("Physique",0);
        Traits.put("Sloth",0);
        Traits.put("Amiability",0);
        Traits.put("Cunning",0);
        Traits.put("Bloodthirst",0);
        Traits.put("Sharing",0);
        Traits.put("Consumer",0);*/
        
        Traits.put("Sleep",0);
        Traits.put("Travel",0);
        Traits.put("Eat",0);
        Traits.put("Give",0);
        Traits.put("Craft",0);
        
        Status.put("Diseased",false);
        Status.put("Poisoned",false);
        Status.put("Tired",false);
        Status.put("Confused",false);
        //Status.put("Thirsty",false);
        Status.put("Hungry",false);
        Status.put("Sleeping",false);
        Status.put("Dead",false);
        
        /*inventory.set("Wood",0);
        //Inventory.put("Water",item,0);
        inventory.set("Apples",0);
        inventory.set("Berries",0);
        inventory.set("Posionous Berries",0);
        inventory.set("Stone",0);
        inventory.set("Vines",0);
        inventory.set("Sticks",0);
        inventory.set("Logs",0);*/
        
        decisionProbabilityModel.add("Sleep",decisionFrequency);
        decisionProbabilityModel.add("Travel",decisionFrequency);
        //decisionProbabilityModel.add("Observe",100);
        decisionProbabilityModel.add("Eat",decisionFrequency);
        //decisionProbabilityModel.add("Drink",200);
        decisionProbabilityModel.add("Give",decisionFrequency);
        decisionProbabilityModel.add("Craft",decisionFrequency);
        
        Influences.put("Sleep",0);
        Influences.put("Travel",0);
        Influences.put("Eat",-20);
        Influences.put("Give",0);
        Influences.put("Craft",0);
        
        /*decisionList = new Decision[]{
            new Sleep(this),
            new Travel(this),
            new Observe(this),
            new Eat(this),
            new Drink(this),
            new Give(this)
        };*/
        
    }
    
    Character Clone(String name) {
        Character child = new Character(name);
                
        child.Health = Health;
        
        child.Traits = new HashMap<String,Integer>();
        for (Map.Entry<String,Integer> entry : Traits.entrySet()) {
            int mutation = RarityPool.randInt(20)-10;
            if (entry.getValue()+mutation >= 100 || entry.getValue()+mutation <= -100){
                mutation = 0;
            }
            child.Traits.put(entry.getKey(),entry.getValue()+mutation);
        }
        
        child.decisionProbabilityModel = new RarityPool();
        for (Map.Entry<String,Double> entry : decisionProbabilityModel.Outcomes.entrySet()) {
            int mutation = RarityPool.randInt(20)-10;
            child.decisionProbabilityModel.add(entry.getKey(),entry.getValue().intValue()+mutation);
        }
        
        return child;
    }
    
    void spawn() {
        String[] choices = Location.getLocations();
        currentLocation = choices[RarityPool.randInt(choices.length)];
        
        new Thread(
        new FListener<AtomicInteger>(Greed_Island.time,(x)->(x.get() < 300)&&(x.get() > 1740),0){
            @Override
            public void onCondition() {
                Influences.replace("Sleep",25);
            }
        }).start();
        
       new Thread(new FListener<Boolean>(true,(x) -> true,5){
            @Override
            void onCondition() {
                setHunger(-1,true);
            }
        }).start();
        
    }
    
    void setHealth(int value, boolean increment){
        Health = (increment==false)? value: (Health+value);
        
        if (Health < 0) {
            setStatus("Dead",true);
            Health = 0;
        }
    }
    
    void setHunger(int value, boolean increment){
        HungerLevel = (increment==false)? value: (HungerLevel+value);
        
        if (HungerLevel < 0) {
            setStatus("Hungry",true);
            HungerLevel = 0;
            Health -= 1;
            Influences.replace("Eat",20);
        }
    }
    
    void setEnergy(int value, boolean increment){
        EnergyLevel = (increment==false)? value: (EnergyLevel+value);
        
        if (EnergyLevel > 700){
            Influences.replace("Sleep",-50);
        }
        
        if (EnergyLevel < 0) {
            setStatus("Tired",true);
            EnergyLevel = 0;
            
            Influences.replace("Sleep",20);
        }
    }
    
    /*void setThirst(int value, boolean increment){
        ThirstLevel = (increment==false)? value: (ThirstLevel+value);
        
        if (ThirstLevel < 0) {
            setStatus("Thirsty",true);
            ThirstLevel = 0;
        }
    }*/
    
    void setStatus(String status, boolean state) {
        Status.replace(status,state);
    }
    
    /*void setTrait(String trait, int value, boolean increment) {
        Traits.replace(trait,(increment==false) ? value : Traits.get(trait)+value);
    }*/
    
    /*void Decide(String decision,Object[] parameters){     
        HashMap<String, Integer> Converter = new HashMap<String,Integer>();
        Converter.put("Sleep",0);
        Converter.put("Travel",1);
        Converter.put("Observe",2);
        Converter.put("Eat",3);
        Converter.put("Drink",4);
        Converter.put("Give",5);
        Converter.put("Craft",6);
        
        //decisionList[Converter.get(decision)].make(parameters);   
    }*/
    
    void randomDecide(){
        //Influences.replace("Sleep",-30);
        
        RarityPool model = new RarityPool();
        
        for (Map.Entry<String,Double> entry : decisionProbabilityModel.Outcomes.entrySet()) {
            model.add(entry.getKey(),entry.getValue().intValue()-Influences.get(entry.getKey())-Traits.get(entry.getKey()));
        }
        
        model.idealize("Observe");
        String randomDecision = model.simulate();
        String information = "";
        
        /*if (information == "") {
            System.out.println(randomDecision);
            model.displayOutcomes();
            model.displayPool();
            return;
        }*/
        
        Object[] parameters;
        switch (randomDecision) {
            case "Sleep":
                if (Status.get("Sleeping")==true) {
                    randomDecide();
                    return;
                }
                
                int random = RarityPool.randInt(9)+1;
                //Decide("Sleep",new Object[]{hours});
                Sleep(random);
                information = " for "+random+" hours.";
                break;
            case "Travel":
                if (EnergyLevel <= 0) {
                    randomDecide();
                    return;
                }
                
                String[] availableChoices = Location.getTravelOptions(currentLocation); 
                String destination = availableChoices[RarityPool.randInt(availableChoices.length)];
                information = " to "+destination+" (from "+currentLocation+")";
                Travel(destination);
                break;
            case "Eat":
                ArrayList<String> Choices = inventory.getFiltered(itemType.Edible,true);
                if (Choices.size() == 0) {
                    randomDecide();
                    return;
                }
                String choice = Choices.get(RarityPool.randInt(Choices.size()));
                Eat(choice);
                information = " "+choice+"x1";
                break;
            case "Give":
                Choices = inventory.getItemList();
                if (Choices.size() == 0) {
                    randomDecide();
                    return;
                }
                choice = Choices.get(RarityPool.randInt(Choices.size()));
                
                random = RarityPool.randInt(Greed_Island.Contestants.length);
                Character recipient = Greed_Island.Contestants[random];
                while (recipient.Status.get("Dead") == true) {
                    random = RarityPool.randInt(Greed_Island.Contestants.length);
                    recipient = Greed_Island.Contestants[random];
                }
                
                Give(choice,recipient);
                information = " "+choice+"x1 to "+recipient.Name;
                break;
            case "Craft":
                Choices = inventory.getCraftable();
                if (Choices.size() == 0) {
                    randomDecide();
                    return;
                }
                choice = Choices.get(RarityPool.randInt(Choices.size()));
                
                Craft(choice);
                information = " "+choice+"x1 from ";
                Craftable item = (Craftable) inventory.get(choice);
                HashMap<String,Integer> recipie = item.recipie();
                for (Map.Entry<String,Integer> entry:recipie.entrySet()) {
                    information += entry.getKey()+"x"+entry.getValue()+"  ";
                }
                break;
            }
        
        if (Greed_Island.showMessages == false) {
            return;
        }
        
        if (randomDecision != "Observe"){
            //new Dialogue((Greed_Island.timeToString() +" | "+Name+" Decided to "+randomDecision+information+"\n")).display(10);
            sendmessage(" Decided to "+randomDecision+information);
        }
    }
    
    void sendmessage(String content) {
        new Dialogue((Greed_Island.timeToString() +" | "+Name+content+"\n")).display(10);
    }
    
    /*ArrayList<String> getEdibles(){
        ArrayList<String> edibles = new ArrayList<String>();
        for (Map.Entry<Item,Integer> entry :inventory.entrySet()) {
            if (entry.getKey().type == itemType.Edible) {
                edibles.add(entry.getKey().name);
            }
        }
        return edibles;
    }*/
    
    void Travel(String location){
        EnergyLevel -= Location.getEnergyCost(currentLocation,location);
        currentLocation = location;
    }
    
    void Sleep(int hours){
        setEnergy(hours*100,true);
        
        setStatus("Sleeping",true);
        //SleepDetails[0]=Greed_Island.time.get();
        //SleepDetails[1]=hours;
        //Greed_Island.runListener(this,hours);
        int sleptAt = Greed_Island.time.get();
        Thread t = new Thread(new Listener<AtomicInteger>(Greed_Island.time,(x) -> x.get() >= sleptAt+60*hours){
            @Override
            void onCondition() {
                setStatus("Sleeping",false);
                //System.out.println("Woke up "+Name+" at"+Greed_Island.time.get());
            }
        });
        
        t.start();
    }
    
    void Eat(String item) {
        //inventory.set(item,inventory.get(item).units - 1);
        Edible edi = (Edible) inventory.get(item);
        edi.eat();
        
        
        //EnergyLevel += inventory.get(item).hungerUnits;
        //setEnergylevel(5,true);
        if (item == "Poisonous_Berries") {
            Status.replace("Poisoned",true);            
            
            int poisonedAtInitial = Greed_Island.time.get();
            for (int i = 0;i<12;i++) {
                int poisonedAt = poisonedAtInitial+5*i;
                
                Thread t = new Thread(new Listener<AtomicInteger>(Greed_Island.time,(x) -> x.get() >= poisonedAt+5){
                    @Override
                    void onCondition() {
                        setHealth(-2,true);
                    }
                });
                
                t.start();
            }
            
            Thread t = new Thread(new Listener<AtomicInteger>(Greed_Island.time,(x) -> x.get() >= poisonedAtInitial+60){
                    @Override
                    void onCondition() {
                        Status.replace("Poisoned",false);
                    }
                });
            t.start();
            /*int poisonedAt = Greed_Island.time.get();
            Thread t = new Thread(new Runnable(){
                for (int i = 0;i<12;i++){
                    try {Thread.Sleep(0)}
                }
                Status.replace("Poisoned",false);
            });*/
        }
    }
    
    void Give(String item, Character recipient) {
        inventory.set(item,inventory.get(item).units - 1);
        recipient.inventory.set(item,recipient.inventory.get(item).units + 1);
    }
    
    void Craft(String itemName) {
        Craftable item = (Craftable) inventory.get(itemName);
        HashMap<String,Integer> recipie = item.recipie();
        
        for (String key:recipie.keySet()) {
            inventory.set(key,inventory.get(key).units - 1);
        }
        
        inventory.set(itemName,inventory.get(itemName).units + 1);
    }
    
    void collect(String item) {
        inventory.set(item,inventory.get(item).units + 1);
        //sendmessage(" collected "+item+"x1");
        //System.out.println((Greed_Island.timeToString() +" | "+Name+" collected "+item+"x1"));
    }
    
    void display(){
        System.out.println("----------------------");
        System.out.println("Name: " + Name+" ("+Health+" hp) ("+HungerLevel+" hunger) ("+EnergyLevel+" energy)");
        //System.out.println("Health: "+ Health);
        //System.out.println("Hunger: "+ HungerLevel);
        //System.out.println("Energy: "+ EnergyLevel);
        //System.out.println("----------------------");
        System.out.print("Traits: ");
        display("Traits");        
        //System.out.println("\n----------------------");
        System.out.print("\nStatuses: ");
        display("Status");        
        //System.out.println("\n----------------------");
        System.out.print("\nInventory: ");
        display("Inventory");
        System.out.println("----------------------");
    }
    
    void display(String info) {
        switch (info) {
            case "Traits":
                for (Map.Entry<String,Integer> entry:Traits.entrySet()) {
                    System.out.print(entry.getKey()+": "+ entry.getValue()+"  ");
                }
                break;
            case "Inventory":
                if (inventory.getItemList().size() <= 0) {
                    System.out.print("-----");
                    break;
                }
                
                for (Map.Entry<String,Item> entry:inventory.inventory.entrySet()) {
                    if (entry.getValue().units>0) {
                        System.out.print(entry.getKey()+"x"+ entry.getValue().units+"  ");
                    }
                }
                break;
            case "Status":
                for (Map.Entry<String,Boolean> entry:Status.entrySet()) {
                    System.out.print(entry.getKey()+": "+ entry.getValue()+"  ");
                }
                break;
        }
    }
    
    static void main(){
        /*ArrayList<Character> a = new ArrayList<Character>();
        a.add(new Character("One"));
        a.add(new Character("Two"));
        
        for (int i =0; i<a.size();i++){
            System.out.println(a.get(i));
        }*/
        
        
        /*ArrayList<ArrayList<String>> b = new ArrayList<ArrayList<String>>();
        ArrayList<String> test = new ArrayList<String>();
        test.add("hello");
        test.add("bye");
        b.add(test);
        System.out.println(b);*/
        
        //a.get(0).Decide();
        //a.get(0).setTrait("Bravery", 35, false);
        //a.get(0).setTrait("Physique",new Integer(RarityPool.randInt(100)).intValue(),false);
        
        /*example[] tree = new example[]{
            new Decision1(),
            new Decision2(),
            new printName(),
        };*/
        
        //tree[2].make(a.get(0).Name);
        
        //decisionProbability.get("Sleep");
        
        Character progenitor = new Character("");
        Character child = progenitor.Clone("TestSubject");
        child.display();
    }
}