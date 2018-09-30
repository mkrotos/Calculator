package com.krotos;

import java.util.ArrayDeque;
import java.util.Deque;

public class PrepareEquation {
    public static final String TO_MANY_OPENING_BRACKETS_MESSAGE = "To many opening brackets";
    public static final String LAST_BRACKET_WAS_REMOVED = "Last ')' was removed";
    public static final String CLOSING_BRACKET = ")";
    public static final char DOT_CHAR = '.';
    public static final char COMMA_CHAR = ',';
    public static final String EMPTY_STRING = "";
    private String equation;
    //stos operatorów
    private Deque<Character> operatorsStack = new ArrayDeque<>();
    //stos funkcji
    private Deque<String> functionsStack = new ArrayDeque<>();
    //wynik
    private String finalEquation = "";
    //do budowania liczb
    private String actualValue = "";
    //oddzielenie poszczególnych wyrazów w String finalEquation
    private String pause = " ";


    public String run(String equation) {
        this.equation = equation;
        //czyszczenie poprzednich wyników
        finalEquation = EMPTY_STRING;
        actualValue = EMPTY_STRING;
        operatorsStack.clear();
        checkBrackets();
        //konwersja do postaci finalEquation
        convert();
        return finalEquation;
    }

    private void convert() {
        Character previous;
        Character actual;
        Character next;
        for (int i = 0; i < equation.length(); i++) {
            //przypisanie kolejnych znaków z równania do analizy
            actual = equation.toCharArray()[i];
            try {
                previous = equation.toCharArray()[i - 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                previous = null;
            }
            try {
                next = equation.toCharArray()[i + 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                next = null;
            }
            //analiza znaku
            //jeśli jest cyfrą:
            if (buildNumber(actual, next)) {
                //jeśli jest literą:
            } else if (buildWord(actual, next)) {
                //jeśli jest znakiem:
            } else {
                switch (actual) {
                    case ' ':
                        break;
                    case '(':
                        //jeśli przed nawiasem była cyfra to dodaje znak mnożenia
                        if (previous != null && Character.isDigit(previous)) {
                            operatorsStack.push('*');
                        }
                        operatorsStack.push(actual);
                        break;
                    case ')':    //jak trafi na nawias zamykający do przerzuca operatory ze stosu az do nawiasu otwierającego
                        while (!operatorsStack.peek().equals('(')) {
                            //rzucac wyjątek ze nie ma nawiasu otwierającego (jesli null)
                            finalEquation += operatorsStack.pop() + pause;
                        }
                        operatorsStack.pop();    //usuwa nawias otwierający
                        //jeśli przed nawiasem otwierającym byl na stosie znacznik funkcji do przerzuc funkcje do finalEquation
                        if (!operatorsStack.isEmpty() && operatorsStack.peek().equals('#')) {
                            finalEquation += functionsStack.pop() + pause;
                            operatorsStack.pop();
                        }
                        break;
                    case '!'://---------- silnia poza kolejką
                        finalEquation += actual + pause;
                        break;
                    //kolejne przypadki lecą do ostatniego bo tam jest sprawdzenie priorytetu (wszedzie tak samo)
                    //minus musi być pierwszy bo może oznaczać liczbe ujemną
                    case '-':
                        //jeśli minus jest pierwszy w równaniu lub po nawiasie to uznaje że to nie dzialanie tylko liczba ujemna
                        if (previous == null || previous.equals('(')) {
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
                            if (prior(actual) == 3 || prior(actual) > prior(operatorsStack.peek())) {
                                break;
                            } else {
                                //przerzuca wszystkie dzialania z priorytetem >= ze stosu do finalEquation
                                finalEquation += operatorsStack.pop() + pause;
                            }
                        }
                        operatorsStack.push(actual);        //dzialanie na stos
                        break;
                    default:
                        operatorsStack.push(actual);

                }
            }
        }
        while (!operatorsStack.isEmpty()) {    //wrzuca wszystkie pozostale operatory do finalEquation
            finalEquation += operatorsStack.pop() + pause;
        }
        //System.out.println(finalEquation);
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
        if (Character.isDigit(c) || c.equals(DOT_CHAR) || c.equals(COMMA_CHAR)) {
            if (c.equals(COMMA_CHAR)) {
                actualValue += DOT_CHAR;
            } else {
                actualValue += c;
            }
            //jesli kolejny znak nie jest cyfrą to konczy aktualny wyraz i przerzuca go do finalEquation
            if (c2 == null || !Character.isDigit(c2) && !c2.equals(DOT_CHAR) && !c2.equals(COMMA_CHAR)) {
                finalEquation += actualValue + pause;
                //reset aktualnego wyrazu
                actualValue = EMPTY_STRING;
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
                //jesli konczy się czyms innym to byl wyrazem i idzie od razu do wyrazenia finalEquation
            } else if (c2 == null || !Character.isAlphabetic(c2)) {
                finalEquation += actualValue.toLowerCase() + pause;
                actualValue = EMPTY_STRING;
            }
            return true;
        } else {
            return false;
        }
    }

    private void checkBrackets() {
        //sprawdza czy w równaniu zgadza sie ilość nawiasów otwierających i zamykających
        long openingBrackets = equation.chars().filter(ch -> ch == '(').count();
        long closingBrackets = equation.chars().filter(ch -> ch == ')').count();
        //jesli brakuje zamykających to dodaje je na koniec
        while (openingBrackets > closingBrackets) {
            equation += CLOSING_BRACKET;
            closingBrackets++;
        }
        //jesli brakuje otwierających
        while (openingBrackets < closingBrackets) {
            //to jesli równanie konczy sie zamykającym to go usuwa i wyswietla komunikat
            if (equation.endsWith(CLOSING_BRACKET)) {
                equation = equation.substring(0, equation.length() - 1);
                System.out.println(LAST_BRACKET_WAS_REMOVED);
                closingBrackets--;
            } else {
                //lub rzuca wyjątek
                throw new WrongEquationException(TO_MANY_OPENING_BRACKETS_MESSAGE);
            }
        }
    }

    public class WrongEquationException extends ArithmeticException {
        private String problem;

        private WrongEquationException(String problem) {
            super();
            this.problem = problem;
        }

        public String getProblem() {
            return problem;
        }
    }
}