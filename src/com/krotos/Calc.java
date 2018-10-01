package com.krotos;

import com.krotos.functions.Factorial;
import com.krotos.functions.Fibonacci;

import java.util.ArrayDeque;
import java.util.Deque;

public class Calc {

    public static final String SPACE = " ";
    //stos liczb
    private Deque<Double> stack = new ArrayDeque<>();
    private String preparedEquation;
    //dzialanie preparedEquation rozdzielone na wyrazy
    private String[] arrayFromEquation;
    //do użycia w kolejnych obliczeniach
    private double savedResult;

    //wyrażenie przekazane jako string
    public double run(String preparedEquation) {
        stack.clear();
        this.preparedEquation = preparedEquation;
        //System.out.println("Zaladowano string: "+preparedEquation);
        split();
        calculate();
        checkResult();
        saveResult();
        //showResult();
        return savedResult;
    }

    //dzieli stringa na wyrazy po spacjach
    private void split() {
        arrayFromEquation = preparedEquation.split(SPACE);
        //System.out.println(Arrays.toString( arrayFromEquation));
    }

    private void calculate() {
        for (String word : arrayFromEquation) {
            try {
                //probuje przerobic kolejny wyraz na doubla i wrzucic na stos
                stack.push(Double.parseDouble(word));
            } catch (NumberFormatException e) {
                //jak sie nie da to uznaje ze wyraz oznacza akcje
                switch (word) {
                    case "sin":
                        stack.push(Math.sin(stack.pop()));
                        break;
                    case "cos":
                        stack.push(Math.cos(stack.pop()));
                        break;
                    case "tan":
                        stack.push(Math.tan(stack.pop()));
                        break;
                    case "pi":
                        stack.push(Math.PI);
                        break;
                    case "e":
                        stack.push(Math.E);
                        break;
                    case "!":
                        Factorial factorial = new Factorial();
                        stack.push(factorial.calc(stack.pop()));        //silnia osobno bo wczytuje tylko jedną liczbe
                        break;
                    case "exp":
                        stack.push(Math.exp(stack.pop()));
                        break;
                    case "random":
                        stack.push(Math.random());
                        break;
                    case "torad":
                        stack.push(Math.toRadians(stack.pop()));
                        break;
                    case "todeg":
                        stack.push(Math.toDegrees(stack.pop()));
                        break;
                    case "sqrt":
                        stack.push(Math.sqrt(stack.pop()));
                        break;
                    case "fibonacci":
                        stack.push(Fibonacci.calc(stack.pop()));
                    case "abs":
                        stack.push(Math.abs(stack.pop()));
                        break;
                    case "log":
                        stack.push(Math.log10(stack.pop()));
                        break;
                    case "ln":
                        stack.push(Math.log(stack.pop()));
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

    //pobiera 2 pozycje ze stosu i wykonuje dzialanie, zwraca savedResult na stos
    private void standardActions(String act) throws NoSuchActionAvailableException {
        double b;
        double a;
        b = stack.pop();
        a = stack.pop();

        Double result;
        switch (act) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
            case "^":
                result = Math.pow(a, b);
                break;
            case "%":
                result = a % b;
                break;
            default:
                //rzuca wyjątek że nie ma takiej operacji
                throw new NoSuchActionAvailableException();
        }
        //wynik na stos
        stack.push(result);
    }

    //wyświetlenie wyniku
    public void showResult() {
        System.out.println("Wynik: " + stack.peek());
    }

    private void saveResult() {
        savedResult = stack.peek();
    }

    private void checkResult() {
        if (stack == null ||stack.size() > 1 ||  stack.size() == 0) {
            throw new NoSuchActionAvailableException();
        }
    }

    public class NoSuchActionAvailableException extends IllegalArgumentException {
        private NoSuchActionAvailableException() {
            super();
        }
    }

}
