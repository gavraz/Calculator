package main.tokenization;

public class Token {
    public enum Type {
        IDENTIFIER,
        EQUAL, PLUS_EQUAL, MINUS_EQUAL, MUL_EQUAL, DIV_EQUAL,
        UNARY_INC, UNARY_DEC,
        OPERATOR_PLUS, OPERATOR_MINUS, OPERATOR_MUL, OPERATOR_DIV,
        LEFT_PARENTHESIS, RIGHT_PARENTHESIS,
        NUMBER,
        Term,
    }

    private static String asString(Type t) {
        return switch (t) {
            case IDENTIFIER -> "identifier";
            case EQUAL -> "=";
            case PLUS_EQUAL -> "+=";
            case MINUS_EQUAL -> "-=";
            case MUL_EQUAL -> "*=";
            case DIV_EQUAL -> "/=";
            case UNARY_INC -> "++";
            case UNARY_DEC -> "--";
            case OPERATOR_PLUS -> "+";
            case OPERATOR_MINUS -> "-";
            case OPERATOR_MUL -> "*";
            case OPERATOR_DIV -> "/";
            case LEFT_PARENTHESIS -> "(";
            case RIGHT_PARENTHESIS -> ")";
            case NUMBER -> "number";
            case Term -> "term";
        };
    }

    public static final Token TERM = new Token(Type.Term);
    private final Type type;

    public Token(Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Token o)) {
            return false;
        }

        return o.type == this.type;
    }

    @Override
    public String toString() {
        return asString(this.type);
    }
}
