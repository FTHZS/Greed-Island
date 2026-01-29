/*
interface Decision {
    void make(Object[] parameters);
}

class Sleep implements Decision {
    int Hours;
    Character character;
    
    Sleep(Character character){
        this.character = character;
    }
    
    public void make(Object[] parameters){
        Hours = (int) parameters[0];
    }
}

class Travel implements Decision {
    String Location;
    Character character;
    
    Travel(Character character){
        this.character = character;
    }
    
    public void make(Object[] parameters){
        Location = (String) parameters[0];
    }
}

class Observe implements Decision {
    Character character;
    
    Observe(Character character){
        this.character = character;
    }
    
    public void make(Object[] parameters){
        
    }
}

class Eat implements Decision {
    String item;
    Character character;
    
    Eat(Character character){
        this.character = character;
    }
    
    public void make(Object[] parameters){
        item = (String) parameters[0];
    }
}

class Drink implements Decision {
    String item;
    Character character;
    
    Drink(Character character){
        this.character = character;
    }
    
    public void make(Object[] parameters){
        item = (String) parameters[0];
    }
}

class Give implements Decision {
    String item;
    Character reciever;
    Character character;
    
    Give(Character character){
        this.character = character;
    }
    
    public void make(Object[] parameters){
        item = (String) parameters[0];
        reciever = (Character) parameters[1];
    }
}
*/


/*class SleepTask extends Thread {
    Character character;
    int hours;
    
    SleepTask(Character character, int hours) {
        this.character = character;
        this.hours = hours;
    }
    
    @Override
    public void run() {
        int current = Greed_Island.time.get();
        while (Greed_Island.time < current + hours*60) {
            try{
                Thread.sleep(1000);
                System.out.println("Waiting, "+Greed_Island.time);
            }catch(Exception e){
                System.out.println(e);
            }
        }
        character.setStatus("Sleeping",false);
        System.out.println("WOKE UP "+character.Name);
    }
}*/


/*class TimeSetter extends Thread{
    int time;
    
    TimeSetter(){
        time = 0;
    }
    
    @Override
    public void run(){
        for (int Round = 1; Round<=5; Round++) {
            if (Greed_Island.showMessages){
                new Dialogue("Day "+Round+" (Round "+Round+")\n").display(100);
            }
            
            time = 0;
            for (int t = 0; t < 288;t++){
                //System.out.println(timeToString());
                
                //Greed_Island.Turn();
                
                time += 5;
                try {
                    this.sleep(1);
                } catch(Exception e) {
                
                }
            }
        }
    }
}*/


/*class Runner extends Thread {
    int time;
    
    Runner() {
        time = 0;
    }
    
    @Override
    public void run() {
        Greed_Island.runIteration(true);
    }
}*/

/*interface example {
    void make();
    void make(String i);
}

class Decision1 implements example {
    public void make(String i) {
    
    }
    
    public void make(){
        System.out.println("Decision 1 is made.");
    }
}

class Decision2 implements example {
    public void make(String i) {
    
    }
    
    public void make(){
        System.out.println("Decision 2 is made.");
    }
}

class printName implements example {
    public void make() {
    
    }
    
    public void make(String name){
        System.out.println("the name is: "+name);
    }
}*/

/*Inventory(HashMap<String,Integer> inventory){
        this.inventory = new HashMap<String,Item>();
        
        for (Map.Entry<String,Integer> entry: inventory.entrySet()) {
            Item i;
            switch (entry.getKey()) {
                case "Apple":
                    i = Item.Apple;
                    break;
                case "Wood":
                    i = Item.Wood;
                    break;
                case "Sticks":
                    i = Item.Sticks;
                    break;
                case "Logs":
                    i = Item.Logs;
                    break;
                case "Berries":
                    i = Item.Berries;
                    break;
                case "Poisonous_Berries":
                    i = Item.Poisonous_Berries;
                    break;
                case "Vines":
                    i = Item.Vines;
                    break;
                case "Stone":
                    i = Item.Stone;
                    break;
                case "Axe":
                    i = Item.Axe;
                    break;
                case "Bow":
                    i = Item.Bow;
                    break;
                case "Arrows":
                    i = Item.Arrows;
                    break;
                case "Poison_Arrows":
                    i = Item.Poison_Arrows;
                    break;
                default:
                    i = Item.Stone;
                    break;
            }
            i.set(entry.getValue());
            this.inventory.put(entry.getKey(),i);
        }
    }*/
    
    /*
     * Thread t = new Thread(new FListener(Greed_Island.time,(AtomicInteger x)->(x.get() < 300)&&(x.get() > 1740),0){
     */