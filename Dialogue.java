

class Dialogue {
    String Message;
    Dialogue[] Paragraph;
    
    Dialogue(String Message){
        this.Message = Message;
    }
    
    Dialogue(Dialogue[] Paragraph){
        this.Paragraph = Paragraph;
    }
    
    void display(int charDelay) {
        try {
            for (int i = 0; i<Message.length();i++) {
                System.out.print(Message.charAt(i));
                Thread.sleep(charDelay);
            }
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
    void display(int charDelay, int lineDelay) {
        try {
            System.out.print("\n");
            for (int i = 0; i<Paragraph.length; i++) {
                //System.out.print("\n");
                Paragraph[i].display(charDelay);
                Thread.sleep(lineDelay);
            }
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static void main(){
        Dialogue one = new Dialogue("Hello world!");
        one.display(100);
        
        Dialogue two = new Dialogue(new Dialogue[]{
            new Dialogue("This is a testing paragraph's first line.\n"),
            new Dialogue("This is a testing paragraph's second line.\n")
        });
        two.display(10,300);
    }
}