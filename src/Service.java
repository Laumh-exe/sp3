import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Service {
    private User currentUser;
    private List<User> users;
    private List<AMedia> media;


    // Lauritz
    public Service() {
        // Setup data
        dataSetup();
        // Setup user and run main menu
        userSetup();
        // Main menu - handles other methods
        mainMenu();
        // Closes when mainMenu is exited
        onClose();
    }


    // Tobias
    private void dataSetup() {


    }


    // Lauritz
    private void userSetup() {

        // Asks: Login og Create user
        UI.displayMessage("1) Login\n" + "2) Create new user");
        if (UI.getInput().equalsIgnoreCase("1")) {
            UI.displayMessage("Enter your username: ");
            String username = UI.getInput();
            UI.displayMessage("Enter your password: ");
            String password = UI.getInput();
            // If login fails - if it doesnt, this method is over and login() has saved the currentUser
            if (!login(password, username)) { //TODO: username, password
                UI.displayMessage("I Can not find that user, try again");
                userSetup();
            }
        }
        // When creating new User
        else if (UI.getInput().equalsIgnoreCase("2")) {
            UI.displayMessage("Enter a username: ");
            String username = UI.getInput();
            UI.displayMessage("Enter a password: ");
            String password = UI.getInput();
            User currentUser = new User(username, password); //TODO make sure password and username are arguments in user class, and in the same order!
            users.add(currentUser);
        }
        // If something went wrong - maybe exception
        else {
            UI.displayMessage("Please type either 1 or 2 and the press enter");
            userSetup();
        }
    }


    // Lauritz
    private void mainMenu() {
        UI.displayMessage("Welcome " + currentUser.getName + "\n" +
                "Please choose one of the following options or type Q to quit:\n" +
                "1) Search for a movie or show by title\n" +
                "2) Search for a movie or show by genre\n" +
                "3) Show your already-seen list\n" +
                "4) Show watchlist");

        // Get input and execute accordingly
        do { // Do while(true)
            switch (UI.getInput().toUpperCase()) {
                case "1":
                    //Search for movie by title
                    searchMedia();
                    //Options
                    searchMedia();
                    makeChoice(searchMedia());
                    break;
                case "2":
                    // Calls toString on the returned Collection
                    searchByGenre();
                    makeChoice(searchByGenre());
                    // Give choice, either add to watchlist or watchMovie
                    break;
                case "3":
                    currentUser.getWatchedMediaList(); //TODO: add options  here
                    makeChoice(currentUser.getWatchedMedia());
                    break;
                case "4":
                    currentUser.getWatchlist(); //TODO: add options  here
                    makeChoice(currentUser.getWatchList());
                case "q":
                    return; //TODO: exit message here or in onClose()
                default:
                    UI.displayMessage("Something went wrong in main menu");
                    return;
            }
        }
        while (true);
    }


    //Lauritz by choice

    private void showMedia() {

    }

    //Lauritz by choice
    private void makeChoice(Collection media) {
        // Ask if user wants to watch or add to watchlist
        UI.displayMessage("Please choose one of the following options\n" +
                "1) Add to watch-List" +
                "2) Watch movie");
        switch (UI.getInput) {
            case "1":
                UI.displayMessage("Please type in the name of the movie or show you want to add");
                AMedia addMedia = getTitleInput(UI.getInput());
                if (currentMedia != null) {
                    currentUser.addToWatchList(addMedia);
                }
                else {
                    UI.displayMessage("The title did not match any existing titles");
                }
                break;
            case "2":
                UI.displayMessage("Please type in the name of the movie or show you want to watch");
                AMedia watchMedia = getTitleInput(UI.getInput());
                if (currentMedia != null) {
                    currentUser.addToWatchedMedia(watchMedia);
                }
                else {
                    UI.displayMessage("The title did not match any existing titles");
                }
        }
    }

    // Tobias

    private boolean login(String username, String password) {
        return false;
    }


    // Lauritz

    private Collection<AMedia> searchMedia() {
        UI.displayMessage("What title do you want to find?\n");
        String title = UI.getInput();
        Collection<AMedia> searchResult = new HashSet<AMedia>();
        for (AMedia m : media) {
            if (title.equalsIgnoreCase(m.getName)) {
                searchResult.add(m);
            }
        }
        return searchResult;
    }

    //Lauritz
    private Collection<AMedia> searchByGenre() {
        Genre genre = genreInput();
        Collection<AMedia> searchResult = new HashSet<AMedia>();
        for (AMedia m : media) {
            for (Genre g : m.getGenre) {
                if (genre.equalsIgnoreCase(g)) {
                    searchResult.add(m);
                    break;
                }
            }
        }
        return searchResult;
    }

    private Genre genreInput() {
        //List all genres so user can choose one with a number
        UI.displayMessage("What genre do you want to search for?" +
                "1) Crime\n" +
                "2) Drama\n" +
                "3) Sport\n" +
                "4) Fantasy\n" +
                "5) Romance\n" +
                "6) Biography\n" +
                "7) Thriller\n" +
                "8) Mystery\n" +
                "9) Musical\n" +
                "10) Comedy\n" +
                "11) Family\n" +
                "12) Action\n" +
                "13) Adventure\n" +
                "14) History\n"+
                "15) War\n"+
                "16) Sci-Fi\n"+
                "17) Film-Noir\n"+
                "18) Western\n"+
                "19) Horror\n"+
                "20) Music\n");
        // TODO: Maybe more genres??

        Genre genre;
        switch (getInput()) {
            case "1":
                genre = Genre.CRIME;
                break;
            case "2":
                genre = Genre.DRAMA;
                break;
            case "3":
                genre = Genre.SPORT;
                break;
            case "4":
                genre = Genre.FANTASY;
                break;
            case "5":
                genre = Genre.ROMANCE;
                break;
            case "6":
                genre = Genre.BIOGRAPHY;
                break;
            case "7":
                genre = Genre.THRILLER;
                break;
            case "8":
                genre = Genre.MYSTERY;
                break;
            case "9":
                genre = Genre.MUSICAL;
                break;
            case "10":
                genre = Genre.COMEDY;
                break;
            case "11":
                genre = Genre.FAMILY;
                break;
            case "12":
                genre = Genre.ACTION;
                break;
            case "13":
                genre = Genre.ADVENTURE;
                break;
            case "14":
                genre = Genre.HISTORY;
                break;
            case "15":
                genre = Genre.WAR;
                break;
            case "16":
                genre = Genre.SCIFI;
                break;
            case "17":
                genre = Genre.FILMNOIR;
                break;
            case "18":
                genre = Genre.WESTERN;
                break;
            case "19":
                genre = Genre.HORROR;
                break;
            case "20":
                genre = Genre.MUSIC;
                break;
            default:
                UI.displayMessage("Please type a number to search for genre");
                genre = null;
                genreInput();
        }
        return genre;
    }

    private AMedia getTitleInput(String title) {
        // Compare title to the titles of the search
        for (AMedia m : media) {
            if (m.getName.equalsIgnoreCase(title)) {
                return m;
            }
        }
        return null;
    }


    // Tobias
    private void onClose() {

    }
}
