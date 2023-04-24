import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class IO {

    File file;

    Scanner scan;
    public ArrayList<String> getData(String path) {

        file = new File(path);
        ArrayList<String> data = new ArrayList<>();


        try {
           Scanner scan = new Scanner(file);

            scan.nextLine(); // ignore header in csv

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                data.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file was not found");

        }

        return data;
    }

    public void saveSavedData(String path, User user) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);

            writer.write("User, Movie \n");


            writer.write(user.getUser() + "," + user.getMovie() + "," + user.getSerie "\n");


            writer.close();


        } catch (IOException e) {


        }

    }
    public void saveWatchedData(String path, User user) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);

            writer.write("User, Movie \n");


            writer.write(user.getUser() + "," + user.getMovie() + "," + user.getSerie "\n");


            writer.close();


        } catch (IOException e) {


        }

    }

}




