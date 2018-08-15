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
        checkBrackets();
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
                        //jeśli przed nawiasem była cyfra to dodaje znak mnożenia
                        if (Character.isDigit(c0)) {
                            operatorsStack.push('*');
                        }
                        operatorsStack.push(c);
                        break;
                    case ')':    //jak trafi na nawias zamykający do przerzuca operatory ze stosu az do nawiasu otwierającego
                        while (!operatorsStack.peek().equals('(')) {    //rzucac wyjątek ze nie ma nawiasu otwierającego (jesli null)
                            onp += operatorsStack.pop() + pause;
                        }
                        operatorsStack.pop();    //usuwa nawias otwierający
                        //jeśli przed nawiasem otwierającym byl na stosie znacznik funkcji do przerzuc funkcje do onp
                        if (!operatorsStack.isEmpty() && operatorsStack.peek().equals('#')) {
                            onp += functionsStack.pop() + pause;
                            operatorsStack.pop();
                        }
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
                            //sprawdza priorytet dzialania, jak wyższy niż poprzedni lub 3 to nic (3 to potęgi)
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
            case '%':
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
                //reset aktualnego wyrazu
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
            //jesli kolejny znak jest nawiasem to wyraz byl funkcją i idzie na stos funkcji + zmacznik na stos operatorów
            if (c2 != null && c2.equals('(')) {
                functionsStack.push(actualValue.toLowerCase());
                //wrzuca # na stos operatorów tam gdzie ma byc funkcja
                operatorsStack.push('#');
                //reset aktualnego wyrazu
                actualValue = "";
                //jesli konczy się czyms innym to byl wyrazem i idzie od razu do wyrazenia onp
            } else if (c2 == null || !Character.isAlphabetic(c2)) {
                onp += actualValue.toLowerCase() + pause;
                actualValue = "";
            }
            return true;
        } else {
            return false;
        }
    }

    private void checkBrackets() {
        long openingBrackets = equation.chars().filter(ch -> ch == '(').count();
        long closingBrackets = equation.chars().filter(ch -> ch == ')').count();
        while (openingBrackets > closingBrackets) {
            equation += ')';
            closingBrackets++;
        }
        while(openingBrackets<closingBrackets){
            if(equation.endsWith(")")){
                equation=equation.substring(0,equation.length()-1);
                System.out.println("Last ')' was removed");
                closingBrackets--;
            }else {
                throw new WrongEquationException("To many opening brackets");
            }
        }
    }
    class WrongEquationException extends ArithmeticException{
        String problem;
        private WrongEquationException(String problem){
            super();
            this.problem=problem;
        }

        public String getProblem() {
            return problem;
        }
    }
}