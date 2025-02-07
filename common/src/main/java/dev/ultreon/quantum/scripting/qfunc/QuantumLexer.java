// Generated from QuantumLexer.g4 by ANTLR 4.5
package dev.ultreon.quantum.scripting.qfunc;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class QuantumLexer extends Lexer {
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
		STOP=50, RETURN=51, INPUT=52, PERSIST=53, PRESENT=54, DIRECIVE=55;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"WHITESPACE", "DIRECTIVE", "STRING", "IDENTIFIER", "FLOATING_POINT", "NUMBER", 
		"COMMENT", "COMMA", "SEMICOLON", "COLON", "ARROW", "STAR", "SLASH", "PERCENT", 
		"ASSIGN", "LESS_THAN", "GREATER_THAN", "LESS_THAN_EQUAL", "GREATER_THAN_EQUAL", 
		"EQUAL", "NOT_EQUAL", "BITWISE_AND", "BITWISE_OR", "BITWISE_XOR", "BITWISE_NOT", 
		"SHIFT_LEFT", "SHIFT_RIGHT", "PLUS", "MINUS", "DOLLAR", "AT", "LPAREN", 
		"RPAREN", "LBRACKET", "RBRACKET", "LBRACE", "RBRACE", "HASH", "NOT", "PERSIST_DIRECTIVE", 
		"INPUT_DIRECTIVE", "IF", "IS", "ELSE", "WHILE", "FOR", "LOOP", "BREAK", 
		"CONTINUE", "STOP", "RETURN", "INPUT", "PERSIST", "PRESENT", "DIRECIVE"
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
		"CONTINUE", "STOP", "RETURN", "INPUT", "PERSIST", "PRESENT", "DIRECIVE"
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


	public QuantumLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "QuantumLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\29\u014d\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\3\2\3\2\3\3\3\3\3\3\7\3w\n\3\f"+
		"\3\16\3z\13\3\3\4\3\4\7\4~\n\4\f\4\16\4\u0081\13\4\3\4\3\4\3\5\3\5\7\5"+
		"\u0087\n\5\f\5\16\5\u008a\13\5\3\5\5\5\u008d\n\5\3\6\6\6\u0090\n\6\r\6"+
		"\16\6\u0091\3\6\3\6\3\6\6\6\u0097\n\6\r\6\16\6\u0098\5\6\u009b\n\6\3\7"+
		"\6\7\u009e\n\7\r\7\16\7\u009f\3\b\3\b\3\b\3\b\7\b\u00a6\n\b\f\b\16\b\u00a9"+
		"\13\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17"+
		"\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25"+
		"\3\25\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33"+
		"\3\33\3\34\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\""+
		"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3)\3)\3)\3)\3)\3)\3*\3*\3"+
		"*\3*\3*\3*\3+\3+\3+\3,\3,\3,\3-\3-\3-\3-\3-\3.\3.\3.\3.\3.\3.\3/\3/\3"+
		"/\3/\3\60\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\61\3\62\3\62"+
		"\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\63\3\64\3\64"+
		"\3\64\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\65\3\65\3\65\3\66\3\66\3\66"+
		"\3\66\3\66\3\66\3\66\3\66\3\67\3\67\3\67\3\67\3\67\3\67\3\67\38\38\58"+
		"\u014c\n8\2\29\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16"+
		"\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34"+
		"\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g"+
		"\65i\66k\67m8o9\3\2\n\5\2\13\f\17\17\"\"\5\2C\\aac|\6\2\62;C\\aac|\7\2"+
		"\f\f\17\17))^^``\7\2//\62;C\\aac|\5\2\62;C\\c|\3\2\62;\5\2\f\f\17\17`"+
		"`\u0156\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2"+
		"\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3"+
		"\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2"+
		"\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2"+
		"/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2"+
		"\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2"+
		"G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3"+
		"\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2"+
		"\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2"+
		"m\3\2\2\2\2o\3\2\2\2\3q\3\2\2\2\5s\3\2\2\2\7{\3\2\2\2\t\u0084\3\2\2\2"+
		"\13\u008f\3\2\2\2\r\u009d\3\2\2\2\17\u00a1\3\2\2\2\21\u00aa\3\2\2\2\23"+
		"\u00ac\3\2\2\2\25\u00ae\3\2\2\2\27\u00b0\3\2\2\2\31\u00b3\3\2\2\2\33\u00b5"+
		"\3\2\2\2\35\u00b7\3\2\2\2\37\u00b9\3\2\2\2!\u00bb\3\2\2\2#\u00bd\3\2\2"+
		"\2%\u00bf\3\2\2\2\'\u00c2\3\2\2\2)\u00c5\3\2\2\2+\u00c8\3\2\2\2-\u00cb"+
		"\3\2\2\2/\u00cd\3\2\2\2\61\u00cf\3\2\2\2\63\u00d1\3\2\2\2\65\u00d3\3\2"+
		"\2\2\67\u00d6\3\2\2\29\u00d9\3\2\2\2;\u00db\3\2\2\2=\u00dd\3\2\2\2?\u00df"+
		"\3\2\2\2A\u00e1\3\2\2\2C\u00e3\3\2\2\2E\u00e5\3\2\2\2G\u00e7\3\2\2\2I"+
		"\u00e9\3\2\2\2K\u00eb\3\2\2\2M\u00ed\3\2\2\2O\u00ef\3\2\2\2Q\u00f1\3\2"+
		"\2\2S\u00f9\3\2\2\2U\u00ff\3\2\2\2W\u0102\3\2\2\2Y\u0105\3\2\2\2[\u010a"+
		"\3\2\2\2]\u0110\3\2\2\2_\u0114\3\2\2\2a\u0119\3\2\2\2c\u011f\3\2\2\2e"+
		"\u0128\3\2\2\2g\u012d\3\2\2\2i\u0134\3\2\2\2k\u013a\3\2\2\2m\u0142\3\2"+
		"\2\2o\u014b\3\2\2\2qr\t\2\2\2r\4\3\2\2\2st\7%\2\2tx\t\3\2\2uw\t\4\2\2"+
		"vu\3\2\2\2wz\3\2\2\2xv\3\2\2\2xy\3\2\2\2y\6\3\2\2\2zx\3\2\2\2{\177\7)"+
		"\2\2|~\t\5\2\2}|\3\2\2\2~\u0081\3\2\2\2\177}\3\2\2\2\177\u0080\3\2\2\2"+
		"\u0080\u0082\3\2\2\2\u0081\177\3\2\2\2\u0082\u0083\7)\2\2\u0083\b\3\2"+
		"\2\2\u0084\u008c\t\3\2\2\u0085\u0087\t\6\2\2\u0086\u0085\3\2\2\2\u0087"+
		"\u008a\3\2\2\2\u0088\u0086\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u008b\3\2"+
		"\2\2\u008a\u0088\3\2\2\2\u008b\u008d\t\7\2\2\u008c\u0088\3\2\2\2\u008c"+
		"\u008d\3\2\2\2\u008d\n\3\2\2\2\u008e\u0090\t\b\2\2\u008f\u008e\3\2\2\2"+
		"\u0090\u0091\3\2\2\2\u0091\u008f\3\2\2\2\u0091\u0092\3\2\2\2\u0092\u009a"+
		"\3\2\2\2\u0093\u0094\7^\2\2\u0094\u0096\13\2\2\2\u0095\u0097\t\b\2\2\u0096"+
		"\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098\u0096\3\2\2\2\u0098\u0099\3\2"+
		"\2\2\u0099\u009b\3\2\2\2\u009a\u0093\3\2\2\2\u009a\u009b\3\2\2\2\u009b"+
		"\f\3\2\2\2\u009c\u009e\t\b\2\2\u009d\u009c\3\2\2\2\u009e\u009f\3\2\2\2"+
		"\u009f\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\16\3\2\2\2\u00a1\u00a2"+
		"\7\61\2\2\u00a2\u00a3\7\61\2\2\u00a3\u00a7\3\2\2\2\u00a4\u00a6\t\t\2\2"+
		"\u00a5\u00a4\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a8"+
		"\3\2\2\2\u00a8\20\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa\u00ab\7.\2\2\u00ab"+
		"\22\3\2\2\2\u00ac\u00ad\7=\2\2\u00ad\24\3\2\2\2\u00ae\u00af\7<\2\2\u00af"+
		"\26\3\2\2\2\u00b0\u00b1\7/\2\2\u00b1\u00b2\7@\2\2\u00b2\30\3\2\2\2\u00b3"+
		"\u00b4\7,\2\2\u00b4\32\3\2\2\2\u00b5\u00b6\7\61\2\2\u00b6\34\3\2\2\2\u00b7"+
		"\u00b8\7\'\2\2\u00b8\36\3\2\2\2\u00b9\u00ba\7?\2\2\u00ba \3\2\2\2\u00bb"+
		"\u00bc\7>\2\2\u00bc\"\3\2\2\2\u00bd\u00be\7@\2\2\u00be$\3\2\2\2\u00bf"+
		"\u00c0\7>\2\2\u00c0\u00c1\7?\2\2\u00c1&\3\2\2\2\u00c2\u00c3\7@\2\2\u00c3"+
		"\u00c4\7?\2\2\u00c4(\3\2\2\2\u00c5\u00c6\7?\2\2\u00c6\u00c7\7?\2\2\u00c7"+
		"*\3\2\2\2\u00c8\u00c9\7#\2\2\u00c9\u00ca\7?\2\2\u00ca,\3\2\2\2\u00cb\u00cc"+
		"\7(\2\2\u00cc.\3\2\2\2\u00cd\u00ce\7~\2\2\u00ce\60\3\2\2\2\u00cf\u00d0"+
		"\7`\2\2\u00d0\62\3\2\2\2\u00d1\u00d2\7\u0080\2\2\u00d2\64\3\2\2\2\u00d3"+
		"\u00d4\7>\2\2\u00d4\u00d5\7>\2\2\u00d5\66\3\2\2\2\u00d6\u00d7\7@\2\2\u00d7"+
		"\u00d8\7@\2\2\u00d88\3\2\2\2\u00d9\u00da\7-\2\2\u00da:\3\2\2\2\u00db\u00dc"+
		"\7/\2\2\u00dc<\3\2\2\2\u00dd\u00de\7&\2\2\u00de>\3\2\2\2\u00df\u00e0\7"+
		"B\2\2\u00e0@\3\2\2\2\u00e1\u00e2\7*\2\2\u00e2B\3\2\2\2\u00e3\u00e4\7+"+
		"\2\2\u00e4D\3\2\2\2\u00e5\u00e6\7]\2\2\u00e6F\3\2\2\2\u00e7\u00e8\7_\2"+
		"\2\u00e8H\3\2\2\2\u00e9\u00ea\7}\2\2\u00eaJ\3\2\2\2\u00eb\u00ec\7\177"+
		"\2\2\u00ecL\3\2\2\2\u00ed\u00ee\7%\2\2\u00eeN\3\2\2\2\u00ef\u00f0\7#\2"+
		"\2\u00f0P\3\2\2\2\u00f1\u00f2\7r\2\2\u00f2\u00f3\7g\2\2\u00f3\u00f4\7"+
		"t\2\2\u00f4\u00f5\7u\2\2\u00f5\u00f6\7k\2\2\u00f6\u00f7\7u\2\2\u00f7\u00f8"+
		"\7v\2\2\u00f8R\3\2\2\2\u00f9\u00fa\7k\2\2\u00fa\u00fb\7p\2\2\u00fb\u00fc"+
		"\7r\2\2\u00fc\u00fd\7w\2\2\u00fd\u00fe\7v\2\2\u00feT\3\2\2\2\u00ff\u0100"+
		"\7k\2\2\u0100\u0101\7h\2\2\u0101V\3\2\2\2\u0102\u0103\7k\2\2\u0103\u0104"+
		"\7u\2\2\u0104X\3\2\2\2\u0105\u0106\7g\2\2\u0106\u0107\7n\2\2\u0107\u0108"+
		"\7u\2\2\u0108\u0109\7g\2\2\u0109Z\3\2\2\2\u010a\u010b\7y\2\2\u010b\u010c"+
		"\7j\2\2\u010c\u010d\7k\2\2\u010d\u010e\7n\2\2\u010e\u010f\7g\2\2\u010f"+
		"\\\3\2\2\2\u0110\u0111\7h\2\2\u0111\u0112\7q\2\2\u0112\u0113\7t\2\2\u0113"+
		"^\3\2\2\2\u0114\u0115\7n\2\2\u0115\u0116\7q\2\2\u0116\u0117\7q\2\2\u0117"+
		"\u0118\7r\2\2\u0118`\3\2\2\2\u0119\u011a\7d\2\2\u011a\u011b\7t\2\2\u011b"+
		"\u011c\7g\2\2\u011c\u011d\7c\2\2\u011d\u011e\7m\2\2\u011eb\3\2\2\2\u011f"+
		"\u0120\7e\2\2\u0120\u0121\7q\2\2\u0121\u0122\7p\2\2\u0122\u0123\7v\2\2"+
		"\u0123\u0124\7k\2\2\u0124\u0125\7p\2\2\u0125\u0126\7w\2\2\u0126\u0127"+
		"\7g\2\2\u0127d\3\2\2\2\u0128\u0129\7u\2\2\u0129\u012a\7v\2\2\u012a\u012b"+
		"\7q\2\2\u012b\u012c\7r\2\2\u012cf\3\2\2\2\u012d\u012e\7t\2\2\u012e\u012f"+
		"\7g\2\2\u012f\u0130\7v\2\2\u0130\u0131\7w\2\2\u0131\u0132\7t\2\2\u0132"+
		"\u0133\7p\2\2\u0133h\3\2\2\2\u0134\u0135\7k\2\2\u0135\u0136\7p\2\2\u0136"+
		"\u0137\7r\2\2\u0137\u0138\7w\2\2\u0138\u0139\7v\2\2\u0139j\3\2\2\2\u013a"+
		"\u013b\7r\2\2\u013b\u013c\7g\2\2\u013c\u013d\7t\2\2\u013d\u013e\7u\2\2"+
		"\u013e\u013f\7k\2\2\u013f\u0140\7u\2\2\u0140\u0141\7v\2\2\u0141l\3\2\2"+
		"\2\u0142\u0143\7t\2\2\u0143\u0144\7g\2\2\u0144\u0145\7u\2\2\u0145\u0146"+
		"\7g\2\2\u0146\u0147\7p\2\2\u0147\u0148\7v\2\2\u0148n\3\2\2\2\u0149\u014c"+
		"\5i\65\2\u014a\u014c\5k\66\2\u014b\u0149\3\2\2\2\u014b\u014a\3\2\2\2\u014c"+
		"p\3\2\2\2\16\2x}\177\u0088\u008c\u0091\u0098\u009a\u009f\u00a7\u014b\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}