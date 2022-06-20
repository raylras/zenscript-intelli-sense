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
    : 'import' reference aliasDeclaration?';'
    ;

reference
    : identifier ('.' identifier)*
    ;

aliasDeclaration
    : 'as' identifier
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
    : 'zenClass' identifier '{' (variableDeclStatement | constructorDeclaration | functionDeclaration)* '}'
    ;

constructorDeclaration
    : 'zenConstructor' '(' formalParameter? (',' formalParameter)* ')' block
    ;

block
    : '{' statement* '}'
    ;

statement
    : blockStatement
    | returnStatement
    | breakStatement
    | continueStatement
    | ifElseStatement
    | foreachStatement
    | whileStatement
    | variableDeclStatement
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
    : 'for' identifier (',' identifier)* 'in' expression block
    ;

whileStatement
    : 'while' '(' expression ')' block
    ;

variableDeclStatement
    : Modifier=('var' | 'val' | 'static' | 'global') identifier ('as' type)? ('=' expression)? ';'
    ;

expressionStatement
    : expression ';'
    ;

expression
    : 'function' '(' formalParameter? (',' formalParameter)* ')' ('as' type)? block # FunctionExpression
    | Left=expression '(' expression? (',' expression)* ')' # ArgumentsExpression
    | Left=expression '.' Right=identifier # MemberAccessExpression
    | Left=expression '[' Index=expression ']' # MemberIndexExpression
    | expression 'as' type # TypeCastExpression
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
    | '[' expression? (',' expression)* ','? ']' # ArrayLiteralExpression
    | '{' mapEntry? (',' mapEntry)* ','? '}' # MapLiteralExpression
    | literal # LiteralExpression
    | '(' expression ')' # ParensExpression
    | identifier # VarAccessExpression
    ;

mapEntry
    : Key=expression ':' Value=expression
    ;

type
    : builtin # BuiltinType
    | reference # ReferenceType
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