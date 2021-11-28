package main.tokenization;

public class NumberToken extends Token {
    private final double value;

    public NumberToken(double value) {
        super(Type.NUMBER);
        this.value = value;
    }

    public static NumberToken tryParse(String s, int i) {
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

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof NumberToken o)) {
            return false;
        }

        return super.equals(o) && o.value == this.value;
    }
}
