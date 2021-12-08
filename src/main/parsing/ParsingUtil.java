package main.parsing;

import main.tokenization.Token;

public class ParsingUtil {
    static boolean isUnaryOperator(Token token) {
        return switch (token.getType()) {
            case UNARY_INC, UNARY_DEC -> true;
            default -> false;
        };
    }

    static boolean isBinaryOperator(Token token) {
        return switch (token.getType()) {
            case OPERATOR_PLUS, OPERATOR_MINUS, OPERATOR_MUL, OPERATOR_DIV,
                    EQUAL, PLUS_EQUAL, MINUS_EQUAL, MUL_EQUAL, DIV_EQUAL -> true;
            default -> false;
        };
    }
}
