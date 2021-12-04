package main.tokenization;

interface TryGetter {
    // TODO ToAsk: is there any new java trick that can bypass this? filling the GC with garbage...
    class Result{
        static Result None = new Result(null, 0);
        Token token;
        int consumed;

        public Result(Token token, int consumed) {
            this.token = token;
            this.consumed = consumed;
        }
    }
    Result tryGetNext(String input, int i);
}
