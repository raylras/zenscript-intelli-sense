package raylras.intellizen.parser;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

%%

%class ZenScriptLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

// White Space
WhiteSpace = [\ \t\r\n]
BlockComment = "/*" .* "*/"
LineComment = "//" [^\r\n]*
Preprocessor = "#" [^\r\n]*

// Identifier
Identifier = {Letter} {LetterOrDigit}*
Letter
    = [a-zA-Z_]
    | [^\u0000-\u007F\uD800-\uDBFF] // covers all characters above 0x7F which are not a surrogate
    | [\uD800-\uDBFF] [\uDC00-\uDFFF] // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
LetterOrDigit = {Letter} | [0-9]

// String
StringLiteral = {SingleQuotedString} | {DoubleQuotedString}
SingleQuotedString = \'{SingleQuotedStringContent}*\'
SingleQuotedStringContent = [^\'\\] | \\[btnfr\"\'\\] | {UnicodeEscape}
DoubleQuotedString = \"{DoubleQuotedStringCintent}*\"
DoubleQuotedStringCintent = [^\"\\] | \\[btnfr\"\'\\] | {UnicodeEscape}
UnicodeEscape = \\[uU]{HexDigit}{4}

// Number
Digit = [0-9]
HexDigit = [0-9a-fA-F]

DecLiteral = 0 | [1-9] {Digit}*
HexLiteral = 0 [xX] {HexDigit}+
BinLiteral = 0 [bB] [01]+

IntLiteral = {DecLiteral} | {HexLiteral} | {BinLiteral}
LongLiteral = ({DecLiteral} | {HexLiteral} | {BinLiteral}) [lL]

FloatLiteral = {Digit}+ \. {Digit}+ {ExponentPart}? [fF]
DoubleLiteral = {Digit}+ \. {Digit}+ {ExponentPart}? [dD]?
ExponentPart = [eE] [+-]? {Digit}+

%%

<YYINITIAL>
{
"var"               { return KW_VAR; }
"val"               { return KW_VAL; }
"global"            { return KW_GLOBAL; }
"static"            { return KW_STATIC; }
"import"            { return KW_IMPORT; }
"function"          { return KW_FUNCTION; }
"as"                { return KW_AS; }
"to"                { return KW_TO; }
"in"                { return KW_IN; }
"has"               { return KW_HAS; }
"instanceof"        { return KW_INSTANCEOF; }
"this"              { return KW_THIS; }
"zenClass"          { return KW_ZENCLASS; }
"zenConstructor"    { return KW_ZENCONSTRUCTOR; }
"$expand"           { return KW_EXPAND; }

"if"                { return KW_IF; }
"else"              { return KW_ELSE; }
"for"               { return KW_FOR; }
"while"             { return KW_WHILE; }
"break"             { return KW_BREAK; }
"continue"          { return KW_CONTINUE; }
"return"            { return KW_RETURN; }

"any"               { return KW_ANY; }
"byte"              { return KW_BYTE; }
"short"             { return KW_SHORT; }
"int"               { return KW_INT; }
"long"              { return KW_LONG; }
"float"             { return KW_FLOAT; }
"double"            { return KW_DOUBLE; }
"bool"              { return KW_BOOL; }
"void"              { return KW_VOID; }
"string"            { return KW_STRING; }

"true"              { return KW_TRUE; }
"false"             { return KW_FALSE; }
"null"              { return KW_NULL; }

"orderly"           { return KW_ORDERLY; }  // zenutils

"("                 { return LPAREN;}
")"                 { return RPAREN;}
"["                 { return LSQUARE;}
"]"                 { return RSQUARE;}
"<"                 { return LANGLE;}
">"                 { return RANGLE;}
"{"                 { return LCURL;}
"}"                 { return RCURL;}

","                 { return COMMA; }
"."                 { return DOT; }
";"                 { return SEMICOLON; }
"+"                 { return ADD; }
"-"                 { return SUB; }
"*"                 { return MUL; }
"/"                 { return DIV; }
"%"                 { return MOD; }
"~"                 { return CONCAT; }
"!"                 { return NOT; }
"^"                 { return XOR; }
":"                 { return COLON; }
"?"                 { return QUEST; }
"$"                 { return DOLLAR; }
"&"                 { return AND; }
"|"                 { return OR; }
"="                 { return EQ; }

"&&"                { return AND_AND; }
"||"                { return OR_OR; }
"=="                { return EQ_EQ; }
"!="                { return NOT_EQ; }
"<="                { return LESS_EQ; }
">="                { return GREATER_EQ; }
"+="                { return ADD_EQ; }
"-="                { return SUB_EQ; }
"*="                { return MUL_EQ; }
"/="                { return DIV_EQ; }
"%="                { return MOD_EQ; }
"^="                { return XOR_EQ; }
"&="                { return AND_EQ; }
"|="                { return OR_EQ; }
"~="                { return CONCAT_EQ; }
".."                { return DOT_DOT; }

{Identifier}        { return IDENTIFIER; }

{StringLiteral}     { return STRING_LITERAL; }
{IntLiteral}        { return INTEGER_LITERAL; }
{LongLiteral}       { return LONG_LITERAL; }
{FloatLiteral}      { return FLOAT_LITERAL; }
{DoubleLiteral}     { return DOUBLE_LITERAL; }

{WhiteSpace}        { return WHITE_SPACE; }
{BlockComment}      { return BLOCK_COMMENT; }
{LineComment}       { return LINE_COMMENT; }
{Preprocessor}      { return PREPROCESSOR; }
}

[^] { return BAD_CHARACTER; }
