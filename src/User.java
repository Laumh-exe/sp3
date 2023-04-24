import java.util.HashSet;
import java.util.Set;
import java.util.regex.*;
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

    public static boolean isValidPassword(String password) {

        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);
        if (password == null) {
            return false;
        } else {
            return true;
        }
    }

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
}
