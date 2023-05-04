package media;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Series extends AMedia{
    
    protected int numSeasons;
	protected List<Integer> numEpisodes;
    private int startDate;
    private int endDate;

    public Series(String title, List<Genre> genres, float rating, String typeOfMedia, int numSeasons, List<Integer> numEpisodes, int startDate, int endDate) {
        super(title, genres, rating, typeOfMedia);
        this.numSeasons  = numSeasons;
        this.numEpisodes = numEpisodes;
        this.startDate   = startDate;
        this.endDate     = endDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());

        sb.append("Number of seasons: " + numSeasons + "\n");
        for (int i = 0; i < numEpisodes.size(); i++) {
            sb.append("Season ");
            sb.append(i+1);
            sb.append(": Number of episodes: ");
            sb.append(numEpisodes.get(i));
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    protected String getDate() {
        return "Start date: " + startDate + " end date: " + (endDate == -1 ? "ongoing" : endDate);
    }

    @Override
    public void create(Connection conn) throws SQLException {
        String sql = "INSERT INTO series(title, startyear, endyear, rating)\nVALUES(?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, title);
        stmt.setInt(2, startDate);
        stmt.setInt(3, endDate);
        stmt.setFloat(4, rating);
        stmt.executeUpdate();
        stmt.close();
        for (Genre genre : genres) {
            if(genre == null){
                continue;
            }
            String genreRelationSql = "INSERT INTO series_genre(seriesID, genreID)\nSELECT seriesID, ? FROM (SELECT ID AS seriesID FROM series WHERE title = ?) subTable";
            PreparedStatement relationStmt = conn.prepareStatement(genreRelationSql);
            relationStmt.setInt(1, (genre.ordinal()+1));
            relationStmt.setString(2, title);
            relationStmt.executeUpdate();
            relationStmt.close();
        }
        
        for (int i = 0; i < numSeasons; i++) {
            int episodes = numEpisodes.get(i);
            String seasonRelationSql = "INSERT INTO series_seasons(seriesID, seasonNumber, seasonNumberOfEpisodes)\nSELECT seriesID, ?, ? FROM (SELECT ID AS seriesID FROM series WHERE title = ?) subTable ";
            PreparedStatement seasonStmt = conn.prepareStatement(seasonRelationSql);
            seasonStmt.setInt(1,i+1);
            seasonStmt.setInt(2,episodes);
            seasonStmt.setString(3, title);
            seasonStmt.executeUpdate();
            seasonStmt.close();
        }
    }


    @Override
    public int update(Connection conn) throws SQLException {
        String sql = "UPDATE series SET title = ? WHERE title = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, title);
        stmt.setString(2, title);
        int linesEffected = stmt.executeUpdate();
        stmt.close();
        return linesEffected;
    } 
}
