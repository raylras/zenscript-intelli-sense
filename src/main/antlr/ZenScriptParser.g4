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
    : 'function' IDENTIFIER '(' parameterList? ')' ('as' typeAnnotation)? block
    ;

parameterList
    : parameter (',' parameter)*
    ;

parameter
    : IDENTIFIER ('as' typeAnnotation)? ('=' defaultValue)?
    ;

defaultValue
    : expression
    ;

typeAnnotation
    : typeName
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
    : Declarator=('var' | 'val' | 'static' | 'global') IDENTIFIER ('as' typeAnnotation)? ('=' initializer)? ';'
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
    : 'if' expression statement ('else' statement)?
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
    : 'function' '(' parameterList? ')' ('as' typeAnnotation)? block # FunctionExpression
    | Left=expression '(' expression? (',' expression)* ')' # CallExpression
    | Left=expression Operator='.' Right=IDENTIFIER # MemberAccessExpression
    | Left=expression '[' Index=expression ']' # MemberIndexExpression
    | expression 'as' typeName # TypeAssertionExpression
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

typeName
    : reference
    | 'function' '(' typeList? ')' ReturnType=typeName
    | '[' BaseType=typeName ']'
    | BaseType=typeName '['']'
    | ValueType=typeName '[' KeyType=typeName ']'
    | ANY
    | BYTE
    | SHORT
    | INT
    | LONG
    | FLOAT
    | DOUBLE
    | BOOL
    | VOID
    | STRING
    ;

typeList
    : typeName (',' typeName)*
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
