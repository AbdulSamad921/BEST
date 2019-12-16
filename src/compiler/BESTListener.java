package compiler;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BESTParser}.
 */
public interface BESTListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BESTParser#start}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterStart(BESTParser.StartContext ctx);

	/**
	 * Exit a parse tree produced by {@link BESTParser#start}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitStart(BESTParser.StartContext ctx);

	/**
	 * Enter a parse tree produced by {@link BESTParser#program}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterProgram(BESTParser.ProgramContext ctx);

	/**
	 * Exit a parse tree produced by {@link BESTParser#program}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitProgram(BESTParser.ProgramContext ctx);

	/**
	 * Enter a parse tree produced by {@link BESTParser#program_block}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterProgram_block(BESTParser.Program_blockContext ctx);

	/**
	 * Exit a parse tree produced by {@link BESTParser#program_block}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitProgram_block(BESTParser.Program_blockContext ctx);

	/**
	 * Enter a parse tree produced by {@link BESTParser#declaration}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterDeclaration(BESTParser.DeclarationContext ctx);

	/**
	 * Exit a parse tree produced by {@link BESTParser#declaration}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitDeclaration(BESTParser.DeclarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link BESTParser#assignment}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterAssignment(BESTParser.AssignmentContext ctx);

	/**
	 * Exit a parse tree produced by {@link BESTParser#assignment}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitAssignment(BESTParser.AssignmentContext ctx);

	/**
	 * Enter a parse tree produced by {@link BESTParser#whenever}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterWhenever(BESTParser.WheneverContext ctx);

	/**
	 * Exit a parse tree produced by {@link BESTParser#whenever}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitWhenever(BESTParser.WheneverContext ctx);

	/**
	 * Enter a parse tree produced by {@link BESTParser#loop}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterLoop(BESTParser.LoopContext ctx);

	/**
	 * Exit a parse tree produced by {@link BESTParser#loop}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitLoop(BESTParser.LoopContext ctx);

	/**
	 * Enter a parse tree produced by {@link BESTParser#function}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterFunction(BESTParser.FunctionContext ctx);

	/**
	 * Exit a parse tree produced by {@link BESTParser#function}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitFunction(BESTParser.FunctionContext ctx);

	/**
	 * Enter a parse tree produced by {@link BESTParser#call}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterCall(BESTParser.CallContext ctx);

	/**
	 * Exit a parse tree produced by {@link BESTParser#call}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitCall(BESTParser.CallContext ctx);

	/**
	 * Enter a parse tree produced by {@link BESTParser#function_parameters}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterFunction_parameters(BESTParser.Function_parametersContext ctx);

	/**
	 * Exit a parse tree produced by {@link BESTParser#function_parameters}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitFunction_parameters(BESTParser.Function_parametersContext ctx);

	/**
	 * Enter a parse tree produced by {@link BESTParser#print}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterPrint(BESTParser.PrintContext ctx);

	/**
	 * Exit a parse tree produced by {@link BESTParser#print}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitPrint(BESTParser.PrintContext ctx);

	/**
	 * Enter a parse tree produced by {@link BESTParser#condition}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterCondition(BESTParser.ConditionContext ctx);

	/**
	 * Exit a parse tree produced by {@link BESTParser#condition}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitCondition(BESTParser.ConditionContext ctx);

	/**
	 * Enter a parse tree produced by {@link BESTParser#boolean_expression}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterBoolean_expression(BESTParser.Boolean_expressionContext ctx);

	/**
	 * Exit a parse tree produced by {@link BESTParser#boolean_expression}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitBoolean_expression(BESTParser.Boolean_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link BESTParser#expression}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterExpression(BESTParser.ExpressionContext ctx);

	/**
	 * Exit a parse tree produced by {@link BESTParser#expression}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitExpression(BESTParser.ExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link BESTParser#expression3}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterExpression3(BESTParser.Expression3Context ctx);

	/**
	 * Exit a parse tree produced by {@link BESTParser#expression3}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitExpression3(BESTParser.Expression3Context ctx);

	/**
	 * Enter a parse tree produced by {@link BESTParser#expression2}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterExpression2(BESTParser.Expression2Context ctx);

	/**
	 * Exit a parse tree produced by {@link BESTParser#expression2}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitExpression2(BESTParser.Expression2Context ctx);

	/**
	 * Enter a parse tree produced by {@link BESTParser#expression4}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterExpression4(BESTParser.Expression4Context ctx);

	/**
	 * Exit a parse tree produced by {@link BESTParser#expression4}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitExpression4(BESTParser.Expression4Context ctx);

	/**
	 * Enter a parse tree produced by {@link BESTParser#expression5}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterExpression5(BESTParser.Expression5Context ctx);

	/**
	 * Exit a parse tree produced by {@link BESTParser#expression5}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitExpression5(BESTParser.Expression5Context ctx);
}