package main;

import main.parsing.EvaluationException;
import main.parsing.ParsingException;
import main.tokenization.TokenizationException;

import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        var stdin = new InputStreamReader(System.in);

        try {
            calc.evaluate(stdin);
        } catch (TokenizationException | EvaluationException | ParsingException e) {
            System.out.printf("Could not evaluate expression: %s\n", e.getMessage());
            return;
        }

        System.out.println(calc.output());
    }
}
