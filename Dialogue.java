import java.util.LinkedList;

/*class Dialogue {
    String Message;
    Dialogue[] Paragraph;

    // Shared linked list + simple boolean
    private static final LinkedList<Dialogue> dialogueList = new LinkedList<>();
    private static boolean isPrinting = false;

    Dialogue(String Message){
        this.Message = Message;
    }
    
    Dialogue(Dialogue[] Paragraph){
        this.Paragraph = Paragraph;
    }

    void display(int charDelay) {
        enqueueAndWait(() -> {
            printMessage(charDelay);
        });
    }

    void display(int charDelay, int lineDelay) {
        enqueueAndWait(() -> {
            printParagraph(charDelay, lineDelay);
        });
    }

    // --- actual printing logic without queue ---
    private void printMessage(int charDelay) {
        try {
            for (int i = 0; i < Message.length(); i++) {
                System.out.print(Message.charAt(i));
                Thread.sleep(charDelay);
            }
            //System.out.print("\n");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void printParagraph(int charDelay, int lineDelay) {
        try {
            for (int i = 0; i < Paragraph.length; i++) {
                Paragraph[i].printMessage(charDelay); // direct print, no enqueue
                Thread.sleep(lineDelay);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // --- queue logic ---
    private void enqueueAndWait(Runnable task) {
        synchronized (dialogueList) {
            dialogueList.addLast(this);

            // Wait until this dialogue is at the front and nothing else is printing
            while (dialogueList.getFirst() != this || isPrinting) {
                try {
                    dialogueList.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                    return;
                }
            }

            isPrinting = true; // mark as active
        }

        // Print outside synchronized block
        task.run();

        synchronized (dialogueList) {
            dialogueList.removeFirst(); // remove self
            isPrinting = false;         // free lock
            dialogueList.notifyAll();   // wake up next
        }
    }

    public static void main(){
        System.out.println("started.\n");
        Dialogue one = new Dialogue("Hello world!");
        new Thread(() -> one.display(100)).start();

        Dialogue two = new Dialogue(new Dialogue[]{
            new Dialogue("This is a testing paragraph's first line.\n"),
            new Dialogue("This is a testing paragraph's second line.\n")
        });
        new Thread(() -> two.display(10,300)).start();

        Dialogue three = new Dialogue("Another dialogue while two is still printing!");
        new Thread(() -> three.display(50)).start();
    }
}*/

class Dialogue {
    String Message;
    Dialogue[] Paragraph;
    
    int charDelay;
    
    private static LinkedList<Dialogue> dialogueQueue;
    private static boolean workerRunning = false;
    private static Thread worker;
    
    static {
        dialogueQueue = new LinkedList<Dialogue>();
        
        worker = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    workerRunning = true;
                    if (dialogueQueue.size()==0) {
                        workerRunning = false;
                        try {
                            Thread.sleep(0);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    
                    Dialogue d = dialogueQueue.pollFirst();
                    if (d == null) {
                        continue; // nothing in queue, loop again
                    }

                    try {
                        for (int i = 0; i<d.Message.length();i++) {
                            System.out.print(d.Message.charAt(i));
                            if (d.charDelay >0) {
                                Thread.sleep(d.charDelay);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        },"Dialogue worker thread");
        worker.start();
    }
    
    Dialogue(String Message){
        this.Message = Message;
    }
    
    Dialogue(Dialogue[] Paragraph){
        this.Paragraph = Paragraph;
    }
    
    void display(int charDelay) {
        this.charDelay = charDelay;
        dialogueQueue.addLast(this);
    }
    
    void display(int charDelay, int lineDelay) {
        try {
            int i = 0;
            if (i>0){new Dialogue("\n").display(0);}
            for (i=0;i<Paragraph.length; i++) {
                //System.out.print("\n");
                Paragraph[i].display(charDelay);
                Thread.sleep(lineDelay);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /*public static void main(){
        Dialogue one = new Dialogue("Hello world!");
        one.display(100);
        
        Dialogue two = new Dialogue(new Dialogue[]{
            new Dialogue("This is a testing paragraph's first line.\n"),
            new Dialogue("This is a testing paragraph's second line.\n")
        });
        two.display(10,300);
    }*/
    
    static void test() {
        for (int i = 0; i<5;i++){
            Dialogue two = new Dialogue(new Dialogue[]{
                new Dialogue("This is a testing paragraph's first line.\n"),
                new Dialogue("This is a testing paragraph's second line.\n")
            });
            new Thread(() -> two.display(10,300)).start();
            
            Dialogue three = new Dialogue("Another dialogue while two is still printing!\n");
            new Thread(() -> three.display(50)).start();
        }
    }
    
    public static void main(){
        System.out.println("started.\n");
        Dialogue one = new Dialogue("Hello world!\n");
        new Thread(() -> one.display(100)).start();

        Dialogue two = new Dialogue(new Dialogue[]{
            new Dialogue("This is a testing paragraph's first line.\n"),
            new Dialogue("This is a testing paragraph's second line.\n")
        });
        new Thread(() -> two.display(10,300)).start();

        Dialogue three = new Dialogue("Another dialogue while two is still printing!\n");
        new Thread(() -> three.display(50)).start();
    }
}
