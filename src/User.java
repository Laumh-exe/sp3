import java.util.HashSet;
import java.util.Set;

public class User {

    private String userName;
    private String password;
    private Set<AMedia> watchedMedia;
    private Set<AMedia> watchList;

    public String getName(){
        return userName;
    }
    public boolean comparePassword(String password){
        return true;
    }
    public void addToWatchList(AMedia media){
        Set addWatchList = new HashSet();

    }
    public Set<AMedia> getWatchList(){
        return watchList;
    }
}
