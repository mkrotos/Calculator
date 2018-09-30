package com.krotos;

import com.krotos.functions.OnlyNaturalNumbersException;

import java.util.NoSuchElementException;
import java.util.Scanner;

class UI {
    private static final String ENTER_THE_EQUATION_MESSAGE = "Enter the equation: (or 'exit' or 'help' or 'history' or 'save')";
    private static final String SUPPORTED_OPERATIONS_MESSAGE = "Supported operations: ";
    private static final String SUPPORTED_OPERATIONS_DISPLAY = " + add \n - subtract \n * multiply \n / divide \n" +
            " % modulo \n ! factorial \n () brackets \n" +
            " sin() of an angle in radians \n cos() of an angle in radians \n" +
            " tan() of an angle in radians \n" +
            " PI \n E \n exp() \n random - between 0 and 1 \n sqrt() square root \n" +
            " toRad() converts an angle measured in degrees to angle in radians \n" +
            " toDeg() converts an angle measured in radians to angle in degrees \n" +
            " fibonacci() return the value of the nth word of a fibonacci sequence \n" +
            " abs() returns the absolute value \n" +
            " log() returns the base 10 logarithm of a value \n" +
            " ln() returns the base e logarithm of a value";
    private static final String NO_SUCH_ELEMENT_EX_ = "No such element ex ";
    private static final String WRONG_EQUATION = "Wrong equation";
    private static final String NO_SUCH_OPERATION_AVAILABLE_MESSAGE_1 = "There is not such operation available";
    private static final String NO_SUCH_OPERATION_AVAILABLE_MESSAGE_2 = "See help for list of supported operations";
    private Scanner scanner = new Scanner(System.in);
    private Calc calc = new Calc();
    private PrepareEquation prepare = new PrepareEquation();
    private History history = new History();
    private ToFile toFile = new ToFile();


    void run() {
        boolean run = true;
        String eq;
        while (run) {
            System.out.println(ENTER_THE_EQUATION_MESSAGE);
            eq = scanner.next();
            switch (eq) {
                case "exit":
                    run = false;
                    break;
                case "help":
                    System.out.println(SUPPORTED_OPERATIONS_MESSAGE);
                    System.out.println(SUPPORTED_OPERATIONS_DISPLAY);
                    break;
                case "history":
                    history.show();
                    break;
                case "save":
                    toFile.write(history.getHistoryList());
                    break;
                default:
                    try {
                        Double res = calc.run(prepare.run(eq));
                        calc.showResult();
                        history.add(eq, res);
                    } catch (NoSuchElementException e) {
                        //rzucane przez calc jesli nie ma liczb do wczytania ze stosu
                        //e.printStackTrace();
                        System.out.println(NO_SUCH_ELEMENT_EX_);
                        System.out.println(WRONG_EQUATION);
                    } catch (Calc.NoSuchActionAvailableException e) {
                        //rzucane przez calc jesli ma liczby ale nie ma takiego przypadku
                        System.out.println(NO_SUCH_OPERATION_AVAILABLE_MESSAGE_1);
                        System.out.println(NO_SUCH_OPERATION_AVAILABLE_MESSAGE_2);
                    } catch (OnlyNaturalNumbersException e) {
                        //rzucany przez silnie jesli liczba nie jest naturalna
                        System.out.println(e.getName() + " only from natural numbers");
                        System.out.println("You trying to run " + e.getName().toLowerCase() + " from " + e.getValue());
                    } catch (PrepareEquation.WrongEquationException e) {
                        //rzucany przez funkcje sprawdzajaca nawiasy
                        System.out.println(WRONG_EQUATION);
                        System.out.println(e.getProblem());
                    }
            }
        }
        System.out.println("Bye");
    }

}

