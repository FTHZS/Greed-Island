import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

class Greed_Island{
    static volatile Character[] Contestants;
    static AtomicInteger time;
    static ArrayList<Character> Winners;
    
    static boolean showMessages;
    static int interval;
    static int messageInterval;
    static LinkedHashMap<String,Boolean> messageSettings;
    
    static Menu menu;
    static Menu finalmenu;
    static Menu messageIntervalmenu;
    static Menu messageSettingsmenu;
    
    static {
        Winners = new ArrayList<Character>();
        Winners.add(new Character("Progenitor"));
        time = new AtomicInteger(0);
        interval = 5;
        messageInterval = 10;
        
        messageSettings = new LinkedHashMap<String,Boolean>();
        messageSettings.put("resource collect",true);
        messageSettings.put("wake up",true);
        
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
        menu.add("Change message settings");
        
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
        //String[] Names = new String[]{"Character#1","Character#2"};
        
        Contestants = new Character[Names.length];
        int children = 0;
        while (children < Names.length) {
            for (Character progenitor:Winners){     
                //for (int i = 0; i<Names.length;i++) {
                Contestants[children] = progenitor.Clone(Names[children]);
                Contestants[children].spawn();
                children++;
                //}   
            }
        }
        
        if (Winners.get(0).Name=="Progenitor") {
            Winners.remove(0);
        }
    }
    
    private static Boolean Turn() {
        if (getCount() <=5) {
            return false; //collect the last 5 survivors;
        }
        
        for (Character character : Contestants) {
            if (character.Status.get("Dead") == false && character.Status.get("Sleeping") == false){
                character.randomDecide();
            }
        }
        return true;
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
            /*new Dialogue("5 "),
            new Dialogue("Days. "),
            new Dialogue("5 "),
            new Dialogue("Rounds. "),
            new Dialogue("Who will survive "),
            new Dialogue("?"),
            new Dialogue("?"),
            new Dialogue("?\n"),
            */
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
        /*int count = 0;
        
        for(Character character: Contestants) {
            if (character.Status.get("Dead") == false){
                count +=1;
            }
        }
        return count;*/
        return (int) Arrays.stream(Contestants).filter(x->x.Status.get("Dead")==false).count();
        //return getContestants().length;
    }
    
    /*static Character[] getContestants() {
        return Arrays.stream(Contestants).filter(x->x.Status.get("Dead")==false).toArray(Character[]::new);
    }*/
    
