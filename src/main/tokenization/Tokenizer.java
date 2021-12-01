package main.tokenization;

import java.util.*;


// TODO ?
//public class TokenizationException extends Exception {
//    public TokenizationException(String err) {
//        super(err);
//    }
//}


public class Tokenizer {
    private final String input;
    private Token next;
    private int i;
    private int last_consumed;

    public Tokenizer(String input) {
        this.input = input;
        this.i = 0;
        this.last_consumed = 0;
    }

    public Token peekNext() {
        if (this.next != null) {
            return this.next;
        }

        TryGetter.Result result = Factory.Instance().TryGet(this.input, this.i);
        this.next = result.token;
        this.last_consumed = result.consumed;
        return this.next;
    }

    public Token next() {
        Token current = this.peekNext();
        this.advance();

        return current;
    }

    public void advance() {
        this.i += this.last_consumed;
        this.last_consumed = 0;
        if (this.next.getType() != Token.Type.Term) {
            this.next = null;
        }
    }

    public static List<Token> analyze(String line) {
        Tokenizer tokenizer = new Tokenizer(line);
        var tokens = new LinkedList<Token>();

        while (true) {
            var token = tokenizer.next();
            tokens.add(token);

            if (token != null && token.getType() == Token.Type.Term) {
                return tokens;
            }
        }
    }
}
