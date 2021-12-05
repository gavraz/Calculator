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
     * Constructs a new tokenizer for the given input.
     *
     * @param input the input to tokenize.
     */
    public Tokenizer(String input) {
        this.input = input;
        this.i = 0;
        this.last_consumed = 0;
    }

    /**
     * Peeks for the next token without advancing to the next token.
     *
     * @return the next token if any; Token.Term otherwise.
     * @throws Exception if tokenization fails.
     */
    public Token peekNext() throws Exception {
        if (this.next != null) {
            return this.next;
        }

        Constructor.Result result = TokenFactory.instance().tryConstructNext(this.input, this.i);
        if (result == null || result.token == null || result == Constructor.Result.None) {
            throw new Exception("tokenization failed: could not tokenize next token");
        }
        this.next = result.token;
        this.last_consumed = result.consumed;
        return this.next;
    }

    /**
     * Advances and returns the next token.
     *
     * @return the next token if any; Term otherwise.
     * @throws Exception if tokenization fails.
     */
    public Token next() throws Exception {
        Token current = this.peekNext();
        this.advance();

        return current;
    }

    /**
     * Advances to the next token.
     */
    public void advance() {
        this.i += this.last_consumed;
        this.last_consumed = 0;
        if (this.next.getType() != Token.Type.Term) {
            this.next = null;
        }
    }

    /**
     * Tokenizes a given string.
     *
     * @param line the input to tokenize.
     * @return the list of tokens.
     */
    public static List<Token> tokenize(String line) {
        Tokenizer tokenizer = new Tokenizer(line);
        var tokens = new LinkedList<Token>();

        while (true) {
            Token token = null;
            try {
                token = tokenizer.next();
            } catch (Exception e) {
                e.printStackTrace();
            }
            tokens.add(token);

            if (token != null && token.getType() == Token.Type.Term) {
                return tokens;
            }
        }
    }
}
