
class Code_bin<T> {}

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
    
/*import java.util.LinkedList;

class Dialogue {
    String Message;
    Dialogue[] Paragraph;
    
    private static final LinkedList<Dialogue> dialogueQueue = new LinkedList<Dialogue>();
    private static boolean workerRunning = false;
    
    Dialogue(String Message){
        this.Message = Message;
    }
    
    Dialogue(Dialogue[] Paragraph){
        this.Paragraph = Paragraph;
    }
    
    void display(int charDelay) {
        try {
            for (int i = 0; i<Message.length();i++) {
                System.out.print(Message.charAt(i));
                Thread.sleep(charDelay);
            }
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
    void display(int charDelay, int lineDelay) {
        try {
            System.out.print("\n");
            for (int i = 0; i<Paragraph.length; i++) {
                //System.out.print("\n");
                Paragraph[i].display(charDelay);
                Thread.sleep(lineDelay);
            }
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static void main(){
        Dialogue one = new Dialogue("Hello world!");
        one.display(100);
        
        Dialogue two = new Dialogue(new Dialogue[]{
            new Dialogue("This is a testing paragraph's first line.\n"),
            new Dialogue("This is a testing paragraph's second line.\n")
        });
        two.display(10,300);
    }
}*/
/*
abstract class Listener<T> implements Runnable{
    Comparison<T> comparison;
    T object;
    
    Listener(T object, Comparison<T> comparison) {
        this.comparison = comparison;
        this.object= object;
    }
    
    abstract void onCondition();
    
    @Override
    public void run(){
        while (comparison.compare(object) == false) {
            try {
                Thread.sleep(0);
            } catch (Exception e) {}
        }
        onCondition();
    }
}

abstract class BiFListener<T> implements Runnable {
    Comparison<T> comparison;
    T object;
    
    BiFListener(T object, Comparison<T> comparison) {
        this.comparison = comparison;
        this.object= object;
    }
    
    abstract void onConditionTrue();
    abstract void onConditionFalse();
    
    @Override
    public void run(){
        while (true) {
            while (comparison.compare(object) == false) {
                try {
                    Thread.sleep(0);
                } catch (Exception e) {}
            }
            onConditionTrue();
            
            while (comparison.compare(object) == true) {
                try {
                    Thread.sleep(0);
                } catch (Exception e) {}
            }
            onConditionFalse();
        }
    }
}

abstract class FListener<T> implements Runnable {
    Comparison<T> comparison;
    T object;
    int interval;
    
    FListener(T object, Comparison<T> comparison, int interval) {
        this.object = object;
        this.comparison = comparison;
        this.interval = interval;
    }
    
    abstract void onCondition();
    
    @Override
    public void run(){
        while (true) {
            while (comparison.compare(object) == false) {
                try {
                    Thread.sleep(0);
                } catch (Exception e) {}
            }
            onCondition();
            
            int currentTime = Greed_Island.time.get();
            while (Greed_Island.time.get() < (currentTime+interval)) {
                try {
                    Thread.sleep(0);
                } catch (Exception e) {}
            }
        }
    }
}
*/

