package tests;

import main.parsing.PrecedenceClimbing;
import main.tokenization.NumberToken;
import main.tokenization.Token;
import main.tokenization.Tokenizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParsingTest {
    @Test
    public void TestAssignment() {
        PrecedenceClimbing parser = new PrecedenceClimbing();
        try {
            var res = parser.parse("x=5+2*3");
            assertEquals(Token.Type.NUMBER, res.getType());
            assertEquals(11.0, ((NumberToken)res).getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}