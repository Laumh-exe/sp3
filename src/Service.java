
import java.util.*;

import media.AMedia;
import media.Genre;

public class Service {
    private User currentUser;
    private UI ui;

    private List<User> users;
    private List<AMedia> media;

    private IO io = new IO();


    private AMedia getReleaseDate(int releaseDate) {
        for (AMedia m : media) {
            if (m.getReleaseDate.equalsIgnoreCase(releaseDate)) {
                return m.getReleaseDate();
            }
        }
        return null;
    }

    private Collection<AMedia> searchByReleaseDate() {
        Date date = getReleaseDate();
        Collection<AMedia> searchResult = new HashSet<AMedia>();
        for (AMedia m : media) {
            for (Date d : m.getReleaseDate()) {
                if (date == d){
                    searchResult.add(m);
                }
            }
        }
        return searchResult;
    }
}