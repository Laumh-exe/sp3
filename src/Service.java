import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import media.AMedia;
import media.Genre;
import media.Movie;
import media.Series;


public class Service {
    private User currentUser;
    
    private List<User> users;
    private List<AMedia> media;
    
    private final IO io = new IO();
    private final UI ui = new UI();

    // Lauritz
    public Service() {
        users = new ArrayList<>();
        media = new ArrayList<>();

        // Setup data
        try {
            dataSetup();
        } catch (FileNotFoundException e) {
            // TODO this shuld be removed when Issue #20 is done
            e.printStackTrace();
        }
        // Setup user and run main menu
        userSetup();
        // Main menu - handles other methods
        mainMenu();
        // Closes when mainMenu is exited
        onClose();
    }


    private List<Genre> addGenreToGenreList(String[] genreStringList) {

        List<Genre> parsedGenres = new ArrayList<Genre>();

        for (String genreString : genreStringList) {
            Genre genre = gerneParser(genreString);
            if (genre == null) {
                //todo: handle this case
            }
            parsedGenres.add(genre);
        }
        return parsedGenres;
    }


    // Tobias

    private Genre gerneParser(String genreString) {
        switch (genreString.replaceFirst("\\W*", "")) {
            case "Drama":
                return Genre.DRAMA;
            case "Mystery":
                return Genre.MYSTERY;
            case "Crime":
                return Genre.CRIME;
            case "History":
                return Genre.HISTORY;
            case "Biography":
                return Genre.BIOGRAPHY;
            case "Romance":
                return Genre.ROMANCE;
            case "War":
                return Genre.WAR;
            case "Sport":
                return Genre.SPORT;
            case "Adventure":
                return Genre.ADVENTURE;
            case "Fantasy":
                return Genre.FANTASY;
            case "Thriller":
                return Genre.THRILLER;
            case "Musical":
                return Genre.MUSICAL;
            case "Family":
                return Genre.FAMILY;
            case "Music":
                return Genre.MUSIC;
            case "Action":
                return Genre.ACTION;
            case "Comedy":
                return Genre.COMEDY;
            case "Film-Noir":
                return Genre.FILMNOIR;
            case "Sci-fi":
                return Genre.SCIFI;
            case "Western":
                return Genre.WESTERN;
            case "Horror":
                return Genre.HORROR;
        }
        return null;
    }


    // TODO: 24-04-2023 User data mangler at blive loaded hvis det ikke gøres i UserSetup
    private void dataSetup() throws FileNotFoundException { //Todo handle this exception

        // ############ FILM OG SERIER DATA ############

        // Data fra IO (lister med String-elementer, der skal splittes)
        List<String> dataFilm = io.getData("data/film.csv");
        List<String> dataSerier = io.getData("data/serier.csv");
        List<String> dataUser = io.getData("data/userdata.csv");
        // FILM
        formatMoviesDataFromString(dataFilm);
        // SERIER
        formatSeriesDataFromString(dataSerier);
        // ############ USERS ##########################
        formatUsersFromData(dataUser);
    }

    private void formatUsersFromData(List<String> dataUser) {
        for (String userAsString : dataUser) {


            String[] dataUserline = userAsString.split(";");

            //navn semi paswoord semi, watchlist, semi watcHED

            // BRUGERNAVN
            String userName = dataUserline[0].trim();

            // KODE
            String password = dataUserline[1].trim();
            User loadedUser = new User(userName, password);

            // GEMTE FILM (WatchList) MEDIETITEL
            if(!(dataUserline.length > 2)){
                users.add(loadedUser);
                return;
            }
            String watchList = dataUserline[2].trim();
            String[] titlesInWatchList = watchList.split(",");
            for (String titel : titlesInWatchList) {
                for (AMedia aMedia : media) {
                    if (titel.equals(aMedia.getTitle())) {
                        loadedUser.addToWatchList(aMedia);
                    }
                }
            }
            
            if(!(dataUserline.length > 3)){
                users.add(loadedUser);
                return;
            }
            // SETE FILM (WatchedList) MEDIETITEL
            String watchedMedia = dataUserline[3].trim();
            String[] titlesInWatchedMedia = watchedMedia.split(",");

            for (String titel : titlesInWatchedMedia) {
                for (AMedia aMedia : this.media) {
                    if (titel.equals(aMedia.getTitle())) {
                        loadedUser.addToWatchedMedia(aMedia);
                    }
                }
            }
            users.add(loadedUser);
        }
    }

