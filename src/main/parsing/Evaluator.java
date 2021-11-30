package main.parsing;

import main.tokenization.Token;

public interface Evaluator {
    Valuable evaluate(Valuable lhs, Valuable rhs);
}