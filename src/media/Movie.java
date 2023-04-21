package media;

import java.util.List;

public class Movie extends AMedia {

    private int date;
    public Movie(String title, List<Genre> genres, int rating, int date) {
        super(title, genres, rating);
        this.date = date;
    }


    @Override
    protected String getDate() {
        return "Date: " + date;
    }
    
}
