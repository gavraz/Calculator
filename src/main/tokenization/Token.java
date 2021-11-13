package main.tokenization;

public class Token {
    public enum Type {
        IDENTIFIER,
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

    public Type GetType() {
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
