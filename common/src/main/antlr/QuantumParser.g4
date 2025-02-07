// Parsing
parser grammar QuantumParser;

options {
  tokenVocab = QuantumLexer;
}

file : (statement)*;

statement : (lineComment
           | persistStatement
           | inputStatement
           | assignment
           | functionCall
           | stopStatement
           | ifStatement
           | forStatement
           | whileStatement
           | loopStatement
           | returnStatement
           | breakStatement
           | continueStatement
           | expressionStatement
           );

expressionStatement : expression SEMICOLON;

lineComment : COMMENT;

blockStatement : LBRACE (statement)* RBRACE;

persistStatement : HASH PERSIST directiveType globalName (globalName)*;

directiveType : LESS_THAN IDENTIFIER GREATER_THAN;

inputStatement : HASH INPUT globalName (globalName)*;

condition : isCond | expression;

isCond : expression IS expression;

assignment : globalExpr ASSIGN expression SEMICOLON;

globalName : IDENTIFIER;

globalExpr : DOLLAR globalName;

paramName : IDENTIFIER;

parameterExpr : AT paramName;

expression : group;

group : LPAREN andExpr RPAREN | andExpr;

andExpr : orExpr (AND andExpr)?;

orExpr : negationExpr (OR orExpr)?;

negationExpr : NOT? equalityExpr | NOT LPAREN negationExpr RPAREN;

equalityExpr : relationalExpr (EQUAL equalityExpr)? | relationalExpr (NOT_EQUAL equalityExpr)?;

relationalExpr : bitwiseAndExpr (LESS_THAN relationalExpr)? | bitwiseAndExpr (GREATER_THAN relationalExpr)? | bitwiseAndExpr (LESS_THAN_EQUAL relationalExpr)? | bitwiseAndExpr (GREATER_THAN_EQUAL relationalExpr)?;

bitwiseAndExpr : bitwiseOrExpr (BITWISE_AND bitwiseAndExpr)?;

bitwiseOrExpr : bitwiseXorExpr (BITWISE_OR bitwiseOrExpr)?;

bitwiseXorExpr : bitwiseNotExpr (BITWISE_XOR bitwiseXorExpr)?;

bitwiseNotExpr : BITWISE_NOT? shiftExpr | BITWISE_NOT bitwiseNotExpr;

shiftExpr : multExpr ((SHIFT_LEFT | SHIFT_RIGHT) shiftExpr)*;

multExpr : addExpr ((STAR | SLASH | PERCENT) multExpr)*;

addExpr : primary ((PLUS | MINUS) addExpr)*;

primary : atom;

atom : namedAtom | STRING | NUMBER | FLOATING_POINT | BOOLEAN | id;

globalRef : PRESENT? globalExpr;

namedAtom : (globalRef | parameterExpr | functionCall | variableName) member*;

variableName : IDENTIFIER;

id : (LBRACKET namespace COLON path RBRACKET) | (LBRACKET HASH namespace COLON path RBRACKET);

funcName : IDENTIFIER;

functionCall : funcName argumentList;

member : COLON (functionCall | variableName);

argumentList : LPAREN (argumentExpr (COMMA argumentExpr)*)? RPAREN;

argumentExpr : argumentName COLON expression;

argumentName : IDENTIFIER;

namespace : IDENTIFIER;

path : IDENTIFIER (SLASH IDENTIFIER) (DOT IDENTIFIER)?;

stopStatement : STOP SEMICOLON;

ifStatement : IF condition statement (ELSE statement)?;

forStatement : FOR LPAREN assignment SEMICOLON condition SEMICOLON assignment RPAREN statement;

whileStatement : WHILE condition statement;

loopStatement : LOOP blockStatement;

returnStatement : RETURN expression SEMICOLON;

breakStatement : BREAK SEMICOLON;

continueStatement : CONTINUE SEMICOLON;
