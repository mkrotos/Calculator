package com.krotos;

import java.util.ArrayList;
import java.util.List;

public class History {
    //tworzy liste <memory(równanie, wynik)>
    //dodaje się do niej każde wykonane równanie (w UI)

    private Memory memory = new Memory();
    private List<Memory> historyList = new ArrayList<>();

    public void add(String eq, Double res) {
        historyList.add(new Memory(eq, res));
    }

    public void show(){
        System.out.println("History: ");
        for(int i=0;i<historyList.size();i++) {
            System.out.println(i+": "+ historyList.get(i).toString());
        }
    }

    public List<Memory> getHistoryList() {
        return historyList;
    }

    //clearHistory
}
