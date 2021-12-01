package main.tokenization;

public class NumberToken extends Token {
    private final double value;

    public NumberToken(double value) {
        super(Type.NUMBER);
        this.value = value;
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
