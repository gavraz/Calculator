package main.tokenization;

public class Token {
    public enum Type {
        IDENTIFIER,
        EQUAL, PLUS_EQUAL, MINUS_EQUAL, MUL_EQUAL, DIV_EQUAL,
        UNARY_PLUS_PLUS, UNARY_MINUS_MINUS,
        OPERATOR_PLUS, OPERATOR_MINUS, OPERATOR_MUL, OPERATOR_DIV,
        SEPARATOR_LEFT_PARENTHESIS, SEPARATOR_RIGHT_PARENTHESIS,
        NUMBER,
        Term,
    }

    public static final Token TERM = new Token(Type.Term);
    private final Type type;

    public Token(Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Token o)) {
            return false;
        }

        return o.type == this.type;
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

    public static boolean isBinaryOperator(Token token) {
        switch (token.getType()) {
            case OPERATOR_PLUS, OPERATOR_MINUS, OPERATOR_MUL, OPERATOR_DIV -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }
}
