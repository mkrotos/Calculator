package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.krotos.*;

import static org.junit.Assert.*;

public class CalcTest {

   private Calc calc=new Calc();
   private double delta=0.0001;

   @Test
    public void add(){
       assertEquals(4,calc.calculate("2 2 +"),delta);
   }
   @Test
    public void subtract(){
       assertEquals(0,calc.calculate("2 2 -"),delta);
   }
    @Test
    public void divide(){
        assertEquals(1,calc.calculate("2 2 /"),delta);
    }
    @Test
    public void multiply(){
        assertEquals(6,calc.calculate("2 3 *"),delta);
    }
    @Test
    public void pow(){
        assertEquals(8,calc.calculate("2 3 ^"),delta);
    }

}