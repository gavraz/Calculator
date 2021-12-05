package main.tokenization;

/**
 * IdentifierToken represents an identifiable token.
 */
public class IdentifierToken extends Token {
    private final String id;

    /**
     * Constructs a new token.
     *
     * @param id the id of the token
     */
    public IdentifierToken(String id) {
        super(Type.IDENTIFIER);
        this.id = id;
    }

    /**
     * Returns the id of the token.
     *
     * @return the id of the token.
     */
    public String getID() {
        return this.id;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + this.id;
    }
}
