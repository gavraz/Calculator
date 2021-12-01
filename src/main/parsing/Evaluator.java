package main.parsing;

import main.tokenization.Token;

public interface Evaluator {
    double evaluate(Token lhs, Token rhs) throws Exception;
}