package test;

import com.krotos.*;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CalcTest {

    private Calc calc = new Calc();
    private double delta = 0.0001;


    @Test
    public void emptyEquation() {
        assertThrows(NoSuchElementException.class, () -> calc.run(""));
        assertThrows(Calc.NoSuchActionAvailableException.class, () -> calc.run("  "));
    }

    @Test
    public void equationWithoutNumbers() {
        assertThrows(NoSuchElementException.class, () -> calc.run("+"));
        assertThrows(NoSuchElementException.class, () -> calc.run("cos *"));
    }

    @Test
    public void equationWithoutActions() {
        assertEquals(3, calc.run("3"), delta);
        assertThrows(Calc.NoSuchActionAvailableException.class, () -> calc.run("3 4"));
    }

    @Test
    public void add() {
        assertEquals(4, calc.run("2 2 +"), delta);
    }

    @Test
    public void subtract() {
        assertEquals(0, calc.run("2 2 -"), delta);
    }

    @Test
    public void divide() {
        assertEquals(1, calc.run("2 2 /"), delta);
    }

    @Test
    public void multiply() {
        assertEquals(6, calc.run("2 3 *"), delta);
    }

    @Test
    public void pow() {
        assertEquals(8, calc.run("2 3 ^"), delta);
    }

    @Test
    public void modulo() {
        assertEquals(2, calc.run("5 3 %"), delta);
    }

    @Test
    public void factorial() {
        assertEquals(120, calc.run("5 !"), delta);
    }

    @Test
    public void sin() {
        assertEquals(0.84147, calc.run("1 sin"), delta);
    }

    @Test
    public void cos() {
        assertEquals(0.5403, calc.run("1 cos"), delta);
    }

    @Test
    public void tan() {
        assertEquals(1.5574, calc.run("1 tan"), delta);
    }

    @Test
    public void PI() {
        assertEquals(3.1415, calc.run("pi"), delta);
    }

    @Test
    public void E() {
        assertEquals(2.7182, calc.run("e"), delta);
    }

    @Test
    public void exp() {
        assertEquals(7.3890, calc.run("2 exp"), delta);
    }


}