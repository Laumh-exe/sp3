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
    private UI ui;

    private List<User> users;
    private List<AMedia> media;

    private IO io = new IO();

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


    private List<Genre> addGenreToGenreList(String[] genreStringList) {

        List<Genre> parsedGenres = new ArrayList<Genre>();

        for (String genreString : genreStringList) {
            Genre genre = gerneParser(genreString);
            if(genre == null){
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
    private void dataSetup() {

        // ############ FILM OG SERIER DATA ############

        // Data fra IO (lister med String-elementer, der skal splittes)
        List<String> dataFilm = io.getData("data/film.csv");
        List<String> dataSerier = io.getData("data/serier.csv");
        List<String> dataUser = io.getData("data/user_data.csv");
        // FILM
        formatMoviesDataFromString(dataFilm);
        // SERIER
        formatSeriesDataFromString(dataSerier);
        // ############ USERS ##########################
        formatUsersFromData(dataUser);
    }

    private void formatUsersFromData(List<String> dataUser) {
        for (String dU : dataUser) {


            String[] dataUserline = dU.split(";");

            //navn semi paswoord semi, watchlist, semi watcHED

            // BRUGERNAVN
            String userName = dataUserline[0].trim();


            // KODE
            String password = dataUserline[1].trim();


            User loadedUser = new User(userName, password);

            // GEMTE FILM (WatchList) MEDIETITEL
            String watchList = dataUserline[2].trim();

            String[] titlesInWatchList = watchList.split(",");


            for (String ttW : titlesInWatchList) {

                for (AMedia aM : this.media) {

                    if (ttW.equals(aM.getTitle())) {

                        loadedUser.addToWatchList(aM);

                    }

                }


            }

            // SETE FILM (WatchedList) MEDIETITEL
            String watchedMedia = dataUserline[3].trim();

            String[] titlesInWatchedMedia = watchedMedia.split(",");


            for (String ttWM : titlesInWatchedMedia) {

                for (AMedia aMW : this.media) {

                    if (ttWM.equals(aMW.getTitle())) {

                        loadedUser.addToWatchedMedia(aMW);

                    }

                }
            }
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
            int slutÅr = Integer.parseInt(splitAfStartOgSlut[1]);


            // GENRE
            String genresSerie = lineSerier[2].trim();

            String[] genreLineSerie = genresSerie.split(",");

            List<Genre> listOfGenresSerier = addGenreToGenreList(genreLineSerie);


            //RATING
            int ratingSerie = Integer.parseInt(lineSerier[3].trim());


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


            AMedia se = new Series(serieTitel, listOfGenresSerier, ratingSerie, "Serie", antalSæsoner, episoderIHverSæson, startÅr, slutÅr);


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
            int rating = Integer.parseInt(line[3].trim());


            AMedia f = new Movie(filmTitel, listOfGenres, rating, "Film", udgivelsesÅr);

            media.add(f);


        }
    }

    // Lauritz
    private void userSetup () {
        String input = ui.getInput("1) Login\n" + "2) Create new user");
        // Asks: Login og Create user
        if (input.equalsIgnoreCase("1")) {
            String username = ui.getInput("Enter your username: ");
            String password = ui.getInput("Enter your password: ");
            // If login fails - if it doesnt, this method is over and login() has saved the currentUser
            if (!login(password, username)) { //TODO: username, password
                ui.displayMessage("I Can not find that user, try again");
                userSetup();
            }


        }
        // When creating new User
        else if (input.equalsIgnoreCase("2")) {
            String username = ui.getInput("Enter a username: ");
            String password = ui.getInput("Enter a password: ");
            User currentUser = new User(username, password); //TODO make sure password and username are arguments in user class, and in the same order!
            users.add(currentUser);
        }
        // If something went wrong - maybe exception
        else {
            ui.displayMessage("Please type either 1 or 2 and the press enter");

            userSetup();
        }
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
                    ui.displayMessage(searchMedia().toString());
                    // Show options and make choice
                    makeChoice(searchMedia());
                    break;
                case "2":
                    // Search and display the returned collection
                    ui.displayMessage(searchByGenre().toString());
                    // Show options and make choice
                    makeChoice(searchByGenre());
                    break;
                case "3":
                    // Search and display the returned collection
                    ui.displayMessage(searchByRating().toString());
                    // Show options and make choice
                    makeChoice(searchByRating());
                case "4":
                    // Search and display the returned collection
                    ui.displayMessage(searchByReleaseDate().toString());
                    // Show options and make choice
                    makeChoice(searchByReleaseDate());
                case "5":
                    ui.displayMessage(currentUser.getWatchedMedia().toString());
                    makeChoice(currentUser.getWatchedMedia());
                    break;
                case "6":
                    ui.displayMessage(currentUser.getWatchList().toString());
                    makeChoice(currentUser.getWatchList());
                case "q":
                    return; //TODO: exit message here or in onClose()
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

    //Lauritz by choice

    private void makeChoice(Collection media) {
        // Ask if user wants to watch or add to watchlist
        String input = ui.getInput("Please choose one of the following options\n" +
                "1) Add to watch-List" +
                "2) Watch movie");
        switch (input) {
            case "1":
                AMedia addMedia = getTitleInput(ui.getInput("Please type in the name of the movie or show you want to add"));
                if (addMedia != null) {
                    currentUser.addToWatchList(addMedia);
                } else {
                    ui.displayMessage("The title did not match any existing titles");
                }
                break;
            case "2":
                AMedia watchMedia = getTitleInput(ui.getInput("Please type in the name of the movie or show you want to watch"));
                if (watchMedia != null) {
                    currentUser.addToWatchedMedia(watchMedia);
                } else {
                    ui.displayMessage("The title did not match any existing titles");
                }
        }
    }


    // Tobias

    private boolean login(String username, String password) {
        return false;
    }


    // Lauritz

    private Collection<AMedia> searchMedia() {
        String title = ui.getInput("What title do you want to find?\n");
        Collection<AMedia> searchResult = new HashSet<AMedia>();
        for (AMedia m : media) {
            if (title.equalsIgnoreCase(m.getTitle())) {
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
        while (genre == null){
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
            if(genre == null){
                ui.displayMessage("Please type a number to search for genre");
            }
        }
        return genre;
    }

    private AMedia getTitleInput(String title) {
        // Compare title to the titles of the search
        for (AMedia m : media) {
            if (m.getTitle().equalsIgnoreCase(title)) {
                return m;
            }
        }
        return null;
    }


    // Tobias
    private void onClose() {
        io.saveData("data/userdata", users);
        ui.displayMessage("Program is closing, goodbye");
    }

    private List<AMedia> searchByRating(){
        float rating = -1;
        while(rating == -1){
            try {
                rating = Float.parseFloat(ui.getInput("Type the minimum rating you are looking for with a . as your decimal point"));
            } catch (Exception e) {
                ui.displayMessage("your input was not a decimal number");
            }
        }
        List<AMedia> ratings = new ArrayList<>();
        for (AMedia md : media) {
            if(md.getRating() >= rating){
                ratings.add(md);
            }
        }
        return ratings;
    }
}
