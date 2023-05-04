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


    public void connectToDB() {

        try {
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");


            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


        } catch (SQLException sE) {

            sE.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();


        }
    }



    public List<String> readUserDatafromDB () {

        // Connecting to a database by calling the connectToDB() method (from this class - see above).
        connectToDB();

        // The List of
        List<String> list_of_Users_Passwords_and_To_Watch_List_from_Database = new ArrayList<>();

        try {
            System.out.println("Creating statements...");

            //--------------------------------------------------------
            // Creating list with all users and their passwords
            String sql_query_Username_Password = "SELECT username, password FROM users";

            stmt = conn.prepareStatement(sql_query_Username_Password);

            ResultSet result_Set_Username_And_Password = stmt.executeQuery(sql_query_Username_Password);

            List<String> userNameS_and_passwordS = new ArrayList<>();

            while (result_Set_Username_And_Password.next()) {
                String userName = result_Set_Username_And_Password.getString("username");
                String password = result_Set_Username_And_Password.getString("password");

                //String userName_and_password = userName + "; " + password + "; ";

                userNameS_and_passwordS.add(userName);
                userNameS_and_passwordS.add(password);
            }


            String sql_getting_ToWatchList_Movies = "SELECT username, movies.title FROM users";
            sql_getting_ToWatchList_Movies += " LEFT JOIN user_to_watch_movies ON user_to_watch_movies.userID = users.ID";
            sql_getting_ToWatchList_Movies +=" LEFT JOIN movies ON user_to_watch_movies.movieID = movies.ID";

            List<String> userNameS_and_movieTitleS = new ArrayList<>();


            stmt = conn.prepareStatement(sql_getting_ToWatchList_Movies);

            ResultSet result_Set_ToWatchList_Movies = stmt.executeQuery(sql_getting_ToWatchList_Movies);

            while (result_Set_ToWatchList_Movies.next()) {
                String userName = result_Set_ToWatchList_Movies.getString("username");
                String movieTitle_for_ToWatchList = result_Set_ToWatchList_Movies.getString("movies.title");

                if (movieTitle_for_ToWatchList != null) {
                    userNameS_and_movieTitleS.add(userName);
                    userNameS_and_movieTitleS.add(movieTitle_for_ToWatchList);
                }
            }

            // Creating list with all users and series on their To Watch List
            List<String> userNameS_and_seriesTitleS = new ArrayList<>();
            String sql_getting_ToWatchList_Series = "SELECT username, series.title FROM users";
            sql_getting_ToWatchList_Series += " LEFT JOIN user_to_watch_series ON user_to_watch_series.userID = users.ID";
            sql_getting_ToWatchList_Series += " LEFT JOIN series ON user_to_watch_series.seriesID = series.ID";

            stmt = conn.prepareStatement(sql_getting_ToWatchList_Series);

            ResultSet result_Set_ToWatchList_Series = stmt.executeQuery(sql_getting_ToWatchList_Series);

            while (result_Set_ToWatchList_Series.next()) {
                String userName = result_Set_ToWatchList_Series.getString("username");
                String seriesTitle_for_ToWatchList = result_Set_ToWatchList_Series.getString("series.title");

                if (seriesTitle_for_ToWatchList != null) {
                    userNameS_and_seriesTitleS.add(userName);
                    userNameS_and_seriesTitleS.add(seriesTitle_for_ToWatchList);
                }
            }

            // Creating list with all users and movies on their Watched List
            String sql_getting_Watched_Movies = "SELECT username, movies.title FROM users";
            sql_getting_Watched_Movies += " LEFT JOIN user_watched_movies ON user_watched_movies.userID = users.ID";
            sql_getting_Watched_Movies += " LEFT JOIN movies ON user_watched_movies.movieID = movies.ID";

            List<String> userNameS_and_movieTitleS_Watched = new ArrayList<>();
            stmt = conn.prepareStatement(sql_getting_Watched_Movies);
            ResultSet result_Set_Watched_Movies = stmt.executeQuery(sql_getting_Watched_Movies);

            while (result_Set_Watched_Movies.next()) {
                String userName = result_Set_Watched_Movies.getString("username");
                String movieTitle_for_Watched_Movies = result_Set_Watched_Movies.getString("movies.title");

                if (movieTitle_for_Watched_Movies != null) {
                    userNameS_and_movieTitleS_Watched.add(userName);
                    userNameS_and_movieTitleS_Watched.add(movieTitle_for_Watched_Movies);
                }
            }

            // Creating list with all users and series on their To Watch List
            List<String> userNameS_and_seriesTitleS_Watched = new ArrayList<>();
            String sql_getting_Watched_Series = "SELECT username, series.title FROM users";
            sql_getting_Watched_Series += " LEFT JOIN user_watched_series ON user_watched_series.userID = users.ID";
            sql_getting_Watched_Series += " LEFT JOIN series ON user_watched_series.seriesID = series.ID";

            stmt = conn.prepareStatement(sql_getting_Watched_Series);
            ResultSet result_Set_Watched_Series = stmt.executeQuery(sql_getting_Watched_Series);

            while (result_Set_Watched_Series.next()) {
                String userName = result_Set_Watched_Series.getString("username");
                String seriesTitle_for_Watched_Series = result_Set_Watched_Series.getString("series.title");

                if (seriesTitle_for_Watched_Series != null) {
                    userNameS_and_seriesTitleS_Watched.add(userName);
                    userNameS_and_seriesTitleS_Watched.add(seriesTitle_for_Watched_Series);
                }
            }

            // Creating list with users and their To Watch List where every user and that users
            // To Watch List is a String-element in the list
            for (int i = 0; i < userNameS_and_passwordS.size(); i++){
                // Creating a string that begins with the users username and later adding that user's
                // To Watch List movies and To Watch List series to the string
                if (i%2 == 0) {
                    String a_User_the_users_Password_and_the_Users_ToWatchList = userNameS_and_passwordS.get(i) + ";" +
                            userNameS_and_passwordS.get(i+1) + ";";

                    // Adding all the movies on the users To Watch List to the above string
                    for (int j = 0; j < userNameS_and_movieTitleS.size(); j++) {
                        String the_ToWatchList_Movies = "";
                        if (userNameS_and_passwordS.get(i).equals(userNameS_and_movieTitleS.get(j))) {
                            the_ToWatchList_Movies = userNameS_and_movieTitleS.get(j + 1) + ":";
                        }
                        a_User_the_users_Password_and_the_Users_ToWatchList += the_ToWatchList_Movies;
                    }
                    
                    // Adding all the series on the users To Watch List to the above string
                    for (int j = 0; j < userNameS_and_seriesTitleS.size(); j++) {
                        String the_ToWatchList_Series = "";

                        if (userNameS_and_passwordS.get(i).equals(userNameS_and_seriesTitleS.get(j))) {
                            the_ToWatchList_Series = userNameS_and_seriesTitleS.get(j + 1) + ":";
                        }

                        a_User_the_users_Password_and_the_Users_ToWatchList += the_ToWatchList_Series;
                    }
                    a_User_the_users_Password_and_the_Users_ToWatchList += ";";

                    // Adding all the movies on the users Watched List to the above string
                    for (int j = 0; j < userNameS_and_movieTitleS_Watched.size(); j++) {
                        String the_Watched_Movies = "";
                        if (userNameS_and_passwordS.get(i).equals(userNameS_and_movieTitleS_Watched.get(j))) {
                            the_Watched_Movies = userNameS_and_movieTitleS_Watched.get(j + 1) + ":";
                        }
                        a_User_the_users_Password_and_the_Users_ToWatchList += the_Watched_Movies;
                    }

                    // Adding all the series on the users To Watch List to the above string
                    for (int j = 0; j < userNameS_and_seriesTitleS_Watched.size(); j++) {
                        String the_Watched_Series = "";
                        if (userNameS_and_passwordS.get(i).equals(userNameS_and_seriesTitleS_Watched.get(j))) {
                            the_Watched_Series = userNameS_and_seriesTitleS_Watched.get(j + 1) + ":";
                        }
                        a_User_the_users_Password_and_the_Users_ToWatchList += the_Watched_Series;
                    }
                    list_of_Users_Passwords_and_To_Watch_List_from_Database.add(a_User_the_users_Password_and_the_Users_ToWatchList);
                }
            }

            stmt.close();
            conn.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();

        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();

        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try

        return list_of_Users_Passwords_and_To_Watch_List_from_Database;
    }

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

        connectToDB();
        /*String DB_URL = "jdbc:mysql://localhost/sp3";
        String USER = "root";
        String PASS = "Simon99";

        Connection conn = null;
        Statement stmt = null;

         */

        try{
            //Class.forName("com.mysql.jdbc.Driver");
            //conn = DriverManager.getConnection(DB_URL,USER,PASS);

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