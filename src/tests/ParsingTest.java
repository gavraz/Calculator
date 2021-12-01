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
    public void TestTokenizer() {
        PrecedenceClimbing parser = new PrecedenceClimbing();
        try {
            var res = parser.parse("x=5");
            assertEquals(5.0, res);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
