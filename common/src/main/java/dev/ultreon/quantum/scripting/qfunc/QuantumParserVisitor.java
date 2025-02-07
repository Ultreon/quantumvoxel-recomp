// Generated from QuantumParser.g4 by ANTLR 4.5
package dev.ultreon.quantum.scripting.qfunc;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link QuantumParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface QuantumParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link QuantumParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(QuantumParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(QuantumParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(QuantumParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#lineComment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLineComment(QuantumParser.LineCommentContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(QuantumParser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#persistStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPersistStatement(QuantumParser.PersistStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#directiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDirectiveType(QuantumParser.DirectiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#inputStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInputStatement(QuantumParser.InputStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(QuantumParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#isCond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsCond(QuantumParser.IsCondContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(QuantumParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#globalName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalName(QuantumParser.GlobalNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#globalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalExpr(QuantumParser.GlobalExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#paramName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamName(QuantumParser.ParamNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#parameterExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterExpr(QuantumParser.ParameterExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(QuantumParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#group}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroup(QuantumParser.GroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#andExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(QuantumParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#orExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(QuantumParser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#negationExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegationExpr(QuantumParser.NegationExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#equalityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpr(QuantumParser.EqualityExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#relationalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpr(QuantumParser.RelationalExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#bitwiseAndExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitwiseAndExpr(QuantumParser.BitwiseAndExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#bitwiseOrExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitwiseOrExpr(QuantumParser.BitwiseOrExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#bitwiseXorExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitwiseXorExpr(QuantumParser.BitwiseXorExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#bitwiseNotExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitwiseNotExpr(QuantumParser.BitwiseNotExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#shiftExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftExpr(QuantumParser.ShiftExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#multExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultExpr(QuantumParser.MultExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#addExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExpr(QuantumParser.AddExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(QuantumParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(QuantumParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#globalRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalRef(QuantumParser.GlobalRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#namedAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedAtom(QuantumParser.NamedAtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#variableName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableName(QuantumParser.VariableNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(QuantumParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#funcName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncName(QuantumParser.FuncNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(QuantumParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#member}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMember(QuantumParser.MemberContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#argumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentList(QuantumParser.ArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#argumentExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentExpr(QuantumParser.ArgumentExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#argumentName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentName(QuantumParser.ArgumentNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#namespace}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamespace(QuantumParser.NamespaceContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#path}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPath(QuantumParser.PathContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#stopStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStopStatement(QuantumParser.StopStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(QuantumParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(QuantumParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(QuantumParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#loopStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStatement(QuantumParser.LoopStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(QuantumParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(QuantumParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuantumParser#continueStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStatement(QuantumParser.ContinueStatementContext ctx);
}