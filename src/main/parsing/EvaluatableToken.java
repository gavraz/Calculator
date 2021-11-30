package main.parsing;

import main.tokenization.Token;

public class EvaluatableToken {
    private final Token token;

    public EvaluatableToken(Token token) throws Exception {
        this.token = token;
        throw new Exception("incompatible token type");
    }
}
