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

            switch (genreString.replaceFirst("\\W*", "")) {
                case "Drama":
                    parsedGenres.add(Genre.DRAMA);
                    break;
                case "Mystery":
                    parsedGenres.add(Genre.MYSTERY);
                    break;
                case "Crime":
                    parsedGenres.add(Genre.CRIME);
                    break;
                case "History":
                    parsedGenres.add(Genre.HISTORY);
                    break;
                case "Biography":
                    parsedGenres.add(Genre.BIOGRAPHY);
                    break;
                case "Romance":
                    parsedGenres.add(Genre.ROMANCE);
                    break;
                case "War":
                    parsedGenres.add(Genre.WAR);
                    break;
                case "Sport":
                    parsedGenres.add(Genre.SPORT);
                    break;
                case "Adventure":
                    parsedGenres.add(Genre.ADVENTURE);
                    break;
                case "Fantasy":
                    parsedGenres.add(Genre.FANTASY);
                    break;
                case "Thriller":
                    parsedGenres.add(Genre.THRILLER);
                    break;
                case "Musical":
                    parsedGenres.add(Genre.MUSICAL);
                    break;
                case "Family":
                    parsedGenres.add(Genre.FAMILY);
                    break;
                case "Music":
                    parsedGenres.add(Genre.MUSIC);
                    break;
                case "Action":
                    parsedGenres.add(Genre.ACTION);
                    break;
                case "Comedy":
                    parsedGenres.add(Genre.COMEDY);
                    break;
                case "Film-Noir":
                    parsedGenres.add(Genre.FILMNOIR);
                    break;
                case "Sci-fi":
                    parsedGenres.add(Genre.SCIFI);
                    break;
                case "Western":
                    parsedGenres.add(Genre.WESTERN);
                    break;
                case "Horror":
                    parsedGenres.add(Genre.HORROR);
                    break;
            }

        }
        return parsedGenres;
    }


    // Tobias

    // TODO: 24-04-2023 User data mangler at blive loaded hvis det ikke gøres i UserSetup
    private void dataSetup() {


        // ############ FILM OG SERIER DATA ############

        // Data fra IO (lister med String-elementer, der skal splittes)
        List<String> dataFilm = io.getData("data/film.csv");
        List<String> dataSerier = io.getData("data/serier.csv");


        // FILM

        //int filmCounter = 0;

        for (String sF : dataFilm) {


            String[] line = sF.split(";");


            // TITEL
            String filmTitel = line[0].trim();

            // UDGIVELSESÅR
            int udgivelsesÅr = Integer.parseInt(line[1].trim());


            // GENRE
            String genres = line[2].trim();

            String[] genreLine = genres.split(",");

            List<Genre> listOfGenres = addGenreToGenreList(genreLine);


            // RATING
            int rating = Integer.parseInt(line[3].trim());


            AMedia f = new Movie(filmTitel, listOfGenres, rating, "Film", udgivelsesÅr);

            this.media.add(f);


        }


        // SERIER

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


        // ############ USERS ##########################

        List<String> dataUser = io.getData("data/user_data.csv");

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


        // Lauritz
        private void userSetup () {
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

    private void createUser(){

        String username = ui.getInput("Enter a username: ");

        for (User u: users){

            if (u.getName().equals(username)){

                ui.displayMessage("This username is already in use. Please select another username");
                createUser();
                return;

            }

            else {

                String password = ui.getInput("Enter a password: ");
                User currentUser = new User(username, password); //TODO make sure password and username are arguments in user class, and in the same order!
                users.add(currentUser);

            }

        }




    }

    // Lauritz
    private void mainMenu() {
        String input = ui.getInput("Welcome " + currentUser.getName() + "\n" +
                "Please choose one of the following options or type Q to quit:\n" +
                "1) Search for a movie or show by title\n" +
                "2) Search for a movie or show by genre\n" +
                "3) Search for a movie or show by rating\n" +
                "4) Search for a movie or show by release date\n" +
                "5) Show your already-seen list\n" +
                "6) Show watchlist");

        // Get input and execute accordingly
        do { // Do while(true)
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
        AMedia foundMedia = getTitleInput(ui.getInput("Please type in the name of a movie or show"));
        if (foundMedia != null) {
            String input = ui.getInput("Please choose one of the following options\n" +
                    "1) Add to watchlist" +
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
        } else {
            ui.displayMessage("The title did not match any existing titles");
        }
    }


    // Tobias

    private void enterPassword(User u) {

        String password = ui.getInput("Enter your password: ");


        if (u.comparePassword(password) == true) {

            this.currentUser = u;

            mainMenu();


        }

        else {

            ui.displayMessage("You entered the wrong code. Try again");
            enterPassword(u);


        }

    }
    private void login() {


        String username = ui.getInput("Enter your username: ");

        for (User u: users) {

            if (u.getName().equals(username)) {
                enterPassword(u);

            }
            else {
                ui.displayMessage("I can't find that user. Try again");
                login();

            }
        }
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
        //List all genres so user can choose one with a number
        String input = ui.getInput("What genre do you want to search for?" +
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
                "14) History\n" +
                "15) War\n" +
                "16) Sci-Fi\n" +
                "17) Film-Noir\n" +
                "18) Western\n" +
                "19) Horror\n" +
                "20) Music\n");
        // TODO: Maybe more genres??

        Genre genre;
        switch (input) {
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
                ui.displayMessage("Please type a number to search for genre");
                genre = null;
                genreInput();
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
}
