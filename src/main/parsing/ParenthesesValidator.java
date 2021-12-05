package main.parsing;

/**
 * ParenthesesValidator can track and validate parentheses.
 */
public class ParenthesesValidator {
    private int open_count; // tracks the number of open parentheses

    /**
     * Constructs an instance.
     */
    public ParenthesesValidator() {
        this.open_count = 0;
    }

    /**
     * Tracks an opening parentheses.
     */
    public void onOpen() {
        this.open_count++;
    }

    /**
     * Tracks a closing parentheses.
     *
     * @throws Exception if the closing parentheses break validation.
     */
    public void onClose() throws Exception {
        if (this.open_count == 0) {
            throw new Exception("invalid parentheses: closing parentheses has no matching opening");
        }
        this.open_count--;
    }

    /**
     * Validates the parentheses.
     *
     * @throws Exception if validation does not hold.
     */
    public void validate() throws Exception {
        if (this.open_count > 0) {
            throw new Exception("invalid parentheses: unclosed parentheses detected");
        }
    }
}
