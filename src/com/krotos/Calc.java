package com.krotos;

import java.util.ArrayDeque;
import java.util.Deque;

class Calc {

    //stos liczb
    private Deque<Double> stack = new ArrayDeque<>();
    private String onp;
    //dzialanie onp rozdzielone na wyrazy
    private String[] tabONP;
    //do użycia w kolejnych obliczeniach
    private double result;

    //wyrażenie przekazane jako string
    public double calculate(String onp) {
        stack.clear();
        this.onp = onp;
        //System.out.println("Zaladowano string: "+onp);
        split();
        tabToStack();
        showResult();
        return result;
    }

    //dzieli stringa na wyrazy po spacjach
    private void split() {
        tabONP = onp.split(" ");
        //System.out.println(Arrays.toString( tabONP));
    }

    private void tabToStack() {
        for (String word : tabONP) {
            try {
                //probuje przerobic kolejny wyraz na doubla i wrzucic na stos
                stack.push(Double.parseDouble(word));
            } catch (NumberFormatException e) {
                //jak sie nie da to uznaje ze wyraz oznacza akcje
                switch (word) {
                    case "!":
                        factorial();        //silnia osobno bo wczytuje tylko jedną liczbe
                        break;
                    default:
                        standardActions(word);
                        break;
                }
            }
            //System.out.println(stack);
        }
        //System.out.println(stack);
    }

    //pobiera 2 pozycje ze stosu i wykonuje dzialanie, zwraca c na stos
    private void standardActions(String act) throws NoSuchActionAvailableException {
        Double b = stack.pop();
        Double a = stack.pop();
        Double c;
        switch (act) {
            case "+":
                c = a + b;
                break;
            case "-":
                c = a - b;
                break;
            case "*":
                c = a * b;
                break;
            case "/":
                c = a / b;
                break;
            case "^":
                c = Math.pow(a, b);
                break;
            case "%":
                c = a % b;
                break;
            default:
                //rzuca wyjątek że nie ma takiej operacji
                throw new NoSuchActionAvailableException();
        }
        //wynik na stos
        stack.push(c);
        // System.out.println(a+" "+b);
    }

    //wyświetlenie wyniku
    private void showResult() {
        System.out.println("Wynik: " + stack.peek());
        result = stack.peek();
    }

    //silnia
    private void factorial() {
        double a = stack.pop();
        int a2 = (int) a;
        int c = 1;
        //sprawdzenie czy liczba jest naturalna
        if (a != a2 || a < 0) {
            System.out.println("Silnia tylko z liczb naturalnych");        //przerobić na wyjątek
            System.out.println("Obecnie silnia z: " + a);
            stack.push(0.0);
            return;
        }

        for (int i = 1; i <= a2; i++) {
            c *= i;
        }
        //wynik na stos
        stack.push((double) c);
    }

    class NoSuchActionAvailableException extends RuntimeException {
        private NoSuchActionAvailableException() {
            super();
        }
    }
}
