# Calculator
<b>An implementation of a programming-language calculator.</b>

### Features
* Arithmetic: +,-,*,/
* Parentheses
* Variables
* Assignment operators: =,+=,-=,*=,/=
* Unary increment/decrement
* In-expression assignments

### Algorithm: Precedence Climbing
* Resources
  * [Operator-precedence parser](https://en.wikipedia.org/wiki/Operator-precedence_parser)
  * [Parsing approaches](https://www.engr.mun.ca/~theo/Misc/exp_parsing.htm)
  * [More about precedence climbing](https://eli.thegreenplace.net/2012/08/02/parsing-expressions-by-precedence-climbing)
* Main advantages: efficient, relatively simple.
* Main disadvantage: ambiguous unary-binary operators with non-equal precedence is not easily supported.  

### Grammar
1. NUM:=[0-9]*
2. VAR:=[a-z;A-Z]+[a-z;A-Z;0-9]*
3. TERMINAL:={NUM|VAR}
4. ASSIGN:={VAR}[=,+=,-=,*=,/=]{EXPR}
5. EXPR:=  ({EXPR}) || ASSIGN || SUM || MUL || DIV || SUB || {VAR}++ || ++{VAR} || {VAR}-- || --{VAR}
