package tests;

import main.tokenization.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NumberTokenTest {

    @Test
    public void TestNumberToken() {
        var t = new NumberToken(10);
        assertEquals(Token.Type.NUMBER, t.GetType());
        assertEquals(10, t.GetValue());
    }

    @Test
    public void TestTryParse() {
        var res = NumberToken.TryParse("1", 0);
        assertNotNull(res);
        assertEquals(1, res.GetValue());

        res = NumberToken.TryParse("aa 123", 3);
        assertNotNull(res);
        assertEquals(123, res.GetValue());

        res = NumberToken.TryParse("a123", 0);
        assertNull(res);
    }
}
