package main.tokenization;


class Factory {
    private static Factory instance;
    private final TryGetter doubleSymbolGetter;
    private final TryGetter singleSymbolGetter;
    private final TryGetter numberGetter;
    private final TryGetter identifierGetter;

    // TODO add ut i++; i+++; i++ +3...
    private Factory() {

        this.doubleSymbolGetter = (input, i) -> {
            if (i + 1 >= input.length()) {
                return TryGetter.Result.None;
            }
            String symbols = "" + input.charAt(i) + input.charAt(i + 1);

            Token.Type type;
            switch (symbols) {
                case "+=" -> type = Token.Type.PLUS_EQUAL;
                case "-=" -> type = Token.Type.MINUS_EQUAL;
                case "*=" -> type = Token.Type.MUL_EQUAL;
                case "/=" -> type = Token.Type.DIV_EQUAL;
                case "++" -> type = Token.Type.UNARY_INC;
                case "--" -> type = Token.Type.UNARY_DEC;
                default -> type = null;
            }
            ;

            if (type == null) {
                return TryGetter.Result.None;
            }
            return new TryGetter.Result(new Token(type), 2);
        };

        this.singleSymbolGetter = (input, i) -> {
            if (i >= input.length()) {
                return TryGetter.Result.None;
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
                return TryGetter.Result.None;
            }
            return new TryGetter.Result(new Token(type), 1);
        };

        this.numberGetter = (input, i) -> {
            int begin = i;
            for (; i < input.length(); i++) {
                if (CharUtil.isWhitespace(input.charAt(i))) {
                    break;
                }
                if (!CharUtil.isNumeric(input.charAt(i))) {
                    break;
                }
            }

            if (begin == i) {
                return TryGetter.Result.None;
            }

            return new TryGetter.Result(
                    new NumberToken(Integer.parseInt(input, begin, i, 10)),
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
                return TryGetter.Result.None;
            }

            return new TryGetter.Result(
                    new IdentifierToken(input.substring(begin, i)),
                    i - begin
            );
        };
    }

    public static Factory Instance() {
        if (instance == null) {
            instance = new Factory();
        }

        return instance;
    }

    // TODO: document the major optimization: c_str + state machine!

    public TryGetter.Result TryGet(String line, int i) {
        if (i >= line.length()) {
            return new TryGetter.Result(Token.TERM, 0);
        }

        /// Tokenization order:
        ///     Inc/Dec; Assignment OPs; Arithmetic OPs;
        ///     Number; Variable;
        ///     Parentheses;
        /// TWO SYMBOLS --> SINGLE SYMBOL --> NUM --> VAR

        var token = this.doubleSymbolGetter.tryGetNext(line, i);
        if (token.token != null) {
            return token;
        }

        token = this.singleSymbolGetter.tryGetNext(line, i);
        if (token.token != null) {
            return token;
        }

        token = this.numberGetter.tryGetNext(line, i);
        if (token.token != null) {
            return token;
        }

        return this.identifierGetter.tryGetNext(line, i);
    }
}
