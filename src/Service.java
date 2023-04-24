import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Service {
    private User currenUser;
    private List<User> users;
    private List<AMedia> media;




    // Lauritz
    public Service() {


    }



    // Tobias
    private void dataSetup() {


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

    private List<AMedia> searchByRating(){
        float rating = -1;
        while(rating == -1){
            try {
                rating = Float.parseFloat(UI.getInput("Type the minimum rating you are looking for with a . as your desimal point"));
            } catch (Exception e) {
                UI.displayMassage("your input was not a desimal number");
            }
        }
        List<AMedia> rating = new ArrayList<>();
        for (AMedia md : media) {
            if(md.getRating() >= rating){
                rating.add(md);
            }
        }
    }
}
