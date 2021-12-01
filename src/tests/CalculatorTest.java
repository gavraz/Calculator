package tests;

import main.Calculator;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    @Test
    public void TestCalculator() {
        Calculator calc = new Calculator();
        var input = new ByteArrayInputStream("x=5+3\ny=1+2*4\nz=x*x".getBytes());

        try {
            calc.evaluate(new InputStreamReader(input));
            assertEquals("(x=8.0,y=9.0,z=64.0)", calc.output());
            System.out.println(calc.output());
        } catch (Exception e) {
            System.out.printf("Could not evaluate expression: %s\n", e);
        }
    }
}
