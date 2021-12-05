package tests;

import main.Calculator;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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

        input = new ByteArrayInputStream("x=(1*3+1)+(2+2)*(3-5)".getBytes());
        try {
            calc.evaluate(new InputStreamReader(input));
            assertEquals("(x=-4.0)", calc.output());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestBadParentheses() {
        Calculator calc = new Calculator();
        try {
            calc.evaluate(new InputStreamReader(new ByteArrayInputStream("x=(2)+((3".getBytes())));
            fail("should have failed: expected exception");
        } catch (Exception ignored) {
        }

        try {
            calc.evaluate(new InputStreamReader(new ByteArrayInputStream("x=(2)+((3)))".getBytes())));
            fail("should have failed: expected exception");
        } catch (Exception ignored) {
        }

        try {
            calc.evaluate(new InputStreamReader(new ByteArrayInputStream("x=)(".getBytes())));
            fail("should have failed: expected exception");
        } catch (Exception ignored) {
        }

        try {
            calc.evaluate(new InputStreamReader(new ByteArrayInputStream("x=(((((4)))))+(1)".getBytes())));
        } catch (Exception ignored) {
        }
    }

    @Test
    public void TestIncDec() {
        double x = 5;
        double y = 2;
        double z = x++ + y++;

        var input = new ByteArrayInputStream("x=5\ny=2\nz=x++ + y++".getBytes());
        Calculator calc = new Calculator();
        try {
            calc.evaluate(new InputStreamReader(input));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Double> exp = Map.of("x", x, "y", y, "z", z);
        assertEquals(exp, calc.getVars());

        calc = new Calculator();
        x = 1;
        y = (x++);
        z = x++;
        input = new ByteArrayInputStream("x=1\ny=(x++)\nz=x++".getBytes());
        try {
            calc.evaluate(new InputStreamReader(input));
        } catch (Exception e) {
            e.printStackTrace();
        }
        exp = Map.of("x", x, "y", y, "z", z);
        assertEquals(exp, calc.getVars());
    }

    @Test
    public void TestBebe() {
        double x = 7;
        double y = --x*3+5*(x++*2)/4;
        double z = x++*(--y+2)-5*x+y++;
        double w = 2+3*x++;

        var input = new ByteArrayInputStream("x=7\ny=--x*3+5*(x++*2)/4\nz=x++*(--y+2)-5*x+y++\nw=2+3*x++".getBytes());
        Calculator calc = new Calculator();
        try {
            calc.evaluate(new InputStreamReader(input));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Double> exp = Map.of("x", x, "y", y, "z", z, "w", w);
        assertEquals(exp, calc.getVars());
    }
}
