import java.util.ArrayList;
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
    
    static Menu menu;
    static Menu finalmenu;
    
    static {
        Winners = new ArrayList<Character>();
        Winners.add(new Character("Progenitor"));
        time = new AtomicInteger(0);
        interval = 5;
        
        menu = new Menu();
        menu.add("Continue");
        menu.add("Change menu interval");
        menu.add("View alive contestants");
        menu.add("View sleeping contestants");
        menu.add("View map");
        menu.add("View contestant inventories");
        menu.add("View decision probabilities");
        
        finalmenu = new Menu();
        finalmenu.add("Exit");
        finalmenu.add("Run another iteration");
    }
    
    static void initializeCharacters(){
        
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
    
    static void Turn() {
        for (Character character : Contestants) {
            if (character.Status.get("Dead") == false && character.Status.get("Sleeping") == false){
                character.randomDecide();
            }
        }
    }
    
    static void startIntro(){
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
    
    static void menuAction() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("------------------------");
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
                    Arrays.stream(Contestants).filter(x->x.Status.get("Sleeping")==true).forEach(x->{
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
                    for (Character character: Contestants) {
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
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
            
            if (menu.choice != "1") {
                System.out.println("------------------------");
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

        for (int Round = 1; Round<=5; Round++) {
            if (show){
                new Dialogue("Day "+Round+" (Round "+Round+")\n").display(100);
            }
            
            
            for (int t = 0; t < (60*24);t++){
                //System.out.println(timeToString());
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
        
        if (getCount() > 0) {
            System.out.println("-----------------------------------");
            System.out.println("THE WINNERS ARE:\n");
            
            Winners = new ArrayList<Character>();
            for (Character character : Contestants) {
                if (character.Status.get("Dead") == false) {
                    Winners.add(character);
                    //System.out.println(character.Name+", "+character.Health+" hp");
                    character.display();
                }
            }
        }
   
    }
    
    static void runIterations(int n) {
        for (int i = 0; i<n; i++){
            runIteration(false);
        }
    }
    
    static void main(){
        String finalchoice = "2";
        while (finalchoice == "2") {
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