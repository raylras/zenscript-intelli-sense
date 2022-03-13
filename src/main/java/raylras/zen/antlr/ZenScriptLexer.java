// Generated from D:/Projects/Forge/1.12.2/ZenServer/src/main/java/raylras/zen/antlr\ZenScriptLexer.g4 by ANTLR 4.9.2
package raylras.zen.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ZenScriptLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		VAR=1, VAL=2, GLOBAL=3, STATIC=4, IMPORT=5, FUNCTION=6, AS=7, TO=8, IN=9, 
		HAS=10, INSTANCEOF=11, ANY=12, BYTE=13, SHORT=14, INT=15, LONG=16, FLOAT=17, 
		DOUBLE=18, BOOL=19, VOID=20, STRING=21, IF=22, ELSE=23, FOR=24, DO=25, 
		WHILE=26, BREAK=27, CONTINUE=28, RETURN=29, FRIGGIN_CLASS=30, FRIGGIN_CONSTRUCTOR=31, 
		ZEN_CLASS=32, ZEN_CONSTRUCTOR=33, PAREN_OPEN=34, PAREN_CLOSE=35, BRACK_OPEN=36, 
		BRACK_CLOSE=37, BRACE_OPEN=38, BRACE_CLOSE=39, COMMA=40, DOT=41, SEMICOLON=42, 
		ADD=43, SUB=44, MUL=45, DIV=46, MOD=47, TILDE=48, NOT=49, LESS=50, GREATER=51, 
		XOR=52, COLON=53, QUEST=54, BACKTICK=55, DOLLAR=56, AND=57, OR=58, ASSIGN=59, 
		AND_AND=60, OR_OR=61, EQUAL=62, NOT_EQUAL=63, LESS_EQUAL=64, GREATER_EQUAL=65, 
		PLUS_ASSIGN=66, MINUS_ASSIGN=67, STAR_ASSIGN=68, DIV_ASSIGN=69, MOD_ASSIGN=70, 
		XOR_ASSIGN=71, AND_ASSIGN=72, OR_ASSIGN=73, TILDE_ASSIGN=74, DOT_DOT=75, 
		DECIMAL_LITERAL=76, HEX_LITERAL=77, FLOATING_LITERAL=78, BOOLEAN_LITERAL=79, 
		STRING_LITERAL=80, NULL_LITERAL=81, IDENTIFIER=82, WHITE_SPACE=83, BLOCK_COMMENT=84, 
		LINE_COMMENT=85, Preprocessor=86;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"VAR", "VAL", "GLOBAL", "STATIC", "IMPORT", "FUNCTION", "AS", "TO", "IN", 
			"HAS", "INSTANCEOF", "ANY", "BYTE", "SHORT", "INT", "LONG", "FLOAT", 
			"DOUBLE", "BOOL", "VOID", "STRING", "IF", "ELSE", "FOR", "DO", "WHILE", 
			"BREAK", "CONTINUE", "RETURN", "FRIGGIN_CLASS", "FRIGGIN_CONSTRUCTOR", 
			"ZEN_CLASS", "ZEN_CONSTRUCTOR", "PAREN_OPEN", "PAREN_CLOSE", "BRACK_OPEN", 
			"BRACK_CLOSE", "BRACE_OPEN", "BRACE_CLOSE", "COMMA", "DOT", "SEMICOLON", 
			"ADD", "SUB", "MUL", "DIV", "MOD", "TILDE", "NOT", "LESS", "GREATER", 
			"XOR", "COLON", "QUEST", "BACKTICK", "DOLLAR", "AND", "OR", "ASSIGN", 
			"AND_AND", "OR_OR", "EQUAL", "NOT_EQUAL", "LESS_EQUAL", "GREATER_EQUAL", 
			"PLUS_ASSIGN", "MINUS_ASSIGN", "STAR_ASSIGN", "DIV_ASSIGN", "MOD_ASSIGN", 
			"XOR_ASSIGN", "AND_ASSIGN", "OR_ASSIGN", "TILDE_ASSIGN", "DOT_DOT", "DECIMAL_LITERAL", 
			"HEX_LITERAL", "FLOATING_LITERAL", "BOOLEAN_LITERAL", "STRING_LITERAL", 
			"NULL_LITERAL", "IDENTIFIER", "WHITE_SPACE", "BLOCK_COMMENT", "LINE_COMMENT", 
			"Preprocessor", "EscapeSequence", "UnicodeCharacter", "Digits", "Digit", 
			"HexDigits", "HexDigit", "LetterOrDigit", "Letter"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'var'", "'val'", "'global'", "'static'", "'import'", "'function'", 
			"'as'", "'to'", "'in'", "'has'", "'instanceof'", "'any'", "'byte'", "'short'", 
			"'int'", "'long'", "'float'", "'double'", "'bool'", "'void'", "'string'", 
			"'if'", "'else'", "'for'", "'do'", "'while'", "'break'", "'continue'", 
			"'return'", "'frigginClass'", "'frigginConstructor'", "'zenClass'", "'zenConstructor'", 
			"'('", "')'", "'['", "']'", "'{'", "'}'", "','", "'.'", "';'", "'+'", 
			"'-'", "'*'", "'/'", "'%'", "'~'", "'!'", "'<'", "'>'", "'^'", "':'", 
			"'?'", "'`'", "'$'", "'&'", "'|'", "'='", "'&&'", "'||'", "'=='", "'!='", 
			"'<='", "'>='", "'+='", "'-='", "'*='", "'/='", "'%='", "'^='", "'&='", 
			"'|='", "'~='", "'..'", null, null, null, null, null, "'null'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "VAR", "VAL", "GLOBAL", "STATIC", "IMPORT", "FUNCTION", "AS", "TO", 
			"IN", "HAS", "INSTANCEOF", "ANY", "BYTE", "SHORT", "INT", "LONG", "FLOAT", 
			"DOUBLE", "BOOL", "VOID", "STRING", "IF", "ELSE", "FOR", "DO", "WHILE", 
			"BREAK", "CONTINUE", "RETURN", "FRIGGIN_CLASS", "FRIGGIN_CONSTRUCTOR", 
			"ZEN_CLASS", "ZEN_CONSTRUCTOR", "PAREN_OPEN", "PAREN_CLOSE", "BRACK_OPEN", 
			"BRACK_CLOSE", "BRACE_OPEN", "BRACE_CLOSE", "COMMA", "DOT", "SEMICOLON", 
			"ADD", "SUB", "MUL", "DIV", "MOD", "TILDE", "NOT", "LESS", "GREATER", 
			"XOR", "COLON", "QUEST", "BACKTICK", "DOLLAR", "AND", "OR", "ASSIGN", 
			"AND_AND", "OR_OR", "EQUAL", "NOT_EQUAL", "LESS_EQUAL", "GREATER_EQUAL", 
			"PLUS_ASSIGN", "MINUS_ASSIGN", "STAR_ASSIGN", "DIV_ASSIGN", "MOD_ASSIGN", 
			"XOR_ASSIGN", "AND_ASSIGN", "OR_ASSIGN", "TILDE_ASSIGN", "DOT_DOT", "DECIMAL_LITERAL", 
			"HEX_LITERAL", "FLOATING_LITERAL", "BOOLEAN_LITERAL", "STRING_LITERAL", 
			"NULL_LITERAL", "IDENTIFIER", "WHITE_SPACE", "BLOCK_COMMENT", "LINE_COMMENT", 
			"Preprocessor"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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


	public ZenScriptLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ZenScriptLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2X\u0287\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_\3"+
		"\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16"+
		"\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\21\3\21"+
		"\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\31"+
		"\3\31\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\36"+
		"\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37"+
		"\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3"+
		" \3 \3 \3 \3 \3!\3!\3!\3!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3"+
		"\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3"+
		")\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62"+
		"\3\63\3\63\3\64\3\64\3\65\3\65\3\66\3\66\3\67\3\67\38\38\39\39\3:\3:\3"+
		";\3;\3<\3<\3=\3=\3=\3>\3>\3>\3?\3?\3?\3@\3@\3@\3A\3A\3A\3B\3B\3B\3C\3"+
		"C\3C\3D\3D\3D\3E\3E\3E\3F\3F\3F\3G\3G\3G\3H\3H\3H\3I\3I\3I\3J\3J\3J\3"+
		"K\3K\3K\3L\3L\3L\3M\3M\3M\7M\u01fe\nM\fM\16M\u0201\13M\5M\u0203\nM\3M"+
		"\5M\u0206\nM\3N\3N\3N\3N\5N\u020c\nN\3O\3O\3O\3O\5O\u0212\nO\3P\3P\3P"+
		"\3P\3P\3P\3P\3P\3P\5P\u021d\nP\3Q\3Q\3Q\7Q\u0222\nQ\fQ\16Q\u0225\13Q\3"+
		"Q\3Q\3Q\3Q\7Q\u022b\nQ\fQ\16Q\u022e\13Q\3Q\5Q\u0231\nQ\3R\3R\3R\3R\3R"+
		"\3S\3S\7S\u023a\nS\fS\16S\u023d\13S\3T\6T\u0240\nT\rT\16T\u0241\3T\3T"+
		"\3U\3U\3U\3U\7U\u024a\nU\fU\16U\u024d\13U\3U\3U\3U\3U\3U\3V\3V\3V\3V\7"+
		"V\u0258\nV\fV\16V\u025b\13V\3V\3V\3W\3W\7W\u0261\nW\fW\16W\u0264\13W\3"+
		"W\3W\3X\3X\3X\5X\u026b\nX\3Y\3Y\3Y\3Y\3Y\3Y\3Y\3Z\6Z\u0275\nZ\rZ\16Z\u0276"+
		"\3[\3[\3\\\6\\\u027c\n\\\r\\\16\\\u027d\3]\3]\3^\3^\5^\u0284\n^\3_\3_"+
		"\3\u024b\2`\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33"+
		"\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67"+
		"\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65"+
		"i\66k\67m8o9q:s;u<w=y>{?}@\177A\u0081B\u0083C\u0085D\u0087E\u0089F\u008b"+
		"G\u008dH\u008fI\u0091J\u0093K\u0095L\u0097M\u0099N\u009bO\u009dP\u009f"+
		"Q\u00a1R\u00a3S\u00a5T\u00a7U\u00a9V\u00abW\u00adX\u00af\2\u00b1\2\u00b3"+
		"\2\u00b5\2\u00b7\2\u00b9\2\u00bb\2\u00bd\2\3\2\16\3\2\63;\3\2\62;\4\2"+
		"NNnn\4\2ZZzz\6\2FFHHffhh\6\2\f\f\17\17$$^^\5\2\13\f\17\17\"\"\4\2\f\f"+
		"\17\17\n\2$$))^^ddhhppttvv\4\2WWww\5\2\62;CHch\5\2C\\aac|\2\u0292\2\3"+
		"\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2"+
		"\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31"+
		"\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2"+
		"\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2"+
		"\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2"+
		"\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2"+
		"I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3"+
		"\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2"+
		"\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2"+
		"o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3"+
		"\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u0083\3\2\2\2\2\u0085"+
		"\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2\2\2\u008b\3\2\2\2\2\u008d\3\2\2"+
		"\2\2\u008f\3\2\2\2\2\u0091\3\2\2\2\2\u0093\3\2\2\2\2\u0095\3\2\2\2\2\u0097"+
		"\3\2\2\2\2\u0099\3\2\2\2\2\u009b\3\2\2\2\2\u009d\3\2\2\2\2\u009f\3\2\2"+
		"\2\2\u00a1\3\2\2\2\2\u00a3\3\2\2\2\2\u00a5\3\2\2\2\2\u00a7\3\2\2\2\2\u00a9"+
		"\3\2\2\2\2\u00ab\3\2\2\2\2\u00ad\3\2\2\2\3\u00bf\3\2\2\2\5\u00c3\3\2\2"+
		"\2\7\u00c7\3\2\2\2\t\u00ce\3\2\2\2\13\u00d5\3\2\2\2\r\u00dc\3\2\2\2\17"+
		"\u00e5\3\2\2\2\21\u00e8\3\2\2\2\23\u00eb\3\2\2\2\25\u00ee\3\2\2\2\27\u00f2"+
		"\3\2\2\2\31\u00fd\3\2\2\2\33\u0101\3\2\2\2\35\u0106\3\2\2\2\37\u010c\3"+
		"\2\2\2!\u0110\3\2\2\2#\u0115\3\2\2\2%\u011b\3\2\2\2\'\u0122\3\2\2\2)\u0127"+
		"\3\2\2\2+\u012c\3\2\2\2-\u0133\3\2\2\2/\u0136\3\2\2\2\61\u013b\3\2\2\2"+
		"\63\u013f\3\2\2\2\65\u0142\3\2\2\2\67\u0148\3\2\2\29\u014e\3\2\2\2;\u0157"+
		"\3\2\2\2=\u015e\3\2\2\2?\u016b\3\2\2\2A\u017e\3\2\2\2C\u0187\3\2\2\2E"+
		"\u0196\3\2\2\2G\u0198\3\2\2\2I\u019a\3\2\2\2K\u019c\3\2\2\2M\u019e\3\2"+
		"\2\2O\u01a0\3\2\2\2Q\u01a2\3\2\2\2S\u01a4\3\2\2\2U\u01a6\3\2\2\2W\u01a8"+
		"\3\2\2\2Y\u01aa\3\2\2\2[\u01ac\3\2\2\2]\u01ae\3\2\2\2_\u01b0\3\2\2\2a"+
		"\u01b2\3\2\2\2c\u01b4\3\2\2\2e\u01b6\3\2\2\2g\u01b8\3\2\2\2i\u01ba\3\2"+
		"\2\2k\u01bc\3\2\2\2m\u01be\3\2\2\2o\u01c0\3\2\2\2q\u01c2\3\2\2\2s\u01c4"+
		"\3\2\2\2u\u01c6\3\2\2\2w\u01c8\3\2\2\2y\u01ca\3\2\2\2{\u01cd\3\2\2\2}"+
		"\u01d0\3\2\2\2\177\u01d3\3\2\2\2\u0081\u01d6\3\2\2\2\u0083\u01d9\3\2\2"+
		"\2\u0085\u01dc\3\2\2\2\u0087\u01df\3\2\2\2\u0089\u01e2\3\2\2\2\u008b\u01e5"+
		"\3\2\2\2\u008d\u01e8\3\2\2\2\u008f\u01eb\3\2\2\2\u0091\u01ee\3\2\2\2\u0093"+
		"\u01f1\3\2\2\2\u0095\u01f4\3\2\2\2\u0097\u01f7\3\2\2\2\u0099\u0202\3\2"+
		"\2\2\u009b\u0207\3\2\2\2\u009d\u020d\3\2\2\2\u009f\u021c\3\2\2\2\u00a1"+
		"\u0230\3\2\2\2\u00a3\u0232\3\2\2\2\u00a5\u0237\3\2\2\2\u00a7\u023f\3\2"+
		"\2\2\u00a9\u0245\3\2\2\2\u00ab\u0253\3\2\2\2\u00ad\u025e\3\2\2\2\u00af"+
		"\u026a\3\2\2\2\u00b1\u026c\3\2\2\2\u00b3\u0274\3\2\2\2\u00b5\u0278\3\2"+
		"\2\2\u00b7\u027b\3\2\2\2\u00b9\u027f\3\2\2\2\u00bb\u0283\3\2\2\2\u00bd"+
		"\u0285\3\2\2\2\u00bf\u00c0\7x\2\2\u00c0\u00c1\7c\2\2\u00c1\u00c2\7t\2"+
		"\2\u00c2\4\3\2\2\2\u00c3\u00c4\7x\2\2\u00c4\u00c5\7c\2\2\u00c5\u00c6\7"+
		"n\2\2\u00c6\6\3\2\2\2\u00c7\u00c8\7i\2\2\u00c8\u00c9\7n\2\2\u00c9\u00ca"+
		"\7q\2\2\u00ca\u00cb\7d\2\2\u00cb\u00cc\7c\2\2\u00cc\u00cd\7n\2\2\u00cd"+
		"\b\3\2\2\2\u00ce\u00cf\7u\2\2\u00cf\u00d0\7v\2\2\u00d0\u00d1\7c\2\2\u00d1"+
		"\u00d2\7v\2\2\u00d2\u00d3\7k\2\2\u00d3\u00d4\7e\2\2\u00d4\n\3\2\2\2\u00d5"+
		"\u00d6\7k\2\2\u00d6\u00d7\7o\2\2\u00d7\u00d8\7r\2\2\u00d8\u00d9\7q\2\2"+
		"\u00d9\u00da\7t\2\2\u00da\u00db\7v\2\2\u00db\f\3\2\2\2\u00dc\u00dd\7h"+
		"\2\2\u00dd\u00de\7w\2\2\u00de\u00df\7p\2\2\u00df\u00e0\7e\2\2\u00e0\u00e1"+
		"\7v\2\2\u00e1\u00e2\7k\2\2\u00e2\u00e3\7q\2\2\u00e3\u00e4\7p\2\2\u00e4"+
		"\16\3\2\2\2\u00e5\u00e6\7c\2\2\u00e6\u00e7\7u\2\2\u00e7\20\3\2\2\2\u00e8"+
		"\u00e9\7v\2\2\u00e9\u00ea\7q\2\2\u00ea\22\3\2\2\2\u00eb\u00ec\7k\2\2\u00ec"+
		"\u00ed\7p\2\2\u00ed\24\3\2\2\2\u00ee\u00ef\7j\2\2\u00ef\u00f0\7c\2\2\u00f0"+
		"\u00f1\7u\2\2\u00f1\26\3\2\2\2\u00f2\u00f3\7k\2\2\u00f3\u00f4\7p\2\2\u00f4"+
		"\u00f5\7u\2\2\u00f5\u00f6\7v\2\2\u00f6\u00f7\7c\2\2\u00f7\u00f8\7p\2\2"+
		"\u00f8\u00f9\7e\2\2\u00f9\u00fa\7g\2\2\u00fa\u00fb\7q\2\2\u00fb\u00fc"+
		"\7h\2\2\u00fc\30\3\2\2\2\u00fd\u00fe\7c\2\2\u00fe\u00ff\7p\2\2\u00ff\u0100"+
		"\7{\2\2\u0100\32\3\2\2\2\u0101\u0102\7d\2\2\u0102\u0103\7{\2\2\u0103\u0104"+
		"\7v\2\2\u0104\u0105\7g\2\2\u0105\34\3\2\2\2\u0106\u0107\7u\2\2\u0107\u0108"+
		"\7j\2\2\u0108\u0109\7q\2\2\u0109\u010a\7t\2\2\u010a\u010b\7v\2\2\u010b"+
		"\36\3\2\2\2\u010c\u010d\7k\2\2\u010d\u010e\7p\2\2\u010e\u010f\7v\2\2\u010f"+
		" \3\2\2\2\u0110\u0111\7n\2\2\u0111\u0112\7q\2\2\u0112\u0113\7p\2\2\u0113"+
		"\u0114\7i\2\2\u0114\"\3\2\2\2\u0115\u0116\7h\2\2\u0116\u0117\7n\2\2\u0117"+
		"\u0118\7q\2\2\u0118\u0119\7c\2\2\u0119\u011a\7v\2\2\u011a$\3\2\2\2\u011b"+
		"\u011c\7f\2\2\u011c\u011d\7q\2\2\u011d\u011e\7w\2\2\u011e\u011f\7d\2\2"+
		"\u011f\u0120\7n\2\2\u0120\u0121\7g\2\2\u0121&\3\2\2\2\u0122\u0123\7d\2"+
		"\2\u0123\u0124\7q\2\2\u0124\u0125\7q\2\2\u0125\u0126\7n\2\2\u0126(\3\2"+
		"\2\2\u0127\u0128\7x\2\2\u0128\u0129\7q\2\2\u0129\u012a\7k\2\2\u012a\u012b"+
		"\7f\2\2\u012b*\3\2\2\2\u012c\u012d\7u\2\2\u012d\u012e\7v\2\2\u012e\u012f"+
		"\7t\2\2\u012f\u0130\7k\2\2\u0130\u0131\7p\2\2\u0131\u0132\7i\2\2\u0132"+
		",\3\2\2\2\u0133\u0134\7k\2\2\u0134\u0135\7h\2\2\u0135.\3\2\2\2\u0136\u0137"+
		"\7g\2\2\u0137\u0138\7n\2\2\u0138\u0139\7u\2\2\u0139\u013a\7g\2\2\u013a"+
		"\60\3\2\2\2\u013b\u013c\7h\2\2\u013c\u013d\7q\2\2\u013d\u013e\7t\2\2\u013e"+
		"\62\3\2\2\2\u013f\u0140\7f\2\2\u0140\u0141\7q\2\2\u0141\64\3\2\2\2\u0142"+
		"\u0143\7y\2\2\u0143\u0144\7j\2\2\u0144\u0145\7k\2\2\u0145\u0146\7n\2\2"+
		"\u0146\u0147\7g\2\2\u0147\66\3\2\2\2\u0148\u0149\7d\2\2\u0149\u014a\7"+
		"t\2\2\u014a\u014b\7g\2\2\u014b\u014c\7c\2\2\u014c\u014d\7m\2\2\u014d8"+
		"\3\2\2\2\u014e\u014f\7e\2\2\u014f\u0150\7q\2\2\u0150\u0151\7p\2\2\u0151"+
		"\u0152\7v\2\2\u0152\u0153\7k\2\2\u0153\u0154\7p\2\2\u0154\u0155\7w\2\2"+
		"\u0155\u0156\7g\2\2\u0156:\3\2\2\2\u0157\u0158\7t\2\2\u0158\u0159\7g\2"+
		"\2\u0159\u015a\7v\2\2\u015a\u015b\7w\2\2\u015b\u015c\7t\2\2\u015c\u015d"+
		"\7p\2\2\u015d<\3\2\2\2\u015e\u015f\7h\2\2\u015f\u0160\7t\2\2\u0160\u0161"+
		"\7k\2\2\u0161\u0162\7i\2\2\u0162\u0163\7i\2\2\u0163\u0164\7k\2\2\u0164"+
		"\u0165\7p\2\2\u0165\u0166\7E\2\2\u0166\u0167\7n\2\2\u0167\u0168\7c\2\2"+
		"\u0168\u0169\7u\2\2\u0169\u016a\7u\2\2\u016a>\3\2\2\2\u016b\u016c\7h\2"+
		"\2\u016c\u016d\7t\2\2\u016d\u016e\7k\2\2\u016e\u016f\7i\2\2\u016f\u0170"+
		"\7i\2\2\u0170\u0171\7k\2\2\u0171\u0172\7p\2\2\u0172\u0173\7E\2\2\u0173"+
		"\u0174\7q\2\2\u0174\u0175\7p\2\2\u0175\u0176\7u\2\2\u0176\u0177\7v\2\2"+
		"\u0177\u0178\7t\2\2\u0178\u0179\7w\2\2\u0179\u017a\7e\2\2\u017a\u017b"+
		"\7v\2\2\u017b\u017c\7q\2\2\u017c\u017d\7t\2\2\u017d@\3\2\2\2\u017e\u017f"+
		"\7|\2\2\u017f\u0180\7g\2\2\u0180\u0181\7p\2\2\u0181\u0182\7E\2\2\u0182"+
		"\u0183\7n\2\2\u0183\u0184\7c\2\2\u0184\u0185\7u\2\2\u0185\u0186\7u\2\2"+
		"\u0186B\3\2\2\2\u0187\u0188\7|\2\2\u0188\u0189\7g\2\2\u0189\u018a\7p\2"+
		"\2\u018a\u018b\7E\2\2\u018b\u018c\7q\2\2\u018c\u018d\7p\2\2\u018d\u018e"+
		"\7u\2\2\u018e\u018f\7v\2\2\u018f\u0190\7t\2\2\u0190\u0191\7w\2\2\u0191"+
		"\u0192\7e\2\2\u0192\u0193\7v\2\2\u0193\u0194\7q\2\2\u0194\u0195\7t\2\2"+
		"\u0195D\3\2\2\2\u0196\u0197\7*\2\2\u0197F\3\2\2\2\u0198\u0199\7+\2\2\u0199"+
		"H\3\2\2\2\u019a\u019b\7]\2\2\u019bJ\3\2\2\2\u019c\u019d\7_\2\2\u019dL"+
		"\3\2\2\2\u019e\u019f\7}\2\2\u019fN\3\2\2\2\u01a0\u01a1\7\177\2\2\u01a1"+
		"P\3\2\2\2\u01a2\u01a3\7.\2\2\u01a3R\3\2\2\2\u01a4\u01a5\7\60\2\2\u01a5"+
		"T\3\2\2\2\u01a6\u01a7\7=\2\2\u01a7V\3\2\2\2\u01a8\u01a9\7-\2\2\u01a9X"+
		"\3\2\2\2\u01aa\u01ab\7/\2\2\u01abZ\3\2\2\2\u01ac\u01ad\7,\2\2\u01ad\\"+
		"\3\2\2\2\u01ae\u01af\7\61\2\2\u01af^\3\2\2\2\u01b0\u01b1\7\'\2\2\u01b1"+
		"`\3\2\2\2\u01b2\u01b3\7\u0080\2\2\u01b3b\3\2\2\2\u01b4\u01b5\7#\2\2\u01b5"+
		"d\3\2\2\2\u01b6\u01b7\7>\2\2\u01b7f\3\2\2\2\u01b8\u01b9\7@\2\2\u01b9h"+
		"\3\2\2\2\u01ba\u01bb\7`\2\2\u01bbj\3\2\2\2\u01bc\u01bd\7<\2\2\u01bdl\3"+
		"\2\2\2\u01be\u01bf\7A\2\2\u01bfn\3\2\2\2\u01c0\u01c1\7b\2\2\u01c1p\3\2"+
		"\2\2\u01c2\u01c3\7&\2\2\u01c3r\3\2\2\2\u01c4\u01c5\7(\2\2\u01c5t\3\2\2"+
		"\2\u01c6\u01c7\7~\2\2\u01c7v\3\2\2\2\u01c8\u01c9\7?\2\2\u01c9x\3\2\2\2"+
		"\u01ca\u01cb\7(\2\2\u01cb\u01cc\7(\2\2\u01ccz\3\2\2\2\u01cd\u01ce\7~\2"+
		"\2\u01ce\u01cf\7~\2\2\u01cf|\3\2\2\2\u01d0\u01d1\7?\2\2\u01d1\u01d2\7"+
		"?\2\2\u01d2~\3\2\2\2\u01d3\u01d4\7#\2\2\u01d4\u01d5\7?\2\2\u01d5\u0080"+
		"\3\2\2\2\u01d6\u01d7\7>\2\2\u01d7\u01d8\7?\2\2\u01d8\u0082\3\2\2\2\u01d9"+
		"\u01da\7@\2\2\u01da\u01db\7?\2\2\u01db\u0084\3\2\2\2\u01dc\u01dd\7-\2"+
		"\2\u01dd\u01de\7?\2\2\u01de\u0086\3\2\2\2\u01df\u01e0\7/\2\2\u01e0\u01e1"+
		"\7?\2\2\u01e1\u0088\3\2\2\2\u01e2\u01e3\7,\2\2\u01e3\u01e4\7?\2\2\u01e4"+
		"\u008a\3\2\2\2\u01e5\u01e6\7\61\2\2\u01e6\u01e7\7?\2\2\u01e7\u008c\3\2"+
		"\2\2\u01e8\u01e9\7\'\2\2\u01e9\u01ea\7?\2\2\u01ea\u008e\3\2\2\2\u01eb"+
		"\u01ec\7`\2\2\u01ec\u01ed\7?\2\2\u01ed\u0090\3\2\2\2\u01ee\u01ef\7(\2"+
		"\2\u01ef\u01f0\7?\2\2\u01f0\u0092\3\2\2\2\u01f1\u01f2\7~\2\2\u01f2\u01f3"+
		"\7?\2\2\u01f3\u0094\3\2\2\2\u01f4\u01f5\7\u0080\2\2\u01f5\u01f6\7?\2\2"+
		"\u01f6\u0096\3\2\2\2\u01f7\u01f8\7\60\2\2\u01f8\u01f9\7\60\2\2\u01f9\u0098"+
		"\3\2\2\2\u01fa\u0203\7\62\2\2\u01fb\u01ff\t\2\2\2\u01fc\u01fe\t\3\2\2"+
		"\u01fd\u01fc\3\2\2\2\u01fe\u0201\3\2\2\2\u01ff\u01fd\3\2\2\2\u01ff\u0200"+
		"\3\2\2\2\u0200\u0203\3\2\2\2\u0201\u01ff\3\2\2\2\u0202\u01fa\3\2\2\2\u0202"+
		"\u01fb\3\2\2\2\u0203\u0205\3\2\2\2\u0204\u0206\t\4\2\2\u0205\u0204\3\2"+
		"\2\2\u0205\u0206\3\2\2\2\u0206\u009a\3\2\2\2\u0207\u0208\7\62\2\2\u0208"+
		"\u0209\t\5\2\2\u0209\u020b\5\u00b7\\\2\u020a\u020c\t\4\2\2\u020b\u020a"+
		"\3\2\2\2\u020b\u020c\3\2\2\2\u020c\u009c\3\2\2\2\u020d\u020e\5\u00b3Z"+
		"\2\u020e\u020f\7\60\2\2\u020f\u0211\5\u00b3Z\2\u0210\u0212\t\6\2\2\u0211"+
		"\u0210\3\2\2\2\u0211\u0212\3\2\2\2\u0212\u009e\3\2\2\2\u0213\u0214\7v"+
		"\2\2\u0214\u0215\7t\2\2\u0215\u0216\7w\2\2\u0216\u021d\7g\2\2\u0217\u0218"+
		"\7h\2\2\u0218\u0219\7c\2\2\u0219\u021a\7n\2\2\u021a\u021b\7u\2\2\u021b"+
		"\u021d\7g\2\2\u021c\u0213\3\2\2\2\u021c\u0217\3\2\2\2\u021d\u00a0\3\2"+
		"\2\2\u021e\u0223\7$\2\2\u021f\u0222\n\7\2\2\u0220\u0222\5\u00afX\2\u0221"+
		"\u021f\3\2\2\2\u0221\u0220\3\2\2\2\u0222\u0225\3\2\2\2\u0223\u0221\3\2"+
		"\2\2\u0223\u0224\3\2\2\2\u0224\u0226\3\2\2\2\u0225\u0223\3\2\2\2\u0226"+
		"\u0231\7$\2\2\u0227\u022c\7)\2\2\u0228\u022b\n\7\2\2\u0229\u022b\5\u00af"+
		"X\2\u022a\u0228\3\2\2\2\u022a\u0229\3\2\2\2\u022b\u022e\3\2\2\2\u022c"+
		"\u022a\3\2\2\2\u022c\u022d\3\2\2\2\u022d\u022f\3\2\2\2\u022e\u022c\3\2"+
		"\2\2\u022f\u0231\7)\2\2\u0230\u021e\3\2\2\2\u0230\u0227\3\2\2\2\u0231"+
		"\u00a2\3\2\2\2\u0232\u0233\7p\2\2\u0233\u0234\7w\2\2\u0234\u0235\7n\2"+
		"\2\u0235\u0236\7n\2\2\u0236\u00a4\3\2\2\2\u0237\u023b\5\u00bd_\2\u0238"+
		"\u023a\5\u00bb^\2\u0239\u0238\3\2\2\2\u023a\u023d\3\2\2\2\u023b\u0239"+
		"\3\2\2\2\u023b\u023c\3\2\2\2\u023c\u00a6\3\2\2\2\u023d\u023b\3\2\2\2\u023e"+
		"\u0240\t\b\2\2\u023f\u023e\3\2\2\2\u0240\u0241\3\2\2\2\u0241\u023f\3\2"+
		"\2\2\u0241\u0242\3\2\2\2\u0242\u0243\3\2\2\2\u0243\u0244\bT\2\2\u0244"+
		"\u00a8\3\2\2\2\u0245\u0246\7\61\2\2\u0246\u0247\7,\2\2\u0247\u024b\3\2"+
		"\2\2\u0248\u024a\13\2\2\2\u0249\u0248\3\2\2\2\u024a\u024d\3\2\2\2\u024b"+
		"\u024c\3\2\2\2\u024b\u0249\3\2\2\2\u024c\u024e\3\2\2\2\u024d\u024b\3\2"+
		"\2\2\u024e\u024f\7,\2\2\u024f\u0250\7\61\2\2\u0250\u0251\3\2\2\2\u0251"+
		"\u0252\bU\2\2\u0252\u00aa\3\2\2\2\u0253\u0254\7\61\2\2\u0254\u0255\7\61"+
		"\2\2\u0255\u0259\3\2\2\2\u0256\u0258\n\t\2\2\u0257\u0256\3\2\2\2\u0258"+
		"\u025b\3\2\2\2\u0259\u0257\3\2\2\2\u0259\u025a\3\2\2\2\u025a\u025c\3\2"+
		"\2\2\u025b\u0259\3\2\2\2\u025c\u025d\bV\2\2\u025d\u00ac\3\2\2\2\u025e"+
		"\u0262\7%\2\2\u025f\u0261\n\t\2\2\u0260\u025f\3\2\2\2\u0261\u0264\3\2"+
		"\2\2\u0262\u0260\3\2\2\2\u0262\u0263\3\2\2\2\u0263\u0265\3\2\2\2\u0264"+
		"\u0262\3\2\2\2\u0265\u0266\bW\2\2\u0266\u00ae\3\2\2\2\u0267\u0268\7^\2"+
		"\2\u0268\u026b\t\n\2\2\u0269\u026b\5\u00b1Y\2\u026a\u0267\3\2\2\2\u026a"+
		"\u0269\3\2\2\2\u026b\u00b0\3\2\2\2\u026c\u026d\7^\2\2\u026d\u026e\t\13"+
		"\2\2\u026e\u026f\5\u00b9]\2\u026f\u0270\5\u00b9]\2\u0270\u0271\5\u00b9"+
		"]\2\u0271\u0272\5\u00b9]\2\u0272\u00b2\3\2\2\2\u0273\u0275\5\u00b5[\2"+
		"\u0274\u0273\3\2\2\2\u0275\u0276\3\2\2\2\u0276\u0274\3\2\2\2\u0276\u0277"+
		"\3\2\2\2\u0277\u00b4\3\2\2\2\u0278\u0279\t\3\2\2\u0279\u00b6\3\2\2\2\u027a"+
		"\u027c\5\u00b9]\2\u027b\u027a\3\2\2\2\u027c\u027d\3\2\2\2\u027d\u027b"+
		"\3\2\2\2\u027d\u027e\3\2\2\2\u027e\u00b8\3\2\2\2\u027f\u0280\t\f\2\2\u0280"+
		"\u00ba\3\2\2\2\u0281\u0284\5\u00bd_\2\u0282\u0284\t\3\2\2\u0283\u0281"+
		"\3\2\2\2\u0283\u0282\3\2\2\2\u0284\u00bc\3\2\2\2\u0285\u0286\t\r\2\2\u0286"+
		"\u00be\3\2\2\2\27\2\u01ff\u0202\u0205\u020b\u0211\u021c\u0221\u0223\u022a"+
		"\u022c\u0230\u023b\u0241\u024b\u0259\u0262\u026a\u0276\u027d\u0283\3\2"+
		"\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}