package com.krotos.functions;

public class OnlyNaturalNumbersException extends ArithmeticException {
    private double value;
    private String name;

    OnlyNaturalNumbersException(String name, Double value) {
        super();
        this.value = value;
        this.name=name;
    }

    public double getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
