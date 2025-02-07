// Generated from QuantumParser.g4 by ANTLR 4.5
package dev.ultreon.quantum.scripting.qfunc;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link QuantumParser}.
 */
public interface QuantumParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link QuantumParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(QuantumParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(QuantumParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(QuantumParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(QuantumParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(QuantumParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(QuantumParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#lineComment}.
	 * @param ctx the parse tree
	 */
	void enterLineComment(QuantumParser.LineCommentContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#lineComment}.
	 * @param ctx the parse tree
	 */
	void exitLineComment(QuantumParser.LineCommentContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(QuantumParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(QuantumParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#persistStatement}.
	 * @param ctx the parse tree
	 */
	void enterPersistStatement(QuantumParser.PersistStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#persistStatement}.
	 * @param ctx the parse tree
	 */
	void exitPersistStatement(QuantumParser.PersistStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#directiveType}.
	 * @param ctx the parse tree
	 */
	void enterDirectiveType(QuantumParser.DirectiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#directiveType}.
	 * @param ctx the parse tree
	 */
	void exitDirectiveType(QuantumParser.DirectiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#inputStatement}.
	 * @param ctx the parse tree
	 */
	void enterInputStatement(QuantumParser.InputStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#inputStatement}.
	 * @param ctx the parse tree
	 */
	void exitInputStatement(QuantumParser.InputStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(QuantumParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(QuantumParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#isCond}.
	 * @param ctx the parse tree
	 */
	void enterIsCond(QuantumParser.IsCondContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#isCond}.
	 * @param ctx the parse tree
	 */
	void exitIsCond(QuantumParser.IsCondContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(QuantumParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(QuantumParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#globalName}.
	 * @param ctx the parse tree
	 */
	void enterGlobalName(QuantumParser.GlobalNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#globalName}.
	 * @param ctx the parse tree
	 */
	void exitGlobalName(QuantumParser.GlobalNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#globalExpr}.
	 * @param ctx the parse tree
	 */
	void enterGlobalExpr(QuantumParser.GlobalExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#globalExpr}.
	 * @param ctx the parse tree
	 */
	void exitGlobalExpr(QuantumParser.GlobalExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#paramName}.
	 * @param ctx the parse tree
	 */
	void enterParamName(QuantumParser.ParamNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#paramName}.
	 * @param ctx the parse tree
	 */
	void exitParamName(QuantumParser.ParamNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#parameterExpr}.
	 * @param ctx the parse tree
	 */
	void enterParameterExpr(QuantumParser.ParameterExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#parameterExpr}.
	 * @param ctx the parse tree
	 */
	void exitParameterExpr(QuantumParser.ParameterExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(QuantumParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(QuantumParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#group}.
	 * @param ctx the parse tree
	 */
	void enterGroup(QuantumParser.GroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#group}.
	 * @param ctx the parse tree
	 */
	void exitGroup(QuantumParser.GroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(QuantumParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(QuantumParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(QuantumParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(QuantumParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#negationExpr}.
	 * @param ctx the parse tree
	 */
	void enterNegationExpr(QuantumParser.NegationExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#negationExpr}.
	 * @param ctx the parse tree
	 */
	void exitNegationExpr(QuantumParser.NegationExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(QuantumParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(QuantumParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpr(QuantumParser.RelationalExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpr(QuantumParser.RelationalExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#bitwiseAndExpr}.
	 * @param ctx the parse tree
	 */
	void enterBitwiseAndExpr(QuantumParser.BitwiseAndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#bitwiseAndExpr}.
	 * @param ctx the parse tree
	 */
	void exitBitwiseAndExpr(QuantumParser.BitwiseAndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#bitwiseOrExpr}.
	 * @param ctx the parse tree
	 */
	void enterBitwiseOrExpr(QuantumParser.BitwiseOrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#bitwiseOrExpr}.
	 * @param ctx the parse tree
	 */
	void exitBitwiseOrExpr(QuantumParser.BitwiseOrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#bitwiseXorExpr}.
	 * @param ctx the parse tree
	 */
	void enterBitwiseXorExpr(QuantumParser.BitwiseXorExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#bitwiseXorExpr}.
	 * @param ctx the parse tree
	 */
	void exitBitwiseXorExpr(QuantumParser.BitwiseXorExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#bitwiseNotExpr}.
	 * @param ctx the parse tree
	 */
	void enterBitwiseNotExpr(QuantumParser.BitwiseNotExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#bitwiseNotExpr}.
	 * @param ctx the parse tree
	 */
	void exitBitwiseNotExpr(QuantumParser.BitwiseNotExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#shiftExpr}.
	 * @param ctx the parse tree
	 */
	void enterShiftExpr(QuantumParser.ShiftExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#shiftExpr}.
	 * @param ctx the parse tree
	 */
	void exitShiftExpr(QuantumParser.ShiftExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#multExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultExpr(QuantumParser.MultExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#multExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultExpr(QuantumParser.MultExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#addExpr}.
	 * @param ctx the parse tree
	 */
	void enterAddExpr(QuantumParser.AddExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#addExpr}.
	 * @param ctx the parse tree
	 */
	void exitAddExpr(QuantumParser.AddExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(QuantumParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(QuantumParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(QuantumParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(QuantumParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#globalRef}.
	 * @param ctx the parse tree
	 */
	void enterGlobalRef(QuantumParser.GlobalRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#globalRef}.
	 * @param ctx the parse tree
	 */
	void exitGlobalRef(QuantumParser.GlobalRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#namedAtom}.
	 * @param ctx the parse tree
	 */
	void enterNamedAtom(QuantumParser.NamedAtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#namedAtom}.
	 * @param ctx the parse tree
	 */
	void exitNamedAtom(QuantumParser.NamedAtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#variableName}.
	 * @param ctx the parse tree
	 */
	void enterVariableName(QuantumParser.VariableNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#variableName}.
	 * @param ctx the parse tree
	 */
	void exitVariableName(QuantumParser.VariableNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#id}.
	 * @param ctx the parse tree
	 */
	void enterId(QuantumParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#id}.
	 * @param ctx the parse tree
	 */
	void exitId(QuantumParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#funcName}.
	 * @param ctx the parse tree
	 */
	void enterFuncName(QuantumParser.FuncNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#funcName}.
	 * @param ctx the parse tree
	 */
	void exitFuncName(QuantumParser.FuncNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(QuantumParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(QuantumParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#member}.
	 * @param ctx the parse tree
	 */
	void enterMember(QuantumParser.MemberContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#member}.
	 * @param ctx the parse tree
	 */
	void exitMember(QuantumParser.MemberContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(QuantumParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(QuantumParser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#argumentExpr}.
	 * @param ctx the parse tree
	 */
	void enterArgumentExpr(QuantumParser.ArgumentExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#argumentExpr}.
	 * @param ctx the parse tree
	 */
	void exitArgumentExpr(QuantumParser.ArgumentExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#argumentName}.
	 * @param ctx the parse tree
	 */
	void enterArgumentName(QuantumParser.ArgumentNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#argumentName}.
	 * @param ctx the parse tree
	 */
	void exitArgumentName(QuantumParser.ArgumentNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#namespace}.
	 * @param ctx the parse tree
	 */
	void enterNamespace(QuantumParser.NamespaceContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#namespace}.
	 * @param ctx the parse tree
	 */
	void exitNamespace(QuantumParser.NamespaceContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#path}.
	 * @param ctx the parse tree
	 */
	void enterPath(QuantumParser.PathContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#path}.
	 * @param ctx the parse tree
	 */
	void exitPath(QuantumParser.PathContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#stopStatement}.
	 * @param ctx the parse tree
	 */
	void enterStopStatement(QuantumParser.StopStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#stopStatement}.
	 * @param ctx the parse tree
	 */
	void exitStopStatement(QuantumParser.StopStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(QuantumParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(QuantumParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(QuantumParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(QuantumParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(QuantumParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(QuantumParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void enterLoopStatement(QuantumParser.LoopStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void exitLoopStatement(QuantumParser.LoopStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(QuantumParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(QuantumParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(QuantumParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(QuantumParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuantumParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(QuantumParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuantumParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(QuantumParser.ContinueStatementContext ctx);
}