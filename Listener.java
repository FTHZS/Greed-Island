
/*class Listener extends Thread{
    Character character;
    int hours;
    
    Listener(Character character,int hours) {
        this.character = character;
        this.hours = hours;
    }
    
    @Override
    public void run() {
        int current = Greed_Island.time;
        while (Greed_Island.time < current + hours*60) {
            try{
                Thread.sleep(1);
                System.out.println("Waiting");
            }catch(Exception e){
                System.out.println(e);
            }
        }
        character.setStatus("Sleeping",false);
        System.out.println("WOKE UP "+character.Name);
    }
}*/

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

/*abstract class RListener<T> implements Runnable {
    Comparison<T> comparison;
    T object;
    int interval;
    int repeat;
    
    RListener(T object, Comparison<T> comparison, int interval,int repeat) {
        this.object = object;
        this.comparison = comparison;
        this.interval = interval;
        this.repeat = repeat;
    }
    
    abstract void onCondition();
    
    @Override
    public void run(){
        for (int i=0; i<repeat; i++) {
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