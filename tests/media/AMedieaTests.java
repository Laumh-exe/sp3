package tests.media;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import media.AMedia;
import media.Genre;
import media.Movie;
import media.Series;

public class AMedieaTests {

    @BeforeEach
    void setup(){}

    @Test
    public void movieToString(){
        //Arrange
        List tmp1 = new ArrayList<Genre>();
        tmp1.add(Genre.CRIME);
        tmp1.add(Genre.DRAMA);
        List tmp2 = new ArrayList<Integer>();
        tmp2.add(10);
        tmp2.add(12);
        AMedia media = new Movie("Movie", tmp1, 0, 2000);

        //Act
        String actual = media.toString();

        //Assert
        String expected = "Title: Movie\nRating: 0\nGenres: CRIME, DRAMA \nDate: 2000\n";
        assertEquals(expected, actual);
    }
    
    @Test
    public void seriesToString(){
        //Arrange
        List tmp1 = new ArrayList<Genre>();
        tmp1.add(Genre.CRIME);
        tmp1.add(Genre.DRAMA);
        List tmp2 = new ArrayList<Integer>();
        tmp2.add(10);
        tmp2.add(12);
        AMedia media = new Series("Series", tmp1, 0, 10, tmp2, 2000, 2001);

        //Act
        String actual = media.toString();

        //Assert
        String expected = "Title: Series\nRating: 0\nGenres: CRIME, DRAMA \nStart date: 2000 end date: 2001\nNumber of seasons: 10\nSeason 1: Number of episodes: 10\nSeason 2: Number of episodes: 12\n";
        assertEquals(expected, actual);
        
    }

}

