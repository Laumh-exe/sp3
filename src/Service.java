
import java.util.*;

import media.AMedia;
import media.Genre;

public class Service {
    private User currentUser;
    private UI ui;

    private List<User> users;
    private List<AMedia> media;

    private IO io = new IO();

    private Collection<AMedia> searchByReleaseDate() {
         int date = releaseDateInput();
        Collection<AMedia> searchResult = new HashSet<AMedia>();
            for (AMedia m : media) {
                String listOfMedias = " ";
               if(m.getReleaseDate()<date){
                   listOfMedias += m.getTitle() + "\n";
                   ui.displayMessage(listOfMedias);
               }
                /*for (int date d: m.getReleaseDate()) {
                if (date == d) {
                    searchResult.add(m);*/
        }
        return searchResult;
    }

    private int releaseDateInput() {
        String input = ui.getInput("What year do you want to search for?" +
                "1) before 1960s\n" +
                "2) after 1960s and before 2000s\n" +
                "3) after 2000s\n");

        boolean releaseDate;
        switch (input) {
            case "1":
                releaseDate = 1930 < 1960;
                break;
            case "2":
                releaseDate = 1960 < 1980;
                break;
            case "3":
                releaseDate = 1980 < 2020;
                break;
            default:
                ui.displayMessage("Please type a number to search for release date");
                releaseDate = null;
                releaseDateInput();
        }
        return releaseDate;
    }
    }
}