/*import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Scanner;

class Greed_Island{
    static volatile Character[] Contestants;
    static AtomicInteger time;
    static ArrayList<Character> Winners;
    static boolean showMessages;
    static int interval;
    static int messageInterval;
    
    static Menu menu;
    static Menu finalmenu;
    static Menu messageIntervalmenu;
    
    static {
        Winners = new ArrayList<Character>();
        Winners.add(new Character("Progenitor"));
        time = new AtomicInteger(0);
        interval = 5;
        messageInterval = 10;
        
        menu = new Menu();
        menu.add("Continue");
        menu.add("Change menu interval");
        menu.add("View alive contestants");
        menu.add("View sleeping contestants");
        menu.add("View map");
        menu.add("View contestant inventories");
        menu.add("View decision probabilities");
        menu.add("Change message interval");
        menu.add("View contestant statuses");
        
        finalmenu = new Menu();
        finalmenu.add("Exit");
        finalmenu.add("Run another iteration");
        
        messageIntervalmenu = new Menu();
        messageIntervalmenu.add("Slow (20 ms)");
        messageIntervalmenu.add("Medium (10 ms) (Recommended)");
        messageIntervalmenu.add("Fast (5 ms)");
        messageIntervalmenu.add("Instant (0 ms) (Developer)");
    }
    
    private static void initializeCharacters(){
        
        String[] Names = new String[]{
            "Jake",
            "Paul",
            "Logan",
            "Michael",
            "Ruby",
            "Alex",
            "Sam",
            "Max",
            "Lucy",
            "John",
            "Angel",
            "Chad",
            "Carla",
            "Leandra",
            "KING"
        };
        
        Contestants = new Character[Names.length];
        int children = 0;
        while (children < 20) {
            for (Character progenitor:Winners){     
                for (int i = 0; i<Names.length;i++) {
                    Contestants[i] = progenitor.Clone(Names[i]);
                    Contestants[i].spawn();
                    children++;
                }   
            }
        }
        
        if (Winners.get(0).Name=="progenitor") {
            Winners.remove(0);
        }
    }
    
    private static void Turn() {
        for (Character character : Contestants) {
            if (character.Status.get("Dead") == false && character.Status.get("Sleeping") == false){
                character.randomDecide();
            }
        }
    }
    
    private static void startIntro(){
        //new Dialogue("Welcome to Greed Island.").display(100);
        
        Dialogue story = new Dialogue(new Dialogue[]{
            new Dialogue("An evil organization, "),
            new Dialogue("'The "),
            new Dialogue("System' "),
            new Dialogue("has kidnapped 20 individuals from various places for"),
            new Dialogue("\nproject codename: "),
            new Dialogue("GREED"),
            new Dialogue("_"),
            new Dialogue("ISLAND"),
            new Dialogue(".\n"),
            new Dialogue("They are airdropped blindfolded into a remote island off-radar.\n"),
            new Dialogue("After 5 Rounds of 'Observation' and 'Testing' which span five days,\n"),
            new Dialogue("the individuals can return home. "),
            new Dialogue("That is, "),
            new Dialogue("if they are still "),
            new Dialogue("A"),
            new Dialogue("L"),
            new Dialogue("I"),
            new Dialogue("V"),
            new Dialogue("E"),
            new Dialogue(".\n"),
          
        });
        //story.display(50,300);
        
    }
    
    static String timeToString(){
        int hours = (getTime() - getTime()%60)/60;
        int minutes = time.get()%60;
        return (hours>12? hours-12 : hours)+":"+(minutes<10? "0":"")+minutes+(hours>12? " PM" : " AM");
    }
    
    static int getTime() {
        return time.get()%(60*24);
    }
    
    static int getCount() {
        
        return (int) Arrays.stream(Contestants).filter(x->x.Status.get("Dead")==false).count();
        //return getContestants().length;
    }
    
    private static void menuAction() {
        Scanner sc = new Scanner(System.in);
        
        Dialogue.send("------------------------\n");
        menu.getInput();
        Dialogue.send("------------------------\n");
        
        while (menu.choice.equals("1")==false) {
            switch (menu.choice) {
                case "1":
                    break;
                case "2":
                    Dialogue.send("Current interval: every "+interval+" minutes.\nNew interval (every __ mins): ");
                    interval = sc.nextInt();
                    break;
                case "3":
                    Arrays.stream(Contestants).filter(x->x.Status.get("Dead")==false).forEach(x->{
                        //Dialogue.send(x.Name+" ("+x.Health+" hp) ("+x.HungerLevel+" hunger)");
                        Dialogue.send("\n");
                        x.display("Name");
                    });
                    Dialogue.send("\n("+Arrays.stream(Contestants).filter(x->x.Status.get("Dead")==false).count()+" out of "+Contestants.length+")\n");
                    break;
                case "4":
                    Arrays.stream(Contestants).filter(x->x.Status.get("Sleeping")==true).forEach(x->{
                        Dialogue.send(x.Name+" ("+x.EnergyLevel+" energy) is Sleeping.");
                    });
                    Dialogue.send("\n("+Arrays.stream(Contestants).filter(x->x.Status.get("Sleeping")==true).count()+" out of "+Contestants.length+")\n");
                    break;
                case "5":
                    for (String location: Location.getLocations()) {
                        Dialogue.send(location+": ");
                        
                        for (Character character: Location.getContestantsAt(location)){
                            Dialogue.send(character.Name+"  ");
                        }
                        Dialogue.send("\n");
                    }
                    break;
                case "6":
                    for (Character character: Contestants) {
                        Dialogue.send(character.Name+"| ");
                        character.display("Inventory");
                        Dialogue.send("\n");
                    }
                    break;
                case "7":
                    //Character ch =Arrays.stream(Contestants).filter(x->x.Status.get("Dead")==false).collect(Collectors.toCollection(ArrayList::new)).get(0);
                    //ch.decisionProbabilityModel.displayOutcomes();
                   
                    for (Character ch : Arrays.stream(Contestants).filter(x->x.Status.get("Dead")==false).collect(Collectors.toCollection(ArrayList::new))) {
                        Dialogue.send(ch.Name + "| ");
                        for (String trait : ch.Traits.keySet()){
                            int rf = ch.decisionProbabilityModel.getRf(trait);
                            int inf = ch.Influences.get(trait);
                            int tra = ch.Traits.get(trait);
                            Dialogue.send(trait+"("+rf+" - "+inf+" - "+tra+" = "+(rf-inf-tra)+") ");
                        }
                        Dialogue.send("\n");
                    }
                   
                    break;
                case "8":
                    Dialogue.send("Current interval: "+messageInterval+" ms\n");
                    messageIntervalmenu.getInput();
                    Dialogue.send("------------------------\n");
                    
                    switch (messageIntervalmenu.choice) {
                        case "1":
                            messageInterval = 20;
                            break;
                        case "3":
                            messageInterval = 5;
                            break;
                        case "4":
                            messageInterval = 0;
                            break;
                        default:
                            messageInterval = 10;
                            break;
                    } 
                    Dialogue.send("New interval: "+messageInterval+" ms\n");
                    
                    break;
                case "9":
                    for (Character ch : Arrays.stream(Contestants).filter(x->x.Status.get("Dead")==false).collect(Collectors.toCollection(ArrayList::new))) {
                        Dialogue.send(ch.Name + "| ");
                        ch.display("Status");
                        Dialogue.send("\n");
                    }
                    break;
                default:
                    Dialogue.send("Invalid choice.\n");
                    break;
            }
            
            if (menu.choice != "1") {
                Dialogue.send("------------------------\n");
                menu.getInput();
                Dialogue.send("------------------------\n");
            }
        }
        Dialogue.send("--It is now "+timeToString()+"--\n");
    }
    
    private static void runIteration(boolean show){
        showMessages = show;
        
        if (show) {
            startIntro();
        }
        
        initializeCharacters();
        Location.initialize();
        
        time.set(0);

        for (int Round = 1; Round<=5; Round++) {
            if (show){
                new Dialogue("Day "+Round+" (Round "+Round+")\n").display(10*messageInterval);
            }
            
            
            for (int t = 0; t < (60*24);t++){
                //Dialogue.send(timeToString());
                time.set(time.get()+1);
                if (show && t%interval == 0) {
                    menuAction();
                }
                
                Turn();
                
                try {
                    Thread.sleep(0);
                } catch(Exception e) {
                
                }
            }
        }
        
        Location.destroy();
        
        Dialogue.send("-----------------------------------\n");
        Dialogue.send("THE WINNERS ARE:\n");
        if (getCount() > 0) {
            Winners = new ArrayList<Character>();
            for (Character character : Contestants) {
                if (character.Status.get("Dead") == false) {
                    Winners.add(character);
                    //Dialogue.send(character.Name+", "+character.Health+" hp");
                    character.display();
                    character.destroy();
                }
            }
        } else {
            Dialogue.send("no winners found. all contestants dead.\n");
        }
   
    }
    
    static void main(){
        String finalchoice = "2";
        int iterationNumber = 1;
        while (finalchoice == "2") {
            if (iterationNumber > 1) {
                Dialogue.send("Iteration "+iterationNumber);
            }
            try {
                runIteration(true);
            } catch (Exception e) {
                e.printStackTrace();
                menuAction();
            }
            finalmenu.getInput();
            finalchoice = finalmenu.choice;
        }
    }
}*/