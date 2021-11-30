package main.parsing;

public class Value {
    public static Valuable New(double value) {
        return () -> value;
    }
}
