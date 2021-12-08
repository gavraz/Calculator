package tests;

import main.parsing.PrecedenceClimbing;
import main.tokenization.Token;
import main.tokenization.ValueToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParsingTest {
    @Test
    public void TestAssignment() {
        PrecedenceClimbing parser = new PrecedenceClimbing();
        try {
            var res = parser.parse("x=5+2*3");
            assertEquals(Token.Type.NUMBER, res.getType());
            assertEquals(11.0, ((ValueToken) res).getValue());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }
}
