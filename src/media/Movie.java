package media;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Movie extends AMedia {

    private int date;
    public Movie(String title, List<Genre> genres, float rating, String typeOfMedia, int date) {
        super(title, genres, rating, typeOfMedia);
        this.date = date;
    }


    @Override
    protected String getDate() {
        return "Date: " + date;
    }


    @Override
    public void create(Connection conn) throws SQLException {
        String sql = "INSERT INTO movies(title, year, rating)\nVALUES(?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, title);
        stmt.setInt(2, date);
        stmt.setFloat(3, rating);
        stmt.executeUpdate();
        stmt.close();

        for (Genre genre : genres) {
            String genreRelationSql = "INSERT INTO movie_genre(movieID, genreID)\nSELECT movieID, ? FROM (SELECT ID AS movieID FROM movies WHERE title = ?) subTable    ";
            PreparedStatement relationStmt = conn.prepareStatement(genreRelationSql);
            relationStmt.setInt(1, (genre.ordinal()+1));
            relationStmt.setString(2, title);
            relationStmt.close();
        }
    }
    
    
    @Override
    public int update(Connection conn) throws SQLException {
        String sql = "UPDATE movies SET title = ? WHERE title = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, title);
        stmt.setString(2, title);
        int linesEffected = stmt.executeUpdate();
        stmt.close();
        return linesEffected;
    }
}
