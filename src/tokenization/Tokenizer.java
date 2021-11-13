package tokenization;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Tokenizer {
    public class TokenException extends Exception {
        public TokenException(String err) {
            super(err);
        }
    }

    private static class Factory {
        private static Factory instance;
        private Map<String, TokenType> tokenPool;

        private Factory() {
            this.tokenPool = new HashMap<>();

            // operators
            this.tokenPool.put("+", TokenType.OPERATOR_PLUS);
            this.tokenPool.put("-", TokenType.OPERATOR_MINUS);
            this.tokenPool.put("*", TokenType.OPERATOR_MUL);
            this.tokenPool.put("/", TokenType.OPERATOR_DIV);

            // separators
            this.tokenPool.put("(", TokenType.SEPARATOR_LEFT_PARENTHESIS);
            this.tokenPool.put(")", TokenType.SEPARATOR_RIGHT_PARENTHESIS);
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

            var token = NumberToken.TryParse(line, i);
            if (token != null) {
                return token;
            }

            return IdentifierToken.TryParse(line, i);
        }
    }

    public Tokenizer() {
    }

    public List<Token> Analyze(String line) {
        var tokens = new LinkedList<Token>();

        for (int i = 0; i < line.length(); i++) {

            Token token = Factory.Instance().TryGet(line, i);
            if (token == null) {
                return null;
            }

            if (token.GetType() == TokenType.Term) {
                return tokens;
            }

            tokens.add(token);
        }

        return tokens;
    }
}
