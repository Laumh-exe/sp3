import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import media.AMedia;
import media.ISavable;

public class IO {
    // database URL
    static final String DB_URL = "jdbc:mysql://localhost/world";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "4&G3#n&7552b44";
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

    public ArrayList<String> readMovieDataFromDB(String query) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<String> dataList = new ArrayList<>();
        try{
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            String sql = query; //Here: whatever query the method is called with - How to use
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            String currentMovie = "";
            String data = "";
            float rating = 0;
            int iDCounter = rs.getInt("ID");
            //STEP 4: Extract data from result set
            while(rs.next()){
                // Do last
                // Add data
                int iD = rs.getInt("ID");
                if(iDCounter != iD) {
                    data = data.substring(0,data.length()-1);
                    data += "; " + rating + ";";
                    dataList.add(data);
                    data = "";
                }
                iDCounter = iD;

                // Do first
                if (currentMovie != rs.getString("Title")) {
                    data += rs.getString("Title") + "; ";
                    data += rs.getInt("Release Date") + "; ";
                    currentMovie = rs.getString("Title");
                }
                // Do for every row
                if(iDCounter == iD) {
                    data += rs.getString("Genre") + ", "; //TODO : Get genre list in correct string
                }
                rating = rs.getFloat("Rating");
            }
//STEP 5: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
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
        return dataList;
    }

    /* public ArrayList<String> readSeriesDataFromDB(String query) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<String> dataList = new ArrayList<>();
        try{
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            String sql = query; //Here: whatever query the method is called with - How to use
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            String currentMovie = rs.getString("title");
            String data = "";
            float rating;
            String title = "";
            String run = ""; // Release date +  end date( if there is one)
            HashSet<String> genres = new HashSet<>();
            HashSet<String> seasonsAndEpisodes =  new HashSet<>();

            String seasons = "";
            //STEP 4: Extract data from result set
            while(rs.next()){
                // IF CURRENTMOVIE IS NOT THE SAME
                    // Save title, startdate, releaseDate, in data String
                        // Check if endDate == -1 (if it is, then there is not end date set)
                    // Then add genres from hashset to data String with a for-each loop
                    // now add seasons/episodes to data String with a for-each loop
                    // save the data in array list and save current movie as whatever the title is now - now we move on to next film:

                // Check if currentmovie is the same as title if it is do the following:
                // save title, startDate, releaseDate, rating in variables for use later
                // save genres in hashset,
                // save seasons with episode as string in hashset

                if(!currentMovie.equalsIgnoreCase(rs.getString("title"))) {

                }
                else {
                    title = rs.getString("Title");
                    run += rs.getint("startYear"); // TODO :
                }
            }
            //STEP 5: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
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
        return dataList;
    } */



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





