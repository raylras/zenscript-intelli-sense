lexer grammar ZenScriptDeclarationLexer;

channels {
    WHITE_SPACE_CHANNEL,
    COMMENTS_CHANNEL
}

VAR:                    'var';
VAL:                    'val';
GLOBAL:                 'global';
STATIC:                 'static';
IMPORT:                 'import';
FUNCTION:               'function';
AS:                     'as';
IN:                     'in';
HAS:                    'has';
ZEN_CLASS:              'zenClass';
ZEN_CONSTRUCTOR:        'zenConstructor';

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

TRUE:                   'true';
FALSE:                  'false';
NULL:                   'null';

EXTENDS:                'extends';
OPERATOR:               'operator';
FOR_IN:                 'for_in';

PAREN_OPEN:             '(';
PAREN_CLOSE:            ')';
BRACK_OPEN:             '[';
BRACK_CLOSE:            ']';
BRACE_OPEN:             '{';
BRACE_CLOSE:            '}';
COMMA:                  ',';
DOT:                    '.';
SEMICOLON:              ';';

ADD:                    '+';
SUB:                    '-';
MUL:                    '*';
DIV:                    '/';
MOD:                    '%';
CONCAT:                 '~';
NOT:                    '!';
LESS_THEN:              '<';
GREATER_THEN:           '>';
XOR:                    '^';
COLON:                  ':';
QUESTION:               '?';
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
CONCAT_ASSIGN:          '~=';
DOT_DOT:                '..';
DOT_DOT_DOT:            '...';

DEC_LITERAL: ('0' | [1-9] Digits?) [lL]?;
HEX_LITERAL: '0' [xX] HexDigits [lL]?;
BIN_LITERAL: '0' [bB] [01]+ [lL]?;

FLOAT_LITERAL: Digits '.' Digits ExponentPart? [fF];
DOUBLE_LITERAL: Digits '.' Digits ExponentPart? [dD]?;

STRING_LITERAL
    : '"' (~["\\] | EscapeSequence)* '"'
    | '\'' (~['\\] | EscapeSequence)* '\'';

IDENTIFIER: Letter LetterOrDigit*;

WHITE_SPACE: [ \t\r\n]+ -> channel(WHITE_SPACE_CHANNEL);
BLOCK_COMMENT: '/*' .*? '*/' -> channel(COMMENTS_CHANNEL);
LINE_COMMENT: '//' ~[\r\n]* -> channel(COMMENTS_CHANNEL);

fragment EscapeSequence
    : '\\' [btnfr"'\\]
    | '\\' [uU] HexDigit HexDigit HexDigit HexDigit
    ;

fragment ExponentPart: [eE] [+-]? Digits;

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
