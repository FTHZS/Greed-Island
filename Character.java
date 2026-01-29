import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class Character { 
    String Name;
    HashMap<String,Integer> Traits;
    volatile HashMap<String,Boolean> Status;
    HashMap<String,Integer> Influences;
    Inventory inventory;
    
    AtomicInteger Health;
    AtomicInteger HungerLevel;
    AtomicInteger EnergyLevel;
    
    String currentLocation;
    RarityPool decisionProbabilityModel;
    ArrayList<String> Truces;
    private boolean destroyed;
    private ArrayList<Listener> threadpool;
    
    final int decisionFrequency;
    private int[] SleepDetails;
    private int poisonedAt;
    
    Character(String Name){
        this.Name = Name;
        Health= new AtomicInteger(1000);
        HungerLevel= new AtomicInteger(1000);
        EnergyLevel= new AtomicInteger(0);
        
        currentLocation = "Bay";
        Truces = new ArrayList<String>();
        decisionFrequency = 300;
        
        Status = new HashMap<String,Boolean>();
        Traits = new HashMap<String,Integer>();
        inventory = new Inventory();
        decisionProbabilityModel = new RarityPool();
        Influences = new HashMap<String,Integer>();
        SleepDetails = new int[]{0,0};
        
        threadpool = new ArrayList<Listener>();
        
        Traits.put("Sleep",0);
        Traits.put("Travel",0);
        Traits.put("Eat",0);
        Traits.put("Give",0);
        Traits.put("Craft",0);
        Traits.put("Attack",0);
        
        Status.put("Diseased",false);
        Status.put("Poisoned",false);
        Status.put("Tired",false);
        Status.put("Confused",false);
        Status.put("Hungry",false);
        Status.put("Sleeping",false);
        Status.put("Dead",false);
        
        decisionProbabilityModel.add("Sleep",decisionFrequency);
        decisionProbabilityModel.add("Travel",decisionFrequency);
        decisionProbabilityModel.add("Eat",decisionFrequency);
        decisionProbabilityModel.add("Give",decisionFrequency);
        decisionProbabilityModel.add("Craft",decisionFrequency);
        decisionProbabilityModel.add("Attack",decisionFrequency);
        
        Influences.put("Sleep",0); //maybe initially 150?
        Influences.put("Travel",0);
        Influences.put("Eat",0);
        Influences.put("Give",0);
        Influences.put("Craft",0);
        Influences.put("Attack",0);
        
    }
    
    Character Clone(String name) {
        Character child = new Character(name);
                
        child.Health = new AtomicInteger(1000);
        
        final int bound = 294;
        int variance = 15;
        
        if (Name== "Progenitor"){
            variance = 50;
        }
        
        child.Traits = new HashMap<String,Integer>();
        for (Map.Entry<String,Integer> entry : Traits.entrySet()) {
            int mutation = RarityPool.randInt(2*variance)-variance;
            
            if (entry.getValue() > bound-10) {
                mutation = RarityPool.randInt(2*5)-5; //decrease variance as getting close to max.
            }
            
            if (entry.getValue()+mutation >= bound ){//|| entry.getValue()+mutation <= (bound*-1)){
                mutation = bound-entry.getValue();
            }
            child.Traits.put(entry.getKey(),entry.getValue()+mutation);
        }
        
        if (child.Name == "KING") {
            child.Traits.put("Sleep",-1000);
            child.Traits.put("Travel",-400);
            child.Traits.put("Eat",294);
            child.Traits.put("Give",-1000);
            child.Traits.put("Craft",-100);
            child.Traits.put("Attack",100);
        }
        
        child.Status = new HashMap<String,Boolean>();
        child.Status.put("Diseased",false);
        child.Status.put("Poisoned",false);
        child.Status.put("Tired",false);
        child.Status.put("Confused",false);
        child.Status.put("Hungry",false);
        child.Status.put("Sleeping",false);
        child.Status.put("Dead",false);
        
        /*child.decisionProbabilityModel = new RarityPool();
        for (Map.Entry<String,Double> entry : decisionProbabilityModel.Outcomes.entrySet()) {
            int mutation = RarityPool.randInt(20)-10;
            child.decisionProbabilityModel.add(entry.getKey(),entry.getValue().intValue()+mutation);
        }*/
        
        return child;
    }
    
    void spawn() {
        String[] choices = Location.getLocations();
        currentLocation = choices[RarityPool.randInt(choices.length)];
        
        /*new Thread(
        new TickListener<AtomicInteger>(Greed_Island.time,(x)->(x.get() < 300)&&(x.get() > 1740),0){
            @Override
            public void onCondition() {
                Influences.replace("Sleep",25);
            }
        }).start();
        */ 
       //this overrides the sleep influence.
        
        TickListener<Boolean> t1 = new TickListener<Boolean>(true,(x) -> true,1){
            @Override
            void onCondition() {
                setHunger(-1,true);
            }
        };
        threadpool.add(t1);
        t1.setName(Name+"#"+Greed_Island.IterationNumber+" hunger tick");
        t1.start();
        
        TickListener<HashMap<String, Boolean>> t2 = new TickListener<HashMap<String,Boolean>>(Status,(x) -> x.get("Poisoned")==true,1){
            @Override
            void onCondition() {
                /*int currentTime = Greed_Island.time.get();
                for (int i=0;i<15;i++) {
                    while (Greed_Island.time.get() < (currentTime+(1*i))) {
                        try {
                            Thread.sleep(0);
                        } catch (Exception e) {}
                    }
                    setHealth(-1,true);
                    if (Health <=0) {
                        break;
                    }
                }
                setStatus("Poisoned",false);*/
                
                if (Greed_Island.time.get() > 15+poisonedAt) {
                    setStatus("Poisoned",false);
                    return;
                }
                
                //sendmessage(" has lost 1 hp to poison.");
                setHealth(-1,true);
            }
        };
        threadpool.add(t2);
        t2.setName(Name+"#"+Greed_Island.IterationNumber+" poison tick");
        t2.start();
        
        TickListener<HashMap<String, Boolean>> t3 = new TickListener<HashMap<String,Boolean>>(Status,(x) -> x.get("Hungry")==true,1){
            @Override
            void onCondition() {
                //System.out.println(Name+" has incremented -1 hp to hunger. "+Health);
                if (Health.get() <=0) {
                    return;
                }
                setHealth(-1,true);
            }
        };
        threadpool.add(t3);
        t3.setName(Name+"#"+Greed_Island.IterationNumber+" Hungry-health tick");
        t3.start();
        
        StateListener<HashMap<String, Boolean>> t4 = new StateListener<HashMap<String,Boolean>>(Status,(x) -> x.get("Sleeping")==true,1){
            @Override
            void onCondition() {                
                int now = Greed_Island.time.get();
                while (running && Greed_Island.time.get() < SleepDetails[0]+60*SleepDetails[1]) {
                    try {
                        Thread.sleep(0);
                    } catch (Exception e) {
                        //e.printStackTrace();
                        setStatus("Sleeping",false);
                        Thread.currentThread().interrupt();
                    }
                }
                
                if (Greed_Island.messageSettings.get("wake up")) sendmessage(" woke up.");
                setStatus("Sleeping",false);
            }
        };
        threadpool.add(t4);
        t4.setName(Name+"#"+Greed_Island.IterationNumber+" Sleep State");
        t4.start();
    }
    
    void setHealth(int value, boolean increment){
        int newHealth;
        if (!increment) {
            Health.set(value); // atomic write
            newHealth = value;
        } else {
            newHealth = Health.addAndGet(value); // atomic add
        }
        
        //System.out.println(Name+" has incremented "+value+" hp, now at "+Health);
        if (newHealth <= 0) {
            setStatus("Dead", true);
            Health.set(0); // clamp to zero safely
            destroy();
            sendmessage(" has died.");
        }
    }
    
    void destroy() {
        if (destroyed == true) {
            return;
        }
        
        destroyed = true;
        
        for (Listener l :threadpool) {
            l.stop();
        }
        
        for (Map.Entry<String,Item> entry: inventory.inventory.entrySet()) {
            Location.add(currentLocation,entry.getKey(),entry.getValue().units);
            inventory.set(entry.getKey(),0);
        }
    }
    
    void setHunger(int value, boolean increment){
        int newHunger;

        if (!increment) {
            HungerLevel.set(value);   // atomic write
            newHunger = value;
        } else {
            newHunger = HungerLevel.addAndGet(value); // atomic add
        }
    
        if (newHunger < 0) {
            setStatus("Hungry", true);
            HungerLevel.set(0); // clamp to 0 safely
            //setInfluence("Eat", 50, false);
        } else {
            setStatus("Hungry", false);
            //setInfluence("Eat", -50, false);
        }
    }
    
    void setEnergy(int value, boolean increment) {
        int newEnergy;
    
        if (!increment) {
            EnergyLevel.set(value);
            newEnergy = value;
        } else {
            newEnergy = EnergyLevel.addAndGet(value);
        }
    
        if (newEnergy < 0) {
            setStatus("Tired", true);
            EnergyLevel.set(0);
            //setInfluence("Sleep", 50, true);
        } else {
            setStatus("Tired", false);
            // setInfluence("Sleep", -50, true);
        }
    }
    
    void setInfluence(String key,int value, boolean increment) {
        int prob = decisionProbabilityModel.getR(key)-Traits.get(key);
        int increase = value;
        if (prob-value <= 1) {
            increase = 2-prob; //so that one in 2 chance for eat.
        }
        
        Influences.replace(key, increment==true? Influences.get(key)+increase : value);
    }
    
    synchronized void setStatus(String status, boolean state) {
        if (status == "Poisoned") {
            poisonedAt = Greed_Island.time.get();
        }
        
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
        
        /*if (Greed_Island.time.get()%(60*24) <= 60 && Greed_Island.time.get()%(60*24) >= 1320) {
            setInfluence("Sleep", 150,false);
        }*/
        
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
        
        //Object[] parameters;
        switch (randomDecision) {
            case "Sleep":
                if (Status.get("Sleeping")==true) {
                    randomDecide();
                    return;
                }
                
                int random = RarityPool.randInt(3)+1;
                //Decide("Sleep",new Object[]{hours});
                Sleep(random);
                information = " for "+random+" hours.";
                break;
            case "Travel":
                if (EnergyLevel.get() <= 0) {
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
                if (Choices.size() == 0 || Greed_Island.getCount()<2) {
                    randomDecide();
                    return;
                }
                choice = Choices.get(RarityPool.randInt(Choices.size()));
                

                random = RarityPool.randInt(Greed_Island.Contestants.length);
                Character recipient = Greed_Island.Contestants[random];
                while (recipient.Status.get("Dead") == true || recipient.Name == Name) {
                    random = RarityPool.randInt(Greed_Island.Contestants.length-1);
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
                Craftable item = (Craftable) inventory.get(choice);
                information = " "+choice+"x"+item.getCraftUnits()+" from ";
                HashMap<String,Integer> recipie = item.recipie();
                for (Map.Entry<String,Integer> entry:recipie.entrySet()) {
                    information += entry.getKey()+"x"+entry.getValue()+" ";
                }
                break;
            case "Attack":
                String weapon = "";
                if (inventory.contains("Axe")){
                    weapon = "Axe";
                }
                if (inventory.contains("Bow")&&(inventory.contains("Arrows")||inventory.contains("Poison_Arrows"))) {
                    weapon = RarityPool.randInt(1)==1 ? "Axe":"Bow";
                }
                
                if (weapon == ""||Greed_Island.getCount()<2) {
                    randomDecide();
                    return;
                }
                
                random = RarityPool.randInt(Greed_Island.Contestants.length);
                recipient = Greed_Island.Contestants[random];
                while (recipient.Status.get("Dead") == true || recipient.Name == Name) {
                    random = RarityPool.randInt(Greed_Island.Contestants.length-1);
                    recipient = Greed_Island.Contestants[random];
                }
                
                information = " "+recipient.Name+" using a "+weapon;
                Weapon w = (Weapon) inventory.inventory.get(weapon);
                int attackUnits = w.getAttackUnits();
                
                if (weapon == "Bow") {
                    ArrayList<String> arrows = new ArrayList<String>();
                    for (int i = 0; i<inventory.get("Arrows").units;i++){
                        arrows.add("Arrows");
                    }
                    for (int i = 0; i<inventory.get("Poison_Arrows").units;i++){
                        arrows.add("Poison_Arrows");
                    }
                    
                    int randarrow = RarityPool.randInt(arrows.size());
                    String arrowType = arrows.get(randarrow);
                    
                    inventory.set(arrowType,inventory.get(arrowType).units - 1);
                    attackUnits += ((Weapon)(inventory.get(arrowType))).getAttackUnits();
          
                    information += " loading it with "+arrowType;
                    
                    if (arrowType == "Poison_Arrow") {
                        recipient.setStatus("Poisoned",true);
                        information += " and poisoning them";
                    }
                }
                
                information += ", dealing "+attackUnits+" dmg.";
                recipient.setHealth(attackUnits * -1,true);
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
        new Dialogue((Greed_Island.timeToString() +" | "+Name+content+"\n")).display(Greed_Island.showMessages==false ? 0 : Greed_Island.messageInterval);
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
        setEnergy(Location.getEnergyCost(currentLocation,location),true);
        currentLocation = location;
    }
    
    void Sleep(int hours){
        setEnergy(hours*100,true);
        
        setStatus("Sleeping",true);
        SleepDetails[0]=Greed_Island.time.get();
        SleepDetails[1]=hours;
        //Greed_Island.runListener(this,hours);
        
        /*int sleptAt = Greed_Island.time.get();
        Listener<AtomicInteger> t = new Listener<AtomicInteger>(Greed_Island.time,(x) -> x.get() >= sleptAt+60*hours){
            @Override
            void onCondition() {
                setStatus("Sleeping",false);
                //System.out.println("Woke up "+Name+" at"+Greed_Island.time.get());
                stop();
            }
        };
        t.start();
        */
    }
    
    void Eat(String item) {
        //inventory.set(item,inventory.get(item).units - 1);
        Edible edi = (Edible) inventory.get(item);
        edi.eat();
        setHunger(edi.getHungerUnits(),true);
        
        
        //EnergyLevel += inventory.get(item).hungerUnits;
        //setEnergylevel(5,true);
        if (item == "Poisonous_Berries") {
            setStatus("Poisoned",true);            
            
            /*int poisonedAtInitial = Greed_Island.time.get();
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
            
            */
           
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
        
        for (Map.Entry<String,Integer>entry:recipie.entrySet()) {
            inventory.set(entry.getKey(),inventory.get(entry.getKey()).units - entry.getValue());
        }
        
        inventory.set(itemName,inventory.get(itemName).units + item.getCraftUnits());
    }
    
    void collect(String item) {
        inventory.set(item,inventory.get(item).units + 1);
        if (Greed_Island.messageSettings.get("resource collect")) sendmessage(" collected "+item+"x1");
        //System.out.println((Greed_Island.timeToString() +" | "+Name+" collected "+item+"x1"));
    }
    
    void display(){
        System.out.println("----------------------");
        display("Name");
        //System.out.println("Health: "+ Health);
        //System.out.println("Hunger: "+ HungerLevel);
        //System.out.println("Energy: "+ EnergyLevel);
        //System.out.println("----------------------");
        System.out.print("\nTraits: ");
        display("Traits");        
        //System.out.println("\n----------------------");
        System.out.print("\nStatuses: ");
        display("Status");        
        //System.out.println("\n----------------------");
        System.out.print("\nInventory: ");
        display("Inventory");
        System.out.println("\n----------------------");
    }
    
    void display(String info) {
        switch (info) {
            case "Name":
                System.out.print("Name: " + Name+" ("+Health+" hp) ("+HungerLevel+" hunger) ("+EnergyLevel+" energy)");
                break;
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
                boolean empty = true;
                for (Map.Entry<String,Boolean> entry:Status.entrySet()) {
                    //System.out.print(entry.getKey()+": "+ entry.getValue()+"  ");
                    if (entry.getValue() == true) {
                        System.out.print(entry.getKey()+"  ");
                        empty = false;
                    }
                }
                if (empty == true) {
                    System.out.print("-----");
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