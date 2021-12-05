package main;

import main.parsing.EvaluationException;
import main.parsing.ParsingException;
import main.parsing.PrecedenceClimbing;
import main.tokenization.TokenizationException;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Calculator evaluates expressions and tracks its state.
 */
public class Calculator {
    private final Map<String, Double> vars;

    /**
     * Constructs an empty calculator.
     */
    public Calculator() {
        this.vars = new HashMap<>();
    }

    /**
     * Evaluates the provided expression.
     *
     * @param input the expression as stream.
     * @throws EvaluationException if evaluation fails.
     */
    public void evaluate(InputStreamReader input) throws EvaluationException, TokenizationException, ParsingException {
        Scanner scanner = new Scanner(input);
        PrecedenceClimbing parser = new PrecedenceClimbing(this.vars);

        while (scanner.hasNextLine()) {
            parser.parse(scanner.nextLine());
        }

        scanner.close();
    }

    /**
     * Returns the internal state.
     *
     * @return the internal state as string.
     */
    public String output() {
        StringBuilder builder = new StringBuilder();
        builder.append('(');
        var iter = this.vars.entrySet().iterator();
        while (iter.hasNext()) {
            var entry = iter.next();
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
            if (!iter.hasNext()) {
                break;
            }
            builder.append(",");
        }

        builder.append(')');

        return builder.toString();
    }

    /**
     * Returns the internal state.
     *
     * @return the internal state as a map.
     */
    public Map<String, Double> getVars() {
        return this.vars;
    }

    @Override
    public String toString() {
        return output();
    }
}
