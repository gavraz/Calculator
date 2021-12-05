package tests;

import main.tokenization.IdentifierToken;
import main.tokenization.Token;
import main.tokenization.Tokenizer;
import main.tokenization.ValueToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TokenizerTest {

    @Test
    public void TestTokenizer() {
        var tokens = Tokenizer.tokenize("1+2");
        assertNotNull(tokens);
        assertEquals(4, tokens.size());
        assertEquals(new ValueToken(1), tokens.get(0));
        assertEquals(new Token(Token.Type.OPERATOR_PLUS), tokens.get(1));
        assertEquals(new ValueToken(2), tokens.get(2));
        assertEquals(new Token(Token.Type.Term), tokens.get(3));
    }

    @Test
    public void TestTokenizerUnary() {
        var tokens = Tokenizer.tokenize("x++");
        assertNotNull(tokens);
        assertEquals(3, tokens.size());
        assertEquals(new IdentifierToken("x"), tokens.get(0));
        assertEquals(new Token(Token.Type.UNARY_INC), tokens.get(1));
        assertEquals(new Token(Token.Type.Term), tokens.get(2));

        tokens = Tokenizer.tokenize("x=1+ y++");
        assertNotNull(tokens);
        assertEquals(7, tokens.size());
        assertEquals(new IdentifierToken("x"), tokens.get(0));
        assertEquals(new Token(Token.Type.EQUAL), tokens.get(1));
        assertEquals(new ValueToken(1), tokens.get(2));
        assertEquals(new Token(Token.Type.OPERATOR_PLUS), tokens.get(3));
        assertEquals(new IdentifierToken("y"), tokens.get(4));
        assertEquals(new Token(Token.Type.UNARY_INC), tokens.get(5));
        assertEquals(new Token(Token.Type.Term), tokens.get(6));
    }
}
