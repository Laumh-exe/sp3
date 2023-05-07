import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import media.AMedia;
import media.Genre;
import media.ISavable;
import media.Movie;
import media.Series;


public class Service {
    private User currentUser;
    
    private List<User> users;
    private List<AMedia> media;
    
    private final IO io = new IO();
    private final UI ui = new UI();

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
        if(userSetup()){
            // Main menu - handles other methods
            mainMenu();
        }
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

    private void dataSetup() throws FileNotFoundException { //Todo handle this exception


        List<String> testing = io.refactoringUserDatafromDB();

        for (String test: testing) {
            System.out.println(test);
        }

        // ############ FILM OG SERIER DATA ############

        // Data fra IO (lister med String-elementer, der skal splittes)

        //List<String> dataFilm = io.getData("data/film.csv");
        List<String> dataFilm = io.readMovieDataFromDB("SELECT * FROM movies " +
                "JOIN movie_genre ON movies.ID = movie_genre.movieid" +
                " JOIN genres ON movie_genre.genreID = genres.id");






        //List<String> dataSerier = io.getData("data/serier.csv");
        List<String> dataSerier = io.readSeriesDataFromDB("SELECT * FROM series " +
                "JOIN series_genre ON series.ID = series_genre.seriesid " +
                "JOIN genres ON series_genre.genreID = genres.id " +
                "JOIN series_seasons ON series_seasons.seriesID = series.ID");


        //List<String> dataUser = io.getData("data/userdata.csv");

        List<String> dataUser = io.readUserDatafromDB();

        //System.out.println(dataFilm);

        /*
        for (String da : dataFilm){
            System.out.println(da);
        }

         */

/*
        for (String dat : dataSerier){
            System.out.println(dat);
        }

 */





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
            String userName = dataUserline[0].trim();
            String password = dataUserline[1].trim();
            User loadedUser = new User(userName, password);

            if(!(dataUserline.length > 2)){
                users.add(loadedUser);
                continue;
            }
            String watchList = dataUserline[2].trim();
            String[] titlesInWatchList = watchList.split(":");
            for (String titel : titlesInWatchList) {
                for (AMedia aMedia : media) {
                    if (titel.equals(aMedia.getTitle())) {
                        loadedUser.addToWatchList(aMedia);
                    }
                }
            }
            
            if(!(dataUserline.length > 3)){
                users.add(loadedUser);
                continue;
            }
            String watchedMedia = dataUserline[3].trim();
            String[] titlesInWatchedMedia = watchedMedia.split(":");

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
            String serieTitel = lineSerier[0].trim();
            String startOgSlutÅrstal = lineSerier[1].trim();
            String[] splitAfStartOgSlut = startOgSlutÅrstal.split("-");
            int startYear = Integer.parseInt(splitAfStartOgSlut[0]);
            int endYear = -1;

            if(splitAfStartOgSlut.length > 1){
                endYear = Integer.parseInt(splitAfStartOgSlut[1]);
            }
            String genresSerie = lineSerier[2].trim();
            String[] genreLineSerie = genresSerie.split(",");

            List<Genre> listOfGenresSerier = addGenreToGenreList(genreLineSerie);
            float rating = Float.parseFloat(lineSerier[3].trim().replace(',', '.'));

            String seasonsAndAmoutOfEpisodes = lineSerier[4].trim();
            String[] seasons = seasonsAndAmoutOfEpisodes.split(",");
            int numberOfSeasons = seasons.length;

            List<Integer> episodesInEachSeason = new ArrayList<Integer>();

            for (String season : seasons) {
                String[] seasonsAndItsNumberOfEpisodes = season.split("-");

                episodesInEachSeason.add(Integer.parseInt(seasonsAndItsNumberOfEpisodes[1].trim()));
            }

            AMedia series = new Series(serieTitel, listOfGenresSerier, rating, "Serie", numberOfSeasons, episodesInEachSeason, startYear, endYear);
            media.add(series);
        }
    }

    private void formatMoviesDataFromString(List<String> filmData) {
        for (String aFilm : filmData) {


            String[] line = aFilm.split(";");

            String filmTitel = line[0].replaceFirst("\\W*", "");
            int reliceYear = Integer.parseInt(line[1].trim());
            String genres = line[2].trim();
            String[] genreLine = genres.split(",");
            List<Genre> listOfGenres = addGenreToGenreList(genreLine);
            float rating = Float.parseFloat(line[3].trim().replace(',', '.'));
            AMedia movie = new Movie(filmTitel, listOfGenres, rating, "Film", reliceYear);

            media.add(movie);
        }
    }

    // Lauritz
    private boolean userSetup() {
        if(users.size() > 0){
            String input = ui.getInput("1) Login\n" + "2) Create new user\nOr Q to exit");
            // Asks: Login og Create user
            if (input.equalsIgnoreCase("1")) {
                login();
                return true;
            }
            // When creating new User
            else if (input.equalsIgnoreCase("2")) {
                createUser();
                return true;
            }
            else if (input.equalsIgnoreCase("q")){
                return false;
            }
            // If something went wrong - maybe exception
            else {
                ui.displayMessage("Please type either 1 or 2 and the press enter");
                return userSetup();
            }
        } else {
            ui.displayMessage("There are no users found please create a new one");
            createUser();
            return true;
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
        ui.displayMessage("Welcome " + currentUser.getName());
        do { // Do while(true)
            String input = ui.getInput("Please choose one of the following options or type Q to quit:\n" +
                    "1) Search for a movie or show by title\n" +
                    "2) Search for a movie or show by genre\n" +
                    "3) Search for a movie or show by rating\n" +
                    "4) Show your already-seen list\n" +
                    "5) Show watchlist");

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
                    Collection<AMedia> genreResult = searchByGenre();
                    printMediaTitles(genreResult);
                    // Show options and make choice
                    makeChoice(genreResult);
                    break;
                case "3":
                    // Search and display the returned collection
                    Collection<AMedia> ratingResult = searchByRating();
                    printMediaTitles(ratingResult);
                    // Show options and make choice
                    makeChoice(ratingResult);
                    break;
                case "4":
                    ui.displayMessage(currentUser.watchedMediaToString());
                    makeChoice(currentUser.getWatchedMedia());
                    break;
                case "5":
                    ui.displayMessage(currentUser.watchListToString());
                    makeChoice(currentUser.getWatchList());
                    break;
                case "q":
                case "Q":
                    return;
                default:
                    ui.displayMessage("Something went wrong in main menu");
                    break;
            }
        }
        while (true);
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

    private void makeChoice(Collection<AMedia> media) {
        if(media.size() == 0) {
            ui.displayMessage("No media was found");
            return;
        }
        if (media.size() == 1) {
            addOrWatchMedia(media.iterator().next());
        } else {
            // Ask if user wants to watch or add to watchlist
            AMedia foundMedia = getTitleInput(ui.getInput("Please choose one of the movies/shows"), media);
            if (foundMedia != null) {
                addOrWatchMedia(foundMedia);
            } else {
                ui.displayMessage("The title did not match any existing titles");
            }
        }
    }

    private void login() {
        
        
        String username = ui.getInput("Enter your username: ");
        
        for (User u : users) {

            if (u.getName().equals(username)) {
                enterPassword(u);
                return;
            }
        }
        ui.displayMessage("I can't find that user. Try again");
        login();
    }
    
    private void enterPassword(User loginUser) {

        String password = ui.getInput("Enter your password: ");

        if (loginUser.comparePassword(password) == true) {
            currentUser = loginUser;
        } else {
            ui.displayMessage("You entered the wrong code. Try again");
            enterPassword(loginUser);
        }

    }

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

    private AMedia getTitleInput(String title, Collection<AMedia> media) {
        // Compare title to the titles of the search
        for (AMedia m : media) {
            if (m.getTitle().toLowerCase().contains(title.toLowerCase())) {
                return m;
            }
        }
        return null;
    }

    private void onClose() {
        io.saveData("data/userdata.csv", users);
        io.saveToDB(new ArrayList<ISavable>(media)); // |
        io.saveToDB(new ArrayList<ISavable>(users)); // | Thies 2 culd be made into one saveToDB() call by first merging them into one ISavable list

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
