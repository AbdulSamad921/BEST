package compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * The IntermediateCodeGenerator generates the intermediate code from the parse
 * tree. It produces the corresponding byte code in the form of instructions
 * have an opcode and one operand.
 *
 * @author Anusha, Salil, Abdul, Mounika
 *
 */
public class IntermediateCodeGenerator extends BESTBaseVisitor<Integer> {
	private static StringBuffer intermediateCode = new StringBuffer();
	private int loopCounter = 0;
	private int ifCounter = 0;
	Stack<String> functionStack = new Stack<>();

	public static void main(String args[]) throws IOException {
		String filename = "Loop.BEST";
		if (args.length != 0) {
			filename = args[0];
		}
		try {
			ANTLRInputStream input = new ANTLRInputStream(
					IntermediateCodeGenerator.class.getResourceAsStream(filename));
			BESTLexer bl = new BESTLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(bl);
			BESTParser bestParser = new BESTParser(tokens);
			ParseTree parseTree = bestParser.start();
			IntermediateCodeGenerator intermediateCodeGenerator = new IntermediateCodeGenerator();
			intermediateCodeGenerator.visit(parseTree);
			filename = filename.substring(0, filename.lastIndexOf('.'));
			File file = new File("data/acm/" + filename + ".ICG");
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				writer.write(intermediateCode.toString());
			}
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The visitStart function is the start symbol for our grammar. It corresponds
	 * to the rule "start : program ;"
	 */
	@Override
	public Integer visitStart(BESTParser.StartContext ctx) {
		return super.visitStart(ctx);
	}

	/**
	 * The visitProgram function corresponds to the rule "program : program_block
	 * ';' program | program_block ';' "
	 */
	@Override
	public Integer visitProgram(BESTParser.ProgramContext ctx) {
		return super.visitProgram(ctx);
	}

	/**
	 * The visitProgram_block function corresponds to the rule "program_block :
	 * declaration | assignment | whenever | loop | function | call | print | ; "
	 */
	@Override
	public Integer visitProgram_block(BESTParser.Program_blockContext ctx) {
		return super.visitProgram_block(ctx);
	}

	/**
	 * The visitDeclaration function corresponds to the rule "declaration :
	 * 'declare' VARIABLE ; "
	 */
	@Override
	public Integer visitDeclaration(BESTParser.DeclarationContext ctx) {
		intermediateCode.append("STORE ").append(ctx.VARIABLE().getText()).append("\n");
		return super.visitDeclaration(ctx);
	}

	/**
	 * The visitAssignment function corresponds to the rule "assignment : VARIABLE
	 * 'equals' expression ; "
	 */
	@Override
	public Integer visitAssignment(BESTParser.AssignmentContext ctx) {
		visitExpression(ctx.expression());
		intermediateCode.append("MOV ").append(ctx.VARIABLE().getText()).append("\n");
		return null;
	}

	/**
	 * The visitWhenever function corresponds to the rule "whenever : 'whenever' '('
	 * condition ')' 'then' '{' program '}' | 'whenever' '(' condition ')' 'then'
	 * '{' program '}' 'other' '{' program '}' ;"
	 */
	@Override
	public Integer visitWhenever(BESTParser.WheneverContext ctx) {
		int localIf = ifCounter;
		intermediateCode.append("WHENEVER ").append(ifCounter).append("\n");
		visitCondition(ctx.condition());
		intermediateCode.append("WHENEVER_START_").append(ifCounter).append("\n");
		int savedIf = ifCounter;
		ifCounter++;
		visitProgram(ctx.program(0));
		intermediateCode.append("THEN_END_").append(savedIf).append("\n");
		if (ctx.getChild(8) != null) {
			intermediateCode.append(ctx.getChild(8).getText().toUpperCase()).append("_").append(localIf).append("\n");
			visitProgram(ctx.program(1));
		}
		ifCounter--;
		intermediateCode.append("WHENEVER_END_").append(ifCounter).append("\n");
		return null;
	}

	/**
	 * The visitLoop function corresponds to the rule "loop : 'loop' VARIABLE '::'
	 * expression '--' condition '--' expression '{' program '}' ;"
	 */
	@Override
	public Integer visitLoop(BESTParser.LoopContext ctx) {
		visitExpression(ctx.expression(0));
		intermediateCode.append("MOV ").append(ctx.VARIABLE().getText()).append("\n");
		intermediateCode.append("LOOP ").append(loopCounter).append("\n");
		visitCondition(ctx.condition());
		intermediateCode.append("LOOP_START_").append(loopCounter).append("\n");
		loopCounter++;
		visitProgram(ctx.program());
		visitExpression(ctx.expression(1));
		intermediateCode.append("MOV ").append(ctx.VARIABLE().getText()).append("\n");
		loopCounter--;
		intermediateCode.append("LOOP_END_").append(loopCounter).append("\n");
		return null;
	}

	/**
	 * The visitPrint function corresponds to the rule "print : 'print ' expression
	 * | 'print ' '"'VARIABLE'"' | 'print ' '"'NUMBER'"' ; "
	 */
	@Override
	public Integer visitPrint(BESTParser.PrintContext ctx) {
		if (ctx.getChild(1).getText().equals("\"")) {
			intermediateCode.append("PRINT ").append(ctx.getChild(1).getText()).append(ctx.getChild(2).getText())
					.append(ctx.getChild(3).getText()).append("\n");
		} else {
			intermediateCode.append("PRINT START_PRINT\n");
			visitExpression(ctx.expression());
			intermediateCode.append("PRINT STOP_PRINT\n");
		}
		return null;
	}

	/**
	 * The visitCondition function corresponds to the rule "condition : expression
	 * '=' expression | expression '<>' expression | expression 'lt' expression |
	 * expression 'gt' expression | expression 'lte' expression | expression 'gte'
	 * expression | boolean_expression ; "
	 */
	@Override
	public Integer visitCondition(BESTParser.ConditionContext ctx) {
		if (ctx.boolean_expression() != null) {
			visitBoolean_expression(ctx.boolean_expression());
		} else {
			visitExpression(ctx.expression(0));
			visitExpression(ctx.expression(1));
			String condition = (ctx.getChild(1).getText());
			switch (condition) {
			case "=":
				intermediateCode.append("EQUAL \n");
				break;
			case "<>":
				intermediateCode.append("NOTEQ \n");
				break;
			case "lt":
				intermediateCode.append("LESS \n");
				break;
			case "lte":
				intermediateCode.append("LESSEQ \n");
				break;
			case "gt":
				intermediateCode.append("GREATER \n");
				break;
			case "gte":
				intermediateCode.append("GREATEREQ \n");
				break;
			}
		}
		return null;
	}

	/**
	 * The visitBoolean_expression function corresponds to the rule
	 * "boolean_expression : 'True' | 'False' ;"
	 */
	@Override
	public Integer visitBoolean_expression(BESTParser.Boolean_expressionContext ctx) {
		intermediateCode.append("PUSH ").append(ctx.getChild(0).getText() + "\n");
		return super.visitBoolean_expression(ctx);
	}

	/**
	 * The visitExpression function corresponds to the rule "expression :
	 * expression2 expression3 ; "
	 */
	@Override
	public Integer visitExpression(BESTParser.ExpressionContext ctx) {
		visitExpression2(ctx.expression2());
		visitExpression3(ctx.expression3());
		return null;
	}

	/**
	 * The visitExpression3 function corresponds to the rule "expression3 : '+'
	 * expression2 expression3 | '-' expression2 expression3 | ;"
	 */
	@Override
	public Integer visitExpression3(BESTParser.Expression3Context ctx) {
		if (ctx.getChild(0) != null) {
			if (ctx.getChild(0).getText().equals("+")) {
				visitExpression2(ctx.expression2());
				visitExpression3(ctx.expression3());
				intermediateCode.append("ADD \n");
			} else if (ctx.getChild(0).getText().equals("-")) {
				visitExpression2(ctx.expression2());
				visitExpression3(ctx.expression3());
				intermediateCode.append("SUB \n");
			}
		}
		return null;
	}

	/**
	 * The visitExpression2 function corresponds to the rule "expression2 :
	 * expression5 expression4 ; "
	 */
	@Override
	public Integer visitExpression2(BESTParser.Expression2Context ctx) {
		visitExpression5(ctx.expression5());
		visitExpression4(ctx.expression4());
		return null;
	}

	/**
	 * The visitExpression4 function corresponds to the rule "expression4 : '*'
	 * expression5 expression4 | '/' expression5 expression4 |'%' expression5
	 * expression4 | ; "
	 */
	@Override
	public Integer visitExpression4(BESTParser.Expression4Context ctx) {
		if (ctx.getChild(0) != null) {
			if (ctx.getChild(0).getText().equals("*")) {
				visitExpression5(ctx.expression5());
				visitExpression4(ctx.expression4());
				intermediateCode.append("MUL \n");
			} else if (ctx.getChild(0).getText().equals("/")) {
				visitExpression5(ctx.expression5());
				visitExpression4(ctx.expression4());
				intermediateCode.append("DIV \n");
			} else if (ctx.getChild(0).getText().equals("%")) {
				visitExpression5(ctx.expression5());
				visitExpression4(ctx.expression4());
				intermediateCode.append("MOD \n");
			}
		}
		return null;
	}

	/**
	 * The visitExpression5 function corresponds to the rule "expression5 : '('
	 * expression ')' | NUMBER | VARIABLE | boolean_expression; "
	 */
	@Override
	public Integer visitExpression5(BESTParser.Expression5Context ctx) {
		if (ctx.NUMBER() != null) {
			intermediateCode.append("PUSH ").append(ctx.NUMBER()).append("\n");
		} else if (ctx.VARIABLE() != null) {
			intermediateCode.append("PUSH ").append(ctx.VARIABLE()).append("\n");
		} else {
		}
		return super.visitExpression5(ctx);
	}

	/**
	 * The visitFunction function corresponds to the rule "function : 'method'
	 * VARIABLE '(' function_parameters ')' '{' program'}' ; "
	 */
	@Override
	public Integer visitFunction(BESTParser.FunctionContext ctx) {
		intermediateCode.append("METHOD ").append(ctx.VARIABLE().getText()).append("\n");
		visitFunction_parameters(ctx.function_parameters());
		intermediateCode.append("METHOD_START_").append(ctx.VARIABLE().getText()).append("\n");
		visitProgram(ctx.program());
		intermediateCode.append("METHOD_END_").append(ctx.VARIABLE().getText()).append("\n");
		return null;
	}

	/**
	 * The visitCall function corresponds to the rule "call : 'calling function'
	 * VARIABLE '('function_parameters ')' ; "
	 */
	@Override
	public Integer visitCall(BESTParser.CallContext ctx) {
		intermediateCode.append("CALL ").append(ctx.VARIABLE().getText()).append("\n");
		visitFunction_parameters(ctx.function_parameters());
		intermediateCode.append("CALL_END_").append(ctx.VARIABLE().getText()).append("\n");
		return null;
	}

	/**
	 * The visitFunction_parameters function corresponds to the rule
	 * "function_parameters : (VARIABLE)(','VARIABLE)* | ; "
	 */
	@Override
	public Integer visitFunction_parameters(BESTParser.Function_parametersContext ctx) {
		for (int i = 0; i < ctx.VARIABLE().size(); i++) {
			intermediateCode.append("MOV ").append(ctx.VARIABLE(i).getText()).append("\n");
		}
		return null;
	}

	/**
	 * The visit function Overrides: visit(...) in AbstractParseTreeVisitor
	 * Parameters:tree
	 */
	@Override
	public Integer visit(ParseTree tree) {
		return super.visit(tree);
	}

	/**
	 * The visitChildren Overrides: visitChildren(...) in AbstractParseTreeVisitor
	 * Parameters:node
	 */
	@Override
	public Integer visitChildren(RuleNode node) {
		return super.visitChildren(node);
	}

	/**
	 * The visitTerminal function Overrides: visitTerminal(...) in
	 * AbstractParseTreeVisitor Parameters:node
	 */
	@Override
	public Integer visitTerminal(TerminalNode node) {
		return super.visitTerminal(node);
	}

	/**
	 * The visitErrorNode function Overrides: visitErrorNode(...) in
	 * AbstractParseTreeVisitor Parameters:node "
	 */
	@Override
	public Integer visitErrorNode(ErrorNode node) {
		return super.visitErrorNode(node);
	}

	/**
	 * The defaultResult function Overrides: defaultResult() in
	 * AbstractParseTreeVisitor"
	 */
	@Override
	protected Integer defaultResult() {
		return super.defaultResult();
	}

	/**
	 * The aggregateResult Overrides: aggregateResult(...) in
	 * AbstractParseTreeVisitor Parameters:aggregate nextResult
	 */
	@Override
	protected Integer aggregateResult(Integer aggregate, Integer nextResult) {
		return super.aggregateResult(aggregate, nextResult);
	}

	/**
	 * The shouldVisitNextChild Overrides shouldVisitNextChild(...) in
	 * AbstractParseTreeVisitor Parameters:node currentResult
	 */
	@Override
	protected boolean shouldVisitNextChild(RuleNode node, Integer currentResult) {
		return super.shouldVisitNextChild(node, currentResult);
	}
}
