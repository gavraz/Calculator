package tests;

import main.tokenization.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenizerTest {

    @Test
    public void TestTokenizer() {
        var tokenizer = new Tokenizer(input);
        var tokens = tokenizer.analyze("1+2");
        assertEquals(3, tokens.size());
        assertEquals(new NumberToken(1), tokens.get(0));
        assertEquals(new Token(Token.Type.OPERATOR_PLUS), tokens.get(1));
        assertEquals(new NumberToken(2), tokens.get(2));
    }
}
