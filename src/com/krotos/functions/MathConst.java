package com.krotos.functions;

public enum MathConst {
    PI(3.141592653589793238462643),
    E(2.71828182846);

    double value;

    MathConst(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
