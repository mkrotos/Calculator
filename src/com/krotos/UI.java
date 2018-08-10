package com.krotos;
import java.util.Scanner;

public class UI {
    Scanner scanner=new Scanner(System.in);
    Calc calc=new Calc();
    ToRPN conv=new ToRPN();

    public void run(){
        boolean run=true;
        String eq;
        while(run){
            System.out.println("Enter the equation: (or 'exit')");
            eq=scanner.next();
            switch(eq){
                case "exit":
                    run=false;
                    break;
                default:
                    calc.calculate(conv.run(eq));
            }
        }
        System.out.println("Bye");
    }

}

