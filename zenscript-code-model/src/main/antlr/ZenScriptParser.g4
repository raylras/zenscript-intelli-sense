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
    : 'import' qualifiedName ('as' alias=simpleName)? ';'
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
    : prefix='static'? 'function' simpleName '(' (parameters+=formalParameter (',' parameters+=formalParameter)*)? ')' ('as' returnType=typeLiteral)? functionBody
    ;

expandFunctionDeclaration
    : '$expand' receiver=typeLiteral '$' simpleName '(' (parameters+=formalParameter (',' parameters+=formalParameter)*)? ')' ('as' returnType=typeLiteral)? functionBody
    ;

formalParameter
    : simpleName ('as' typeLiteral)? ('=' defaultValue=expression)?
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
    : 'zenConstructor' '(' (parameters+=formalParameter (',' parameters+=formalParameter)*)? ')' constructorBody
    ;

constructorBody
    : '{' statement* '}'
    ;

methodDeclaration
    : prefix='static'? 'function' simpleName '(' (parameters+=formalParameter (',' parameters+=formalParameter)*)? ')' ('as' returnType=typeLiteral)? methodBody
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
    : 'for' variables+=foreachVariable (',' variables+=foreachVariable)* 'in' iterable=expression foreachBody
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
    | simpleName      #referenceExpr
    | 'function' '(' (parameters+=formalParameter (',' parameters+=formalParameter)*)? ')' ('as' returnType=typeLiteral)? functionBody  #functionExpr
    | '<' content '>'  #bracketHandlerExpr
    | '[' (elements+=expression (',' elements+=expression)*)? ','? ']'  #arrayLiteral
    | '{' (entries+=mapEntry (',' entries+=mapEntry)*)? ','? '}'        #mapLiteral
    | '(' expression ')'                                #parensExpr
    | left=expression op='instanceof' right=expression  #instanceOfExpr
    | expression 'as' typeLiteral                       #typeCastExpr
    | receiver=expression '(' (arguments+=expression (',' arguments+=expression)*)? ','? ')'  #callExpr
    | receiver=expression '[' index=expression ']'              #memberIndexExpr
    | receiver=expression op='.' (simpleName | STRING_LITERAL)  #memberAccessExpr
    | from=expression op=('..' | 'to') to=expression            #intRangeExpr
    | <assoc=right> op=('!' | '-') expression                   #unaryExpr
    | left=expression op=('*' | '/' | '%') right=expression     #binaryExpr
    | left=expression op=('+' | '-' | '~') right=expression     #binaryExpr
    | left=expression op=('==' | '!=' | '<' | '<=' | '>' | '>=') right=expression  #binaryExpr
    | left=expression op=('|' | '^' | '&' | 'in' | 'has') right=expression   #binaryExpr
    | left=expression op=('||' | '&&') right=expression                      #binaryExpr
    | condition=expression '?' truePart=expression ':' falsePart=expression  #ternaryExpr
    | <assoc=right> left=expression op=('=' | '+=' | '-=' | '~=' | '*=' | '/=' | '%=' | '|=' | '&=' | '^=') right=expression  #assignmentExpr
    ;

content
    : (~'>')*
    ;

mapEntry
    : key=expression ':' value=expression
    ;

typeLiteral
    : qualifiedName                                                    #referenceType
    | 'function' '(' (parameterTypes+=typeLiteral (',' parameterTypes+=typeLiteral)*)? ')' returnType=typeLiteral  #functionType
    | '[' typeLiteral ']'                                              #listType
    | typeLiteral '['']'                                               #arrayType
    | valueType=typeLiteral '[' keyType=typeLiteral ']' ('$' 'orderly')?       #mapType
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
