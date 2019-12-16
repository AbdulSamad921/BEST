package compiler;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link BESTParser}.
 *
 * @param <T>
 *            The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface BESTVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link BESTParser#start}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitStart(BESTParser.StartContext ctx);

	/**
	 * Visit a parse tree produced by {@link BESTParser#program}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitProgram(BESTParser.ProgramContext ctx);

	/**
	 * Visit a parse tree produced by {@link BESTParser#program_block}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitProgram_block(BESTParser.Program_blockContext ctx);

	/**
	 * Visit a parse tree produced by {@link BESTParser#declaration}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(BESTParser.DeclarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link BESTParser#assignment}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(BESTParser.AssignmentContext ctx);

	/**
	 * Visit a parse tree produced by {@link BESTParser#whenever}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitWhenever(BESTParser.WheneverContext ctx);

	/**
	 * Visit a parse tree produced by {@link BESTParser#loop}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitLoop(BESTParser.LoopContext ctx);

	/**
	 * Visit a parse tree produced by {@link BESTParser#function}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitFunction(BESTParser.FunctionContext ctx);

	/**
	 * Visit a parse tree produced by {@link BESTParser#call}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitCall(BESTParser.CallContext ctx);

	/**
	 * Visit a parse tree produced by {@link BESTParser#function_parameters}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitFunction_parameters(BESTParser.Function_parametersContext ctx);

	/**
	 * Visit a parse tree produced by {@link BESTParser#print}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitPrint(BESTParser.PrintContext ctx);

	/**
	 * Visit a parse tree produced by {@link BESTParser#condition}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitCondition(BESTParser.ConditionContext ctx);

	/**
	 * Visit a parse tree produced by {@link BESTParser#boolean_expression}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitBoolean_expression(BESTParser.Boolean_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link BESTParser#expression}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitExpression(BESTParser.ExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link BESTParser#expression3}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitExpression3(BESTParser.Expression3Context ctx);

	/**
	 * Visit a parse tree produced by {@link BESTParser#expression2}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitExpression2(BESTParser.Expression2Context ctx);

	/**
	 * Visit a parse tree produced by {@link BESTParser#expression4}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitExpression4(BESTParser.Expression4Context ctx);

	/**
	 * Visit a parse tree produced by {@link BESTParser#expression5}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitExpression5(BESTParser.Expression5Context ctx);
}