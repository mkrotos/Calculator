package com.krotos;

import com.krotos.functions.Factorial;

import java.util.NoSuchElementException;
import java.util.Scanner;

class UI {
    private Scanner scanner = new Scanner(System.in);
    private Calc calc = new Calc();
    private ToRPN conv = new ToRPN();
    private History history = new History();
    private ToFile toFile = new ToFile();


    void run() {
        boolean run = true;
        String eq;
        while (run) {
            System.out.println("Enter the equation: (or 'exit' or 'help' or 'history' or 'save')");
            eq = scanner.next();
            switch (eq) {
                case "exit":
                    run = false;
                    break;
                case "help":
                    System.out.println("Supported operations: ");
                    System.out.println(" + add \n - subtract \n * multiply \n / divide \n % modulo \n ! factorial \n () brackets \n" +
                            " sin() \n cos() \n tan() \n ctan() \n PI \n E");
                    break;
                case "history":
                    history.show();
                    break;
                case "save":
                    toFile.write(history.getHistoryList());
                    break;
                default:
                    try {
                        Double res = calc.calculate(conv.run(eq));
                        history.add(eq, res);
                    } catch (NoSuchElementException e) {
                        //rzucane przez calc.standardActions jesli nie ma dwoch liczb do wczytania ze stosu
                        //e.printStackTrace();
                        System.out.println("No such element ex");
                        System.out.println("Wrong equation");
                    } catch (Calc.NoSuchActionAvailableException e) {
                        //rzucane przez calc.standardActions jesli ma liczby ale nie ma takiego przypadku
                        System.out.println("There is not such operation available");
                        System.out.println("See help for list of supported operations");
                    } catch (Factorial.FactorialOnlyNaturalNumbersException e){
                        //rzucany przez silnie jesli liczba nie jest naturalna
                        System.out.println("Factorial only from natural numbers");
                        System.out.println("You trying to calculate factorial from "+e.getValue());
                    }
            }
        }
        System.out.println("Bye");
    }

}

