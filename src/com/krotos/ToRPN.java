package com.krotos;

import java.util.ArrayDeque;
import java.util.Deque;

class ToRPN {

    String equation;
    Deque<Character> operatorsStack=new ArrayDeque<>();
    String onp="";
    String actualValue="";
    //oddzielenie poszczególnych wyrazów w String onp
    String pause=" ";


    public String run(String equation){
        this.equation=equation;
        onp="";
        actualValue="";
        operatorsStack.clear();
        convert();
        return onp;
    }

    private void convert(){
        Character c;
        Character c2;
        for(int i=0;i<equation.length();i++){
            c=equation.toCharArray()[i];				//zamienic budowanie liczb na osobna metode
            try{
                c2=equation.toCharArray()[i+1];
            }catch(ArrayIndexOutOfBoundsException e){
                c2=null;
            }
            if(Character.isDigit(c)||c.equals('.')||c.equals(',')){	//jesli znak jest cyfra to buduje liczbe
                if(c.equals(',')){
                    actualValue+='.';
                }else{
                    actualValue+=c;
                }
                //jesli kolejny znak nie jest cyfrą to konczy aktualny wyraz i przerzuca go do onp
                if(c2==null||!Character.isDigit(c2)&&!c2.equals('.')&&!c2.equals(',')){
                    onp+=actualValue+pause;
                    actualValue="";
                }

            }else{		//jesli trafi na znak inny niz nawias zamykający
                switch(c){
                    case ' ': break;
                    case '(':
                        operatorsStack.push(c);
                        break;
                    case ')': 	//jak trafi na nawias zamykający do przerzuca operatory ze stosu az do nawiasu otwierającego
                        while(!operatorsStack.peek().equals('(')){
                            onp+=operatorsStack.pop()+pause;
                        }
                        operatorsStack.pop();	//usuwa nawias otwierający
                        break;
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                    case '^':
                        while(!operatorsStack.isEmpty()){
                            if(prior(c)==3||prior(c)>prior(operatorsStack.peek())){
                                break;
                            }else{
                                onp+=operatorsStack.pop()+pause;
                            }}
                        operatorsStack.push(c);
                        break;
                    default: operatorsStack.push(c);

                }
            }
        }
        while(!operatorsStack.isEmpty()){	//wrzuca wszystkie pozostale operatory do onp
            onp+=operatorsStack.pop()+pause;
        }
        System.out.println(onp);
    }

    private int prior(char a){
        switch(a){
            case '+':
                return 1;
            case '-':
                return 1;
            case '*':
                return 2;
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return 0;
        }
    }
}
