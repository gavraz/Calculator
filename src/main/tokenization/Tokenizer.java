package main.tokenization;

import java.util.*;

/**
 * Tokenizer exposes an iterator-like interface to go over the tokens of the input.
 */
public class Tokenizer {
    private final String input;
    private Token next;
    private int i;
    private int last_consumed;

    /**
     * Tokenizer constructs a new tokenizer for the given input.
     *
     * @param input the input to tokenize.
     */
    public Tokenizer(String input) {
        this.input = input;
        this.i = 0;
        this.last_consumed = 0;
    }

    /**
     * peekNext peeks to the next token without advancing to the next token.
     *
     * @return the next token if any; Term otherwise.
     */
    public Token peekNext() {
        if (this.next != null) {
            return this.next;
        }

        TryGetter.Result result = Factory.Instance().TryGet(this.input, this.i);
        this.next = result.token;
        this.last_consumed = result.consumed;
        return this.next;
    }

    /**
     * next advances to the next token.
     *
     * @return the next token if any; Term otherwise.
     */
    public Token next() {
        Token current = this.peekNext();
        this.advance();

        return current;
    }

    /**
     * advance to the next token.
     * if the token
     */
    public void advance() {
        this.i += this.last_consumed;
        this.last_consumed = 0;
        if (this.next.getType() != Token.Type.Term) {
            this.next = null;
        }
    }

    /**
     * analyze a given string for tokenization.
     *
     * @param line the input to tokenize.
     * @return the list of tokens.
     */
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
