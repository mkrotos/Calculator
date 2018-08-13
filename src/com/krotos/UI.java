package com.krotos;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class UI {
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
                    System.out.println(" + add \n - subtract \n * multiply \n / divide \n % modulo \n ! factorial \n () brackets");
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
                        //e.printStackTrace();
                        System.out.println("No such element ex");
                        System.out.println("Wrong equation");
                    } catch (Calc.NoSuchActionAvailableException e){
                        System.out.println("There is not such operation available");
                        System.out.println("See help for list of supported operations");
                    }
            }
        }
        System.out.println("Bye");
    }

}

