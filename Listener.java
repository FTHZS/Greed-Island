
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

abstract class Listener<T> implements Runnable {
    protected final Comparison<T> comparison;
    protected final T object;
    
    protected volatile boolean running = true;
    protected Thread thread;
    protected String name;
    
    Listener(T object, Comparison<T> comparison) {
        this.comparison = comparison;
        this.object = object;
        //name = "unknown thread";
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    abstract void onCondition();
    
    public void start() {
        thread = new Thread(this,name);
        thread.start();
    }

    public void stop() {
        running = false;        
        if (thread != null) {
            thread.interrupt(); 
            try {
                if (Thread.currentThread() != thread) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    @Override
    public void run() {
        try {
            while (running && !comparison.compare(object)) {
                Thread.sleep(0);
            }
            if (running) onCondition();
        } catch (InterruptedException e) {
            //e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }   
}



abstract class TickListener<T> extends Listener<T> {
    private final int interval;

    TickListener(T object, Comparison<T> comparison, int interval) {
        super(object, comparison);
        this.interval = interval;
    }

    abstract void onCondition();

    @Override
    public void run() {
        try {
            while (running) {
                while (running && !comparison.compare(object)) {
                    Thread.sleep(0);
                }
                if (!running) break;
    
                int lastTime = Greed_Island.time.get();
                /*if (lastTime == 7200) {
                    lastTime = 0;    
                }*/
                
                while (running && comparison.compare(object)) {
                    int now = Greed_Island.time.get();
                    
                    int elapsed = now - lastTime;
                    if (elapsed >= interval) {
                        int steps = elapsed / interval; 
                        for (int i = 0; i < steps; i++) {
                            onCondition();
                        }
                        lastTime += steps * interval;
                    }
    
                    Thread.sleep(0);
                }
            }
        } catch (InterruptedException e) {
            //e.printStackTrace();
            running = false;
            Thread.currentThread().interrupt();
        }
    }
}

abstract class StateListener<T> extends Listener<T> {
    private final int interval;

    StateListener(T object, Comparison<T> comparison, int interval) {
        super(object, comparison);
        this.interval = interval;
    }

    abstract void onCondition();

    @Override
    public void run() {
        try {
            while (running) {
                while (running && !comparison.compare(object)) {
                    Thread.sleep(0);
                }
                if (!running) break;
    
                int lastTime = Greed_Island.time.get();
                /*if (lastTime == 7200) {
                    lastTime = 0;    
                }*/
                
                while (running && comparison.compare(object)) {
                    int now = Greed_Island.time.get();
                    
                    int elapsed = now - lastTime;
                    if (elapsed >= interval) {
                        onCondition();
                        lastTime = now;
                    }
    
                    Thread.sleep(0);
                }
            }
        } catch (InterruptedException e) {
            //e.printStackTrace();
            running = false;
            Thread.currentThread().interrupt();
        }
    }
}

/*abstract class BiFListener<T> extends Listener<T> {
    BiFListener(T object, Comparison<T> comparison) {
        super(object, comparison);
    }

    abstract void onConditionTrue();
    abstract void onConditionFalse();

    @Override
    public void run() {
        try {
            while (running) {
                while (running && !comparison.compare(object)) {
                    Thread.sleep(0);
                }
                if (running) onConditionTrue();

                while (running && comparison.compare(object)) {
                    Thread.sleep(0);
                }
                if (running) onConditionFalse();
            }
        } catch (InterruptedException e) {
            // exit
        }
    }
}*/

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