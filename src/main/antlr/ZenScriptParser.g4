parser grammar ZenScriptParser;

options { tokenVocab = ZenScriptLexer; }

compilationUnit
    :   ( importDeclaration
        | functionDeclaration
        | zenClassDeclaration
        | statement
        )*
        EOF
    ;

importDeclaration
    : 'import' reference ('as' alias)? ';'
    ;

reference
    : IDENTIFIER ('.' IDENTIFIER)*
    ;

alias
    : IDENTIFIER
    ;

functionDeclaration
    : 'function' IDENTIFIER '(' parameterList? ')' ('as' type)? block
    ;

parameterList
    : parameter (',' parameter)*
    ;

parameter
    : IDENTIFIER ('as' type)? ('=' defaultValue)?
    ;

defaultValue
    : expression
    ;

zenClassDeclaration
    : 'zenClass' IDENTIFIER '{' (variableDeclaration | constructorDeclaration | functionDeclaration)* '}'
    ;

constructorDeclaration
    : 'zenConstructor' '(' parameterList? ')' block
    ;

block
    : '{' statement* '}'
    ;

variableDeclaration
    : Declarator=('var' | 'val' | 'static' | 'global') IDENTIFIER ('as' type)? ('=' initializer)? ';'
    ;

initializer
    : expression
    ;

statement
    : blockStatement
    | returnStatement
    | breakStatement
    | continueStatement
    | ifElseStatement
    | foreachStatement
    | whileStatement
    | variableDeclaration
    | expressionStatement
    ;

blockStatement
    : block
    ;

returnStatement
    : 'return' expression? ';'
    ;

breakStatement
    : 'break' ';'
    ;

continueStatement
    : 'continue' ';'
    ;

ifElseStatement
    : 'if' expression (statement | block) ('else' (statement | block))?
    ;

foreachStatement
    : 'for' IDENTIFIER (',' IDENTIFIER)* 'in' expression block
    ;

whileStatement
    : 'while' '(' expression ')' block
    ;

expressionStatement
    : expression ';'
    ;

expression
    : 'function' '(' parameterList? ')' ('as' type)? block # FunctionExpression
    | Left=expression '(' expression? (',' expression)* ')' # ArgumentsExpression
    | Left=expression Operator='.' Right=IDENTIFIER # MemberAccessExpression
    | Left=expression '[' Index=expression ']' # MemberIndexExpression
    | expression 'as' type # TypeAssertionExpression
    | <assoc=right> Operator=('!' | '-' | '+') expression # UnaryExpression
    | Left=expression Operator=('*' | '/' | '%') Right=expression # BinaryExpression
    | Left=expression Operator=('+' | '-') Right=expression # BinaryExpression
    | Left=expression Operator='~' Right=expression # BinaryExpression
    | Left=expression Operator=('<' | '<=' | '>' | '>=') Right=expression # BinaryExpression
    | Left=expression Operator=('==' | '!=') Right=expression # BinaryExpression
    | Left=expression Operator='instanceof' Right=expression # BinaryExpression
    | Left=expression Operator=('in' | 'has') Right=expression # BinaryExpression
    | Left=expression Operator='&' Right=expression # BinaryExpression
    | Left=expression Operator='|' Right=expression # BinaryExpression
    | Left=expression Operator='^'Right=expression # BinaryExpression
    | Left=expression Operator='&&' Right=expression # BinaryExpression
    | Left=expression Operator='||' Right=expression # BinaryExpression
    | <assoc=right> Condition=expression '?' Then=expression ':' Else=expression # TernaryExpression
    | <assoc=right> Left=expression Operator=('=' | '+=' | '-=' | '*=' | '/=' | '%=' | '~=' | '&=' | '|=' | '^=') Right=expression # AssignmentExpression
    | '<' (~'>')*? '>' # BracketHandlerExpression
    | From=expression Operator=('..' | 'to') To=expression # IntRangeExpression
    | 'this' # ThisExpression
    | '[' expression? (',' expression)* ','? ']' # ArrayLiteralExpression
    | '{' mapEntry? (',' mapEntry)* ','? '}' # MapLiteralExpression
    | literal # LiteralExpression
    | '(' expression ')' # ParensExpression
    | IDENTIFIER # IDExpression
    ;

mapEntry
    : Key=expression ':' Value=expression
    ;

type
    : reference # ReferenceType
    | 'function' '(' typeList? ')' ReturnType=type # FunctionType
    | '[' BaseType=type ']' # ListType
    | BaseType=type '['']' # ArrayType
    | ValueType=type '[' KeyType=type ']' # MapType
    | ANY # AnyType
    | BYTE # ByteType
    | SHORT # ShortType
    | INT # IntType
    | LONG # LongType
    | FLOAT # FloatType
    | DOUBLE # DoubleType
    | BOOL # BoolType
    | VOID # VoidType
    | STRING # StringType
    ;

typeList
    : type (',' type)*
    ;

literal
    : INT_LITERAL
    | LONG_LITERAL
    | HEX_LITERAL
    | FLOAT_LITERAL
    | DOUBLE_LITERAL
    | STRING_LITERAL
    | BOOL_LITERAL
    | NULL_LITERAL
    ;
