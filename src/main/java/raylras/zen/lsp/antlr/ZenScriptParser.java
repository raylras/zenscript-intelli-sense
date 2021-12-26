// Generated from D:/Projects/Forge/1.12.2/ZenServer/src/main/resources\ZenScriptParser.g4 by ANTLR 4.9.2
package raylras.zen.lsp.antlr;
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
		RULE_field = 6, RULE_method = 7, RULE_localVariableDeclaration = 8, RULE_globalVariableDeclaration = 9, 
		RULE_asType = 10, RULE_memberCall = 11, RULE_methodCall = 12, RULE_anonymousFunction = 13, 
		RULE_formalParameters = 14, RULE_formalParameter = 15, RULE_arguments = 16, 
		RULE_argument = 17, RULE_block = 18, RULE_array = 19, RULE_map = 20, RULE_mapEntry = 21, 
		RULE_mapKey = 22, RULE_mapValue = 23, RULE_statements = 24, RULE_ifStatement = 25, 
		RULE_forStatement = 26, RULE_whileStatement = 27, RULE_doWhileStatement = 28, 
		RULE_forControl = 29, RULE_range = 30, RULE_bounds = 31, RULE_packageName = 32, 
		RULE_expression = 33, RULE_type = 34, RULE_typeFunction = 35, RULE_typePrimitive = 36, 
		RULE_typeArray = 37, RULE_typeList = 38, RULE_typeMap = 39, RULE_typeClass = 40, 
		RULE_literal = 41, RULE_integerLiteral = 42, RULE_bracketHandler = 43;
	private static String[] makeRuleNames() {
		return new String[] {
			"script", "importStatement", "functionDeclaration", "zenClassDeclaration", 
			"classBody", "constructor", "field", "method", "localVariableDeclaration", 
			"globalVariableDeclaration", "asType", "memberCall", "methodCall", "anonymousFunction", 
			"formalParameters", "formalParameter", "arguments", "argument", "block", 
			"array", "map", "mapEntry", "mapKey", "mapValue", "statements", "ifStatement", 
			"forStatement", "whileStatement", "doWhileStatement", "forControl", "range", 
			"bounds", "packageName", "expression", "type", "typeFunction", "typePrimitive", 
			"typeArray", "typeList", "typeMap", "typeClass", "literal", "integerLiteral", 
			"bracketHandler"
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
		public List<StatementsContext> statements() {
			return getRuleContexts(StatementsContext.class);
		}
		public StatementsContext statements(int i) {
			return getRuleContext(StatementsContext.class,i);
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
			setState(94);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC) | (1L << IMPORT) | (1L << FUNCTION) | (1L << IF) | (1L << FOR) | (1L << DO) | (1L << WHILE) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << ZEN_CLASS) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (DECIMAL_LITERAL - 76)) | (1L << (HEX_LITERAL - 76)) | (1L << (FLOATING_LITERAL - 76)) | (1L << (BOOLEAN_LITERAL - 76)) | (1L << (STRING_LITERAL - 76)) | (1L << (NULL_LITERAL - 76)) | (1L << (IDENTIFIER - 76)))) != 0)) {
				{
				setState(92);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(88);
					importStatement();
					}
					break;
				case 2:
					{
					setState(89);
					functionDeclaration();
					}
					break;
				case 3:
					{
					setState(90);
					zenClassDeclaration();
					}
					break;
				case 4:
					{
					setState(91);
					statements();
					}
					break;
				}
				}
				setState(96);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(97);
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
		public PackageNameContext packageName() {
			return getRuleContext(PackageNameContext.class,0);
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
			setState(99);
			match(IMPORT);
			setState(100);
			packageName();
			setState(103);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(101);
				match(AS);
				setState(102);
				match(IDENTIFIER);
				}
			}

			setState(105);
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
		public FormalParametersContext formalParameters() {
			return getRuleContext(FormalParametersContext.class,0);
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
			setState(107);
			match(FUNCTION);
			setState(108);
			match(IDENTIFIER);
			setState(109);
			formalParameters();
			setState(111);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(110);
				asType();
				}
			}

			setState(113);
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
			setState(115);
			match(ZEN_CLASS);
			setState(116);
			match(IDENTIFIER);
			setState(117);
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
			setState(119);
			match(BRACE_OPEN);
			setState(125);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << FUNCTION) | (1L << ZEN_CONSTRUCTOR))) != 0)) {
				{
				setState(123);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case ZEN_CONSTRUCTOR:
					{
					setState(120);
					constructor();
					}
					break;
				case VAR:
				case VAL:
					{
					setState(121);
					field();
					}
					break;
				case FUNCTION:
					{
					setState(122);
					method();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(127);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(128);
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
		public FormalParametersContext formalParameters() {
			return getRuleContext(FormalParametersContext.class,0);
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
			setState(130);
			match(ZEN_CONSTRUCTOR);
			setState(131);
			formalParameters();
			setState(132);
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
			setState(134);
			_la = _input.LA(1);
			if ( !(_la==VAR || _la==VAL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(135);
			match(IDENTIFIER);
			setState(136);
			asType();
			setState(142);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(137);
				match(ASSIGN);
				setState(138);
				expression(0);
				setState(140);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(139);
					asType();
					}
				}

				}
			}

			setState(144);
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
		public FormalParametersContext formalParameters() {
			return getRuleContext(FormalParametersContext.class,0);
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
			setState(146);
			match(FUNCTION);
			setState(147);
			match(IDENTIFIER);
			setState(148);
			formalParameters();
			setState(150);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(149);
				asType();
				}
			}

			setState(152);
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

	public static class LocalVariableDeclarationContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }
		public TerminalNode ASSIGN() { return getToken(ZenScriptParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }
		public TerminalNode VAR() { return getToken(ZenScriptParser.VAR, 0); }
		public TerminalNode VAL() { return getToken(ZenScriptParser.VAL, 0); }
		public AsTypeContext asType() {
			return getRuleContext(AsTypeContext.class,0);
		}
		public LocalVariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_localVariableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterLocalVariableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitLocalVariableDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitLocalVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LocalVariableDeclarationContext localVariableDeclaration() throws RecognitionException {
		LocalVariableDeclarationContext _localctx = new LocalVariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_localVariableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			_la = _input.LA(1);
			if ( !(_la==VAR || _la==VAL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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

			setState(159);
			match(ASSIGN);
			setState(160);
			expression(0);
			setState(161);
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

	public static class GlobalVariableDeclarationContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }
		public AsTypeContext asType() {
			return getRuleContext(AsTypeContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(ZenScriptParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }
		public TerminalNode GLOBAL() { return getToken(ZenScriptParser.GLOBAL, 0); }
		public TerminalNode STATIC() { return getToken(ZenScriptParser.STATIC, 0); }
		public GlobalVariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globalVariableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterGlobalVariableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitGlobalVariableDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitGlobalVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalVariableDeclarationContext globalVariableDeclaration() throws RecognitionException {
		GlobalVariableDeclarationContext _localctx = new GlobalVariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_globalVariableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			_la = _input.LA(1);
			if ( !(_la==GLOBAL || _la==STATIC) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(164);
			match(IDENTIFIER);
			setState(165);
			asType();
			setState(166);
			match(ASSIGN);
			setState(167);
			expression(0);
			setState(168);
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
		enterRule(_localctx, 20, RULE_asType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			match(AS);
			setState(171);
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
		enterRule(_localctx, 22, RULE_memberCall);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
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
		enterRule(_localctx, 24, RULE_methodCall);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			match(IDENTIFIER);
			setState(176);
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
		public FormalParametersContext formalParameters() {
			return getRuleContext(FormalParametersContext.class,0);
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
		enterRule(_localctx, 26, RULE_anonymousFunction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			match(FUNCTION);
			setState(179);
			formalParameters();
			setState(181);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(180);
				asType();
				}
			}

			setState(183);
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

	public static class FormalParametersContext extends ParserRuleContext {
		public TerminalNode PAREN_OPEN() { return getToken(ZenScriptParser.PAREN_OPEN, 0); }
		public TerminalNode PAREN_CLOSE() { return getToken(ZenScriptParser.PAREN_CLOSE, 0); }
		public List<FormalParameterContext> formalParameter() {
			return getRuleContexts(FormalParameterContext.class);
		}
		public FormalParameterContext formalParameter(int i) {
			return getRuleContext(FormalParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ZenScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ZenScriptParser.COMMA, i);
		}
		public FormalParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalParameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterFormalParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitFormalParameters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitFormalParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalParametersContext formalParameters() throws RecognitionException {
		FormalParametersContext _localctx = new FormalParametersContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_formalParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185);
			match(PAREN_OPEN);
			setState(187);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(186);
				formalParameter();
				}
			}

			setState(193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(189);
				match(COMMA);
				setState(190);
				formalParameter();
				}
				}
				setState(195);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(196);
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

	public static class FormalParameterContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }
		public AsTypeContext asType() {
			return getRuleContext(AsTypeContext.class,0);
		}
		public FormalParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterFormalParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitFormalParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitFormalParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalParameterContext formalParameter() throws RecognitionException {
		FormalParameterContext _localctx = new FormalParameterContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_formalParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			match(IDENTIFIER);
			setState(200);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(199);
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
		enterRule(_localctx, 32, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
			match(PAREN_OPEN);
			{
			setState(204);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (DECIMAL_LITERAL - 76)) | (1L << (HEX_LITERAL - 76)) | (1L << (FLOATING_LITERAL - 76)) | (1L << (BOOLEAN_LITERAL - 76)) | (1L << (STRING_LITERAL - 76)) | (1L << (NULL_LITERAL - 76)) | (1L << (IDENTIFIER - 76)))) != 0)) {
				{
				setState(203);
				argument();
				}
			}

			setState(210);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(206);
				match(COMMA);
				setState(207);
				argument();
				}
				}
				setState(212);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(213);
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
		enterRule(_localctx, 34, RULE_argument);
		int _la;
		try {
			setState(224);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(218);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
				case 1:
					{
					setState(215);
					match(IDENTIFIER);
					}
					break;
				case 2:
					{
					setState(216);
					expression(0);
					}
					break;
				case 3:
					{
					setState(217);
					literal();
					}
					break;
				}
				setState(221);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(220);
					asType();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(223);
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
		public List<StatementsContext> statements() {
			return getRuleContexts(StatementsContext.class);
		}
		public StatementsContext statements(int i) {
			return getRuleContext(StatementsContext.class,i);
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
		enterRule(_localctx, 36, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(226);
			match(BRACE_OPEN);
			setState(230);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC) | (1L << FUNCTION) | (1L << IF) | (1L << FOR) | (1L << DO) | (1L << WHILE) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (DECIMAL_LITERAL - 76)) | (1L << (HEX_LITERAL - 76)) | (1L << (FLOATING_LITERAL - 76)) | (1L << (BOOLEAN_LITERAL - 76)) | (1L << (STRING_LITERAL - 76)) | (1L << (NULL_LITERAL - 76)) | (1L << (IDENTIFIER - 76)))) != 0)) {
				{
				{
				setState(227);
				statements();
				}
				}
				setState(232);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(233);
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
		enterRule(_localctx, 38, RULE_array);
		int _la;
		try {
			setState(264);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(235);
				match(BRACK_OPEN);
				setState(237);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==BRACK_OPEN) {
					{
					setState(236);
					array();
					}
				}

				setState(243);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(239);
					match(COMMA);
					setState(240);
					array();
					}
					}
					setState(245);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(246);
				match(BRACK_CLOSE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(247);
				match(BRACK_OPEN);
				setState(250);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
				case 1:
					{
					setState(248);
					literal();
					}
					break;
				case 2:
					{
					setState(249);
					expression(0);
					}
					break;
				}
				setState(259);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(252);
					match(COMMA);
					setState(255);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
					case 1:
						{
						setState(253);
						literal();
						}
						break;
					case 2:
						{
						setState(254);
						expression(0);
						}
						break;
					}
					}
					}
					setState(261);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(262);
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
		enterRule(_localctx, 40, RULE_map);
		int _la;
		try {
			setState(290);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(266);
				match(BRACE_OPEN);
				setState(268);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==BRACE_OPEN) {
					{
					setState(267);
					map();
					}
				}

				setState(274);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(270);
					match(COMMA);
					setState(271);
					map();
					}
					}
					setState(276);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(277);
				match(BRACE_CLOSE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(278);
				match(BRACE_OPEN);
				setState(287);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (DECIMAL_LITERAL - 76)) | (1L << (HEX_LITERAL - 76)) | (1L << (FLOATING_LITERAL - 76)) | (1L << (BOOLEAN_LITERAL - 76)) | (1L << (STRING_LITERAL - 76)) | (1L << (NULL_LITERAL - 76)) | (1L << (IDENTIFIER - 76)))) != 0)) {
					{
					setState(279);
					mapEntry();
					setState(284);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(280);
						match(COMMA);
						setState(281);
						mapEntry();
						}
						}
						setState(286);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(289);
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
		enterRule(_localctx, 42, RULE_mapEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(292);
			mapKey();
			setState(293);
			match(COLON);
			setState(294);
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
		enterRule(_localctx, 44, RULE_mapKey);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(296);
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
		enterRule(_localctx, 46, RULE_mapValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(298);
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

	public static class StatementsContext extends ParserRuleContext {
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public DoWhileStatementContext doWhileStatement() {
			return getRuleContext(DoWhileStatementContext.class,0);
		}
		public LocalVariableDeclarationContext localVariableDeclaration() {
			return getRuleContext(LocalVariableDeclarationContext.class,0);
		}
		public GlobalVariableDeclarationContext globalVariableDeclaration() {
			return getRuleContext(GlobalVariableDeclarationContext.class,0);
		}
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }
		public StatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterStatements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitStatements(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitStatements(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementsContext statements() throws RecognitionException {
		StatementsContext _localctx = new StatementsContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_statements);
		try {
			setState(310);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(300);
				ifStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(301);
				forStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(302);
				whileStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(303);
				doWhileStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(304);
				localVariableDeclaration();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(305);
				globalVariableDeclaration();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(306);
				functionDeclaration();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(307);
				expression(0);
				setState(308);
				match(SEMICOLON);
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

	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(ZenScriptParser.IF, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<StatementsContext> statements() {
			return getRuleContexts(StatementsContext.class);
		}
		public StatementsContext statements(int i) {
			return getRuleContext(StatementsContext.class,i);
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
		enterRule(_localctx, 50, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(312);
			match(IF);
			setState(313);
			expression(0);
			setState(317);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				{
				setState(314);
				statements();
				}
				break;
			case 2:
				{
				setState(315);
				block();
				}
				break;
			case 3:
				{
				}
				break;
			}
			setState(325);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				{
				setState(319);
				match(ELSE);
				setState(323);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
				case 1:
					{
					setState(320);
					statements();
					}
					break;
				case 2:
					{
					setState(321);
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
		enterRule(_localctx, 52, RULE_forStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(327);
			match(FOR);
			setState(328);
			forControl();
			setState(329);
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
		enterRule(_localctx, 54, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(331);
			match(WHILE);
			setState(332);
			match(PAREN_OPEN);
			setState(333);
			expression(0);
			setState(334);
			match(PAREN_CLOSE);
			setState(335);
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

	public static class DoWhileStatementContext extends ParserRuleContext {
		public TerminalNode DO() { return getToken(ZenScriptParser.DO, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode WHILE() { return getToken(ZenScriptParser.WHILE, 0); }
		public TerminalNode PAREN_OPEN() { return getToken(ZenScriptParser.PAREN_OPEN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode PAREN_CLOSE() { return getToken(ZenScriptParser.PAREN_CLOSE, 0); }
		public DoWhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doWhileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterDoWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitDoWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitDoWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DoWhileStatementContext doWhileStatement() throws RecognitionException {
		DoWhileStatementContext _localctx = new DoWhileStatementContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_doWhileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(337);
			match(DO);
			setState(338);
			block();
			setState(339);
			match(WHILE);
			setState(340);
			match(PAREN_OPEN);
			setState(341);
			expression(0);
			setState(342);
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
		enterRule(_localctx, 58, RULE_forControl);
		int _la;
		try {
			setState(358);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(344);
				match(IDENTIFIER);
				setState(345);
				match(IN);
				setState(346);
				range();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(347);
				match(IDENTIFIER);
				setState(350);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(348);
					match(COMMA);
					setState(349);
					match(IDENTIFIER);
					}
				}

				setState(352);
				match(IN);
				setState(356);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
				case 1:
					{
					setState(353);
					expression(0);
					}
					break;
				case 2:
					{
					setState(354);
					memberCall();
					}
					break;
				case 3:
					{
					setState(355);
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
		enterRule(_localctx, 60, RULE_range);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(360);
			bounds();
			setState(361);
			_la = _input.LA(1);
			if ( !(_la==TO || _la==DOT_DOT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(362);
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
		enterRule(_localctx, 62, RULE_bounds);
		try {
			setState(367);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(364);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(365);
				memberCall();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(366);
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

	public static class PackageNameContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(ZenScriptParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(ZenScriptParser.IDENTIFIER, i);
		}
		public List<TerminalNode> DOT() { return getTokens(ZenScriptParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(ZenScriptParser.DOT, i);
		}
		public PackageNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_packageName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterPackageName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitPackageName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitPackageName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PackageNameContext packageName() throws RecognitionException {
		PackageNameContext _localctx = new PackageNameContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_packageName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(369);
			match(IDENTIFIER);
			setState(374);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(370);
					match(DOT);
					setState(371);
					match(IDENTIFIER);
					}
					} 
				}
				setState(376);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
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
	public static class ExpressionReturnContext extends ExpressionContext {
		public TerminalNode RETURN() { return getToken(ZenScriptParser.RETURN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionReturnContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionReturn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionReturn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionReturn(this);
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
	public static class ExpressionIDContext extends ExpressionContext {
		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }
		public ExpressionIDContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionID(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionID(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionID(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionAndContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode AND() { return getToken(ZenScriptParser.AND, 0); }
		public ExpressionAndContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionCatContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode TILDE() { return getToken(ZenScriptParser.TILDE, 0); }
		public ExpressionCatContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionCat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionCat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionCat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionAndAndContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode AND_AND() { return getToken(ZenScriptParser.AND_AND, 0); }
		public ExpressionAndAndContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionAndAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionAndAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionAndAnd(this);
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
	public static class ExpressionBreakContext extends ExpressionContext {
		public TerminalNode BREAK() { return getToken(ZenScriptParser.BREAK, 0); }
		public ExpressionBreakContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionBreak(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionBreak(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionBreak(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionCompareContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode LESS_EQUAL() { return getToken(ZenScriptParser.LESS_EQUAL, 0); }
		public TerminalNode GREATER_EQUAL() { return getToken(ZenScriptParser.GREATER_EQUAL, 0); }
		public TerminalNode GREATER() { return getToken(ZenScriptParser.GREATER, 0); }
		public TerminalNode LESS() { return getToken(ZenScriptParser.LESS, 0); }
		public TerminalNode EQUAL() { return getToken(ZenScriptParser.EQUAL, 0); }
		public TerminalNode NOT_EQUAL() { return getToken(ZenScriptParser.NOT_EQUAL, 0); }
		public ExpressionCompareContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionCompare(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionCompare(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionCompare(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionContinueContext extends ExpressionContext {
		public TerminalNode CONTINUE() { return getToken(ZenScriptParser.CONTINUE, 0); }
		public ExpressionContinueContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionContinue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionContinue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionContinue(this);
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
	public static class ExpressionInstanceofContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode INSTANCEOF() { return getToken(ZenScriptParser.INSTANCEOF, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ExpressionInstanceofContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionInstanceof(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionInstanceof(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionInstanceof(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionXorContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode XOR() { return getToken(ZenScriptParser.XOR, 0); }
		public ExpressionXorContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionXor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionXor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionXor(this);
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
	public static class ExpressionOrOrContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode OR_OR() { return getToken(ZenScriptParser.OR_OR, 0); }
		public ExpressionOrOrContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionOrOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionOrOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionOrOr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionOrContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode OR() { return getToken(ZenScriptParser.OR, 0); }
		public ExpressionOrContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionOr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionInContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode IN() { return getToken(ZenScriptParser.IN, 0); }
		public TerminalNode HAS() { return getToken(ZenScriptParser.HAS, 0); }
		public ExpressionInContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).enterExpressionIn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ZenScriptParserListener ) ((ZenScriptParserListener)listener).exitExpressionIn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionIn(this);
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
		int _startState = 66;
		enterRecursionRule(_localctx, 66, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(398);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				{
				_localctx = new ExpressionLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(378);
				literal();
				}
				break;
			case 2:
				{
				_localctx = new ExpressionReturnContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(379);
				match(RETURN);
				setState(381);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
				case 1:
					{
					setState(380);
					expression(0);
					}
					break;
				}
				}
				break;
			case 3:
				{
				_localctx = new ExpressionBreakContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(383);
				match(BREAK);
				}
				break;
			case 4:
				{
				_localctx = new ExpressionContinueContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(384);
				match(CONTINUE);
				}
				break;
			case 5:
				{
				_localctx = new ExpressionCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(385);
				methodCall();
				}
				break;
			case 6:
				{
				_localctx = new ExpressionCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(386);
				memberCall();
				}
				break;
			case 7:
				{
				_localctx = new ExpressionParensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(387);
				match(PAREN_OPEN);
				setState(388);
				expression(0);
				setState(389);
				match(PAREN_CLOSE);
				}
				break;
			case 8:
				{
				_localctx = new ExpressionUnaryContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(391);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADD) | (1L << SUB) | (1L << NOT))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(392);
				expression(19);
				}
				break;
			case 9:
				{
				_localctx = new ExpressionIDContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(393);
				match(IDENTIFIER);
				}
				break;
			case 10:
				{
				_localctx = new ExpressionBracketHandlerContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(394);
				bracketHandler();
				}
				break;
			case 11:
				{
				_localctx = new ExpressionArrayContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(395);
				array();
				}
				break;
			case 12:
				{
				_localctx = new ExpressionMapContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(396);
				map();
				}
				break;
			case 13:
				{
				_localctx = new ExpressionFunctionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(397);
				anonymousFunction();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(452);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(450);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionCallContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(400);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(401);
						match(DOT);
						setState(402);
						expression(22);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(403);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(404);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << MOD))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(405);
						expression(19);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionBinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(406);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(407);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(408);
						expression(18);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionCompareContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(409);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(410);
						_la = _input.LA(1);
						if ( !(((((_la - 50)) & ~0x3f) == 0 && ((1L << (_la - 50)) & ((1L << (LESS - 50)) | (1L << (GREATER - 50)) | (1L << (EQUAL - 50)) | (1L << (NOT_EQUAL - 50)) | (1L << (LESS_EQUAL - 50)) | (1L << (GREATER_EQUAL - 50)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(411);
						expression(17);
						}
						break;
					case 5:
						{
						_localctx = new ExpressionInContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(412);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(413);
						_la = _input.LA(1);
						if ( !(_la==IN || _la==HAS) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(414);
						expression(15);
						}
						break;
					case 6:
						{
						_localctx = new ExpressionAndContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(415);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(416);
						match(AND);
						setState(417);
						expression(14);
						}
						break;
					case 7:
						{
						_localctx = new ExpressionXorContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(418);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(419);
						match(XOR);
						setState(420);
						expression(13);
						}
						break;
					case 8:
						{
						_localctx = new ExpressionOrContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(421);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(422);
						match(OR);
						setState(423);
						expression(12);
						}
						break;
					case 9:
						{
						_localctx = new ExpressionAndAndContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(424);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(425);
						match(AND_AND);
						setState(426);
						expression(11);
						}
						break;
					case 10:
						{
						_localctx = new ExpressionOrOrContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(427);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(428);
						match(OR_OR);
						setState(429);
						expression(10);
						}
						break;
					case 11:
						{
						_localctx = new ExpressionCatContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(430);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(431);
						match(TILDE);
						setState(432);
						expression(9);
						}
						break;
					case 12:
						{
						_localctx = new ExpressionTrinaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(433);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(434);
						match(QUEST);
						setState(435);
						expression(0);
						setState(436);
						match(COLON);
						setState(437);
						expression(7);
						}
						break;
					case 13:
						{
						_localctx = new ExpressionAssignContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(439);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(440);
						_la = _input.LA(1);
						if ( !(((((_la - 59)) & ~0x3f) == 0 && ((1L << (_la - 59)) & ((1L << (ASSIGN - 59)) | (1L << (PLUS_ASSIGN - 59)) | (1L << (MINUS_ASSIGN - 59)) | (1L << (STAR_ASSIGN - 59)) | (1L << (DIV_ASSIGN - 59)) | (1L << (MOD_ASSIGN - 59)) | (1L << (XOR_ASSIGN - 59)) | (1L << (AND_ASSIGN - 59)) | (1L << (OR_ASSIGN - 59)) | (1L << (TILDE_ASSIGN - 59)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(441);
						expression(6);
						}
						break;
					case 14:
						{
						_localctx = new ExpressionArrayGetContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(442);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(443);
						match(BRACK_OPEN);
						setState(444);
						expression(0);
						setState(445);
						match(BRACK_CLOSE);
						}
						break;
					case 15:
						{
						_localctx = new ExpressionInstanceofContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(447);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(448);
						match(INSTANCEOF);
						setState(449);
						type();
						}
						break;
					}
					} 
				}
				setState(454);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
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
		enterRule(_localctx, 68, RULE_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(461);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,44,_ctx) ) {
			case 1:
				{
				setState(455);
				typeList();
				}
				break;
			case 2:
				{
				setState(456);
				typeMap();
				}
				break;
			case 3:
				{
				setState(457);
				typeArray();
				}
				break;
			case 4:
				{
				setState(458);
				typePrimitive();
				}
				break;
			case 5:
				{
				setState(459);
				typeClass();
				}
				break;
			case 6:
				{
				setState(460);
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
		enterRule(_localctx, 70, RULE_typeFunction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(463);
			match(FUNCTION);
			setState(464);
			match(PAREN_OPEN);
			setState(466);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << ANY) | (1L << BYTE) | (1L << SHORT) | (1L << INT) | (1L << LONG) | (1L << FLOAT) | (1L << DOUBLE) | (1L << BOOL) | (1L << VOID) | (1L << STRING) | (1L << BRACK_OPEN))) != 0) || _la==IDENTIFIER) {
				{
				setState(465);
				type();
				}
			}

			setState(472);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(468);
				match(COMMA);
				setState(469);
				type();
				}
				}
				setState(474);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(475);
			match(PAREN_CLOSE);
			setState(476);
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
		enterRule(_localctx, 72, RULE_typePrimitive);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(478);
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
		enterRule(_localctx, 74, RULE_typeArray);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(482);
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
				setState(480);
				typePrimitive();
				}
				break;
			case IDENTIFIER:
				{
				setState(481);
				typeClass();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(486); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(484);
					match(BRACK_OPEN);
					setState(485);
					match(BRACK_CLOSE);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(488); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
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
		enterRule(_localctx, 76, RULE_typeList);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(490);
			match(BRACK_OPEN);
			setState(493);
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
				setState(491);
				typePrimitive();
				}
				break;
			case IDENTIFIER:
				{
				setState(492);
				typeClass();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(495);
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
		enterRule(_localctx, 78, RULE_typeMap);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(499);
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
				setState(497);
				typePrimitive();
				}
				break;
			case IDENTIFIER:
				{
				setState(498);
				typeClass();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(508);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(501);
					match(BRACK_OPEN);
					setState(503);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << ANY) | (1L << BYTE) | (1L << SHORT) | (1L << INT) | (1L << LONG) | (1L << FLOAT) | (1L << DOUBLE) | (1L << BOOL) | (1L << VOID) | (1L << STRING) | (1L << BRACK_OPEN))) != 0) || _la==IDENTIFIER) {
						{
						setState(502);
						type();
						}
					}

					setState(505);
					match(BRACK_CLOSE);
					}
					} 
				}
				setState(510);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
			}
			setState(511);
			match(BRACK_OPEN);
			setState(512);
			type();
			setState(513);
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
		public PackageNameContext packageName() {
			return getRuleContext(PackageNameContext.class,0);
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
		enterRule(_localctx, 80, RULE_typeClass);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(515);
			packageName();
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
		enterRule(_localctx, 82, RULE_literal);
		try {
			setState(522);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DECIMAL_LITERAL:
			case HEX_LITERAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(517);
				integerLiteral();
				}
				break;
			case FLOATING_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(518);
				match(FLOATING_LITERAL);
				}
				break;
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 3);
				{
				setState(519);
				match(STRING_LITERAL);
				}
				break;
			case BOOLEAN_LITERAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(520);
				match(BOOLEAN_LITERAL);
				}
				break;
			case NULL_LITERAL:
				enterOuterAlt(_localctx, 5);
				{
				setState(521);
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
		enterRule(_localctx, 84, RULE_integerLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(524);
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
		enterRule(_localctx, 86, RULE_bracketHandler);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(526);
			match(LESS);
			setState(533);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC) | (1L << IMPORT) | (1L << FUNCTION) | (1L << AS) | (1L << TO) | (1L << IN) | (1L << HAS) | (1L << INSTANCEOF) | (1L << ANY) | (1L << BYTE) | (1L << SHORT) | (1L << INT) | (1L << LONG) | (1L << FLOAT) | (1L << DOUBLE) | (1L << BOOL) | (1L << VOID) | (1L << STRING) | (1L << IF) | (1L << ELSE) | (1L << FOR) | (1L << DO) | (1L << WHILE) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << FRIGGIN_CLASS) | (1L << FRIGGIN_CONSTRUCTOR) | (1L << ZEN_CLASS) | (1L << ZEN_CONSTRUCTOR) | (1L << PAREN_OPEN) | (1L << PAREN_CLOSE) | (1L << BRACK_OPEN) | (1L << BRACK_CLOSE) | (1L << BRACE_OPEN) | (1L << BRACE_CLOSE) | (1L << COMMA) | (1L << DOT) | (1L << SEMICOLON) | (1L << ADD) | (1L << SUB) | (1L << MUL) | (1L << DIV) | (1L << MOD) | (1L << TILDE) | (1L << NOT) | (1L << LESS) | (1L << XOR) | (1L << COLON) | (1L << QUEST) | (1L << BACKTICK) | (1L << DOLLAR) | (1L << AND) | (1L << OR) | (1L << ASSIGN) | (1L << AND_AND) | (1L << OR_OR) | (1L << EQUAL) | (1L << NOT_EQUAL))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (LESS_EQUAL - 64)) | (1L << (GREATER_EQUAL - 64)) | (1L << (PLUS_ASSIGN - 64)) | (1L << (MINUS_ASSIGN - 64)) | (1L << (STAR_ASSIGN - 64)) | (1L << (DIV_ASSIGN - 64)) | (1L << (MOD_ASSIGN - 64)) | (1L << (XOR_ASSIGN - 64)) | (1L << (AND_ASSIGN - 64)) | (1L << (OR_ASSIGN - 64)) | (1L << (TILDE_ASSIGN - 64)) | (1L << (DOT_DOT - 64)) | (1L << (DECIMAL_LITERAL - 64)) | (1L << (HEX_LITERAL - 64)) | (1L << (FLOATING_LITERAL - 64)) | (1L << (BOOLEAN_LITERAL - 64)) | (1L << (STRING_LITERAL - 64)) | (1L << (NULL_LITERAL - 64)) | (1L << (IDENTIFIER - 64)) | (1L << (WHITE_SPACE - 64)) | (1L << (BLOCK_COMMENT - 64)) | (1L << (LINE_COMMENT - 64)) | (1L << (Preprocessor - 64)))) != 0)) {
				{
				{
				setState(527);
				_la = _input.LA(1);
				if ( _la <= 0 || (_la==GREATER) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(529);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
				case 1:
					{
					setState(528);
					match(COLON);
					}
					break;
				}
				}
				}
				setState(535);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(536);
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
		case 33:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 21);
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
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3X\u021d\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\3\2\3\2\3\2\3\2\7\2_\n\2\f\2\16\2b\13\2\3\2\3\2\3\3\3\3\3\3"+
		"\3\3\5\3j\n\3\3\3\3\3\3\4\3\4\3\4\3\4\5\4r\n\4\3\4\3\4\3\5\3\5\3\5\3\5"+
		"\3\6\3\6\3\6\3\6\7\6~\n\6\f\6\16\6\u0081\13\6\3\6\3\6\3\7\3\7\3\7\3\7"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u008f\n\b\5\b\u0091\n\b\3\b\3\b\3\t\3\t\3"+
		"\t\3\t\5\t\u0099\n\t\3\t\3\t\3\n\3\n\3\n\5\n\u00a0\n\n\3\n\3\n\3\n\3\n"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\16"+
		"\3\17\3\17\3\17\5\17\u00b8\n\17\3\17\3\17\3\20\3\20\5\20\u00be\n\20\3"+
		"\20\3\20\7\20\u00c2\n\20\f\20\16\20\u00c5\13\20\3\20\3\20\3\21\3\21\5"+
		"\21\u00cb\n\21\3\22\3\22\5\22\u00cf\n\22\3\22\3\22\7\22\u00d3\n\22\f\22"+
		"\16\22\u00d6\13\22\3\22\3\22\3\23\3\23\3\23\5\23\u00dd\n\23\3\23\5\23"+
		"\u00e0\n\23\3\23\5\23\u00e3\n\23\3\24\3\24\7\24\u00e7\n\24\f\24\16\24"+
		"\u00ea\13\24\3\24\3\24\3\25\3\25\5\25\u00f0\n\25\3\25\3\25\7\25\u00f4"+
		"\n\25\f\25\16\25\u00f7\13\25\3\25\3\25\3\25\3\25\5\25\u00fd\n\25\3\25"+
		"\3\25\3\25\5\25\u0102\n\25\7\25\u0104\n\25\f\25\16\25\u0107\13\25\3\25"+
		"\3\25\5\25\u010b\n\25\3\26\3\26\5\26\u010f\n\26\3\26\3\26\7\26\u0113\n"+
		"\26\f\26\16\26\u0116\13\26\3\26\3\26\3\26\3\26\3\26\7\26\u011d\n\26\f"+
		"\26\16\26\u0120\13\26\5\26\u0122\n\26\3\26\5\26\u0125\n\26\3\27\3\27\3"+
		"\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3"+
		"\32\3\32\5\32\u0139\n\32\3\33\3\33\3\33\3\33\3\33\5\33\u0140\n\33\3\33"+
		"\3\33\3\33\3\33\5\33\u0146\n\33\5\33\u0148\n\33\3\34\3\34\3\34\3\34\3"+
		"\35\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3"+
		"\37\3\37\3\37\3\37\3\37\5\37\u0161\n\37\3\37\3\37\3\37\3\37\5\37\u0167"+
		"\n\37\5\37\u0169\n\37\3 \3 \3 \3 \3!\3!\3!\5!\u0172\n!\3\"\3\"\3\"\7\""+
		"\u0177\n\"\f\"\16\"\u017a\13\"\3#\3#\3#\3#\5#\u0180\n#\3#\3#\3#\3#\3#"+
		"\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\5#\u0191\n#\3#\3#\3#\3#\3#\3#\3#\3#\3#"+
		"\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#"+
		"\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\7#\u01c5\n#\f#"+
		"\16#\u01c8\13#\3$\3$\3$\3$\3$\3$\5$\u01d0\n$\3%\3%\3%\5%\u01d5\n%\3%\3"+
		"%\7%\u01d9\n%\f%\16%\u01dc\13%\3%\3%\3%\3&\3&\3\'\3\'\5\'\u01e5\n\'\3"+
		"\'\3\'\6\'\u01e9\n\'\r\'\16\'\u01ea\3(\3(\3(\5(\u01f0\n(\3(\3(\3)\3)\5"+
		")\u01f6\n)\3)\3)\5)\u01fa\n)\3)\7)\u01fd\n)\f)\16)\u0200\13)\3)\3)\3)"+
		"\3)\3*\3*\3+\3+\3+\3+\3+\5+\u020d\n+\3,\3,\3-\3-\3-\5-\u0214\n-\7-\u0216"+
		"\n-\f-\16-\u0219\13-\3-\3-\3-\2\3D.\2\4\6\b\n\f\16\20\22\24\26\30\32\34"+
		"\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVX\2\16\3\2\3\4\3\2\5\6\4\2\n\n"+
		"MM\4\2-.\63\63\3\2/\61\3\2-.\4\2\64\65@C\3\2\13\f\4\2==DL\3\2\16\27\3"+
		"\2NO\3\2\65\65\2\u0255\2`\3\2\2\2\4e\3\2\2\2\6m\3\2\2\2\bu\3\2\2\2\ny"+
		"\3\2\2\2\f\u0084\3\2\2\2\16\u0088\3\2\2\2\20\u0094\3\2\2\2\22\u009c\3"+
		"\2\2\2\24\u00a5\3\2\2\2\26\u00ac\3\2\2\2\30\u00af\3\2\2\2\32\u00b1\3\2"+
		"\2\2\34\u00b4\3\2\2\2\36\u00bb\3\2\2\2 \u00c8\3\2\2\2\"\u00cc\3\2\2\2"+
		"$\u00e2\3\2\2\2&\u00e4\3\2\2\2(\u010a\3\2\2\2*\u0124\3\2\2\2,\u0126\3"+
		"\2\2\2.\u012a\3\2\2\2\60\u012c\3\2\2\2\62\u0138\3\2\2\2\64\u013a\3\2\2"+
		"\2\66\u0149\3\2\2\28\u014d\3\2\2\2:\u0153\3\2\2\2<\u0168\3\2\2\2>\u016a"+
		"\3\2\2\2@\u0171\3\2\2\2B\u0173\3\2\2\2D\u0190\3\2\2\2F\u01cf\3\2\2\2H"+
		"\u01d1\3\2\2\2J\u01e0\3\2\2\2L\u01e4\3\2\2\2N\u01ec\3\2\2\2P\u01f5\3\2"+
		"\2\2R\u0205\3\2\2\2T\u020c\3\2\2\2V\u020e\3\2\2\2X\u0210\3\2\2\2Z_\5\4"+
		"\3\2[_\5\6\4\2\\_\5\b\5\2]_\5\62\32\2^Z\3\2\2\2^[\3\2\2\2^\\\3\2\2\2^"+
		"]\3\2\2\2_b\3\2\2\2`^\3\2\2\2`a\3\2\2\2ac\3\2\2\2b`\3\2\2\2cd\7\2\2\3"+
		"d\3\3\2\2\2ef\7\7\2\2fi\5B\"\2gh\7\t\2\2hj\7T\2\2ig\3\2\2\2ij\3\2\2\2"+
		"jk\3\2\2\2kl\7,\2\2l\5\3\2\2\2mn\7\b\2\2no\7T\2\2oq\5\36\20\2pr\5\26\f"+
		"\2qp\3\2\2\2qr\3\2\2\2rs\3\2\2\2st\5&\24\2t\7\3\2\2\2uv\7\"\2\2vw\7T\2"+
		"\2wx\5\n\6\2x\t\3\2\2\2y\177\7(\2\2z~\5\f\7\2{~\5\16\b\2|~\5\20\t\2}z"+
		"\3\2\2\2}{\3\2\2\2}|\3\2\2\2~\u0081\3\2\2\2\177}\3\2\2\2\177\u0080\3\2"+
		"\2\2\u0080\u0082\3\2\2\2\u0081\177\3\2\2\2\u0082\u0083\7)\2\2\u0083\13"+
		"\3\2\2\2\u0084\u0085\7#\2\2\u0085\u0086\5\36\20\2\u0086\u0087\5&\24\2"+
		"\u0087\r\3\2\2\2\u0088\u0089\t\2\2\2\u0089\u008a\7T\2\2\u008a\u0090\5"+
		"\26\f\2\u008b\u008c\7=\2\2\u008c\u008e\5D#\2\u008d\u008f\5\26\f\2\u008e"+
		"\u008d\3\2\2\2\u008e\u008f\3\2\2\2\u008f\u0091\3\2\2\2\u0090\u008b\3\2"+
		"\2\2\u0090\u0091\3\2\2\2\u0091\u0092\3\2\2\2\u0092\u0093\7,\2\2\u0093"+
		"\17\3\2\2\2\u0094\u0095\7\b\2\2\u0095\u0096\7T\2\2\u0096\u0098\5\36\20"+
		"\2\u0097\u0099\5\26\f\2\u0098\u0097\3\2\2\2\u0098\u0099\3\2\2\2\u0099"+
		"\u009a\3\2\2\2\u009a\u009b\5&\24\2\u009b\21\3\2\2\2\u009c\u009d\t\2\2"+
		"\2\u009d\u009f\7T\2\2\u009e\u00a0\5\26\f\2\u009f\u009e\3\2\2\2\u009f\u00a0"+
		"\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a2\7=\2\2\u00a2\u00a3\5D#\2\u00a3"+
		"\u00a4\7,\2\2\u00a4\23\3\2\2\2\u00a5\u00a6\t\3\2\2\u00a6\u00a7\7T\2\2"+
		"\u00a7\u00a8\5\26\f\2\u00a8\u00a9\7=\2\2\u00a9\u00aa\5D#\2\u00aa\u00ab"+
		"\7,\2\2\u00ab\25\3\2\2\2\u00ac\u00ad\7\t\2\2\u00ad\u00ae\5F$\2\u00ae\27"+
		"\3\2\2\2\u00af\u00b0\7T\2\2\u00b0\31\3\2\2\2\u00b1\u00b2\7T\2\2\u00b2"+
		"\u00b3\5\"\22\2\u00b3\33\3\2\2\2\u00b4\u00b5\7\b\2\2\u00b5\u00b7\5\36"+
		"\20\2\u00b6\u00b8\5\26\f\2\u00b7\u00b6\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8"+
		"\u00b9\3\2\2\2\u00b9\u00ba\5&\24\2\u00ba\35\3\2\2\2\u00bb\u00bd\7$\2\2"+
		"\u00bc\u00be\5 \21\2\u00bd\u00bc\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\u00c3"+
		"\3\2\2\2\u00bf\u00c0\7*\2\2\u00c0\u00c2\5 \21\2\u00c1\u00bf\3\2\2\2\u00c2"+
		"\u00c5\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c6\3\2"+
		"\2\2\u00c5\u00c3\3\2\2\2\u00c6\u00c7\7%\2\2\u00c7\37\3\2\2\2\u00c8\u00ca"+
		"\7T\2\2\u00c9\u00cb\5\26\f\2\u00ca\u00c9\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb"+
		"!\3\2\2\2\u00cc\u00ce\7$\2\2\u00cd\u00cf\5$\23\2\u00ce\u00cd\3\2\2\2\u00ce"+
		"\u00cf\3\2\2\2\u00cf\u00d4\3\2\2\2\u00d0\u00d1\7*\2\2\u00d1\u00d3\5$\23"+
		"\2\u00d2\u00d0\3\2\2\2\u00d3\u00d6\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d4\u00d5"+
		"\3\2\2\2\u00d5\u00d7\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d7\u00d8\7%\2\2\u00d8"+
		"#\3\2\2\2\u00d9\u00dd\7T\2\2\u00da\u00dd\5D#\2\u00db\u00dd\5T+\2\u00dc"+
		"\u00d9\3\2\2\2\u00dc\u00da\3\2\2\2\u00dc\u00db\3\2\2\2\u00dd\u00df\3\2"+
		"\2\2\u00de\u00e0\5\26\f\2\u00df\u00de\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0"+
		"\u00e3\3\2\2\2\u00e1\u00e3\5\34\17\2\u00e2\u00dc\3\2\2\2\u00e2\u00e1\3"+
		"\2\2\2\u00e3%\3\2\2\2\u00e4\u00e8\7(\2\2\u00e5\u00e7\5\62\32\2\u00e6\u00e5"+
		"\3\2\2\2\u00e7\u00ea\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9"+
		"\u00eb\3\2\2\2\u00ea\u00e8\3\2\2\2\u00eb\u00ec\7)\2\2\u00ec\'\3\2\2\2"+
		"\u00ed\u00ef\7&\2\2\u00ee\u00f0\5(\25\2\u00ef\u00ee\3\2\2\2\u00ef\u00f0"+
		"\3\2\2\2\u00f0\u00f5\3\2\2\2\u00f1\u00f2\7*\2\2\u00f2\u00f4\5(\25\2\u00f3"+
		"\u00f1\3\2\2\2\u00f4\u00f7\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f5\u00f6\3\2"+
		"\2\2\u00f6\u00f8\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f8\u010b\7\'\2\2\u00f9"+
		"\u00fc\7&\2\2\u00fa\u00fd\5T+\2\u00fb\u00fd\5D#\2\u00fc\u00fa\3\2\2\2"+
		"\u00fc\u00fb\3\2\2\2\u00fd\u0105\3\2\2\2\u00fe\u0101\7*\2\2\u00ff\u0102"+
		"\5T+\2\u0100\u0102\5D#\2\u0101\u00ff\3\2\2\2\u0101\u0100\3\2\2\2\u0102"+
		"\u0104\3\2\2\2\u0103\u00fe\3\2\2\2\u0104\u0107\3\2\2\2\u0105\u0103\3\2"+
		"\2\2\u0105\u0106\3\2\2\2\u0106\u0108\3\2\2\2\u0107\u0105\3\2\2\2\u0108"+
		"\u0109\7\'\2\2\u0109\u010b\3\2\2\2\u010a\u00ed\3\2\2\2\u010a\u00f9\3\2"+
		"\2\2\u010b)\3\2\2\2\u010c\u010e\7(\2\2\u010d\u010f\5*\26\2\u010e\u010d"+
		"\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0114\3\2\2\2\u0110\u0111\7*\2\2\u0111"+
		"\u0113\5*\26\2\u0112\u0110\3\2\2\2\u0113\u0116\3\2\2\2\u0114\u0112\3\2"+
		"\2\2\u0114\u0115\3\2\2\2\u0115\u0117\3\2\2\2\u0116\u0114\3\2\2\2\u0117"+
		"\u0125\7)\2\2\u0118\u0121\7(\2\2\u0119\u011e\5,\27\2\u011a\u011b\7*\2"+
		"\2\u011b\u011d\5,\27\2\u011c\u011a\3\2\2\2\u011d\u0120\3\2\2\2\u011e\u011c"+
		"\3\2\2\2\u011e\u011f\3\2\2\2\u011f\u0122\3\2\2\2\u0120\u011e\3\2\2\2\u0121"+
		"\u0119\3\2\2\2\u0121\u0122\3\2\2\2\u0122\u0123\3\2\2\2\u0123\u0125\7)"+
		"\2\2\u0124\u010c\3\2\2\2\u0124\u0118\3\2\2\2\u0125+\3\2\2\2\u0126\u0127"+
		"\5.\30\2\u0127\u0128\7\67\2\2\u0128\u0129\5\60\31\2\u0129-\3\2\2\2\u012a"+
		"\u012b\5D#\2\u012b/\3\2\2\2\u012c\u012d\5D#\2\u012d\61\3\2\2\2\u012e\u0139"+
		"\5\64\33\2\u012f\u0139\5\66\34\2\u0130\u0139\58\35\2\u0131\u0139\5:\36"+
		"\2\u0132\u0139\5\22\n\2\u0133\u0139\5\24\13\2\u0134\u0139\5\6\4\2\u0135"+
		"\u0136\5D#\2\u0136\u0137\7,\2\2\u0137\u0139\3\2\2\2\u0138\u012e\3\2\2"+
		"\2\u0138\u012f\3\2\2\2\u0138\u0130\3\2\2\2\u0138\u0131\3\2\2\2\u0138\u0132"+
		"\3\2\2\2\u0138\u0133\3\2\2\2\u0138\u0134\3\2\2\2\u0138\u0135\3\2\2\2\u0139"+
		"\63\3\2\2\2\u013a\u013b\7\30\2\2\u013b\u013f\5D#\2\u013c\u0140\5\62\32"+
		"\2\u013d\u0140\5&\24\2\u013e\u0140\3\2\2\2\u013f\u013c\3\2\2\2\u013f\u013d"+
		"\3\2\2\2\u013f\u013e\3\2\2\2\u0140\u0147\3\2\2\2\u0141\u0145\7\31\2\2"+
		"\u0142\u0146\5\62\32\2\u0143\u0146\5&\24\2\u0144\u0146\3\2\2\2\u0145\u0142"+
		"\3\2\2\2\u0145\u0143\3\2\2\2\u0145\u0144\3\2\2\2\u0146\u0148\3\2\2\2\u0147"+
		"\u0141\3\2\2\2\u0147\u0148\3\2\2\2\u0148\65\3\2\2\2\u0149\u014a\7\32\2"+
		"\2\u014a\u014b\5<\37\2\u014b\u014c\5&\24\2\u014c\67\3\2\2\2\u014d\u014e"+
		"\7\34\2\2\u014e\u014f\7$\2\2\u014f\u0150\5D#\2\u0150\u0151\7%\2\2\u0151"+
		"\u0152\5&\24\2\u01529\3\2\2\2\u0153\u0154\7\33\2\2\u0154\u0155\5&\24\2"+
		"\u0155\u0156\7\34\2\2\u0156\u0157\7$\2\2\u0157\u0158\5D#\2\u0158\u0159"+
		"\7%\2\2\u0159;\3\2\2\2\u015a\u015b\7T\2\2\u015b\u015c\7\13\2\2\u015c\u0169"+
		"\5> \2\u015d\u0160\7T\2\2\u015e\u015f\7*\2\2\u015f\u0161\7T\2\2\u0160"+
		"\u015e\3\2\2\2\u0160\u0161\3\2\2\2\u0161\u0162\3\2\2\2\u0162\u0166\7\13"+
		"\2\2\u0163\u0167\5D#\2\u0164\u0167\5\30\r\2\u0165\u0167\5\32\16\2\u0166"+
		"\u0163\3\2\2\2\u0166\u0164\3\2\2\2\u0166\u0165\3\2\2\2\u0167\u0169\3\2"+
		"\2\2\u0168\u015a\3\2\2\2\u0168\u015d\3\2\2\2\u0169=\3\2\2\2\u016a\u016b"+
		"\5@!\2\u016b\u016c\t\4\2\2\u016c\u016d\5@!\2\u016d?\3\2\2\2\u016e\u0172"+
		"\5D#\2\u016f\u0172\5\30\r\2\u0170\u0172\5\32\16\2\u0171\u016e\3\2\2\2"+
		"\u0171\u016f\3\2\2\2\u0171\u0170\3\2\2\2\u0172A\3\2\2\2\u0173\u0178\7"+
		"T\2\2\u0174\u0175\7+\2\2\u0175\u0177\7T\2\2\u0176\u0174\3\2\2\2\u0177"+
		"\u017a\3\2\2\2\u0178\u0176\3\2\2\2\u0178\u0179\3\2\2\2\u0179C\3\2\2\2"+
		"\u017a\u0178\3\2\2\2\u017b\u017c\b#\1\2\u017c\u0191\5T+\2\u017d\u017f"+
		"\7\37\2\2\u017e\u0180\5D#\2\u017f\u017e\3\2\2\2\u017f\u0180\3\2\2\2\u0180"+
		"\u0191\3\2\2\2\u0181\u0191\7\35\2\2\u0182\u0191\7\36\2\2\u0183\u0191\5"+
		"\32\16\2\u0184\u0191\5\30\r\2\u0185\u0186\7$\2\2\u0186\u0187\5D#\2\u0187"+
		"\u0188\7%\2\2\u0188\u0191\3\2\2\2\u0189\u018a\t\5\2\2\u018a\u0191\5D#"+
		"\25\u018b\u0191\7T\2\2\u018c\u0191\5X-\2\u018d\u0191\5(\25\2\u018e\u0191"+
		"\5*\26\2\u018f\u0191\5\34\17\2\u0190\u017b\3\2\2\2\u0190\u017d\3\2\2\2"+
		"\u0190\u0181\3\2\2\2\u0190\u0182\3\2\2\2\u0190\u0183\3\2\2\2\u0190\u0184"+
		"\3\2\2\2\u0190\u0185\3\2\2\2\u0190\u0189\3\2\2\2\u0190\u018b\3\2\2\2\u0190"+
		"\u018c\3\2\2\2\u0190\u018d\3\2\2\2\u0190\u018e\3\2\2\2\u0190\u018f\3\2"+
		"\2\2\u0191\u01c6\3\2\2\2\u0192\u0193\f\27\2\2\u0193\u0194\7+\2\2\u0194"+
		"\u01c5\5D#\30\u0195\u0196\f\24\2\2\u0196\u0197\t\6\2\2\u0197\u01c5\5D"+
		"#\25\u0198\u0199\f\23\2\2\u0199\u019a\t\7\2\2\u019a\u01c5\5D#\24\u019b"+
		"\u019c\f\22\2\2\u019c\u019d\t\b\2\2\u019d\u01c5\5D#\23\u019e\u019f\f\20"+
		"\2\2\u019f\u01a0\t\t\2\2\u01a0\u01c5\5D#\21\u01a1\u01a2\f\17\2\2\u01a2"+
		"\u01a3\7;\2\2\u01a3\u01c5\5D#\20\u01a4\u01a5\f\16\2\2\u01a5\u01a6\7\66"+
		"\2\2\u01a6\u01c5\5D#\17\u01a7\u01a8\f\r\2\2\u01a8\u01a9\7<\2\2\u01a9\u01c5"+
		"\5D#\16\u01aa\u01ab\f\f\2\2\u01ab\u01ac\7>\2\2\u01ac\u01c5\5D#\r\u01ad"+
		"\u01ae\f\13\2\2\u01ae\u01af\7?\2\2\u01af\u01c5\5D#\f\u01b0\u01b1\f\n\2"+
		"\2\u01b1\u01b2\7\62\2\2\u01b2\u01c5\5D#\13\u01b3\u01b4\f\t\2\2\u01b4\u01b5"+
		"\78\2\2\u01b5\u01b6\5D#\2\u01b6\u01b7\7\67\2\2\u01b7\u01b8\5D#\t\u01b8"+
		"\u01c5\3\2\2\2\u01b9\u01ba\f\b\2\2\u01ba\u01bb\t\n\2\2\u01bb\u01c5\5D"+
		"#\b\u01bc\u01bd\f\26\2\2\u01bd\u01be\7&\2\2\u01be\u01bf\5D#\2\u01bf\u01c0"+
		"\7\'\2\2\u01c0\u01c5\3\2\2\2\u01c1\u01c2\f\21\2\2\u01c2\u01c3\7\r\2\2"+
		"\u01c3\u01c5\5F$\2\u01c4\u0192\3\2\2\2\u01c4\u0195\3\2\2\2\u01c4\u0198"+
		"\3\2\2\2\u01c4\u019b\3\2\2\2\u01c4\u019e\3\2\2\2\u01c4\u01a1\3\2\2\2\u01c4"+
		"\u01a4\3\2\2\2\u01c4\u01a7\3\2\2\2\u01c4\u01aa\3\2\2\2\u01c4\u01ad\3\2"+
		"\2\2\u01c4\u01b0\3\2\2\2\u01c4\u01b3\3\2\2\2\u01c4\u01b9\3\2\2\2\u01c4"+
		"\u01bc\3\2\2\2\u01c4\u01c1\3\2\2\2\u01c5\u01c8\3\2\2\2\u01c6\u01c4\3\2"+
		"\2\2\u01c6\u01c7\3\2\2\2\u01c7E\3\2\2\2\u01c8\u01c6\3\2\2\2\u01c9\u01d0"+
		"\5N(\2\u01ca\u01d0\5P)\2\u01cb\u01d0\5L\'\2\u01cc\u01d0\5J&\2\u01cd\u01d0"+
		"\5R*\2\u01ce\u01d0\5H%\2\u01cf\u01c9\3\2\2\2\u01cf\u01ca\3\2\2\2\u01cf"+
		"\u01cb\3\2\2\2\u01cf\u01cc\3\2\2\2\u01cf\u01cd\3\2\2\2\u01cf\u01ce\3\2"+
		"\2\2\u01d0G\3\2\2\2\u01d1\u01d2\7\b\2\2\u01d2\u01d4\7$\2\2\u01d3\u01d5"+
		"\5F$\2\u01d4\u01d3\3\2\2\2\u01d4\u01d5\3\2\2\2\u01d5\u01da\3\2\2\2\u01d6"+
		"\u01d7\7*\2\2\u01d7\u01d9\5F$\2\u01d8\u01d6\3\2\2\2\u01d9\u01dc\3\2\2"+
		"\2\u01da\u01d8\3\2\2\2\u01da\u01db\3\2\2\2\u01db\u01dd\3\2\2\2\u01dc\u01da"+
		"\3\2\2\2\u01dd\u01de\7%\2\2\u01de\u01df\5F$\2\u01dfI\3\2\2\2\u01e0\u01e1"+
		"\t\13\2\2\u01e1K\3\2\2\2\u01e2\u01e5\5J&\2\u01e3\u01e5\5R*\2\u01e4\u01e2"+
		"\3\2\2\2\u01e4\u01e3\3\2\2\2\u01e5\u01e8\3\2\2\2\u01e6\u01e7\7&\2\2\u01e7"+
		"\u01e9\7\'\2\2\u01e8\u01e6\3\2\2\2\u01e9\u01ea\3\2\2\2\u01ea\u01e8\3\2"+
		"\2\2\u01ea\u01eb\3\2\2\2\u01ebM\3\2\2\2\u01ec\u01ef\7&\2\2\u01ed\u01f0"+
		"\5J&\2\u01ee\u01f0\5R*\2\u01ef\u01ed\3\2\2\2\u01ef\u01ee\3\2\2\2\u01f0"+
		"\u01f1\3\2\2\2\u01f1\u01f2\7\'\2\2\u01f2O\3\2\2\2\u01f3\u01f6\5J&\2\u01f4"+
		"\u01f6\5R*\2\u01f5\u01f3\3\2\2\2\u01f5\u01f4\3\2\2\2\u01f6\u01fe\3\2\2"+
		"\2\u01f7\u01f9\7&\2\2\u01f8\u01fa\5F$\2\u01f9\u01f8\3\2\2\2\u01f9\u01fa"+
		"\3\2\2\2\u01fa\u01fb\3\2\2\2\u01fb\u01fd\7\'\2\2\u01fc\u01f7\3\2\2\2\u01fd"+
		"\u0200\3\2\2\2\u01fe\u01fc\3\2\2\2\u01fe\u01ff\3\2\2\2\u01ff\u0201\3\2"+
		"\2\2\u0200\u01fe\3\2\2\2\u0201\u0202\7&\2\2\u0202\u0203\5F$\2\u0203\u0204"+
		"\7\'\2\2\u0204Q\3\2\2\2\u0205\u0206\5B\"\2\u0206S\3\2\2\2\u0207\u020d"+
		"\5V,\2\u0208\u020d\7P\2\2\u0209\u020d\7R\2\2\u020a\u020d\7Q\2\2\u020b"+
		"\u020d\7S\2\2\u020c\u0207\3\2\2\2\u020c\u0208\3\2\2\2\u020c\u0209\3\2"+
		"\2\2\u020c\u020a\3\2\2\2\u020c\u020b\3\2\2\2\u020dU\3\2\2\2\u020e\u020f"+
		"\t\f\2\2\u020fW\3\2\2\2\u0210\u0217\7\64\2\2\u0211\u0213\n\r\2\2\u0212"+
		"\u0214\7\67\2\2\u0213\u0212\3\2\2\2\u0213\u0214\3\2\2\2\u0214\u0216\3"+
		"\2\2\2\u0215\u0211\3\2\2\2\u0216\u0219\3\2\2\2\u0217\u0215\3\2\2\2\u0217"+
		"\u0218\3\2\2\2\u0218\u021a\3\2\2\2\u0219\u0217\3\2\2\2\u021a\u021b\7\65"+
		"\2\2\u021bY\3\2\2\2:^`iq}\177\u008e\u0090\u0098\u009f\u00b7\u00bd\u00c3"+
		"\u00ca\u00ce\u00d4\u00dc\u00df\u00e2\u00e8\u00ef\u00f5\u00fc\u0101\u0105"+
		"\u010a\u010e\u0114\u011e\u0121\u0124\u0138\u013f\u0145\u0147\u0160\u0166"+
		"\u0168\u0171\u0178\u017f\u0190\u01c4\u01c6\u01cf\u01d4\u01da\u01e4\u01ea"+
		"\u01ef\u01f5\u01f9\u01fe\u020c\u0213\u0217";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}