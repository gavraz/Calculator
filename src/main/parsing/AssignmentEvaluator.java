package main.parsing;

import main.tokenization.IdentifierToken;

public interface AssignmentEvaluator {
    Valuable evaluate(IdentifierToken lhs, Valuable rhs);
}
