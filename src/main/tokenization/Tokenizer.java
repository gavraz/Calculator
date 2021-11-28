package main.tokenization;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Tokenizer {

    public class TokenizationException extends Exception {
        public TokenizationException(String err) {
            super(err);
        }
    }

    private static class Factory {
        private static Factory instance;
        private final Map<String, Token.Type> tokenPool;

        private Factory() {
            this.tokenPool = new HashMap<>();

            // TODO add unaries

            // operators
            this.tokenPool.put("=", Token.Type.EQUAL);
            this.tokenPool.put("+=", Token.Type.PLUS_EQUAL);
            this.tokenPool.put("-=", Token.Type.MINUS_EQUAL);
            this.tokenPool.put("*=", Token.Type.MUL_EQUAL);
            this.tokenPool.put("/=", Token.Type.DIV_EQUAL);

            this.tokenPool.put("+", Token.Type.OPERATOR_PLUS);
            this.tokenPool.put("-", Token.Type.OPERATOR_MINUS);
            this.tokenPool.put("*", Token.Type.OPERATOR_MUL);
            this.tokenPool.put("/", Token.Type.OPERATOR_DIV);

            // separators
            this.tokenPool.put("(", Token.Type.SEPARATOR_LEFT_PARENTHESIS);
            this.tokenPool.put(")", Token.Type.SEPARATOR_RIGHT_PARENTHESIS);
        }

        public static Factory Instance() {
            if (instance == null) {
                instance = new Factory();
            }

            return instance;
        }

        public Token TryGet(String line, int i) {
            if (i >= line.length()) {
                return Token.TERM;
            }

            char current = line.charAt(i);
            var type = this.tokenPool.get(current + "");
            if (type != null) {
                return new Token(type);
            }

            var token = NumberToken.tryParse(line, i);
            if (token != null) {
                return token;
            }

            return IdentifierToken.tryParse(line, i);
        }
    }

    public Tokenizer() {
    }

    public Token PeekNext() {

    }

    public Token Next() {

    }

    public List<Token> Analyze(String line) {
        var tokens = new LinkedList<Token>();

        for (int i = 0; i < line.length(); i++) {

            Token token = Factory.Instance().TryGet(line, i);
            if (token == null) {
                return null;
            }

            if (token.getType() == Token.Type.Term) {
                return tokens;
            }

            tokens.add(token);
        }

        return tokens;
    }
}
