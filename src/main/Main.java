package main;

import java.io.ByteArrayInputStream;

class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        try {
            calc.evaluate(new ByteArrayInputStream("x=5+3\ny=1+2*4".getBytes()));
            System.out.println(calc.output());
        } catch (Exception e) {
            System.out.printf("Could not evaluate expression: %s\n", e);
        }
    }
}
