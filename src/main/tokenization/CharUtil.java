package main.tokenization;

public class CharUtil {
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

    public static boolean isOperator(char c) {
        return switch (c) {
            case '+', '-', '/', '*' -> true;
            default -> false;
        };
    }
}
