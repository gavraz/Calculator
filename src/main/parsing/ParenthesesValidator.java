package main.parsing;

public class ParenthesesValidator {
    private int open_count;

    public ParenthesesValidator() {
        this.open_count = 0;
    }

    public void onOpen() {
        this.open_count++;
    }

    public void onClose() throws Exception {
        if (this.open_count == 0) {
            throw new Exception("invalid parentheses: closing parentheses has no matching opening");
        }
        this.open_count--;
    }

    public void validate() throws Exception {
        if (this.open_count > 0) {
            throw new Exception("invalid parentheses: unclosed parentheses detected");
        }
    }
}
