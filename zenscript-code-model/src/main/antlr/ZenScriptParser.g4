parser grammar ZenScriptParser;

options { tokenVocab = ZenScriptLexer; }

compilationUnit
    : topLevelElement* EOF
    ;

topLevelElement
    : importDeclaration
    | classDeclaration
    | functionDeclaration
    | expandFunctionDeclaration
    | statement
    ;

importDeclaration
    : 'import' qualifiedName ('as' alias = simpleName)? ';'
    ;

qualifiedName
    : simpleName ('.' simpleName)*
    ;

simpleName
    : IDENTIFIER
    | 'to'
    | 'extends'  // dzs
    | 'operator' // dzs
    | 'for_in'   // dzs
    | 'orderly'  // zenutils
    ;

functionDeclaration
    : prefix='static'? 'function' simpleName '(' (formalParameter (',' formalParameter)*)? ')' ('as' returnType)? functionBody
    | prefix=('static' | 'global')? 'function' simpleName? '(' (formalParameter (',' formalParameter)*)? ')' 'as' returnType ';' // dzs
    ;

expandFunctionDeclaration
    : '$expand' typeLiteral '$' simpleName '(' (formalParameter (',' formalParameter)*)? ')' ('as' returnType)? functionBody
    ;

formalParameter
    : simpleName ('as' typeLiteral)? ('=' defaultValue)?
    | varargsPrefix simpleName ('as' typeLiteral)? ('=' defaultValue)? //dzs
    ;

varargsPrefix // dzs
    : '...'
    ;

defaultValue
    : expression
    ;

returnType
    : typeLiteral
    ;

functionBody
    : '{' statement* '}'
    ;

classDeclaration
    : 'zenClass' simpleClassName ('extends' qualifiedName (',' qualifiedName)*)? classBody // extended for dzs
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
    | operatorFunctionDeclaration // dzs
    | invaildStatementInClassBody
    ;

invaildStatementInClassBody
    : expression ';'?
    ;

constructorDeclaration
    : 'zenConstructor' '(' (formalParameter (',' formalParameter)*)? ')' constructorBody
    | 'zenConstructor' '(' (formalParameter (',' formalParameter)*)? ')' ';' // dzs
    ;

constructorBody
    : '{' statement* '}'
    ;

variableDeclaration
    : prefix=('var' | 'val' | 'static' | 'global') simpleName ('as' typeLiteral)? ('=' initializer = expression)? ';'
    | prefix=('var' | 'val' | 'static' | 'global') simpleName 'as' typeLiteral ';' //dzs
    ;

operatorFunctionDeclaration // dzs
    : 'operator' operator '(' (formalParameter (',' formalParameter)*)? ')' 'as' returnType ';'
    ;

operator // dzs
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

statement
    : blockStatement
    | returnStatement
    | breakStatement
    | continueStatement
    | ifStatement
    | foreachStatement
    | whileStatement
    | variableDeclaration
    | expressionStatement
    ;

blockStatement
    : '{' statement* '}'
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
    : 'if' expression thenPart = statement ('else' elsePart = statement)?
    ;

foreachStatement
    : 'for' foreachVariable (',' foreachVariable)* 'in' expression foreachBody
    ;

foreachVariable
    : simpleName
    ;

foreachBody
    : '{' statement* '}'
    ;

whileStatement
    : 'while' expression statement
    ;

expressionStatement
    : expression ';'?
    ;

// Paraphrased from https://github.com/CraftTweaker/ZenScript/blob/master/src/main/java/stanhebben/zenscript/parser/expression/ParsedExpression.java
expression
    // Not really sure about this
    : 'this'  #thisExpr

    // ParsedExpression.java: L308-L453
    | literal=(TRUE | FALSE | NULL | DECIMAL_LITERAL | HEX_LITERAL | FLOAT_LITERAL | STRING_LITERAL)  #literalExpr
    | simpleName  #simpleNameExpr
    | 'function' '(' (formalParameter (',' formalParameter)*)? ')' ('as' typeLiteral)? functionBody  #functionExpr
    | '<' raw '>'                  #bracketHandlerExpr
    | '[' (expression (',' expression)*)? ','? ']'  #arrayLiteralExpr
    | '{' (mapEntry (',' mapEntry)*)? ','? '}'    #mapLiteralExpr
    | '(' expression ')'           #parensExpr

    // ParsedExpression.java: L35-L306
    | left=expression op='instanceof' right=expression       #instanceOfExpr
    | expression 'as' typeLiteral                            #typeCastExpr
    | caller=expression '(' (argument (',' argument)*)? ','? ')'  #callExpr
    | left=expression '[' index=expression ']'               #memberIndexExpr
    | expression op='.' (simpleName | STRING_LITERAL)        #memberAccessExpr
    | from=expression op=('..' | 'to') to=expression         #intRangeExpr
    | <assoc=right> op=('!' | '-') expression                #unaryExpr
    | left=expression op=('*' | '/' | '%') right=expression  #binaryExpr
    | left=expression op=('+' | '-' | '~') right=expression  #binaryExpr
    | left=expression op=('==' | '!=' | '<' | '<=' | '>' | '>=') right=expression  #binaryExpr
    | left=expression op=('|' | '^' | '&' | 'in' | 'has') right=expression   #binaryExpr
    | left=expression op=('||' | '&&') right=expression                      #binaryExpr
    | condition=expression '?' truePart=expression ':' falsePart=expression  #ternaryExpr
    | <assoc=right> left=expression op=('=' | '+=' | '-=' | '~=' | '*=' | '/=' | '%=' | '|=' | '&=' | '^=') right=expression  #assignmentExpr
    ;

raw
    : (~'>')*
    ;

argument
    : expression
    ;

mapEntry
    : key=expression ':' value=expression
    ;

typeLiteral
    : qualifiedName                                                    #classType
    | 'function' '(' (typeLiteral (',' typeLiteral)*)? ')' returnType  #functionType
    | '[' typeLiteral ']'                                              #listType
    | typeLiteral '['']'                                               #arrayType
    | value=typeLiteral '[' key=typeLiteral ']' ('$' 'orderly')?       #mapType
    | typeLiteral ('&' typeLiteral)+                                   #intersectionType // dzs
    | typeLiteral ('|' typeLiteral)+                                   #unionType        // dzs
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
