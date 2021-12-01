package main;

import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        var input = new InputStreamReader(System.in);

        try {
            calc.evaluate(input);
        } catch (Exception e) {
            System.out.printf("Could not evaluate expression: %s\n", e.getMessage());
        }

        System.out.println(calc.output());
    }
}
