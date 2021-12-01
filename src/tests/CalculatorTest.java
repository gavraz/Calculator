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

    @Test
    public void TestParentheses() {
        Calculator calc = new Calculator();
        var input = new ByteArrayInputStream("x=((1+2)*5)".getBytes());

        try {
            calc.evaluate(new InputStreamReader(input));
            assertEquals("(x=15.0)", calc.output());
        } catch (Exception e) {
            e.printStackTrace();
        }

        input = new ByteArrayInputStream("x=((5+1)*(2+1)+3)".getBytes());
        try {
            calc.evaluate(new InputStreamReader(input));
            assertEquals("(x=21.0)", calc.output());
        } catch (Exception e) {
            e.printStackTrace();
        }

        input = new ByteArrayInputStream("x=((5+1)+(1+1)*(3+0))".getBytes());
        try {
            calc.evaluate(new InputStreamReader(input));
            assertEquals("(x=12.0)", calc.output());
        } catch (Exception e) {
            e.printStackTrace();
        }

        input = new ByteArrayInputStream("x=(6)".getBytes());
        try {
            calc.evaluate(new InputStreamReader(input));
            assertEquals("(x=6.0)", calc.output());
        } catch (Exception e) {
            e.printStackTrace();
        }

        input = new ByteArrayInputStream("x=((1+2))+(3)".getBytes());
        try {
            calc.evaluate(new InputStreamReader(input));
            assertEquals("(x=6.0)", calc.output());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
