import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Service {
    private User currenUser;
    private List<User> users;
    private List<AMedia> media;

    private IO io = new IO();




    // Lauritz
    public Service() {


    }





    // Tobias
    private void dataSetup() {

        // Navne skal tilpasses her
        String[] dataFilm = io.readFilmData("data/film.csv");
        String[] dataSerier = io.readFilmData("data/serier.csv");


       // FILM

        int filmCounter = 0;

        for (String sF : dataFilm) {
            String[] line = sF.split(";");

            String filmTitel = line[0].trim();

            int udgivelsesÅr = Integer.parseInt(line[1].trim());


            //Den skal splittes på komma. I nogle tilfælde har den to, tre eller fire genre
            String genres = line[2].trim();
            String[] genreLine = genres.split(",");

            List<Genre> parsedGenres = new ArrayList<>();

            for (String genreString: genreLine) {
                switch (genreString){
                    case "Drama":
                        parsedGenres.add(Genre.DRAMA);
                        break;
                    case "Mystery":
                        parsedGenres.add(Genre.DRAMA);
                        break;
                    case "":
                        parsedGenres.add(Genre.DRAMA);
                        break;
                    case "Drama":
                        parsedGenres.add(Genre.DRAMA);
                        break;
                    case "Drama":
                        parsedGenres.add(Genre.DRAMA);
                        break;
                    case "Drama":
                        parsedGenres.add(Genre.DRAMA);
                        break;
                    case "Drama":
                        parsedGenres.add(Genre.DRAMA);
                        break;
                    case "Drama":
                        parsedGenres.add(Genre.DRAMA);
                        break;
                }
            }

            /*
            if (genreLine.length == 1) {
                firstGenre = genreLine[0].trim();


            } else if (genreLine.length == 2) {
                firstGenre = genreLine[0].trim();
                secondGenre = genreLine[1].trim();

            } else if (genreLine.length == 3) {
                firstGenre = genreLine[0].trim();
                secondGenre = genreLine[1].trim();
                thirdGenre = genreLine[2].trim();

            } else if (genreLine.length == 4) {
                firstGenre = genreLine[0].trim();
                secondGenre = genreLine[1].trim();
                thirdGenre = genreLine[2].trim();
                fourthGenre = genreLine[3].trim();

            }

             */


            int rating = Integer.parseInt(line[3].trim());


            AMedia f = new Movie(filmTitel, udgivelsesÅr, Genre, rating);


           /* } else if (genreLine.length == 2) {
                f = new Movie(filmTitel, udgivelsesÅr, firstGenre, secondGenre, rating);

            } else if (genreLine.length == 3) {
                f = new Movie(filmTitel, udgivelsesÅr, firstGenre, secondGenre, thirdGenre, rating);

            } else if (genreLine.length == 4) {
                f = new Movie(filmTitel, udgivelsesÅr, firstGenre, secondGenre, thirdGenre, fourthGenre, rating);

            }

            */


            this.media[filmCounter] = f;
            filmCounter++;






    // SERIER
        int serieCounter =0;


        for(String sS : dataSerier){
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

            // TODO: 24-04-2023 sørg for alle genre er en case
            List<Genre> parsedGenresSeries = new ArrayList<Genre>();

            for (String genreString: genreLineSerie) {
                switch (genreString){
                    case "Drama":
                        parsedGenresSeries.add(Genre.DRAMA);
                        break;
                    case "Mystery":
                        parsedGenresSeries.add(Genre.DRAMA);
                        break;
                    case "Crime":
                        parsedGenresSeries.add(Genre.DRAMA);
                        break;
                    case "History":
                        parsedGenresSeries.add(Genre.DRAMA);
                        break;
                    case "Biography":
                        parsedGenresSeries.add(Genre.DRAMA);
                        break;
                    case "Romance":
                        parsedGenresSeries.add(Genre.DRAMA);
                        break;
                    case "War":
                        parsedGenresSeries.add(Genre.DRAMA);
                        break;
                    case "Sport":
                        parsedGenresSeries.add(Genre.DRAMA);
                        break;
                    case "Adventure":
                        parsedGenresSeries.add(Genre.DRAMA);
                        break;
                    case "Fantasy":
                        parsedGenresSeries.add(Genre.DRAMA);
                        break;
                    case "War":
                        parsedGenresSeries.add(Genre.DRAMA);
                        break;
                    case "War":
                        parsedGenresSeries.add(Genre.DRAMA);
                        break;
                    case "War":
                        parsedGenresSeries.add(Genre.DRAMA);
                        break;
                    case "War":
                        parsedGenresSeries.add(Genre.DRAMA);
                        break;
                }
            }


            //RATING
            int ratingSerie = Integer.parseInt(lineSerier[3].trim());


            // SÆSONER
            String sæsonerOgAntalEpisoder = lineSerier[4].trim();


            String[] sæsoner = sæsonerOgAntalEpisoder.split(",");

            // Antal sæsoner
            int antalSæsoner = sæsoner.length;


            // Liste hvor hvert element er et tal, der angiver antal episoder i en sæson. Første element i listen
            // er antal episoder i første sæson osv.
            List<Integer> episoderIHverSæson  = new ArrayList<Integer>();

            for (String sÆs : sæsoner )
            {
                String[] sæsonOgTilhørendeAntalEpisoder = sÆs.split("-");

                episoderIHverSæson.add(Integer.parseInt(sæsonOgTilhørendeAntalEpisoder[1].trim()));


            }


            AMedia se = new Series(serieTitel, Genre, ratingSerie, "Serie", antalSæsoner, episoderIHverSæson, startÅr, slutÅr);


            this.media[serieCounter] = se;
            serieCounter++;
        }

    }





    // Lauritz
    private void userSetup() {

        // Asks: Login og Create user
        // Execute methods accordingly: Login or Create user

    }



    // Lauritz
    private void mainMenu() {

        /* kalder:

        dataSetup()
        userSetup()
            viser:
            Hovedmenu (De 4 valgmuligheder brugeren har når han/hun/hen er logget ind eller har oprettet
            en bruger)

            1) Søg efter medie
            2) Søg efter alle medier i en kategori
            3) Se sete medier
            4) Se gemte medier

         */


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
        //return list


    }

    //Lauritz
    private list<AMedia> searchByGenre(Genre genre) {
        //return list
    }




    // Tobias
    private void onClose() {

    }
}
