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
    : 'import' className ('as' alias)? ';'
    ;

className
    : IDENTIFIER ('.' IDENTIFIER)*
    ;

alias
    : IDENTIFIER
    ;

functionDeclaration
    : 'function' IDENTIFIER '(' parameterList? ')' ('as' typeLiteral)? functionBody
    ;

parameterList
    : parameter (',' parameter)*
    ;

parameter
    : IDENTIFIER ('as' typeLiteral)? ('=' defaultValue)?
    ;

defaultValue
    : expression
    ;

functionBody
    : '{' statement* '}'
    ;

classDeclaration
    : 'zenClass' IDENTIFIER '{' (variableDeclaration | constructorDeclaration | functionDeclaration)* '}'
    ;

constructorDeclaration
    : 'zenConstructor' '(' parameterList? ')' constructorBody
    ;

constructorBody
    : '{' statement* '}'
    ;

variableDeclaration
    : Declarator=('var' | 'val' | 'static' | 'global') IDENTIFIER ('as' typeLiteral)? ('=' initializer)? ';'
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
    : 'for' simpleVariableDeclarations 'in' expression foreachBody
    ;

simpleVariableDeclarations
    : IDENTIFIER (',' IDENTIFIER)*
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
    : 'function' '(' parameterList? ')' ('as' typeLiteral)? functionBody # FunctionExpr
    | Left=expression '(' expressionList? ')' # CallExpr
    | Left=expression Op='.' IDENTIFIER # MemberAccessExpr
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
    | '[' expressionList? ','? ']' # ArrayLiteralExpr
    | '{' mapEntryList? ','? '}' # MapLiteralExpr
    | '(' expression ')' # ParensExpr
    | 'this' # ThisExpr
    | 'super' # SuperExpr
    | INT_LITERAL # IntExpr
    | LONG_LITERAL # LongExpr
    | FLOAT_LITERAL # FloatExpr
    | DOUBLE_LITERAL # DoubleExpr
    | STRING_LITERAL # StringExpr
    | TRUE_LITERAL # TrueExpr
    | FALSE_LITERAL # FalseExpr
    | NULL_LITERAL # NullExpr
    | IDENTIFIER # IDExpr
    ;

expressionList
    : expression (',' expression)*
    ;

mapEntry
    : K=expression ':' V=expression
    ;

mapEntryList
    : mapEntry (',' mapEntry)*
    ;

typeLiteral
    : className # ClassType
    | 'function' '(' typeLiteralList? ')' R=typeLiteral # FunctionType
    | '[' typeLiteral ']' # ListType
    | typeLiteral '['']' # ArrayType
    | V=typeLiteral '[' K=typeLiteral ']' # MapType
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

typeLiteralList
    : typeLiteral (',' typeLiteral)*
    ;
