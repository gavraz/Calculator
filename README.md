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
3. TERMINALS:={NUM|VAR}
4. ASSIGN:={VAR}[=,+=,-=,*=,/=]{EXPR}
5. EXPR:=  ({EXPR}) || ASSIGN || SUM || MUL || DIV || SUB
                 || {VAR}++ || ++{VAR} || {VAR}-- || --{VAR}

### Future Optimizations
1. A state machine tokenizer instead of the factory (disadvantage: less clear).
2. Consider a c-like string implementation to improve tokenization.
3. The Constructor.Result in the tokenization factory burdens the GC.
We can partially bypass it with a reference to a position. 

### Notes
1. ParenthesesValidator can be generalized to describe a simple open-close validator.

### Known Issues
1. The calculator is agnostic to spaces and will accept "i+++++i"