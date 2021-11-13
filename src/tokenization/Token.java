package tokenization;

enum TokenType {
    IDENTIFIER,
    OPERATOR_PLUS, OPERATOR_MINUS, OPERATOR_MUL, OPERATOR_DIV,
    SEPARATOR_LEFT_PARENTHESIS, SEPARATOR_RIGHT_PARENTHESIS,
    NUMBER,
    Term,
}

public class Token {
    public static final Token TERM = new Token(TokenType.Term);
    private final TokenType type;

    public Token(TokenType type) {
        this.type = type;
    }

    public TokenType GetType() {
        return this.type;
    }

    public static boolean isWhitespace(char c) {
        return c == ' ';
    }

    public static boolean isNumeric(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isAlphabet(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z');
    }
}
