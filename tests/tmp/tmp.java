package tests.tmp;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class tmp {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream(); 
    private ByteArrayInputStream  inContent;
    private PrintStream originalOut = System.out;
    private InputStream originalIn = System.in;

    
    @Before
    public void setup(){
        System.setOut(new PrintStream(outContent));
    }
    @After
    public void teardown(){
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void tmp(){

        //################# this is what is neede to test input
        String consoleInput = "hello\nworld";
        inContent = new ByteArrayInputStream(consoleInput.getBytes());
        System.setIn(inContent);
        Scanner s = new Scanner(System.in);
        //#################

        String actual = s.nextLine();
        assertEquals("hello", actual);
        s.close();
    }

    
}
