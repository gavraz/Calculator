package tokenization;

public class NumberToken extends Token {
    private final double value;

    public NumberToken(double value) {
        super(TokenType.NUMBER);
        this.value = value;
    }

    public static NumberToken TryParse(String s, int i) {
        int begin = i;
        for (; i < s.length(); i++) {
            if (Token.isWhitespace(s.charAt(i))) {
                break;
            }
            if (!Token.isNumeric(s.charAt(i))) {
                break;
            }
        }

        if (begin == i) {
            return null;
        }

        return new NumberToken(Integer.parseInt(s, begin, i, 10));
    }

    public double GetValue() {
        return value;
    }
}
