parser grammar ZenScriptDeclarationsParser;
import ZenScriptParser;

options { tokenVocab = ZenScriptDeclarationsLexer; }

compilationUnit
    : toplevelEntity* EOF
    ;

functionDeclaration
    : prefix=('static' | 'global')? 'function' simpleIdentifier '(' (parameters+=formalParameter (',' parameters+=formalParameter)*)? ')' 'as' returnType=type ';'
    ;

formalParameter
    : prefix='...'? simpleIdentifier 'as' type ('=' defaultValue=expression)?
    ;

classDeclaration
    : 'zenClass' simpleClassName ('extends' interfaces+=classReference (',' interfaces+=classReference)*)? classBody
    ;

simpleClassName
    : simpleIdentifier
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

classReference
    : identifier
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
    : prefix='static'? 'function' simpleIdentifier? '(' (parameters+=formalParameter (',' parameters+=formalParameter)*)? ')' 'as' returnType=type ';'
    ;

fieldDeclaration
    : prefix=('var' | 'val' | 'static') simpleIdentifier 'as' type ';'
    ;

operatorFunctionDeclaration
    : 'operator' operator '(' (parameters+=formalParameter (',' parameters+=formalParameter)*)? ')' 'as' returnType=type ';'
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

type
    : 'function' '(' (parameters+=type (',' parameters+=type)*)? ')' returnType=type #functionType
    | '(' type ')' #parenthesizedType
    | '[' type ']' #listType
    | type '['']' #arrayType
    | value=type '[' key=type ']' ('$' 'orderly')? #mapType
    | simpleIdentifier ('.' simpleIdentifier)* #userType
    | 'any'    #primitiveType
    | 'byte'   #primitiveType
    | 'short'  #primitiveType
    | 'int'    #primitiveType
    | 'long'   #primitiveType
    | 'float'  #primitiveType
    | 'double' #primitiveType
    | 'bool'   #primitiveType
    | 'void'   #primitiveType
    | 'string' #primitiveType
    ;

identifier
    : simpleIdentifier ('.' simpleIdentifier)*
    ;

simpleIdentifier
    : Identifier
    | 'to'
    | 'orderly'
    | 'extends'
    | 'operator'
    | 'for_in'
    ;
