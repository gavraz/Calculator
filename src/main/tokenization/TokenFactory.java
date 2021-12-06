package main.tokenization;

/**
 * TokenFactory is a singleton that can construct tokens.
 */
class TokenFactory {
    private static TokenFactory instance;

    private final Constructor doubleSymbolUnaryGetter;
    private final Constructor doubleSymbolAssignmentGetter;
    private final Constructor singleSymbolGetter;
    private final Constructor numberGetter;
    private final Constructor identifierGetter;

    private TokenFactory() {
        this.doubleSymbolUnaryGetter = (input, i) -> {
            if (i + 1 >= input.length()) {
                return Constructor.Result.None;
            }
            String symbols = "" + input.charAt(i) + input.charAt(i + 1);

            Token.Type type;
            switch (symbols) {
                case "++" -> type = Token.Type.UNARY_INC;
                case "--" -> type = Token.Type.UNARY_DEC;
                default -> type = null;
            }

            if (type == null) {
                return Constructor.Result.None;
            }

            if (i + 2 < input.length() && CharUtil.isOperator(input.charAt(i + 2))) {
                throw new TokenizationException("unexpected operator after double-symbol operator");
            }

            return new Constructor.Result(new Token(type), 2);
        };

        this.doubleSymbolAssignmentGetter = (input, i) -> {
            if (i + 1 >= input.length()) {
                return Constructor.Result.None;
            }
            String symbols = "" + input.charAt(i) + input.charAt(i + 1);

            Token.Type type;
            switch (symbols) {
                case "+=" -> type = Token.Type.PLUS_EQUAL;
                case "-=" -> type = Token.Type.MINUS_EQUAL;
                case "*=" -> type = Token.Type.MUL_EQUAL;
                case "/=" -> type = Token.Type.DIV_EQUAL;
                default -> type = null;
            }

            if (type == null) {
                return Constructor.Result.None;
            }
            return new Constructor.Result(new Token(type), 2);
        };

        this.singleSymbolGetter = (input, i) -> {
            if (i >= input.length()) {
                return Constructor.Result.None;
            }

            Token.Type type;
            switch (input.charAt(i)) {
                case '+' -> type = Token.Type.OPERATOR_PLUS;
                case '-' -> type = Token.Type.OPERATOR_MINUS;
                case '*' -> type = Token.Type.OPERATOR_MUL;
                case '/' -> type = Token.Type.OPERATOR_DIV;
                case '(' -> type = Token.Type.LEFT_PARENTHESIS;
                case ')' -> type = Token.Type.RIGHT_PARENTHESIS;
                case '=' -> type = Token.Type.EQUAL;
                default -> type = null;
            }

            if (type == null) {
                return Constructor.Result.None;
            }
            return new Constructor.Result(new Token(type), 1);
        };

        this.numberGetter = (input, i) -> {
            int begin = i;
            for (; i < input.length(); i++) {
                var c = input.charAt(i);
                if (CharUtil.isWhitespace(c)) {
                    break;
                }
                if (c != '.' && !CharUtil.isNumeric(c)) {
                    break;
                }
            }

            if (begin == i) {
                return Constructor.Result.None;
            }

            return new Constructor.Result(
                    new ValueToken(Double.parseDouble(input.substring(begin, i))),
                    i - begin
            );
        };

        this.identifierGetter = (input, i) -> {
            if (!CharUtil.isAlphabet(input.charAt(i))) {
                return null;
            }

            int begin = i;
            for (; i < input.length(); i++) {
                var current = input.charAt(i);
                if (CharUtil.isWhitespace(current)) {
                    break;
                }
                if (!CharUtil.isNumeric(current) && !CharUtil.isAlphabet(current)) {
                    break;
                }
            }

            if (begin == i) {
                return Constructor.Result.None;
            }

            return new Constructor.Result(
                    new IdentifierToken(input.substring(begin, i)),
                    i - begin
            );
        };
    }

    private static int consumeWhitespaces(String s, int i) {
        int consumed = 0;
        while (i < s.length() && CharUtil.isWhitespace(s.charAt(i))) {
            i++;
            consumed++;
        }

        return consumed;
    }

    /**
     * Returns the singleton factory instance.
     *
     * @return the TokenFactory instance
     */
    public static TokenFactory instance() {
        if (instance == null) {
            instance = new TokenFactory();
        }

        return instance;
    }

    /**
     * Tries to construct the next token from the provided string at the given position.
     *
     * @param str the string to construct from.
     * @param i   the position to try to construct at.
     * @return a Construct.Result which is the constructed token and the number of consumed characters.
     * @throws TokenizationException if the string could not be tokenized.
     */
    public Constructor.Result tryConstructNext(String str, int i) throws TokenizationException {

        int consumed = consumeWhitespaces(str, i);
        i += consumed;

        if (i >= str.length()) {
            return new Constructor.Result(Token.TERM, 0);
        }

        /// Tokenization order:
        ///     Inc/Dec; Assignment OPs; Arithmetic OPs;
        ///     Number; Variable;
        ///     Parentheses;
        /// DOUBLE SYMBOLS --> SINGLE SYMBOL --> NUM --> VAR

        var token = this.doubleSymbolUnaryGetter.tryConstructNext(str, i);
        if (token.token != null) {
            token.consumed += consumed;
            return token;
        }

        token = this.doubleSymbolAssignmentGetter.tryConstructNext(str, i);
        if (token != Constructor.Result.None) {
            token.consumed += consumed;
            return token;
        }

        token = this.singleSymbolGetter.tryConstructNext(str, i);
        if (token != Constructor.Result.None) {
            token.consumed += consumed;
            return token;
        }

        token = this.numberGetter.tryConstructNext(str, i);
        if (token != Constructor.Result.None) {
            token.consumed += consumed;
            return token;
        }

        token = this.identifierGetter.tryConstructNext(str, i);
        token.consumed += consumed;

        if (token != Constructor.Result.None) {
            return token;
        }

        throw new TokenizationException("could not tokenize next token");
    }
}
