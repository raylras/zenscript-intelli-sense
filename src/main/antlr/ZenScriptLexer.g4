lexer grammar ZenScriptLexer;

// Keywords

VAR:                    'var';
VAL:                    'val';
GLOBAL:                 'global';
STATIC:                 'static';
IMPORT:                 'import';
FUNCTION:               'function';
AS:                     'as';
TO:                     'to';
IN:                     'in';
HAS:                    'has';
INSTANCEOF:             'instanceof';
THIS:                   'this';

ANY:                    'any';
BYTE:                   'byte';
BYTE_OBJ:               'byte?';
SHORT:                  'short';
SHORT_OBJ:              'short?';
INT:                    'int';
INT_OBJ:                'int?';
LONG:                   'long';
LONG_OBJ:               'long?';
FLOAT:                  'float';
FLOAT_OBJ:              'float?';
DOUBLE:                 'double';
DOUBLE_OBJ:             'double?';
BOOL:                   'bool';
BOOL_OBJ:               'bool?';
VOID:                   'void';
STRING:                 'string';


IF:                     'if';
ELSE:                   'else';
FOR:                    'for';
DO:                     'do';
WHILE:                  'while';
BREAK:                  'break';
CONTINUE:               'continue';
RETURN:                 'return';

FRIGGIN_CLASS:          'frigginClass';
FRIGGIN_CONSTRUCTOR:    'frigginConstructor';
ZEN_CLASS:              'zenClass';
ZEN_CONSTRUCTOR:        'zenConstructor';
SCRIPT:                 'script';

// Separators

PAREN_OPEN:             '(';
PAREN_CLOSE:            ')';
BRACK_OPEN:             '[';
BRACK_CLOSE:            ']';
BRACE_OPEN:             '{';
BRACE_CLOSE:            '}';
COMMA:                  ',';
DOT:                    '.';
SEMICOLON:              ';';

// Operators

ADD:                    '+';
SUB:                    '-';
MUL:                    '*';
DIV:                    '/';
MOD:                    '%';
CAT:                    '~';
NOT:                    '!';
LESS:                   '<';
GREATER:                '>';
XOR:                    '^';
COLON:                  ':';
QUEST:                  '?';
BACKTICK:               '`';
DOLLAR:                 '$';
AND:                    '&';
OR:                     '|';
ASSIGN:                 '=';

AND_AND:                '&&';
OR_OR:                  '||';
EQUAL:                  '==';
NOT_EQUAL:              '!=';
LESS_EQUAL:             '<=';
GREATER_EQUAL:          '>=';
PLUS_ASSIGN:            '+=';
MINUS_ASSIGN:           '-=';
STAR_ASSIGN:            '*=';
DIV_ASSIGN:             '/=';
MOD_ASSIGN:             '%=';
XOR_ASSIGN:             '^=';
AND_ASSIGN:             '&=';
OR_ASSIGN:              '|=';
CAT_ASSIGN:             '~=';
DOT_DOT:                '..';

// Literals

DECIMAL_LITERAL: ('0' | [1-9] [0-9]*) [lL]?;

HEX_LITERAL: '0' [xX] HexDigits [lL]?;

FLOATING_LITERAL
    : Digits '.' Digits ([eE] Digits)? [fFdD]?
    ;

BOOLEAN_LITERAL
    : 'true'
    | 'false'
    ;

STRING_LITERAL
    : '"' (~["\\\r\n] | EscapeSequence)* '"'
    | '\'' (~["\\\r\n] | EscapeSequence)* '\'';

NULL_LITERAL: 'null';

// Identifier

IDENTIFIER: Letter LetterOrDigit*;

// Whitespace and comments

WHITE_SPACE: [ \t\r\n]+ -> channel(HIDDEN);
BLOCK_COMMENT: '/*' .*? '*/' -> channel(HIDDEN);
LINE_COMMENT: '//' ~[\r\n]* -> channel(HIDDEN);
Preprocessor: '#' ~[\r\n]* -> channel(HIDDEN);

// Fragments

fragment EscapeSequence
    : '\\' [btnfr"'\\]
    | UnicodeCharacter
    ;

fragment UnicodeCharacter: '\\'[uU] HexDigit HexDigit HexDigit HexDigit;

fragment Digits: Digit+;

fragment Digit: [0-9];

fragment HexDigits: HexDigit+;

fragment HexDigit: [0-9a-fA-F];

fragment LetterOrDigit: Letter | [0-9];

fragment Letter: [a-zA-Z_];
