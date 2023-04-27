import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import media.AMedia;

public class IO {

    File file;

    Scanner scan;
    public ArrayList<String> getData(String path) throws FileNotFoundException {

        ArrayList<String> data = new ArrayList<>();
        try {
            file = new File(path);


            Scanner scan = new Scanner(file);

            scan.nextLine(); // ignore header in csv


            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                data.add(line);
            }

        } catch (FileNotFoundException e) {
            System.out.println(path + " was not found ");
        }
        return data;
    }



    public void saveData (String path, List <User> users){
        FileWriter writer = null;

        try {
            String saveData = "";

            for (User u : users) {
                saveData += u.getName();
                saveData += ";";
                saveData += u.getPassword();
                saveData += ";";
                for (AMedia a : u.getWatchList()) {
                    saveData += a.getTitle();
                    saveData += ",";


                }
                saveData += ";";
                for (AMedia a : u.getWatchedMedia()) {
                    saveData += a.getTitle();
                    saveData += ",";

                }
                saveData += "\n";
            }

            writer = new FileWriter(path);

            writer.write("User, Movie \n");


            writer.write(saveData);


            writer.close();


        } catch (IOException e) {
            e.getMessage();
        }

    }

}





