import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Service {
    private User currenUser;
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
            if (!login(password, username)) {
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
        UI.displayMessage("Welcome " + currenUser.getName + "\n" +
                "Please choose one of the following options or type Q to quit:\n" +
                "1) Search for a movie or show by title\n" +
                "2) Search for a movie or show by genre\n" +
                "3) Show your already-seen list\n" +
                "4) Show watchlist");

        // Get input and execute accordingly
        switch (UI.getInput().toUpperCase()) {
            case "1":
                //Search for movie by title
                UI.displayMessage("What title do you want to find?\n");
                searchMedia(UI.getInput());
                //TODO: add options here
                break;
            case "2":
                // Show list of all genres to choose from
                // Search for media by genre
                break;
            case "3":
                currenUser.getWatchedMedialist(); //TODO: add options  here
                break;
            case "4":
                currenUser.getWatchlist(); //TODO: add options  here
            case "q":
                return;
            default:
                UI.displayMessage("Something went wrong in main menu");
                return;
        }
        mainMenu();
    }


    //Lauritz by choice

    private void showMovies() {

    }

    //Lauritz by choice
    private void showSeries() {

    }

    // Tobias

    private boolean login(String username, String password) {
        return false;
    }


    // Lauritz

    private list<AMedia> searchMedia(String title) {
        list<AMedia> searchResult = new HashSet<AMedia>();
        for (AMedia m : media) {
            if (title.equalsIgnoreCase(m.getName)) {
                searchResult.add(m);
            }
        }
        return searchResult;
    }

    //Lauritz
    private list<AMedia> searchByGenre(Genre genre) {
        list<AMedia> searchResult = new HashSet<AMedia>();
        for (AMedia m : media) {
            if (genre.equalsIgnoreCase(m.getGenre)) {
                searchResult.add(m);
            }
        }
        return searchResult; //TODO: Make sure you only have to match with one genre, and not all.
    }


    // Tobias
    private void onClose() {

    }
}
