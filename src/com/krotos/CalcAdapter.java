package com.krotos;

import com.krotos.functions.OnlyNaturalNumbersException;

import java.util.NoSuchElementException;

public class CalcAdapter {
    private static final String NO_SUCH_ELEMENT_EX_ = "No such element ex ";
    private static final String WRONG_EQUATION = "Wrong equation";
    private static final String NO_SUCH_OPERATION_AVAILABLE_MESSAGE_1 = "There is not such operation available";
    private static final String NO_SUCH_OPERATION_AVAILABLE_MESSAGE_2 = "See help for list of supported operations";
    private static final String PAUSE = " ";

    private Calc calc=new Calc();
    private PrepareEquation prepare=new PrepareEquation();
    private History history=new History();

    public String calculate(String equation){
        Double res;
        try {
            res = calc.run(prepare.run(equation));
            history.add(equation, res);
            return res.toString();
        } catch (NoSuchElementException e) {
            //rzucane przez calc jesli nie ma liczb do wczytania ze stosu
            //e.printStackTrace();
            return NO_SUCH_ELEMENT_EX_+PAUSE+WRONG_EQUATION;
        } catch (Calc.NoSuchActionAvailableException e) {
            //rzucane przez calc jesli ma liczby ale nie ma takiego przypadku
            return NO_SUCH_OPERATION_AVAILABLE_MESSAGE_1+PAUSE+NO_SUCH_OPERATION_AVAILABLE_MESSAGE_2;
        } catch (OnlyNaturalNumbersException e) {
            //rzucany przez silnie jesli liczba nie jest naturalna
            String onlyFromNatural = e.getName() + " only from natural numbers";
            String youTryingTo = "You trying to run "+e.getName().toLowerCase() + " from " + e.getValue();
            return onlyFromNatural + PAUSE + youTryingTo;
        } catch (PrepareEquation.WrongEquationException e) {
            //rzucany przez funkcje sprawdzajaca nawiasy
            return WRONG_EQUATION;
        }
    }
}
