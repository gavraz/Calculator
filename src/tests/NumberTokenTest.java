package tests;

import main.tokenization.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NumberTokenTest {

    @Test
    public void TestNumberToken() {
        var t = new NumberToken(10);
        assertEquals(Token.Type.NUMBER, t.getType());
        assertEquals(10, t.getValue());
    }

    @Test
    public void TestTryParse() {
        var res = NumberToken.tryParse("1", 0);
        assertNotNull(res);
        assertEquals(1, res.getValue());

        res = NumberToken.tryParse("aa 123", 3);
        assertNotNull(res);
        assertEquals(123, res.getValue());

        res = NumberToken.tryParse("a123", 0);
        assertNull(res);
    }
}
