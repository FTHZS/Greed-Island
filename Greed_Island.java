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
    
    static {
        Winners = new ArrayList<Character>();
        Winners.add(new Character(""));
        time = new AtomicInteger(0);
        interval = 5;
        
        menu = new Menu();
        menu.add("Continue");
        menu.add("Change menu interval");
        menu.add("View alive contestants");
        menu.add("View sleeping contestants");
        menu.add("View map");
        menu.add("View contestant inventories");
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
    }
    
    static void Turn() {
        for (Character character : Contestants) {
            if (character.Status.get("Dead") == false && character.Status.get("Sleeping") == false)
                character.randomDecide();
        }
    }
    
    static void startIntro(){
        //new Dialogue("Welcome to Greed Island.").display(100);
        
        Dialogue story = new Dialogue(new Dialogue[]{
            new Dialogue("5 Days. "),
            new Dialogue("5 Rounds. "),
            new Dialogue("Who will survive "),
            new Dialogue("?"),
            new Dialogue("?"),
        });
        //story.display(50,300);
        
    }
    
    static String timeToString(){
        int hours = (time.get() - time.get()%60)/60;
        int minutes = time.get()%60;
        return (hours>12? hours-12 : hours)+":"+(minutes<10? "0":"")+minutes+(hours>12? " PM" : " AM");
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
    }
    
    private static void runIteration(boolean show){
        Scanner sc = new Scanner(System.in);
        
        showMessages = show;
        
        startIntro();
        initializeCharacters();
        Location.initialize();

        for (int Round = 1; Round<=5; Round++) {
            if (show){
                new Dialogue("Day "+Round+" (Round "+Round+")\n").display(100);
            }
            
            time.set(0);
            for (int t = 0; t < (60*12);t++){
                //System.out.println(timeToString());
                time.set(time.get()+1);
                if (show && t%interval == 0) {
                    System.out.println("------------------------");
                    menu.getInput();
                    System.out.println("------------------------");
                    
                    switch (menu.choice) {
                        case "2":
                            System.out.print("Current interval: every "+interval+" minutes.\nNew interval (every __ mins): ");
                            interval = sc.nextInt();
                            break;
                        case "3":
                            Arrays.stream(Contestants).filter(x->x.Status.get("Dead")==false).forEach(x->{
                                System.out.println(x.Name+" ("+x.Health+" hp) ("+x.HungerLevel+" hunger)");
                            });
                            System.out.println("\n("+Arrays.stream(Contestants).filter(x->x.Status.get("Dead")==false).count()+" out of 20)");
                            break;
                        case "4":
                            Arrays.stream(Contestants).filter(x->x.Status.get("Sleeping")==true).forEach(x->{
                                System.out.println(x.Name+" ("+x.EnergyLevel+" energy)");
                            });
                            System.out.println("\n("+Arrays.stream(Contestants).filter(x->x.Status.get("Sleeping")==true).count()+" out of 20)");
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
                    }
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
        runIteration(true);
    }
}