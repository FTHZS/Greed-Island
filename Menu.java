import java.util.ArrayList;
import java.util.Scanner;

class Menu {
    String choice;
    ArrayList<String> Options;
    
    Menu() {
        Options = new ArrayList<String>();
    }
    
    void add(String option) {
        Options.add((Options.size()+1)+". "+option);
    }
    
    void getInput() {
        Dialogue[] para = new Dialogue[Options.size()+1];

        int i = 0;
        for (String option:Options) {
            //System.out.println(option);
            para[i++]= new Dialogue(option+"\n");
        }
        
        Scanner sc = new Scanner(System.in);
        para[Options.size()]=new Dialogue("Enter your choice: ");
        new Dialogue(para).display(0,0);
        choice = sc.next();
    }
}