parser grammar ZenScriptParser;

options { tokenVocab = ZenScriptLexer; }

compilationUnit
    : ( importDeclaration
      | classDeclaration
      | functionDeclaration
      | expandFunctionDeclaration
      | statement
      )* EOF
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
    | 'orderly' // zenutils
    ;

functionDeclaration
    : prefix='static'? 'function' simpleName '(' (formalParameter (',' formalParameter)*)? ')' ('as' returnType)? functionBody
    ;

expandFunctionDeclaration
    : '$expand' typeLiteral '$' simpleName '(' (formalParameter (',' formalParameter)*)? ')' ('as' returnType)? functionBody
    ;

formalParameter
    : simpleName ('as' typeLiteral)? ('=' defaultValue=expression)?
    ;

returnType
    : typeLiteral
    ;

functionBody
    : '{' statement* '}'
    ;

classDeclaration
    : 'zenClass' simpleName classBody
    ;

classBody
    : '{' (constructorDeclaration | methodDeclaration | fieldDeclaration)* '}'
    ;

constructorDeclaration
    : 'zenConstructor' '(' (formalParameter (',' formalParameter)*)? ')' constructorBody
    ;

constructorBody
    : '{' statement* '}'
    ;

methodDeclaration
    : prefix='static'? 'function' simpleName '(' (formalParameter (',' formalParameter)*)? ')' ('as' returnType)? methodBody
    ;

methodBody
    : '{' statement* '}'
    ;

fieldDeclaration
    : prefix=('var' | 'val' | 'static') simpleName ('as' typeLiteral)? ('=' initializer=expression)? ';'
    ;

variableDeclaration
    : prefix=('var' | 'val' | 'static' | 'global') simpleName ('as' typeLiteral)? ('=' initializer=expression)? ';'
    ;

statement
    : variableDeclaration
    | blockStatement
    | returnStatement
    | breakStatement
    | continueStatement
    | ifStatement
    | foreachStatement
    | whileStatement
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
    : 'if' expression thenPart=statement ('else' elsePart=statement)?
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

expression
    : 'this'          #thisExpr
    | (TRUE | FALSE)  #boolLiteral
    | INT_LITERAL     #intLiteral
    | LONG_LITERAL    #longLiteral
    | FLOAT_LITERAL   #floatLiteral
    | DOUBLE_LITERAL  #doubleLiteral
    | STRING_LITERAL  #stringLiteral
    | NULL            #nullLiteral
    | simpleName  #simpleNameExpr
    | 'function' '(' (formalParameter (',' formalParameter)*)? ')' ('as' returnType)? functionBody  #functionExpr
    | '<' content '>'  #bracketHandlerExpr
    | '[' (expression (',' expression)*)? ','? ']'  #arrayLiteral
    | '{' (mapEntry (',' mapEntry)*)? ','? '}'      #mapLiteral
    | '(' expression ')'                            #parensExpr
    | left=expression op='instanceof' right=expression       #instanceOfExpr
    | expression 'as' typeLiteral                            #typeCastExpr
    | callee=expression '(' (argument (',' argument)*)? ','? ')'  #callExpr
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

content
    : (~'>')*
    ;

argument
    : expression
    ;

mapEntry
    : key=expression ':' value=expression
    ;

typeLiteral
    : qualifiedName                                                    #referenceType
    | 'function' '(' (typeLiteral (',' typeLiteral)*)? ')' returnType  #functionType
    | '[' typeLiteral ']'                                              #listType
    | typeLiteral '['']'                                               #arrayType
    | value=typeLiteral '[' key=typeLiteral ']' ('$' 'orderly')?       #mapType
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
