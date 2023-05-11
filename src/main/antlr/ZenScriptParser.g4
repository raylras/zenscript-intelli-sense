parser grammar ZenScriptParser;

options { tokenVocab = ZenScriptLexer; }

compilationUnit
    :   ( importDeclaration
        | functionDeclaration
        | classDeclaration
        | statement
        )*
        EOF
    ;

importDeclaration
    : 'import' qualifiedName ('as' alias)? ';'
    ;

qualifiedName
    : name ('.' name)*
    ;

alias
    : name
    ;

name
    : IDENTIFIER
    | 'to'
    ;

functionDeclaration
    : Declarator='static'? 'function' name '(' (parameter (',' parameter)*)? ')' ('as' typeLiteral)? functionBody
    ;

parameter
    : name ('as' typeLiteral)? ('=' defaultValue)?
    ;

defaultValue
    : expression
    ;

functionBody
    : '{' statement* '}'
    ;

classDeclaration
    : 'zenClass' qualifiedName '{' (variableDeclaration | constructorDeclaration | functionDeclaration)* '}'
    ;

constructorDeclaration
    : 'zenConstructor' '(' (parameter (',' parameter)*)? ')' constructorBody
    ;

constructorBody
    : '{' statement* '}'
    ;

variableDeclaration
    : Declarator=('var' | 'val' | 'static' | 'global') name ('as' typeLiteral)? ('=' initializer)? ';'
    ;

initializer
    : expression
    ;

statement
    : blockStatement
    | returnStatement
    | breakStatement
    | continueStatement
    | ifStatement
    | foreachStatement
    | whileStatement
    | variableDeclaration
    | expressionStatement
    ;

blockStatement
    : '{' statement* '}'
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

ifStatement
    : 'if' expression thenBody ('else' elseBody)?
    ;

thenBody
    : statement
    ;

elseBody
    : statement
    ;

foreachStatement
    : 'for' simpleVariable (',' simpleVariable)* 'in' expression foreachBody
    ;

simpleVariable
    : name
    ;

foreachBody
    : '{' statement* '}'
    ;

whileStatement
    : 'while' '(' expression ')' statement
    ;

expressionStatement
    : expression ';'
    ;

expression
    : 'function' '(' (parameter (',' parameter)*)? ')' ('as' typeLiteral)? functionBody # FunctionExprission
    | Left=expression '(' (expression (',' expression)*)? ')' # Call
    | Left=expression Op='.' name # MemberAccess
    | Left=expression '[' Index=expression ']' # ArrayIndex
    | expression 'as' typeLiteral # TypeCast
    | <assoc=right> Op=('!' | '-' | '+') expression # Unary
    | Left=expression Op=('*' | '/' | '%') Right=expression # Binary
    | Left=expression Op=('+' | '-') Right=expression # Binary
    | Left=expression Op='~' Right=expression # Binary
    | Left=expression Op=('<' | '<=' | '>' | '>=') Right=expression # Binary
    | Left=expression Op=('==' | '!=') Right=expression # Binary
    | Left=expression Op='instanceof' Right=expression # Binary
    | Left=expression Op=('in' | 'has') Right=expression # Binary
    | Left=expression Op='&' Right=expression # Binary
    | Left=expression Op='|' Right=expression # Binary
    | Left=expression Op='^'Right=expression # Binary
    | Left=expression Op='&&' Right=expression # Binary
    | Left=expression Op='||' Right=expression # Binary
    | <assoc=right> Condition=expression '?' TruePart=expression ':' FalsePart=expression # Ternary
    | <assoc=right> Left=expression Op=('=' | '+=' | '-=' | '*=' | '/=' | '%=' | '~=' | '&=' | '|=' | '^=') Right=expression # Assignment
    | '<' (~'>')*? '>' # BracketHandler
    | From=expression Op=('..' | 'to') To=expression # IntRange
    | '[' (expression (',' expression)*)? ','? ']' # ArrayInitializer
    | '{' (mapEntry (',' mapEntry)*)? ','? '}' # MapInitializer
    | '(' expression ')' # Parens
    | 'this' # This
    | 'super' # Super
    | INT_LITERAL # IntLiteral
    | LONG_LITERAL # LongLiteral
    | FLOAT_LITERAL # FloatLiteral
    | DOUBLE_LITERAL # DoubleLiteral
    | STRING_LITERAL # StringLiteral
    | TRUE_LITERAL # TrueLiteral
    | FALSE_LITERAL # FalseLiteral
    | NULL_LITERAL # NullLiteral
    | name # SimpleNameExpression
    ;

mapEntry
    : Key=expression ':' Value=expression
    ;

typeLiteral
    : qualifiedName # ClassType
    | 'function' '(' (typeLiteral (',' typeLiteral)*)? ')' Return=typeLiteral # FunctionType
    | '[' typeLiteral ']' # ListType
    | typeLiteral '['']' # ArrayType
    | Value=typeLiteral '[' Key=typeLiteral ']' # MapType
    | ANY # PrimitiveType
    | BYTE # PrimitiveType
    | SHORT # PrimitiveType
    | INT # PrimitiveType
    | LONG # PrimitiveType
    | FLOAT # PrimitiveType
    | DOUBLE # PrimitiveType
    | BOOL # PrimitiveType
    | VOID # PrimitiveType
    | STRING # PrimitiveType
    ;
