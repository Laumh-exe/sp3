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
        do{ // Do while(true)
            switch (UI.getInput().toUpperCase()) {
                case "1":
                    //Search for movie by title
                    UI.displayMessage("What title do you want to find?\n");
                    searchMedia(UI.getInput());
                    //TODO: add options here
                    break;
                case "2":
                    // Calls toString on the returned Collection
                    searchByGenre();
                    // Give choice, either add to watchlist or watchMovie

                    break;
                case "3":
                    currentUser.getWatchedMedialist(); //TODO: add options  here
                    break;
                case "4":
                    currentUser.getWatchlist(); //TODO: add options  here
                case "q":
                    return; //TODO: exit message here or in onClose()
                default:
                    UI.displayMessage("Something went wrong in main menu");
                    return;
            }
        }
        while(true);
    }


    //Lauritz by choice

    private void showMedia() {

    }

    //Lauritz by choice
    private void makeChoice() {
        // Ask if user wants to watch or add to watchlist
        UI.displayMessage("Please choose one of the following options")

        // Ask the user to type in name of movie
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

    private Genre genreInput(){
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
                        "12) Action\n"
                "13) Adventure\n" +
                        "14) History\n");
        // TODO: Maybe more genres??

        Genre genre;
        switch (getInput()) {
            case "1" :
                genre = Genre.CRIME;
                break;
            case "2" :
                genre = Genre.DRAMA;
                break;
            case "3" :
                genre = Genre.SPORT;
                break;
            case "4" :
                genre = Genre.FANTASY;
                break;
            case "5" :
                genre = Genre.ROMANCE;
                break;
            case "6" :
                genre = Genre.BIOGRAPHY;
                break;
            case "7" :
                genre = Genre.THRILLER;
                break;
            case "8" :
                genre = Genre.MYSTERY;
                break;
            case "9" :
                genre = Genre.MUSICAL;
                break;
            case "10" :
                genre = Genre.COMEDY;
                break;
            case "11" :
                genre = Genre.FAMILY;
                break;
            case "12" :
                genre = Genre.ACTION;
                break;
            case "13" :
                genre = Genre.ADVENTURE;
                break;
            case "14" :
                genre = Genre.HISTORY;
                break;
            default:
                UI.displayMessage("Please type a number to search for genre");
                genre = null;
                genreInput();
        }
        return genre;
    }


    // Tobias
    private void onClose() {

    }
}
