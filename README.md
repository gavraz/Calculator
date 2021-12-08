# Calculator

<b>An implementation of a basic programming-language calculator.</b>

### Algorithm: Precedence Climbing

* Wiki: https://en.wikipedia.org/wiki/Operator-precedence_parser
* Additional resource: https://www.engr.mun.ca/~theo/Misc/exp_parsing.htm#more_climbing

### Features

* Arithmetic: +,-,*,/
* Parentheses
* Variables
* Assignment operators: =,+=,-=,*=,/=
* Unary increment/decrement

### Grammar

1. NUM:=[0-9]*
2. VAR:=[a-z;A-Z]+[a-z;A-Z;0-9]*
3. TERMINAL:={NUM|VAR}
4. ASSIGN:={VAR}[=,+=,-=,*=,/=]{EXPR}
5. EXPR:=  ({EXPR}) || ASSIGN || SUM || MUL || DIV || SUB || {VAR}++ || ++{VAR} || {VAR}-- || --{VAR}

### Optional Optimizations

Tokenization Factory:
1. A state machine tokenizer (disadvantage: less clear).
2. Consider pattern matching algorithms.
3. The Constructor.Result burdens the GC. We can partially bypass it with a reference to a position.

### Additional Tests
1. Test the constructors in the TokenizationFactory (requires extraction of the functions).
2. Test the unary constructor.

### Notes

1. ParenthesesValidator can be generalized to describe a simple open-close matcher.
2. We currently don't support "-" as an unary operator (not even for numbers).