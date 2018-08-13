package com.krotos.functions;

public class Factorial {
    //silnia
    static public double calc(double a) {
        int a2 = (int) a;
        int c = 1;
        //sprawdzenie czy liczba jest naturalna
        if (a != a2 || a < 0) {
            System.out.println("Silnia tylko z liczb naturalnych");        //przerobić na wyjątek
            System.out.println("Obecnie silnia z: " + a);
            return 0;
        }

        for (int i = 1; i <= a2; i++) {
            c *= i;
        }
        //wynik na stos
        return c;
    }
}
