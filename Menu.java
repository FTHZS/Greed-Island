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
        for (String option:Options) {
            System.out.println(option);
        }
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your choice: ");
        choice = sc.next();
    }
}