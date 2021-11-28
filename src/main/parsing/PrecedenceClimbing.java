package main.parsing;

import main.tokenization.IdentifierToken;
import main.tokenization.NumberToken;
import main.tokenization.Token;
import main.tokenization.Tokenizer;

import java.util.HashMap;
import java.util.Map;

public class PrecedenceClimbing {
    private final Map<Token.Type, Evaluator> evaluators;
    private final Map<Token.Type, Integer> precedence; // lower precedes higher
    private final Tokenizer tokenizer;
    private final Map<String, Integer> vars;

    public PrecedenceClimbing() {
        this.evaluators = new HashMap<>();
        this.precedence = new HashMap<>();
        this.tokenizer = new Tokenizer();
        this.vars = new HashMap<>();

        init_evaluators();
        init_precedence();
    }
    
    private void init_evaluators() {
        this.evaluators.put(Token.Type.OPERATOR_PLUS, new Evaluator() {
            @Override
            public double evaluate(Token lhs, Token rhs) {
                return 0;
            }
        })
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

    private static void expect(Token token, Token.Type... types) throws Exception {
        for (Token.Type type : types) {
            if (token.getType() == type) {
                return;
            }
        }

        throw new Exception("parsing failed");
    }

    private double evaluate(Token token) throws Exception {
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

    public void parse(String line) throws Exception {
        this.tokenizer.SetInputStream();

        var token = this.tokenizer.Next();
        expect(token, Token.Type.IDENTIFIER);

        this.parseExpression(token, 0);
    }

    private double parseExpression(Token lhs, int max_precedence) throws Exception {
        var lookahead = this.tokenizer.PeekNext();

        while (Token.isBinaryOperator(lookahead) &&
                this.precedence.get(lookahead.getType()) <= max_precedence) {
            var op = lookahead;
            this.tokenizer.Advance();
            var rhs = this.tokenizer.Next();
            expect(rhs, Token.Type.IDENTIFIER, Token.Type.NUMBER);

            var rhs_val = evaluate(rhs);
            lookahead = this.tokenizer.PeekNext();

            while (Token.isBinaryOperator(lookahead) &&
                    this.precedence.get(lookahead.getType()) < this.precedence.get(op.getType())) {
                // TODO: or a right-associative operator whose precedence is equal to op's
                double rhsValue = parseExpression(rhs, this.precedence.get(op.getType()) - 1);
                lookahead = this.tokenizer.PeekNext();
            }


            double lhsValue = the result of applying op with operands lhs and rhsValue
        }

        return lhsValue;
    }
}
