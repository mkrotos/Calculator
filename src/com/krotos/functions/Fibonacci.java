package com.krotos.functions;

import java.util.ArrayList;
import java.util.List;

public class Fibonacci {

    static private double calc(double a) throws OnlyNaturalNumbersException{
        int a2 = (int) a;
        //sprawdzenie czy liczba jest naturalna
        if (a != a2 || a < 0) {
            throw new OnlyNaturalNumbersException("Fibonacci", a);
        }

        List<Integer> fibList=new ArrayList<>();
        fibList.add(0);
        fibList.add(1);
        fibList.add(1);
        for(int i=3;i<=a;i++){
            fibList.add(fibList.get(i-1)+fibList.get(i-2));
        }
        return fibList.get(a);
    }
}
