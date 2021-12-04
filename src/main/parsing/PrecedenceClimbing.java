package main.parsing;

import main.tokenization.IdentifierToken;
import main.tokenization.ValueToken;
import main.tokenization.Token;
import main.tokenization.Tokenizer;

import java.util.HashMap;
import java.util.Map;

/**
 * PrecedenceClimbing is the implementation of the precedence climbing parser.
 * @link https://en.wikipedia.org/wiki/Operator-precedence_parser
 */
public class PrecedenceClimbing {
    private final UnaryEvaluator unary_evaluator;
    private final Map<Token.Type, Evaluator> binary_evaluators; // a collection of all binary operator evaluators
    private final Map<Token.Type, Integer> precedence; // lower precedes higher
    private final Map<String, Double> vars;
    private Tokenizer tokenizer;
    private int max_precedence;
    private ParenthesesValidator parentheses_validator;

    public PrecedenceClimbing() {
        this(new HashMap<>());
    }

    public PrecedenceClimbing(Map<String, Double> vars) {
        this.binary_evaluators = new HashMap<>();
        this.precedence = new HashMap<>();
        this.vars = vars;
        this.tokenizer = null;

        this.unary_evaluator = new UnaryEvaluator(this.vars);

        init_binary_evaluators();
        init_precedence();
    }

