package com.krotos.functions;


public class Factorial {
    //silnia
    public double calc(double a) throws OnlyNaturalNumbersException {
        int a2 = (int) a;
        int c = 1;
        //sprawdzenie czy liczba jest naturalna
        if (a != a2 || a < 0) {
            throw new OnlyNaturalNumbersException("Factorial",a);
        }
        //licz silnie
        for (int i = 1; i <= a2; i++) {
            c *= i;
        }
        //wynik na stos
        return c;
    }


}
