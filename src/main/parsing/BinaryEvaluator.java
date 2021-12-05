package main.parsing;

import main.tokenization.Token;

/**
 * BinaryEvaluator describes an object that can be used to evaluate a binary operation.
 */
interface BinaryEvaluator {
    /**
     * evaluates a binary operation that operates on lhs and rhs.
     *
     * @param lhs the left hand-side operand.
     * @param rhs the right hand-side operand.
     * @return the value of performing a binary operation on the operands.
     * @throws Exception if the operation could not be evaluated.
     */
    double evaluate(Token lhs, Token rhs) throws Exception;
}