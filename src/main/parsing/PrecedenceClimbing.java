package main.parsing;

import main.tokenization.IdentifierToken;
import main.tokenization.NumberToken;
import main.tokenization.Token;
import main.tokenization.Tokenizer;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PrecedenceClimbing {
    private final Map<Token.Type, Evaluator> op_evaluators;
    private final Map<Token.Type, Integer> precedence; // lower precedes higher
    private final Map<String, Double> vars;
    private Tokenizer tokenizer;

    public PrecedenceClimbing() {
        this.op_evaluators = new HashMap<>();
        this.precedence = new HashMap<>();
        this.vars = new HashMap<>();
        this.tokenizer = null;

        init_evaluators();
        init_precedence();
    }

    private void init_evaluators() {
        // TODO: consider bound checks
        this.op_evaluators.put(Token.Type.OPERATOR_PLUS, (lhs, rhs) -> Value.New(lhs.value() + rhs.value()));
        this.op_evaluators.put(Token.Type.OPERATOR_MINUS, (lhs, rhs) -> Value.New(lhs.value() - rhs.value()));
        this.op_evaluators.put(Token.Type.OPERATOR_MUL, (lhs, rhs) -> Value.New(lhs.value() * rhs.value()));
        this.op_evaluators.put(Token.Type.OPERATOR_DIV, (lhs, rhs) -> Value.New(lhs.value() / rhs.value()));

        // TODO how do we capture += ?
    }

    private void init_precedence() {
        // TODO add ++ -- etc

        this.precedence.put(Token.Type.OPERATOR_MUL, 0);
        this.precedence.put(Token.Type.OPERATOR_DIV, 0);

        this.precedence.put(Token.Type.OPERATOR_PLUS, 1);
        this.precedence.put(Token.Type.OPERATOR_MINUS, 1);

        this.precedence.put(Token.Type.EQUAL, 2);
        this.precedence.put(Token.Type.PLUS_EQUAL, 2);
        this.precedence.put(Token.Type.MINUS_EQUAL, 2);
        this.precedence.put(Token.Type.MUL_EQUAL, 2);
        this.precedence.put(Token.Type.DIV_EQUAL, 2);
    }

    private Valuable evaluate(Token token) throws Exception {
        if (token.getType() == Token.Type.NUMBER) {
            return Value.New(((NumberToken) (token)).getValue());
        }

        if (token.getType() == Token.Type.IDENTIFIER) {
            var result = this.vars.get(((IdentifierToken) (token)).getID());
            if (result == null) {
                throw new Exception("variable used prior declaration");
            }

            return Value.New(result);
        }

        throw new Exception("could not evaluate token");
    }

    public double parse(String input) throws Exception {
        this.tokenizer = new Tokenizer(input);

        var variable = this.tokenizer.next();
        ParsingUtil.expect(variable, Token.Type.IDENTIFIER);
        var assignment_op = this.tokenizer.next();
        ParsingUtil.expect(ParsingUtil.isAssignmentOperator(assignment_op));


        return this.parseExpression(this.tokenizer.next(), 0).value();
    }

    private Valuable parseExpression(Token lhs, int max_precedence) throws Exception {
        var lookahead = this.tokenizer.peekNext();
        var lhs_val = evaluate(lhs);

        while (ParsingUtil.isBinaryOperator(lookahead) &&
                this.precedence.get(lookahead.getType()) <= max_precedence) {
            var op = lookahead;
            this.tokenizer.advance();
            var rhs = this.tokenizer.next();
            ParsingUtil.expect(rhs, Token.Type.IDENTIFIER, Token.Type.NUMBER);
            var rhs_val = evaluate(rhs);

            lookahead = this.tokenizer.peekNext();
            while (ParsingUtil.isBinaryOperator(lookahead) &&
                    this.precedence.get(lookahead.getType()) < this.precedence.get(op.getType())) {
                // TODO: or a right-associative operator whose precedence is equal to op's
                rhs_val = parseExpression(rhs, this.precedence.get(op.getType()) - 1);
                lookahead = this.tokenizer.peekNext();
            }

            lhs_val = this.op_evaluators.get(op.getType()).evaluate(lhs_val, rhs_val);
        }

        return lhs_val;
    }
}
