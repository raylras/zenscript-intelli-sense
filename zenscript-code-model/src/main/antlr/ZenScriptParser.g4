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
    : 'import' qualifiedName ('as' alias)? ';'
    ;

qualifiedName
    : simpleName ('.' simpleName)*
    ;

alias
    : simpleName
    ;

simpleName
    : IDENTIFIER
    | 'to'
    | 'version'
    | 'extends'  // dzs
    | 'operator' // dzs
    | 'for_in'   // dzs
    | 'orderly'  // zenutils
    ;

functionDeclaration
    : prefix='static'? 'function' simpleName '(' formalParameterList ')' ('as' returnType)? functionBody
    | prefix=('static' | 'global')? 'function' simpleName? '(' formalParameterList ')' 'as' returnType ';' // dzs
    ;

expandFunctionDeclaration
    : '$expand' typeLiteral '$' simpleName '(' formalParameterList ')' ('as' returnType)? functionBody
    ;

formalParameterList
    : formalParameter (',' formalParameter)*
    |
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
    : 'zenClass' simpleName classBody
    | 'zenClass' simpleNameOrPrimitiveType ('extends' qualifiedNameList)? classBody // dzs
    ;

simpleNameOrPrimitiveType
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

qualifiedNameList
    : qualifiedName (',' qualifiedName)*
    ;

classBody
    : '{' classMemberDeclaration* '}'
    ;

classMemberDeclaration
    : variableDeclaration
    | constructorDeclaration
    | functionDeclaration
    | operatorFunctionDeclaration // dzs
    ;

constructorDeclaration
    : 'zenConstructor' '(' formalParameterList ')' constructorBody
    | 'zenConstructor' '(' formalParameterList ')' ';' // dzs
    ;

constructorBody
    : '{' statement* '}'
    ;

variableDeclaration
    : prefix=('var' | 'val' | 'static' | 'global') simpleName ('as' typeLiteral)? ('=' initializer)? ';'
    | prefix=('var' | 'val' | 'static' | 'global') simpleName 'as' typeLiteral ';' //dzs
    ;

initializer
    : expression
    ;

operatorFunctionDeclaration // dzs
    : 'operator' operator '(' formalParameterList ')' 'as' returnType ';'
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
    | versionStatement
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
    : 'if' expression thenPart ('else' elsePart)?
    ;

thenPart
    : statement
    ;

elsePart
    : statement
    ;

foreachStatement
    : 'for' foreachVariableList 'in' expression foreachBody
    ;

versionStatement
    : 'version' INT_LITERAL ';'
    ;

foreachVariableList
    : foreachVariable (',' foreachVariable)*
    ;

foreachVariable
    : simpleName
    ;

foreachBody
    : '{' statement* '}'
    ;

whileStatement
    : 'while' '(' expression ')' statement
    ;

expressionStatement
    : expression ';'?
    ;

// Paraphrased from https://github.com/CraftTweaker/ZenScript/blob/master/src/main/java/stanhebben/zenscript/parser/expression/ParsedExpression.java
expression
    // Not really sure about this
    : 'this'  #thisExpr

    // ParsedExpression.java: L308-L453
    | literal     #literalExpr
    | simpleName  #simpleNameExpr
    | 'function' '(' formalParameterList ')' ('as' typeLiteral)? functionBody  #functionExpr
    | '<' raw '>'                  #bracketHandlerExpr
    | '[' expressionList ','? ']'  #arrayLiteralExpr
    | '{' mapEntryList ','? '}'    #mapLiteralExpr
    | '(' expression ')'           #parensExpr

    // ParsedExpression.java: L35-L306
    | left=expression op='instanceof' right=expression       #instanceOfExpr
    | expression 'as' typeLiteral                            #typeCastExpr
    | expression '(' expressionList ')'                      #callExpr
    | left=expression '[' index=expression ']'               #memberIndexExpr
    | from=expression op=('..' | 'to') to=expression         #intRangeExpr
    | expression op='.' (simpleName | STRING_LITERAL)        #memberAccessExpr
    | <assoc=right> op=('!' | '-') expression                #unaryExpr
    | left=expression op=('*' | '/' | '%') right=expression  #binaryExpr
    | left=expression op=('+' | '-' | '~') right=expression  #binaryExpr
    | left=expression op=('==' | '!=' | '<' | '<=' | '>' | '>=') right=expression  #compareExpr
    | left=expression op=('|' | '^' | '&' | 'in' | 'has') right=expression   #binaryExpr
    | left=expression op=('||' | '&&') right=expression                      #logicExpr
    | condition=expression '?' truePart=expression ':' falsePart=expression  #ternaryExpr
    | <assoc=right> left=expression op=('=' | '+=' | '-=' | '~=' | '*=' | '/=' | '%=' | '|=' | '&=' | '^=') right=expression  #assignmentExpr
    ;

literal
    : INT_LITERAL
    | LONG_LITERAL
    | FLOAT_LITERAL
    | DOUBLE_LITERAL
    | STRING_LITERAL
    | TRUE_LITERAL
    | FALSE_LITERAL
    | NULL_LITERAL
    ;

raw
    : (~'>')*
    ;

expressionList
    : expression (',' expression)*
    |
    ;

mapEntryList
    : mapEntry (',' mapEntry)*
    |
    ;

mapEntry
    : key=expression ':' value=expression
    ;

typeLiteral
    : qualifiedName                                               #classType
    | 'function' '(' typeLiteralList ')' returnType               #functionType
    | '[' typeLiteral ']'                                         #listType
    | typeLiteral '['']'                                          #arrayType
    | value=typeLiteral '[' key=typeLiteral ']' ('$' 'orderly')?  #mapType
    | typeLiteral ('&' typeLiteral)+                              #intersectionType // dzs
    | typeLiteral ('|' typeLiteral)+                              #unionType        // dzs
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

typeLiteralList
    : typeLiteral (',' typeLiteral)*
    |
    ;
