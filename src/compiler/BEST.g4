grammar BEST;
start : program ;
program : program_block ';' program | program_block ';' ;
program_block : declaration | assignment | whenever | loop | function | call | print | ;
declaration : 'declare' VARIABLE ;
assignment : VARIABLE 'equals' expression ;
whenever : 'whenever' '(' condition ')' 'then' '{' program '}' |
          'whenever' '(' condition ')' 'then' '{' program '}' 'other' '{' program '}' ;
loop : 'loop' VARIABLE '::' expression '--' condition '--' expression '{' program '}' ;
function : 'method' VARIABLE '(' function_parameters ')' '{' program'}' ;
call : 'calling function' VARIABLE '('function_parameters ')'  ;
function_parameters : (VARIABLE)(','VARIABLE)* | ;
print : 'print ' expression | 'print ' '"'VARIABLE'"' | 'print ' '"'NUMBER'"' ;
condition : expression '=' expression | expression '<>' expression |
               expression 'lt' expression | expression 'gt' expression |
               expression 'lte' expression | expression 'gte' expression | boolean_expression ;
boolean_expression : 'True' | 'False' ;
expression : expression2 expression3 ;
expression3 : '+' expression2 expression3 | '-' expression2 expression3 | ;
expression2 : expression5  expression4 ;
expression4 : '*' expression5 expression4 | '/' expression5 expression4 |'%' expression5 expression4 | ;
expression5 : '(' expression ')' | NUMBER | VARIABLE | boolean_expression;
NUMBER :  [0-9]+;
VARIABLE : [a-z|A-Z]+ ;
WS : [ \t\r\n]+ -> skip ;








