parser grammar ZenScriptParser;

options { tokenVocab = ZenScriptLexer; }

compilationUnit
    :   ( importDeclaration
        | functionDeclaration
        | expandFunctionDeclaration
        | classDeclaration
        | statement
        )*
        EOF
    ;

importDeclaration
    : 'import' qualifiedName ('as' alias)? ';'
    ;

qualifiedName
    : qualifier identifier
    ;

qualifier
    : (identifier '.')*
    ;

alias
    : identifier
    ;

identifier
    : IDENTIFIER
    | 'to'
    ;

functionDeclaration
    : Declarator='static'? 'function' identifier '(' (parameter (',' parameter)*)? ')' ('as' returnType)? functionBody
    ;

expandFunctionDeclaration
    : Declarator='$expand' Expand=typeLiteral '$' identifier '(' (parameter (',' parameter)*)? ')' ('as' returnType)? functionBody
    ;

parameter
    : identifier ('as' typeLiteral)? ('=' defaultValue)?
    ;

defaultValue
    : expression
    ;

returnType
    : typeLiteral
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
    : Declarator=('var' | 'val' | 'static' | 'global') identifier ('as' typeLiteral)? ('=' initializer)? ';'
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
    : 'for' foreachVariableDeclaration (',' foreachVariableDeclaration)* 'in' expression foreachBody
    ;

foreachVariableDeclaration
    : identifier
    ;

foreachBody
    : '{' statement* '}'
    ;

whileStatement
    : 'while' '(' expression ')' statement
    ;

expressionStatement
    : expression ';'?
    ;

expression
    : 'function' '(' (parameter (',' parameter)*)? ')' ('as' typeLiteral)? functionBody # FunctionExpr
    | Left=expression '(' (argument (',' argument)*)? ')' # CallExpr
    | Left=expression Op='.' identifier # MemberAccessExpr
    | Left=expression '[' Index=expression ']' # ArrayAccessExpr
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
    | identifier # LocalAccessExpr
    ;

argument
    : expression
    ;

mapEntry
    : Key=expression ':' Value=expression
    ;

typeLiteral
    : qualifiedName # ClassType
    | 'function' '(' (typeLiteral (',' typeLiteral)*)? ')' returnType # FunctionType
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
