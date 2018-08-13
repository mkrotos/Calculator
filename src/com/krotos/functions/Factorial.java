package com.krotos.functions;

import java.io.IOException;

public class Factorial {
    //silnia
    public double calc(double a) throws FactorialOnlyNaturalNumbersException {
        int a2 = (int) a;
        int c = 1;
        //sprawdzenie czy liczba jest naturalna
        if (a != a2 || a < 0) {
            throw new FactorialOnlyNaturalNumbersException(a);
        }
        //licz silnie
        for (int i = 1; i <= a2; i++) {
            c *= i;
        }
        //wynik na stos
        return c;
    }

    public class FactorialOnlyNaturalNumbersException extends ArithmeticException {
        private double value;

        private FactorialOnlyNaturalNumbersException(Double value) {
            super();
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }
}
