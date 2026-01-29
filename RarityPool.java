import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.lang.Math;

class RarityPool {
    HashMap<String,Double> Outcomes;
    HashMap<String,Integer> Pool;
    int poolSize;
    int LCM;
    boolean isIdeal;
    
    RarityPool(){
        Outcomes = new HashMap<String,Double>();
        poolSize = 0;
        isIdeal = false;
    }
    
    RarityPool(HashMap<String,Integer> outcomes){
        HashMap<String,Double> Outcomes = new HashMap<String,Double>();
        for (Map.Entry<String,Integer> entry : outcomes.entrySet()) {
            Outcomes.put(entry.getKey(),(double)entry.getValue());
        }
        
        this.Outcomes = Outcomes;
        poolSize = 0;
        isIdeal = false;
    }
    
    /*RarityPool(int start, int end){
        HashMap<String,Double> Outcomes = new HashMap<String,Double>();
        for (int i = start; i<end;i++) {
            Outcomes.put(new Integer(i).toString(),(double)i);
        }
        poolSize = 0;
        isIdeal = false;
    }*/
    
    void add(String rarity, int rFactor){
        if (rFactor <= 1) {
            throw new IllegalArgumentException(rFactor==1?"cannot add (one in 1)":"attempted to add negative");
        }
        
        Outcomes.put(rarity,(double)(rFactor));
        updateLCM();
        updatePool();
    }
    
    void addDouble(String rarity, double rFactor){
        Outcomes.put(rarity,rFactor);
        //updateLCM();
        updatePool();
    }
    
    void remove(String rarity){
        if (Outcomes.get(rarity) == null) {
            System.out.println("rarity not found in model!");
        } else {
            Outcomes.remove(rarity);
            updateLCM();
            updatePool();
        }
    }
    
    void change(String rarity,int rFactor){
        Outcomes.replace(rarity,(double)(rFactor));
        updateLCM();
        updatePool();
    }
    
    static int randInt(int range){
        return new Random().nextInt(range);
    }
    
    private void updateLCM() {
        int[] values = new int[Outcomes.size()];
        int i = 0;
        for (Map.Entry<String,Double> entry: Outcomes.entrySet()){
            values[i++] = (entry.getValue()).intValue();
        }
        LCM = lcmArray(values);
    }
    
    private void updatePool() {      
        poolSize = 0;
        Pool = new HashMap<String,Integer>();
        for (Map.Entry<String,Double> entry: Outcomes.entrySet()){
            int rFactor = (int)( (double)(LCM) / entry.getValue());
            
            Pool.put(entry.getKey(),rFactor);
            poolSize += rFactor;
        }
        
        isIdeal = (poolSize==LCM) ? true : false;
    }
    
    String simulate() {
        int random = new Random().nextInt(poolSize);
        int cumulative = 0;
        
        //System.out.println("random: "+random);
        
        String key = "";
        for (Map.Entry<String,Integer> entry: Pool.entrySet()) {
            //System.out.println(entry.getKey()+entry.getValue());
            
            if (cumulative > random){
                continue;
            }
            
            cumulative += entry.getValue();
            key = entry.getKey();
            //System.out.println(key+entry.getValue());
        }
        
        //System.out.println("cumulative: "+cumulative);
         //System.out.println("item: "+key);
        
        return key;
    }
    
    void idealize(String filler) {
        if (isIdeal==false && poolSize<LCM){
            //System.out.println("LCM: "+LCM+" Poolsize: "+poolSize);
            //System.out.println(( (double)(LCM) / (double)(LCM-poolSize) ));
            addDouble(filler,( (double)(LCM) / (double)(LCM-poolSize) ));
            //System.out.println("LCM: "+LCM+" Poolsize: "+poolSize);
        }
    }
    
    void displayOutcomes() {
        System.out.println("Outcomes: ");
        for (Map.Entry<String,Double> entry: Outcomes.entrySet()){
            System.out.println(entry.getKey()+": one in "+entry.getValue()+" ("+((double)(100) / (double)(entry.getValue()) )+"%)");
        }
    }
    void displayPool() {
        System.out.println("Pool: ");
        for (Map.Entry<String,Integer> entry: Pool.entrySet()){
            System.out.println(entry.getKey()+": "+entry.getValue()+" units");
        }
    }
    
    int getR(String rarity) {
        if (Pool.get(rarity) == null) {
            return (int)(0);
        } else {
            return (int)(Pool.get(rarity));
        }
    }
    
    int getRf(String rarity) {
        if (Outcomes.get(rarity) == null) {
            return (int)(0);
        } else {
            return (Outcomes.get(rarity)).intValue();
        }
    }
    
    double actualRarity(String rarity) {
        return (double)(100*getR(rarity))/(double)(poolSize);
    }
    
    private int gcd (int a, int b) {
        if (b==0) {
            return a;
        }
        return gcd(b, a%b);
    }
    
    private int lcm(int a, int b) {
        if (a==0 || b==0) return 0;
        
        return Math.abs(a*b) / gcd(a,b);
    }
    
    private int lcmArray(int[] arr){
        int resultLcm = arr[0];
        for (int i:arr) {
            resultLcm = lcm(resultLcm, i);
        }
        return resultLcm;
    }
    
    public static void main() {
        RarityPool model = new RarityPool();
        model.add("Blue",1000);
        model.add("Red",30);
        model.add("Green",20);
        
        System.out.println("blue: one in "+model.getRf("Blue")+" ("+((double)(100) / (double)(model.getRf("Blue")) )+"%)");
        System.out.println("red: one in "+model.getRf("Red")+" ("+((double)(100) / (double)(model.getRf("Red")) )+"%)");
        System.out.println("green: one in "+model.getRf("Green")+" ("+((double)(100) / (double)(model.getRf("Green")) )+"%)");
        System.out.println("-----");
        
        System.out.println("poolR blue: "+model.getR("Blue"));
        System.out.println("poolR red: "+model.getR("Red"));
        System.out.println("poolR green: "+model.getR("Green"));
        System.out.println("-----");
             
        System.out.println("poolsize: "+model.poolSize);
        System.out.println("Ideal pool?: "+model.isIdeal);
        System.out.println("-----");
        
        System.out.println("actual blue: "+model.actualRarity("Blue")+"%");
        System.out.println("actual red: "+model.actualRarity("Red")+"%");
        System.out.println("actual green: "+model.actualRarity("Green")+"%");
        System.out.println("-----");
        
        System.out.println("random color: "+model.simulate());
        
        System.out.println("\n------------------------------------\n");
        model.idealize("colourless");
        model.displayOutcomes();
        model.displayPool();
        System.out.println("-----");
             
        System.out.println("poolsize: "+model.poolSize);
        System.out.println("Ideal pool?: "+model.isIdeal);
        System.out.println("-----");
        
        System.out.println("actual blue: "+model.actualRarity("Blue")+"%");
        System.out.println("actual red: "+model.actualRarity("Red")+"%");
        System.out.println("actual green: "+model.actualRarity("Green")+"%");
        System.out.println("actual null: "+model.actualRarity("null")+"%");
        System.out.println("-----");
        
        System.out.println("random color: "+model.simulate());
    }
}