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



    private List<Genre> addGenreToGenreList(String[] genreStringList){

        List<Genre> parsedGenres = new ArrayList<Genre>();

        for (String genreString: genreStringList) {

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
        return parsedGenres;
        }



    // Tobias

    // TODO: 24-04-2023 User data mangler at blive loaded hvis det ikke gøres i UserSetup
    private void dataSetup () {

        // ############ USERS ##########################

        //String [] dataUser = io.readFilemData("")


        // ############ FILM OG SERIER DATA ############

        // Data fra IO (lister med String-elementer, der skal splittes)
        String[] dataFilm = io.readFilmData("data/film.csv");
        String[] dataSerier = io.readFilmData("data/serier.csv");


        // FILM

        int filmCounter = 0;

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


            this.media[filmCounter] = f;
            filmCounter++;

            }





    // SERIER
        int serieCounter = 0;


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
            List<Integer> episoderIHverSæson  = new ArrayList<Integer>();

            for (String sÆs : sæsoner ) {
                String[] sæsonOgTilhørendeAntalEpisoder = sÆs.split("-");

                episoderIHverSæson.add(Integer.parseInt(sæsonOgTilhørendeAntalEpisoder[1].trim()));


            }


            AMedia se = new Series(serieTitel, listOfGenresSerier, ratingSerie, "Serie", antalSæsoner, episoderIHverSæson, startÅr, slutÅr);


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
