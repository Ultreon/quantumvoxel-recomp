// Generated from QuantumParser.g4 by ANTLR 4.5
package dev.ultreon.quantum.scripting.qfunc;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class QuantumParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WHITESPACE=1, DIRECTIVE=2, STRING=3, IDENTIFIER=4, FLOATING_POINT=5, NUMBER=6, 
		COMMENT=7, COMMA=8, SEMICOLON=9, COLON=10, ARROW=11, STAR=12, SLASH=13, 
		PERCENT=14, ASSIGN=15, LESS_THAN=16, GREATER_THAN=17, LESS_THAN_EQUAL=18, 
		GREATER_THAN_EQUAL=19, EQUAL=20, NOT_EQUAL=21, BITWISE_AND=22, BITWISE_OR=23, 
		BITWISE_XOR=24, BITWISE_NOT=25, SHIFT_LEFT=26, SHIFT_RIGHT=27, PLUS=28, 
		MINUS=29, DOLLAR=30, AT=31, LPAREN=32, RPAREN=33, LBRACKET=34, RBRACKET=35, 
		LBRACE=36, RBRACE=37, HASH=38, NOT=39, PERSIST_DIRECTIVE=40, INPUT_DIRECTIVE=41, 
		IF=42, IS=43, ELSE=44, WHILE=45, FOR=46, LOOP=47, BREAK=48, CONTINUE=49, 
		STOP=50, RETURN=51, INPUT=52, PERSIST=53, PRESENT=54, DIRECIVE=55, AND=56, 
		OR=57, BOOLEAN=58, DOT=59;
	public static final int
		RULE_file = 0, RULE_statement = 1, RULE_expressionStatement = 2, RULE_lineComment = 3, 
		RULE_blockStatement = 4, RULE_persistStatement = 5, RULE_directiveType = 6, 
		RULE_inputStatement = 7, RULE_condition = 8, RULE_isCond = 9, RULE_assignment = 10, 
		RULE_globalName = 11, RULE_globalExpr = 12, RULE_paramName = 13, RULE_parameterExpr = 14, 
		RULE_expression = 15, RULE_group = 16, RULE_andExpr = 17, RULE_orExpr = 18, 
		RULE_negationExpr = 19, RULE_equalityExpr = 20, RULE_relationalExpr = 21, 
		RULE_bitwiseAndExpr = 22, RULE_bitwiseOrExpr = 23, RULE_bitwiseXorExpr = 24, 
		RULE_bitwiseNotExpr = 25, RULE_shiftExpr = 26, RULE_multExpr = 27, RULE_addExpr = 28, 
		RULE_primary = 29, RULE_atom = 30, RULE_globalRef = 31, RULE_namedAtom = 32, 
		RULE_variableName = 33, RULE_id = 34, RULE_funcName = 35, RULE_functionCall = 36, 
		RULE_member = 37, RULE_argumentList = 38, RULE_argumentExpr = 39, RULE_argumentName = 40, 
		RULE_namespace = 41, RULE_path = 42, RULE_stopStatement = 43, RULE_ifStatement = 44, 
		RULE_forStatement = 45, RULE_whileStatement = 46, RULE_loopStatement = 47, 
		RULE_returnStatement = 48, RULE_breakStatement = 49, RULE_continueStatement = 50;
	public static final String[] ruleNames = {
		"file", "statement", "expressionStatement", "lineComment", "blockStatement", 
		"persistStatement", "directiveType", "inputStatement", "condition", "isCond", 
		"assignment", "globalName", "globalExpr", "paramName", "parameterExpr", 
		"expression", "group", "andExpr", "orExpr", "negationExpr", "equalityExpr", 
		"relationalExpr", "bitwiseAndExpr", "bitwiseOrExpr", "bitwiseXorExpr", 
		"bitwiseNotExpr", "shiftExpr", "multExpr", "addExpr", "primary", "atom", 
		"globalRef", "namedAtom", "variableName", "id", "funcName", "functionCall", 
		"member", "argumentList", "argumentExpr", "argumentName", "namespace", 
		"path", "stopStatement", "ifStatement", "forStatement", "whileStatement", 
		"loopStatement", "returnStatement", "breakStatement", "continueStatement"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, null, null, null, "','", "';'", "':'", "'->'", 
		"'*'", "'/'", "'%'", "'='", "'<'", "'>'", "'<='", "'>='", "'=='", "'!='", 
		"'&'", "'|'", "'^'", "'~'", "'<<'", "'>>'", "'+'", "'-'", "'$'", "'@'", 
		"'('", "')'", "'['", "']'", "'{'", "'}'", "'#'", "'!'", null, null, "'if'", 
		"'is'", "'else'", "'while'", "'for'", "'loop'", "'break'", "'continue'", 
		"'stop'", "'return'", null, null, "'resent'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "WHITESPACE", "DIRECTIVE", "STRING", "IDENTIFIER", "FLOATING_POINT", 
		"NUMBER", "COMMENT", "COMMA", "SEMICOLON", "COLON", "ARROW", "STAR", "SLASH", 
		"PERCENT", "ASSIGN", "LESS_THAN", "GREATER_THAN", "LESS_THAN_EQUAL", "GREATER_THAN_EQUAL", 
		"EQUAL", "NOT_EQUAL", "BITWISE_AND", "BITWISE_OR", "BITWISE_XOR", "BITWISE_NOT", 
		"SHIFT_LEFT", "SHIFT_RIGHT", "PLUS", "MINUS", "DOLLAR", "AT", "LPAREN", 
		"RPAREN", "LBRACKET", "RBRACKET", "LBRACE", "RBRACE", "HASH", "NOT", "PERSIST_DIRECTIVE", 
		"INPUT_DIRECTIVE", "IF", "IS", "ELSE", "WHILE", "FOR", "LOOP", "BREAK", 
		"CONTINUE", "STOP", "RETURN", "INPUT", "PERSIST", "PRESENT", "DIRECIVE", 
		"AND", "OR", "BOOLEAN", "DOT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "QuantumParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public QuantumParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class FileContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public FileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_file; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitFile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FileContext file() throws RecognitionException {
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING) | (1L << IDENTIFIER) | (1L << FLOATING_POINT) | (1L << NUMBER) | (1L << COMMENT) | (1L << BITWISE_NOT) | (1L << DOLLAR) | (1L << AT) | (1L << LPAREN) | (1L << LBRACKET) | (1L << HASH) | (1L << NOT) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << LOOP) | (1L << BREAK) | (1L << CONTINUE) | (1L << STOP) | (1L << RETURN) | (1L << PRESENT) | (1L << BOOLEAN))) != 0)) {
				{
				{
				setState(102);
				statement();
				}
				}
				setState(107);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public LineCommentContext lineComment() {
			return getRuleContext(LineCommentContext.class,0);
		}
		public PersistStatementContext persistStatement() {
			return getRuleContext(PersistStatementContext.class,0);
		}
		public InputStatementContext inputStatement() {
			return getRuleContext(InputStatementContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public StopStatementContext stopStatement() {
			return getRuleContext(StopStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public LoopStatementContext loopStatement() {
			return getRuleContext(LoopStatementContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public BreakStatementContext breakStatement() {
			return getRuleContext(BreakStatementContext.class,0);
		}
		public ContinueStatementContext continueStatement() {
			return getRuleContext(ContinueStatementContext.class,0);
		}
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				setState(108);
				lineComment();
				}
				break;
			case 2:
				{
				setState(109);
				persistStatement();
				}
				break;
			case 3:
				{
				setState(110);
				inputStatement();
				}
				break;
			case 4:
				{
				setState(111);
				assignment();
				}
				break;
			case 5:
				{
				setState(112);
				functionCall();
				}
				break;
			case 6:
				{
				setState(113);
				stopStatement();
				}
				break;
			case 7:
				{
				setState(114);
				ifStatement();
				}
				break;
			case 8:
				{
				setState(115);
				forStatement();
				}
				break;
			case 9:
				{
				setState(116);
				whileStatement();
				}
				break;
			case 10:
				{
				setState(117);
				loopStatement();
				}
				break;
			case 11:
				{
				setState(118);
				returnStatement();
				}
				break;
			case 12:
				{
				setState(119);
				breakStatement();
				}
				break;
			case 13:
				{
				setState(120);
				continueStatement();
				}
				break;
			case 14:
				{
				setState(121);
				expressionStatement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(QuantumParser.SEMICOLON, 0); }
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterExpressionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitExpressionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitExpressionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			expression();
			setState(125);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LineCommentContext extends ParserRuleContext {
		public TerminalNode COMMENT() { return getToken(QuantumParser.COMMENT, 0); }
		public LineCommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lineComment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterLineComment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitLineComment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitLineComment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LineCommentContext lineComment() throws RecognitionException {
		LineCommentContext _localctx = new LineCommentContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_lineComment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			match(COMMENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockStatementContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(QuantumParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(QuantumParser.RBRACE, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterBlockStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitBlockStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitBlockStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockStatementContext blockStatement() throws RecognitionException {
		BlockStatementContext _localctx = new BlockStatementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_blockStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			match(LBRACE);
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING) | (1L << IDENTIFIER) | (1L << FLOATING_POINT) | (1L << NUMBER) | (1L << COMMENT) | (1L << BITWISE_NOT) | (1L << DOLLAR) | (1L << AT) | (1L << LPAREN) | (1L << LBRACKET) | (1L << HASH) | (1L << NOT) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << LOOP) | (1L << BREAK) | (1L << CONTINUE) | (1L << STOP) | (1L << RETURN) | (1L << PRESENT) | (1L << BOOLEAN))) != 0)) {
				{
				{
				setState(130);
				statement();
				}
				}
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(136);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PersistStatementContext extends ParserRuleContext {
		public TerminalNode HASH() { return getToken(QuantumParser.HASH, 0); }
		public TerminalNode PERSIST() { return getToken(QuantumParser.PERSIST, 0); }
		public DirectiveTypeContext directiveType() {
			return getRuleContext(DirectiveTypeContext.class,0);
		}
		public List<GlobalNameContext> globalName() {
			return getRuleContexts(GlobalNameContext.class);
		}
		public GlobalNameContext globalName(int i) {
			return getRuleContext(GlobalNameContext.class,i);
		}
		public PersistStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_persistStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterPersistStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitPersistStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitPersistStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PersistStatementContext persistStatement() throws RecognitionException {
		PersistStatementContext _localctx = new PersistStatementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_persistStatement);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			match(HASH);
			setState(139);
			match(PERSIST);
			setState(140);
			directiveType();
			setState(141);
			globalName();
			setState(145);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(142);
					globalName();
					}
					} 
				}
				setState(147);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DirectiveTypeContext extends ParserRuleContext {
		public TerminalNode LESS_THAN() { return getToken(QuantumParser.LESS_THAN, 0); }
		public TerminalNode IDENTIFIER() { return getToken(QuantumParser.IDENTIFIER, 0); }
		public TerminalNode GREATER_THAN() { return getToken(QuantumParser.GREATER_THAN, 0); }
		public DirectiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_directiveType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterDirectiveType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitDirectiveType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitDirectiveType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DirectiveTypeContext directiveType() throws RecognitionException {
		DirectiveTypeContext _localctx = new DirectiveTypeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_directiveType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			match(LESS_THAN);
			setState(149);
			match(IDENTIFIER);
			setState(150);
			match(GREATER_THAN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InputStatementContext extends ParserRuleContext {
		public TerminalNode HASH() { return getToken(QuantumParser.HASH, 0); }
		public TerminalNode INPUT() { return getToken(QuantumParser.INPUT, 0); }
		public List<GlobalNameContext> globalName() {
			return getRuleContexts(GlobalNameContext.class);
		}
		public GlobalNameContext globalName(int i) {
			return getRuleContext(GlobalNameContext.class,i);
		}
		public InputStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inputStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterInputStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitInputStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitInputStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InputStatementContext inputStatement() throws RecognitionException {
		InputStatementContext _localctx = new InputStatementContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_inputStatement);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			match(HASH);
			setState(153);
			match(INPUT);
			setState(154);
			globalName();
			setState(158);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(155);
					globalName();
					}
					} 
				}
				setState(160);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public IsCondContext isCond() {
			return getRuleContext(IsCondContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_condition);
		try {
			setState(163);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(161);
				isCond();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(162);
				expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IsCondContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode IS() { return getToken(QuantumParser.IS, 0); }
		public IsCondContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_isCond; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterIsCond(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitIsCond(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitIsCond(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IsCondContext isCond() throws RecognitionException {
		IsCondContext _localctx = new IsCondContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_isCond);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			expression();
			setState(166);
			match(IS);
			setState(167);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentContext extends ParserRuleContext {
		public GlobalExprContext globalExpr() {
			return getRuleContext(GlobalExprContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(QuantumParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(QuantumParser.SEMICOLON, 0); }
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(169);
			globalExpr();
			setState(170);
			match(ASSIGN);
			setState(171);
			expression();
			setState(172);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GlobalNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(QuantumParser.IDENTIFIER, 0); }
		public GlobalNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globalName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterGlobalName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitGlobalName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitGlobalName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalNameContext globalName() throws RecognitionException {
		GlobalNameContext _localctx = new GlobalNameContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_globalName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GlobalExprContext extends ParserRuleContext {
		public TerminalNode DOLLAR() { return getToken(QuantumParser.DOLLAR, 0); }
		public GlobalNameContext globalName() {
			return getRuleContext(GlobalNameContext.class,0);
		}
		public GlobalExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globalExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterGlobalExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitGlobalExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitGlobalExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalExprContext globalExpr() throws RecognitionException {
		GlobalExprContext _localctx = new GlobalExprContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_globalExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(176);
			match(DOLLAR);
			setState(177);
			globalName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(QuantumParser.IDENTIFIER, 0); }
		public ParamNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterParamName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitParamName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitParamName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamNameContext paramName() throws RecognitionException {
		ParamNameContext _localctx = new ParamNameContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_paramName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterExprContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(QuantumParser.AT, 0); }
		public ParamNameContext paramName() {
			return getRuleContext(ParamNameContext.class,0);
		}
		public ParameterExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterParameterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitParameterExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitParameterExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterExprContext parameterExpr() throws RecognitionException {
		ParameterExprContext _localctx = new ParameterExprContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_parameterExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
			match(AT);
			setState(182);
			paramName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public GroupContext group() {
			return getRuleContext(GroupContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(184);
			group();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroupContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(QuantumParser.LPAREN, 0); }
		public AndExprContext andExpr() {
			return getRuleContext(AndExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(QuantumParser.RPAREN, 0); }
		public GroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_group; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitGroup(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitGroup(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupContext group() throws RecognitionException {
		GroupContext _localctx = new GroupContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_group);
		try {
			setState(191);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(186);
				match(LPAREN);
				setState(187);
				andExpr();
				setState(188);
				match(RPAREN);
				}
				break;
			case STRING:
			case IDENTIFIER:
			case FLOATING_POINT:
			case NUMBER:
			case BITWISE_NOT:
			case DOLLAR:
			case AT:
			case LBRACKET:
			case NOT:
			case PRESENT:
			case BOOLEAN:
				enterOuterAlt(_localctx, 2);
				{
				setState(190);
				andExpr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AndExprContext extends ParserRuleContext {
		public OrExprContext orExpr() {
			return getRuleContext(OrExprContext.class,0);
		}
		public TerminalNode AND() { return getToken(QuantumParser.AND, 0); }
		public AndExprContext andExpr() {
			return getRuleContext(AndExprContext.class,0);
		}
		public AndExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitAndExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AndExprContext andExpr() throws RecognitionException {
		AndExprContext _localctx = new AndExprContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_andExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			orExpr();
			setState(196);
			_la = _input.LA(1);
			if (_la==AND) {
				{
				setState(194);
				match(AND);
				setState(195);
				andExpr();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrExprContext extends ParserRuleContext {
		public NegationExprContext negationExpr() {
			return getRuleContext(NegationExprContext.class,0);
		}
		public TerminalNode OR() { return getToken(QuantumParser.OR, 0); }
		public OrExprContext orExpr() {
			return getRuleContext(OrExprContext.class,0);
		}
		public OrExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterOrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitOrExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitOrExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrExprContext orExpr() throws RecognitionException {
		OrExprContext _localctx = new OrExprContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_orExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			negationExpr();
			setState(201);
			_la = _input.LA(1);
			if (_la==OR) {
				{
				setState(199);
				match(OR);
				setState(200);
				orExpr();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NegationExprContext extends ParserRuleContext {
		public EqualityExprContext equalityExpr() {
			return getRuleContext(EqualityExprContext.class,0);
		}
		public TerminalNode NOT() { return getToken(QuantumParser.NOT, 0); }
		public TerminalNode LPAREN() { return getToken(QuantumParser.LPAREN, 0); }
		public NegationExprContext negationExpr() {
			return getRuleContext(NegationExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(QuantumParser.RPAREN, 0); }
		public NegationExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_negationExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterNegationExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitNegationExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitNegationExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NegationExprContext negationExpr() throws RecognitionException {
		NegationExprContext _localctx = new NegationExprContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_negationExpr);
		int _la;
		try {
			setState(212);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(204);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(203);
					match(NOT);
					}
				}

				setState(206);
				equalityExpr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(207);
				match(NOT);
				setState(208);
				match(LPAREN);
				setState(209);
				negationExpr();
				setState(210);
				match(RPAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EqualityExprContext extends ParserRuleContext {
		public RelationalExprContext relationalExpr() {
			return getRuleContext(RelationalExprContext.class,0);
		}
		public TerminalNode EQUAL() { return getToken(QuantumParser.EQUAL, 0); }
		public EqualityExprContext equalityExpr() {
			return getRuleContext(EqualityExprContext.class,0);
		}
		public TerminalNode NOT_EQUAL() { return getToken(QuantumParser.NOT_EQUAL, 0); }
		public EqualityExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterEqualityExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitEqualityExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitEqualityExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityExprContext equalityExpr() throws RecognitionException {
		EqualityExprContext _localctx = new EqualityExprContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_equalityExpr);
		int _la;
		try {
			setState(224);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(214);
				relationalExpr();
				setState(217);
				_la = _input.LA(1);
				if (_la==EQUAL) {
					{
					setState(215);
					match(EQUAL);
					setState(216);
					equalityExpr();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(219);
				relationalExpr();
				setState(222);
				_la = _input.LA(1);
				if (_la==NOT_EQUAL) {
					{
					setState(220);
					match(NOT_EQUAL);
					setState(221);
					equalityExpr();
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelationalExprContext extends ParserRuleContext {
		public BitwiseAndExprContext bitwiseAndExpr() {
			return getRuleContext(BitwiseAndExprContext.class,0);
		}
		public TerminalNode LESS_THAN() { return getToken(QuantumParser.LESS_THAN, 0); }
		public RelationalExprContext relationalExpr() {
			return getRuleContext(RelationalExprContext.class,0);
		}
		public TerminalNode GREATER_THAN() { return getToken(QuantumParser.GREATER_THAN, 0); }
		public TerminalNode LESS_THAN_EQUAL() { return getToken(QuantumParser.LESS_THAN_EQUAL, 0); }
		public TerminalNode GREATER_THAN_EQUAL() { return getToken(QuantumParser.GREATER_THAN_EQUAL, 0); }
		public RelationalExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationalExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterRelationalExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitRelationalExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitRelationalExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationalExprContext relationalExpr() throws RecognitionException {
		RelationalExprContext _localctx = new RelationalExprContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_relationalExpr);
		int _la;
		try {
			setState(246);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(226);
				bitwiseAndExpr();
				setState(229);
				_la = _input.LA(1);
				if (_la==LESS_THAN) {
					{
					setState(227);
					match(LESS_THAN);
					setState(228);
					relationalExpr();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(231);
				bitwiseAndExpr();
				setState(234);
				_la = _input.LA(1);
				if (_la==GREATER_THAN) {
					{
					setState(232);
					match(GREATER_THAN);
					setState(233);
					relationalExpr();
					}
				}

				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(236);
				bitwiseAndExpr();
				setState(239);
				_la = _input.LA(1);
				if (_la==LESS_THAN_EQUAL) {
					{
					setState(237);
					match(LESS_THAN_EQUAL);
					setState(238);
					relationalExpr();
					}
				}

				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(241);
				bitwiseAndExpr();
				setState(244);
				_la = _input.LA(1);
				if (_la==GREATER_THAN_EQUAL) {
					{
					setState(242);
					match(GREATER_THAN_EQUAL);
					setState(243);
					relationalExpr();
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BitwiseAndExprContext extends ParserRuleContext {
		public BitwiseOrExprContext bitwiseOrExpr() {
			return getRuleContext(BitwiseOrExprContext.class,0);
		}
		public TerminalNode BITWISE_AND() { return getToken(QuantumParser.BITWISE_AND, 0); }
		public BitwiseAndExprContext bitwiseAndExpr() {
			return getRuleContext(BitwiseAndExprContext.class,0);
		}
		public BitwiseAndExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bitwiseAndExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterBitwiseAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitBitwiseAndExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitBitwiseAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BitwiseAndExprContext bitwiseAndExpr() throws RecognitionException {
		BitwiseAndExprContext _localctx = new BitwiseAndExprContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_bitwiseAndExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(248);
			bitwiseOrExpr();
			setState(251);
			_la = _input.LA(1);
			if (_la==BITWISE_AND) {
				{
				setState(249);
				match(BITWISE_AND);
				setState(250);
				bitwiseAndExpr();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BitwiseOrExprContext extends ParserRuleContext {
		public BitwiseXorExprContext bitwiseXorExpr() {
			return getRuleContext(BitwiseXorExprContext.class,0);
		}
		public TerminalNode BITWISE_OR() { return getToken(QuantumParser.BITWISE_OR, 0); }
		public BitwiseOrExprContext bitwiseOrExpr() {
			return getRuleContext(BitwiseOrExprContext.class,0);
		}
		public BitwiseOrExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bitwiseOrExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterBitwiseOrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitBitwiseOrExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitBitwiseOrExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BitwiseOrExprContext bitwiseOrExpr() throws RecognitionException {
		BitwiseOrExprContext _localctx = new BitwiseOrExprContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_bitwiseOrExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			bitwiseXorExpr();
			setState(256);
			_la = _input.LA(1);
			if (_la==BITWISE_OR) {
				{
				setState(254);
				match(BITWISE_OR);
				setState(255);
				bitwiseOrExpr();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BitwiseXorExprContext extends ParserRuleContext {
		public BitwiseNotExprContext bitwiseNotExpr() {
			return getRuleContext(BitwiseNotExprContext.class,0);
		}
		public TerminalNode BITWISE_XOR() { return getToken(QuantumParser.BITWISE_XOR, 0); }
		public BitwiseXorExprContext bitwiseXorExpr() {
			return getRuleContext(BitwiseXorExprContext.class,0);
		}
		public BitwiseXorExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bitwiseXorExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterBitwiseXorExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitBitwiseXorExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitBitwiseXorExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BitwiseXorExprContext bitwiseXorExpr() throws RecognitionException {
		BitwiseXorExprContext _localctx = new BitwiseXorExprContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_bitwiseXorExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(258);
			bitwiseNotExpr();
			setState(261);
			_la = _input.LA(1);
			if (_la==BITWISE_XOR) {
				{
				setState(259);
				match(BITWISE_XOR);
				setState(260);
				bitwiseXorExpr();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BitwiseNotExprContext extends ParserRuleContext {
		public ShiftExprContext shiftExpr() {
			return getRuleContext(ShiftExprContext.class,0);
		}
		public TerminalNode BITWISE_NOT() { return getToken(QuantumParser.BITWISE_NOT, 0); }
		public BitwiseNotExprContext bitwiseNotExpr() {
			return getRuleContext(BitwiseNotExprContext.class,0);
		}
		public BitwiseNotExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bitwiseNotExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterBitwiseNotExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitBitwiseNotExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitBitwiseNotExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BitwiseNotExprContext bitwiseNotExpr() throws RecognitionException {
		BitwiseNotExprContext _localctx = new BitwiseNotExprContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_bitwiseNotExpr);
		int _la;
		try {
			setState(269);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(264);
				_la = _input.LA(1);
				if (_la==BITWISE_NOT) {
					{
					setState(263);
					match(BITWISE_NOT);
					}
				}

				setState(266);
				shiftExpr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(267);
				match(BITWISE_NOT);
				setState(268);
				bitwiseNotExpr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ShiftExprContext extends ParserRuleContext {
		public MultExprContext multExpr() {
			return getRuleContext(MultExprContext.class,0);
		}
		public List<ShiftExprContext> shiftExpr() {
			return getRuleContexts(ShiftExprContext.class);
		}
		public ShiftExprContext shiftExpr(int i) {
			return getRuleContext(ShiftExprContext.class,i);
		}
		public List<TerminalNode> SHIFT_LEFT() { return getTokens(QuantumParser.SHIFT_LEFT); }
		public TerminalNode SHIFT_LEFT(int i) {
			return getToken(QuantumParser.SHIFT_LEFT, i);
		}
		public List<TerminalNode> SHIFT_RIGHT() { return getTokens(QuantumParser.SHIFT_RIGHT); }
		public TerminalNode SHIFT_RIGHT(int i) {
			return getToken(QuantumParser.SHIFT_RIGHT, i);
		}
		public ShiftExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shiftExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterShiftExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitShiftExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitShiftExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShiftExprContext shiftExpr() throws RecognitionException {
		ShiftExprContext _localctx = new ShiftExprContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_shiftExpr);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(271);
			multExpr();
			setState(276);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(272);
					_la = _input.LA(1);
					if ( !(_la==SHIFT_LEFT || _la==SHIFT_RIGHT) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					setState(273);
					shiftExpr();
					}
					} 
				}
				setState(278);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultExprContext extends ParserRuleContext {
		public AddExprContext addExpr() {
			return getRuleContext(AddExprContext.class,0);
		}
		public List<MultExprContext> multExpr() {
			return getRuleContexts(MultExprContext.class);
		}
		public MultExprContext multExpr(int i) {
			return getRuleContext(MultExprContext.class,i);
		}
		public List<TerminalNode> STAR() { return getTokens(QuantumParser.STAR); }
		public TerminalNode STAR(int i) {
			return getToken(QuantumParser.STAR, i);
		}
		public List<TerminalNode> SLASH() { return getTokens(QuantumParser.SLASH); }
		public TerminalNode SLASH(int i) {
			return getToken(QuantumParser.SLASH, i);
		}
		public List<TerminalNode> PERCENT() { return getTokens(QuantumParser.PERCENT); }
		public TerminalNode PERCENT(int i) {
			return getToken(QuantumParser.PERCENT, i);
		}
		public MultExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterMultExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitMultExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitMultExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultExprContext multExpr() throws RecognitionException {
		MultExprContext _localctx = new MultExprContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_multExpr);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(279);
			addExpr();
			setState(284);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(280);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STAR) | (1L << SLASH) | (1L << PERCENT))) != 0)) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					setState(281);
					multExpr();
					}
					} 
				}
				setState(286);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AddExprContext extends ParserRuleContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public List<AddExprContext> addExpr() {
			return getRuleContexts(AddExprContext.class);
		}
		public AddExprContext addExpr(int i) {
			return getRuleContext(AddExprContext.class,i);
		}
		public List<TerminalNode> PLUS() { return getTokens(QuantumParser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(QuantumParser.PLUS, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(QuantumParser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(QuantumParser.MINUS, i);
		}
		public AddExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_addExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterAddExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitAddExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitAddExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AddExprContext addExpr() throws RecognitionException {
		AddExprContext _localctx = new AddExprContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_addExpr);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(287);
			primary();
			setState(292);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(288);
					_la = _input.LA(1);
					if ( !(_la==PLUS || _la==MINUS) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					setState(289);
					addExpr();
					}
					} 
				}
				setState(294);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryContext extends ParserRuleContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_primary);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(295);
			atom();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public NamedAtomContext namedAtom() {
			return getRuleContext(NamedAtomContext.class,0);
		}
		public TerminalNode STRING() { return getToken(QuantumParser.STRING, 0); }
		public TerminalNode NUMBER() { return getToken(QuantumParser.NUMBER, 0); }
		public TerminalNode FLOATING_POINT() { return getToken(QuantumParser.FLOATING_POINT, 0); }
		public TerminalNode BOOLEAN() { return getToken(QuantumParser.BOOLEAN, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_atom);
		try {
			setState(303);
			switch (_input.LA(1)) {
			case IDENTIFIER:
			case DOLLAR:
			case AT:
			case PRESENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(297);
				namedAtom();
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(298);
				match(STRING);
				}
				break;
			case NUMBER:
				enterOuterAlt(_localctx, 3);
				{
				setState(299);
				match(NUMBER);
				}
				break;
			case FLOATING_POINT:
				enterOuterAlt(_localctx, 4);
				{
				setState(300);
				match(FLOATING_POINT);
				}
				break;
			case BOOLEAN:
				enterOuterAlt(_localctx, 5);
				{
				setState(301);
				match(BOOLEAN);
				}
				break;
			case LBRACKET:
				enterOuterAlt(_localctx, 6);
				{
				setState(302);
				id();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GlobalRefContext extends ParserRuleContext {
		public GlobalExprContext globalExpr() {
			return getRuleContext(GlobalExprContext.class,0);
		}
		public TerminalNode PRESENT() { return getToken(QuantumParser.PRESENT, 0); }
		public GlobalRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globalRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterGlobalRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitGlobalRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitGlobalRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalRefContext globalRef() throws RecognitionException {
		GlobalRefContext _localctx = new GlobalRefContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_globalRef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(306);
			_la = _input.LA(1);
			if (_la==PRESENT) {
				{
				setState(305);
				match(PRESENT);
				}
			}

			setState(308);
			globalExpr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamedAtomContext extends ParserRuleContext {
		public GlobalRefContext globalRef() {
			return getRuleContext(GlobalRefContext.class,0);
		}
		public ParameterExprContext parameterExpr() {
			return getRuleContext(ParameterExprContext.class,0);
		}
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public VariableNameContext variableName() {
			return getRuleContext(VariableNameContext.class,0);
		}
		public List<MemberContext> member() {
			return getRuleContexts(MemberContext.class);
		}
		public MemberContext member(int i) {
			return getRuleContext(MemberContext.class,i);
		}
		public NamedAtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namedAtom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterNamedAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitNamedAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitNamedAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamedAtomContext namedAtom() throws RecognitionException {
		NamedAtomContext _localctx = new NamedAtomContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_namedAtom);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(314);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				setState(310);
				globalRef();
				}
				break;
			case 2:
				{
				setState(311);
				parameterExpr();
				}
				break;
			case 3:
				{
				setState(312);
				functionCall();
				}
				break;
			case 4:
				{
				setState(313);
				variableName();
				}
				break;
			}
			setState(319);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COLON) {
				{
				{
				setState(316);
				member();
				}
				}
				setState(321);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(QuantumParser.IDENTIFIER, 0); }
		public VariableNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterVariableName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitVariableName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitVariableName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableNameContext variableName() throws RecognitionException {
		VariableNameContext _localctx = new VariableNameContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_variableName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(322);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdContext extends ParserRuleContext {
		public TerminalNode LBRACKET() { return getToken(QuantumParser.LBRACKET, 0); }
		public NamespaceContext namespace() {
			return getRuleContext(NamespaceContext.class,0);
		}
		public TerminalNode COLON() { return getToken(QuantumParser.COLON, 0); }
		public PathContext path() {
			return getRuleContext(PathContext.class,0);
		}
		public TerminalNode RBRACKET() { return getToken(QuantumParser.RBRACKET, 0); }
		public TerminalNode HASH() { return getToken(QuantumParser.HASH, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_id);
		try {
			setState(337);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(324);
				match(LBRACKET);
				setState(325);
				namespace();
				setState(326);
				match(COLON);
				setState(327);
				path();
				setState(328);
				match(RBRACKET);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(330);
				match(LBRACKET);
				setState(331);
				match(HASH);
				setState(332);
				namespace();
				setState(333);
				match(COLON);
				setState(334);
				path();
				setState(335);
				match(RBRACKET);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(QuantumParser.IDENTIFIER, 0); }
		public FuncNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterFuncName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitFuncName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitFuncName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncNameContext funcName() throws RecognitionException {
		FuncNameContext _localctx = new FuncNameContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_funcName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(339);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionCallContext extends ParserRuleContext {
		public FuncNameContext funcName() {
			return getRuleContext(FuncNameContext.class,0);
		}
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public FunctionCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterFunctionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitFunctionCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionCallContext functionCall() throws RecognitionException {
		FunctionCallContext _localctx = new FunctionCallContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_functionCall);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(341);
			funcName();
			setState(342);
			argumentList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MemberContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(QuantumParser.COLON, 0); }
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public VariableNameContext variableName() {
			return getRuleContext(VariableNameContext.class,0);
		}
		public MemberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_member; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterMember(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitMember(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitMember(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MemberContext member() throws RecognitionException {
		MemberContext _localctx = new MemberContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_member);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(344);
			match(COLON);
			setState(347);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				{
				setState(345);
				functionCall();
				}
				break;
			case 2:
				{
				setState(346);
				variableName();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentListContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(QuantumParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(QuantumParser.RPAREN, 0); }
		public List<ArgumentExprContext> argumentExpr() {
			return getRuleContexts(ArgumentExprContext.class);
		}
		public ArgumentExprContext argumentExpr(int i) {
			return getRuleContext(ArgumentExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(QuantumParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(QuantumParser.COMMA, i);
		}
		public ArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitArgumentList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitArgumentList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentListContext argumentList() throws RecognitionException {
		ArgumentListContext _localctx = new ArgumentListContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_argumentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(349);
			match(LPAREN);
			setState(358);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(350);
				argumentExpr();
				setState(355);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(351);
					match(COMMA);
					setState(352);
					argumentExpr();
					}
					}
					setState(357);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(360);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentExprContext extends ParserRuleContext {
		public ArgumentNameContext argumentName() {
			return getRuleContext(ArgumentNameContext.class,0);
		}
		public TerminalNode COLON() { return getToken(QuantumParser.COLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ArgumentExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterArgumentExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitArgumentExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitArgumentExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentExprContext argumentExpr() throws RecognitionException {
		ArgumentExprContext _localctx = new ArgumentExprContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_argumentExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(362);
			argumentName();
			setState(363);
			match(COLON);
			setState(364);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(QuantumParser.IDENTIFIER, 0); }
		public ArgumentNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterArgumentName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitArgumentName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitArgumentName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentNameContext argumentName() throws RecognitionException {
		ArgumentNameContext _localctx = new ArgumentNameContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_argumentName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(366);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamespaceContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(QuantumParser.IDENTIFIER, 0); }
		public NamespaceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namespace; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterNamespace(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitNamespace(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitNamespace(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamespaceContext namespace() throws RecognitionException {
		NamespaceContext _localctx = new NamespaceContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_namespace);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(368);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(QuantumParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(QuantumParser.IDENTIFIER, i);
		}
		public TerminalNode SLASH() { return getToken(QuantumParser.SLASH, 0); }
		public TerminalNode DOT() { return getToken(QuantumParser.DOT, 0); }
		public PathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_path; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitPath(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitPath(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathContext path() throws RecognitionException {
		PathContext _localctx = new PathContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_path);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(370);
			match(IDENTIFIER);
			{
			setState(371);
			match(SLASH);
			setState(372);
			match(IDENTIFIER);
			}
			setState(376);
			_la = _input.LA(1);
			if (_la==DOT) {
				{
				setState(374);
				match(DOT);
				setState(375);
				match(IDENTIFIER);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StopStatementContext extends ParserRuleContext {
		public TerminalNode STOP() { return getToken(QuantumParser.STOP, 0); }
		public TerminalNode SEMICOLON() { return getToken(QuantumParser.SEMICOLON, 0); }
		public StopStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stopStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterStopStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitStopStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitStopStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StopStatementContext stopStatement() throws RecognitionException {
		StopStatementContext _localctx = new StopStatementContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_stopStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(378);
			match(STOP);
			setState(379);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(QuantumParser.IF, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(QuantumParser.ELSE, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(381);
			match(IF);
			setState(382);
			condition();
			setState(383);
			statement();
			setState(386);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				{
				setState(384);
				match(ELSE);
				setState(385);
				statement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForStatementContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(QuantumParser.FOR, 0); }
		public TerminalNode LPAREN() { return getToken(QuantumParser.LPAREN, 0); }
		public List<AssignmentContext> assignment() {
			return getRuleContexts(AssignmentContext.class);
		}
		public AssignmentContext assignment(int i) {
			return getRuleContext(AssignmentContext.class,i);
		}
		public List<TerminalNode> SEMICOLON() { return getTokens(QuantumParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(QuantumParser.SEMICOLON, i);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(QuantumParser.RPAREN, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterForStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitForStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_forStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(388);
			match(FOR);
			setState(389);
			match(LPAREN);
			setState(390);
			assignment();
			setState(391);
			match(SEMICOLON);
			setState(392);
			condition();
			setState(393);
			match(SEMICOLON);
			setState(394);
			assignment();
			setState(395);
			match(RPAREN);
			setState(396);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStatementContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(QuantumParser.WHILE, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(398);
			match(WHILE);
			setState(399);
			condition();
			setState(400);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LoopStatementContext extends ParserRuleContext {
		public TerminalNode LOOP() { return getToken(QuantumParser.LOOP, 0); }
		public BlockStatementContext blockStatement() {
			return getRuleContext(BlockStatementContext.class,0);
		}
		public LoopStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loopStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterLoopStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitLoopStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitLoopStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LoopStatementContext loopStatement() throws RecognitionException {
		LoopStatementContext _localctx = new LoopStatementContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_loopStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(402);
			match(LOOP);
			setState(403);
			blockStatement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(QuantumParser.RETURN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(QuantumParser.SEMICOLON, 0); }
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitReturnStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(405);
			match(RETURN);
			setState(406);
			expression();
			setState(407);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BreakStatementContext extends ParserRuleContext {
		public TerminalNode BREAK() { return getToken(QuantumParser.BREAK, 0); }
		public TerminalNode SEMICOLON() { return getToken(QuantumParser.SEMICOLON, 0); }
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterBreakStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitBreakStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitBreakStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(409);
			match(BREAK);
			setState(410);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ContinueStatementContext extends ParserRuleContext {
		public TerminalNode CONTINUE() { return getToken(QuantumParser.CONTINUE, 0); }
		public TerminalNode SEMICOLON() { return getToken(QuantumParser.SEMICOLON, 0); }
		public ContinueStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continueStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).enterContinueStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuantumParserListener ) ((QuantumParserListener)listener).exitContinueStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuantumParserVisitor ) return ((QuantumParserVisitor<? extends T>)visitor).visitContinueStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContinueStatementContext continueStatement() throws RecognitionException {
		ContinueStatementContext _localctx = new ContinueStatementContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(412);
			match(CONTINUE);
			setState(413);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3=\u01a2\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\3\2\7\2j\n\2\f\2\16\2m\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\5\3}\n\3\3\4\3\4\3\4\3\5\3\5\3\6\3\6\7\6\u0086\n\6"+
		"\f\6\16\6\u0089\13\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\7\7\u0092\n\7\f\7\16"+
		"\7\u0095\13\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\7\t\u009f\n\t\f\t\16\t\u00a2"+
		"\13\t\3\n\3\n\5\n\u00a6\n\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3"+
		"\r\3\r\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\22"+
		"\3\22\3\22\5\22\u00c2\n\22\3\23\3\23\3\23\5\23\u00c7\n\23\3\24\3\24\3"+
		"\24\5\24\u00cc\n\24\3\25\5\25\u00cf\n\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\5\25\u00d7\n\25\3\26\3\26\3\26\5\26\u00dc\n\26\3\26\3\26\3\26\5\26\u00e1"+
		"\n\26\5\26\u00e3\n\26\3\27\3\27\3\27\5\27\u00e8\n\27\3\27\3\27\3\27\5"+
		"\27\u00ed\n\27\3\27\3\27\3\27\5\27\u00f2\n\27\3\27\3\27\3\27\5\27\u00f7"+
		"\n\27\5\27\u00f9\n\27\3\30\3\30\3\30\5\30\u00fe\n\30\3\31\3\31\3\31\5"+
		"\31\u0103\n\31\3\32\3\32\3\32\5\32\u0108\n\32\3\33\5\33\u010b\n\33\3\33"+
		"\3\33\3\33\5\33\u0110\n\33\3\34\3\34\3\34\7\34\u0115\n\34\f\34\16\34\u0118"+
		"\13\34\3\35\3\35\3\35\7\35\u011d\n\35\f\35\16\35\u0120\13\35\3\36\3\36"+
		"\3\36\7\36\u0125\n\36\f\36\16\36\u0128\13\36\3\37\3\37\3 \3 \3 \3 \3 "+
		"\3 \5 \u0132\n \3!\5!\u0135\n!\3!\3!\3\"\3\"\3\"\3\"\5\"\u013d\n\"\3\""+
		"\7\"\u0140\n\"\f\"\16\"\u0143\13\"\3#\3#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3"+
		"$\3$\3$\3$\5$\u0154\n$\3%\3%\3&\3&\3&\3\'\3\'\3\'\5\'\u015e\n\'\3(\3("+
		"\3(\3(\7(\u0164\n(\f(\16(\u0167\13(\5(\u0169\n(\3(\3(\3)\3)\3)\3)\3*\3"+
		"*\3+\3+\3,\3,\3,\3,\3,\3,\5,\u017b\n,\3-\3-\3-\3.\3.\3.\3.\3.\5.\u0185"+
		"\n.\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3"+
		"\62\3\62\3\62\3\62\3\63\3\63\3\63\3\64\3\64\3\64\3\64\2\2\65\2\4\6\b\n"+
		"\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\"+
		"^`bdf\2\5\3\2\34\35\3\2\16\20\3\2\36\37\u01a7\2k\3\2\2\2\4|\3\2\2\2\6"+
		"~\3\2\2\2\b\u0081\3\2\2\2\n\u0083\3\2\2\2\f\u008c\3\2\2\2\16\u0096\3\2"+
		"\2\2\20\u009a\3\2\2\2\22\u00a5\3\2\2\2\24\u00a7\3\2\2\2\26\u00ab\3\2\2"+
		"\2\30\u00b0\3\2\2\2\32\u00b2\3\2\2\2\34\u00b5\3\2\2\2\36\u00b7\3\2\2\2"+
		" \u00ba\3\2\2\2\"\u00c1\3\2\2\2$\u00c3\3\2\2\2&\u00c8\3\2\2\2(\u00d6\3"+
		"\2\2\2*\u00e2\3\2\2\2,\u00f8\3\2\2\2.\u00fa\3\2\2\2\60\u00ff\3\2\2\2\62"+
		"\u0104\3\2\2\2\64\u010f\3\2\2\2\66\u0111\3\2\2\28\u0119\3\2\2\2:\u0121"+
		"\3\2\2\2<\u0129\3\2\2\2>\u0131\3\2\2\2@\u0134\3\2\2\2B\u013c\3\2\2\2D"+
		"\u0144\3\2\2\2F\u0153\3\2\2\2H\u0155\3\2\2\2J\u0157\3\2\2\2L\u015a\3\2"+
		"\2\2N\u015f\3\2\2\2P\u016c\3\2\2\2R\u0170\3\2\2\2T\u0172\3\2\2\2V\u0174"+
		"\3\2\2\2X\u017c\3\2\2\2Z\u017f\3\2\2\2\\\u0186\3\2\2\2^\u0190\3\2\2\2"+
		"`\u0194\3\2\2\2b\u0197\3\2\2\2d\u019b\3\2\2\2f\u019e\3\2\2\2hj\5\4\3\2"+
		"ih\3\2\2\2jm\3\2\2\2ki\3\2\2\2kl\3\2\2\2l\3\3\2\2\2mk\3\2\2\2n}\5\b\5"+
		"\2o}\5\f\7\2p}\5\20\t\2q}\5\26\f\2r}\5J&\2s}\5X-\2t}\5Z.\2u}\5\\/\2v}"+
		"\5^\60\2w}\5`\61\2x}\5b\62\2y}\5d\63\2z}\5f\64\2{}\5\6\4\2|n\3\2\2\2|"+
		"o\3\2\2\2|p\3\2\2\2|q\3\2\2\2|r\3\2\2\2|s\3\2\2\2|t\3\2\2\2|u\3\2\2\2"+
		"|v\3\2\2\2|w\3\2\2\2|x\3\2\2\2|y\3\2\2\2|z\3\2\2\2|{\3\2\2\2}\5\3\2\2"+
		"\2~\177\5 \21\2\177\u0080\7\13\2\2\u0080\7\3\2\2\2\u0081\u0082\7\t\2\2"+
		"\u0082\t\3\2\2\2\u0083\u0087\7&\2\2\u0084\u0086\5\4\3\2\u0085\u0084\3"+
		"\2\2\2\u0086\u0089\3\2\2\2\u0087\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088"+
		"\u008a\3\2\2\2\u0089\u0087\3\2\2\2\u008a\u008b\7\'\2\2\u008b\13\3\2\2"+
		"\2\u008c\u008d\7(\2\2\u008d\u008e\7\67\2\2\u008e\u008f\5\16\b\2\u008f"+
		"\u0093\5\30\r\2\u0090\u0092\5\30\r\2\u0091\u0090\3\2\2\2\u0092\u0095\3"+
		"\2\2\2\u0093\u0091\3\2\2\2\u0093\u0094\3\2\2\2\u0094\r\3\2\2\2\u0095\u0093"+
		"\3\2\2\2\u0096\u0097\7\22\2\2\u0097\u0098\7\6\2\2\u0098\u0099\7\23\2\2"+
		"\u0099\17\3\2\2\2\u009a\u009b\7(\2\2\u009b\u009c\7\66\2\2\u009c\u00a0"+
		"\5\30\r\2\u009d\u009f\5\30\r\2\u009e\u009d\3\2\2\2\u009f\u00a2\3\2\2\2"+
		"\u00a0\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\21\3\2\2\2\u00a2\u00a0"+
		"\3\2\2\2\u00a3\u00a6\5\24\13\2\u00a4\u00a6\5 \21\2\u00a5\u00a3\3\2\2\2"+
		"\u00a5\u00a4\3\2\2\2\u00a6\23\3\2\2\2\u00a7\u00a8\5 \21\2\u00a8\u00a9"+
		"\7-\2\2\u00a9\u00aa\5 \21\2\u00aa\25\3\2\2\2\u00ab\u00ac\5\32\16\2\u00ac"+
		"\u00ad\7\21\2\2\u00ad\u00ae\5 \21\2\u00ae\u00af\7\13\2\2\u00af\27\3\2"+
		"\2\2\u00b0\u00b1\7\6\2\2\u00b1\31\3\2\2\2\u00b2\u00b3\7 \2\2\u00b3\u00b4"+
		"\5\30\r\2\u00b4\33\3\2\2\2\u00b5\u00b6\7\6\2\2\u00b6\35\3\2\2\2\u00b7"+
		"\u00b8\7!\2\2\u00b8\u00b9\5\34\17\2\u00b9\37\3\2\2\2\u00ba\u00bb\5\"\22"+
		"\2\u00bb!\3\2\2\2\u00bc\u00bd\7\"\2\2\u00bd\u00be\5$\23\2\u00be\u00bf"+
		"\7#\2\2\u00bf\u00c2\3\2\2\2\u00c0\u00c2\5$\23\2\u00c1\u00bc\3\2\2\2\u00c1"+
		"\u00c0\3\2\2\2\u00c2#\3\2\2\2\u00c3\u00c6\5&\24\2\u00c4\u00c5\7:\2\2\u00c5"+
		"\u00c7\5$\23\2\u00c6\u00c4\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7%\3\2\2\2"+
		"\u00c8\u00cb\5(\25\2\u00c9\u00ca\7;\2\2\u00ca\u00cc\5&\24\2\u00cb\u00c9"+
		"\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\'\3\2\2\2\u00cd\u00cf\7)\2\2\u00ce"+
		"\u00cd\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\u00d7\5*"+
		"\26\2\u00d1\u00d2\7)\2\2\u00d2\u00d3\7\"\2\2\u00d3\u00d4\5(\25\2\u00d4"+
		"\u00d5\7#\2\2\u00d5\u00d7\3\2\2\2\u00d6\u00ce\3\2\2\2\u00d6\u00d1\3\2"+
		"\2\2\u00d7)\3\2\2\2\u00d8\u00db\5,\27\2\u00d9\u00da\7\26\2\2\u00da\u00dc"+
		"\5*\26\2\u00db\u00d9\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc\u00e3\3\2\2\2\u00dd"+
		"\u00e0\5,\27\2\u00de\u00df\7\27\2\2\u00df\u00e1\5*\26\2\u00e0\u00de\3"+
		"\2\2\2\u00e0\u00e1\3\2\2\2\u00e1\u00e3\3\2\2\2\u00e2\u00d8\3\2\2\2\u00e2"+
		"\u00dd\3\2\2\2\u00e3+\3\2\2\2\u00e4\u00e7\5.\30\2\u00e5\u00e6\7\22\2\2"+
		"\u00e6\u00e8\5,\27\2\u00e7\u00e5\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8\u00f9"+
		"\3\2\2\2\u00e9\u00ec\5.\30\2\u00ea\u00eb\7\23\2\2\u00eb\u00ed\5,\27\2"+
		"\u00ec\u00ea\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00f9\3\2\2\2\u00ee\u00f1"+
		"\5.\30\2\u00ef\u00f0\7\24\2\2\u00f0\u00f2\5,\27\2\u00f1\u00ef\3\2\2\2"+
		"\u00f1\u00f2\3\2\2\2\u00f2\u00f9\3\2\2\2\u00f3\u00f6\5.\30\2\u00f4\u00f5"+
		"\7\25\2\2\u00f5\u00f7\5,\27\2\u00f6\u00f4\3\2\2\2\u00f6\u00f7\3\2\2\2"+
		"\u00f7\u00f9\3\2\2\2\u00f8\u00e4\3\2\2\2\u00f8\u00e9\3\2\2\2\u00f8\u00ee"+
		"\3\2\2\2\u00f8\u00f3\3\2\2\2\u00f9-\3\2\2\2\u00fa\u00fd\5\60\31\2\u00fb"+
		"\u00fc\7\30\2\2\u00fc\u00fe\5.\30\2\u00fd\u00fb\3\2\2\2\u00fd\u00fe\3"+
		"\2\2\2\u00fe/\3\2\2\2\u00ff\u0102\5\62\32\2\u0100\u0101\7\31\2\2\u0101"+
		"\u0103\5\60\31\2\u0102\u0100\3\2\2\2\u0102\u0103\3\2\2\2\u0103\61\3\2"+
		"\2\2\u0104\u0107\5\64\33\2\u0105\u0106\7\32\2\2\u0106\u0108\5\62\32\2"+
		"\u0107\u0105\3\2\2\2\u0107\u0108\3\2\2\2\u0108\63\3\2\2\2\u0109\u010b"+
		"\7\33\2\2\u010a\u0109\3\2\2\2\u010a\u010b\3\2\2\2\u010b\u010c\3\2\2\2"+
		"\u010c\u0110\5\66\34\2\u010d\u010e\7\33\2\2\u010e\u0110\5\64\33\2\u010f"+
		"\u010a\3\2\2\2\u010f\u010d\3\2\2\2\u0110\65\3\2\2\2\u0111\u0116\58\35"+
		"\2\u0112\u0113\t\2\2\2\u0113\u0115\5\66\34\2\u0114\u0112\3\2\2\2\u0115"+
		"\u0118\3\2\2\2\u0116\u0114\3\2\2\2\u0116\u0117\3\2\2\2\u0117\67\3\2\2"+
		"\2\u0118\u0116\3\2\2\2\u0119\u011e\5:\36\2\u011a\u011b\t\3\2\2\u011b\u011d"+
		"\58\35\2\u011c\u011a\3\2\2\2\u011d\u0120\3\2\2\2\u011e\u011c\3\2\2\2\u011e"+
		"\u011f\3\2\2\2\u011f9\3\2\2\2\u0120\u011e\3\2\2\2\u0121\u0126\5<\37\2"+
		"\u0122\u0123\t\4\2\2\u0123\u0125\5:\36\2\u0124\u0122\3\2\2\2\u0125\u0128"+
		"\3\2\2\2\u0126\u0124\3\2\2\2\u0126\u0127\3\2\2\2\u0127;\3\2\2\2\u0128"+
		"\u0126\3\2\2\2\u0129\u012a\5> \2\u012a=\3\2\2\2\u012b\u0132\5B\"\2\u012c"+
		"\u0132\7\5\2\2\u012d\u0132\7\b\2\2\u012e\u0132\7\7\2\2\u012f\u0132\7<"+
		"\2\2\u0130\u0132\5F$\2\u0131\u012b\3\2\2\2\u0131\u012c\3\2\2\2\u0131\u012d"+
		"\3\2\2\2\u0131\u012e\3\2\2\2\u0131\u012f\3\2\2\2\u0131\u0130\3\2\2\2\u0132"+
		"?\3\2\2\2\u0133\u0135\78\2\2\u0134\u0133\3\2\2\2\u0134\u0135\3\2\2\2\u0135"+
		"\u0136\3\2\2\2\u0136\u0137\5\32\16\2\u0137A\3\2\2\2\u0138\u013d\5@!\2"+
		"\u0139\u013d\5\36\20\2\u013a\u013d\5J&\2\u013b\u013d\5D#\2\u013c\u0138"+
		"\3\2\2\2\u013c\u0139\3\2\2\2\u013c\u013a\3\2\2\2\u013c\u013b\3\2\2\2\u013d"+
		"\u0141\3\2\2\2\u013e\u0140\5L\'\2\u013f\u013e\3\2\2\2\u0140\u0143\3\2"+
		"\2\2\u0141\u013f\3\2\2\2\u0141\u0142\3\2\2\2\u0142C\3\2\2\2\u0143\u0141"+
		"\3\2\2\2\u0144\u0145\7\6\2\2\u0145E\3\2\2\2\u0146\u0147\7$\2\2\u0147\u0148"+
		"\5T+\2\u0148\u0149\7\f\2\2\u0149\u014a\5V,\2\u014a\u014b\7%\2\2\u014b"+
		"\u0154\3\2\2\2\u014c\u014d\7$\2\2\u014d\u014e\7(\2\2\u014e\u014f\5T+\2"+
		"\u014f\u0150\7\f\2\2\u0150\u0151\5V,\2\u0151\u0152\7%\2\2\u0152\u0154"+
		"\3\2\2\2\u0153\u0146\3\2\2\2\u0153\u014c\3\2\2\2\u0154G\3\2\2\2\u0155"+
		"\u0156\7\6\2\2\u0156I\3\2\2\2\u0157\u0158\5H%\2\u0158\u0159\5N(\2\u0159"+
		"K\3\2\2\2\u015a\u015d\7\f\2\2\u015b\u015e\5J&\2\u015c\u015e\5D#\2\u015d"+
		"\u015b\3\2\2\2\u015d\u015c\3\2\2\2\u015eM\3\2\2\2\u015f\u0168\7\"\2\2"+
		"\u0160\u0165\5P)\2\u0161\u0162\7\n\2\2\u0162\u0164\5P)\2\u0163\u0161\3"+
		"\2\2\2\u0164\u0167\3\2\2\2\u0165\u0163\3\2\2\2\u0165\u0166\3\2\2\2\u0166"+
		"\u0169\3\2\2\2\u0167\u0165\3\2\2\2\u0168\u0160\3\2\2\2\u0168\u0169\3\2"+
		"\2\2\u0169\u016a\3\2\2\2\u016a\u016b\7#\2\2\u016bO\3\2\2\2\u016c\u016d"+
		"\5R*\2\u016d\u016e\7\f\2\2\u016e\u016f\5 \21\2\u016fQ\3\2\2\2\u0170\u0171"+
		"\7\6\2\2\u0171S\3\2\2\2\u0172\u0173\7\6\2\2\u0173U\3\2\2\2\u0174\u0175"+
		"\7\6\2\2\u0175\u0176\7\17\2\2\u0176\u0177\7\6\2\2\u0177\u017a\3\2\2\2"+
		"\u0178\u0179\7=\2\2\u0179\u017b\7\6\2\2\u017a\u0178\3\2\2\2\u017a\u017b"+
		"\3\2\2\2\u017bW\3\2\2\2\u017c\u017d\7\64\2\2\u017d\u017e\7\13\2\2\u017e"+
		"Y\3\2\2\2\u017f\u0180\7,\2\2\u0180\u0181\5\22\n\2\u0181\u0184\5\4\3\2"+
		"\u0182\u0183\7.\2\2\u0183\u0185\5\4\3\2\u0184\u0182\3\2\2\2\u0184\u0185"+
		"\3\2\2\2\u0185[\3\2\2\2\u0186\u0187\7\60\2\2\u0187\u0188\7\"\2\2\u0188"+
		"\u0189\5\26\f\2\u0189\u018a\7\13\2\2\u018a\u018b\5\22\n\2\u018b\u018c"+
		"\7\13\2\2\u018c\u018d\5\26\f\2\u018d\u018e\7#\2\2\u018e\u018f\5\4\3\2"+
		"\u018f]\3\2\2\2\u0190\u0191\7/\2\2\u0191\u0192\5\22\n\2\u0192\u0193\5"+
		"\4\3\2\u0193_\3\2\2\2\u0194\u0195\7\61\2\2\u0195\u0196\5\n\6\2\u0196a"+
		"\3\2\2\2\u0197\u0198\7\65\2\2\u0198\u0199\5 \21\2\u0199\u019a\7\13\2\2"+
		"\u019ac\3\2\2\2\u019b\u019c\7\62\2\2\u019c\u019d\7\13\2\2\u019de\3\2\2"+
		"\2\u019e\u019f\7\63\2\2\u019f\u01a0\7\13\2\2\u01a0g\3\2\2\2\'k|\u0087"+
		"\u0093\u00a0\u00a5\u00c1\u00c6\u00cb\u00ce\u00d6\u00db\u00e0\u00e2\u00e7"+
		"\u00ec\u00f1\u00f6\u00f8\u00fd\u0102\u0107\u010a\u010f\u0116\u011e\u0126"+
		"\u0131\u0134\u013c\u0141\u0153\u015d\u0165\u0168\u017a\u0184";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}