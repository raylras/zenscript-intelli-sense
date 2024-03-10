parser grammar ZenScriptDeclarationsParser;
import ZenScriptParser;

options { tokenVocab = ZenScriptDeclarationsLexer; }

compilationUnit
    : toplevelEntity* EOF
    ;

simpleName
    : IDENTIFIER
    | 'to'
    | 'orderly'
    | 'extends'
    | 'operator'
    | 'for_in'
    ;

functionDeclaration
    : prefix=('static' | 'global')? 'function' simpleName '(' (parameters+=formalParameter (',' parameters+=formalParameter)*)? ')' 'as' returnType=typeLiteral ';'
    ;

formalParameter
    : prefix='...'? simpleName 'as' typeLiteral ('=' defaultValue=expression)?
    ;

classDeclaration
    : 'zenClass' simpleClassName ('extends' interfaces+=qualifiedName (',' interfaces+=qualifiedName)*)? classBody
    ;

simpleClassName
    : simpleName
    | 'any'
    | 'byte'
    | 'short'
    | 'int'
    | 'long'
    | 'float'
    | 'double'
    | 'bool'
    | 'void'
    | 'string'
    ;

classBodyEntity
    : fieldDeclaration
    | constructorDeclaration
    | methodDeclaration
    | operatorFunctionDeclaration
    ;

constructorDeclaration
    : 'zenConstructor' '(' (parameters+=formalParameter (',' parameters+=formalParameter)*)? ')' ';'
    ;

methodDeclaration
    : prefix='static'? 'function' simpleName? '(' (parameters+=formalParameter (',' parameters+=formalParameter)*)? ')' 'as' returnType=typeLiteral ';'
    ;

fieldDeclaration
    : prefix=('var' | 'val' | 'static') simpleName 'as' typeLiteral ';'
    ;

operatorFunctionDeclaration
    : 'operator' operator '(' (parameters+=formalParameter (',' parameters+=formalParameter)*)? ')' 'as' returnType=typeLiteral ';'
    ;

operator
    : '+'
    | '-'
    | '*'
    | '/'
    | '%'
    | '~'
    | '|'
    | '&'
    | '^'
    | '!'
    | '['']'
    | '['']''='
    | '..'
    | 'has'
    | '.'
    | '.''='
    | 'for_in'
    | 'as'
    | '=='
    | '!='
    | '<'
    | '<='
    | '>'
    | '>='
    ;

typeLiteral
    : qualifiedName  #referenceType
    | 'function' '(' (parameterTypes+=typeLiteral (',' parameterTypes+=typeLiteral)*)? ')' returnType=typeLiteral  #functionType
    | '[' typeLiteral ']'  #listType
    | typeLiteral '['']'   #arrayType
    | valueType=typeLiteral '[' keyType=typeLiteral ']' ('$' 'orderly')?  #mapType
    | typeLiteral ('&' typeLiteral)+  #intersectionType
    | typeLiteral ('|' typeLiteral)+  #unionType
    | ANY     #primitiveType
    | BYTE    #primitiveType
    | SHORT   #primitiveType
    | INT     #primitiveType
    | LONG    #primitiveType
    | FLOAT   #primitiveType
    | DOUBLE  #primitiveType
    | BOOL    #primitiveType
    | VOID    #primitiveType
    | STRING  #primitiveType
    ;
