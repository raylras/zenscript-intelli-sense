parser grammar ZenScriptParser;

options { tokenVocab = ZenScriptLexer; }

scriptUnit
    :   ( importDeclaration
        | functionDeclaration
        | zenClassDeclaration
        | statement
        )*
        EOF
    ;

importDeclaration
    : 'import' className ('as' alias )? ';'
    | 'import' crossScriptReference ';'
    ;

className
    : identifier
    | (identifier ('.' identifier)*) '.' identifier
    ;

crossScriptReference
    : 'script' '.' identifier ('.' identifier)*
    ;

alias
    : identifier
    ;

functionDeclaration
    : 'function' identifier '(' formalParameter? (',' formalParameter)* ')' ('as' type)? block
    ;

formalParameter
    : identifier ('as' type)? defaultValue?
    ;

defaultValue
    : '=' expression
    ;

zenClassDeclaration
    : 'zenClass' identifier '{' (fieldDeclaration | constructorDeclaration | functionDeclaration)* '}'
    ;

constructorDeclaration
    : 'zenConstructor' '(' formalParameter? (',' formalParameter)* ')' block
    ;

fieldDeclaration
    : Modifier=('var' | 'val') identifier ('as' type) ('=' expression)? ';'
    ;

block
    : '{' statement* '}'
    ;

statement
    : blockStatement
    | returnStatement
    | breakStatement
    | continueStatement
    | ifStatement
    | foreachStatement
    | whileStatement
    | variableDeclarationStatement
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

ifStatement
    : 'if' expression (statement | block) ('else' (statement | block))?
    ;

foreachStatement
    : 'for' identifier (',' identifier)* 'in' expression block
    ;

whileStatement
    : 'while' '(' expression ')' block
    ;

variableDeclarationStatement
    : Modifier=('var' | 'val' | 'static' | 'global') identifier ('as' type)? ('=' expression)? ';'
    ;

expressionStatement
    : expression ';'
    ;

expression
    : 'function' '(' formalParameter? (',' formalParameter)* ')' ('as' type)? block # FunctionExpression
    | Left=expression '(' expression? (',' expression)* ')' # ArgumentsExpression
    | Left=expression '.' Right=expression # MemberAccessExpression
    | Left=expression '[' Index=expression ']' # MemberIndexExpression
    | <assoc=right> Operator=('!' | '-' | '+') expression # UnaryExpression
    | Left=expression Operator=('*' | '/' | '%' | '+' | '-' | '~') Right=expression # BinaryExpression
    | Left=expression Operator=('<=' | '>=' | '>' | '<' | '==' | '!=') Right=expression # BinaryExpression
    | Left=expression Operator='instanceof' Right=expression # BinaryExpression
    | Left=expression Operator=('in' | 'has') Right=expression # BinaryExpression
    | Left=expression Operator=('&' | '|' | '^' | '&&' | '||') Right=expression # BinaryExpression
    | <assoc=right> Condition=expression '?' Then=expression ':' Else=expression # TrinaryExpression
    | <assoc=right> Left=expression Operator=('=' | '+=' | '-=' | '*=' | '/=' | '%=' | '~=' | '&=' | '|=' | '^=') Right=expression # AssignmentExpression
    | '<' (~'>' ':'?)* '>' # BracketHandlerExpression
    | From=expression  Operator=('..' | 'to') To=expression # RangeExpression
    | 'this' # ThisExpression
    | expression 'as' type # TypeCastExpression
    | '[' expression? (',' expression)* ','? ']' # ArrayLiteralExpression
    | '{' mapEntry? (',' mapEntry)* ','? '}' # MapLiteralExpression
    | literal # LiteralExpression
    | '(' expression ')' # ParensExpression
    | identifier # identifierExpression
    ;

mapEntry
    : Key=expression ':' Value=expression
    ;

type
    : builtin # BuiltinType
    | className # ReferenceType
    | 'function' '(' argumentTypeList? ')' ResultType=type # FunctionType
    | '[' BaseType=type ']' # ListType
    | BaseType=type '['']' # ArrayType
    | ValueType=type '[' KeyType=type ']' # MapType
    ;

builtin
    : 'bool'
    | 'byte'
    | 'short'
    | 'int'
    | 'long'
    | 'float'
    | 'double'
    | 'void'
    | 'string'
    | 'bool?'
    | 'byte?'
    | 'short?'
    | 'int?'
    | 'long?'
    | 'float?'
    | 'double?'
    ;

argumentTypeList
    : type (',' type)*
    ;

literal
    : DECIMAL_LITERAL
    | HEX_LITERAL
    | FLOATING_LITERAL
    | STRING_LITERAL
    | BOOLEAN_LITERAL
    | NULL_LITERAL
    ;

identifier
    : IDENTIFIER
    | 'to'
    ;