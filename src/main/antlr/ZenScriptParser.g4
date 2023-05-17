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
    : simpleName ('.' simpleName)*
    ;

alias
    : simpleName
    ;

simpleName
    : IDENTIFIER
    | 'to'
    ;

functionDeclaration
    : Declarator='static'? 'function' simpleName '(' (parameter (',' parameter)*)? ')' ('as' typeLiteral)? functionBody
    ;

parameter
    : simpleName ('as' typeLiteral)? ('=' defaultValue)?
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
    : Declarator=('var' | 'val' | 'static' | 'global') simpleName ('as' typeLiteral)? ('=' initializer)? ';'
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
    : simpleName
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
    : 'function' '(' (parameter (',' parameter)*)? ')' ('as' typeLiteral)? functionBody # FunctionExpr
    | Left=expression '(' (expression (',' expression)*)? ')' # CallExpr
    | Left=expression Op='.' simpleName # MemberAccessExpr
    | Left=expression '[' Index=expression ']' # ArrayIndexExpr
    | expression 'as' typeLiteral # TypeCastExpr
    | <assoc=right> Op=('!' | '-' | '+') expression # UnaryExpr
    | Left=expression Op=('*' | '/' | '%') Right=expression # BinaryExpr
    | Left=expression Op=('+' | '-') Right=expression # BinaryExpr
    | Left=expression Op='~' Right=expression # BinaryExpr
    | Left=expression Op=('<' | '<=' | '>' | '>=') Right=expression # BinaryExpr
    | Left=expression Op=('==' | '!=') Right=expression # BinaryExpr
    | Left=expression Op='instanceof' Right=expression # BinaryExpr
    | Left=expression Op=('in' | 'has') Right=expression # BinaryExpr
    | Left=expression Op='&' Right=expression # BinaryExpr
    | Left=expression Op='|' Right=expression # BinaryExpr
    | Left=expression Op='^'Right=expression # BinaryExpr
    | Left=expression Op='&&' Right=expression # BinaryExpr
    | Left=expression Op='||' Right=expression # BinaryExpr
    | <assoc=right> Condition=expression '?' TruePart=expression ':' FalsePart=expression # TernaryExpr
    | <assoc=right> Left=expression Op=('=' | '+=' | '-=' | '*=' | '/=' | '%=' | '~=' | '&=' | '|=' | '^=') Right=expression # AssignmentExpr
    | '<' (~'>')*? '>' # BracketHandlerExpr
    | From=expression Op=('..' | 'to') To=expression # IntRangeExpr
    | '[' (expression (',' expression)*)? ','? ']' # ArrayInitializerExpr
    | '{' (mapEntry (',' mapEntry)*)? ','? '}' # MapInitializerExpr
    | '(' expression ')' # ParensExpr
    | 'this' # ThisExpr
    | 'super' # SuperExpr
    | INT_LITERAL # IntLiteralExpr
    | LONG_LITERAL # LongLiteralExpr
    | FLOAT_LITERAL # FloatLiteralExpr
    | DOUBLE_LITERAL # DoubleLiteralExpr
    | STRING_LITERAL # StringLiteralExpr
    | TRUE_LITERAL # TrueLiteralExpr
    | FALSE_LITERAL # FalseLiteralExpr
    | NULL_LITERAL # NullLiteralExpr
    | simpleName # LocalAccessExpr
    ;

argument
    : expression
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
