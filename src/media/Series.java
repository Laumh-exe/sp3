package media;

import java.util.List;

public class Series extends AMedia{
    
    protected int numSeasons;
	protected List<Integer> numEpisodes;
    private int startDate;
    private int endDate;

    public Series(String title, List<Genre> genres, int rating, String typeOfMedia, int numSeasons, List<Integer> numEpisodes, int startDate, int endDate) {
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
        return "Start date: " + startDate + " end date: " + endDate;
    }
     public int getReleaseDate(){return startDate;}
    
}
