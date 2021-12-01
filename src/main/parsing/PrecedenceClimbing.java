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
    private int max_precedence;

    public PrecedenceClimbing() {
        this(new HashMap<>());
    }

    public PrecedenceClimbing(Map<String, Double> vars) {
        this.op_evaluators = new HashMap<>();
        this.precedence = new HashMap<>();
        this.vars = vars;
        this.tokenizer = null;

        init_evaluators();
        init_precedence();
    }

    private void init_evaluators() {
        // TODO: consider bound checks
        this.op_evaluators.put(Token.Type.OPERATOR_PLUS, (lhs, rhs) -> evaluate_token(lhs) + evaluate_token(rhs));
        this.op_evaluators.put(Token.Type.OPERATOR_MINUS, (lhs, rhs) -> evaluate_token(lhs) - evaluate_token(rhs));
        this.op_evaluators.put(Token.Type.OPERATOR_MUL, (lhs, rhs) -> evaluate_token(lhs) * evaluate_token(rhs));
        this.op_evaluators.put(Token.Type.OPERATOR_DIV, (lhs, rhs) -> evaluate_token(lhs) / evaluate_token(rhs));

        this.op_evaluators.put(Token.Type.EQUAL, (lhs, rhs) -> {
            if (lhs.getType() != Token.Type.IDENTIFIER) {
                throw new Exception("expected identifier in assignment");
            }

            var lhs_var = (IdentifierToken) lhs;
            var rhs_val = evaluate_token(rhs);
            vars.put(lhs_var.getID(), rhs_val);

            return rhs_val;
        });

        this.op_evaluators.put(Token.Type.PLUS_EQUAL, (lhs, rhs) -> {
            if (lhs.getType() != Token.Type.IDENTIFIER) {
                throw new Exception("expected identifier in assignment");
            }

            var lhs_var = (IdentifierToken) lhs;
            var rhs_val = evaluate_token(rhs);
            var res = evaluate_token(lhs_var) + rhs_val;
            vars.put(lhs_var.getID(), res);

            return res;
        });

        this.op_evaluators.put(Token.Type.MINUS_EQUAL, (lhs, rhs) -> {
            if (lhs.getType() != Token.Type.IDENTIFIER) {
                throw new Exception("expected identifier in assignment");
            }

            var lhs_var = (IdentifierToken) lhs;
            var rhs_val = evaluate_token(rhs);
            var res = evaluate_token(lhs_var) - rhs_val;
            vars.put(lhs_var.getID(), res);

            return res;
        });

        this.op_evaluators.put(Token.Type.MUL_EQUAL, (lhs, rhs) -> {
            if (lhs.getType() != Token.Type.IDENTIFIER) {
                throw new Exception("expected identifier in assignment");
            }

            var lhs_var = (IdentifierToken) lhs;
            var rhs_val = evaluate_token(rhs);
            var res = evaluate_token(lhs_var) * rhs_val;
            vars.put(lhs_var.getID(), res);

            return res;
        });

        this.op_evaluators.put(Token.Type.DIV_EQUAL, (lhs, rhs) -> {
            if (lhs.getType() != Token.Type.IDENTIFIER) {
                throw new Exception("expected identifier in assignment");
            }

            var lhs_var = (IdentifierToken) lhs;
            var rhs_val = evaluate_token(rhs);
            var res = evaluate_token(lhs_var) / rhs_val;
            vars.put(lhs_var.getID(), res);

            return res;
        });
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
        this.max_precedence = 2;
    }

    private double evaluate_token(Token token) throws Exception {
        if (token.getType() == Token.Type.NUMBER) {
            return ((NumberToken) (token)).getValue();
        }

        if (token.getType() == Token.Type.IDENTIFIER) {
            var result = this.vars.get(((IdentifierToken) (token)).getID());
            if (result == null) {
                throw new Exception("variable used prior declaration");
            }

            return result;
        }

        throw new Exception("could not evaluate token");
    }

    public Token parse(String input) throws Exception {
        this.tokenizer = new Tokenizer(input);

        var lhs = this.tokenizer.next();

        return this.parseExpression(lhs, this.max_precedence);
    }

    private Token parseExpression(Token lhs, int max_precedence) throws Exception {
        if (lhs.getType() == Token.Type.LEFT_PARENTHESIS) {
            lhs = parseExpression(this.tokenizer.next(), this.max_precedence);
        }

        var lookahead = this.tokenizer.peekNext();

        if (lookahead.getType() == Token.Type.RIGHT_PARENTHESIS) {
            this.tokenizer.advance();
        }

        while (ParsingUtil.isBinaryOperator(lookahead) &&
                this.precedence.get(lookahead.getType()) <= max_precedence) {
            var op = lookahead;
            this.tokenizer.advance();
            var rhs = this.tokenizer.next();

            if (rhs.getType() == Token.Type.LEFT_PARENTHESIS) {
                rhs = parseExpression(this.tokenizer.next(), this.max_precedence);
            }

            ParsingUtil.expect(rhs, Token.Type.IDENTIFIER, Token.Type.NUMBER); // TODO ?

            lookahead = this.tokenizer.peekNext();
            while (ParsingUtil.isBinaryOperator(lookahead) &&
                    this.precedence.get(lookahead.getType()) < this.precedence.get(op.getType())) {
                // TODO: or a right-associative operator whose precedence is equal to op's
                rhs = parseExpression(rhs, this.precedence.get(op.getType()) - 1);
                lookahead = this.tokenizer.peekNext();
            }

            lhs = new NumberToken(this.op_evaluators.get(op.getType()).evaluate(lhs, rhs));

            if (lookahead.getType() == Token.Type.RIGHT_PARENTHESIS) {
                this.tokenizer.advance();
            }
        }

        return lhs;
    }
}
