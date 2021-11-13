import tokenization.Tokenizer;

import java.util.HashMap;
import java.util.Map;

public class Calculator {
    private Map<String, Integer> vars;

    public Calculator() {
        this.vars = new HashMap<>();
    }

    public void ParseLine(String line) { // TODO should throw something
        Tokenizer tokenizer = new Tokenizer();

        //var token = tokenizer.Next(line);
    }
}
