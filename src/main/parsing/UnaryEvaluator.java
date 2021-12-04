package main.parsing;

import main.tokenization.IdentifierToken;
import main.tokenization.ValueToken;
import main.tokenization.Token;

import java.util.Map;

class UnaryEvaluator {
    private final Map<String, Double> context;

    public UnaryEvaluator(Map<String, Double> context) {
        this.context = context;
    }

    public Token Evaluate(Token token, Token op, boolean return_original) throws Exception {
        var var = (IdentifierToken) token;
        if (token.getType() != Token.Type.IDENTIFIER) {
            throw new Exception("unary evaluation failed: expected identifier");
        }

        var value = this.context.get(var.getID());
        if (value == null) {
            throw new Exception("unary evaluation failed: variable used prior declaration");
        }

        double delta;
        switch (op.getType()) {
            case UNARY_INC -> delta = 1;
            case UNARY_DEC -> delta = -1;
            default -> throw new Exception("unary evaluation failed: unexpected value: " + op.getType());
        }

        this.context.put(var.getID(), value + delta);

        if (!return_original) {
            value += delta;
        }

        return new ValueToken(value);
    }

}
