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
    public void TestTokenizerNumber() {
        var tokens = Tokenizer.tokenize("1 10 10.5");
        assertNotNull(tokens);
        assertEquals(4, tokens.size());
        assertEquals(new ValueToken(1), tokens.get(0));
        assertEquals(new ValueToken(10), tokens.get(1));
        assertEquals(new ValueToken(10.5), tokens.get(2));
        assertEquals(new Token(Token.Type.Term), tokens.get(3));

        // note: we currently don't support it
        //assertEquals(new ValueToken(-10), tokens.get(3));
    }

    @Test
    public void TestTokenizerIdentifier() {
        var tokens = Tokenizer.tokenize("x xx xx1 xx123");
        assertNotNull(tokens);
        assertEquals(5, tokens.size());
        assertEquals(new IdentifierToken("x"), tokens.get(0));
        assertEquals(new IdentifierToken("xx"), tokens.get(1));
        assertEquals(new IdentifierToken("xx1"), tokens.get(2));
        assertEquals(new IdentifierToken("xx123"), tokens.get(3));
        assertEquals(new Token(Token.Type.Term), tokens.get(4));
    }

    @Test
    public void TestTokenizerParentheses() {
        var tokens = Tokenizer.tokenize("( (( () )) )");
        assertNotNull(tokens);
        assertEquals(9, tokens.size());
        assertEquals(new Token(Token.Type.LEFT_PARENTHESIS), tokens.get(0));
        assertEquals(new Token(Token.Type.LEFT_PARENTHESIS), tokens.get(1));
        assertEquals(new Token(Token.Type.LEFT_PARENTHESIS), tokens.get(2));
        assertEquals(new Token(Token.Type.LEFT_PARENTHESIS), tokens.get(3));
        assertEquals(new Token(Token.Type.RIGHT_PARENTHESIS), tokens.get(4));
        assertEquals(new Token(Token.Type.RIGHT_PARENTHESIS), tokens.get(5));
        assertEquals(new Token(Token.Type.RIGHT_PARENTHESIS), tokens.get(6));
        assertEquals(new Token(Token.Type.RIGHT_PARENTHESIS), tokens.get(7));
        assertEquals(new Token(Token.Type.Term), tokens.get(8));
    }


    @Test
    public void TestTokenizerBinary() {
        var tokens = Tokenizer.tokenize("+-*/");
        assertNotNull(tokens);
        assertEquals(5, tokens.size());
        assertEquals(new Token(Token.Type.OPERATOR_PLUS), tokens.get(0));
        assertEquals(new Token(Token.Type.OPERATOR_MINUS), tokens.get(1));
        assertEquals(new Token(Token.Type.OPERATOR_MUL), tokens.get(2));
        assertEquals(new Token(Token.Type.OPERATOR_DIV), tokens.get(3));
        assertEquals(new Token(Token.Type.Term), tokens.get(4));
    }

    @Test
    public void TestTokenizerUnary() {
        var tokens = Tokenizer.tokenize("++ --");
        assertNotNull(tokens);
        assertEquals(3, tokens.size());
        assertEquals(new Token(Token.Type.UNARY_INC), tokens.get(0));
        assertEquals(new Token(Token.Type.UNARY_DEC), tokens.get(1));
        assertEquals(new Token(Token.Type.Term), tokens.get(2));
    }

    @Test
    public void TestTokenizerEqual() {
        var tokens = Tokenizer.tokenize("=+=-=*=/=");
        assertNotNull(tokens);
        assertEquals(6, tokens.size());
        assertEquals(new Token(Token.Type.EQUAL), tokens.get(0));
        assertEquals(new Token(Token.Type.PLUS_EQUAL), tokens.get(1));
        assertEquals(new Token(Token.Type.MINUS_EQUAL), tokens.get(2));
        assertEquals(new Token(Token.Type.MUL_EQUAL), tokens.get(3));
        assertEquals(new Token(Token.Type.DIV_EQUAL), tokens.get(4));
        assertEquals(new Token(Token.Type.Term), tokens.get(5));
    }

    @Test
    public void TestAll() {
        var tokens = Tokenizer.tokenize("x=1+ y++");
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
