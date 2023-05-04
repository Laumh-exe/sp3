import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import media.AMedia;
import media.ISavable;

public class IO {

    File file;

    Scanner scan;

    private final String DB_URL = "jdbc:mysql://localhost/sp3";

    //  Database credentials
    private final String USER = "root";
    private final String PASS = "/TRosseneri1899";

    private Connection conn = null;
    private PreparedStatement stmt = null;
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
                    saveData += ":";


                }
                saveData += ";";
                for (AMedia a : u.getWatchedMedia()) {
                    saveData += a.getTitle();
                    saveData += ":";

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



    public void saveToDB(List<ISavable> savables) {
        String DB_URL = "jdbc:mysql://localhost/sp3";
        String USER = "root";
        String PASS = "Simon99";

        Connection conn = null;
        Statement stmt = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            for (ISavable toSave : savables) {
                int linesEffected = toSave.update(conn);
                if(linesEffected == 0){
                    toSave.create(conn);
                }
            }
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try 
    }

}





