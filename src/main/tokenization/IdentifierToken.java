package main.tokenization;

public class IdentifierToken extends Token {
    private final String id;

    public IdentifierToken(String id) {
        super(Type.IDENTIFIER);
        this.id = id;
    }

    public static Token tryParse(String s, int i) {
        if (!Token.isAlphabet(s.charAt(i))) {
            return null;
        }

        int begin = i;
        for (; i < s.length(); i++) {
            var current = s.charAt(i);
            if (Token.isWhitespace(current)) {
                break;
            }
            if (!Token.isNumeric(current) && !Token.isAlphabet(current)) {
                break;
            }
        }

        if (begin == i) {
            return null;
        }

        return new IdentifierToken(s.substring(begin, i));
    }

    public String getID() {
        return this.id;
    }
}
