import java.util.HashSet;
import java.util.Set;

import media.AMedia;

public class User {

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
}
