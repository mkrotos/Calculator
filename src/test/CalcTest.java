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
        Assert.assertEquals(10,r1,delta);
    }
}