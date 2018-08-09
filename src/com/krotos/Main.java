package com.krotos;


class Main {
    public static void main(String[] args) {

        String onp="12 2 3 4 * 10 5 / + * +";
        Calc calc=new Calc();
        calc.calculate(onp);

        String eq="12*(3+2)+12,5*2-4/4+5^2";
        ToRPN toonp=new ToRPN();
        calc.calculate(toonp.run(eq));

        //dorobić UI
        //rów kwadratowe
        //silnia
        //szereg harmonicznych
        //wykresy wielomianów
        //RPN
        //obsługa wyjątków
        //pochodne
        //całki
        //ciąg fibonacciego


    }
}
