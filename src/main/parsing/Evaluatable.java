package main.parsing;

import main.tokenization.Token;

public class Evaluatable {
    private final Token token;

    public Evaluatable(Token token) throws Exception {
        this.token = token;
        throw new Exception("incompatible token type");
    }
}
