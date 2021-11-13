package tokenization;

public class IdentifierToken extends Token {
    private final String id;

    public IdentifierToken(String id) {
        super(TokenType.IDENTIFIER);
        this.id = id;
    }

    public static Token TryParse(String s, int j) {
        // TODO IMPL
        return null;
    }

    public String GetID() {
        return this.id;
    }
}
