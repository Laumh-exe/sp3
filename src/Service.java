
import java.util.*;

import media.AMedia;
import media.Genre;

public class Service {
    private User currentUser;
    private UI ui;

    private List<User> users;
    private List<AMedia> media;

    private IO io = new IO();
    

    private AMedia getReleaseDateInput(int releaseDate) {
        for (AMedia m : media) {
            if (m.getReleaseDate.equalsIgnoreCase(releaseDate)) {
                return m;
            }
        }
        return null;
    }

    private Collection<AMedia> searchByReleaseDate() {
        Date releaseDateInput = null;
        Date date = null;
        Collection<AMedia> searchResult = new HashSet<AMedia>();
        for (AMedia m : media) {
            for (Date date : m.getReleaseDate()) {
                if (date == d) {
                    searchResult.add(m);
                }
            }
        }
        return searchResult;
    }
    private Collection<AMedia> searchMedia () {
        String title = ui.getInput("What date do you want to find?\n");
        Collection<AMedia> searchResult = new HashSet<AMedia>();
        for (AMedia m : media) {
            if (title.equalsIgnoreCase(m.getReleaseDate)) {
                searchResult.add(m);
            }
        }
        return searchResult;
    }
}