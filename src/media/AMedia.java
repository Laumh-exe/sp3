package media;

import java.util.List;

public abstract class AMedia implements ISavable{
    
    protected String title;
    protected String typeOfMedia;
    protected List<Genre> genres;
    protected float rating;

    public AMedia(String title, List<Genre> genres, Float rating, String typeOfMedia){
        this.typeOfMedia = typeOfMedia;
        this.title       = title;
        this.genres      = genres;
        this.rating      = rating;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getTitle(){
        return title;
    }

    public float getRating(){
        return rating;
    }
    
    public String getTypeOfMedia() {
        return typeOfMedia;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("This is a " + typeOfMedia + "\n");
        sb.append("Title: " + title + "\n");
        sb.append("Rating: " + rating + "\n");
        sb.append("Genres: ");
        for (Genre genre : genres) {
            sb.append(genre + ", ");
        }
        sb.deleteCharAt(sb.length()-2);
        sb.append("\n");
        sb.append(getDate() + "\n");
        return sb.toString();
    }

    /**
     * This method returns the date(s) in a formated way. 
     * Often this starts with "Date: "
     * @return date(s)
     */
    protected abstract String getDate();


}