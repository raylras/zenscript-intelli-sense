lexer grammar ZenScriptLexer;

channels {
    WHITE_SPACE_CHANNEL,
    COMMENTS_CHANNEL,
    PREPROCESSOR_CHANNEL
}

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
ZEN_CLASS:              'zenClass';
ZEN_CONSTRUCTOR:        'zenConstructor';
EXPAND:                 '$expand';

IF:                     'if';
ELSE:                   'else';
FOR:                    'for';
WHILE:                  'while';
BREAK:                  'break';
CONTINUE:               'continue';
RETURN:                 'return';

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

ORDERLY:                'orderly';  // zenutils

LPAREN:                 '(';
RPAREN:                 ')';
LSQUARE:                '[';
RSQUARE:                ']';
LANGLE:                 '<';
RANGLE:                 '>';
LCURL:                  '{';
RCURL:                  '}';

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
XOR:                    '^';
COLON:                  ':';
QUEST:                  '?';
DOLLAR:                 '$';
AND:                    '&';
OR:                     '|';
EQ:                     '=';

AND_AND:                '&&';
OR_OR:                  '||';
EQ_EQ:                  '==';
NOT_EQ:                 '!=';
LESS_EQ:                '<=';
GREATER_EQ:             '>=';
ADD_EQ:                 '+=';
SUB_EQ:                 '-=';
MUL_EQ:                 '*=';
DIV_EQ:                 '/=';
MOD_EQ:                 '%=';
XOR_EQ:                 '^=';
AND_EQ:                 '&=';
OR_EQ:                  '|=';
CONCAT_EQ:              '~=';
DOT_DOT:                '..';

fragment DecLiteral: ('0' | [1-9] Digits?);
fragment HexLiteral: '0' [xX] HexDigits;
fragment BinLiteral: '0' [bB] [01]+;

IntLiteral: (DecLiteral | HexLiteral | BinLiteral);
LongLiteral: (DecLiteral | HexLiteral | BinLiteral) [lL];

FloatLiteral: Digits '.' Digits ExponentPart? [fF];
DoubleLiteral: Digits '.' Digits ExponentPart? [dD]?;

BoolLiteral: 'true' | 'false';

NullLiteral: 'null';

StringLiteral
    : '"' (~["\\] | EscapeSequence)* '"'
    | '\'' (~['\\] | EscapeSequence)* '\'';

Identifier: Letter LetterOrDigit*;

WhiteSpace: [ \t\r\n]+ -> channel(WHITE_SPACE_CHANNEL);
BlockComment: '/*' .*? '*/' -> channel(COMMENTS_CHANNEL);
LineComment: '//' ~[\r\n]* -> channel(COMMENTS_CHANNEL);
Preprocessor: '#' ~[\r\n]* -> channel(PREPROCESSOR_CHANNEL);

fragment EscapeSequence
    : '\\' [btnfr"'\\]
    | '\\' [uU] HexDigit HexDigit HexDigit HexDigit
    | '\\' . // illegal escape
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