    private void formatSeriesDataFromString(List<String> dataSerier) {
        for (String sS : dataSerier) {

            String[] lineSerier = sS.split(";");

            // TITEL
            String serieTitel = lineSerier[0].trim();


            // START- OG SLUTÅRSTAL
            String startOgSlutÅrstal = lineSerier[1].trim();

            String[] splitAfStartOgSlut = startOgSlutÅrstal.split("-");

            int startÅr = Integer.parseInt(splitAfStartOgSlut[0]);
            int slutÅr = -1;
            if(splitAfStartOgSlut.length > 1){
                slutÅr = Integer.parseInt(splitAfStartOgSlut[1]);
            }


            // GENRE
            String genresSerie = lineSerier[2].trim();

            String[] genreLineSerie = genresSerie.split(",");

            List<Genre> listOfGenresSerier = addGenreToGenreList(genreLineSerie);


            //RATING
            float rating = Float.parseFloat(lineSerier[3].trim().replace(',', '.'));


            // SÆSONER
            String sæsonerOgAntalEpisoder = lineSerier[4].trim();

            String[] sæsoner = sæsonerOgAntalEpisoder.split(",");


            // Antal sæsoner
            int antalSæsoner = sæsoner.length;


            /* Liste hvor hvert element er et tal, der angiver antal episoder i en sæson. Første element i listen
             er antal episoder i første sæson osv.
             */
            List<Integer> episoderIHverSæson = new ArrayList<Integer>();

            for (String sÆs : sæsoner) {
                String[] sæsonOgTilhørendeAntalEpisoder = sÆs.split("-");

                episoderIHverSæson.add(Integer.parseInt(sæsonOgTilhørendeAntalEpisoder[1].trim()));


            }


            AMedia se = new Series(serieTitel, listOfGenresSerier, rating, "Serie", antalSæsoner, episoderIHverSæson, startÅr, slutÅr);


            this.media.add(se);
            //this.media[serieCounter] = se;
            //serieCounter++;
        }
    }

    private void formatMoviesDataFromString(List<String> dataFilm) {
        for (String sF : dataFilm) {


            String[] line = sF.split(";");


            // TITEL
            String filmTitel = line[0].replaceFirst("\\W*", "");

            // UDGIVELSESÅR
            int udgivelsesÅr = Integer.parseInt(line[1].trim());


            // GENRE
            String genres = line[2].trim();

            String[] genreLine = genres.split(",");

            List<Genre> listOfGenres = addGenreToGenreList(genreLine);


            // RATING
            float rating = Float.parseFloat(line[3].trim().replace(',', '.'));


            AMedia f = new Movie(filmTitel, listOfGenres, rating, "Film", udgivelsesÅr);

            media.add(f);


        }
    }

    // Lauritz
    private void userSetup() {
        //TODO: hanle if no users exsits
        String input = ui.getInput("1) Login\n" + "2) Create new user");
        // Asks: Login og Create user
        if (input.equalsIgnoreCase("1")) {
            login();
        }
        // When creating new User
        else if (input.equalsIgnoreCase("2")) {
            createUser();
        }
        // If something went wrong - maybe exception
        else {
            ui.displayMessage("Please type either 1 or 2 and the press enter");
            userSetup();
        }
    }

    private void createUser() {

        String username = ui.getInput("Enter a username: ");

        for (User u : users) {

            if (u.getName().equals(username)) {

                ui.displayMessage("This username is already in use. Please select another username");
                createUser();
                return;

            }
        }
        String password = ui.getInput("Enter a password: ");
        currentUser = new User(username, password); //TODO make sure password and username are arguments in user class, and in the same order!
        users.add(currentUser);
    }

    // Lauritz
    private void mainMenu() {
        do { // Do while(true)
            String input = ui.getInput("Welcome " + currentUser.getName() + "\n" +
                    "Please choose one of the following options or type Q to quit:\n" +
                    "1) Search for a movie or show by title\n" +
                    "2) Search for a movie or show by genre\n" +
                    "3) Search for a movie or show by rating\n" +
                    "4) Search for a movie or show by release date\n" +
                    "5) Show your already-seen list\n" +
                    "6) Show watchlist");

            // Get input and execute accordingly
            switch (input) {
                case "1":
                    // Search and display the returned collection
                    HashSet<AMedia> foundMedia = searchMedia();
                    // Show options and make choice
                    if (foundMedia.size() > 0) {
                        makeChoice(foundMedia);
                    }
                    else {
                        ui.displayMessage("You search had no results");
                    }

                    break;
                case "2":
                    // Search and display the returned collection
                    printMediaTitles(searchByGenre());
                    // Show options and make choice
                    makeChoice(searchByGenre());
                    break;
                case "3":
                    // Search and display the returned collection
                    printMediaTitles(searchByRating());
                    // Show options and make choice
                    makeChoice(searchByRating());
                case "4":
                    // Search and display the returned collection
                    //ui.displayMessage(searchByReleaseDate().toString());
                    // Show options and make choice
                    // makeChoice(searchByReleaseDate());
                case "5":
                    ui.displayMessage(currentUser.getWatchedMedia().toString());
                    makeChoice(currentUser.getWatchedMedia());
                    break;
                case "6":
                    ui.displayMessage(currentUser.getWatchList().toString());
                    makeChoice(currentUser.getWatchList());
                case "q":
                case "Q":
                    return;
                default:
                    ui.displayMessage("Something went wrong in main menu");
                    return;
            }
        }
        while (true);
    }


