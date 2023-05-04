import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import media.AMedia;
import media.ISavable;

public class User implements ISavable {

    private String userName;
    private String password;
    private Set<AMedia> watchedMedia;
    private Set<AMedia> watchList;

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
        watchedMedia = new HashSet<>();
        watchList = new HashSet<>();
    }

    public String getName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }
    //Todo: Possible password validator?
    public boolean comparePassword(String password){
        if(password.equals(this.password)) {
            return true;
        } else {
            return false;
        }
    }

    public void addToWatchedMedia(AMedia media){
        watchedMedia.add(media);
    }

    public Set<AMedia> getWatchedMedia(){
        return watchedMedia;
    }

    public void addToWatchList(AMedia media){
        watchList.add(media);
    }
    
    public Set<AMedia> getWatchList(){
        return watchList;
    }

    public String watchListToString() {
        String watchListToString = "";
        for(AMedia media:watchList) {
            watchListToString += media.getTitle() + "\n";
        }
        return watchListToString;
    }
    public String watchedMediaToString() {
        String watchedListToString = "";
        for(AMedia media:watchedMedia) {
            watchedListToString += media.getTitle() + "\n";
        }
        return watchedListToString;
    }

    @Override
    public void create(Connection conn) throws SQLException {
        String sql = "INSERT INTO users(username, password)\nVALUES(?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, userName);
        stmt.setString(2, password);
        stmt.executeUpdate();
        update(conn);
        stmt.close();
    }

    @Override
    public int update(Connection conn) throws SQLException {
        String userIDGetSQL = "SELECT ID FROM users WHERE username = ?"; 
        PreparedStatement stmt = conn.prepareStatement(userIDGetSQL);
        stmt.setString(1, userName);
        ResultSet iDResultSet = stmt.executeQuery();
        if(!iDResultSet.next()){
            return 0;
        }
        int userID = iDResultSet.getInt(1);
        
        for (AMedia aMedia : watchList) {
            if (aMedia.getTypeOfMedia().equalsIgnoreCase("film")) {

                int mediaID = getMediaID("movies", stmt, conn, aMedia);

                ResultSet pairRS = findPair("user_to_watch_movies", stmt, conn, userID, mediaID,"movieID");
                
                if(!pairRS.next()){
                    insertInto("user_to_watch_movies", stmt, conn, userID, mediaID,"movieID");
                }
            } else {
                int mediaID = getMediaID("series", stmt, conn, aMedia);
                
                ResultSet pairRS = findPair("user_to_watch_series", stmt, conn, userID, mediaID,"seriesID");
                if(!pairRS.next()){
                    insertInto("user_to_watch_series", stmt, conn, userID, mediaID, "seriesID");
                }
            }
        }
        for (AMedia aMedia : watchedMedia) {
            if (aMedia.getTypeOfMedia().equalsIgnoreCase("film")) {
                int mediaID = getMediaID("movies", stmt, conn, aMedia);
                
                ResultSet pairRS = findPair("user_watched_movies", stmt, conn, userID, mediaID,"movieID");
                
                if(!pairRS.next()){
                    insertInto("user_watched_movies", stmt, conn, userID, mediaID, "movieID");
                }
            } else {
                int mediaID = getMediaID("series", stmt, conn, aMedia);
                
                ResultSet pairRS = findPair("user_watched_series", stmt, conn, userID, mediaID,"seriesID");
                
                if(!pairRS.next()){
                    insertInto("user_watched_series", stmt, conn, userID, mediaID,"seriesID");
                }

            }
        }
        stmt.close();
        return 1;
    }

    private int getMediaID(String table, PreparedStatement stmt, Connection conn, AMedia aMedia) throws SQLException{
        String mediaIDGetSQL = "SELECT ID FROM " + table + " WHERE title = ?"; 
        stmt = conn.prepareStatement(mediaIDGetSQL);
        stmt.setString(1, aMedia.getTitle());
        ResultSet mediaIDRS = stmt.executeQuery();
        mediaIDRS.next();
        return mediaIDRS.getInt("ID");
    }

    private void insertInto(String table, PreparedStatement stmt, Connection conn, int userID, int mediaID, String mediaIDName) throws SQLException{
        String insertSQL = "INSERT INTO " + table + "(userID,"+ mediaIDName +") VALUES(?,?)";
        // String insertSQL = "INSERT INTO user_to_watch_series(userID, ?) VALUES(?,?)";
        stmt = conn.prepareStatement(insertSQL);
        stmt.setInt(1, userID);
        stmt.setInt(2, mediaID);
        stmt.executeUpdate();
    }

    private ResultSet findPair(String table, PreparedStatement stmt, Connection conn, int userID, int mediaID, String mediaIDName) throws SQLException{
        String userMoviePair = "SELECT * FROM "+ table +" WHERE userID = ? AND " + mediaIDName + " = ?"; 
        stmt = conn.prepareStatement(userMoviePair);
        stmt.setInt(1, userID);
        stmt.setInt(2, mediaID);
        return stmt.executeQuery();
    }

}



