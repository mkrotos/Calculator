package test;

import org.junit.Assert;
import org.junit.Test;
import com.krotos.*;

import static org.junit.Assert.*;

public class CalcTest {

    @Test
    public void calculate() {
        double delta=0.00001;
        Calc calc=new Calc();

        double r1=calc.calculate("2 5 *");
        assertEquals(10,r1,delta);

        double r2=calc.calculate("pi cos");
        assertEquals(-1,r2, delta);

        double r3=calc.calculate("pi 2 / cos");
        assertEquals(0,r3,delta);

        double r4=calc.calculate("*");

    }
}