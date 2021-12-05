package main;

import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        var stdin = new InputStreamReader(System.in);

        try {
            calc.evaluate(stdin);
        } catch (Exception e) {
            System.out.printf("Could not evaluate expression: %s\n", e.getMessage());
            return;
        }

        System.out.println(calc.output());
    }
}
