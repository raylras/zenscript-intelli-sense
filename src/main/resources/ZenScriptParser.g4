parser grammar ZenScriptParser;

options { tokenVocab = ZenScriptLexer; }

script:
        ( importStatement
        | functionDeclaration
        | zenClassDeclaration
        | statements
        )*
    EOF
    ;

importStatement
    : IMPORT packageName (AS IDENTIFIER )? ';'
    ;

functionDeclaration
    : FUNCTION IDENTIFIER formalParameters asType? block
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
    : ZEN_CONSTRUCTOR formalParameters block
    ;

field
    : (VAR | VAL) IDENTIFIER asType ('=' expression asType?)? ';'
    ;

method
    : FUNCTION IDENTIFIER formalParameters asType? block
    ;

localVariableDeclaration
    : (VAR | VAL) IDENTIFIER asType? '=' expression ';'
    ;

globalVariableDeclaration
    : (GLOBAL | STATIC) IDENTIFIER asType '=' expression ';'
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
    : FUNCTION formalParameters asType? block
    ;

formalParameters
    : '(' formalParameter? (',' formalParameter)* ')'
    ;

formalParameter
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
    : '{' statements* '}'
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

statements
    : ifStatement
    | forStatement
    | whileStatement
    | doWhileStatement
    | localVariableDeclaration
    | globalVariableDeclaration
    | functionDeclaration
    | expression ';'
    ;

ifStatement: IF expression (statements | block | ) (ELSE (statements | block | ))?;

forStatement: FOR forControl block;

whileStatement: WHILE '(' expression ')' block;

doWhileStatement: DO block WHILE '(' expression ')';

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

packageName
    : IDENTIFIER ('.' IDENTIFIER)*
    ;

expression
    : literal # expressionLiteral
    | RETURN expression? # expressionReturn
    | BREAK # expressionBreak
    | CONTINUE # expressionContinue
    | methodCall # expressionCall
    | memberCall # expressionCall
    | '(' expression ')' # expressionParens
    | expression '.' expression # expressionCall
    | expression '[' expression ']' # expressionArrayGet
    | ('+'|'-'|'!') expression # expressionUnary
    | expression ('*'|'/'|'%') expression # expressionBinary
    | expression ('+'|'-') expression # expressionBinary
    | expression ('<=' | '>=' | '>' | '<' | '==' | '!=') expression # expressionCompare
    | expression INSTANCEOF type # expressionInstanceof
    | expression (IN | HAS) expression # expressionIn
    | expression '&' expression # expressionAnd
    | expression '^' expression # expressionXor
    | expression '|' expression # expressionOr
    | expression '&&' expression # expressionAndAnd
    | expression '||' expression # expressionOrOr
    | expression '~' expression # expressionCat
    | <assoc=right> expression '?' expression ':' expression # expressionTrinary
    | <assoc=right> expression ('=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '%=' | '~=') expression # expressionAssign
    | IDENTIFIER # expressionID
    | bracketHandler # expressionBracketHandler
    | array # expressionArray
    | map # expressionMap
    | anonymousFunction # expressionFunction
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
    : packageName
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
