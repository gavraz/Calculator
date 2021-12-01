package main.tokenization;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;





public class Tokenizer {
    private final Scanner scanner;
    private int index;
    // private Token current; TODO we can optimize

    public class TokenizationException extends Exception {
        public TokenizationException(String err) {
            super(err);
        }
    }

    public Tokenizer(InputStream input) {
        this.scanner = new Scanner(input);
        this.index = 0;
    }

    public Token peekNext() {
        Token token = Factory.Instance().TryGet(line, i);

        return token;
    }

    public Token next() {


    }

    public void advance() {

    }

    public List<Token> analyze(String line) {
        var tokens = new LinkedList<Token>();

        for (int i = 0; i < line.length(); i++) {

            Token token = Factory.Instance().TryGet(line, i);
            if (token == null) {
                return null;
            }

            if (token.getType() == Token.Type.Term) {
                return tokens;
            }

            tokens.add(token);
        }

        return tokens;
    }
}
