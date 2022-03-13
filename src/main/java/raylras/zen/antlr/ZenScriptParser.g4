parser grammar ZenScriptParser;

options { tokenVocab = ZenScriptLexer; }

script:
        ( importStatement
        | functionDeclaration
        | zenClassDeclaration
        | statement
        )*
    EOF
    ;

importStatement
    : IMPORT className (AS IDENTIFIER )? ';'
    ;

functionDeclaration
    : FUNCTION IDENTIFIER parameters asType? block
    ;

zenClassDeclaration
    : ZEN_CLASS IDENTIFIER classBody
    ;

classBody
    : '{'
          ( constructor
          | field
          | method
          )*
      '}'
    ;

constructor
    : ZEN_CONSTRUCTOR parameters block
    ;

field
    : (VAR | VAL) IDENTIFIER asType ('=' expression asType?)? ';'
    ;

method
    : FUNCTION IDENTIFIER parameters asType? block
    ;


asType
    : AS type
    ;

memberCall
    :  IDENTIFIER
    ;

methodCall
    : IDENTIFIER arguments
    ;

anonymousFunction
    : FUNCTION parameters asType? block
    ;

parameters
    : '(' parameter? (',' parameter)* ')'
    ;

parameter
    : IDENTIFIER asType? defaultValue?
    ;

defaultValue
    : '=' expression
    ;

arguments
    : '(' (argument? (',' argument)*) ')'
    ;

argument
    : (IDENTIFIER | expression | literal) asType?
    | anonymousFunction
    ;

block
    : '{' statement* '}'
    ;

array
    : '[' array? (',' array)* ']'
    | '[' (literal | expression) (',' (literal | expression) )* ']'
    ;

map
    : '{' map? (',' map)* '}'
    | '{' (mapEntry (',' mapEntry)* )? '}'
    ;

mapEntry
    : mapKey ':' mapValue
    ;

mapKey
    : expression
    ;

mapValue
    : expression
    ;

statement
    : returnStatement
    | breakStatement
    | continueStatement
    | ifStatement
    | forStatement
    | whileStatement
    | varStatement
    | functionDeclaration
    | expressionStatement
    ;

returnStatement: RETURN expression? ';';

breakStatement: BREAK ';';

continueStatement: CONTINUE ';';

ifStatement: IF expression (statement | block | ) (ELSE (statement | block | ))?;

forStatement: FOR forControl block;

whileStatement: WHILE '(' expression ')' block;

varStatement
    : (VAR | VAL | GLOBAL | STATIC) IDENTIFIER asType? '=' expression ';'
    ;

expressionStatement
    : expression ';'
    ;

forControl
    : IDENTIFIER IN range
    | IDENTIFIER (',' IDENTIFIER)? IN (expression | memberCall | methodCall)
    ;

range
    : bounds  ('..' | 'to') bounds
    ;

bounds
    : expression
    | memberCall
    | methodCall
    ;

className
    : IDENTIFIER ('.' IDENTIFIER)*
    ;

expression
    : literal # expressionLiteral
    | '(' expression ')' # expressionParens
    | expression '.' expression # expressionCall
    | methodCall # expressionCall
    | memberCall # expressionCall
    | expression '[' expression ']' # expressionArrayGet
    | ('+'|'-'|'!') expression # expressionUnary
    | expression ('*'|'/'|'%') expression # expressionBinary
    | expression ('+'|'-') expression # expressionBinary
    | expression ('<=' | '>=' | '>' | '<' | '==' | '!=') expression # expressionBinary
    | expression INSTANCEOF type # expressionBinary
    | expression (IN | HAS) expression # expressionBinary
    | expression '&' expression # expressionBinary
    | expression '^' expression # expressionBinary
    | expression '|' expression # expressionBinary
    | expression '&&' expression # expressionBinary
    | expression '||' expression # expressionBinary
    | expression '~' expression # expressionBinary
    | <assoc=right> expression '?' expression ':' expression # expressionTrinary
    | <assoc=right> expression ('=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '%=' | '~=') expression # expressionAssign
    | bracketHandler # expressionBracketHandler
    | array # expressionArray
    | map # expressionMap
    | anonymousFunction # expressionFunction
    | expression asType # expressionCast
    ;

type
    : (typeList | typeMap | typeArray | typePrimitive | typeClass | typeFunction)
    ;

typeFunction
    : FUNCTION '(' type? (',' type)* ')' type
    ;

typePrimitive
    : ANY
    | BOOL
    | BYTE
    | SHORT
    | INT
    | LONG
    | FLOAT
    | DOUBLE
    | STRING
    | VOID
    ;

typeArray: (typePrimitive | typeClass) ('['']')+;

typeList: '[' (typePrimitive | typeClass) ']';

typeMap: (typePrimitive | typeClass) ('['type?']')* '['type']';

typeClass
    : className
    ;

literal
    : integerLiteral
    | FLOATING_LITERAL
    | STRING_LITERAL
    | BOOLEAN_LITERAL
    | NULL_LITERAL
    ;

integerLiteral
    : DECIMAL_LITERAL
    | HEX_LITERAL
    ;

bracketHandler
    : '<' (~'>' ':'?)* '>'
    ;
