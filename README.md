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

### Known Issues
1. The calculator is agnostic to spaces and will accept "i+++++i"