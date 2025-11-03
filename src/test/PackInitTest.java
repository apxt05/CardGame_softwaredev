package test;

import CardGame;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PackInitTest {

    private Path tempDir;
    private final InputStream originalIn = System.in;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void initTest() throws IOException{
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        this.tempDir = Files.createTempDirectory("pack-test-");
        System.setProperty("user.dir", this.tempDir.toAbsolutePath().toString());
    }

    @AfterEach
    public void cleanTest() throws IOException {
        if (tempDir != null && Files.exists(tempDir)) {
            Files.walk(tempDir)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(p -> p.toFile().delete());
        }
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testNotEnoughPlayers() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String in = "1";
        System.setIn(new ByteArrayInputStream(in.getBytes()));
        Scanner s = new Scanner(System.in);
        assertEquals(null, CardGame.getParsePlayerNumberMethod().invoke(null, s));
        assertEquals("Number must be more than 1, please try again:\r\n", outContent.toString());
    }

    @Test
    public void testPlayerNumberNotNumber() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException{
        String in = "hello";
        System.setIn(new ByteArrayInputStream(in.getBytes()));
        Scanner s = new Scanner(System.in);
        assertEquals(null, CardGame.getParsePlayerNumberMethod().invoke(null, s));
        assertEquals("Invalid number format, please try again:\r\n", outContent.toString());
    }

    @Test
    public void validPlayerNumber() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException{
        String in = "4";
        System.setIn(new ByteArrayInputStream(in.getBytes()));
        Scanner s = new Scanner(System.in);
        assertEquals(4, CardGame.getParsePlayerNumberMethod().invoke(null, s));
    }

    
    @Test
    public void testInvalidFileDir() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException{
        String fakePath = ("fakePack.txt");
        File fakeFile = new File(fakePath);
        assertEquals(false, CardGame.getCheckValidFileLocationMethod().invoke(null, fakeFile));
        assertEquals("Invalid file name, please try again:\r\n", outContent.toString());
    }
    
    @Test
    public void testValidFileDir() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException{
        Path fakePath = this.tempDir.resolve("tempPack.txt");
        Files.writeString(fakePath, "1\n2\n3\n4");
        assertEquals(true, CardGame.getCheckValidFileLocationMethod().invoke(null, fakePath.toFile()));
    }

    @Test
    public void testReadInputFileInvalidFormat() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException{
        Path fakePath = this.tempDir.resolve("tempPack.txt");
        Files.writeString(fakePath, "1\n2\n3\n4\nhello");
        assertEquals(null, CardGame.getReadInputFileMethod().invoke(null, fakePath.toFile()));
        assertEquals("Input file is of an invalid format, please try again:\r\n", outContent.toString());
    }

    @Test
    public void testReadInputFileValidFormat() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException{
        Path fakePath = this.tempDir.resolve("tempPack.txt");
        Files.writeString(fakePath, "1\n2\n3\n4\n");
        assertEquals("[Card: 1, Card: 2, Card: 3, Card: 4]", CardGame.getReadInputFileMethod().invoke(null, fakePath.toFile()).toString());
    }
    
    @Test
    public void testPackSizeTooSmall() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException{
        int size = 15;
        int numberOfPlayers = 2;
        assertEquals(false, CardGame.getCheckPackSizeMethod().invoke(null, size, numberOfPlayers));
        assertEquals("Input file has an incorrect number of cards, please try again:\r\n", outContent.toString());
    }

    @Test
    public void testPackSizeTooLarge() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException{
        int size = 17;
        int numberOfPlayers = 2;
        assertEquals(false, CardGame.getCheckPackSizeMethod().invoke(null, size, numberOfPlayers));
        assertEquals("Input file has an incorrect number of cards, please try again:\r\n", outContent.toString());
    }
}