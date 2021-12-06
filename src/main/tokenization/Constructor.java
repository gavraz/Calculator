package main.tokenization;

// interface Constructor represents an instance that can construct tokens.
interface Constructor {
    // Result of construction attempt.
    class Result {
        static Result None = new Result(null, 0);
        Token token;
        int consumed;

        public Result(Token token, int consumed) {
            this.token = token;
            this.consumed = consumed;
        }
    }

    /**
     * Tries to construct the next token from str at the given position.
     *
     * @param str the string to construct from.
     * @param i   the position to construct at.
     * @return a Result representing the constructed token and the number of consumed characters; Result.None if it could not construct.
     * @throws TokenizationException if an error occurred during tokenization.
     */
    Result tryConstructNext(String str, int i) throws TokenizationException;
}