    //Lauritz by choice

    private void showMedia() {

    }



    private void addOrWatchMedia (AMedia media) {

        String input = ui.getInput("Please choose one of the following options\n" +
                "1) Add to watchlist\n" +
                "2) Watch movie");

        switch (input) {
            case "1":
                ui.displayMessage("Adding " + media.getTitle() + " to watchlist");
                currentUser.addToWatchList(media);
                break;
            case "2":
                ui.displayMessage("Watching " + media.getTitle());
                currentUser.addToWatchedMedia(media);
        }
    }

    //Lauritz by choice

    private void makeChoice(Collection<AMedia> media) {

        if (media.size() < 2) {

            addOrWatchMedia(media.iterator().next());

        } else {


            // Ask if user wants to watch or add to watchlist
            AMedia foundMedia = getTitleInput(ui.getInput("Please choose one of the movies/shows"));

            if (foundMedia != null) {

                addOrWatchMedia(foundMedia);


                /*String input = ui.getInput("Please choose one of the following options\n" +
                        "1) Add to watchlist\n" +
                        "2) Watch movie");
                switch (input) {
                    case "1":
                        ui.displayMessage("Adding " + foundMedia.getTitle() + " to watchlist");
                        currentUser.addToWatchList(foundMedia);
                        break;
                    case "2":
                        ui.displayMessage("Watching " + foundMedia.getTitle());
                        currentUser.addToWatchedMedia(foundMedia);
                }

                 */
            } else {
                ui.displayMessage("The title did not match any existing titles");
            }
        }
    }

    // Tobias

    private void enterPassword(User u) {

        String password = ui.getInput("Enter your password: ");


        if (u.comparePassword(password) == true) {

            this.currentUser = u;

            mainMenu();


        } else {

            ui.displayMessage("You entered the wrong code. Try again");
            enterPassword(u);


        }

    }

    private void login() {


        String username = ui.getInput("Enter your username: ");

        for (User u : users) {

            if (u.getName().equals(username)) {
                enterPassword(u);

            } else {
                ui.displayMessage("I can't find that user. Try again");
                login();

            }
        }
    }


    // Lauritz

    private HashSet<AMedia> searchMedia() {
        String title = ui.getInput("Search for title").toLowerCase();
        HashSet<AMedia> searchResult = new HashSet<>();

        for (AMedia m : media) {
            try {
                if (m.getTitle().toLowerCase().contains(title)) {
                    searchResult.add(m);
                }
            }
            catch (NullPointerException e) {
                continue;
            }
        }
        printMediaTitles(searchResult);
        return searchResult;
    }

    //Lauritz
    private Collection<AMedia> searchByGenre() {
        Genre genre = genreInput();
        Collection<AMedia> searchResult = new HashSet<AMedia>();
        for (AMedia m : media) {
            for (Genre g : m.getGenres()) {
                if (genre == g) {
                    searchResult.add(m);
                    break;
                }
            }
        }
        return searchResult;
    }

    private Genre genreInput() {
        Genre genre = null;
        while (genre == null) {
            //List all genres so user can choose one with a number
            String input = ui.getInput("What genre do you want to search for?\n" +
                    "Crime\n" +
                    "Drama\n" +
                    "Sport\n" +
                    "Fantasy\n" +
                    "Romance\n" +
                    "Biography\n" +
                    "Thriller\n" +
                    "Mystery\n" +
                    "Musical\n" +
                    "Comedy\n" +
                    "Family\n" +
                    "Action\n" +
                    "Adventure\n" +
                    "History\n" +
                    "War\n" +
                    "Sci-Fi\n" +
                    "Film-Noir\n" +
                    "Western\n" +
                    "Horror\n" +
                    "Music\n");
            // TODO: Maybe more genres??

            genre = gerneParser(input);
            if (genre == null) {
                ui.displayMessage("Please type a number to search for genre");
            }
        }
        return genre;
    }

    private AMedia getTitleInput(String title) {
        // Compare title to the titles of the search
        for (AMedia m : media) {
            if (m.getTitle().toLowerCase().contains(title.toLowerCase())) {
                return m;
            }
        }
        return null;
    }


    // Tobias
    private void onClose() {
        io.saveData("data/userdata.csv", users);
        ui.displayMessage("Program is closing, goodbye");
    }

    private List<AMedia> searchByRating() {
        float rating = -1;
        while (rating == -1) {
            try {
                rating = Float.parseFloat(ui.getInput("Type the minimum rating you are looking for with a . as your decimal point"));
            } catch (Exception e) {
                ui.displayMessage("your input was not a decimal number");
            }
        }
        List<AMedia> ratings = new ArrayList<>();
        for (AMedia md : media) {
            if (md.getRating() >= rating) {
                ratings.add(md);
            }
        }
        return ratings;
    }

    private void printMediaTitles(Collection<AMedia> media) {
        for(AMedia m : media) {
            ui.displayMessage(m.getTitle());
        }
    }
}
