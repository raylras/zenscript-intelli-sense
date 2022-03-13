// Generated from D:/Projects/Forge/1.12.2/ZenServer/src/main/java/raylras/zen/antlr\ZenScriptParser.g4 by ANTLR 4.9.2
package raylras.zen.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

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
		ADD=43, SUB=44, MUL=45, DIV=46, MOD=47, TILDE=48, NOT=49, LESS=50, GREATER=51, 
		XOR=52, COLON=53, QUEST=54, BACKTICK=55, DOLLAR=56, AND=57, OR=58, ASSIGN=59, 
		AND_AND=60, OR_OR=61, EQUAL=62, NOT_EQUAL=63, LESS_EQUAL=64, GREATER_EQUAL=65, 
		PLUS_ASSIGN=66, MINUS_ASSIGN=67, STAR_ASSIGN=68, DIV_ASSIGN=69, MOD_ASSIGN=70, 
		XOR_ASSIGN=71, AND_ASSIGN=72, OR_ASSIGN=73, TILDE_ASSIGN=74, DOT_DOT=75, 
		DECIMAL_LITERAL=76, HEX_LITERAL=77, FLOATING_LITERAL=78, BOOLEAN_LITERAL=79, 
		STRING_LITERAL=80, NULL_LITERAL=81, IDENTIFIER=82, WHITE_SPACE=83, BLOCK_COMMENT=84, 
		LINE_COMMENT=85, Preprocessor=86;
	public static final int
		RULE_script = 0, RULE_importStatement = 1, RULE_functionDeclaration = 2, 
		RULE_zenClassDeclaration = 3, RULE_classBody = 4, RULE_constructor = 5, 
		RULE_field = 6, RULE_method = 7, RULE_asType = 8, RULE_memberCall = 9, 
		RULE_methodCall = 10, RULE_anonymousFunction = 11, RULE_parameters = 12, 
		RULE_parameter = 13, RULE_defaultValue = 14, RULE_arguments = 15, RULE_argument = 16, 
		RULE_block = 17, RULE_array = 18, RULE_map = 19, RULE_mapEntry = 20, RULE_mapKey = 21, 
		RULE_mapValue = 22, RULE_statement = 23, RULE_returnStatement = 24, RULE_breakStatement = 25, 
		RULE_continueStatement = 26, RULE_ifStatement = 27, RULE_forStatement = 28, 
		RULE_whileStatement = 29, RULE_varStatement = 30, RULE_expressionStatement = 31, 
		RULE_forControl = 32, RULE_range = 33, RULE_bounds = 34, RULE_className = 35, 
		RULE_expression = 36, RULE_type = 37, RULE_typeFunction = 38, RULE_typePrimitive = 39, 
		RULE_typeArray = 40, RULE_typeList = 41, RULE_typeMap = 42, RULE_typeClass = 43, 
		RULE_literal = 44, RULE_integerLiteral = 45, RULE_bracketHandler = 46;
	private static String[] makeRuleNames() {
		return new String[] {
			"script", "importStatement", "functionDeclaration", "zenClassDeclaration", 
			"classBody", "constructor", "field", "method", "asType", "memberCall", 
			"methodCall", "anonymousFunction", "parameters", "parameter", "defaultValue", 
			"arguments", "argument", "block", "array", "map", "mapEntry", "mapKey", 
			"mapValue", "statement", "returnStatement", "breakStatement", "continueStatement", 
			"ifStatement", "forStatement", "whileStatement", "varStatement", "expressionStatement", 
			"forControl", "range", "bounds", "className", "expression", "type", "typeFunction", 
			"typePrimitive", "typeArray", "typeList", "typeMap", "typeClass", "literal", 
			"integerLiteral", "bracketHandler"
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterScript(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitScript(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitScript(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScriptContext script() throws RecognitionException {
		ScriptContext _localctx = new ScriptContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_script);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC) | (1L << IMPORT) | (1L << FUNCTION) | (1L << IF) | (1L << FOR) | (1L << WHILE) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << ZEN_CLASS) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (DECIMAL_LITERAL - 76)) | (1L << (HEX_LITERAL - 76)) | (1L << (FLOATING_LITERAL - 76)) | (1L << (BOOLEAN_LITERAL - 76)) | (1L << (STRING_LITERAL - 76)) | (1L << (NULL_LITERAL - 76)) | (1L << (IDENTIFIER - 76)))) != 0)) {
				{
				setState(98);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(94);
					importStatement();
					}
					break;
				case 2:
					{
					setState(95);
					functionDeclaration();
					}
					break;
				case 3:
					{
					setState(96);
					zenClassDeclaration();
					}
					break;
				case 4:
					{
					setState(97);
					statement();
					}
					break;
				}
				}
				setState(102);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(103);
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

	public static class ImportStatementContext extends ParserRuleContext {
		public TerminalNode IMPORT() { return getToken(ZenScriptParser.IMPORT, 0); }
		public ClassNameContext className() {
			return getRuleContext(ClassNameContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }
		public TerminalNode AS() { return getToken(ZenScriptParser.AS, 0); }
		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }
		public ImportStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterImportStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitImportStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitImportStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportStatementContext importStatement() throws RecognitionException {
		ImportStatementContext _localctx = new ImportStatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_importStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			match(IMPORT);
			setState(106);
			className();
			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(107);
				match(AS);
				setState(108);
				match(IDENTIFIER);
				}
			}

			setState(111);
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

	public static class FunctionDeclarationContext extends ParserRuleContext {
		public TerminalNode FUNCTION() { return getToken(ZenScriptParser.FUNCTION, 0); }
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
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterFunctionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitFunctionDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_functionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(FUNCTION);
			setState(114);
			match(IDENTIFIER);
			setState(115);
			parameters();
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(116);
				asType();
				}
			}

			setState(119);
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

	public static class ZenClassDeclarationContext extends ParserRuleContext {
		public TerminalNode ZEN_CLASS() { return getToken(ZenScriptParser.ZEN_CLASS, 0); }
		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public ZenClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_zenClassDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterZenClassDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitZenClassDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitZenClassDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ZenClassDeclarationContext zenClassDeclaration() throws RecognitionException {
		ZenClassDeclarationContext _localctx = new ZenClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_zenClassDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			match(ZEN_CLASS);
			setState(122);
			match(IDENTIFIER);
			setState(123);
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

	public static class ClassBodyContext extends ParserRuleContext {
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
		public ClassBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterClassBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitClassBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitClassBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBodyContext classBody() throws RecognitionException {
		ClassBodyContext _localctx = new ClassBodyContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_classBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			match(BRACE_OPEN);
			setState(131);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << FUNCTION) | (1L << ZEN_CONSTRUCTOR))) != 0)) {
				{
				setState(129);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case ZEN_CONSTRUCTOR:
					{
					setState(126);
					constructor();
					}
					break;
				case VAR:
				case VAL:
					{
					setState(127);
					field();
					}
					break;
				case FUNCTION:
					{
					setState(128);
					method();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(133);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(134);
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

	public static class ConstructorContext extends ParserRuleContext {
		public TerminalNode ZEN_CONSTRUCTOR() { return getToken(ZenScriptParser.ZEN_CONSTRUCTOR, 0); }
		public ParametersContext parameters() {
			return getRuleContext(ParametersContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ConstructorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterConstructor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitConstructor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitConstructor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorContext constructor() throws RecognitionException {
		ConstructorContext _localctx = new ConstructorContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_constructor);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			match(ZEN_CONSTRUCTOR);
			setState(137);
			parameters();
			setState(138);
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

	public static class FieldContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }
		public List<AsTypeContext> asType() {
			return getRuleContexts(AsTypeContext.class);
		}
		public AsTypeContext asType(int i) {
			return getRuleContext(AsTypeContext.class,i);
		}
		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }
		public TerminalNode VAR() { return getToken(ZenScriptParser.VAR, 0); }
		public TerminalNode VAL() { return getToken(ZenScriptParser.VAL, 0); }
		public TerminalNode ASSIGN() { return getToken(ZenScriptParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_field; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldContext field() throws RecognitionException {
		FieldContext _localctx = new FieldContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_field);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			_la = _input.LA(1);
			if ( !(_la==VAR || _la==VAL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(141);
			match(IDENTIFIER);
			setState(142);
			asType();
			setState(148);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(143);
				match(ASSIGN);
				setState(144);
				expression(0);
				setState(146);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(145);
					asType();
					}
				}

				}
			}

			setState(150);
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

	public static class MethodContext extends ParserRuleContext {
		public TerminalNode FUNCTION() { return getToken(ZenScriptParser.FUNCTION, 0); }
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
		public MethodContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterMethod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitMethod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitMethod(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodContext method() throws RecognitionException {
		MethodContext _localctx = new MethodContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_method);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			match(FUNCTION);
			setState(153);
			match(IDENTIFIER);
			setState(154);
			parameters();
			setState(156);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(155);
				asType();
				}
			}

			setState(158);
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

	public static class AsTypeContext extends ParserRuleContext {
		public TerminalNode AS() { return getToken(ZenScriptParser.AS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public AsTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_asType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterAsType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitAsType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitAsType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AsTypeContext asType() throws RecognitionException {
		AsTypeContext _localctx = new AsTypeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_asType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			match(AS);
			setState(161);
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

	public static class MemberCallContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }
		public MemberCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterMemberCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitMemberCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitMemberCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MemberCallContext memberCall() throws RecognitionException {
		MemberCallContext _localctx = new MemberCallContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_memberCall);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
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

	public static class MethodCallContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public MethodCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterMethodCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitMethodCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitMethodCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodCallContext methodCall() throws RecognitionException {
		MethodCallContext _localctx = new MethodCallContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_methodCall);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			match(IDENTIFIER);
			setState(166);
			arguments();
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

	public static class AnonymousFunctionContext extends ParserRuleContext {
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
		public AnonymousFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anonymousFunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterAnonymousFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitAnonymousFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitAnonymousFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnonymousFunctionContext anonymousFunction() throws RecognitionException {
		AnonymousFunctionContext _localctx = new AnonymousFunctionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_anonymousFunction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			match(FUNCTION);
			setState(169);
			parameters();
			setState(171);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(170);
				asType();
				}
			}

			setState(173);
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

	public static class ParametersContext extends ParserRuleContext {
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
		public ParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitParameters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParametersContext parameters() throws RecognitionException {
		ParametersContext _localctx = new ParametersContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_parameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			match(PAREN_OPEN);
			setState(177);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(176);
				parameter();
				}
			}

			setState(183);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(179);
				match(COMMA);
				setState(180);
				parameter();
				}
				}
				setState(185);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(186);
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

	public static class ParameterContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }
		public AsTypeContext asType() {
			return getRuleContext(AsTypeContext.class,0);
		}
		public DefaultValueContext defaultValue() {
			return getRuleContext(DefaultValueContext.class,0);
		}
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_parameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			match(IDENTIFIER);
			setState(190);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(189);
				asType();
				}
			}

			setState(193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(192);
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

	public static class DefaultValueContext extends ParserRuleContext {
		public TerminalNode ASSIGN() { return getToken(ZenScriptParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DefaultValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defaultValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterDefaultValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitDefaultValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitDefaultValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefaultValueContext defaultValue() throws RecognitionException {
		DefaultValueContext _localctx = new DefaultValueContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_defaultValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(195);
			match(ASSIGN);
			setState(196);
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

	public static class ArgumentsContext extends ParserRuleContext {
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
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitArguments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			match(PAREN_OPEN);
			{
			setState(200);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (DECIMAL_LITERAL - 76)) | (1L << (HEX_LITERAL - 76)) | (1L << (FLOATING_LITERAL - 76)) | (1L << (BOOLEAN_LITERAL - 76)) | (1L << (STRING_LITERAL - 76)) | (1L << (NULL_LITERAL - 76)) | (1L << (IDENTIFIER - 76)))) != 0)) {
				{
				setState(199);
				argument();
				}
			}

			setState(206);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(202);
				match(COMMA);
				setState(203);
				argument();
				}
				}
				setState(208);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(209);
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

	public static class ArgumentContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public AsTypeContext asType() {
			return getRuleContext(AsTypeContext.class,0);
		}
		public AnonymousFunctionContext anonymousFunction() {
			return getRuleContext(AnonymousFunctionContext.class,0);
		}
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_argument);
		int _la;
		try {
			setState(220);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(214);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
				case 1:
					{
					setState(211);
					match(IDENTIFIER);
					}
					break;
				case 2:
					{
					setState(212);
					expression(0);
					}
					break;
				case 3:
					{
					setState(213);
					literal();
					}
					break;
				}
				setState(217);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(216);
					asType();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(219);
				anonymousFunction();
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

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode BRACE_OPEN() { return getToken(ZenScriptParser.BRACE_OPEN, 0); }
		public TerminalNode BRACE_CLOSE() { return getToken(ZenScriptParser.BRACE_CLOSE, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			match(BRACE_OPEN);
			setState(226);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC) | (1L << FUNCTION) | (1L << IF) | (1L << FOR) | (1L << WHILE) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (DECIMAL_LITERAL - 76)) | (1L << (HEX_LITERAL - 76)) | (1L << (FLOATING_LITERAL - 76)) | (1L << (BOOLEAN_LITERAL - 76)) | (1L << (STRING_LITERAL - 76)) | (1L << (NULL_LITERAL - 76)) | (1L << (IDENTIFIER - 76)))) != 0)) {
				{
				{
				setState(223);
				statement();
				}
				}
				setState(228);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(229);
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

	public static class ArrayContext extends ParserRuleContext {
		public TerminalNode BRACK_OPEN() { return getToken(ZenScriptParser.BRACK_OPEN, 0); }
		public TerminalNode BRACK_CLOSE() { return getToken(ZenScriptParser.BRACK_CLOSE, 0); }
		public List<ArrayContext> array() {
			return getRuleContexts(ArrayContext.class);
		}
		public ArrayContext array(int i) {
			return getRuleContext(ArrayContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ZenScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ZenScriptParser.COMMA, i);
		}
		public List<LiteralContext> literal() {
			return getRuleContexts(LiteralContext.class);
		}
		public LiteralContext literal(int i) {
			return getRuleContext(LiteralContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayContext array() throws RecognitionException {
		ArrayContext _localctx = new ArrayContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_array);
		int _la;
		try {
			setState(260);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(231);
				match(BRACK_OPEN);
				setState(233);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==BRACK_OPEN) {
					{
					setState(232);
					array();
					}
				}

				setState(239);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(235);
					match(COMMA);
					setState(236);
					array();
					}
					}
					setState(241);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(242);
				match(BRACK_CLOSE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(243);
				match(BRACK_OPEN);
				setState(246);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
				case 1:
					{
					setState(244);
					literal();
					}
					break;
				case 2:
					{
					setState(245);
					expression(0);
					}
					break;
				}
				setState(255);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(248);
					match(COMMA);
					setState(251);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
					case 1:
						{
						setState(249);
						literal();
						}
						break;
					case 2:
						{
						setState(250);
						expression(0);
						}
						break;
					}
					}
					}
					setState(257);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(258);
				match(BRACK_CLOSE);
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

	public static class MapContext extends ParserRuleContext {
		public TerminalNode BRACE_OPEN() { return getToken(ZenScriptParser.BRACE_OPEN, 0); }
		public TerminalNode BRACE_CLOSE() { return getToken(ZenScriptParser.BRACE_CLOSE, 0); }
		public List<MapContext> map() {
			return getRuleContexts(MapContext.class);
		}
		public MapContext map(int i) {
			return getRuleContext(MapContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ZenScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ZenScriptParser.COMMA, i);
		}
		public List<MapEntryContext> mapEntry() {
			return getRuleContexts(MapEntryContext.class);
		}
		public MapEntryContext mapEntry(int i) {
			return getRuleContext(MapEntryContext.class,i);
		}
		public MapContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_map; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterMap(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitMap(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitMap(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MapContext map() throws RecognitionException {
		MapContext _localctx = new MapContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_map);
		int _la;
		try {
			setState(286);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(262);
				match(BRACE_OPEN);
				setState(264);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==BRACE_OPEN) {
					{
					setState(263);
					map();
					}
				}

				setState(270);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(266);
					match(COMMA);
					setState(267);
					map();
					}
					}
					setState(272);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(273);
				match(BRACE_CLOSE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(274);
				match(BRACE_OPEN);
				setState(283);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (DECIMAL_LITERAL - 76)) | (1L << (HEX_LITERAL - 76)) | (1L << (FLOATING_LITERAL - 76)) | (1L << (BOOLEAN_LITERAL - 76)) | (1L << (STRING_LITERAL - 76)) | (1L << (NULL_LITERAL - 76)) | (1L << (IDENTIFIER - 76)))) != 0)) {
					{
					setState(275);
					mapEntry();
					setState(280);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(276);
						match(COMMA);
						setState(277);
						mapEntry();
						}
						}
						setState(282);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(285);
				match(BRACE_CLOSE);
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

	public static class MapEntryContext extends ParserRuleContext {
		public MapKeyContext mapKey() {
			return getRuleContext(MapKeyContext.class,0);
		}
		public TerminalNode COLON() { return getToken(ZenScriptParser.COLON, 0); }
		public MapValueContext mapValue() {
			return getRuleContext(MapValueContext.class,0);
		}
		public MapEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterMapEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitMapEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitMapEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MapEntryContext mapEntry() throws RecognitionException {
		MapEntryContext _localctx = new MapEntryContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_mapEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			mapKey();
			setState(289);
			match(COLON);
			setState(290);
			mapValue();
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

	public static class MapKeyContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public MapKeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapKey; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterMapKey(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitMapKey(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitMapKey(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MapKeyContext mapKey() throws RecognitionException {
		MapKeyContext _localctx = new MapKeyContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_mapKey);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(292);
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

	public static class MapValueContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public MapValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterMapValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitMapValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitMapValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MapValueContext mapValue() throws RecognitionException {
		MapValueContext _localctx = new MapValueContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_mapValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(294);
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

	public static class StatementContext extends ParserRuleContext {
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
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_statement);
		try {
			setState(305);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(296);
				returnStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(297);
				breakStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(298);
				continueStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(299);
				ifStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(300);
				forStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(301);
				whileStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(302);
				varStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(303);
				functionDeclaration();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(304);
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

	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(ZenScriptParser.RETURN, 0); }
		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitReturnStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(307);
			match(RETURN);
			setState(309);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (DECIMAL_LITERAL - 76)) | (1L << (HEX_LITERAL - 76)) | (1L << (FLOATING_LITERAL - 76)) | (1L << (BOOLEAN_LITERAL - 76)) | (1L << (STRING_LITERAL - 76)) | (1L << (NULL_LITERAL - 76)) | (1L << (IDENTIFIER - 76)))) != 0)) {
				{
				setState(308);
				expression(0);
				}
			}

			setState(311);
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
		public TerminalNode BREAK() { return getToken(ZenScriptParser.BREAK, 0); }
		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterBreakStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitBreakStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitBreakStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			match(BREAK);
			setState(314);
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
		public TerminalNode CONTINUE() { return getToken(ZenScriptParser.CONTINUE, 0); }
		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }
		public ContinueStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continueStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterContinueStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitContinueStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitContinueStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContinueStatementContext continueStatement() throws RecognitionException {
		ContinueStatementContext _localctx = new ContinueStatementContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(316);
			match(CONTINUE);
			setState(317);
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
		public TerminalNode IF() { return getToken(ZenScriptParser.IF, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
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
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(319);
			match(IF);
			setState(320);
			expression(0);
			setState(324);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				{
				setState(321);
				statement();
				}
				break;
			case 2:
				{
				setState(322);
				block();
				}
				break;
			case 3:
				{
				}
				break;
			}
			setState(332);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				{
				setState(326);
				match(ELSE);
				setState(330);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
				case 1:
					{
					setState(327);
					statement();
					}
					break;
				case 2:
					{
					setState(328);
					block();
					}
					break;
				case 3:
					{
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

	public static class ForStatementContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(ZenScriptParser.FOR, 0); }
		public ForControlContext forControl() {
			return getRuleContext(ForControlContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterForStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitForStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_forStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(334);
			match(FOR);
			setState(335);
			forControl();
			setState(336);
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

	public static class WhileStatementContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(ZenScriptParser.WHILE, 0); }
		public TerminalNode PAREN_OPEN() { return getToken(ZenScriptParser.PAREN_OPEN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode PAREN_CLOSE() { return getToken(ZenScriptParser.PAREN_CLOSE, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(338);
			match(WHILE);
			setState(339);
			match(PAREN_OPEN);
			setState(340);
			expression(0);
			setState(341);
			match(PAREN_CLOSE);
			setState(342);
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

	public static class VarStatementContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }
		public TerminalNode ASSIGN() { return getToken(ZenScriptParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }
		public TerminalNode VAR() { return getToken(ZenScriptParser.VAR, 0); }
		public TerminalNode VAL() { return getToken(ZenScriptParser.VAL, 0); }
		public TerminalNode GLOBAL() { return getToken(ZenScriptParser.GLOBAL, 0); }
		public TerminalNode STATIC() { return getToken(ZenScriptParser.STATIC, 0); }
		public AsTypeContext asType() {
			return getRuleContext(AsTypeContext.class,0);
		}
		public VarStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterVarStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitVarStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitVarStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarStatementContext varStatement() throws RecognitionException {
		VarStatementContext _localctx = new VarStatementContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_varStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(344);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(345);
			match(IDENTIFIER);
			setState(347);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(346);
				asType();
				}
			}

			setState(349);
			match(ASSIGN);
			setState(350);
			expression(0);
			setState(351);
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

	public static class ExpressionStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(353);
			expression(0);
			setState(354);
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

	public static class ForControlContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(ZenScriptParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(ZenScriptParser.IDENTIFIER, i);
		}
		public TerminalNode IN() { return getToken(ZenScriptParser.IN, 0); }
		public RangeContext range() {
			return getRuleContext(RangeContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public MemberCallContext memberCall() {
			return getRuleContext(MemberCallContext.class,0);
		}
		public MethodCallContext methodCall() {
			return getRuleContext(MethodCallContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(ZenScriptParser.COMMA, 0); }
		public ForControlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forControl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterForControl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitForControl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitForControl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForControlContext forControl() throws RecognitionException {
		ForControlContext _localctx = new ForControlContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_forControl);
		int _la;
		try {
			setState(370);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(356);
				match(IDENTIFIER);
				setState(357);
				match(IN);
				setState(358);
				range();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(359);
				match(IDENTIFIER);
				setState(362);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(360);
					match(COMMA);
					setState(361);
					match(IDENTIFIER);
					}
				}

				setState(364);
				match(IN);
				setState(368);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
				case 1:
					{
					setState(365);
					expression(0);
					}
					break;
				case 2:
					{
					setState(366);
					memberCall();
					}
					break;
				case 3:
					{
					setState(367);
					methodCall();
					}
					break;
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

	public static class RangeContext extends ParserRuleContext {
		public List<BoundsContext> bounds() {
			return getRuleContexts(BoundsContext.class);
		}
		public BoundsContext bounds(int i) {
			return getRuleContext(BoundsContext.class,i);
		}
		public TerminalNode DOT_DOT() { return getToken(ZenScriptParser.DOT_DOT, 0); }
		public TerminalNode TO() { return getToken(ZenScriptParser.TO, 0); }
		public RangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_range; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterRange(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitRange(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitRange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RangeContext range() throws RecognitionException {
		RangeContext _localctx = new RangeContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_range);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(372);
			bounds();
			setState(373);
			_la = _input.LA(1);
			if ( !(_la==TO || _la==DOT_DOT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(374);
			bounds();
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

	public static class BoundsContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public MemberCallContext memberCall() {
			return getRuleContext(MemberCallContext.class,0);
		}
		public MethodCallContext methodCall() {
			return getRuleContext(MethodCallContext.class,0);
		}
		public BoundsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bounds; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterBounds(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitBounds(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitBounds(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoundsContext bounds() throws RecognitionException {
		BoundsContext _localctx = new BoundsContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_bounds);
		try {
			setState(379);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(376);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(377);
				memberCall();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(378);
				methodCall();
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

	public static class ClassNameContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(ZenScriptParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(ZenScriptParser.IDENTIFIER, i);
		}
		public List<TerminalNode> DOT() { return getTokens(ZenScriptParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(ZenScriptParser.DOT, i);
		}
		public ClassNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_className; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterClassName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitClassName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitClassName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassNameContext className() throws RecognitionException {
		ClassNameContext _localctx = new ClassNameContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_className);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(381);
			match(IDENTIFIER);
			setState(386);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(382);
					match(DOT);
					setState(383);
					match(IDENTIFIER);
					}
					} 
				}
				setState(388);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
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

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ExpressionArrayGetContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode BRACK_OPEN() { return getToken(ZenScriptParser.BRACK_OPEN, 0); }
		public TerminalNode BRACK_CLOSE() { return getToken(ZenScriptParser.BRACK_CLOSE, 0); }
		public ExpressionArrayGetContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionArrayGet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionArrayGet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionArrayGet(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionFunctionContext extends ExpressionContext {
		public AnonymousFunctionContext anonymousFunction() {
			return getRuleContext(AnonymousFunctionContext.class,0);
		}
		public ExpressionFunctionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionFunction(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionUnaryContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ADD() { return getToken(ZenScriptParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(ZenScriptParser.SUB, 0); }
		public TerminalNode NOT() { return getToken(ZenScriptParser.NOT, 0); }
		public ExpressionUnaryContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionUnary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionUnary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionUnary(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionParensContext extends ExpressionContext {
		public TerminalNode PAREN_OPEN() { return getToken(ZenScriptParser.PAREN_OPEN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode PAREN_CLOSE() { return getToken(ZenScriptParser.PAREN_CLOSE, 0); }
		public ExpressionParensContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionParens(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionParens(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionParens(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionAssignContext extends ExpressionContext {
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
		public TerminalNode AND_ASSIGN() { return getToken(ZenScriptParser.AND_ASSIGN, 0); }
		public TerminalNode OR_ASSIGN() { return getToken(ZenScriptParser.OR_ASSIGN, 0); }
		public TerminalNode XOR_ASSIGN() { return getToken(ZenScriptParser.XOR_ASSIGN, 0); }
		public TerminalNode MOD_ASSIGN() { return getToken(ZenScriptParser.MOD_ASSIGN, 0); }
		public TerminalNode TILDE_ASSIGN() { return getToken(ZenScriptParser.TILDE_ASSIGN, 0); }
		public ExpressionAssignContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionAssign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionAssign(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionMapContext extends ExpressionContext {
		public MapContext map() {
			return getRuleContext(MapContext.class,0);
		}
		public ExpressionMapContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionMap(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionMap(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionMap(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionBinaryContext extends ExpressionContext {
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
		public TerminalNode XOR() { return getToken(ZenScriptParser.XOR, 0); }
		public TerminalNode OR() { return getToken(ZenScriptParser.OR, 0); }
		public TerminalNode AND_AND() { return getToken(ZenScriptParser.AND_AND, 0); }
		public TerminalNode OR_OR() { return getToken(ZenScriptParser.OR_OR, 0); }
		public TerminalNode TILDE() { return getToken(ZenScriptParser.TILDE, 0); }
		public TerminalNode INSTANCEOF() { return getToken(ZenScriptParser.INSTANCEOF, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ExpressionBinaryContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionBinary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionBinary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionBinary(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionLiteralContext extends ExpressionContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public ExpressionLiteralContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionTrinaryContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode QUEST() { return getToken(ZenScriptParser.QUEST, 0); }
		public TerminalNode COLON() { return getToken(ZenScriptParser.COLON, 0); }
		public ExpressionTrinaryContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionTrinary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionTrinary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionTrinary(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionCallContext extends ExpressionContext {
		public MethodCallContext methodCall() {
			return getRuleContext(MethodCallContext.class,0);
		}
		public MemberCallContext memberCall() {
			return getRuleContext(MemberCallContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode DOT() { return getToken(ZenScriptParser.DOT, 0); }
		public ExpressionCallContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionCastContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AsTypeContext asType() {
			return getRuleContext(AsTypeContext.class,0);
		}
		public ExpressionCastContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionCast(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionCast(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionCast(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionArrayContext extends ExpressionContext {
		public ArrayContext array() {
			return getRuleContext(ArrayContext.class,0);
		}
		public ExpressionArrayContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionArray(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionBracketHandlerContext extends ExpressionContext {
		public BracketHandlerContext bracketHandler() {
			return getRuleContext(BracketHandlerContext.class,0);
		}
		public ExpressionBracketHandlerContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionBracketHandler(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionBracketHandler(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionBracketHandler(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 72;
		enterRecursionRule(_localctx, 72, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(403);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				{
				_localctx = new ExpressionLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(390);
				literal();
				}
				break;
			case 2:
				{
				_localctx = new ExpressionParensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(391);
				match(PAREN_OPEN);
				setState(392);
				expression(0);
				setState(393);
				match(PAREN_CLOSE);
				}
				break;
			case 3:
				{
				_localctx = new ExpressionCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(395);
				methodCall();
				}
				break;
			case 4:
				{
				_localctx = new ExpressionCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(396);
				memberCall();
				}
				break;
			case 5:
				{
				_localctx = new ExpressionUnaryContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(397);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADD) | (1L << SUB) | (1L << NOT))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(398);
				expression(19);
				}
				break;
			case 6:
				{
				_localctx = new ExpressionBracketHandlerContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(399);
				bracketHandler();
				}
				break;
			case 7:
				{
				_localctx = new ExpressionArrayContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(400);
				array();
				}
				break;
			case 8:
				{
				_localctx = new ExpressionMapContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(401);
				map();
				}
				break;
			case 9:
				{
				_localctx = new ExpressionFunctionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(402);
				anonymousFunction();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(459);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(457);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionCallContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(405);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(406);
						match(DOT);
						setState(407);
						expression(24);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(408);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(409);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << MOD))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(410);
						expression(19);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(411);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(412);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(413);
						expression(18);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(414);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(415);
						_la = _input.LA(1);
						if ( !(((((_la - 50)) & ~0x3f) == 0 && ((1L << (_la - 50)) & ((1L << (LESS - 50)) | (1L << (GREATER - 50)) | (1L << (EQUAL - 50)) | (1L << (NOT_EQUAL - 50)) | (1L << (LESS_EQUAL - 50)) | (1L << (GREATER_EQUAL - 50)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(416);
						expression(17);
						}
						break;
					case 5:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(417);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(418);
						_la = _input.LA(1);
						if ( !(_la==IN || _la==HAS) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(419);
						expression(15);
						}
						break;
					case 6:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(420);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(421);
						match(AND);
						setState(422);
						expression(14);
						}
						break;
					case 7:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(423);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(424);
						match(XOR);
						setState(425);
						expression(13);
						}
						break;
					case 8:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(426);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(427);
						match(OR);
						setState(428);
						expression(12);
						}
						break;
					case 9:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(429);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(430);
						match(AND_AND);
						setState(431);
						expression(11);
						}
						break;
					case 10:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(432);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(433);
						match(OR_OR);
						setState(434);
						expression(10);
						}
						break;
					case 11:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(435);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(436);
						match(TILDE);
						setState(437);
						expression(9);
						}
						break;
					case 12:
						{
						_localctx = new ExpressionTrinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(438);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(439);
						match(QUEST);
						setState(440);
						expression(0);
						setState(441);
						match(COLON);
						setState(442);
						expression(7);
						}
						break;
					case 13:
						{
						_localctx = new ExpressionAssignContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(444);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(445);
						_la = _input.LA(1);
						if ( !(((((_la - 59)) & ~0x3f) == 0 && ((1L << (_la - 59)) & ((1L << (ASSIGN - 59)) | (1L << (PLUS_ASSIGN - 59)) | (1L << (MINUS_ASSIGN - 59)) | (1L << (STAR_ASSIGN - 59)) | (1L << (DIV_ASSIGN - 59)) | (1L << (MOD_ASSIGN - 59)) | (1L << (XOR_ASSIGN - 59)) | (1L << (AND_ASSIGN - 59)) | (1L << (OR_ASSIGN - 59)) | (1L << (TILDE_ASSIGN - 59)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(446);
						expression(6);
						}
						break;
					case 14:
						{
						_localctx = new ExpressionArrayGetContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(447);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(448);
						match(BRACK_OPEN);
						setState(449);
						expression(0);
						setState(450);
						match(BRACK_CLOSE);
						}
						break;
					case 15:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(452);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(453);
						match(INSTANCEOF);
						setState(454);
						type();
						}
						break;
					case 16:
						{
						_localctx = new ExpressionCastContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(455);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(456);
						asType();
						}
						break;
					}
					} 
				}
				setState(461);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
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

	public static class TypeContext extends ParserRuleContext {
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
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(468);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
			case 1:
				{
				setState(462);
				typeList();
				}
				break;
			case 2:
				{
				setState(463);
				typeMap();
				}
				break;
			case 3:
				{
				setState(464);
				typeArray();
				}
				break;
			case 4:
				{
				setState(465);
				typePrimitive();
				}
				break;
			case 5:
				{
				setState(466);
				typeClass();
				}
				break;
			case 6:
				{
				setState(467);
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

	public static class TypeFunctionContext extends ParserRuleContext {
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
		public TypeFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeFunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterTypeFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitTypeFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitTypeFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeFunctionContext typeFunction() throws RecognitionException {
		TypeFunctionContext _localctx = new TypeFunctionContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_typeFunction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(470);
			match(FUNCTION);
			setState(471);
			match(PAREN_OPEN);
			setState(473);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << ANY) | (1L << BYTE) | (1L << SHORT) | (1L << INT) | (1L << LONG) | (1L << FLOAT) | (1L << DOUBLE) | (1L << BOOL) | (1L << VOID) | (1L << STRING) | (1L << BRACK_OPEN))) != 0) || _la==IDENTIFIER) {
				{
				setState(472);
				type();
				}
			}

			setState(479);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(475);
				match(COMMA);
				setState(476);
				type();
				}
				}
				setState(481);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(482);
			match(PAREN_CLOSE);
			setState(483);
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

	public static class TypePrimitiveContext extends ParserRuleContext {
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
		public TypePrimitiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typePrimitive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterTypePrimitive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitTypePrimitive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitTypePrimitive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypePrimitiveContext typePrimitive() throws RecognitionException {
		TypePrimitiveContext _localctx = new TypePrimitiveContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_typePrimitive);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(485);
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

	public static class TypeArrayContext extends ParserRuleContext {
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
		public TypeArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArray; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterTypeArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitTypeArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitTypeArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArrayContext typeArray() throws RecognitionException {
		TypeArrayContext _localctx = new TypeArrayContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_typeArray);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(489);
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
				setState(487);
				typePrimitive();
				}
				break;
			case IDENTIFIER:
				{
				setState(488);
				typeClass();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(493); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(491);
					match(BRACK_OPEN);
					setState(492);
					match(BRACK_CLOSE);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(495); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
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

	public static class TypeListContext extends ParserRuleContext {
		public TerminalNode BRACK_OPEN() { return getToken(ZenScriptParser.BRACK_OPEN, 0); }
		public TerminalNode BRACK_CLOSE() { return getToken(ZenScriptParser.BRACK_CLOSE, 0); }
		public TypePrimitiveContext typePrimitive() {
			return getRuleContext(TypePrimitiveContext.class,0);
		}
		public TypeClassContext typeClass() {
			return getRuleContext(TypeClassContext.class,0);
		}
		public TypeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterTypeList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitTypeList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitTypeList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeListContext typeList() throws RecognitionException {
		TypeListContext _localctx = new TypeListContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_typeList);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(497);
			match(BRACK_OPEN);
			setState(500);
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
				setState(498);
				typePrimitive();
				}
				break;
			case IDENTIFIER:
				{
				setState(499);
				typeClass();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(502);
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

	public static class TypeMapContext extends ParserRuleContext {
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
		public TypeMapContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeMap; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterTypeMap(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitTypeMap(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitTypeMap(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeMapContext typeMap() throws RecognitionException {
		TypeMapContext _localctx = new TypeMapContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_typeMap);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(506);
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
				setState(504);
				typePrimitive();
				}
				break;
			case IDENTIFIER:
				{
				setState(505);
				typeClass();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(515);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(508);
					match(BRACK_OPEN);
					setState(510);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << ANY) | (1L << BYTE) | (1L << SHORT) | (1L << INT) | (1L << LONG) | (1L << FLOAT) | (1L << DOUBLE) | (1L << BOOL) | (1L << VOID) | (1L << STRING) | (1L << BRACK_OPEN))) != 0) || _la==IDENTIFIER) {
						{
						setState(509);
						type();
						}
					}

					setState(512);
					match(BRACK_CLOSE);
					}
					} 
				}
				setState(517);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
			}
			setState(518);
			match(BRACK_OPEN);
			setState(519);
			type();
			setState(520);
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

	public static class TypeClassContext extends ParserRuleContext {
		public ClassNameContext className() {
			return getRuleContext(ClassNameContext.class,0);
		}
		public TypeClassContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeClass; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterTypeClass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitTypeClass(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitTypeClass(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeClassContext typeClass() throws RecognitionException {
		TypeClassContext _localctx = new TypeClassContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_typeClass);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(522);
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

	public static class LiteralContext extends ParserRuleContext {
		public IntegerLiteralContext integerLiteral() {
			return getRuleContext(IntegerLiteralContext.class,0);
		}
		public TerminalNode FLOATING_LITERAL() { return getToken(ZenScriptParser.FLOATING_LITERAL, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(ZenScriptParser.STRING_LITERAL, 0); }
		public TerminalNode BOOLEAN_LITERAL() { return getToken(ZenScriptParser.BOOLEAN_LITERAL, 0); }
		public TerminalNode NULL_LITERAL() { return getToken(ZenScriptParser.NULL_LITERAL, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_literal);
		try {
			setState(529);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DECIMAL_LITERAL:
			case HEX_LITERAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(524);
				integerLiteral();
				}
				break;
			case FLOATING_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(525);
				match(FLOATING_LITERAL);
				}
				break;
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 3);
				{
				setState(526);
				match(STRING_LITERAL);
				}
				break;
			case BOOLEAN_LITERAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(527);
				match(BOOLEAN_LITERAL);
				}
				break;
			case NULL_LITERAL:
				enterOuterAlt(_localctx, 5);
				{
				setState(528);
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

	public static class IntegerLiteralContext extends ParserRuleContext {
		public TerminalNode DECIMAL_LITERAL() { return getToken(ZenScriptParser.DECIMAL_LITERAL, 0); }
		public TerminalNode HEX_LITERAL() { return getToken(ZenScriptParser.HEX_LITERAL, 0); }
		public IntegerLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integerLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterIntegerLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitIntegerLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitIntegerLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntegerLiteralContext integerLiteral() throws RecognitionException {
		IntegerLiteralContext _localctx = new IntegerLiteralContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_integerLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(531);
			_la = _input.LA(1);
			if ( !(_la==DECIMAL_LITERAL || _la==HEX_LITERAL) ) {
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

	public static class BracketHandlerContext extends ParserRuleContext {
		public TerminalNode LESS() { return getToken(ZenScriptParser.LESS, 0); }
		public List<TerminalNode> GREATER() { return getTokens(ZenScriptParser.GREATER); }
		public TerminalNode GREATER(int i) {
			return getToken(ZenScriptParser.GREATER, i);
		}
		public List<TerminalNode> COLON() { return getTokens(ZenScriptParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(ZenScriptParser.COLON, i);
		}
		public BracketHandlerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bracketHandler; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterBracketHandler(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitBracketHandler(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitBracketHandler(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BracketHandlerContext bracketHandler() throws RecognitionException {
		BracketHandlerContext _localctx = new BracketHandlerContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_bracketHandler);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(533);
			match(LESS);
			setState(540);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC) | (1L << IMPORT) | (1L << FUNCTION) | (1L << AS) | (1L << TO) | (1L << IN) | (1L << HAS) | (1L << INSTANCEOF) | (1L << ANY) | (1L << BYTE) | (1L << SHORT) | (1L << INT) | (1L << LONG) | (1L << FLOAT) | (1L << DOUBLE) | (1L << BOOL) | (1L << VOID) | (1L << STRING) | (1L << IF) | (1L << ELSE) | (1L << FOR) | (1L << DO) | (1L << WHILE) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << FRIGGIN_CLASS) | (1L << FRIGGIN_CONSTRUCTOR) | (1L << ZEN_CLASS) | (1L << ZEN_CONSTRUCTOR) | (1L << PAREN_OPEN) | (1L << PAREN_CLOSE) | (1L << BRACK_OPEN) | (1L << BRACK_CLOSE) | (1L << BRACE_OPEN) | (1L << BRACE_CLOSE) | (1L << COMMA) | (1L << DOT) | (1L << SEMICOLON) | (1L << ADD) | (1L << SUB) | (1L << MUL) | (1L << DIV) | (1L << MOD) | (1L << TILDE) | (1L << NOT) | (1L << LESS) | (1L << XOR) | (1L << COLON) | (1L << QUEST) | (1L << BACKTICK) | (1L << DOLLAR) | (1L << AND) | (1L << OR) | (1L << ASSIGN) | (1L << AND_AND) | (1L << OR_OR) | (1L << EQUAL) | (1L << NOT_EQUAL))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (LESS_EQUAL - 64)) | (1L << (GREATER_EQUAL - 64)) | (1L << (PLUS_ASSIGN - 64)) | (1L << (MINUS_ASSIGN - 64)) | (1L << (STAR_ASSIGN - 64)) | (1L << (DIV_ASSIGN - 64)) | (1L << (MOD_ASSIGN - 64)) | (1L << (XOR_ASSIGN - 64)) | (1L << (AND_ASSIGN - 64)) | (1L << (OR_ASSIGN - 64)) | (1L << (TILDE_ASSIGN - 64)) | (1L << (DOT_DOT - 64)) | (1L << (DECIMAL_LITERAL - 64)) | (1L << (HEX_LITERAL - 64)) | (1L << (FLOATING_LITERAL - 64)) | (1L << (BOOLEAN_LITERAL - 64)) | (1L << (STRING_LITERAL - 64)) | (1L << (NULL_LITERAL - 64)) | (1L << (IDENTIFIER - 64)) | (1L << (WHITE_SPACE - 64)) | (1L << (BLOCK_COMMENT - 64)) | (1L << (LINE_COMMENT - 64)) | (1L << (Preprocessor - 64)))) != 0)) {
				{
				{
				setState(534);
				_la = _input.LA(1);
				if ( _la <= 0 || (_la==GREATER) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(536);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,55,_ctx) ) {
				case 1:
					{
					setState(535);
					match(COLON);
					}
					break;
				}
				}
				}
				setState(542);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(543);
			match(GREATER);
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
		case 36:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 23);
		case 1:
			return precpred(_ctx, 18);
		case 2:
			return precpred(_ctx, 17);
		case 3:
			return precpred(_ctx, 16);
		case 4:
			return precpred(_ctx, 14);
		case 5:
			return precpred(_ctx, 13);
		case 6:
			return precpred(_ctx, 12);
		case 7:
			return precpred(_ctx, 11);
		case 8:
			return precpred(_ctx, 10);
		case 9:
			return precpred(_ctx, 9);
		case 10:
			return precpred(_ctx, 8);
		case 11:
			return precpred(_ctx, 7);
		case 12:
			return precpred(_ctx, 6);
		case 13:
			return precpred(_ctx, 20);
		case 14:
			return precpred(_ctx, 15);
		case 15:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3X\u0224\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\3\2\3\2\3\2\3\2\7\2e\n\2\f\2\16\2h\13"+
		"\2\3\2\3\2\3\3\3\3\3\3\3\3\5\3p\n\3\3\3\3\3\3\4\3\4\3\4\3\4\5\4x\n\4\3"+
		"\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\7\6\u0084\n\6\f\6\16\6\u0087\13"+
		"\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u0095\n\b\5\b\u0097"+
		"\n\b\3\b\3\b\3\t\3\t\3\t\3\t\5\t\u009f\n\t\3\t\3\t\3\n\3\n\3\n\3\13\3"+
		"\13\3\f\3\f\3\f\3\r\3\r\3\r\5\r\u00ae\n\r\3\r\3\r\3\16\3\16\5\16\u00b4"+
		"\n\16\3\16\3\16\7\16\u00b8\n\16\f\16\16\16\u00bb\13\16\3\16\3\16\3\17"+
		"\3\17\5\17\u00c1\n\17\3\17\5\17\u00c4\n\17\3\20\3\20\3\20\3\21\3\21\5"+
		"\21\u00cb\n\21\3\21\3\21\7\21\u00cf\n\21\f\21\16\21\u00d2\13\21\3\21\3"+
		"\21\3\22\3\22\3\22\5\22\u00d9\n\22\3\22\5\22\u00dc\n\22\3\22\5\22\u00df"+
		"\n\22\3\23\3\23\7\23\u00e3\n\23\f\23\16\23\u00e6\13\23\3\23\3\23\3\24"+
		"\3\24\5\24\u00ec\n\24\3\24\3\24\7\24\u00f0\n\24\f\24\16\24\u00f3\13\24"+
		"\3\24\3\24\3\24\3\24\5\24\u00f9\n\24\3\24\3\24\3\24\5\24\u00fe\n\24\7"+
		"\24\u0100\n\24\f\24\16\24\u0103\13\24\3\24\3\24\5\24\u0107\n\24\3\25\3"+
		"\25\5\25\u010b\n\25\3\25\3\25\7\25\u010f\n\25\f\25\16\25\u0112\13\25\3"+
		"\25\3\25\3\25\3\25\3\25\7\25\u0119\n\25\f\25\16\25\u011c\13\25\5\25\u011e"+
		"\n\25\3\25\5\25\u0121\n\25\3\26\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u0134\n\31\3\32\3\32\5\32"+
		"\u0138\n\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\35"+
		"\3\35\5\35\u0147\n\35\3\35\3\35\3\35\3\35\5\35\u014d\n\35\5\35\u014f\n"+
		"\35\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \5 \u015e"+
		"\n \3 \3 \3 \3 \3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\5\"\u016d\n\"\3\"\3\""+
		"\3\"\3\"\5\"\u0173\n\"\5\"\u0175\n\"\3#\3#\3#\3#\3$\3$\3$\5$\u017e\n$"+
		"\3%\3%\3%\7%\u0183\n%\f%\16%\u0186\13%\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3"+
		"&\3&\3&\3&\5&\u0196\n&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3"+
		"&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3"+
		"&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\7&\u01cc\n&\f&\16&\u01cf\13&"+
		"\3\'\3\'\3\'\3\'\3\'\3\'\5\'\u01d7\n\'\3(\3(\3(\5(\u01dc\n(\3(\3(\7(\u01e0"+
		"\n(\f(\16(\u01e3\13(\3(\3(\3(\3)\3)\3*\3*\5*\u01ec\n*\3*\3*\6*\u01f0\n"+
		"*\r*\16*\u01f1\3+\3+\3+\5+\u01f7\n+\3+\3+\3,\3,\5,\u01fd\n,\3,\3,\5,\u0201"+
		"\n,\3,\7,\u0204\n,\f,\16,\u0207\13,\3,\3,\3,\3,\3-\3-\3.\3.\3.\3.\3.\5"+
		".\u0214\n.\3/\3/\3\60\3\60\3\60\5\60\u021b\n\60\7\60\u021d\n\60\f\60\16"+
		"\60\u0220\13\60\3\60\3\60\3\60\2\3J\61\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^\2\16\3\2\3\4\3\2\3\6"+
		"\4\2\n\nMM\4\2-.\63\63\3\2/\61\3\2-.\4\2\64\65@C\3\2\13\f\4\2==DL\3\2"+
		"\16\27\3\2NO\3\2\65\65\2\u0258\2f\3\2\2\2\4k\3\2\2\2\6s\3\2\2\2\b{\3\2"+
		"\2\2\n\177\3\2\2\2\f\u008a\3\2\2\2\16\u008e\3\2\2\2\20\u009a\3\2\2\2\22"+
		"\u00a2\3\2\2\2\24\u00a5\3\2\2\2\26\u00a7\3\2\2\2\30\u00aa\3\2\2\2\32\u00b1"+
		"\3\2\2\2\34\u00be\3\2\2\2\36\u00c5\3\2\2\2 \u00c8\3\2\2\2\"\u00de\3\2"+
		"\2\2$\u00e0\3\2\2\2&\u0106\3\2\2\2(\u0120\3\2\2\2*\u0122\3\2\2\2,\u0126"+
		"\3\2\2\2.\u0128\3\2\2\2\60\u0133\3\2\2\2\62\u0135\3\2\2\2\64\u013b\3\2"+
		"\2\2\66\u013e\3\2\2\28\u0141\3\2\2\2:\u0150\3\2\2\2<\u0154\3\2\2\2>\u015a"+
		"\3\2\2\2@\u0163\3\2\2\2B\u0174\3\2\2\2D\u0176\3\2\2\2F\u017d\3\2\2\2H"+
		"\u017f\3\2\2\2J\u0195\3\2\2\2L\u01d6\3\2\2\2N\u01d8\3\2\2\2P\u01e7\3\2"+
		"\2\2R\u01eb\3\2\2\2T\u01f3\3\2\2\2V\u01fc\3\2\2\2X\u020c\3\2\2\2Z\u0213"+
		"\3\2\2\2\\\u0215\3\2\2\2^\u0217\3\2\2\2`e\5\4\3\2ae\5\6\4\2be\5\b\5\2"+
		"ce\5\60\31\2d`\3\2\2\2da\3\2\2\2db\3\2\2\2dc\3\2\2\2eh\3\2\2\2fd\3\2\2"+
		"\2fg\3\2\2\2gi\3\2\2\2hf\3\2\2\2ij\7\2\2\3j\3\3\2\2\2kl\7\7\2\2lo\5H%"+
		"\2mn\7\t\2\2np\7T\2\2om\3\2\2\2op\3\2\2\2pq\3\2\2\2qr\7,\2\2r\5\3\2\2"+
		"\2st\7\b\2\2tu\7T\2\2uw\5\32\16\2vx\5\22\n\2wv\3\2\2\2wx\3\2\2\2xy\3\2"+
		"\2\2yz\5$\23\2z\7\3\2\2\2{|\7\"\2\2|}\7T\2\2}~\5\n\6\2~\t\3\2\2\2\177"+
		"\u0085\7(\2\2\u0080\u0084\5\f\7\2\u0081\u0084\5\16\b\2\u0082\u0084\5\20"+
		"\t\2\u0083\u0080\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0082\3\2\2\2\u0084"+
		"\u0087\3\2\2\2\u0085\u0083\3\2\2\2\u0085\u0086\3\2\2\2\u0086\u0088\3\2"+
		"\2\2\u0087\u0085\3\2\2\2\u0088\u0089\7)\2\2\u0089\13\3\2\2\2\u008a\u008b"+
		"\7#\2\2\u008b\u008c\5\32\16\2\u008c\u008d\5$\23\2\u008d\r\3\2\2\2\u008e"+
		"\u008f\t\2\2\2\u008f\u0090\7T\2\2\u0090\u0096\5\22\n\2\u0091\u0092\7="+
		"\2\2\u0092\u0094\5J&\2\u0093\u0095\5\22\n\2\u0094\u0093\3\2\2\2\u0094"+
		"\u0095\3\2\2\2\u0095\u0097\3\2\2\2\u0096\u0091\3\2\2\2\u0096\u0097\3\2"+
		"\2\2\u0097\u0098\3\2\2\2\u0098\u0099\7,\2\2\u0099\17\3\2\2\2\u009a\u009b"+
		"\7\b\2\2\u009b\u009c\7T\2\2\u009c\u009e\5\32\16\2\u009d\u009f\5\22\n\2"+
		"\u009e\u009d\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u00a1"+
		"\5$\23\2\u00a1\21\3\2\2\2\u00a2\u00a3\7\t\2\2\u00a3\u00a4\5L\'\2\u00a4"+
		"\23\3\2\2\2\u00a5\u00a6\7T\2\2\u00a6\25\3\2\2\2\u00a7\u00a8\7T\2\2\u00a8"+
		"\u00a9\5 \21\2\u00a9\27\3\2\2\2\u00aa\u00ab\7\b\2\2\u00ab\u00ad\5\32\16"+
		"\2\u00ac\u00ae\5\22\n\2\u00ad\u00ac\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae"+
		"\u00af\3\2\2\2\u00af\u00b0\5$\23\2\u00b0\31\3\2\2\2\u00b1\u00b3\7$\2\2"+
		"\u00b2\u00b4\5\34\17\2\u00b3\u00b2\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00b9"+
		"\3\2\2\2\u00b5\u00b6\7*\2\2\u00b6\u00b8\5\34\17\2\u00b7\u00b5\3\2\2\2"+
		"\u00b8\u00bb\3\2\2\2\u00b9\u00b7\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00bc"+
		"\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bc\u00bd\7%\2\2\u00bd\33\3\2\2\2\u00be"+
		"\u00c0\7T\2\2\u00bf\u00c1\5\22\n\2\u00c0\u00bf\3\2\2\2\u00c0\u00c1\3\2"+
		"\2\2\u00c1\u00c3\3\2\2\2\u00c2\u00c4\5\36\20\2\u00c3\u00c2\3\2\2\2\u00c3"+
		"\u00c4\3\2\2\2\u00c4\35\3\2\2\2\u00c5\u00c6\7=\2\2\u00c6\u00c7\5J&\2\u00c7"+
		"\37\3\2\2\2\u00c8\u00ca\7$\2\2\u00c9\u00cb\5\"\22\2\u00ca\u00c9\3\2\2"+
		"\2\u00ca\u00cb\3\2\2\2\u00cb\u00d0\3\2\2\2\u00cc\u00cd\7*\2\2\u00cd\u00cf"+
		"\5\"\22\2\u00ce\u00cc\3\2\2\2\u00cf\u00d2\3\2\2\2\u00d0\u00ce\3\2\2\2"+
		"\u00d0\u00d1\3\2\2\2\u00d1\u00d3\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d3\u00d4"+
		"\7%\2\2\u00d4!\3\2\2\2\u00d5\u00d9\7T\2\2\u00d6\u00d9\5J&\2\u00d7\u00d9"+
		"\5Z.\2\u00d8\u00d5\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d8\u00d7\3\2\2\2\u00d9"+
		"\u00db\3\2\2\2\u00da\u00dc\5\22\n\2\u00db\u00da\3\2\2\2\u00db\u00dc\3"+
		"\2\2\2\u00dc\u00df\3\2\2\2\u00dd\u00df\5\30\r\2\u00de\u00d8\3\2\2\2\u00de"+
		"\u00dd\3\2\2\2\u00df#\3\2\2\2\u00e0\u00e4\7(\2\2\u00e1\u00e3\5\60\31\2"+
		"\u00e2\u00e1\3\2\2\2\u00e3\u00e6\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e4\u00e5"+
		"\3\2\2\2\u00e5\u00e7\3\2\2\2\u00e6\u00e4\3\2\2\2\u00e7\u00e8\7)\2\2\u00e8"+
		"%\3\2\2\2\u00e9\u00eb\7&\2\2\u00ea\u00ec\5&\24\2\u00eb\u00ea\3\2\2\2\u00eb"+
		"\u00ec\3\2\2\2\u00ec\u00f1\3\2\2\2\u00ed\u00ee\7*\2\2\u00ee\u00f0\5&\24"+
		"\2\u00ef\u00ed\3\2\2\2\u00f0\u00f3\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f1\u00f2"+
		"\3\2\2\2\u00f2\u00f4\3\2\2\2\u00f3\u00f1\3\2\2\2\u00f4\u0107\7\'\2\2\u00f5"+
		"\u00f8\7&\2\2\u00f6\u00f9\5Z.\2\u00f7\u00f9\5J&\2\u00f8\u00f6\3\2\2\2"+
		"\u00f8\u00f7\3\2\2\2\u00f9\u0101\3\2\2\2\u00fa\u00fd\7*\2\2\u00fb\u00fe"+
		"\5Z.\2\u00fc\u00fe\5J&\2\u00fd\u00fb\3\2\2\2\u00fd\u00fc\3\2\2\2\u00fe"+
		"\u0100\3\2\2\2\u00ff\u00fa\3\2\2\2\u0100\u0103\3\2\2\2\u0101\u00ff\3\2"+
		"\2\2\u0101\u0102\3\2\2\2\u0102\u0104\3\2\2\2\u0103\u0101\3\2\2\2\u0104"+
		"\u0105\7\'\2\2\u0105\u0107\3\2\2\2\u0106\u00e9\3\2\2\2\u0106\u00f5\3\2"+
		"\2\2\u0107\'\3\2\2\2\u0108\u010a\7(\2\2\u0109\u010b\5(\25\2\u010a\u0109"+
		"\3\2\2\2\u010a\u010b\3\2\2\2\u010b\u0110\3\2\2\2\u010c\u010d\7*\2\2\u010d"+
		"\u010f\5(\25\2\u010e\u010c\3\2\2\2\u010f\u0112\3\2\2\2\u0110\u010e\3\2"+
		"\2\2\u0110\u0111\3\2\2\2\u0111\u0113\3\2\2\2\u0112\u0110\3\2\2\2\u0113"+
		"\u0121\7)\2\2\u0114\u011d\7(\2\2\u0115\u011a\5*\26\2\u0116\u0117\7*\2"+
		"\2\u0117\u0119\5*\26\2\u0118\u0116\3\2\2\2\u0119\u011c\3\2\2\2\u011a\u0118"+
		"\3\2\2\2\u011a\u011b\3\2\2\2\u011b\u011e\3\2\2\2\u011c\u011a\3\2\2\2\u011d"+
		"\u0115\3\2\2\2\u011d\u011e\3\2\2\2\u011e\u011f\3\2\2\2\u011f\u0121\7)"+
		"\2\2\u0120\u0108\3\2\2\2\u0120\u0114\3\2\2\2\u0121)\3\2\2\2\u0122\u0123"+
		"\5,\27\2\u0123\u0124\7\67\2\2\u0124\u0125\5.\30\2\u0125+\3\2\2\2\u0126"+
		"\u0127\5J&\2\u0127-\3\2\2\2\u0128\u0129\5J&\2\u0129/\3\2\2\2\u012a\u0134"+
		"\5\62\32\2\u012b\u0134\5\64\33\2\u012c\u0134\5\66\34\2\u012d\u0134\58"+
		"\35\2\u012e\u0134\5:\36\2\u012f\u0134\5<\37\2\u0130\u0134\5> \2\u0131"+
		"\u0134\5\6\4\2\u0132\u0134\5@!\2\u0133\u012a\3\2\2\2\u0133\u012b\3\2\2"+
		"\2\u0133\u012c\3\2\2\2\u0133\u012d\3\2\2\2\u0133\u012e\3\2\2\2\u0133\u012f"+
		"\3\2\2\2\u0133\u0130\3\2\2\2\u0133\u0131\3\2\2\2\u0133\u0132\3\2\2\2\u0134"+
		"\61\3\2\2\2\u0135\u0137\7\37\2\2\u0136\u0138\5J&\2\u0137\u0136\3\2\2\2"+
		"\u0137\u0138\3\2\2\2\u0138\u0139\3\2\2\2\u0139\u013a\7,\2\2\u013a\63\3"+
		"\2\2\2\u013b\u013c\7\35\2\2\u013c\u013d\7,\2\2\u013d\65\3\2\2\2\u013e"+
		"\u013f\7\36\2\2\u013f\u0140\7,\2\2\u0140\67\3\2\2\2\u0141\u0142\7\30\2"+
		"\2\u0142\u0146\5J&\2\u0143\u0147\5\60\31\2\u0144\u0147\5$\23\2\u0145\u0147"+
		"\3\2\2\2\u0146\u0143\3\2\2\2\u0146\u0144\3\2\2\2\u0146\u0145\3\2\2\2\u0147"+
		"\u014e\3\2\2\2\u0148\u014c\7\31\2\2\u0149\u014d\5\60\31\2\u014a\u014d"+
		"\5$\23\2\u014b\u014d\3\2\2\2\u014c\u0149\3\2\2\2\u014c\u014a\3\2\2\2\u014c"+
		"\u014b\3\2\2\2\u014d\u014f\3\2\2\2\u014e\u0148\3\2\2\2\u014e\u014f\3\2"+
		"\2\2\u014f9\3\2\2\2\u0150\u0151\7\32\2\2\u0151\u0152\5B\"\2\u0152\u0153"+
		"\5$\23\2\u0153;\3\2\2\2\u0154\u0155\7\34\2\2\u0155\u0156\7$\2\2\u0156"+
		"\u0157\5J&\2\u0157\u0158\7%\2\2\u0158\u0159\5$\23\2\u0159=\3\2\2\2\u015a"+
		"\u015b\t\3\2\2\u015b\u015d\7T\2\2\u015c\u015e\5\22\n\2\u015d\u015c\3\2"+
		"\2\2\u015d\u015e\3\2\2\2\u015e\u015f\3\2\2\2\u015f\u0160\7=\2\2\u0160"+
		"\u0161\5J&\2\u0161\u0162\7,\2\2\u0162?\3\2\2\2\u0163\u0164\5J&\2\u0164"+
		"\u0165\7,\2\2\u0165A\3\2\2\2\u0166\u0167\7T\2\2\u0167\u0168\7\13\2\2\u0168"+
		"\u0175\5D#\2\u0169\u016c\7T\2\2\u016a\u016b\7*\2\2\u016b\u016d\7T\2\2"+
		"\u016c\u016a\3\2\2\2\u016c\u016d\3\2\2\2\u016d\u016e\3\2\2\2\u016e\u0172"+
		"\7\13\2\2\u016f\u0173\5J&\2\u0170\u0173\5\24\13\2\u0171\u0173\5\26\f\2"+
		"\u0172\u016f\3\2\2\2\u0172\u0170\3\2\2\2\u0172\u0171\3\2\2\2\u0173\u0175"+
		"\3\2\2\2\u0174\u0166\3\2\2\2\u0174\u0169\3\2\2\2\u0175C\3\2\2\2\u0176"+
		"\u0177\5F$\2\u0177\u0178\t\4\2\2\u0178\u0179\5F$\2\u0179E\3\2\2\2\u017a"+
		"\u017e\5J&\2\u017b\u017e\5\24\13\2\u017c\u017e\5\26\f\2\u017d\u017a\3"+
		"\2\2\2\u017d\u017b\3\2\2\2\u017d\u017c\3\2\2\2\u017eG\3\2\2\2\u017f\u0184"+
		"\7T\2\2\u0180\u0181\7+\2\2\u0181\u0183\7T\2\2\u0182\u0180\3\2\2\2\u0183"+
		"\u0186\3\2\2\2\u0184\u0182\3\2\2\2\u0184\u0185\3\2\2\2\u0185I\3\2\2\2"+
		"\u0186\u0184\3\2\2\2\u0187\u0188\b&\1\2\u0188\u0196\5Z.\2\u0189\u018a"+
		"\7$\2\2\u018a\u018b\5J&\2\u018b\u018c\7%\2\2\u018c\u0196\3\2\2\2\u018d"+
		"\u0196\5\26\f\2\u018e\u0196\5\24\13\2\u018f\u0190\t\5\2\2\u0190\u0196"+
		"\5J&\25\u0191\u0196\5^\60\2\u0192\u0196\5&\24\2\u0193\u0196\5(\25\2\u0194"+
		"\u0196\5\30\r\2\u0195\u0187\3\2\2\2\u0195\u0189\3\2\2\2\u0195\u018d\3"+
		"\2\2\2\u0195\u018e\3\2\2\2\u0195\u018f\3\2\2\2\u0195\u0191\3\2\2\2\u0195"+
		"\u0192\3\2\2\2\u0195\u0193\3\2\2\2\u0195\u0194\3\2\2\2\u0196\u01cd\3\2"+
		"\2\2\u0197\u0198\f\31\2\2\u0198\u0199\7+\2\2\u0199\u01cc\5J&\32\u019a"+
		"\u019b\f\24\2\2\u019b\u019c\t\6\2\2\u019c\u01cc\5J&\25\u019d\u019e\f\23"+
		"\2\2\u019e\u019f\t\7\2\2\u019f\u01cc\5J&\24\u01a0\u01a1\f\22\2\2\u01a1"+
		"\u01a2\t\b\2\2\u01a2\u01cc\5J&\23\u01a3\u01a4\f\20\2\2\u01a4\u01a5\t\t"+
		"\2\2\u01a5\u01cc\5J&\21\u01a6\u01a7\f\17\2\2\u01a7\u01a8\7;\2\2\u01a8"+
		"\u01cc\5J&\20\u01a9\u01aa\f\16\2\2\u01aa\u01ab\7\66\2\2\u01ab\u01cc\5"+
		"J&\17\u01ac\u01ad\f\r\2\2\u01ad\u01ae\7<\2\2\u01ae\u01cc\5J&\16\u01af"+
		"\u01b0\f\f\2\2\u01b0\u01b1\7>\2\2\u01b1\u01cc\5J&\r\u01b2\u01b3\f\13\2"+
		"\2\u01b3\u01b4\7?\2\2\u01b4\u01cc\5J&\f\u01b5\u01b6\f\n\2\2\u01b6\u01b7"+
		"\7\62\2\2\u01b7\u01cc\5J&\13\u01b8\u01b9\f\t\2\2\u01b9\u01ba\78\2\2\u01ba"+
		"\u01bb\5J&\2\u01bb\u01bc\7\67\2\2\u01bc\u01bd\5J&\t\u01bd\u01cc\3\2\2"+
		"\2\u01be\u01bf\f\b\2\2\u01bf\u01c0\t\n\2\2\u01c0\u01cc\5J&\b\u01c1\u01c2"+
		"\f\26\2\2\u01c2\u01c3\7&\2\2\u01c3\u01c4\5J&\2\u01c4\u01c5\7\'\2\2\u01c5"+
		"\u01cc\3\2\2\2\u01c6\u01c7\f\21\2\2\u01c7\u01c8\7\r\2\2\u01c8\u01cc\5"+
		"L\'\2\u01c9\u01ca\f\3\2\2\u01ca\u01cc\5\22\n\2\u01cb\u0197\3\2\2\2\u01cb"+
		"\u019a\3\2\2\2\u01cb\u019d\3\2\2\2\u01cb\u01a0\3\2\2\2\u01cb\u01a3\3\2"+
		"\2\2\u01cb\u01a6\3\2\2\2\u01cb\u01a9\3\2\2\2\u01cb\u01ac\3\2\2\2\u01cb"+
		"\u01af\3\2\2\2\u01cb\u01b2\3\2\2\2\u01cb\u01b5\3\2\2\2\u01cb\u01b8\3\2"+
		"\2\2\u01cb\u01be\3\2\2\2\u01cb\u01c1\3\2\2\2\u01cb\u01c6\3\2\2\2\u01cb"+
		"\u01c9\3\2\2\2\u01cc\u01cf\3\2\2\2\u01cd\u01cb\3\2\2\2\u01cd\u01ce\3\2"+
		"\2\2\u01ceK\3\2\2\2\u01cf\u01cd\3\2\2\2\u01d0\u01d7\5T+\2\u01d1\u01d7"+
		"\5V,\2\u01d2\u01d7\5R*\2\u01d3\u01d7\5P)\2\u01d4\u01d7\5X-\2\u01d5\u01d7"+
		"\5N(\2\u01d6\u01d0\3\2\2\2\u01d6\u01d1\3\2\2\2\u01d6\u01d2\3\2\2\2\u01d6"+
		"\u01d3\3\2\2\2\u01d6\u01d4\3\2\2\2\u01d6\u01d5\3\2\2\2\u01d7M\3\2\2\2"+
		"\u01d8\u01d9\7\b\2\2\u01d9\u01db\7$\2\2\u01da\u01dc\5L\'\2\u01db\u01da"+
		"\3\2\2\2\u01db\u01dc\3\2\2\2\u01dc\u01e1\3\2\2\2\u01dd\u01de\7*\2\2\u01de"+
		"\u01e0\5L\'\2\u01df\u01dd\3\2\2\2\u01e0\u01e3\3\2\2\2\u01e1\u01df\3\2"+
		"\2\2\u01e1\u01e2\3\2\2\2\u01e2\u01e4\3\2\2\2\u01e3\u01e1\3\2\2\2\u01e4"+
		"\u01e5\7%\2\2\u01e5\u01e6\5L\'\2\u01e6O\3\2\2\2\u01e7\u01e8\t\13\2\2\u01e8"+
		"Q\3\2\2\2\u01e9\u01ec\5P)\2\u01ea\u01ec\5X-\2\u01eb\u01e9\3\2\2\2\u01eb"+
		"\u01ea\3\2\2\2\u01ec\u01ef\3\2\2\2\u01ed\u01ee\7&\2\2\u01ee\u01f0\7\'"+
		"\2\2\u01ef\u01ed\3\2\2\2\u01f0\u01f1\3\2\2\2\u01f1\u01ef\3\2\2\2\u01f1"+
		"\u01f2\3\2\2\2\u01f2S\3\2\2\2\u01f3\u01f6\7&\2\2\u01f4\u01f7\5P)\2\u01f5"+
		"\u01f7\5X-\2\u01f6\u01f4\3\2\2\2\u01f6\u01f5\3\2\2\2\u01f7\u01f8\3\2\2"+
		"\2\u01f8\u01f9\7\'\2\2\u01f9U\3\2\2\2\u01fa\u01fd\5P)\2\u01fb\u01fd\5"+
		"X-\2\u01fc\u01fa\3\2\2\2\u01fc\u01fb\3\2\2\2\u01fd\u0205\3\2\2\2\u01fe"+
		"\u0200\7&\2\2\u01ff\u0201\5L\'\2\u0200\u01ff\3\2\2\2\u0200\u0201\3\2\2"+
		"\2\u0201\u0202\3\2\2\2\u0202\u0204\7\'\2\2\u0203\u01fe\3\2\2\2\u0204\u0207"+
		"\3\2\2\2\u0205\u0203\3\2\2\2\u0205\u0206\3\2\2\2\u0206\u0208\3\2\2\2\u0207"+
		"\u0205\3\2\2\2\u0208\u0209\7&\2\2\u0209\u020a\5L\'\2\u020a\u020b\7\'\2"+
		"\2\u020bW\3\2\2\2\u020c\u020d\5H%\2\u020dY\3\2\2\2\u020e\u0214\5\\/\2"+
		"\u020f\u0214\7P\2\2\u0210\u0214\7R\2\2\u0211\u0214\7Q\2\2\u0212\u0214"+
		"\7S\2\2\u0213\u020e\3\2\2\2\u0213\u020f\3\2\2\2\u0213\u0210\3\2\2\2\u0213"+
		"\u0211\3\2\2\2\u0213\u0212\3\2\2\2\u0214[\3\2\2\2\u0215\u0216\t\f\2\2"+
		"\u0216]\3\2\2\2\u0217\u021e\7\64\2\2\u0218\u021a\n\r\2\2\u0219\u021b\7"+
		"\67\2\2\u021a\u0219\3\2\2\2\u021a\u021b\3\2\2\2\u021b\u021d\3\2\2\2\u021c"+
		"\u0218\3\2\2\2\u021d\u0220\3\2\2\2\u021e\u021c\3\2\2\2\u021e\u021f\3\2"+
		"\2\2\u021f\u0221\3\2\2\2\u0220\u021e\3\2\2\2\u0221\u0222\7\65\2\2\u0222"+
		"_\3\2\2\2;dfow\u0083\u0085\u0094\u0096\u009e\u00ad\u00b3\u00b9\u00c0\u00c3"+
		"\u00ca\u00d0\u00d8\u00db\u00de\u00e4\u00eb\u00f1\u00f8\u00fd\u0101\u0106"+
		"\u010a\u0110\u011a\u011d\u0120\u0133\u0137\u0146\u014c\u014e\u015d\u016c"+
		"\u0172\u0174\u017d\u0184\u0195\u01cb\u01cd\u01d6\u01db\u01e1\u01eb\u01f1"+
		"\u01f6\u01fc\u0200\u0205\u0213\u021a\u021e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}