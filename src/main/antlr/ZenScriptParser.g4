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
    : 'import' packageName ('as' alias)? ';'
    ;

packageName
    : IDENTIFIER ('.' IDENTIFIER)*
    ;

alias
    : IDENTIFIER
    ;

functionDeclaration
    : 'function' IDENTIFIER '(' parameterList ')' ('as' typeAnnotation)? functionBody
    ;

parameterList
    : parameter? (',' parameter)*
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

functionBody
    : '{' statement* '}'
    ;

classDeclaration
    : 'zenClass' IDENTIFIER classBody
    ;

classBody
    : '{' (fieldDeclaration | constructorDeclaration | methodDeclaration)* '}'
    ;

fieldDeclaration
    : Declarator=('var' | 'val' | 'static') IDENTIFIER ('as' typeAnnotation)? ('=' initializer)? ';'
    ;

constructorDeclaration
    : 'zenConstructor' '(' parameterList ')' constructorBody
    ;

methodDeclaration
    : 'function' IDENTIFIER '(' parameterList ')' ('as' typeAnnotation)? functionBody
    ;

constructorBody
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

ifElseStatement
    : 'if' expression ifBody ('else' elseBody)?
    ;

ifBody
    : statement
    ;

elseBody
    : statement
    ;

foreachStatement
    : 'for' IDENTIFIER (',' IDENTIFIER)* 'in' expression foreachBody
    ;

foreachBody
    : '{' statement* '}'
    ;

whileStatement
    : 'while' '(' expression ')' whileBody
    ;

whileBody
    : '{' statement* '}'
    ;

expressionStatement
    : expression ';'
    ;

expression
    : 'function' '(' parameterList ')' ('as' typeAnnotation)? functionBody # FunctionExpression
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
    : packageName # ClassType
    | 'function' '(' typeList ')' ReturnType=typeName # FunctionType
    | '[' BaseType=typeName ']' # ListType
    | BaseType=typeName '['']' # ArrayType
    | ValueType=typeName '[' KeyType=typeName ']' # MapType
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

typeList
    :
    | typeName (',' typeName)*
    ;

literal
    : INT_LITERAL
    | LONG_LITERAL
    | FLOAT_LITERAL
    | DOUBLE_LITERAL
    | STRING_LITERAL
    | BOOL_LITERAL
    | NULL_LITERAL
    ;
