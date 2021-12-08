package main.parsing;

import main.tokenization.IdentifierToken;
import main.tokenization.Token;
import main.tokenization.ValueToken;

import java.util.Map;

/**
 * UnaryEvaluator can evaluate the increment and decrement unary operators.
 */
class UnaryEvaluator {
    private final Map<String, Double> env;

    /**
     * Construct a new unary evaluator with the given environment.
     *
     * @param env the environment to be used for evaluation.
     */
    public UnaryEvaluator(Map<String, Double> env) {
        this.env = env;
    }

    /**
     * Evaluates the token with respect to the provided operation.
     *
     * @param operand             the operand token.
     * @param operation           the unary operation.
     * @param post_fix is true iff the operation should be evaluated to the operand token.
     * @return the value of the unary operation on the provided operand with respect to the post_fix.
     * @throws EvaluationException if the operation could not be evaluated with respect to the operand.
     */
    public Token Evaluate(Token operand, Token operation, boolean post_fix) throws EvaluationException {
        var var = (IdentifierToken) operand;
        if (operand.getType() != Token.Type.IDENTIFIER) {
            throw new EvaluationException("unary evaluation failed: expected identifier");
        }

        var value = this.env.get(var.getID());
        if (value == null) {
            throw new EvaluationException("unary evaluation failed: variable used prior declaration");
        }

        double delta;
        switch (operation.getType()) {
            case UNARY_INC -> delta = 1;
            case UNARY_DEC -> delta = -1;
            default -> throw new EvaluationException("unary evaluation failed: unexpected value: " + operation.getType());
        }

        this.env.put(var.getID(), value + delta);

        if (post_fix) {
            return new ValueToken(value);
        }

        return new ValueToken(value + delta);
    }

}
