package main;

import main.parsing.PrecedenceClimbing;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {
    private final Map<String, Double> vars;

    public Calculator() {
        this.vars = new HashMap<>();
    }

    public void evaluate(InputStreamReader input) throws Exception {
        Scanner scanner = new Scanner(input);
        PrecedenceClimbing parser = new PrecedenceClimbing(this.vars);

        while (scanner.hasNextLine()) {
            parser.parse(scanner.nextLine());
        }

        scanner.close();
    }

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

    public Map<String, Double> getVars() {
        return this.vars;
    }

    @Override
    public String toString() {
        return output();
    }
}
