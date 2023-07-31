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
    ;

functionDeclaration
    : prefix='static'? 'function' simpleName '(' formalParameterList ')' ('as' returnType)? functionBody
    | prefix=('static' | 'global')? 'function' simpleName '(' formalParameterList ')' 'as' returnType ';'
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
    : 'zenClass' simpleNameOrPrimitiveType classBody
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

classBody
    : '{' classMemberDeclaration* '}'
    ;

classMemberDeclaration
    : variableDeclaration
    | constructorDeclaration
    | functionDeclaration
    ;

constructorDeclaration
    : 'zenConstructor' '(' formalParameterList ')' constructorBody
    ;

constructorBody
    : '{' statement* '}'
    ;

variableDeclaration
    : prefix=('var' | 'val' | 'static' | 'global') simpleName ('as' typeLiteral)? ('=' initializer)? ';'
    ;

initializer
    : expression
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
    : expression ';'
    ;

// Paraphrased from https://github.com/CraftTweaker/ZenScript/blob/master/src/main/java/stanhebben/zenscript/parser/expression/ParsedExpression.java
expression
    // Not really sure about this
    : 'this'  #thisExpr

    // ParsedExpression.java: L308-L453
    | literal     #literalExpr
    | simpleName  #simpleNameExpr
    | 'function' '(' formalParameterList ')' ('as' typeLiteral)? functionBody  #functionExpr
    | '<' (~'>')*? '>'              #bracketHandlerExpr
    | '[' expressionList ','? ']'  #arrayLiteralExpr
    | '{' mapEntryList ','? '}'    #mapLiteralExpr
    | '(' expression ')'            #parensExpr

    // ParsedExpression.java: L35-L306
    | left=expression op='instanceof' right=expression       #instanceOfExpr
    | expression 'as' typeLiteral                            #typeCastExpr
    | expression '(' expressionList ')'                     #callExpr
    | left=expression '[' index=expression ']'               #memberIndexExpr
    | from=expression op=('..' | 'to') to=expression         #intRangeExpr
    | expression op='.' (simpleName | STRING_LITERAL)        #memberAccessExpr
    | <assoc=right> op=('!' | '-') expression                #unaryExpr
    | left=expression op=('*' | '/' | '%') right=expression  #binaryExpr
    | left=expression op=('+' | '-' | '~') right=expression  #binaryExpr
    | left=expression op=('==' | '!=' | '<' | '<=' | '>' | '>=' | 'in' | 'has') right=expression  #compareExpr
    | left=expression op=('|' | '^' | '&') right=expression                  #binaryExpr
    | left=expression op=('||' | '&&') right=expression                      #binaryExpr
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
    : qualifiedName                                  #classType
    | 'function' '(' typeLiteralList ')' returnType  #functionType
    | '[' typeLiteral ']'                            #listType
    | typeLiteral '['']'                             #arrayType
    | value=typeLiteral '[' key=typeLiteral ']'      #mapType
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
