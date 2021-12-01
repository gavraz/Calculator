package main.tokenization;

public class IdentifierToken extends Token {
    private final String id;

    public IdentifierToken(String id) {
        super(Type.IDENTIFIER);
        this.id = id;
    }

    public String getID() {
        return this.id;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + this.id;
    }
}
