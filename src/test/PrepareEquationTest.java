package test;

import com.krotos.PrepareEquation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrepareEquationTest {

    private PrepareEquation prepareEquation = new PrepareEquation();

    @Test
    public void add() {
        assertEquals("2 3 + ", prepareEquation.run("2+3"));
    }

    @Test
    public void function() {
        assertEquals("2 sin ", prepareEquation.run("sin(2)"));
    }

    @Test
    public void checkingBrackets() {
        assertEquals("2 sin ", prepareEquation.run("sin(2"));
        assertThrows(PrepareEquation.WrongEquationException.class, () -> prepareEquation.run("sin)2"));
    }

    @Test
    public void brackets() {
        assertEquals("2 3 4 + * ", prepareEquation.run("2*(3+4)"));
    }

    @Test
    public void emptyEquation() {
        assertEquals("", prepareEquation.run(""));
        assertEquals("", prepareEquation.run("   "));
    }

    @Test
    public void equationWithSpaces() {
        assertEquals("2 3 + ", prepareEquation.run("  2+3  "));
        assertEquals("2 3 + ", prepareEquation.run("  2+  3  "));
    }

    @Test
    public void values() {
        assertEquals("123.05 2 + ", prepareEquation.run("123.05+2"));
    }

    @Test
    public void power() {
        assertEquals("2 3 ^ 4 + ", prepareEquation.run("2^3+4"));
    }

    @Test
    public void multiplyBeforeBracket() {
        assertEquals("2 pi * ", prepareEquation.run("2(pi)"));
    }

    @Test
    public void wordsToLowerCase() {
        assertEquals("word ", prepareEquation.run("WORD"));
    }


}