    private void init_binary_evaluators() {
        // TODO: consider bound checks
        this.binary_evaluators.put(Token.Type.OPERATOR_PLUS, (lhs, rhs) -> evaluate_token(lhs) + evaluate_token(rhs));
        this.binary_evaluators.put(Token.Type.OPERATOR_MINUS, (lhs, rhs) -> evaluate_token(lhs) - evaluate_token(rhs));
        this.binary_evaluators.put(Token.Type.OPERATOR_MUL, (lhs, rhs) -> evaluate_token(lhs) * evaluate_token(rhs));
        this.binary_evaluators.put(Token.Type.OPERATOR_DIV, (lhs, rhs) -> evaluate_token(lhs) / evaluate_token(rhs)); // TODO DIV BY ZERO?

        this.binary_evaluators.put(Token.Type.EQUAL, (lhs, rhs) -> {
            if (lhs.getType() != Token.Type.IDENTIFIER) {
                throw new Exception("expected identifier in assignment");
            }

            var lhs_var = (IdentifierToken) lhs;
            var rhs_val = evaluate_token(rhs);
            vars.put(lhs_var.getID(), rhs_val);

            return rhs_val;
        });

        this.binary_evaluators.put(Token.Type.PLUS_EQUAL, (lhs, rhs) -> {
            if (lhs.getType() != Token.Type.IDENTIFIER) {
                throw new Exception("expected identifier in assignment");
            }

            var lhs_var = (IdentifierToken) lhs;
            var rhs_val = evaluate_token(rhs);
            var res = evaluate_token(lhs_var) + rhs_val;
            vars.put(lhs_var.getID(), res);

            return res;
        });

        this.binary_evaluators.put(Token.Type.MINUS_EQUAL, (lhs, rhs) -> {
            if (lhs.getType() != Token.Type.IDENTIFIER) {
                throw new Exception("expected identifier in assignment");
            }

            var lhs_var = (IdentifierToken) lhs;
            var rhs_val = evaluate_token(rhs);
            var res = evaluate_token(lhs_var) - rhs_val;
            vars.put(lhs_var.getID(), res);

            return res;
        });

        this.binary_evaluators.put(Token.Type.MUL_EQUAL, (lhs, rhs) -> {
            if (lhs.getType() != Token.Type.IDENTIFIER) {
                throw new Exception("expected identifier in assignment");
            }

            var lhs_var = (IdentifierToken) lhs;
            var rhs_val = evaluate_token(rhs);
            var res = evaluate_token(lhs_var) * rhs_val;
            vars.put(lhs_var.getID(), res);

            return res;
        });

        this.binary_evaluators.put(Token.Type.DIV_EQUAL, (lhs, rhs) -> {
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
        this.precedence.put(Token.Type.UNARY_INC, 0);
        this.precedence.put(Token.Type.UNARY_DEC, 0);

        this.precedence.put(Token.Type.OPERATOR_MUL, 1); //??
        this.precedence.put(Token.Type.OPERATOR_DIV, 1);

        this.precedence.put(Token.Type.OPERATOR_PLUS, 2);
        this.precedence.put(Token.Type.OPERATOR_MINUS, 2);

        this.precedence.put(Token.Type.EQUAL, 3);
        this.precedence.put(Token.Type.PLUS_EQUAL, 3);
        this.precedence.put(Token.Type.MINUS_EQUAL, 3);
        this.precedence.put(Token.Type.MUL_EQUAL, 3);
        this.precedence.put(Token.Type.DIV_EQUAL, 3);
        this.max_precedence = 3;
    }

    private double evaluate_token(Token token) throws Exception {
        if (token.getType() == Token.Type.NUMBER) {
            return ((ValueToken) (token)).getValue();
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

        this.parentheses_validator = new ParenthesesValidator();

        var res = this.parseExpression(lhs, this.max_precedence);
        this.parentheses_validator.validate();

        return res;
    }


    private Token parseExpression(Token lhs, int max_precedence) throws Exception {
        if (lhs.getType() == Token.Type.LEFT_PARENTHESIS) {
            this.parentheses_validator.onOpen();
            lhs = parseExpression(this.tokenizer.next(), this.max_precedence);
        }

        // ++VAR
        if (ParsingUtil.isUnaryOperator(lhs)) {
            lhs = this.unary_evaluator.Evaluate(this.tokenizer.next(), lhs, false);
        }

        var lookahead = this.tokenizer.peekNext();

        if (lookahead.getType() == Token.Type.RIGHT_PARENTHESIS) {
            this.parentheses_validator.onClose();
            this.tokenizer.advance(); // TODO: update lookahead?
        }

        // VAR++
        if (lhs.getType() == Token.Type.IDENTIFIER && ParsingUtil.isUnaryOperator(lookahead)) {
            lhs = this.unary_evaluator.Evaluate(lhs, lookahead, true);
            this.tokenizer.advance();
            lookahead = this.tokenizer.peekNext();
        }

        while (ParsingUtil.isBinaryOperator(lookahead) &&
                this.precedence.get(lookahead.getType()) <= max_precedence) {
            var op = lookahead;
            this.tokenizer.advance();
            var rhs = this.tokenizer.next();

            if (rhs.getType() == Token.Type.LEFT_PARENTHESIS) {
                this.parentheses_validator.onOpen();
                rhs = parseExpression(this.tokenizer.next(), this.max_precedence);
            }

            // ++VAR
            if (ParsingUtil.isUnaryOperator(rhs)) {
                rhs = this.unary_evaluator.Evaluate(this.tokenizer.next(), rhs, false);
                // TODO: call?
            }

            ParsingUtil.expect(rhs, Token.Type.IDENTIFIER, Token.Type.NUMBER); // TODO ?

            lookahead = this.tokenizer.peekNext(); // TODO VERIFY

            // VAR++
            if (rhs.getType() == Token.Type.IDENTIFIER && ParsingUtil.isUnaryOperator(lookahead)) {
                rhs = this.unary_evaluator.Evaluate(rhs, lookahead, true);
                this.tokenizer.advance();
                lookahead = this.tokenizer.peekNext();
            }

            while (
                    ParsingUtil.isBinaryOperator(lookahead) &&
                            this.precedence.get(lookahead.getType()) < this.precedence.get(op.getType())) {
                rhs = parseExpression(rhs, this.precedence.get(op.getType()) - 1);
                lookahead = this.tokenizer.peekNext();
            }

            lhs = new ValueToken(this.binary_evaluators.get(op.getType()).evaluate(lhs, rhs));

            if (lookahead.getType() == Token.Type.RIGHT_PARENTHESIS) {
                this.parentheses_validator.onClose();
                this.tokenizer.advance();
            }
        }

        return lhs;
    }
}