    private static void menuAction() {
        Scanner sc = new Scanner(System.in);
        
        new Dialogue("------------------------\n").display(0);
        menu.getInput();
        System.out.println("------------------------");
        
        while (menu.choice.equals("1")==false) {
            switch (menu.choice) {
                case "1":
                    break;
                case "2":
                    System.out.print("Current interval: every "+interval+" minutes.\nNew interval (every __ mins): ");
                    interval = sc.nextInt();
                    break;
                case "3":
                    Arrays.stream(Contestants).filter(x->x.Status.get("Dead")==false).forEach(x->{
                        //System.out.println(x.Name+" ("+x.Health+" hp) ("+x.HungerLevel+" hunger)");
                        System.out.print("\n");
                        x.display("Name");
                    });
                    System.out.println("\n("+Arrays.stream(Contestants).filter(x->x.Status.get("Dead")==false).count()+" out of "+Contestants.length+")");
                    break;
                case "4":
                    Arrays.stream(Contestants).filter(x->x.Status.get("Dead")==false&&x.Status.get("Sleeping")==true).forEach(x->{
                        System.out.println(x.Name+" ("+x.EnergyLevel+" energy) is Sleeping.");
                    });
                    System.out.println("\n("+Arrays.stream(Contestants).filter(x->x.Status.get("Sleeping")==true).count()+" out of "+Contestants.length+")");
                    break;
                case "5":
                    for (String location: Location.getLocations()) {
                        System.out.print(location+": ");
                        
                        for (Character character: Location.getContestantsAt(location)){
                            System.out.print(character.Name+"  ");
                        }
                        System.out.println("");
                    }
                    break;
                case "6":
                    for (Character character : Arrays.stream(Contestants).filter(x->x.Status.get("Dead")==false).collect(Collectors.toCollection(ArrayList::new))) {
                        System.out.print(character.Name+"| ");
                        character.display("Inventory");
                        System.out.println("");
                    }
                    break;
                case "7":
                    //Character ch =Arrays.stream(Contestants).filter(x->x.Status.get("Dead")==false).collect(Collectors.toCollection(ArrayList::new)).get(0);
                    //ch.decisionProbabilityModel.displayOutcomes();
                    /*int rf = ch.decisionProbabilityModel.getRf("Sleep");
                    int inf = ch.Influences.get("Sleep");
                    int tra = ch.Traits.get("Sleep");
                    System.out.println("rf: "+rf+" inf: "+inf+" tra: "+tra+" total: "+(rf-inf-tra));
                    */
                    for (Character ch : Arrays.stream(Contestants).filter(x->x.Status.get("Dead")==false).collect(Collectors.toCollection(ArrayList::new))) {
                        System.out.print(ch.Name + "| ");
                        for (String trait : ch.Traits.keySet()){
                            int rf = ch.decisionProbabilityModel.getRf(trait);
                            int inf = ch.Influences.get(trait);
                            int tra = ch.Traits.get(trait);
                            System.out.print(trait+"("+rf+" - "+inf+" - "+tra+" = "+(rf-inf-tra)+") ");
                        }
                        System.out.print("\n");
                    }
                   
                    break;
                case "8":
                    System.out.print("Current interval: "+messageInterval+" ms\n");
                    messageIntervalmenu.getInput();
                    System.out.println("------------------------");
                    
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
                    System.out.print("New interval: "+messageInterval+" ms\n");
                    
                    break;
                case "9":
                    for (Character ch : Arrays.stream(Contestants).filter(x->x.Status.get("Dead")==false).collect(Collectors.toCollection(ArrayList::new))) {
                        System.out.print(ch.Name + "| ");
                        ch.display("Status");
                        System.out.print("\n");
                    }
                    break;
                case "10":
                    messageSettingsmenu = new Menu();
                    for (Map.Entry<String,Boolean>entry : messageSettings.entrySet()) {
                        messageSettingsmenu.add(entry.getKey()+" message | "+entry.getValue()+" -> "+!entry.getValue());
                    }
        
                    messageSettingsmenu.getInput();
                    System.out.println("------------------------");
                    
                    int index = 0;
                    String targetKey = null;
                    for (String key: messageSettings.keySet()) {
                        if (Integer.toString(index+1).equals(messageSettingsmenu.choice)) {
                            targetKey = key;
                            break;
                        }
                        index++;
                    }
                    if (targetKey != null) {
                        messageSettings.put(targetKey, !messageSettings.get(targetKey));
                        System.out.print(targetKey+" messages will "+(messageSettings.get(targetKey) == true? "start showing.\n": "not show anymore.\n"));
                    } else {
                        System.out.print("Nothing changed.\n");
                    }
                    
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
            
            if (menu.choice != "1") {
                new Dialogue("------------------------\n").display(0);
                menu.getInput();
                System.out.println("------------------------");
            }
        }
        System.out.println("--It is now "+timeToString()+"--");
    }
    
    private static void runIteration(boolean show){
        showMessages = show;
        
        if (show) {
            startIntro();
        }
        
        initializeCharacters();
        Location.initialize();
        
        time.set(0);

        boolean running = true;
        outer:
        for (int Round = 1; Round<=5; Round++) {
            if (show){
                new Dialogue("Day "+Round+" (Round "+Round+")\n").display(10*messageInterval);
            }
            
            
            for (int t = 0; t < (60*24);t++){
                //System.out.println(timeToString());
                time.set(time.get()+1);
                
                if (running == false) {
                    continue;
                };
                
                if (show && t%interval == 0) {
                    menuAction();
                }
                
                if (Turn() == false) {
                    running = false;
                    messageSettings.replace("resource collect",false);
                    messageSettings.replace("wake up",false);
                }
                
                try {
                    Thread.sleep(0);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        //Location.destroy();
        
        System.out.println("-----------------------------------");
        System.out.println("THE WINNERS ARE:\n");
        if (getCount() > 0) {
            Winners = new ArrayList<Character>();
            for (Character character : Contestants) {
                if (character.Status.get("Dead") == false) {
                    Winners.add(character);
                    //System.out.println(character.Name+", "+character.Health+" hp");
                    character.display();
                    character.destroy();
                }
            }
        } else {
            System.out.println("no winners found. all contestants dead.");
        }
   
    }
    
    /*static void runIterations(int n) {
        for (int i = 0; i<n; i++){
            runIteration(false);
        }
    }*/
    
    static void main(){
        String finalchoice = "2";
        int iterationNumber = 1;
        while (finalchoice != "1") {
            if (iterationNumber > 1) {
                System.out.println("Iteration "+iterationNumber);
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
}