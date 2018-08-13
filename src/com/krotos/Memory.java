package com.krotos;

public class Memory {

    //tworzy pare równanie, wynik
    private String eq;
    private Double res;

    Memory(String eq, Double res) {
        this.eq = eq;
        this.res = res;
    }

    @Override
    public String toString() {
        return eq + "\n Result: " + res + "\n";
    }

    public String getEq() {
        return eq;
    }

    public Double getRes() {
        return res;
    }
}
