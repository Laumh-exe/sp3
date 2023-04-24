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
    private void dataSetup(String[] data, String typeOfData) {

        // Navne skal tilpasses her
        String[] dataFilm = io.readGameData("data/film.csv", 100);
        String[] dataSerier = io.readBoardData("src/carddata.csv",100);


        if (typeOfData.equals("film")) {
            int counter = 0;

            for (String s : data) {
                String[] line = s.split(";");

                String filmTitel = line[0].trim();

                int udgivelsesÅr = Integer.parseInt(line[1].trim());


                //Den skal splittes på komma. I nogle tilfælde har den to, tre eller fire genre
                String genres = line[2].trim();
                String[] genreLine = genres.split(",");

                String firstGenre = "";
                String secondGenre = "";
                String thirdGenre = "";
                String fourthGenre = "";

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


                int rating = Integer.parseInt(line[3].trim());


                AMedia f;

                if (genreLine.length == 1) {
                    f = new Movie(filmTitel, udgivelsesÅr, firstGenre, rating);
                } else if (genreLine.length == 2) {
                    f = new Movie(filmTitel, udgivelsesÅr, firstGenre, secondGenre, rating);

                } else if (genreLine.length == 3) {
                    f = new Movie(filmTitel, udgivelsesÅr, firstGenre, secondGenre, thirdGenre, rating);

                } else if (genreLine.length == 4) {
                    f = new Movie(filmTitel, udgivelsesÅr, firstGenre, secondGenre, thirdGenre, fourthGenre, rating);

                }


                this.media[counter] = f;
                counter++;
            }
        }


        else {
                int counter =0;

                for(String s : data){
                    String[] line = s.split(";");

                    String serieTitel = line[0].trim();

                    int udgivelsesÅr = Integer.parseInt(line[1].trim());


                    //Den skal splittes på komma. I nogle tilfælde har den to, tre eller fire genre
                    String genres = line[2].trim();
                    String[] genreLine = genres.split(",");

                    String firstGenre = "";
                    String secondGenre = "";
                    String thirdGenre = "";
                    String fourthGenre = "";

                    if(genreLine.length == 1) {
                        firstGenre = genreLine[0].trim();
                    } else if (genreLine.length ==2) {
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

                    String sæsonerOgAntalEpisoder = line[4].trim();



                    String[] sæsoner = sæsonerOgAntalEpisoder.split(",");

                    int antalSæsoner = sæsoner.length;



                    String[] sæsonOgTilhørendeAntalEpisoder = s



                    int rating = Integer.parseInt(genreLine[3].trim());



                    AMedia se;

                    if(genreLine.length == 1) {
                        f = new Series(serieTitel, udgivelsesÅr, firstGenre, rating);
                    } else if (genreLine.length ==2) {
                        f = new Series(serieTitel, udgivelsesÅr, firstGenre, secondGenre, rating);

                    } else if (genreLine.length == 3) {
                        f = new Series(serieTitel, udgivelsesÅr, firstGenre, secondGenre, thirdGenre, rating);

                    } else if (genreLine.length == 4) {
                        f = new Series(serieTitel, udgivelsesÅr, firstGenre, secondGenre, thirdGenre, fourthGenre, rating);

                    }


                    this.media[counter] = se;
                    counter++;
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
