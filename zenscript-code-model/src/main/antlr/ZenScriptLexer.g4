lexer grammar ZenScriptLexer;

channels {
    WHITE_SPACE_CHANNEL,
    COMMENTS_CHANNEL,
    PREPROCESSOR_CHANNEL
}

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
VERSION:                'version';

ANY:                    'any';
BYTE:                   'byte';
SHORT:                  'short';
INT:                    'int';
LONG:                   'long';
FLOAT:                  'float';
DOUBLE:                 'double';
BOOL:                   'bool';
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
EXPAND:                 '$expand';

EXTENDS:                'extends';  // dzs
OPERATOR:               'operator'; // dzs
FOR_IN:                 'for_in';   // dzs

ORDERLY:                'orderly';  // zenutils

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
ADD_ASSIGN:             '+=';
SUB_ASSIGN:             '-=';
MUL_ASSIGN:             '*=';
DIV_ASSIGN:             '/=';
MOD_ASSIGN:             '%=';
XOR_ASSIGN:             '^=';
AND_ASSIGN:             '&=';
OR_ASSIGN:              '|=';
CAT_ASSIGN:             '~=';
DOT_DOT:                '..';
DOT_DOT_DOT:            '...'; // dzs

// Literal

INT_LITERAL: ('0' | [1-9] [0-9]* ) | ('0' [xX] HexDigits);

LONG_LITERAL: ('0' | [1-9] [0-9]*) | ('0' [xX] HexDigits) [lL];

FLOAT_LITERAL
    : Digits '.' Digits ([eE] Digits)? [fF]
    ;

DOUBLE_LITERAL
    : Digits '.' Digits ([eE] Digits)? [dD]?
    ;

TRUE_LITERAL
    : 'true'
    ;

FALSE_LITERAL
    : 'false'
    ;

STRING_LITERAL
    : '"' (~["\\\r\n] | EscapeSequence)* '"'
    | '\'' (~["\\\r\n] | EscapeSequence)* '\'';

NULL_LITERAL: 'null';

// Identifier

IDENTIFIER: Letter LetterOrDigit*;

// Whitespace and comments

WHITE_SPACE: [ \t\r\n]+ -> channel(WHITE_SPACE_CHANNEL);
BLOCK_COMMENT: '/*' .*? '*/' -> channel(COMMENTS_CHANNEL);
LINE_COMMENT: '//' ~[\r\n]* -> channel(COMMENTS_CHANNEL);
Preprocessor: '#' ~[\r\n]* -> channel(PREPROCESSOR_CHANNEL);

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

fragment Letter
    : [a-zA-Z_]
    | ~[\u0000-\u007F\uD800-\uDBFF] // covers all characters above 0x7F which are not a surrogate
    | [\uD800-\uDBFF] [\uDC00-\uDFFF] // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
    ;
