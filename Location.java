import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

class Location {
    static HashMap<String,Integer> energyCosts;
    static String[] Locations;
    
    static HashMap<String,ArrayList<String>> resourceModel;
    private static ArrayList<Listener> threadpool;
    
    static {
        energyCosts = new HashMap<String,Integer>();
        energyCosts.put("Bay-Volcano",200);
        energyCosts.put("Bay-South Coast",200);
        energyCosts.put("Forest-Volcano",200);
        energyCosts.put("Forest-East Coast",200);
        energyCosts.put("Forest-South Coast",200);
        energyCosts.put("East Coast-Volcano",200);
        energyCosts.put("East Coast-South Coast",200);
        energyCosts.put("East Coast-Forest",200);
        energyCosts.put("South Coast-Bay",200);
        energyCosts.put("South Coast-East Coast",200);
        energyCosts.put("South Coast-Forest",200);
        energyCosts.put("Volcano-Bay",200);
        energyCosts.put("Volcano-East Coast",200);
        energyCosts.put("Volcano-Forest",200);
        
        Locations = new String[]{
            "Forest",
            "East Coast",
            "Bay",
            "Volcano",
            "South Coast"
        };
        
        threadpool = new ArrayList<Listener>();
    }
    
    static void initialize() {
        resourceModel = new HashMap<String,ArrayList<String>>();
        
        for (String location: Locations) {
            /*ArrayList<String> defaultList = new ArrayList<String>();
            for (int i = 0;i<30;i++) {defaultList.add("Berries");}
            for (int i = 0;i<30;i++) {defaultList.add("Wood");}
            for (int i = 0;i<30;i++) {defaultList.add("Stone");}
            for (int i = 0;i<30;i++) {defaultList.add("Vines");}
            for (int i = 0;i<30;i++) {defaultList.add("Apple");}
            for (int i = 0;i<30;i++) {defaultList.add("Poisonous_Berries");}
            */
            resourceModel.put(location,new ArrayList<String>());
            
           
            add(location,"Berries",30);
            add(location,"Wood",10);
            add(location,"Stone",10);
            add(location,"Vines",10);
            add(location,"Apple",30);
            add(location,"Poisonous_Berries",30);
            
            //AtomicInteger recordedSize = new AtomicInteger(0);
            TickListener<Character[]> t = new TickListener<Character[]>(Greed_Island.Contestants,x->Arrays.stream(x).filter(y->y.currentLocation == location).count()>0,5+RarityPool.randInt(10)){
                @Override
                public void onCondition() {
                    if (getResources(location).size() <= 0) {
                        return;
                    }
                    
                    ArrayList<Character> contestants = Arrays.stream(Greed_Island.Contestants).filter(x->x.currentLocation == location).collect(Collectors.toCollection(ArrayList::new));
                    if (contestants.size() <= 0) {
                        return;
                        //make the get contestants function thread safe.
                    }
                    int random = RarityPool.randInt(contestants.size());
                    Character chosen = contestants.get(random);
                    while (running && chosen.Status.get("Dead") == true) {
                        contestants = Arrays.stream(Greed_Island.Contestants).filter(x->x.currentLocation == location).collect(Collectors.toCollection(ArrayList::new));
                        if (contestants.size() <= 0) {
                            return;
                        }
                        random = RarityPool.randInt(contestants.size());
                        chosen = contestants.get(random);
                    }
                    
                    /*int currentTime = Greed_Island.time.get();
                    int randomTime = 5+RarityPool.randInt(10);
                    
                    while (!(Greed_Island.time.get() >= currentTime+randomTime)) {
                        try {
                            Thread.sleep(0);
                        } catch (Exception e) {}
                    }*/
                    
                    giveResource(location, chosen);
                    
                }
            };
            threadpool.add(t);
            t.setName(location+"#"+Greed_Island.IterationNumber+" resource tick");
            t.start();
        }
    }
    
    static void destroy() {
        for (Listener l :threadpool) {
            l.stop();
        }
    }
    
    static void add(String location, String resource, int number) {
        for (int i = 0;i<number;i++) {
            
            if (resourceModel.get(location) == null) {
                resourceModel.put(location,new ArrayList<String>());
            }
            
            resourceModel.get(location).add(resource);
        }
    }
    
    static ArrayList<Character> getContestantsAt(String location) {
        ArrayList<Character> contestants = new ArrayList<Character>();
        for (Character character : Greed_Island.Contestants) {
            if (character.Status.get("Dead")==false&&character.currentLocation == location) {
                contestants.add(character);
            }
        }
        
        return contestants;
        //return Arrays.stream(Greed_Island.Contestants).filter(x-> x.currentLocation == location).collect(Collectors.toCollection(ArrayList::new));
    }
    
    static String[] getTravelOptions(String currentLocation) {
        switch (currentLocation) {
            case "Bay":
                return new String[]{"Volcano","South Coast"};
                
            case "Forest":
                return new String[]{"Volcano","South Coast","East Coast"};
                
            case "East Coast":
                return new String[]{"Volcano","South Coast","Forest"};
                
            case "South Coast":
                return new String[]{"East Coast","Bay","Forest"};
                
            case "Volcano":
                return new String[]{"Bay","East Coast","Forest"};
            default:
                return new String[0];
        }
    }
    
    static int getEnergyCost(String from, String to){
        return energyCosts.get(from+"-"+to);
    }
    
    static ArrayList<String> getResources(String location) {
        return resourceModel.get(location);
    }
    
    static void giveResource(String location, Character character) {
        ArrayList<String> resources = resourceModel.get(location);
        int random = RarityPool.randInt(resources.size());
        String item = resources.get(random);
        
        //character.inventory.set(item,character.inventory.get(item).units + 1);
        character.collect(item);
        //add function to remove item from that place's resourceModel
        int i;
        boolean removed = false;
        for (i = 0; i<resourceModel.get(location).size();i++) {
            if (removed == false && resourceModel.get(location).get(i) == item) {
                resourceModel.remove(i);
                removed = true; //for ensuring that only one copy is removed.
            }
        }
        
    }
    
    static String[] getLocations() {
        return new String[]{"Volcano","Forest","Bay","East Coast","South Coast"};
    }
}