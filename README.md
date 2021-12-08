# Calculator
<b>An implementation of a basic programming-language calculator.</b>

### Algorithm: Precedence Climbing
* Wiki: https://en.wikipedia.org/wiki/Operator-precedence_parser
* Parsing approaches in general + precedence climbing: https://www.engr.mun.ca/~theo/Misc/exp_parsing.htm
* More about precedence climbing: https://eli.thegreenplace.net/2012/08/02/parsing-expressions-by-precedence-climbing
* Main advantages: efficient, relatively simple.
* Main disadvantage: ambiguous unary-binary operators with non-equal precedence is cumbersome to support.  

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
3. Customize the exceptions and have tighter tests.

### Notes
1. ParenthesesValidator can be generalized to describe a simple open-close matcher.
2. We currently don't support "-" as a unary operator (not even for numbers).
3. We do support in-expression assignments (e.g. "x=(y+=5)*3").
4. There are additional possible cosmetics such as:
Tokenizer can use an object for the buffer functionality (premature IMO);
Refactoring the parse_expression method (losses explicitness & context).