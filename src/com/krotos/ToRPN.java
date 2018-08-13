package com.krotos;

import java.util.ArrayDeque;
import java.util.Deque;

class ToRPN {
    private String equation;
    //stos operatorów
    private Deque<Character> operatorsStack = new ArrayDeque<>();
    //stos funkcji
    private Deque<String> functionsStack = new ArrayDeque<>();
    //wynik
    private String onp = "";
    //do budowania liczb
    private String actualValue = "";
    //oddzielenie poszczególnych wyrazów w String onp
    private String pause = " ";


    public String run(String equation) {
        this.equation = equation;
        //czyszczenie poprzednich wyników
        onp = "";
        actualValue = "";
        operatorsStack.clear();
        convert();
        return onp;
    }

    private void convert() {
        Character c0;
        Character c;
        Character c2;
        for (int i = 0; i < equation.length(); i++) {
            //przypisanie kolejnych znaków z równania do analizy
            c = equation.toCharArray()[i];
            try {
                c0 = equation.toCharArray()[i - 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                c0 = null;
            }
            try {
                c2 = equation.toCharArray()[i + 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                c2 = null;
            }
            //analiza znaku
            //jeśli jest cyfrą:
            if (buildNumber(c, c2)) {
                //jeśli jest literą:
            } else if (buildWord(c, c2)) {
                //jeśli jest znakiem:
            } else {
                switch (c) {
                    case ' ':
                        break;
                    case '(':
                        //dodac że jeśli c0 było liczbą to jeszcze mnożenie
                        operatorsStack.push(c);
                        break;
                    case ')':    //jak trafi na nawias zamykający do przerzuca operatory ze stosu az do nawiasu otwierającego
                        while (!operatorsStack.peek().equals('(')) {    //rzucac wyjątek ze nie ma nawiasu otwierającego (jesli null)
                            onp += operatorsStack.pop() + pause;
                        }
                        operatorsStack.pop();    //usuwa nawias otwierający
                        //tutaj ma przerzucać funkcje
                        break;
                    case '!'://---------- silnia poza kolejką
                        onp += c + pause;
                        break;
                    //kolejne przypadki lecą do ostatniego bo tam jest sprawdzenie priorytetu (wszedzie tak samo)
                    //minus musi być pierwszy bo może oznaczać liczbe ujemną
                    case '-':
                        //jeśli minus jest pierwszy w równaniu lub po nawiasie to uznaje że to nie dzialanie tylko liczba ujemna
                        if (c0 == null || c0.equals('(')) {
                            actualValue += "-";
                            break;
                        }
                    case '+':
                    case '*':
                    case '/':
                    case '%'://-------
                    case '^':
                        while (!operatorsStack.isEmpty()) {
                            //sprawdza priorytet dzialania, jak wyższy niż poprzedni lub 3 to nic
                            if (prior(c) == 3 || prior(c) > prior(operatorsStack.peek())) {
                                break;
                            } else {
                                //przerzuca wszystkie dzialania z priorytetem >= ze stosu do onp
                                onp += operatorsStack.pop() + pause;
                            }
                        }
                        operatorsStack.push(c);        //dzialanie na stos
                        break;
                    default:
                        operatorsStack.push(c);

                }
            }
        }
        while (!operatorsStack.isEmpty()) {    //wrzuca wszystkie pozostale operatory do onp
            onp += operatorsStack.pop() + pause;
        }
        System.out.println(onp);
    }

    private int prior(char a) {        //ustalenie priorytetu dzialania
        switch (a) {
            case '+':
                return 1;
            case '-':
                return 1;
            case '*':
                return 2;
            case '/':
                return 2;
            case '%'://----------
                return 2;
            case '^':
                return 3;
            default:
                return 0;
        }
    }

    private boolean buildNumber(Character c, Character c2) {
        //zwraca true, jeśli znak jest cyfrą lub , .
        //jesli znak jest cyfra to buduje liczbe
        if (Character.isDigit(c) || c.equals('.') || c.equals(',')) {
            if (c.equals(',')) {
                actualValue += '.';
            } else {
                actualValue += c;
            }
            //jesli kolejny znak nie jest cyfrą to konczy aktualny wyraz i przerzuca go do onp
            if (c2 == null || !Character.isDigit(c2) && !c2.equals('.') && !c2.equals(',')) {
                onp += actualValue + pause;
                actualValue = "";
            }
            return true;
        } else {
            return false;
        }
    }

    //buildWord
    private boolean buildWord(Character c, Character c2) {
        if (Character.isAlphabetic(c)) {
            actualValue += c;
            if (c2 == null || !Character.isAlphabetic(c2)) {    //zamienic na nawias?
                functionsStack.push(actualValue.toLowerCase());
                //kiedy wrzucać do rpn? po nawiasie? dodawac znacznik do stosu operatorów? przerobić stos oper na stos String?
                actualValue = "";
            }
            return true;
        } else {
            return false;
        }
    }
}