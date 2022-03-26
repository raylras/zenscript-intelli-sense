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
    : IMPORT className (AS alias )? ';'
    ;

alias
    : IDENTIFIER
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
    : (VAR | VAL) IDENTIFIER asType ('=' expression)? ';'
    ;

method
    : FUNCTION IDENTIFIER parameters asType? block
    ;

asType
    : AS type
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
    : expression asType?
    ;

block
    : '{' statement* '}'
    ;

mapEntry
    : key=expression ':' value=expression
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

ifStatement: IF expression (statement | block) (ELSE (statement | block))?;

forStatement: FOR forControl block;

whileStatement: WHILE '(' expression ')' block;

varStatement
    : (VAR | VAL | GLOBAL | STATIC) IDENTIFIER asType? '=' expression ';'
    ;

expressionStatement
    : expression ';'
    ;

forControl
    : IDENTIFIER (',' IDENTIFIER)? IN expression
    ;

className
    : IDENTIFIER ('.' IDENTIFIER)*
    ;

expression
    : literal # expressionLiteral
    | id=IDENTIFIER # expressionIdentifier
    | '(' expression ')' # expressionParens
    | expression '.' expression # expressionAccess
    | expression '[' expression ']' # expressionIndex
    | expression arguments # expressionCall
    | op=('+'|'-'|'!') expression # expressionUnary
    | expression op=('*'|'/'|'%') expression # expressionBinary
    | expression op=('+'|'-') expression # expressionBinary
    | expression op=('<=' | '>=' | '>' | '<' | '==' | '!=') expression # expressionBinary
    | expression INSTANCEOF type # expressionBinary
    | expression (IN | HAS) expression # expressionBinary
    | expression op=('&'| '|' |'^'| '&&' | '||' | '~') expression # expressionBinary
    | <assoc=right> expression '?' expression ':' expression # expressionTrinary
    | <assoc=right> expression op=('=' | '+=' | '-=' | '*=' | '/=' | '%=' | '~=' | '&=' | '|=' | '^=') expression # expressionAssign
    | '<' (~'>' ':'?)* '>' # bracketHandler
    | '[' expression (',' expression)* ']' # arrayInit
    | '{' (mapEntry (',' mapEntry)* )? '}' # mapInit
    | FUNCTION parameters asType? block # anonymousFunction
    | expression  ('..' | 'to') expression # expressionRange
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
    : DECIMAL_LITERAL # integerLiteral
    | HEX_LITERAL # integerLiteral
    | FLOATING_LITERAL # floatingLiteral
    | STRING_LITERAL # stringLiteral
    | BOOLEAN_LITERAL # booleanLiteral
    | NULL_LITERAL # nullLiteral
    ;
