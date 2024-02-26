parser grammar ZenScriptDeclarationParser;

options { tokenVocab = ZenScriptDeclarationLexer; }

compilationUnit
    : ( importDeclaration
      | classDeclaration
      | functionDeclaration
      | variableDeclaration
      )* EOF
    ;

importDeclaration
    : 'import' qualifiedName ';'
    ;

qualifiedName
    : simpleName ('.' simpleName)*
    ;

simpleName
    : IDENTIFIER
    | 'extends'
    | 'operator'
    | 'for_in'
    ;

functionDeclaration
    : prefix=('static' | 'global')? 'function' simpleName '(' (formalParameter (',' formalParameter)*)? ')' 'as' returnType ';'
    ;

formalParameter
    : prefix='...'? simpleName 'as' typeLiteral ('=' defaultValue=expression)?
    ;

returnType
    : typeLiteral
    ;

classDeclaration
    : 'zenClass' simpleClassName ('extends' qualifiedName (',' qualifiedName)*)? classBody
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

classBody
    : '{' classMemberDeclaration* '}'
    ;

classMemberDeclaration
    : variableDeclaration
    | constructorDeclaration
    | functionDeclaration
    | operatorFunctionDeclaration
    | functionalInterfaceDeclaration
    ;

functionalInterfaceDeclaration
    : 'function' simpleName '(' (formalParameter (',' formalParameter)*)? ')' 'as' returnType ';'
    ;

constructorDeclaration
    : 'zenConstructor' '(' (formalParameter (',' formalParameter)*)? ')' ';'
    ;

variableDeclaration
    : prefix=('var' | 'val' | 'static' | 'global') simpleName 'as' typeLiteral ';'
    ;

operatorFunctionDeclaration
    : 'operator' operator '(' (formalParameter (',' formalParameter)*)? ')' 'as' returnType ';'
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

expression
    : DEC_LITERAL     #decimalLiteral
    | HEX_LITERAL     #hexadecimalLiteral
    | BIN_LITERAL     #binaryLiteral
    | (TRUE | FALSE)  #boolLiteral
    | NULL            #nullLiteral
    | STRING_LITERAL  #stringLiteral
    | FLOAT_LITERAL   #floatLiteral
    | DOUBLE_LITERAL  #doubleLiteral
    ;

typeLiteral
    : qualifiedName                                                    #classType
    | 'function' '(' (typeLiteral (',' typeLiteral)*)? ')' returnType  #functionType
    | '[' typeLiteral ']'                                              #listType
    | typeLiteral '['']'                                               #arrayType
    | value=typeLiteral '[' key=typeLiteral ']'                        #mapType
    | typeLiteral ('&' typeLiteral)+                                   #intersectionType
    | typeLiteral ('|' typeLiteral)+                                   #unionType
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
