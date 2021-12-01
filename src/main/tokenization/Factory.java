package main.tokenization;

import java.io.BufferedInputStream;


class Factory {
    private static Factory instance;
    private final TryGetter doubleSymbolGetter;
    private final TryGetter singleSymbolGetter;
    private final TryGetter numberGetter;
    private final TryGetter identifierGetter;

    // TODO add ut i++; i+++; i++ +3...
    private Factory() {

        this.doubleSymbolGetter = (input, i) -> {
            if (i + 1 > input.length()) {
                return null; // TODO maybe term?
            }
            String symbols = "" + input.charAt(i) + input.charAt(i + 1);
            return switch (symbols) {
                case "+=" -> new Token(Token.Type.PLUS_EQUAL);
                case "-=" -> new Token(Token.Type.MINUS_EQUAL);
                case "*=" -> new Token(Token.Type.MUL_EQUAL);
                case "/=" -> new Token(Token.Type.DIV_EQUAL);
                case "++" -> new Token(Token.Type.UNARY_INC);
                case "--" -> new Token(Token.Type.UNARY_DEC);
                default -> null;
            };
        };

        this.singleSymbolGetter = (input, i) -> switch (input.charAt(i)) {
            case '+' -> new Token(Token.Type.OPERATOR_PLUS);
            case '-' -> new Token(Token.Type.OPERATOR_MINUS);
            case '*' -> new Token(Token.Type.OPERATOR_MUL);
            case '/' -> new Token(Token.Type.OPERATOR_DIV);
            case '(' -> new Token(Token.Type.LEFT_PARENTHESIS);
            case ')' -> new Token(Token.Type.RIGHT_PARENTHESIS);
            case '=' -> new Token(Token.Type.EQUAL);
            default -> null;
        };

        this.numberGetter = (input, i) -> {
            int begin = i;
            for (; i < input.length(); i++) {
                if (Token.isWhitespace(input.charAt(i))) {
                    break;
                }
                if (!Token.isNumeric(input.charAt(i))) {
                    break;
                }
            }

            if (begin == i) {
                return null;
            }

            return new NumberToken(Integer.parseInt(input, begin, i, 10));
        };

        this.identifierGetter = (input, i) -> {
            if (!Token.isAlphabet(input.charAt(i))) {
                return null;
            }

            int begin = i;
            for (; i < input.length(); i++) {
                var current = input.charAt(i);
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

            return new IdentifierToken(input.substring(begin, i));
        };
    }

    public static Factory Instance() {
        if (instance == null) {
            instance = new Factory();
        }

        return instance;
    }

    // TODO: document the major optimization: c_str + state machine!

    public Token TryGet(String line, int i) {
        if (i >= line.length()) {
            return Token.TERM;
        }

        /// Tokenization order:
        ///     Inc/Dec; Assignment OPs; Arithmetic OPs;
        ///     Number; Variable;
        ///     Parentheses;
        /// TWO SYMBOLS --> SINGLE SYMBOL --> NUM --> VAR

        var token = this.doubleSymbolGetter.tryGetNext(line, i);
        if (token != null) {
            return token;
        }

        token = this.singleSymbolGetter.tryGetNext(line, i);
        if (token != null) {
            return token;
        }

        token = this.numberGetter.tryGetNext(line, i);
        if (token != null) {
            return token;
        }

        return this.identifierGetter.tryGetNext(line, i);
    }
}
