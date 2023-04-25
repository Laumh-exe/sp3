import java.util.Scanner;

public class UI {
    Scanner scan;
    public void TextUI (){

        Scanner scan = new Scanner(System.in);
    }
    public void displayMessage(String msg){
        System.out.println(msg);
    }
    public String getInput(String msg){
        displayMessage(msg);

        String input = scan.nextLine();
        return input;
    }
    public void showMessage(String msg){
        System.out.println("**********************");
        System.out.println(msg);
        System.out.println("**********************");
    }
}

