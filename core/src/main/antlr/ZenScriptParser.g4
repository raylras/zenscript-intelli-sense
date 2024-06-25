parser grammar ZenScriptParser;

options { tokenVocab = ZenScriptLexer; }

compilationUnit
    : toplevelEntity* EOF
    ;

toplevelEntity
    : importDeclaration
    | variableDeclaration
    | functionDeclaration
    | expandFunctionDeclaration
    | classDeclaration
    | statement
    ;

importDeclaration
    : 'import' identifier ('as' alias=simpleIdentifier)? ';'?
    ;

variableDeclaration
    : prefix=('var' | 'val' | 'static' | 'global') simpleIdentifier ('as' type)? ('=' expression)? ';'?
    ;

functionDeclaration
    : prefix='static'? 'function' simpleIdentifier functionValueParameters ('as' type)? block
    ;

expandFunctionDeclaration
    : '$expand' receiverType '$' simpleIdentifier functionValueParameters ('as' type)? block
    ;

functionValueParameters
    : '(' (functionValueParameter (',' functionValueParameter)* ','?)? ')'
    ;

functionValueParameter
    : simpleIdentifier ('as' type)? ('=' expression)?
    ;

classDeclaration
    : 'zenClass' simpleIdentifier classBody
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
    : 'zenConstructor' functionValueParameters block
    ;

block
    : '{' statement* '}'
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
    : 'return' expression? ';'?
    ;

breakStatement
    : 'break' ';'?
    ;

continueStatement
    : 'continue' ';'?
    ;

ifStatement
    : 'if' expression controlStructureBody ('else' controlStructureBody)?
    ;

whileStatement
    : 'while' expression controlStructureBody
    ;

controlStructureBody
    : '{' statement* '}'
    | statement
    ;

foreachStatement
    : 'for' simpleIdentifier (',' simpleIdentifier)* 'in' expression foreachBody
    ;

foreachBody
    : '{' statement* '}'
    ;

expressionStatement
    : expression ';'?
    ;

expression
    : 'this'         #thisExpression
    | 'null'         #nullLiteral
    | BoolLiteral    #boolLiteral
    | IntLiteral     #intLiteral
    | LongLiteral    #longLiteral
    | FloatLiteral   #floatLiteral
    | DoubleLiteral  #doubleLiteral
    | StringLiteral  #stringLiteral
    | simpleIdentifier #referenceExpression
    | 'function' functionValueParameters ('as' type)? block  #functionExpression
    | '<' (~'>')* '>'  #bracketHandlerExpression
    | '[' (expression (',' expression)*)? ','? ']'  #arrayLiteral
    | '{' (mapEntry (',' mapEntry)*)? ','? '}'        #mapLiteral
    | '(' expression ')'            #parenthesizedExpression
    | expression 'instanceof' type  #instanceOfExpression
    | expression 'as' type          #typeCastExpression
    | expression callSuffix         #callExpression
    | expression indexingSuffix     #indexingExpression
    | expression op='.' (simpleIdentifier | StringLiteral)  #memberAccessExpression
    | expression op=('..' | 'to') expression  #intRangeExpression
    | <assoc=right> op=('!' | '-') expression  #unaryExpression
    | expression op=('*' | '/' | '%') expression                        #binaryExpression
    | expression op=('+' | '-' | '~') expression                        #binaryExpression
    | expression op=('==' | '!=' | '<' | '<=' | '>' | '>=') expression  #binaryExpression
    | expression op=('|' | '^' | '&' | 'in' | 'has') expression         #binaryExpression
    | expression op=('||' | '&&') expression                            #binaryExpression
    | expression '?' expression ':' expression   #ternaryExpression
    | <assoc=right> expression op=('=' | '+=' | '-=' | '~=' | '*=' | '/=' | '%=' | '|=' | '&=' | '^=') expression  #assignmentExpression
    ;

mapEntry
    : key=expression ':' value=expression
    ;

valueArguments
    : '(' (valueArgument (',' valueArgument)* ','?)? ')'
    ;

valueArgument
    : expression
    ;

callSuffix
    : valueArguments
    ;

indexingSuffix
    : '[' expression ']'
    ;

receiverType
    : type
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
    ;
