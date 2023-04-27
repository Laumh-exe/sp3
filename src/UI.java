import java.util.Scanner;

public class UI {
    Scanner scan;
    public UI (){
        scan = new Scanner(System.in);
    }
    public void displayMessage(String msg){
        System.out.println(msg);
    }
    public String getInput(String msg){
        displayMessage(msg);

        String input = scan.nextLine();
        return input;
    }
}

