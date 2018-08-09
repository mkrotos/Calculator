package com.krotos;

import java.util.ArrayDeque;
import java.util.Deque;

class Calc {

    Deque<Double> stack=new ArrayDeque<>();
    String onp;
    String[] tabONP;
    double result;		//do użycia w kolejnych obliczeniach

    //wyrażenie przekazane jako string
    public double calculate(String onp){
        stack.clear();
        this.onp=onp;
        //System.out.println("Zaladowano string: "+onp);
        split();
        tabToStack();
        showResult();
        return result;
    }

    //dzieli stringa na wyrazy po spacjach
    private void split(){
        tabONP=onp.split(" ");
        //System.out.println(Arrays.toString( tabONP));
    }

    private void tabToStack(){
        for(String word:tabONP){
            try{
                //probuje przerobic kolejny wyraz na doubla
                stack.push(Double.parseDouble(word));
            }catch(NumberFormatException e){
                //jak sie nie da to uznaje ze wyraz oznacza akcje
                actions(word);
            }
            //System.out.println(stack);
        }
        //System.out.println(stack);
    }

    //pobiera 2 pozycje ze stosu i wykonuje dzialanie, zwraca c na stos
    private void actions(String act){
        Double b=stack.pop();
        Double a=stack.pop();
        Double c;
        switch (act){
            case "+":
                c=a+b;
                break;
            case "-":
                c=a-b;
                break;
            case "*":
                c=a*b;
                break;
            case "/":
                c=a/b;
                break;
            case "^":
                c=Math.pow(a, b);
                break;
            default:		//przerobić na wyjątek?
                System.out.println("Brak akcji");
                c=0.0;
        }

        stack.push(c);
        // System.out.println(a+" "+b);
    }

    private void showResult(){
        System.out.println("Wynik: "+stack.peek());
        result=stack.peek();
    }
}
