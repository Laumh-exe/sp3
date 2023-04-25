package media;

import java.util.List;

public class Movie extends AMedia {

    private int date;
    public Movie(String title, List<Genre> genres, int rating, String typeOfMedia, int date) {
        super(title, genres, rating, typeOfMedia);
        this.date = date;
    }


    @Override
    protected String getDate() {
        return "Date: " + date;
    }
    public int getReleaseDate(){return date;}
    
}
