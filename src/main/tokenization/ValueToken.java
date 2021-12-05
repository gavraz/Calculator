package main.tokenization;

/**
 * ValueToken is a token that has a value.
 */
public class ValueToken extends Token {
    private final double value;

    /**
     * Constructs a token with the given value.
     *
     * @param value the value of the token.
     */
    public ValueToken(double value) {
        super(Type.NUMBER);
        this.value = value;
    }

    /**
     * Returns the value of the token.
     *
     * @return the value of the token.
     */
    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ValueToken o)) {
            return false;
        }

        return super.equals(o) && o.value == this.value;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + this.value;
    }
}
