// Generated from D:/Projects/Forge/1.12.2/ZenServer/src/main/java/raylras/zen/antlr\ZenScriptParser.g4 by ANTLR 4.9.2
package raylras.zen.antlr;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ZenScriptParser extends Parser {
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
		ADD=43, SUB=44, MUL=45, DIV=46, MOD=47, CAT=48, NOT=49, LESS=50, GREATER=51, 
		XOR=52, COLON=53, QUEST=54, BACKTICK=55, DOLLAR=56, AND=57, OR=58, ASSIGN=59, 
		AND_AND=60, OR_OR=61, EQUAL=62, NOT_EQUAL=63, LESS_EQUAL=64, GREATER_EQUAL=65, 
		PLUS_ASSIGN=66, MINUS_ASSIGN=67, STAR_ASSIGN=68, DIV_ASSIGN=69, MOD_ASSIGN=70, 
		XOR_ASSIGN=71, AND_ASSIGN=72, OR_ASSIGN=73, TILDE_ASSIGN=74, DOT_DOT=75, 
		DECIMAL_LITERAL=76, HEX_LITERAL=77, FLOATING_LITERAL=78, BOOLEAN_LITERAL=79, 
		STRING_LITERAL=80, NULL_LITERAL=81, IDENTIFIER=82, WHITE_SPACE=83, BLOCK_COMMENT=84, 
		LINE_COMMENT=85, Preprocessor=86;
	public static final int
		RULE_script = 0, RULE_importStatement = 1, RULE_alias = 2, RULE_functionDeclaration = 3, 
		RULE_zenClassDeclaration = 4, RULE_classBody = 5, RULE_constructor = 6, 
		RULE_field = 7, RULE_method = 8, RULE_asType = 9, RULE_parameters = 10, 
		RULE_parameter = 11, RULE_defaultValue = 12, RULE_arguments = 13, RULE_argument = 14, 
		RULE_block = 15, RULE_mapEntry = 16, RULE_statement = 17, RULE_returnStatement = 18, 
		RULE_breakStatement = 19, RULE_continueStatement = 20, RULE_ifStatement = 21, 
		RULE_forStatement = 22, RULE_whileStatement = 23, RULE_varStatement = 24, 
		RULE_expressionStatement = 25, RULE_forControl = 26, RULE_className = 27, 
		RULE_expression = 28, RULE_type = 29, RULE_typeFunction = 30, RULE_typePrimitive = 31, 
		RULE_typeArray = 32, RULE_typeList = 33, RULE_typeMap = 34, RULE_typeClass = 35, 
		RULE_literal = 36;
	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3X\u01b9\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\3\2\3\2\3\2\3\2\7\2Q\n\2\f\2\16\2"+
		"T\13\2\3\2\3\2\3\3\3\3\3\3\3\3\5\3\\\n\3\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3"+
		"\5\5\5f\n\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\7\7r\n\7\f\7\16\7"+
		"u\13\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\5\t\u0082\n\t\3\t\3"+
		"\t\3\n\3\n\3\n\3\n\5\n\u008a\n\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\5\f\u0093"+
		"\n\f\3\f\3\f\7\f\u0097\n\f\f\f\16\f\u009a\13\f\3\f\3\f\3\r\3\r\5\r\u00a0"+
		"\n\r\3\r\5\r\u00a3\n\r\3\16\3\16\3\16\3\17\3\17\5\17\u00aa\n\17\3\17\3"+
		"\17\7\17\u00ae\n\17\f\17\16\17\u00b1\13\17\3\17\3\17\3\20\3\20\5\20\u00b7"+
		"\n\20\3\21\3\21\7\21\u00bb\n\21\f\21\16\21\u00be\13\21\3\21\3\21\3\22"+
		"\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u00cf"+
		"\n\23\3\24\3\24\5\24\u00d3\n\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26"+
		"\3\27\3\27\3\27\3\27\5\27\u00e1\n\27\3\27\3\27\3\27\5\27\u00e6\n\27\5"+
		"\27\u00e8\n\27\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\32"+
		"\3\32\3\32\5\32\u00f7\n\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34"+
		"\3\34\5\34\u0103\n\34\3\34\3\34\3\34\3\35\3\35\3\35\7\35\u010b\n\35\f"+
		"\35\16\35\u010e\13\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\3\36\3\36\5\36\u011c\n\36\7\36\u011e\n\36\f\36\16\36\u0121\13\36\3\36"+
		"\3\36\3\36\3\36\3\36\7\36\u0128\n\36\f\36\16\36\u012b\13\36\3\36\3\36"+
		"\3\36\3\36\3\36\3\36\7\36\u0133\n\36\f\36\16\36\u0136\13\36\5\36\u0138"+
		"\n\36\3\36\3\36\3\36\3\36\5\36\u013e\n\36\3\36\3\36\5\36\u0142\n\36\3"+
		"\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3"+
		"\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3"+
		"\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\7"+
		"\36\u016e\n\36\f\36\16\36\u0171\13\36\3\37\3\37\3\37\3\37\3\37\3\37\5"+
		"\37\u0179\n\37\3 \3 \3 \5 \u017e\n \3 \3 \7 \u0182\n \f \16 \u0185\13"+
		" \3 \3 \3 \3!\3!\3\"\3\"\5\"\u018e\n\"\3\"\3\"\6\"\u0192\n\"\r\"\16\""+
		"\u0193\3#\3#\3#\5#\u0199\n#\3#\3#\3$\3$\5$\u019f\n$\3$\3$\5$\u01a3\n$"+
		"\3$\7$\u01a6\n$\f$\16$\u01a9\13$\3$\3$\3$\3$\3%\3%\3&\3&\3&\3&\3&\3&\5"+
		"&\u01b7\n&\3&\2\3:\'\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60"+
		"\62\64\668:<>@BDFHJ\2\16\3\2\3\4\3\2\3\6\4\2-.\63\63\3\2\65\65\3\2/\61"+
		"\3\2-.\4\2\64\65@C\3\2\13\f\6\2\62\62\66\66;<>?\4\2==DL\4\2\n\nMM\3\2"+
		"\16\27\2\u01e1\2R\3\2\2\2\4W\3\2\2\2\6_\3\2\2\2\ba\3\2\2\2\ni\3\2\2\2"+
		"\fm\3\2\2\2\16x\3\2\2\2\20|\3\2\2\2\22\u0085\3\2\2\2\24\u008d\3\2\2\2"+
		"\26\u0090\3\2\2\2\30\u009d\3\2\2\2\32\u00a4\3\2\2\2\34\u00a7\3\2\2\2\36"+
		"\u00b4\3\2\2\2 \u00b8\3\2\2\2\"\u00c1\3\2\2\2$\u00ce\3\2\2\2&\u00d0\3"+
		"\2\2\2(\u00d6\3\2\2\2*\u00d9\3\2\2\2,\u00dc\3\2\2\2.\u00e9\3\2\2\2\60"+
		"\u00ed\3\2\2\2\62\u00f3\3\2\2\2\64\u00fc\3\2\2\2\66\u00ff\3\2\2\28\u0107"+
		"\3\2\2\2:\u0141\3\2\2\2<\u0178\3\2\2\2>\u017a\3\2\2\2@\u0189\3\2\2\2B"+
		"\u018d\3\2\2\2D\u0195\3\2\2\2F\u019e\3\2\2\2H\u01ae\3\2\2\2J\u01b6\3\2"+
		"\2\2LQ\5\4\3\2MQ\5\b\5\2NQ\5\n\6\2OQ\5$\23\2PL\3\2\2\2PM\3\2\2\2PN\3\2"+
		"\2\2PO\3\2\2\2QT\3\2\2\2RP\3\2\2\2RS\3\2\2\2SU\3\2\2\2TR\3\2\2\2UV\7\2"+
		"\2\3V\3\3\2\2\2WX\7\7\2\2X[\58\35\2YZ\7\t\2\2Z\\\5\6\4\2[Y\3\2\2\2[\\"+
		"\3\2\2\2\\]\3\2\2\2]^\7,\2\2^\5\3\2\2\2_`\7T\2\2`\7\3\2\2\2ab\7\b\2\2"+
		"bc\7T\2\2ce\5\26\f\2df\5\24\13\2ed\3\2\2\2ef\3\2\2\2fg\3\2\2\2gh\5 \21"+
		"\2h\t\3\2\2\2ij\7\"\2\2jk\7T\2\2kl\5\f\7\2l\13\3\2\2\2ms\7(\2\2nr\5\16"+
		"\b\2or\5\20\t\2pr\5\22\n\2qn\3\2\2\2qo\3\2\2\2qp\3\2\2\2ru\3\2\2\2sq\3"+
		"\2\2\2st\3\2\2\2tv\3\2\2\2us\3\2\2\2vw\7)\2\2w\r\3\2\2\2xy\7#\2\2yz\5"+
		"\26\f\2z{\5 \21\2{\17\3\2\2\2|}\t\2\2\2}~\7T\2\2~\u0081\5\24\13\2\177"+
		"\u0080\7=\2\2\u0080\u0082\5:\36\2\u0081\177\3\2\2\2\u0081\u0082\3\2\2"+
		"\2\u0082\u0083\3\2\2\2\u0083\u0084\7,\2\2\u0084\21\3\2\2\2\u0085\u0086"+
		"\7\b\2\2\u0086\u0087\7T\2\2\u0087\u0089\5\26\f\2\u0088\u008a\5\24\13\2"+
		"\u0089\u0088\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u008c"+
		"\5 \21\2\u008c\23\3\2\2\2\u008d\u008e\7\t\2\2\u008e\u008f\5<\37\2\u008f"+
		"\25\3\2\2\2\u0090\u0092\7$\2\2\u0091\u0093\5\30\r\2\u0092\u0091\3\2\2"+
		"\2\u0092\u0093\3\2\2\2\u0093\u0098\3\2\2\2\u0094\u0095\7*\2\2\u0095\u0097"+
		"\5\30\r\2\u0096\u0094\3\2\2\2\u0097\u009a\3\2\2\2\u0098\u0096\3\2\2\2"+
		"\u0098\u0099\3\2\2\2\u0099\u009b\3\2\2\2\u009a\u0098\3\2\2\2\u009b\u009c"+
		"\7%\2\2\u009c\27\3\2\2\2\u009d\u009f\7T\2\2\u009e\u00a0\5\24\13\2\u009f"+
		"\u009e\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u00a2\3\2\2\2\u00a1\u00a3\5\32"+
		"\16\2\u00a2\u00a1\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\31\3\2\2\2\u00a4\u00a5"+
		"\7=\2\2\u00a5\u00a6\5:\36\2\u00a6\33\3\2\2\2\u00a7\u00a9\7$\2\2\u00a8"+
		"\u00aa\5\36\20\2\u00a9\u00a8\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00af\3"+
		"\2\2\2\u00ab\u00ac\7*\2\2\u00ac\u00ae\5\36\20\2\u00ad\u00ab\3\2\2\2\u00ae"+
		"\u00b1\3\2\2\2\u00af\u00ad\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0\u00b2\3\2"+
		"\2\2\u00b1\u00af\3\2\2\2\u00b2\u00b3\7%\2\2\u00b3\35\3\2\2\2\u00b4\u00b6"+
		"\5:\36\2\u00b5\u00b7\5\24\13\2\u00b6\u00b5\3\2\2\2\u00b6\u00b7\3\2\2\2"+
		"\u00b7\37\3\2\2\2\u00b8\u00bc\7(\2\2\u00b9\u00bb\5$\23\2\u00ba\u00b9\3"+
		"\2\2\2\u00bb\u00be\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd"+
		"\u00bf\3\2\2\2\u00be\u00bc\3\2\2\2\u00bf\u00c0\7)\2\2\u00c0!\3\2\2\2\u00c1"+
		"\u00c2\5:\36\2\u00c2\u00c3\7\67\2\2\u00c3\u00c4\5:\36\2\u00c4#\3\2\2\2"+
		"\u00c5\u00cf\5&\24\2\u00c6\u00cf\5(\25\2\u00c7\u00cf\5*\26\2\u00c8\u00cf"+
		"\5,\27\2\u00c9\u00cf\5.\30\2\u00ca\u00cf\5\60\31\2\u00cb\u00cf\5\62\32"+
		"\2\u00cc\u00cf\5\b\5\2\u00cd\u00cf\5\64\33\2\u00ce\u00c5\3\2\2\2\u00ce"+
		"\u00c6\3\2\2\2\u00ce\u00c7\3\2\2\2\u00ce\u00c8\3\2\2\2\u00ce\u00c9\3\2"+
		"\2\2\u00ce\u00ca\3\2\2\2\u00ce\u00cb\3\2\2\2\u00ce\u00cc\3\2\2\2\u00ce"+
		"\u00cd\3\2\2\2\u00cf%\3\2\2\2\u00d0\u00d2\7\37\2\2\u00d1\u00d3\5:\36\2"+
		"\u00d2\u00d1\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d5"+
		"\7,\2\2\u00d5\'\3\2\2\2\u00d6\u00d7\7\35\2\2\u00d7\u00d8\7,\2\2\u00d8"+
		")\3\2\2\2\u00d9\u00da\7\36\2\2\u00da\u00db\7,\2\2\u00db+\3\2\2\2\u00dc"+
		"\u00dd\7\30\2\2\u00dd\u00e0\5:\36\2\u00de\u00e1\5$\23\2\u00df\u00e1\5"+
		" \21\2\u00e0\u00de\3\2\2\2\u00e0\u00df\3\2\2\2\u00e1\u00e7\3\2\2\2\u00e2"+
		"\u00e5\7\31\2\2\u00e3\u00e6\5$\23\2\u00e4\u00e6\5 \21\2\u00e5\u00e3\3"+
		"\2\2\2\u00e5\u00e4\3\2\2\2\u00e6\u00e8\3\2\2\2\u00e7\u00e2\3\2\2\2\u00e7"+
		"\u00e8\3\2\2\2\u00e8-\3\2\2\2\u00e9\u00ea\7\32\2\2\u00ea\u00eb\5\66\34"+
		"\2\u00eb\u00ec\5 \21\2\u00ec/\3\2\2\2\u00ed\u00ee\7\34\2\2\u00ee\u00ef"+
		"\7$\2\2\u00ef\u00f0\5:\36\2\u00f0\u00f1\7%\2\2\u00f1\u00f2\5 \21\2\u00f2"+
		"\61\3\2\2\2\u00f3\u00f4\t\3\2\2\u00f4\u00f6\7T\2\2\u00f5\u00f7\5\24\13"+
		"\2\u00f6\u00f5\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8\u00f9"+
		"\7=\2\2\u00f9\u00fa\5:\36\2\u00fa\u00fb\7,\2\2\u00fb\63\3\2\2\2\u00fc"+
		"\u00fd\5:\36\2\u00fd\u00fe\7,\2\2\u00fe\65\3\2\2\2\u00ff\u0102\7T\2\2"+
		"\u0100\u0101\7*\2\2\u0101\u0103\7T\2\2\u0102\u0100\3\2\2\2\u0102\u0103"+
		"\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u0105\7\13\2\2\u0105\u0106\5:\36\2"+
		"\u0106\67\3\2\2\2\u0107\u010c\7T\2\2\u0108\u0109\7+\2\2\u0109\u010b\7"+
		"T\2\2\u010a\u0108\3\2\2\2\u010b\u010e\3\2\2\2\u010c\u010a\3\2\2\2\u010c"+
		"\u010d\3\2\2\2\u010d9\3\2\2\2\u010e\u010c\3\2\2\2\u010f\u0110\b\36\1\2"+
		"\u0110\u0142\5J&\2\u0111\u0142\7T\2\2\u0112\u0113\7$\2\2\u0113\u0114\5"+
		":\36\2\u0114\u0115\7%\2\2\u0115\u0142\3\2\2\2\u0116\u0117\t\4\2\2\u0117"+
		"\u0142\5:\36\21\u0118\u011f\7\64\2\2\u0119\u011b\n\5\2\2\u011a\u011c\7"+
		"\67\2\2\u011b\u011a\3\2\2\2\u011b\u011c\3\2\2\2\u011c\u011e\3\2\2\2\u011d"+
		"\u0119\3\2\2\2\u011e\u0121\3\2\2\2\u011f\u011d\3\2\2\2\u011f\u0120\3\2"+
		"\2\2\u0120\u0122\3\2\2\2\u0121\u011f\3\2\2\2\u0122\u0142\7\65\2\2\u0123"+
		"\u0124\7&\2\2\u0124\u0129\5:\36\2\u0125\u0126\7*\2\2\u0126\u0128\5:\36"+
		"\2\u0127\u0125\3\2\2\2\u0128\u012b\3\2\2\2\u0129\u0127\3\2\2\2\u0129\u012a"+
		"\3\2\2\2\u012a\u012c\3\2\2\2\u012b\u0129\3\2\2\2\u012c\u012d\7\'\2\2\u012d"+
		"\u0142\3\2\2\2\u012e\u0137\7(\2\2\u012f\u0134\5\"\22\2\u0130\u0131\7*"+
		"\2\2\u0131\u0133\5\"\22\2\u0132\u0130\3\2\2\2\u0133\u0136\3\2\2\2\u0134"+
		"\u0132\3\2\2\2\u0134\u0135\3\2\2\2\u0135\u0138\3\2\2\2\u0136\u0134\3\2"+
		"\2\2\u0137\u012f\3\2\2\2\u0137\u0138\3\2\2\2\u0138\u0139\3\2\2\2\u0139"+
		"\u0142\7)\2\2\u013a\u013b\7\b\2\2\u013b\u013d\5\26\f\2\u013c\u013e\5\24"+
		"\13\2\u013d\u013c\3\2\2\2\u013d\u013e\3\2\2\2\u013e\u013f\3\2\2\2\u013f"+
		"\u0140\5 \21\2\u0140\u0142\3\2\2\2\u0141\u010f\3\2\2\2\u0141\u0111\3\2"+
		"\2\2\u0141\u0112\3\2\2\2\u0141\u0116\3\2\2\2\u0141\u0118\3\2\2\2\u0141"+
		"\u0123\3\2\2\2\u0141\u012e\3\2\2\2\u0141\u013a\3\2\2\2\u0142\u016f\3\2"+
		"\2\2\u0143\u0144\f\24\2\2\u0144\u0145\7+\2\2\u0145\u016e\5:\36\25\u0146"+
		"\u0147\f\20\2\2\u0147\u0148\t\6\2\2\u0148\u016e\5:\36\21\u0149\u014a\f"+
		"\17\2\2\u014a\u014b\t\7\2\2\u014b\u016e\5:\36\20\u014c\u014d\f\16\2\2"+
		"\u014d\u014e\t\b\2\2\u014e\u016e\5:\36\17\u014f\u0150\f\f\2\2\u0150\u0151"+
		"\t\t\2\2\u0151\u016e\5:\36\r\u0152\u0153\f\13\2\2\u0153\u0154\t\n\2\2"+
		"\u0154\u016e\5:\36\f\u0155\u0156\f\n\2\2\u0156\u0157\78\2\2\u0157\u0158"+
		"\5:\36\2\u0158\u0159\7\67\2\2\u0159\u015a\5:\36\n\u015a\u016e\3\2\2\2"+
		"\u015b\u015c\f\t\2\2\u015c\u015d\t\13\2\2\u015d\u016e\5:\36\t\u015e\u015f"+
		"\f\4\2\2\u015f\u0160\t\f\2\2\u0160\u016e\5:\36\5\u0161\u0162\f\23\2\2"+
		"\u0162\u016e\5\34\17\2\u0163\u0164\f\22\2\2\u0164\u0165\7&\2\2\u0165\u0166"+
		"\5:\36\2\u0166\u0167\7\'\2\2\u0167\u016e\3\2\2\2\u0168\u0169\f\r\2\2\u0169"+
		"\u016a\7\r\2\2\u016a\u016e\5<\37\2\u016b\u016c\f\3\2\2\u016c\u016e\5\24"+
		"\13\2\u016d\u0143\3\2\2\2\u016d\u0146\3\2\2\2\u016d\u0149\3\2\2\2\u016d"+
		"\u014c\3\2\2\2\u016d\u014f\3\2\2\2\u016d\u0152\3\2\2\2\u016d\u0155\3\2"+
		"\2\2\u016d\u015b\3\2\2\2\u016d\u015e\3\2\2\2\u016d\u0161\3\2\2\2\u016d"+
		"\u0163\3\2\2\2\u016d\u0168\3\2\2\2\u016d\u016b\3\2\2\2\u016e\u0171\3\2"+
		"\2\2\u016f\u016d\3\2\2\2\u016f\u0170\3\2\2\2\u0170;\3\2\2\2\u0171\u016f"+
		"\3\2\2\2\u0172\u0179\5D#\2\u0173\u0179\5F$\2\u0174\u0179\5B\"\2\u0175"+
		"\u0179\5@!\2\u0176\u0179\5H%\2\u0177\u0179\5> \2\u0178\u0172\3\2\2\2\u0178"+
		"\u0173\3\2\2\2\u0178\u0174\3\2\2\2\u0178\u0175\3\2\2\2\u0178\u0176\3\2"+
		"\2\2\u0178\u0177\3\2\2\2\u0179=\3\2\2\2\u017a\u017b\7\b\2\2\u017b\u017d"+
		"\7$\2\2\u017c\u017e\5<\37\2\u017d\u017c\3\2\2\2\u017d\u017e\3\2\2\2\u017e"+
		"\u0183\3\2\2\2\u017f\u0180\7*\2\2\u0180\u0182\5<\37\2\u0181\u017f\3\2"+
		"\2\2\u0182\u0185\3\2\2\2\u0183\u0181\3\2\2\2\u0183\u0184\3\2\2\2\u0184"+
		"\u0186\3\2\2\2\u0185\u0183\3\2\2\2\u0186\u0187\7%\2\2\u0187\u0188\5<\37"+
		"\2\u0188?\3\2\2\2\u0189\u018a\t\r\2\2\u018aA\3\2\2\2\u018b\u018e\5@!\2"+
		"\u018c\u018e\5H%\2\u018d\u018b\3\2\2\2\u018d\u018c\3\2\2\2\u018e\u0191"+
		"\3\2\2\2\u018f\u0190\7&\2\2\u0190\u0192\7\'\2\2\u0191\u018f\3\2\2\2\u0192"+
		"\u0193\3\2\2\2\u0193\u0191\3\2\2\2\u0193\u0194\3\2\2\2\u0194C\3\2\2\2"+
		"\u0195\u0198\7&\2\2\u0196\u0199\5@!\2\u0197\u0199\5H%\2\u0198\u0196\3"+
		"\2\2\2\u0198\u0197\3\2\2\2\u0199\u019a\3\2\2\2\u019a\u019b\7\'\2\2\u019b"+
		"E\3\2\2\2\u019c\u019f\5@!\2\u019d\u019f\5H%\2\u019e\u019c\3\2\2\2\u019e"+
		"\u019d\3\2\2\2\u019f\u01a7\3\2\2\2\u01a0\u01a2\7&\2\2\u01a1\u01a3\5<\37"+
		"\2\u01a2\u01a1\3\2\2\2\u01a2\u01a3\3\2\2\2\u01a3\u01a4\3\2\2\2\u01a4\u01a6"+
		"\7\'\2\2\u01a5\u01a0\3\2\2\2\u01a6\u01a9\3\2\2\2\u01a7\u01a5\3\2\2\2\u01a7"+
		"\u01a8\3\2\2\2\u01a8\u01aa\3\2\2\2\u01a9\u01a7\3\2\2\2\u01aa\u01ab\7&"+
		"\2\2\u01ab\u01ac\5<\37\2\u01ac\u01ad\7\'\2\2\u01adG\3\2\2\2\u01ae\u01af"+
		"\58\35\2\u01afI\3\2\2\2\u01b0\u01b7\7N\2\2\u01b1\u01b7\7O\2\2\u01b2\u01b7"+
		"\7P\2\2\u01b3\u01b7\7R\2\2\u01b4\u01b7\7Q\2\2\u01b5\u01b7\7S\2\2\u01b6"+
		"\u01b0\3\2\2\2\u01b6\u01b1\3\2\2\2\u01b6\u01b2\3\2\2\2\u01b6\u01b3\3\2"+
		"\2\2\u01b6\u01b4\3\2\2\2\u01b6\u01b5\3\2\2\2\u01b7K\3\2\2\2-PR[eqs\u0081"+
		"\u0089\u0092\u0098\u009f\u00a2\u00a9\u00af\u00b6\u00bc\u00ce\u00d2\u00e0"+
		"\u00e5\u00e7\u00f6\u0102\u010c\u011b\u011f\u0129\u0134\u0137\u013d\u0141"+
		"\u016d\u016f\u0178\u017d\u0183\u018d\u0193\u0198\u019e\u01a2\u01a7\u01b6";
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

	private static String[] makeRuleNames() {
		return new String[] {
			"script", "importStatement", "alias", "functionDeclaration", "zenClassDeclaration",
			"classBody", "constructor", "field", "method", "asType", "parameters",
			"parameter", "defaultValue", "arguments", "argument", "block", "mapEntry",
			"statement", "returnStatement", "breakStatement", "continueStatement",
			"ifStatement", "forStatement", "whileStatement", "varStatement", "expressionStatement",
			"forControl", "className", "expression", "type", "typeFunction", "typePrimitive",
			"typeArray", "typeList", "typeMap", "typeClass", "literal"
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

	@Override
	public String getGrammarFileName() { return "ZenScriptParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ZenScriptParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ScriptContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(ZenScriptParser.EOF, 0); }
		public List<ImportStatementContext> importStatement() {
			return getRuleContexts(ImportStatementContext.class);
		}
		public ImportStatementContext importStatement(int i) {
			return getRuleContext(ImportStatementContext.class,i);
		}
		public List<FunctionDeclarationContext> functionDeclaration() {
			return getRuleContexts(FunctionDeclarationContext.class);
		}
		public FunctionDeclarationContext functionDeclaration(int i) {
			return getRuleContext(FunctionDeclarationContext.class,i);
		}
		public List<ZenClassDeclarationContext> zenClassDeclaration() {
			return getRuleContexts(ZenClassDeclarationContext.class);
		}
		public ZenClassDeclarationContext zenClassDeclaration(int i) {
			return getRuleContext(ZenClassDeclarationContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ScriptContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_script; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitScript(this);
			else return visitor.visitChildren(this);
		}
	}

	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "VAR", "VAL", "GLOBAL", "STATIC", "IMPORT", "FUNCTION", "AS", "TO",
			"IN", "HAS", "INSTANCEOF", "ANY", "BYTE", "SHORT", "INT", "LONG", "FLOAT",
			"DOUBLE", "BOOL", "VOID", "STRING", "IF", "ELSE", "FOR", "DO", "WHILE",
			"BREAK", "CONTINUE", "RETURN", "FRIGGIN_CLASS", "FRIGGIN_CONSTRUCTOR",
			"ZEN_CLASS", "ZEN_CONSTRUCTOR", "PAREN_OPEN", "PAREN_CLOSE", "BRACK_OPEN",
			"BRACK_CLOSE", "BRACE_OPEN", "BRACE_CLOSE", "COMMA", "DOT", "SEMICOLON",
			"ADD", "SUB", "MUL", "DIV", "MOD", "CAT", "NOT", "LESS", "GREATER", "XOR",
			"COLON", "QUEST", "BACKTICK", "DOLLAR", "AND", "OR", "ASSIGN", "AND_AND",
			"OR_OR", "EQUAL", "NOT_EQUAL", "LESS_EQUAL", "GREATER_EQUAL", "PLUS_ASSIGN",
			"MINUS_ASSIGN", "STAR_ASSIGN", "DIV_ASSIGN", "MOD_ASSIGN", "XOR_ASSIGN",
			"AND_ASSIGN", "OR_ASSIGN", "TILDE_ASSIGN", "DOT_DOT", "DECIMAL_LITERAL",
			"HEX_LITERAL", "FLOATING_LITERAL", "BOOLEAN_LITERAL", "STRING_LITERAL",
			"NULL_LITERAL", "IDENTIFIER", "WHITE_SPACE", "BLOCK_COMMENT", "LINE_COMMENT",
			"Preprocessor"
		};
	}

	public final ScriptContext script() throws RecognitionException {
		ScriptContext _localctx = new ScriptContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_script);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC) | (1L << IMPORT) | (1L << FUNCTION) | (1L << IF) | (1L << FOR) | (1L << WHILE) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << ZEN_CLASS) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (DECIMAL_LITERAL - 76)) | (1L << (HEX_LITERAL - 76)) | (1L << (FLOATING_LITERAL - 76)) | (1L << (BOOLEAN_LITERAL - 76)) | (1L << (STRING_LITERAL - 76)) | (1L << (NULL_LITERAL - 76)) | (1L << (IDENTIFIER - 76)))) != 0)) {
				{
				setState(78);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(74);
					importStatement();
					}
					break;
				case 2:
					{
					setState(75);
					functionDeclaration();
					}
					break;
				case 3:
					{
					setState(76);
					zenClassDeclaration();
					}
					break;
				case 4:
					{
					setState(77);
					statement();
					}
					break;
				}
				}
				setState(82);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(83);
			match(EOF);
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

	public final ImportStatementContext importStatement() throws RecognitionException {
		ImportStatementContext _localctx = new ImportStatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_importStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			match(IMPORT);
			setState(86);
			className();
			setState(89);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(87);
				match(AS);
				setState(88);
				alias();
				}
			}

			setState(91);
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

	public final AliasContext alias() throws RecognitionException {
		AliasContext _localctx = new AliasContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_alias);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
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

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_functionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(FUNCTION);
			setState(96);
			match(IDENTIFIER);
			setState(97);
			parameters();
			setState(99);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(98);
				asType();
				}
			}

			setState(101);
			block();
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

	public final ZenClassDeclarationContext zenClassDeclaration() throws RecognitionException {
		ZenClassDeclarationContext _localctx = new ZenClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_zenClassDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			match(ZEN_CLASS);
			setState(104);
			match(IDENTIFIER);
			setState(105);
			classBody();
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

	public final ClassBodyContext classBody() throws RecognitionException {
		ClassBodyContext _localctx = new ClassBodyContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_classBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			match(BRACE_OPEN);
			setState(113);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << FUNCTION) | (1L << ZEN_CONSTRUCTOR))) != 0)) {
				{
				setState(111);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case ZEN_CONSTRUCTOR:
					{
					setState(108);
					constructor();
					}
					break;
				case VAR:
				case VAL:
					{
					setState(109);
					field();
					}
					break;
				case FUNCTION:
					{
					setState(110);
					method();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(115);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(116);
			match(BRACE_CLOSE);
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

	public final ConstructorContext constructor() throws RecognitionException {
		ConstructorContext _localctx = new ConstructorContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_constructor);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			match(ZEN_CONSTRUCTOR);
			setState(119);
			parameters();
			setState(120);
			block();
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

	public final FieldContext field() throws RecognitionException {
		FieldContext _localctx = new FieldContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_field);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			_la = _input.LA(1);
			if ( !(_la==VAR || _la==VAL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(123);
			match(IDENTIFIER);
			setState(124);
			asType();
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(125);
				match(ASSIGN);
				setState(126);
				expression(0);
				}
			}

			setState(129);
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

	public final MethodContext method() throws RecognitionException {
		MethodContext _localctx = new MethodContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_method);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			match(FUNCTION);
			setState(132);
			match(IDENTIFIER);
			setState(133);
			parameters();
			setState(135);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(134);
				asType();
				}
			}

			setState(137);
			block();
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

	public final AsTypeContext asType() throws RecognitionException {
		AsTypeContext _localctx = new AsTypeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_asType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			match(AS);
			setState(140);
			type();
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

	public final ParametersContext parameters() throws RecognitionException {
		ParametersContext _localctx = new ParametersContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_parameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(142);
			match(PAREN_OPEN);
			setState(144);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(143);
				parameter();
				}
			}

			setState(150);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(146);
				match(COMMA);
				setState(147);
				parameter();
				}
				}
				setState(152);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(153);
			match(PAREN_CLOSE);
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

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_parameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			match(IDENTIFIER);
			setState(157);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(156);
				asType();
				}
			}

			setState(160);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(159);
				defaultValue();
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

	public final DefaultValueContext defaultValue() throws RecognitionException {
		DefaultValueContext _localctx = new DefaultValueContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_defaultValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			match(ASSIGN);
			setState(163);
			expression(0);
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

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			match(PAREN_OPEN);
			{
			setState(167);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (DECIMAL_LITERAL - 76)) | (1L << (HEX_LITERAL - 76)) | (1L << (FLOATING_LITERAL - 76)) | (1L << (BOOLEAN_LITERAL - 76)) | (1L << (STRING_LITERAL - 76)) | (1L << (NULL_LITERAL - 76)) | (1L << (IDENTIFIER - 76)))) != 0)) {
				{
				setState(166);
				argument();
				}
			}

			setState(173);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(169);
				match(COMMA);
				setState(170);
				argument();
				}
				}
				setState(175);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(176);
			match(PAREN_CLOSE);
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

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_argument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			expression(0);
			setState(180);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(179);
				asType();
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

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			match(BRACE_OPEN);
			setState(186);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC) | (1L << FUNCTION) | (1L << IF) | (1L << FOR) | (1L << WHILE) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (DECIMAL_LITERAL - 76)) | (1L << (HEX_LITERAL - 76)) | (1L << (FLOATING_LITERAL - 76)) | (1L << (BOOLEAN_LITERAL - 76)) | (1L << (STRING_LITERAL - 76)) | (1L << (NULL_LITERAL - 76)) | (1L << (IDENTIFIER - 76)))) != 0)) {
				{
				{
				setState(183);
				statement();
				}
				}
				setState(188);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(189);
			match(BRACE_CLOSE);
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

	public final MapEntryContext mapEntry() throws RecognitionException {
		MapEntryContext _localctx = new MapEntryContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_mapEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
			((MapEntryContext)_localctx).key = expression(0);
			setState(192);
			match(COLON);
			setState(193);
			((MapEntryContext)_localctx).value = expression(0);
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

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_statement);
		try {
			setState(204);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(195);
				returnStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(196);
				breakStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(197);
				continueStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(198);
				ifStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(199);
				forStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(200);
				whileStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(201);
				varStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(202);
				functionDeclaration();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(203);
				expressionStatement();
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

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(RETURN);
			setState(208);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (DECIMAL_LITERAL - 76)) | (1L << (HEX_LITERAL - 76)) | (1L << (FLOATING_LITERAL - 76)) | (1L << (BOOLEAN_LITERAL - 76)) | (1L << (STRING_LITERAL - 76)) | (1L << (NULL_LITERAL - 76)) | (1L << (IDENTIFIER - 76)))) != 0)) {
				{
				setState(207);
				expression(0);
				}
			}

			setState(210);
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

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			match(BREAK);
			setState(213);
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

	public final ContinueStatementContext continueStatement() throws RecognitionException {
		ContinueStatementContext _localctx = new ContinueStatementContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(215);
			match(CONTINUE);
			setState(216);
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

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			match(IF);
			setState(219);
			expression(0);
			setState(222);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(220);
				statement();
				}
				break;
			case 2:
				{
				setState(221);
				block();
				}
				break;
			}
			setState(229);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				{
				setState(224);
				match(ELSE);
				setState(227);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
				case 1:
					{
					setState(225);
					statement();
					}
					break;
				case 2:
					{
					setState(226);
					block();
					}
					break;
				}
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

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_forStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			match(FOR);
			setState(232);
			forControl();
			setState(233);
			block();
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

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			match(WHILE);
			setState(236);
			match(PAREN_OPEN);
			setState(237);
			expression(0);
			setState(238);
			match(PAREN_CLOSE);
			setState(239);
			block();
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

	public final VarStatementContext varStatement() throws RecognitionException {
		VarStatementContext _localctx = new VarStatementContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_varStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(241);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(242);
			match(IDENTIFIER);
			setState(244);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(243);
				asType();
				}
			}

			setState(246);
			match(ASSIGN);
			setState(247);
			expression(0);
			setState(248);
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

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(250);
			expression(0);
			setState(251);
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

	public final ForControlContext forControl() throws RecognitionException {
		ForControlContext _localctx = new ForControlContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_forControl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			match(IDENTIFIER);
			setState(256);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(254);
				match(COMMA);
				setState(255);
				match(IDENTIFIER);
				}
			}

			setState(258);
			match(IN);
			setState(259);
			expression(0);
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

	public final ClassNameContext className() throws RecognitionException {
		ClassNameContext _localctx = new ClassNameContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_className);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(261);
			match(IDENTIFIER);
			setState(266);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(262);
					match(DOT);
					setState(263);
					match(IDENTIFIER);
					}
					}
				}
				setState(268);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
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

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 56;
		enterRecursionRule(_localctx, 56, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(319);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DECIMAL_LITERAL:
			case HEX_LITERAL:
			case FLOATING_LITERAL:
			case BOOLEAN_LITERAL:
			case STRING_LITERAL:
			case NULL_LITERAL:
				{
				_localctx = new ExpressionLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(270);
				literal();
				}
				break;
			case IDENTIFIER:
				{
				_localctx = new ExpressionIdentifierContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(271);
				((ExpressionIdentifierContext)_localctx).id = match(IDENTIFIER);
				}
				break;
			case PAREN_OPEN:
				{
				_localctx = new ExpressionParensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(272);
				match(PAREN_OPEN);
				setState(273);
				expression(0);
				setState(274);
				match(PAREN_CLOSE);
				}
				break;
			case ADD:
			case SUB:
			case NOT:
				{
				_localctx = new ExpressionUnaryContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(276);
				((ExpressionUnaryContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADD) | (1L << SUB) | (1L << NOT))) != 0)) ) {
					((ExpressionUnaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(277);
				expression(15);
				}
				break;
			case LESS:
				{
				_localctx = new BracketHandlerContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(278);
				match(LESS);
				setState(285);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC) | (1L << IMPORT) | (1L << FUNCTION) | (1L << AS) | (1L << TO) | (1L << IN) | (1L << HAS) | (1L << INSTANCEOF) | (1L << ANY) | (1L << BYTE) | (1L << SHORT) | (1L << INT) | (1L << LONG) | (1L << FLOAT) | (1L << DOUBLE) | (1L << BOOL) | (1L << VOID) | (1L << STRING) | (1L << IF) | (1L << ELSE) | (1L << FOR) | (1L << DO) | (1L << WHILE) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << FRIGGIN_CLASS) | (1L << FRIGGIN_CONSTRUCTOR) | (1L << ZEN_CLASS) | (1L << ZEN_CONSTRUCTOR) | (1L << PAREN_OPEN) | (1L << PAREN_CLOSE) | (1L << BRACK_OPEN) | (1L << BRACK_CLOSE) | (1L << BRACE_OPEN) | (1L << BRACE_CLOSE) | (1L << COMMA) | (1L << DOT) | (1L << SEMICOLON) | (1L << ADD) | (1L << SUB) | (1L << MUL) | (1L << DIV) | (1L << MOD) | (1L << CAT) | (1L << NOT) | (1L << LESS) | (1L << XOR) | (1L << COLON) | (1L << QUEST) | (1L << BACKTICK) | (1L << DOLLAR) | (1L << AND) | (1L << OR) | (1L << ASSIGN) | (1L << AND_AND) | (1L << OR_OR) | (1L << EQUAL) | (1L << NOT_EQUAL))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (LESS_EQUAL - 64)) | (1L << (GREATER_EQUAL - 64)) | (1L << (PLUS_ASSIGN - 64)) | (1L << (MINUS_ASSIGN - 64)) | (1L << (STAR_ASSIGN - 64)) | (1L << (DIV_ASSIGN - 64)) | (1L << (MOD_ASSIGN - 64)) | (1L << (XOR_ASSIGN - 64)) | (1L << (AND_ASSIGN - 64)) | (1L << (OR_ASSIGN - 64)) | (1L << (TILDE_ASSIGN - 64)) | (1L << (DOT_DOT - 64)) | (1L << (DECIMAL_LITERAL - 64)) | (1L << (HEX_LITERAL - 64)) | (1L << (FLOATING_LITERAL - 64)) | (1L << (BOOLEAN_LITERAL - 64)) | (1L << (STRING_LITERAL - 64)) | (1L << (NULL_LITERAL - 64)) | (1L << (IDENTIFIER - 64)) | (1L << (WHITE_SPACE - 64)) | (1L << (BLOCK_COMMENT - 64)) | (1L << (LINE_COMMENT - 64)) | (1L << (Preprocessor - 64)))) != 0)) {
					{
					{
					setState(279);
					_la = _input.LA(1);
					if ( _la <= 0 || (_la==GREATER) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(281);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
					case 1:
						{
						setState(280);
						match(COLON);
						}
						break;
					}
					}
					}
					setState(287);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(288);
				match(GREATER);
				}
				break;
			case BRACK_OPEN:
				{
				_localctx = new ArrayInitContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(289);
				match(BRACK_OPEN);
				setState(290);
				expression(0);
				setState(295);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(291);
					match(COMMA);
					setState(292);
					expression(0);
					}
					}
					setState(297);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(298);
				match(BRACK_CLOSE);
				}
				break;
			case BRACE_OPEN:
				{
				_localctx = new MapInitContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(300);
				match(BRACE_OPEN);
				setState(309);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (DECIMAL_LITERAL - 76)) | (1L << (HEX_LITERAL - 76)) | (1L << (FLOATING_LITERAL - 76)) | (1L << (BOOLEAN_LITERAL - 76)) | (1L << (STRING_LITERAL - 76)) | (1L << (NULL_LITERAL - 76)) | (1L << (IDENTIFIER - 76)))) != 0)) {
					{
					setState(301);
					mapEntry();
					setState(306);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(302);
						match(COMMA);
						setState(303);
						mapEntry();
						}
						}
						setState(308);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(311);
				match(BRACE_CLOSE);
				}
				break;
			case FUNCTION:
				{
				_localctx = new AnonymousFunctionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(312);
				match(FUNCTION);
				setState(313);
				parameters();
				setState(315);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(314);
					asType();
					}
				}

				setState(317);
				block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(365);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(363);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionAccessContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(321);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(322);
						match(DOT);
						setState(323);
						expression(19);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(324);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(325);
						((ExpressionBinaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << MOD))) != 0)) ) {
							((ExpressionBinaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(326);
						expression(15);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(327);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(328);
						((ExpressionBinaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
							((ExpressionBinaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(329);
						expression(14);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(330);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(331);
						((ExpressionBinaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 50)) & ~0x3f) == 0 && ((1L << (_la - 50)) & ((1L << (LESS - 50)) | (1L << (GREATER - 50)) | (1L << (EQUAL - 50)) | (1L << (NOT_EQUAL - 50)) | (1L << (LESS_EQUAL - 50)) | (1L << (GREATER_EQUAL - 50)))) != 0)) ) {
							((ExpressionBinaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(332);
						expression(13);
						}
						break;
					case 5:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(333);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(334);
						_la = _input.LA(1);
						if ( !(_la==IN || _la==HAS) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(335);
						expression(11);
						}
						break;
					case 6:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(336);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(337);
						((ExpressionBinaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CAT) | (1L << XOR) | (1L << AND) | (1L << OR) | (1L << AND_AND) | (1L << OR_OR))) != 0)) ) {
							((ExpressionBinaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(338);
						expression(10);
						}
						break;
					case 7:
						{
						_localctx = new ExpressionTrinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(339);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(340);
						match(QUEST);
						setState(341);
						expression(0);
						setState(342);
						match(COLON);
						setState(343);
						expression(8);
						}
						break;
					case 8:
						{
						_localctx = new ExpressionAssignContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(345);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(346);
						((ExpressionAssignContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 59)) & ~0x3f) == 0 && ((1L << (_la - 59)) & ((1L << (ASSIGN - 59)) | (1L << (PLUS_ASSIGN - 59)) | (1L << (MINUS_ASSIGN - 59)) | (1L << (STAR_ASSIGN - 59)) | (1L << (DIV_ASSIGN - 59)) | (1L << (MOD_ASSIGN - 59)) | (1L << (XOR_ASSIGN - 59)) | (1L << (AND_ASSIGN - 59)) | (1L << (OR_ASSIGN - 59)) | (1L << (TILDE_ASSIGN - 59)))) != 0)) ) {
							((ExpressionAssignContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(347);
						expression(7);
						}
						break;
					case 9:
						{
						_localctx = new ExpressionRangeContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(348);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(349);
						_la = _input.LA(1);
						if ( !(_la==TO || _la==DOT_DOT) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(350);
						expression(3);
						}
						break;
					case 10:
						{
						_localctx = new ExpressionCallContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(351);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(352);
						arguments();
						}
						break;
					case 11:
						{
						_localctx = new ExpressionIndexContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(353);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(354);
						match(BRACK_OPEN);
						setState(355);
						expression(0);
						setState(356);
						match(BRACK_CLOSE);
						}
						break;
					case 12:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(358);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(359);
						match(INSTANCEOF);
						setState(360);
						type();
						}
						break;
					case 13:
						{
						_localctx = new ExpressionCastContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(361);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(362);
						asType();
						}
						break;
					}
					}
				}
				setState(367);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(374);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				{
				setState(368);
				typeList();
				}
				break;
			case 2:
				{
				setState(369);
				typeMap();
				}
				break;
			case 3:
				{
				setState(370);
				typeArray();
				}
				break;
			case 4:
				{
				setState(371);
				typePrimitive();
				}
				break;
			case 5:
				{
				setState(372);
				typeClass();
				}
				break;
			case 6:
				{
				setState(373);
				typeFunction();
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

	public final TypeFunctionContext typeFunction() throws RecognitionException {
		TypeFunctionContext _localctx = new TypeFunctionContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_typeFunction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(376);
			match(FUNCTION);
			setState(377);
			match(PAREN_OPEN);
			setState(379);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << ANY) | (1L << BYTE) | (1L << SHORT) | (1L << INT) | (1L << LONG) | (1L << FLOAT) | (1L << DOUBLE) | (1L << BOOL) | (1L << VOID) | (1L << STRING) | (1L << BRACK_OPEN))) != 0) || _la==IDENTIFIER) {
				{
				setState(378);
				type();
				}
			}

			setState(385);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(381);
				match(COMMA);
				setState(382);
				type();
				}
				}
				setState(387);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(388);
			match(PAREN_CLOSE);
			setState(389);
			type();
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

	public final TypePrimitiveContext typePrimitive() throws RecognitionException {
		TypePrimitiveContext _localctx = new TypePrimitiveContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_typePrimitive);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(391);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ANY) | (1L << BYTE) | (1L << SHORT) | (1L << INT) | (1L << LONG) | (1L << FLOAT) | (1L << DOUBLE) | (1L << BOOL) | (1L << VOID) | (1L << STRING))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public final TypeArrayContext typeArray() throws RecognitionException {
		TypeArrayContext _localctx = new TypeArrayContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_typeArray);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(395);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ANY:
			case BYTE:
			case SHORT:
			case INT:
			case LONG:
			case FLOAT:
			case DOUBLE:
			case BOOL:
			case VOID:
			case STRING:
				{
				setState(393);
				typePrimitive();
				}
				break;
			case IDENTIFIER:
				{
				setState(394);
				typeClass();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(399);
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(397);
					match(BRACK_OPEN);
					setState(398);
					match(BRACK_CLOSE);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(401);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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

	public final TypeListContext typeList() throws RecognitionException {
		TypeListContext _localctx = new TypeListContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_typeList);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(403);
			match(BRACK_OPEN);
			setState(406);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ANY:
			case BYTE:
			case SHORT:
			case INT:
			case LONG:
			case FLOAT:
			case DOUBLE:
			case BOOL:
			case VOID:
			case STRING:
				{
				setState(404);
				typePrimitive();
				}
				break;
			case IDENTIFIER:
				{
				setState(405);
				typeClass();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(408);
			match(BRACK_CLOSE);
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

	public final TypeMapContext typeMap() throws RecognitionException {
		TypeMapContext _localctx = new TypeMapContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_typeMap);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(412);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ANY:
			case BYTE:
			case SHORT:
			case INT:
			case LONG:
			case FLOAT:
			case DOUBLE:
			case BOOL:
			case VOID:
			case STRING:
				{
				setState(410);
				typePrimitive();
				}
				break;
			case IDENTIFIER:
				{
				setState(411);
				typeClass();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(421);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(414);
					match(BRACK_OPEN);
					setState(416);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << ANY) | (1L << BYTE) | (1L << SHORT) | (1L << INT) | (1L << LONG) | (1L << FLOAT) | (1L << DOUBLE) | (1L << BOOL) | (1L << VOID) | (1L << STRING) | (1L << BRACK_OPEN))) != 0) || _la==IDENTIFIER) {
						{
						setState(415);
						type();
						}
					}

					setState(418);
					match(BRACK_CLOSE);
					}
					}
				}
				setState(423);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			}
			setState(424);
			match(BRACK_OPEN);
			setState(425);
			type();
			setState(426);
			match(BRACK_CLOSE);
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

	public final TypeClassContext typeClass() throws RecognitionException {
		TypeClassContext _localctx = new TypeClassContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_typeClass);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(428);
			className();
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

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_literal);
		try {
			setState(436);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DECIMAL_LITERAL:
				_localctx = new IntegerLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(430);
				match(DECIMAL_LITERAL);
				}
				break;
			case HEX_LITERAL:
				_localctx = new IntegerLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(431);
				match(HEX_LITERAL);
				}
				break;
			case FLOATING_LITERAL:
				_localctx = new FloatingLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(432);
				match(FLOATING_LITERAL);
				}
				break;
			case STRING_LITERAL:
				_localctx = new StringLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(433);
				match(STRING_LITERAL);
				}
				break;
			case BOOLEAN_LITERAL:
				_localctx = new BooleanLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(434);
				match(BOOLEAN_LITERAL);
				}
				break;
			case NULL_LITERAL:
				_localctx = new NullLiteralContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(435);
				match(NULL_LITERAL);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 28:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}

	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 18);
		case 1:
			return precpred(_ctx, 14);
		case 2:
			return precpred(_ctx, 13);
		case 3:
			return precpred(_ctx, 12);
		case 4:
			return precpred(_ctx, 10);
		case 5:
			return precpred(_ctx, 9);
		case 6:
			return precpred(_ctx, 8);
		case 7:
			return precpred(_ctx, 7);
		case 8:
			return precpred(_ctx, 2);
		case 9:
			return precpred(_ctx, 17);
		case 10:
			return precpred(_ctx, 16);
		case 11:
			return precpred(_ctx, 11);
		case 12:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	public static class FunctionDeclarationContext extends ParserRuleContext {
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }

		public TerminalNode FUNCTION() { return getToken(ZenScriptParser.FUNCTION, 0); }

		public ParametersContext parameters() {
			return getRuleContext(ParametersContext.class,0);
		}

		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}

		public AsTypeContext asType() {
			return getRuleContext(AsTypeContext.class,0);
		}

		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ZenClassDeclarationContext extends ParserRuleContext {
		public ZenClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode ZEN_CLASS() { return getToken(ZenScriptParser.ZEN_CLASS, 0); }

		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }

		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}

		@Override public int getRuleIndex() { return RULE_zenClassDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitZenClassDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ClassBodyContext extends ParserRuleContext {
		public ClassBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode BRACE_OPEN() { return getToken(ZenScriptParser.BRACE_OPEN, 0); }

		public TerminalNode BRACE_CLOSE() { return getToken(ZenScriptParser.BRACE_CLOSE, 0); }

		public List<ConstructorContext> constructor() {
			return getRuleContexts(ConstructorContext.class);
		}

		public ConstructorContext constructor(int i) {
			return getRuleContext(ConstructorContext.class,i);
		}

		public List<FieldContext> field() {
			return getRuleContexts(FieldContext.class);
		}

		public FieldContext field(int i) {
			return getRuleContext(FieldContext.class,i);
		}

		public List<MethodContext> method() {
			return getRuleContexts(MethodContext.class);
		}

		public MethodContext method(int i) {
			return getRuleContext(MethodContext.class,i);
		}

		@Override public int getRuleIndex() { return RULE_classBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitClassBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ConstructorContext extends ParserRuleContext {
		public ConstructorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode ZEN_CONSTRUCTOR() { return getToken(ZenScriptParser.ZEN_CONSTRUCTOR, 0); }

		public ParametersContext parameters() {
			return getRuleContext(ParametersContext.class,0);
		}

		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}

		@Override public int getRuleIndex() { return RULE_constructor; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitConstructor(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class MethodContext extends ParserRuleContext {
		public MethodContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }
		public ParametersContext parameters() {
			return getRuleContext(ParametersContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public AsTypeContext asType() {
			return getRuleContext(AsTypeContext.class,0);
		}

		public TerminalNode FUNCTION() { return getToken(ZenScriptParser.FUNCTION, 0); }

		@Override public int getRuleIndex() { return RULE_method; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitMethod(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class AsTypeContext extends ParserRuleContext {
		public AsTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode AS() { return getToken(ZenScriptParser.AS, 0); }

		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}

		@Override public int getRuleIndex() { return RULE_asType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitAsType(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ParametersContext extends ParserRuleContext {
		public ParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode PAREN_OPEN() { return getToken(ZenScriptParser.PAREN_OPEN, 0); }

		public TerminalNode PAREN_CLOSE() { return getToken(ZenScriptParser.PAREN_CLOSE, 0); }

		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}

		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}

		public List<TerminalNode> COMMA() { return getTokens(ZenScriptParser.COMMA); }

		public TerminalNode COMMA(int i) {
			return getToken(ZenScriptParser.COMMA, i);
		}

		@Override public int getRuleIndex() { return RULE_parameters; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ParameterContext extends ParserRuleContext {
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }

		public AsTypeContext asType() {
			return getRuleContext(AsTypeContext.class,0);
		}

		public DefaultValueContext defaultValue() {
			return getRuleContext(DefaultValueContext.class,0);
		}

		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class DefaultValueContext extends ParserRuleContext {
		public DefaultValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}

		public TerminalNode ASSIGN() { return getToken(ZenScriptParser.ASSIGN, 0); }

		@Override public int getRuleIndex() { return RULE_defaultValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitDefaultValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ArgumentsContext extends ParserRuleContext {
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode PAREN_OPEN() { return getToken(ZenScriptParser.PAREN_OPEN, 0); }

		public TerminalNode PAREN_CLOSE() { return getToken(ZenScriptParser.PAREN_CLOSE, 0); }

		public List<ArgumentContext> argument() {
			return getRuleContexts(ArgumentContext.class);
		}

		public ArgumentContext argument(int i) {
			return getRuleContext(ArgumentContext.class,i);
		}

		public List<TerminalNode> COMMA() { return getTokens(ZenScriptParser.COMMA); }

		public TerminalNode COMMA(int i) {
			return getToken(ZenScriptParser.COMMA, i);
		}

		@Override public int getRuleIndex() { return RULE_arguments; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ArgumentContext extends ParserRuleContext {
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}

		public AsTypeContext asType() {
			return getRuleContext(AsTypeContext.class,0);
		}

		@Override public int getRuleIndex() { return RULE_argument; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class BlockContext extends ParserRuleContext {
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode BRACE_OPEN() { return getToken(ZenScriptParser.BRACE_OPEN, 0); }

		public TerminalNode BRACE_CLOSE() { return getToken(ZenScriptParser.BRACE_CLOSE, 0); }

		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}

		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}

		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
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

		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}

		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}

		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}

		public VarStatementContext varStatement() {
			return getRuleContext(VarStatementContext.class,0);
		}

		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}

		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}

		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ReturnStatementContext extends ParserRuleContext {
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode RETURN() { return getToken(ZenScriptParser.RETURN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}

		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }

		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class BreakStatementContext extends ParserRuleContext {
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode BREAK() { return getToken(ZenScriptParser.BREAK, 0); }

		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }

		@Override public int getRuleIndex() { return RULE_breakStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitBreakStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ContinueStatementContext extends ParserRuleContext {
		public ContinueStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode CONTINUE() { return getToken(ZenScriptParser.CONTINUE, 0); }

		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }

		@Override public int getRuleIndex() { return RULE_continueStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitContinueStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class IfStatementContext extends ParserRuleContext {
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}

		public TerminalNode IF() { return getToken(ZenScriptParser.IF, 0); }

		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}

		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}

		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}

		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}

		public TerminalNode ELSE() { return getToken(ZenScriptParser.ELSE, 0); }

		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ForStatementContext extends ParserRuleContext {
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode FOR() { return getToken(ZenScriptParser.FOR, 0); }

		public ForControlContext forControl() {
			return getRuleContext(ForControlContext.class,0);
		}

		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}

		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class WhileStatementContext extends ParserRuleContext {
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode WHILE() { return getToken(ZenScriptParser.WHILE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}

		public TerminalNode PAREN_OPEN() { return getToken(ZenScriptParser.PAREN_OPEN, 0); }

		public TerminalNode PAREN_CLOSE() { return getToken(ZenScriptParser.PAREN_CLOSE, 0); }

		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}

		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class VarStatementContext extends ParserRuleContext {
		public VarStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}

		public TerminalNode ASSIGN() { return getToken(ZenScriptParser.ASSIGN, 0); }

		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }

		public TerminalNode VAR() { return getToken(ZenScriptParser.VAR, 0); }

		public TerminalNode VAL() { return getToken(ZenScriptParser.VAL, 0); }

		public TerminalNode GLOBAL() { return getToken(ZenScriptParser.GLOBAL, 0); }

		public TerminalNode STATIC() { return getToken(ZenScriptParser.STATIC, 0); }

		public AsTypeContext asType() {
			return getRuleContext(AsTypeContext.class,0);
		}

		@Override public int getRuleIndex() { return RULE_varStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitVarStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ExpressionStatementContext extends ParserRuleContext {
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}

		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }

		@Override public int getRuleIndex() { return RULE_expressionStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ForControlContext extends ParserRuleContext {
		public ForControlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public List<TerminalNode> IDENTIFIER() { return getTokens(ZenScriptParser.IDENTIFIER); }

		public TerminalNode IDENTIFIER(int i) {
			return getToken(ZenScriptParser.IDENTIFIER, i);
		}

		public TerminalNode IN() { return getToken(ZenScriptParser.IN, 0); }

		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}

		public TerminalNode COMMA() { return getToken(ZenScriptParser.COMMA, 0); }

		@Override public int getRuleIndex() { return RULE_forControl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitForControl(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ClassNameContext extends ParserRuleContext {
		public ClassNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public List<TerminalNode> IDENTIFIER() { return getTokens(ZenScriptParser.IDENTIFIER); }

		public TerminalNode IDENTIFIER(int i) {
			return getToken(ZenScriptParser.IDENTIFIER, i);
		}

		public List<TerminalNode> DOT() { return getTokens(ZenScriptParser.DOT); }

		public TerminalNode DOT(int i) {
			return getToken(ZenScriptParser.DOT, i);
		}

		@Override public int getRuleIndex() { return RULE_className; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitClassName(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		public ExpressionContext() { }

		@Override public int getRuleIndex() { return RULE_expression; }

		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class ImportStatementContext extends ParserRuleContext {
		public ImportStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode IMPORT() { return getToken(ZenScriptParser.IMPORT, 0); }

		public ClassNameContext className() {
			return getRuleContext(ClassNameContext.class,0);
		}

		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }

		public TerminalNode AS() { return getToken(ZenScriptParser.AS, 0); }

		public AliasContext alias() {
			return getRuleContext(AliasContext.class,0);
		}

		@Override public int getRuleIndex() { return RULE_importStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitImportStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ExpressionTrinaryContext extends ExpressionContext {
		public ExpressionTrinaryContext(ExpressionContext ctx) { copyFrom(ctx); }

		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}

		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}

		public TerminalNode QUEST() { return getToken(ZenScriptParser.QUEST, 0); }

		public TerminalNode COLON() { return getToken(ZenScriptParser.COLON, 0); }

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionTrinary(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class AliasContext extends ParserRuleContext {
		public AliasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }

		@Override public int getRuleIndex() { return RULE_alias; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitAlias(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class FieldContext extends ParserRuleContext {
		public FieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }

		public AsTypeContext asType() {
			return getRuleContext(AsTypeContext.class,0);
		}

		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }

		public TerminalNode VAR() { return getToken(ZenScriptParser.VAR, 0); }

		public TerminalNode VAL() { return getToken(ZenScriptParser.VAL, 0); }

		public TerminalNode ASSIGN() { return getToken(ZenScriptParser.ASSIGN, 0); }

		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}

		@Override public int getRuleIndex() { return RULE_field; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitField(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class MapEntryContext extends ParserRuleContext {
		public ExpressionContext key;
		public ExpressionContext value;
		public MapEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode COLON() { return getToken(ZenScriptParser.COLON, 0); }

		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}

		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}

		@Override public int getRuleIndex() { return RULE_mapEntry; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitMapEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ExpressionCastContext extends ExpressionContext {
		public ExpressionCastContext(ExpressionContext ctx) { copyFrom(ctx); }

		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}

		public AsTypeContext asType() {
			return getRuleContext(AsTypeContext.class,0);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionCast(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ExpressionRangeContext extends ExpressionContext {
		public ExpressionRangeContext(ExpressionContext ctx) { copyFrom(ctx); }

		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}

		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}

		public TerminalNode DOT_DOT() { return getToken(ZenScriptParser.DOT_DOT, 0); }

		public TerminalNode TO() { return getToken(ZenScriptParser.TO, 0); }

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionRange(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ArrayInitContext extends ExpressionContext {
		public ArrayInitContext(ExpressionContext ctx) { copyFrom(ctx); }

		public TerminalNode BRACK_OPEN() { return getToken(ZenScriptParser.BRACK_OPEN, 0); }

		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}

		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}

		public TerminalNode BRACK_CLOSE() { return getToken(ZenScriptParser.BRACK_CLOSE, 0); }

		public List<TerminalNode> COMMA() { return getTokens(ZenScriptParser.COMMA); }

		public TerminalNode COMMA(int i) {
			return getToken(ZenScriptParser.COMMA, i);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitArrayInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ExpressionUnaryContext extends ExpressionContext {
		public Token op;
		public ExpressionUnaryContext(ExpressionContext ctx) { copyFrom(ctx); }

		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}

		public TerminalNode ADD() { return getToken(ZenScriptParser.ADD, 0); }

		public TerminalNode SUB() { return getToken(ZenScriptParser.SUB, 0); }

		public TerminalNode NOT() { return getToken(ZenScriptParser.NOT, 0); }

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionUnary(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TypeListContext typeList() {
			return getRuleContext(TypeListContext.class,0);
		}

		public TypeMapContext typeMap() {
			return getRuleContext(TypeMapContext.class,0);
		}

		public TypeArrayContext typeArray() {
			return getRuleContext(TypeArrayContext.class,0);
		}

		public TypePrimitiveContext typePrimitive() {
			return getRuleContext(TypePrimitiveContext.class,0);
		}

		public TypeClassContext typeClass() {
			return getRuleContext(TypeClassContext.class,0);
		}

		public TypeFunctionContext typeFunction() {
			return getRuleContext(TypeFunctionContext.class,0);
		}

		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ExpressionParensContext extends ExpressionContext {
		public ExpressionParensContext(ExpressionContext ctx) { copyFrom(ctx); }

		public TerminalNode PAREN_OPEN() { return getToken(ZenScriptParser.PAREN_OPEN, 0); }

		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}

		public TerminalNode PAREN_CLOSE() { return getToken(ZenScriptParser.PAREN_CLOSE, 0); }

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionParens(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class TypeFunctionContext extends ParserRuleContext {
		public TypeFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode FUNCTION() { return getToken(ZenScriptParser.FUNCTION, 0); }

		public TerminalNode PAREN_OPEN() { return getToken(ZenScriptParser.PAREN_OPEN, 0); }

		public TerminalNode PAREN_CLOSE() { return getToken(ZenScriptParser.PAREN_CLOSE, 0); }

		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}

		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}

		public List<TerminalNode> COMMA() { return getTokens(ZenScriptParser.COMMA); }

		public TerminalNode COMMA(int i) {
			return getToken(ZenScriptParser.COMMA, i);
		}

		@Override public int getRuleIndex() { return RULE_typeFunction; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitTypeFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class AnonymousFunctionContext extends ExpressionContext {
		public AnonymousFunctionContext(ExpressionContext ctx) { copyFrom(ctx); }

		public TerminalNode FUNCTION() { return getToken(ZenScriptParser.FUNCTION, 0); }

		public ParametersContext parameters() {
			return getRuleContext(ParametersContext.class,0);
		}

		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}

		public AsTypeContext asType() {
			return getRuleContext(AsTypeContext.class,0);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitAnonymousFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class TypePrimitiveContext extends ParserRuleContext {
		public TypePrimitiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode ANY() { return getToken(ZenScriptParser.ANY, 0); }

		public TerminalNode BOOL() { return getToken(ZenScriptParser.BOOL, 0); }

		public TerminalNode BYTE() { return getToken(ZenScriptParser.BYTE, 0); }

		public TerminalNode SHORT() { return getToken(ZenScriptParser.SHORT, 0); }

		public TerminalNode INT() { return getToken(ZenScriptParser.INT, 0); }

		public TerminalNode LONG() { return getToken(ZenScriptParser.LONG, 0); }

		public TerminalNode FLOAT() { return getToken(ZenScriptParser.FLOAT, 0); }

		public TerminalNode DOUBLE() { return getToken(ZenScriptParser.DOUBLE, 0); }

		public TerminalNode STRING() { return getToken(ZenScriptParser.STRING, 0); }

		public TerminalNode VOID() { return getToken(ZenScriptParser.VOID, 0); }

		@Override public int getRuleIndex() { return RULE_typePrimitive; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitTypePrimitive(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ExpressionAssignContext extends ExpressionContext {
		public Token op;
		public ExpressionAssignContext(ExpressionContext ctx) { copyFrom(ctx); }

		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}

		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}

		public TerminalNode ASSIGN() { return getToken(ZenScriptParser.ASSIGN, 0); }

		public TerminalNode PLUS_ASSIGN() { return getToken(ZenScriptParser.PLUS_ASSIGN, 0); }

		public TerminalNode MINUS_ASSIGN() { return getToken(ZenScriptParser.MINUS_ASSIGN, 0); }

		public TerminalNode STAR_ASSIGN() { return getToken(ZenScriptParser.STAR_ASSIGN, 0); }

		public TerminalNode DIV_ASSIGN() { return getToken(ZenScriptParser.DIV_ASSIGN, 0); }

		public TerminalNode MOD_ASSIGN() { return getToken(ZenScriptParser.MOD_ASSIGN, 0); }

		public TerminalNode TILDE_ASSIGN() { return getToken(ZenScriptParser.TILDE_ASSIGN, 0); }

		public TerminalNode AND_ASSIGN() { return getToken(ZenScriptParser.AND_ASSIGN, 0); }

		public TerminalNode OR_ASSIGN() { return getToken(ZenScriptParser.OR_ASSIGN, 0); }

		public TerminalNode XOR_ASSIGN() { return getToken(ZenScriptParser.XOR_ASSIGN, 0); }

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionAssign(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class TypeArrayContext extends ParserRuleContext {
		public TypeArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TypePrimitiveContext typePrimitive() {
			return getRuleContext(TypePrimitiveContext.class,0);
		}

		public TypeClassContext typeClass() {
			return getRuleContext(TypeClassContext.class,0);
		}

		public List<TerminalNode> BRACK_OPEN() { return getTokens(ZenScriptParser.BRACK_OPEN); }

		public TerminalNode BRACK_OPEN(int i) {
			return getToken(ZenScriptParser.BRACK_OPEN, i);
		}

		public List<TerminalNode> BRACK_CLOSE() { return getTokens(ZenScriptParser.BRACK_CLOSE); }

		public TerminalNode BRACK_CLOSE(int i) {
			return getToken(ZenScriptParser.BRACK_CLOSE, i);
		}

		@Override public int getRuleIndex() { return RULE_typeArray; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitTypeArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ExpressionBinaryContext extends ExpressionContext {
		public Token op;
		public ExpressionBinaryContext(ExpressionContext ctx) { copyFrom(ctx); }

		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}

		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}

		public TerminalNode MUL() { return getToken(ZenScriptParser.MUL, 0); }

		public TerminalNode DIV() { return getToken(ZenScriptParser.DIV, 0); }

		public TerminalNode MOD() { return getToken(ZenScriptParser.MOD, 0); }

		public TerminalNode ADD() { return getToken(ZenScriptParser.ADD, 0); }

		public TerminalNode SUB() { return getToken(ZenScriptParser.SUB, 0); }

		public TerminalNode LESS_EQUAL() { return getToken(ZenScriptParser.LESS_EQUAL, 0); }

		public TerminalNode GREATER_EQUAL() { return getToken(ZenScriptParser.GREATER_EQUAL, 0); }

		public TerminalNode GREATER() { return getToken(ZenScriptParser.GREATER, 0); }

		public TerminalNode LESS() { return getToken(ZenScriptParser.LESS, 0); }

		public TerminalNode EQUAL() { return getToken(ZenScriptParser.EQUAL, 0); }

		public TerminalNode NOT_EQUAL() { return getToken(ZenScriptParser.NOT_EQUAL, 0); }

		public TerminalNode IN() { return getToken(ZenScriptParser.IN, 0); }

		public TerminalNode HAS() { return getToken(ZenScriptParser.HAS, 0); }

		public TerminalNode AND() { return getToken(ZenScriptParser.AND, 0); }

		public TerminalNode OR() { return getToken(ZenScriptParser.OR, 0); }

		public TerminalNode XOR() { return getToken(ZenScriptParser.XOR, 0); }

		public TerminalNode AND_AND() { return getToken(ZenScriptParser.AND_AND, 0); }

		public TerminalNode OR_OR() { return getToken(ZenScriptParser.OR_OR, 0); }

		public TerminalNode CAT() { return getToken(ZenScriptParser.CAT, 0); }

		public TerminalNode INSTANCEOF() { return getToken(ZenScriptParser.INSTANCEOF, 0); }

		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionBinary(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class TypeListContext extends ParserRuleContext {
		public TypeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public TerminalNode BRACK_OPEN() { return getToken(ZenScriptParser.BRACK_OPEN, 0); }

		public TerminalNode BRACK_CLOSE() { return getToken(ZenScriptParser.BRACK_CLOSE, 0); }

		public TypePrimitiveContext typePrimitive() {
			return getRuleContext(TypePrimitiveContext.class,0);
		}

		public TypeClassContext typeClass() {
			return getRuleContext(TypeClassContext.class,0);
		}

		@Override public int getRuleIndex() { return RULE_typeList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitTypeList(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ExpressionLiteralContext extends ExpressionContext {
		public ExpressionLiteralContext(ExpressionContext ctx) { copyFrom(ctx); }

		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class TypeMapContext extends ParserRuleContext {
		public TypeMapContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public List<TerminalNode> BRACK_OPEN() { return getTokens(ZenScriptParser.BRACK_OPEN); }

		public TerminalNode BRACK_OPEN(int i) {
			return getToken(ZenScriptParser.BRACK_OPEN, i);
		}

		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}

		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}

		public List<TerminalNode> BRACK_CLOSE() { return getTokens(ZenScriptParser.BRACK_CLOSE); }

		public TerminalNode BRACK_CLOSE(int i) {
			return getToken(ZenScriptParser.BRACK_CLOSE, i);
		}

		public TypePrimitiveContext typePrimitive() {
			return getRuleContext(TypePrimitiveContext.class,0);
		}

		public TypeClassContext typeClass() {
			return getRuleContext(TypeClassContext.class,0);
		}

		@Override public int getRuleIndex() { return RULE_typeMap; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitTypeMap(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ExpressionIdentifierContext extends ExpressionContext {
		public Token id;
		public ExpressionIdentifierContext(ExpressionContext ctx) { copyFrom(ctx); }

		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class TypeClassContext extends ParserRuleContext {
		public TypeClassContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public ClassNameContext className() {
			return getRuleContext(ClassNameContext.class,0);
		}

		@Override public int getRuleIndex() { return RULE_typeClass; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitTypeClass(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ExpressionIndexContext extends ExpressionContext {
		public ExpressionIndexContext(ExpressionContext ctx) { copyFrom(ctx); }

		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}

		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}

		public TerminalNode BRACK_OPEN() { return getToken(ZenScriptParser.BRACK_OPEN, 0); }

		public TerminalNode BRACK_CLOSE() { return getToken(ZenScriptParser.BRACK_CLOSE, 0); }

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionIndex(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class MapInitContext extends ExpressionContext {
		public MapInitContext(ExpressionContext ctx) { copyFrom(ctx); }

		public TerminalNode BRACE_OPEN() { return getToken(ZenScriptParser.BRACE_OPEN, 0); }

		public TerminalNode BRACE_CLOSE() { return getToken(ZenScriptParser.BRACE_CLOSE, 0); }

		public List<MapEntryContext> mapEntry() {
			return getRuleContexts(MapEntryContext.class);
		}

		public MapEntryContext mapEntry(int i) {
			return getRuleContext(MapEntryContext.class,i);
		}

		public List<TerminalNode> COMMA() { return getTokens(ZenScriptParser.COMMA); }

		public TerminalNode COMMA(int i) {
			return getToken(ZenScriptParser.COMMA, i);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitMapInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ExpressionCallContext extends ExpressionContext {
		public ExpressionCallContext(ExpressionContext ctx) { copyFrom(ctx); }

		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}

		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ExpressionAccessContext extends ExpressionContext {
		public ExpressionAccessContext(ExpressionContext ctx) { copyFrom(ctx); }

		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}

		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}

		public TerminalNode DOT() { return getToken(ZenScriptParser.DOT, 0); }

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionAccess(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class BracketHandlerContext extends ExpressionContext {
		public BracketHandlerContext(ExpressionContext ctx) { copyFrom(ctx); }

		public TerminalNode LESS() { return getToken(ZenScriptParser.LESS, 0); }

		public List<TerminalNode> GREATER() { return getTokens(ZenScriptParser.GREATER); }

		public TerminalNode GREATER(int i) {
			return getToken(ZenScriptParser.GREATER, i);
		}

		public List<TerminalNode> COLON() { return getTokens(ZenScriptParser.COLON); }

		public TerminalNode COLON(int i) {
			return getToken(ZenScriptParser.COLON, i);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitBracketHandler(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class LiteralContext extends ParserRuleContext {
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		public LiteralContext() { }

		@Override public int getRuleIndex() { return RULE_literal; }

		public void copyFrom(LiteralContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class NullLiteralContext extends LiteralContext {
		public NullLiteralContext(LiteralContext ctx) { copyFrom(ctx); }

		public TerminalNode NULL_LITERAL() { return getToken(ZenScriptParser.NULL_LITERAL, 0); }

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitNullLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class StringLiteralContext extends LiteralContext {
		public StringLiteralContext(LiteralContext ctx) { copyFrom(ctx); }

		public TerminalNode STRING_LITERAL() { return getToken(ZenScriptParser.STRING_LITERAL, 0); }

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class IntegerLiteralContext extends LiteralContext {
		public IntegerLiteralContext(LiteralContext ctx) { copyFrom(ctx); }

		public TerminalNode DECIMAL_LITERAL() { return getToken(ZenScriptParser.DECIMAL_LITERAL, 0); }

		public TerminalNode HEX_LITERAL() { return getToken(ZenScriptParser.HEX_LITERAL, 0); }

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitIntegerLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class BooleanLiteralContext extends LiteralContext {
		public BooleanLiteralContext(LiteralContext ctx) { copyFrom(ctx); }

		public TerminalNode BOOLEAN_LITERAL() { return getToken(ZenScriptParser.BOOLEAN_LITERAL, 0); }

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitBooleanLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class FloatingLiteralContext extends LiteralContext {
		public FloatingLiteralContext(LiteralContext ctx) { copyFrom(ctx); }

		public TerminalNode FLOATING_LITERAL() { return getToken(ZenScriptParser.FLOATING_LITERAL, 0); }

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitFloatingLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}