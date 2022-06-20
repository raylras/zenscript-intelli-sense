// Generated from ZenScriptParser.g4 by ANTLR 4.10.1
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
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		VAR=1, VAL=2, GLOBAL=3, STATIC=4, IMPORT=5, FUNCTION=6, AS=7, TO=8, IN=9, 
		HAS=10, INSTANCEOF=11, THIS=12, ANY=13, BYTE=14, BYTE_OBJ=15, SHORT=16, 
		SHORT_OBJ=17, INT=18, INT_OBJ=19, LONG=20, LONG_OBJ=21, FLOAT=22, FLOAT_OBJ=23, 
		DOUBLE=24, DOUBLE_OBJ=25, BOOL=26, BOOL_OBJ=27, VOID=28, STRING=29, IF=30, 
		ELSE=31, FOR=32, DO=33, WHILE=34, BREAK=35, CONTINUE=36, RETURN=37, FRIGGIN_CLASS=38, 
		FRIGGIN_CONSTRUCTOR=39, ZEN_CLASS=40, ZEN_CONSTRUCTOR=41, PAREN_OPEN=42, 
		PAREN_CLOSE=43, BRACK_OPEN=44, BRACK_CLOSE=45, BRACE_OPEN=46, BRACE_CLOSE=47, 
		COMMA=48, DOT=49, SEMICOLON=50, ADD=51, SUB=52, MUL=53, DIV=54, MOD=55, 
		CAT=56, NOT=57, LESS=58, GREATER=59, XOR=60, COLON=61, QUEST=62, BACKTICK=63, 
		DOLLAR=64, AND=65, OR=66, ASSIGN=67, AND_AND=68, OR_OR=69, EQUAL=70, NOT_EQUAL=71, 
		LESS_EQUAL=72, GREATER_EQUAL=73, PLUS_ASSIGN=74, MINUS_ASSIGN=75, STAR_ASSIGN=76, 
		DIV_ASSIGN=77, MOD_ASSIGN=78, XOR_ASSIGN=79, AND_ASSIGN=80, OR_ASSIGN=81, 
		CAT_ASSIGN=82, DOT_DOT=83, DECIMAL_LITERAL=84, HEX_LITERAL=85, FLOATING_LITERAL=86, 
		BOOLEAN_LITERAL=87, STRING_LITERAL=88, NULL_LITERAL=89, IDENTIFIER=90, 
		WHITE_SPACE=91, BLOCK_COMMENT=92, LINE_COMMENT=93, Preprocessor=94;
	public static final int
		RULE_scriptUnit = 0, RULE_importDeclaration = 1, RULE_reference = 2, RULE_aliasDeclaration = 3, 
		RULE_functionDeclaration = 4, RULE_formalParameter = 5, RULE_defaultValue = 6, 
		RULE_zenClassDeclaration = 7, RULE_constructorDeclaration = 8, RULE_block = 9, 
		RULE_statement = 10, RULE_blockStatement = 11, RULE_returnStatement = 12, 
		RULE_breakStatement = 13, RULE_continueStatement = 14, RULE_ifElseStatement = 15, 
		RULE_foreachStatement = 16, RULE_whileStatement = 17, RULE_variableDeclStatement = 18, 
		RULE_expressionStatement = 19, RULE_expression = 20, RULE_mapEntry = 21, 
		RULE_type = 22, RULE_builtin = 23, RULE_argumentTypeList = 24, RULE_literal = 25, 
		RULE_identifier = 26;
	private static String[] makeRuleNames() {
		return new String[] {
			"scriptUnit", "importDeclaration", "reference", "aliasDeclaration", "functionDeclaration", 
			"formalParameter", "defaultValue", "zenClassDeclaration", "constructorDeclaration", 
			"block", "statement", "blockStatement", "returnStatement", "breakStatement", 
			"continueStatement", "ifElseStatement", "foreachStatement", "whileStatement", 
			"variableDeclStatement", "expressionStatement", "expression", "mapEntry", 
			"type", "builtin", "argumentTypeList", "literal", "identifier"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'var'", "'val'", "'global'", "'static'", "'import'", "'function'", 
			"'as'", "'to'", "'in'", "'has'", "'instanceof'", "'this'", "'any'", "'byte'", 
			"'byte?'", "'short'", "'short?'", "'int'", "'int?'", "'long'", "'long?'", 
			"'float'", "'float?'", "'double'", "'double?'", "'bool'", "'bool?'", 
			"'void'", "'string'", "'if'", "'else'", "'for'", "'do'", "'while'", "'break'", 
			"'continue'", "'return'", "'frigginClass'", "'frigginConstructor'", "'zenClass'", 
			"'zenConstructor'", "'('", "')'", "'['", "']'", "'{'", "'}'", "','", 
			"'.'", "';'", "'+'", "'-'", "'*'", "'/'", "'%'", "'~'", "'!'", "'<'", 
			"'>'", "'^'", "':'", "'?'", "'`'", "'$'", "'&'", "'|'", "'='", "'&&'", 
			"'||'", "'=='", "'!='", "'<='", "'>='", "'+='", "'-='", "'*='", "'/='", 
			"'%='", "'^='", "'&='", "'|='", "'~='", "'..'", null, null, null, null, 
			null, "'null'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "VAR", "VAL", "GLOBAL", "STATIC", "IMPORT", "FUNCTION", "AS", "TO", 
			"IN", "HAS", "INSTANCEOF", "THIS", "ANY", "BYTE", "BYTE_OBJ", "SHORT", 
			"SHORT_OBJ", "INT", "INT_OBJ", "LONG", "LONG_OBJ", "FLOAT", "FLOAT_OBJ", 
			"DOUBLE", "DOUBLE_OBJ", "BOOL", "BOOL_OBJ", "VOID", "STRING", "IF", "ELSE", 
			"FOR", "DO", "WHILE", "BREAK", "CONTINUE", "RETURN", "FRIGGIN_CLASS", 
			"FRIGGIN_CONSTRUCTOR", "ZEN_CLASS", "ZEN_CONSTRUCTOR", "PAREN_OPEN", 
			"PAREN_CLOSE", "BRACK_OPEN", "BRACK_CLOSE", "BRACE_OPEN", "BRACE_CLOSE", 
			"COMMA", "DOT", "SEMICOLON", "ADD", "SUB", "MUL", "DIV", "MOD", "CAT", 
			"NOT", "LESS", "GREATER", "XOR", "COLON", "QUEST", "BACKTICK", "DOLLAR", 
			"AND", "OR", "ASSIGN", "AND_AND", "OR_OR", "EQUAL", "NOT_EQUAL", "LESS_EQUAL", 
			"GREATER_EQUAL", "PLUS_ASSIGN", "MINUS_ASSIGN", "STAR_ASSIGN", "DIV_ASSIGN", 
			"MOD_ASSIGN", "XOR_ASSIGN", "AND_ASSIGN", "OR_ASSIGN", "CAT_ASSIGN", 
			"DOT_DOT", "DECIMAL_LITERAL", "HEX_LITERAL", "FLOATING_LITERAL", "BOOLEAN_LITERAL", 
			"STRING_LITERAL", "NULL_LITERAL", "IDENTIFIER", "WHITE_SPACE", "BLOCK_COMMENT", 
			"LINE_COMMENT", "Preprocessor"
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

	public static class ScriptUnitContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(ZenScriptParser.EOF, 0); }
		public List<ImportDeclarationContext> importDeclaration() {
			return getRuleContexts(ImportDeclarationContext.class);
		}
		public ImportDeclarationContext importDeclaration(int i) {
			return getRuleContext(ImportDeclarationContext.class,i);
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
		public ScriptUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scriptUnit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitScriptUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScriptUnitContext scriptUnit() throws RecognitionException {
		ScriptUnitContext _localctx = new ScriptUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_scriptUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC) | (1L << IMPORT) | (1L << FUNCTION) | (1L << TO) | (1L << THIS) | (1L << IF) | (1L << FOR) | (1L << WHILE) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << ZEN_CLASS) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 84)) & ~0x3f) == 0 && ((1L << (_la - 84)) & ((1L << (DECIMAL_LITERAL - 84)) | (1L << (HEX_LITERAL - 84)) | (1L << (FLOATING_LITERAL - 84)) | (1L << (BOOLEAN_LITERAL - 84)) | (1L << (STRING_LITERAL - 84)) | (1L << (NULL_LITERAL - 84)) | (1L << (IDENTIFIER - 84)))) != 0)) {
				{
				setState(58);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(54);
					importDeclaration();
					}
					break;
				case 2:
					{
					setState(55);
					functionDeclaration();
					}
					break;
				case 3:
					{
					setState(56);
					zenClassDeclaration();
					}
					break;
				case 4:
					{
					setState(57);
					statement();
					}
					break;
				}
				}
				setState(62);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(63);
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

	public static class ImportDeclarationContext extends ParserRuleContext {
		public TerminalNode IMPORT() { return getToken(ZenScriptParser.IMPORT, 0); }
		public ReferenceContext reference() {
			return getRuleContext(ReferenceContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }
		public AliasDeclarationContext aliasDeclaration() {
			return getRuleContext(AliasDeclarationContext.class,0);
		}
		public ImportDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitImportDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportDeclarationContext importDeclaration() throws RecognitionException {
		ImportDeclarationContext _localctx = new ImportDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_importDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			match(IMPORT);
			setState(66);
			reference();
			setState(68);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(67);
				aliasDeclaration();
				}
			}

			setState(70);
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

	public static class ReferenceContext extends ParserRuleContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public List<TerminalNode> DOT() { return getTokens(ZenScriptParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(ZenScriptParser.DOT, i);
		}
		public ReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reference; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReferenceContext reference() throws RecognitionException {
		ReferenceContext _localctx = new ReferenceContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_reference);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			identifier();
			setState(77);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(73);
					match(DOT);
					setState(74);
					identifier();
					}
					} 
				}
				setState(79);
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

	public static class AliasDeclarationContext extends ParserRuleContext {
		public TerminalNode AS() { return getToken(ZenScriptParser.AS, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public AliasDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aliasDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitAliasDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AliasDeclarationContext aliasDeclaration() throws RecognitionException {
		AliasDeclarationContext _localctx = new AliasDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_aliasDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(AS);
			setState(81);
			identifier();
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
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode PAREN_OPEN() { return getToken(ZenScriptParser.PAREN_OPEN, 0); }
		public TerminalNode PAREN_CLOSE() { return getToken(ZenScriptParser.PAREN_CLOSE, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
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
		public TerminalNode AS() { return getToken(ZenScriptParser.AS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_functionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			match(FUNCTION);
			setState(84);
			identifier();
			setState(85);
			match(PAREN_OPEN);
			setState(87);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TO || _la==IDENTIFIER) {
				{
				setState(86);
				formalParameter();
				}
			}

			setState(93);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(89);
				match(COMMA);
				setState(90);
				formalParameter();
				}
				}
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(96);
			match(PAREN_CLOSE);
			setState(99);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(97);
				match(AS);
				setState(98);
				type(0);
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

	public static class FormalParameterContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode AS() { return getToken(ZenScriptParser.AS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public DefaultValueContext defaultValue() {
			return getRuleContext(DefaultValueContext.class,0);
		}
		public FormalParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalParameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitFormalParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalParameterContext formalParameter() throws RecognitionException {
		FormalParameterContext _localctx = new FormalParameterContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_formalParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			identifier();
			setState(106);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(104);
				match(AS);
				setState(105);
				type(0);
				}
			}

			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(108);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitDefaultValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefaultValueContext defaultValue() throws RecognitionException {
		DefaultValueContext _localctx = new DefaultValueContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_defaultValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			match(ASSIGN);
			setState(112);
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

	public static class ZenClassDeclarationContext extends ParserRuleContext {
		public TerminalNode ZEN_CLASS() { return getToken(ZenScriptParser.ZEN_CLASS, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode BRACE_OPEN() { return getToken(ZenScriptParser.BRACE_OPEN, 0); }
		public TerminalNode BRACE_CLOSE() { return getToken(ZenScriptParser.BRACE_CLOSE, 0); }
		public List<VariableDeclStatementContext> variableDeclStatement() {
			return getRuleContexts(VariableDeclStatementContext.class);
		}
		public VariableDeclStatementContext variableDeclStatement(int i) {
			return getRuleContext(VariableDeclStatementContext.class,i);
		}
		public List<ConstructorDeclarationContext> constructorDeclaration() {
			return getRuleContexts(ConstructorDeclarationContext.class);
		}
		public ConstructorDeclarationContext constructorDeclaration(int i) {
			return getRuleContext(ConstructorDeclarationContext.class,i);
		}
		public List<FunctionDeclarationContext> functionDeclaration() {
			return getRuleContexts(FunctionDeclarationContext.class);
		}
		public FunctionDeclarationContext functionDeclaration(int i) {
			return getRuleContext(FunctionDeclarationContext.class,i);
		}
		public ZenClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_zenClassDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitZenClassDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ZenClassDeclarationContext zenClassDeclaration() throws RecognitionException {
		ZenClassDeclarationContext _localctx = new ZenClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_zenClassDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			match(ZEN_CLASS);
			setState(115);
			identifier();
			setState(116);
			match(BRACE_OPEN);
			setState(122);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC) | (1L << FUNCTION) | (1L << ZEN_CONSTRUCTOR))) != 0)) {
				{
				setState(120);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case VAR:
				case VAL:
				case GLOBAL:
				case STATIC:
					{
					setState(117);
					variableDeclStatement();
					}
					break;
				case ZEN_CONSTRUCTOR:
					{
					setState(118);
					constructorDeclaration();
					}
					break;
				case FUNCTION:
					{
					setState(119);
					functionDeclaration();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(124);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(125);
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

	public static class ConstructorDeclarationContext extends ParserRuleContext {
		public TerminalNode ZEN_CONSTRUCTOR() { return getToken(ZenScriptParser.ZEN_CONSTRUCTOR, 0); }
		public TerminalNode PAREN_OPEN() { return getToken(ZenScriptParser.PAREN_OPEN, 0); }
		public TerminalNode PAREN_CLOSE() { return getToken(ZenScriptParser.PAREN_CLOSE, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
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
		public ConstructorDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitConstructorDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorDeclarationContext constructorDeclaration() throws RecognitionException {
		ConstructorDeclarationContext _localctx = new ConstructorDeclarationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_constructorDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			match(ZEN_CONSTRUCTOR);
			setState(128);
			match(PAREN_OPEN);
			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TO || _la==IDENTIFIER) {
				{
				setState(129);
				formalParameter();
				}
			}

			setState(136);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(132);
				match(COMMA);
				setState(133);
				formalParameter();
				}
				}
				setState(138);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(139);
			match(PAREN_CLOSE);
			setState(140);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(142);
			match(BRACE_OPEN);
			setState(146);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC) | (1L << FUNCTION) | (1L << TO) | (1L << THIS) | (1L << IF) | (1L << FOR) | (1L << WHILE) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 84)) & ~0x3f) == 0 && ((1L << (_la - 84)) & ((1L << (DECIMAL_LITERAL - 84)) | (1L << (HEX_LITERAL - 84)) | (1L << (FLOATING_LITERAL - 84)) | (1L << (BOOLEAN_LITERAL - 84)) | (1L << (STRING_LITERAL - 84)) | (1L << (NULL_LITERAL - 84)) | (1L << (IDENTIFIER - 84)))) != 0)) {
				{
				{
				setState(143);
				statement();
				}
				}
				setState(148);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(149);
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

	public static class StatementContext extends ParserRuleContext {
		public BlockStatementContext blockStatement() {
			return getRuleContext(BlockStatementContext.class,0);
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
		public IfElseStatementContext ifElseStatement() {
			return getRuleContext(IfElseStatementContext.class,0);
		}
		public ForeachStatementContext foreachStatement() {
			return getRuleContext(ForeachStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public VariableDeclStatementContext variableDeclStatement() {
			return getRuleContext(VariableDeclStatementContext.class,0);
		}
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_statement);
		try {
			setState(160);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(151);
				blockStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(152);
				returnStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(153);
				breakStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(154);
				continueStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(155);
				ifElseStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(156);
				foreachStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(157);
				whileStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(158);
				variableDeclStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(159);
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

	public static class BlockStatementContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public BlockStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitBlockStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockStatementContext blockStatement() throws RecognitionException {
		BlockStatementContext _localctx = new BlockStatementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_blockStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			match(RETURN);
			setState(166);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << TO) | (1L << THIS) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 84)) & ~0x3f) == 0 && ((1L << (_la - 84)) & ((1L << (DECIMAL_LITERAL - 84)) | (1L << (HEX_LITERAL - 84)) | (1L << (FLOATING_LITERAL - 84)) | (1L << (BOOLEAN_LITERAL - 84)) | (1L << (STRING_LITERAL - 84)) | (1L << (NULL_LITERAL - 84)) | (1L << (IDENTIFIER - 84)))) != 0)) {
				{
				setState(165);
				expression(0);
				}
			}

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

	public static class BreakStatementContext extends ParserRuleContext {
		public TerminalNode BREAK() { return getToken(ZenScriptParser.BREAK, 0); }
		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitBreakStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			match(BREAK);
			setState(171);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitContinueStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContinueStatementContext continueStatement() throws RecognitionException {
		ContinueStatementContext _localctx = new ContinueStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			match(CONTINUE);
			setState(174);
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

	public static class IfElseStatementContext extends ParserRuleContext {
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
		public IfElseStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifElseStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitIfElseStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfElseStatementContext ifElseStatement() throws RecognitionException {
		IfElseStatementContext _localctx = new IfElseStatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_ifElseStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(176);
			match(IF);
			setState(177);
			expression(0);
			setState(180);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(178);
				statement();
				}
				break;
			case 2:
				{
				setState(179);
				block();
				}
				break;
			}
			setState(187);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(182);
				match(ELSE);
				setState(185);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
				case 1:
					{
					setState(183);
					statement();
					}
					break;
				case 2:
					{
					setState(184);
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

	public static class ForeachStatementContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(ZenScriptParser.FOR, 0); }
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public TerminalNode IN() { return getToken(ZenScriptParser.IN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(ZenScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ZenScriptParser.COMMA, i);
		}
		public ForeachStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_foreachStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitForeachStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForeachStatementContext foreachStatement() throws RecognitionException {
		ForeachStatementContext _localctx = new ForeachStatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_foreachStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			match(FOR);
			setState(190);
			identifier();
			setState(195);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(191);
				match(COMMA);
				setState(192);
				identifier();
				}
				}
				setState(197);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(198);
			match(IN);
			setState(199);
			expression(0);
			setState(200);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
			match(WHILE);
			setState(203);
			match(PAREN_OPEN);
			setState(204);
			expression(0);
			setState(205);
			match(PAREN_CLOSE);
			setState(206);
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

	public static class VariableDeclStatementContext extends ParserRuleContext {
		public Token Modifier;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }
		public TerminalNode VAR() { return getToken(ZenScriptParser.VAR, 0); }
		public TerminalNode VAL() { return getToken(ZenScriptParser.VAL, 0); }
		public TerminalNode STATIC() { return getToken(ZenScriptParser.STATIC, 0); }
		public TerminalNode GLOBAL() { return getToken(ZenScriptParser.GLOBAL, 0); }
		public TerminalNode AS() { return getToken(ZenScriptParser.AS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(ZenScriptParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableDeclStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitVariableDeclStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclStatementContext variableDeclStatement() throws RecognitionException {
		VariableDeclStatementContext _localctx = new VariableDeclStatementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_variableDeclStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208);
			((VariableDeclStatementContext)_localctx).Modifier = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC))) != 0)) ) {
				((VariableDeclStatementContext)_localctx).Modifier = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(209);
			identifier();
			setState(212);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(210);
				match(AS);
				setState(211);
				type(0);
				}
			}

			setState(216);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(214);
				match(ASSIGN);
				setState(215);
				expression(0);
				}
			}

			setState(218);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitExpressionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(220);
			expression(0);
			setState(221);
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
	public static class MemberAccessExpressionContext extends ExpressionContext {
		public ExpressionContext Left;
		public IdentifierContext Right;
		public TerminalNode DOT() { return getToken(ZenScriptParser.DOT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public MemberAccessExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitMemberAccessExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MapLiteralExpressionContext extends ExpressionContext {
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
		public MapLiteralExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitMapLiteralExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BracketHandlerExpressionContext extends ExpressionContext {
		public TerminalNode LESS() { return getToken(ZenScriptParser.LESS, 0); }
		public List<TerminalNode> GREATER() { return getTokens(ZenScriptParser.GREATER); }
		public TerminalNode GREATER(int i) {
			return getToken(ZenScriptParser.GREATER, i);
		}
		public List<TerminalNode> COLON() { return getTokens(ZenScriptParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(ZenScriptParser.COLON, i);
		}
		public BracketHandlerExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitBracketHandlerExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TypeCastExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode AS() { return getToken(ZenScriptParser.AS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypeCastExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitTypeCastExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LiteralExpressionContext extends ExpressionContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public LiteralExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitLiteralExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayLiteralExpressionContext extends ExpressionContext {
		public TerminalNode BRACK_OPEN() { return getToken(ZenScriptParser.BRACK_OPEN, 0); }
		public TerminalNode BRACK_CLOSE() { return getToken(ZenScriptParser.BRACK_CLOSE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ZenScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ZenScriptParser.COMMA, i);
		}
		public ArrayLiteralExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitArrayLiteralExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryExpressionContext extends ExpressionContext {
		public Token Operator;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode NOT() { return getToken(ZenScriptParser.NOT, 0); }
		public TerminalNode SUB() { return getToken(ZenScriptParser.SUB, 0); }
		public TerminalNode ADD() { return getToken(ZenScriptParser.ADD, 0); }
		public UnaryExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RangeExpressionContext extends ExpressionContext {
		public ExpressionContext From;
		public Token Operator;
		public ExpressionContext To;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode DOT_DOT() { return getToken(ZenScriptParser.DOT_DOT, 0); }
		public TerminalNode TO() { return getToken(ZenScriptParser.TO, 0); }
		public RangeExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitRangeExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MemberIndexExpressionContext extends ExpressionContext {
		public ExpressionContext Left;
		public ExpressionContext Index;
		public TerminalNode BRACK_OPEN() { return getToken(ZenScriptParser.BRACK_OPEN, 0); }
		public TerminalNode BRACK_CLOSE() { return getToken(ZenScriptParser.BRACK_CLOSE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public MemberIndexExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitMemberIndexExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParensExpressionContext extends ExpressionContext {
		public TerminalNode PAREN_OPEN() { return getToken(ZenScriptParser.PAREN_OPEN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode PAREN_CLOSE() { return getToken(ZenScriptParser.PAREN_CLOSE, 0); }
		public ParensExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitParensExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArgumentsExpressionContext extends ExpressionContext {
		public ExpressionContext Left;
		public TerminalNode PAREN_OPEN() { return getToken(ZenScriptParser.PAREN_OPEN, 0); }
		public TerminalNode PAREN_CLOSE() { return getToken(ZenScriptParser.PAREN_CLOSE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ZenScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ZenScriptParser.COMMA, i);
		}
		public ArgumentsExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitArgumentsExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ThisExpressionContext extends ExpressionContext {
		public TerminalNode THIS() { return getToken(ZenScriptParser.THIS, 0); }
		public ThisExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitThisExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunctionExpressionContext extends ExpressionContext {
		public TerminalNode FUNCTION() { return getToken(ZenScriptParser.FUNCTION, 0); }
		public TerminalNode PAREN_OPEN() { return getToken(ZenScriptParser.PAREN_OPEN, 0); }
		public TerminalNode PAREN_CLOSE() { return getToken(ZenScriptParser.PAREN_CLOSE, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
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
		public TerminalNode AS() { return getToken(ZenScriptParser.AS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public FunctionExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitFunctionExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryExpressionContext extends ExpressionContext {
		public ExpressionContext Left;
		public Token Operator;
		public ExpressionContext Right;
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
		public TerminalNode CAT() { return getToken(ZenScriptParser.CAT, 0); }
		public TerminalNode LESS_EQUAL() { return getToken(ZenScriptParser.LESS_EQUAL, 0); }
		public TerminalNode GREATER_EQUAL() { return getToken(ZenScriptParser.GREATER_EQUAL, 0); }
		public TerminalNode GREATER() { return getToken(ZenScriptParser.GREATER, 0); }
		public TerminalNode LESS() { return getToken(ZenScriptParser.LESS, 0); }
		public TerminalNode EQUAL() { return getToken(ZenScriptParser.EQUAL, 0); }
		public TerminalNode NOT_EQUAL() { return getToken(ZenScriptParser.NOT_EQUAL, 0); }
		public TerminalNode INSTANCEOF() { return getToken(ZenScriptParser.INSTANCEOF, 0); }
		public TerminalNode IN() { return getToken(ZenScriptParser.IN, 0); }
		public TerminalNode HAS() { return getToken(ZenScriptParser.HAS, 0); }
		public TerminalNode AND() { return getToken(ZenScriptParser.AND, 0); }
		public TerminalNode OR() { return getToken(ZenScriptParser.OR, 0); }
		public TerminalNode XOR() { return getToken(ZenScriptParser.XOR, 0); }
		public TerminalNode AND_AND() { return getToken(ZenScriptParser.AND_AND, 0); }
		public TerminalNode OR_OR() { return getToken(ZenScriptParser.OR_OR, 0); }
		public BinaryExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitBinaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignmentExpressionContext extends ExpressionContext {
		public ExpressionContext Left;
		public Token Operator;
		public ExpressionContext Right;
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
		public TerminalNode CAT_ASSIGN() { return getToken(ZenScriptParser.CAT_ASSIGN, 0); }
		public TerminalNode AND_ASSIGN() { return getToken(ZenScriptParser.AND_ASSIGN, 0); }
		public TerminalNode OR_ASSIGN() { return getToken(ZenScriptParser.OR_ASSIGN, 0); }
		public TerminalNode XOR_ASSIGN() { return getToken(ZenScriptParser.XOR_ASSIGN, 0); }
		public AssignmentExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitAssignmentExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VarAccessExpressionContext extends ExpressionContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public VarAccessExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitVarAccessExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TrinaryExpressionContext extends ExpressionContext {
		public ExpressionContext Condition;
		public ExpressionContext Then;
		public ExpressionContext Else;
		public TerminalNode QUEST() { return getToken(ZenScriptParser.QUEST, 0); }
		public TerminalNode COLON() { return getToken(ZenScriptParser.COLON, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TrinaryExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitTrinaryExpression(this);
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
		int _startState = 40;
		enterRecursionRule(_localctx, 40, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(292);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FUNCTION:
				{
				_localctx = new FunctionExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(224);
				match(FUNCTION);
				setState(225);
				match(PAREN_OPEN);
				setState(227);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TO || _la==IDENTIFIER) {
					{
					setState(226);
					formalParameter();
					}
				}

				setState(233);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(229);
					match(COMMA);
					setState(230);
					formalParameter();
					}
					}
					setState(235);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(236);
				match(PAREN_CLOSE);
				setState(239);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(237);
					match(AS);
					setState(238);
					type(0);
					}
				}

				setState(241);
				block();
				}
				break;
			case ADD:
			case SUB:
			case NOT:
				{
				_localctx = new UnaryExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(242);
				((UnaryExpressionContext)_localctx).Operator = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADD) | (1L << SUB) | (1L << NOT))) != 0)) ) {
					((UnaryExpressionContext)_localctx).Operator = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(243);
				expression(16);
				}
				break;
			case LESS:
				{
				_localctx = new BracketHandlerExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(244);
				match(LESS);
				setState(251);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC) | (1L << IMPORT) | (1L << FUNCTION) | (1L << AS) | (1L << TO) | (1L << IN) | (1L << HAS) | (1L << INSTANCEOF) | (1L << THIS) | (1L << ANY) | (1L << BYTE) | (1L << BYTE_OBJ) | (1L << SHORT) | (1L << SHORT_OBJ) | (1L << INT) | (1L << INT_OBJ) | (1L << LONG) | (1L << LONG_OBJ) | (1L << FLOAT) | (1L << FLOAT_OBJ) | (1L << DOUBLE) | (1L << DOUBLE_OBJ) | (1L << BOOL) | (1L << BOOL_OBJ) | (1L << VOID) | (1L << STRING) | (1L << IF) | (1L << ELSE) | (1L << FOR) | (1L << DO) | (1L << WHILE) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << FRIGGIN_CLASS) | (1L << FRIGGIN_CONSTRUCTOR) | (1L << ZEN_CLASS) | (1L << ZEN_CONSTRUCTOR) | (1L << PAREN_OPEN) | (1L << PAREN_CLOSE) | (1L << BRACK_OPEN) | (1L << BRACK_CLOSE) | (1L << BRACE_OPEN) | (1L << BRACE_CLOSE) | (1L << COMMA) | (1L << DOT) | (1L << SEMICOLON) | (1L << ADD) | (1L << SUB) | (1L << MUL) | (1L << DIV) | (1L << MOD) | (1L << CAT) | (1L << NOT) | (1L << LESS) | (1L << XOR) | (1L << COLON) | (1L << QUEST) | (1L << BACKTICK))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (DOLLAR - 64)) | (1L << (AND - 64)) | (1L << (OR - 64)) | (1L << (ASSIGN - 64)) | (1L << (AND_AND - 64)) | (1L << (OR_OR - 64)) | (1L << (EQUAL - 64)) | (1L << (NOT_EQUAL - 64)) | (1L << (LESS_EQUAL - 64)) | (1L << (GREATER_EQUAL - 64)) | (1L << (PLUS_ASSIGN - 64)) | (1L << (MINUS_ASSIGN - 64)) | (1L << (STAR_ASSIGN - 64)) | (1L << (DIV_ASSIGN - 64)) | (1L << (MOD_ASSIGN - 64)) | (1L << (XOR_ASSIGN - 64)) | (1L << (AND_ASSIGN - 64)) | (1L << (OR_ASSIGN - 64)) | (1L << (CAT_ASSIGN - 64)) | (1L << (DOT_DOT - 64)) | (1L << (DECIMAL_LITERAL - 64)) | (1L << (HEX_LITERAL - 64)) | (1L << (FLOATING_LITERAL - 64)) | (1L << (BOOLEAN_LITERAL - 64)) | (1L << (STRING_LITERAL - 64)) | (1L << (NULL_LITERAL - 64)) | (1L << (IDENTIFIER - 64)) | (1L << (WHITE_SPACE - 64)) | (1L << (BLOCK_COMMENT - 64)) | (1L << (LINE_COMMENT - 64)) | (1L << (Preprocessor - 64)))) != 0)) {
					{
					{
					setState(245);
					_la = _input.LA(1);
					if ( _la <= 0 || (_la==GREATER) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(247);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
					case 1:
						{
						setState(246);
						match(COLON);
						}
						break;
					}
					}
					}
					setState(253);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(254);
				match(GREATER);
				}
				break;
			case THIS:
				{
				_localctx = new ThisExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(255);
				match(THIS);
				}
				break;
			case BRACK_OPEN:
				{
				_localctx = new ArrayLiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(256);
				match(BRACK_OPEN);
				setState(258);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << TO) | (1L << THIS) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 84)) & ~0x3f) == 0 && ((1L << (_la - 84)) & ((1L << (DECIMAL_LITERAL - 84)) | (1L << (HEX_LITERAL - 84)) | (1L << (FLOATING_LITERAL - 84)) | (1L << (BOOLEAN_LITERAL - 84)) | (1L << (STRING_LITERAL - 84)) | (1L << (NULL_LITERAL - 84)) | (1L << (IDENTIFIER - 84)))) != 0)) {
					{
					setState(257);
					expression(0);
					}
				}

				setState(264);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(260);
						match(COMMA);
						setState(261);
						expression(0);
						}
						} 
					}
					setState(266);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
				}
				setState(268);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(267);
					match(COMMA);
					}
				}

				setState(270);
				match(BRACK_CLOSE);
				}
				break;
			case BRACE_OPEN:
				{
				_localctx = new MapLiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(271);
				match(BRACE_OPEN);
				setState(273);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << TO) | (1L << THIS) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 84)) & ~0x3f) == 0 && ((1L << (_la - 84)) & ((1L << (DECIMAL_LITERAL - 84)) | (1L << (HEX_LITERAL - 84)) | (1L << (FLOATING_LITERAL - 84)) | (1L << (BOOLEAN_LITERAL - 84)) | (1L << (STRING_LITERAL - 84)) | (1L << (NULL_LITERAL - 84)) | (1L << (IDENTIFIER - 84)))) != 0)) {
					{
					setState(272);
					mapEntry();
					}
				}

				setState(279);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(275);
						match(COMMA);
						setState(276);
						mapEntry();
						}
						} 
					}
					setState(281);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
				}
				setState(283);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(282);
					match(COMMA);
					}
				}

				setState(285);
				match(BRACE_CLOSE);
				}
				break;
			case DECIMAL_LITERAL:
			case HEX_LITERAL:
			case FLOATING_LITERAL:
			case BOOLEAN_LITERAL:
			case STRING_LITERAL:
			case NULL_LITERAL:
				{
				_localctx = new LiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(286);
				literal();
				}
				break;
			case PAREN_OPEN:
				{
				_localctx = new ParensExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(287);
				match(PAREN_OPEN);
				setState(288);
				expression(0);
				setState(289);
				match(PAREN_CLOSE);
				}
				break;
			case TO:
			case IDENTIFIER:
				{
				_localctx = new VarAccessExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(291);
				identifier();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(347);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(345);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((BinaryExpressionContext)_localctx).Left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(294);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(295);
						((BinaryExpressionContext)_localctx).Operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADD) | (1L << SUB) | (1L << MUL) | (1L << DIV) | (1L << MOD) | (1L << CAT))) != 0)) ) {
							((BinaryExpressionContext)_localctx).Operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(296);
						((BinaryExpressionContext)_localctx).Right = expression(16);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((BinaryExpressionContext)_localctx).Left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(297);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(298);
						((BinaryExpressionContext)_localctx).Operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 58)) & ~0x3f) == 0 && ((1L << (_la - 58)) & ((1L << (LESS - 58)) | (1L << (GREATER - 58)) | (1L << (EQUAL - 58)) | (1L << (NOT_EQUAL - 58)) | (1L << (LESS_EQUAL - 58)) | (1L << (GREATER_EQUAL - 58)))) != 0)) ) {
							((BinaryExpressionContext)_localctx).Operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(299);
						((BinaryExpressionContext)_localctx).Right = expression(15);
						}
						break;
					case 3:
						{
						_localctx = new BinaryExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((BinaryExpressionContext)_localctx).Left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(300);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(301);
						((BinaryExpressionContext)_localctx).Operator = match(INSTANCEOF);
						setState(302);
						((BinaryExpressionContext)_localctx).Right = expression(14);
						}
						break;
					case 4:
						{
						_localctx = new BinaryExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((BinaryExpressionContext)_localctx).Left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(303);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(304);
						((BinaryExpressionContext)_localctx).Operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==IN || _la==HAS) ) {
							((BinaryExpressionContext)_localctx).Operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(305);
						((BinaryExpressionContext)_localctx).Right = expression(13);
						}
						break;
					case 5:
						{
						_localctx = new BinaryExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((BinaryExpressionContext)_localctx).Left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(306);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(307);
						((BinaryExpressionContext)_localctx).Operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (XOR - 60)) | (1L << (AND - 60)) | (1L << (OR - 60)) | (1L << (AND_AND - 60)) | (1L << (OR_OR - 60)))) != 0)) ) {
							((BinaryExpressionContext)_localctx).Operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(308);
						((BinaryExpressionContext)_localctx).Right = expression(12);
						}
						break;
					case 6:
						{
						_localctx = new TrinaryExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((TrinaryExpressionContext)_localctx).Condition = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(309);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(310);
						match(QUEST);
						setState(311);
						((TrinaryExpressionContext)_localctx).Then = expression(0);
						setState(312);
						match(COLON);
						setState(313);
						((TrinaryExpressionContext)_localctx).Else = expression(10);
						}
						break;
					case 7:
						{
						_localctx = new AssignmentExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((AssignmentExpressionContext)_localctx).Left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(315);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(316);
						((AssignmentExpressionContext)_localctx).Operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (ASSIGN - 67)) | (1L << (PLUS_ASSIGN - 67)) | (1L << (MINUS_ASSIGN - 67)) | (1L << (STAR_ASSIGN - 67)) | (1L << (DIV_ASSIGN - 67)) | (1L << (MOD_ASSIGN - 67)) | (1L << (XOR_ASSIGN - 67)) | (1L << (AND_ASSIGN - 67)) | (1L << (OR_ASSIGN - 67)) | (1L << (CAT_ASSIGN - 67)))) != 0)) ) {
							((AssignmentExpressionContext)_localctx).Operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(317);
						((AssignmentExpressionContext)_localctx).Right = expression(9);
						}
						break;
					case 8:
						{
						_localctx = new RangeExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((RangeExpressionContext)_localctx).From = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(318);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(319);
						((RangeExpressionContext)_localctx).Operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==TO || _la==DOT_DOT) ) {
							((RangeExpressionContext)_localctx).Operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(320);
						((RangeExpressionContext)_localctx).To = expression(8);
						}
						break;
					case 9:
						{
						_localctx = new ArgumentsExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((ArgumentsExpressionContext)_localctx).Left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(321);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(322);
						match(PAREN_OPEN);
						setState(324);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << TO) | (1L << THIS) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 84)) & ~0x3f) == 0 && ((1L << (_la - 84)) & ((1L << (DECIMAL_LITERAL - 84)) | (1L << (HEX_LITERAL - 84)) | (1L << (FLOATING_LITERAL - 84)) | (1L << (BOOLEAN_LITERAL - 84)) | (1L << (STRING_LITERAL - 84)) | (1L << (NULL_LITERAL - 84)) | (1L << (IDENTIFIER - 84)))) != 0)) {
							{
							setState(323);
							expression(0);
							}
						}

						setState(330);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==COMMA) {
							{
							{
							setState(326);
							match(COMMA);
							setState(327);
							expression(0);
							}
							}
							setState(332);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(333);
						match(PAREN_CLOSE);
						}
						break;
					case 10:
						{
						_localctx = new MemberAccessExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((MemberAccessExpressionContext)_localctx).Left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(334);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(335);
						match(DOT);
						setState(336);
						((MemberAccessExpressionContext)_localctx).Right = identifier();
						}
						break;
					case 11:
						{
						_localctx = new MemberIndexExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((MemberIndexExpressionContext)_localctx).Left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(337);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(338);
						match(BRACK_OPEN);
						setState(339);
						((MemberIndexExpressionContext)_localctx).Index = expression(0);
						setState(340);
						match(BRACK_CLOSE);
						}
						break;
					case 12:
						{
						_localctx = new TypeCastExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(342);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(343);
						match(AS);
						setState(344);
						type(0);
						}
						break;
					}
					} 
				}
				setState(349);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
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

	public static class MapEntryContext extends ParserRuleContext {
		public ExpressionContext Key;
		public ExpressionContext Value;
		public TerminalNode COLON() { return getToken(ZenScriptParser.COLON, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public MapEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapEntry; }
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
			setState(350);
			((MapEntryContext)_localctx).Key = expression(0);
			setState(351);
			match(COLON);
			setState(352);
			((MapEntryContext)_localctx).Value = expression(0);
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

	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	 
		public TypeContext() { }
		public void copyFrom(TypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BuiltinTypeContext extends TypeContext {
		public BuiltinContext builtin() {
			return getRuleContext(BuiltinContext.class,0);
		}
		public BuiltinTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitBuiltinType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayTypeContext extends TypeContext {
		public TypeContext BaseType;
		public TerminalNode BRACK_OPEN() { return getToken(ZenScriptParser.BRACK_OPEN, 0); }
		public TerminalNode BRACK_CLOSE() { return getToken(ZenScriptParser.BRACK_CLOSE, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ArrayTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunctionTypeContext extends TypeContext {
		public TypeContext ResultType;
		public TerminalNode FUNCTION() { return getToken(ZenScriptParser.FUNCTION, 0); }
		public TerminalNode PAREN_OPEN() { return getToken(ZenScriptParser.PAREN_OPEN, 0); }
		public TerminalNode PAREN_CLOSE() { return getToken(ZenScriptParser.PAREN_CLOSE, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ArgumentTypeListContext argumentTypeList() {
			return getRuleContext(ArgumentTypeListContext.class,0);
		}
		public FunctionTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitFunctionType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ListTypeContext extends TypeContext {
		public TypeContext BaseType;
		public TerminalNode BRACK_OPEN() { return getToken(ZenScriptParser.BRACK_OPEN, 0); }
		public TerminalNode BRACK_CLOSE() { return getToken(ZenScriptParser.BRACK_CLOSE, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ListTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitListType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReferenceTypeContext extends TypeContext {
		public ReferenceContext reference() {
			return getRuleContext(ReferenceContext.class,0);
		}
		public ReferenceTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitReferenceType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MapTypeContext extends TypeContext {
		public TypeContext ValueType;
		public TypeContext KeyType;
		public TerminalNode BRACK_OPEN() { return getToken(ZenScriptParser.BRACK_OPEN, 0); }
		public TerminalNode BRACK_CLOSE() { return getToken(ZenScriptParser.BRACK_CLOSE, 0); }
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public MapTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitMapType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		return type(0);
	}

	private TypeContext type(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TypeContext _localctx = new TypeContext(_ctx, _parentState);
		TypeContext _prevctx = _localctx;
		int _startState = 44;
		enterRecursionRule(_localctx, 44, RULE_type, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(368);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BYTE:
			case BYTE_OBJ:
			case SHORT:
			case SHORT_OBJ:
			case INT:
			case INT_OBJ:
			case LONG:
			case LONG_OBJ:
			case FLOAT:
			case FLOAT_OBJ:
			case DOUBLE:
			case DOUBLE_OBJ:
			case BOOL:
			case BOOL_OBJ:
			case VOID:
			case STRING:
				{
				_localctx = new BuiltinTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(355);
				builtin();
				}
				break;
			case TO:
			case IDENTIFIER:
				{
				_localctx = new ReferenceTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(356);
				reference();
				}
				break;
			case FUNCTION:
				{
				_localctx = new FunctionTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(357);
				match(FUNCTION);
				setState(358);
				match(PAREN_OPEN);
				setState(360);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << TO) | (1L << BYTE) | (1L << BYTE_OBJ) | (1L << SHORT) | (1L << SHORT_OBJ) | (1L << INT) | (1L << INT_OBJ) | (1L << LONG) | (1L << LONG_OBJ) | (1L << FLOAT) | (1L << FLOAT_OBJ) | (1L << DOUBLE) | (1L << DOUBLE_OBJ) | (1L << BOOL) | (1L << BOOL_OBJ) | (1L << VOID) | (1L << STRING) | (1L << BRACK_OPEN))) != 0) || _la==IDENTIFIER) {
					{
					setState(359);
					argumentTypeList();
					}
				}

				setState(362);
				match(PAREN_CLOSE);
				setState(363);
				((FunctionTypeContext)_localctx).ResultType = type(4);
				}
				break;
			case BRACK_OPEN:
				{
				_localctx = new ListTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(364);
				match(BRACK_OPEN);
				setState(365);
				((ListTypeContext)_localctx).BaseType = type(0);
				setState(366);
				match(BRACK_CLOSE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(380);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(378);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
					case 1:
						{
						_localctx = new ArrayTypeContext(new TypeContext(_parentctx, _parentState));
						((ArrayTypeContext)_localctx).BaseType = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(370);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(371);
						match(BRACK_OPEN);
						setState(372);
						match(BRACK_CLOSE);
						}
						break;
					case 2:
						{
						_localctx = new MapTypeContext(new TypeContext(_parentctx, _parentState));
						((MapTypeContext)_localctx).ValueType = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(373);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(374);
						match(BRACK_OPEN);
						setState(375);
						((MapTypeContext)_localctx).KeyType = type(0);
						setState(376);
						match(BRACK_CLOSE);
						}
						break;
					}
					} 
				}
				setState(382);
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
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class BuiltinContext extends ParserRuleContext {
		public TerminalNode BOOL() { return getToken(ZenScriptParser.BOOL, 0); }
		public TerminalNode BYTE() { return getToken(ZenScriptParser.BYTE, 0); }
		public TerminalNode SHORT() { return getToken(ZenScriptParser.SHORT, 0); }
		public TerminalNode INT() { return getToken(ZenScriptParser.INT, 0); }
		public TerminalNode LONG() { return getToken(ZenScriptParser.LONG, 0); }
		public TerminalNode FLOAT() { return getToken(ZenScriptParser.FLOAT, 0); }
		public TerminalNode DOUBLE() { return getToken(ZenScriptParser.DOUBLE, 0); }
		public TerminalNode VOID() { return getToken(ZenScriptParser.VOID, 0); }
		public TerminalNode STRING() { return getToken(ZenScriptParser.STRING, 0); }
		public TerminalNode BOOL_OBJ() { return getToken(ZenScriptParser.BOOL_OBJ, 0); }
		public TerminalNode BYTE_OBJ() { return getToken(ZenScriptParser.BYTE_OBJ, 0); }
		public TerminalNode SHORT_OBJ() { return getToken(ZenScriptParser.SHORT_OBJ, 0); }
		public TerminalNode INT_OBJ() { return getToken(ZenScriptParser.INT_OBJ, 0); }
		public TerminalNode LONG_OBJ() { return getToken(ZenScriptParser.LONG_OBJ, 0); }
		public TerminalNode FLOAT_OBJ() { return getToken(ZenScriptParser.FLOAT_OBJ, 0); }
		public TerminalNode DOUBLE_OBJ() { return getToken(ZenScriptParser.DOUBLE_OBJ, 0); }
		public BuiltinContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_builtin; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitBuiltin(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BuiltinContext builtin() throws RecognitionException {
		BuiltinContext _localctx = new BuiltinContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_builtin);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(383);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BYTE) | (1L << BYTE_OBJ) | (1L << SHORT) | (1L << SHORT_OBJ) | (1L << INT) | (1L << INT_OBJ) | (1L << LONG) | (1L << LONG_OBJ) | (1L << FLOAT) | (1L << FLOAT_OBJ) | (1L << DOUBLE) | (1L << DOUBLE_OBJ) | (1L << BOOL) | (1L << BOOL_OBJ) | (1L << VOID) | (1L << STRING))) != 0)) ) {
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

	public static class ArgumentTypeListContext extends ParserRuleContext {
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
		public ArgumentTypeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentTypeList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitArgumentTypeList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentTypeListContext argumentTypeList() throws RecognitionException {
		ArgumentTypeListContext _localctx = new ArgumentTypeListContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_argumentTypeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(385);
			type(0);
			setState(390);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(386);
				match(COMMA);
				setState(387);
				type(0);
				}
				}
				setState(392);
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

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode DECIMAL_LITERAL() { return getToken(ZenScriptParser.DECIMAL_LITERAL, 0); }
		public TerminalNode HEX_LITERAL() { return getToken(ZenScriptParser.HEX_LITERAL, 0); }
		public TerminalNode FLOATING_LITERAL() { return getToken(ZenScriptParser.FLOATING_LITERAL, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(ZenScriptParser.STRING_LITERAL, 0); }
		public TerminalNode BOOLEAN_LITERAL() { return getToken(ZenScriptParser.BOOLEAN_LITERAL, 0); }
		public TerminalNode NULL_LITERAL() { return getToken(ZenScriptParser.NULL_LITERAL, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(393);
			_la = _input.LA(1);
			if ( !(((((_la - 84)) & ~0x3f) == 0 && ((1L << (_la - 84)) & ((1L << (DECIMAL_LITERAL - 84)) | (1L << (HEX_LITERAL - 84)) | (1L << (FLOATING_LITERAL - 84)) | (1L << (BOOLEAN_LITERAL - 84)) | (1L << (STRING_LITERAL - 84)) | (1L << (NULL_LITERAL - 84)))) != 0)) ) {
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

	public static class IdentifierContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ZenScriptParser.IDENTIFIER, 0); }
		public TerminalNode TO() { return getToken(ZenScriptParser.TO, 0); }
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_identifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(395);
			_la = _input.LA(1);
			if ( !(_la==TO || _la==IDENTIFIER) ) {
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 20:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		case 22:
			return type_sempred((TypeContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 15);
		case 1:
			return precpred(_ctx, 14);
		case 2:
			return precpred(_ctx, 13);
		case 3:
			return precpred(_ctx, 12);
		case 4:
			return precpred(_ctx, 11);
		case 5:
			return precpred(_ctx, 10);
		case 6:
			return precpred(_ctx, 9);
		case 7:
			return precpred(_ctx, 7);
		case 8:
			return precpred(_ctx, 20);
		case 9:
			return precpred(_ctx, 19);
		case 10:
			return precpred(_ctx, 18);
		case 11:
			return precpred(_ctx, 17);
		}
		return true;
	}
	private boolean type_sempred(TypeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 12:
			return precpred(_ctx, 2);
		case 13:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001^\u018e\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0005\u0000;\b\u0000\n\u0000\f\u0000>\t\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001"+
		"E\b\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0005\u0002L\b\u0002\n\u0002\f\u0002O\t\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004"+
		"X\b\u0004\u0001\u0004\u0001\u0004\u0005\u0004\\\b\u0004\n\u0004\f\u0004"+
		"_\t\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004d\b\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005k\b"+
		"\u0005\u0001\u0005\u0003\u0005n\b\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0005\u0007y\b\u0007\n\u0007\f\u0007|\t\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0001\b\u0003\b\u0083\b\b\u0001\b\u0001\b\u0005"+
		"\b\u0087\b\b\n\b\f\b\u008a\t\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t"+
		"\u0005\t\u0091\b\t\n\t\f\t\u0094\t\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u00a1\b\n\u0001"+
		"\u000b\u0001\u000b\u0001\f\u0001\f\u0003\f\u00a7\b\f\u0001\f\u0001\f\u0001"+
		"\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u00b5\b\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0003\u000f\u00ba\b\u000f\u0003\u000f\u00bc\b\u000f"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0005\u0010\u00c2\b\u0010"+
		"\n\u0010\f\u0010\u00c5\t\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u00d5"+
		"\b\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u00d9\b\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0003\u0014\u00e4\b\u0014\u0001\u0014\u0001\u0014"+
		"\u0005\u0014\u00e8\b\u0014\n\u0014\f\u0014\u00eb\t\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0003\u0014\u00f0\b\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0003\u0014\u00f8\b\u0014\u0005"+
		"\u0014\u00fa\b\u0014\n\u0014\f\u0014\u00fd\t\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0003\u0014\u0103\b\u0014\u0001\u0014\u0001\u0014"+
		"\u0005\u0014\u0107\b\u0014\n\u0014\f\u0014\u010a\t\u0014\u0001\u0014\u0003"+
		"\u0014\u010d\b\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0003\u0014\u0112"+
		"\b\u0014\u0001\u0014\u0001\u0014\u0005\u0014\u0116\b\u0014\n\u0014\f\u0014"+
		"\u0119\t\u0014\u0001\u0014\u0003\u0014\u011c\b\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0003"+
		"\u0014\u0125\b\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0003\u0014\u0145\b\u0014\u0001\u0014\u0001\u0014\u0005"+
		"\u0014\u0149\b\u0014\n\u0014\f\u0014\u014c\t\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0005\u0014\u015a\b\u0014"+
		"\n\u0014\f\u0014\u015d\t\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0003\u0016\u0169\b\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0003\u0016\u0171\b\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0005\u0016\u017b\b\u0016\n\u0016\f\u0016\u017e\t\u0016\u0001\u0017"+
		"\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0005\u0018\u0185\b\u0018"+
		"\n\u0018\f\u0018\u0188\t\u0018\u0001\u0019\u0001\u0019\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0000\u0002(,\u001b\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.024\u0000\f\u0001"+
		"\u0000\u0001\u0004\u0002\u00003499\u0001\u0000;;\u0001\u000038\u0002\u0000"+
		":;FI\u0001\u0000\t\n\u0003\u0000<<ABDE\u0002\u0000CCJR\u0002\u0000\b\b"+
		"SS\u0001\u0000\u000e\u001d\u0001\u0000TY\u0002\u0000\b\bZZ\u01ba\u0000"+
		"<\u0001\u0000\u0000\u0000\u0002A\u0001\u0000\u0000\u0000\u0004H\u0001"+
		"\u0000\u0000\u0000\u0006P\u0001\u0000\u0000\u0000\bS\u0001\u0000\u0000"+
		"\u0000\ng\u0001\u0000\u0000\u0000\fo\u0001\u0000\u0000\u0000\u000er\u0001"+
		"\u0000\u0000\u0000\u0010\u007f\u0001\u0000\u0000\u0000\u0012\u008e\u0001"+
		"\u0000\u0000\u0000\u0014\u00a0\u0001\u0000\u0000\u0000\u0016\u00a2\u0001"+
		"\u0000\u0000\u0000\u0018\u00a4\u0001\u0000\u0000\u0000\u001a\u00aa\u0001"+
		"\u0000\u0000\u0000\u001c\u00ad\u0001\u0000\u0000\u0000\u001e\u00b0\u0001"+
		"\u0000\u0000\u0000 \u00bd\u0001\u0000\u0000\u0000\"\u00ca\u0001\u0000"+
		"\u0000\u0000$\u00d0\u0001\u0000\u0000\u0000&\u00dc\u0001\u0000\u0000\u0000"+
		"(\u0124\u0001\u0000\u0000\u0000*\u015e\u0001\u0000\u0000\u0000,\u0170"+
		"\u0001\u0000\u0000\u0000.\u017f\u0001\u0000\u0000\u00000\u0181\u0001\u0000"+
		"\u0000\u00002\u0189\u0001\u0000\u0000\u00004\u018b\u0001\u0000\u0000\u0000"+
		"6;\u0003\u0002\u0001\u00007;\u0003\b\u0004\u00008;\u0003\u000e\u0007\u0000"+
		"9;\u0003\u0014\n\u0000:6\u0001\u0000\u0000\u0000:7\u0001\u0000\u0000\u0000"+
		":8\u0001\u0000\u0000\u0000:9\u0001\u0000\u0000\u0000;>\u0001\u0000\u0000"+
		"\u0000<:\u0001\u0000\u0000\u0000<=\u0001\u0000\u0000\u0000=?\u0001\u0000"+
		"\u0000\u0000><\u0001\u0000\u0000\u0000?@\u0005\u0000\u0000\u0001@\u0001"+
		"\u0001\u0000\u0000\u0000AB\u0005\u0005\u0000\u0000BD\u0003\u0004\u0002"+
		"\u0000CE\u0003\u0006\u0003\u0000DC\u0001\u0000\u0000\u0000DE\u0001\u0000"+
		"\u0000\u0000EF\u0001\u0000\u0000\u0000FG\u00052\u0000\u0000G\u0003\u0001"+
		"\u0000\u0000\u0000HM\u00034\u001a\u0000IJ\u00051\u0000\u0000JL\u00034"+
		"\u001a\u0000KI\u0001\u0000\u0000\u0000LO\u0001\u0000\u0000\u0000MK\u0001"+
		"\u0000\u0000\u0000MN\u0001\u0000\u0000\u0000N\u0005\u0001\u0000\u0000"+
		"\u0000OM\u0001\u0000\u0000\u0000PQ\u0005\u0007\u0000\u0000QR\u00034\u001a"+
		"\u0000R\u0007\u0001\u0000\u0000\u0000ST\u0005\u0006\u0000\u0000TU\u0003"+
		"4\u001a\u0000UW\u0005*\u0000\u0000VX\u0003\n\u0005\u0000WV\u0001\u0000"+
		"\u0000\u0000WX\u0001\u0000\u0000\u0000X]\u0001\u0000\u0000\u0000YZ\u0005"+
		"0\u0000\u0000Z\\\u0003\n\u0005\u0000[Y\u0001\u0000\u0000\u0000\\_\u0001"+
		"\u0000\u0000\u0000][\u0001\u0000\u0000\u0000]^\u0001\u0000\u0000\u0000"+
		"^`\u0001\u0000\u0000\u0000_]\u0001\u0000\u0000\u0000`c\u0005+\u0000\u0000"+
		"ab\u0005\u0007\u0000\u0000bd\u0003,\u0016\u0000ca\u0001\u0000\u0000\u0000"+
		"cd\u0001\u0000\u0000\u0000de\u0001\u0000\u0000\u0000ef\u0003\u0012\t\u0000"+
		"f\t\u0001\u0000\u0000\u0000gj\u00034\u001a\u0000hi\u0005\u0007\u0000\u0000"+
		"ik\u0003,\u0016\u0000jh\u0001\u0000\u0000\u0000jk\u0001\u0000\u0000\u0000"+
		"km\u0001\u0000\u0000\u0000ln\u0003\f\u0006\u0000ml\u0001\u0000\u0000\u0000"+
		"mn\u0001\u0000\u0000\u0000n\u000b\u0001\u0000\u0000\u0000op\u0005C\u0000"+
		"\u0000pq\u0003(\u0014\u0000q\r\u0001\u0000\u0000\u0000rs\u0005(\u0000"+
		"\u0000st\u00034\u001a\u0000tz\u0005.\u0000\u0000uy\u0003$\u0012\u0000"+
		"vy\u0003\u0010\b\u0000wy\u0003\b\u0004\u0000xu\u0001\u0000\u0000\u0000"+
		"xv\u0001\u0000\u0000\u0000xw\u0001\u0000\u0000\u0000y|\u0001\u0000\u0000"+
		"\u0000zx\u0001\u0000\u0000\u0000z{\u0001\u0000\u0000\u0000{}\u0001\u0000"+
		"\u0000\u0000|z\u0001\u0000\u0000\u0000}~\u0005/\u0000\u0000~\u000f\u0001"+
		"\u0000\u0000\u0000\u007f\u0080\u0005)\u0000\u0000\u0080\u0082\u0005*\u0000"+
		"\u0000\u0081\u0083\u0003\n\u0005\u0000\u0082\u0081\u0001\u0000\u0000\u0000"+
		"\u0082\u0083\u0001\u0000\u0000\u0000\u0083\u0088\u0001\u0000\u0000\u0000"+
		"\u0084\u0085\u00050\u0000\u0000\u0085\u0087\u0003\n\u0005\u0000\u0086"+
		"\u0084\u0001\u0000\u0000\u0000\u0087\u008a\u0001\u0000\u0000\u0000\u0088"+
		"\u0086\u0001\u0000\u0000\u0000\u0088\u0089\u0001\u0000\u0000\u0000\u0089"+
		"\u008b\u0001\u0000\u0000\u0000\u008a\u0088\u0001\u0000\u0000\u0000\u008b"+
		"\u008c\u0005+\u0000\u0000\u008c\u008d\u0003\u0012\t\u0000\u008d\u0011"+
		"\u0001\u0000\u0000\u0000\u008e\u0092\u0005.\u0000\u0000\u008f\u0091\u0003"+
		"\u0014\n\u0000\u0090\u008f\u0001\u0000\u0000\u0000\u0091\u0094\u0001\u0000"+
		"\u0000\u0000\u0092\u0090\u0001\u0000\u0000\u0000\u0092\u0093\u0001\u0000"+
		"\u0000\u0000\u0093\u0095\u0001\u0000\u0000\u0000\u0094\u0092\u0001\u0000"+
		"\u0000\u0000\u0095\u0096\u0005/\u0000\u0000\u0096\u0013\u0001\u0000\u0000"+
		"\u0000\u0097\u00a1\u0003\u0016\u000b\u0000\u0098\u00a1\u0003\u0018\f\u0000"+
		"\u0099\u00a1\u0003\u001a\r\u0000\u009a\u00a1\u0003\u001c\u000e\u0000\u009b"+
		"\u00a1\u0003\u001e\u000f\u0000\u009c\u00a1\u0003 \u0010\u0000\u009d\u00a1"+
		"\u0003\"\u0011\u0000\u009e\u00a1\u0003$\u0012\u0000\u009f\u00a1\u0003"+
		"&\u0013\u0000\u00a0\u0097\u0001\u0000\u0000\u0000\u00a0\u0098\u0001\u0000"+
		"\u0000\u0000\u00a0\u0099\u0001\u0000\u0000\u0000\u00a0\u009a\u0001\u0000"+
		"\u0000\u0000\u00a0\u009b\u0001\u0000\u0000\u0000\u00a0\u009c\u0001\u0000"+
		"\u0000\u0000\u00a0\u009d\u0001\u0000\u0000\u0000\u00a0\u009e\u0001\u0000"+
		"\u0000\u0000\u00a0\u009f\u0001\u0000\u0000\u0000\u00a1\u0015\u0001\u0000"+
		"\u0000\u0000\u00a2\u00a3\u0003\u0012\t\u0000\u00a3\u0017\u0001\u0000\u0000"+
		"\u0000\u00a4\u00a6\u0005%\u0000\u0000\u00a5\u00a7\u0003(\u0014\u0000\u00a6"+
		"\u00a5\u0001\u0000\u0000\u0000\u00a6\u00a7\u0001\u0000\u0000\u0000\u00a7"+
		"\u00a8\u0001\u0000\u0000\u0000\u00a8\u00a9\u00052\u0000\u0000\u00a9\u0019"+
		"\u0001\u0000\u0000\u0000\u00aa\u00ab\u0005#\u0000\u0000\u00ab\u00ac\u0005"+
		"2\u0000\u0000\u00ac\u001b\u0001\u0000\u0000\u0000\u00ad\u00ae\u0005$\u0000"+
		"\u0000\u00ae\u00af\u00052\u0000\u0000\u00af\u001d\u0001\u0000\u0000\u0000"+
		"\u00b0\u00b1\u0005\u001e\u0000\u0000\u00b1\u00b4\u0003(\u0014\u0000\u00b2"+
		"\u00b5\u0003\u0014\n\u0000\u00b3\u00b5\u0003\u0012\t\u0000\u00b4\u00b2"+
		"\u0001\u0000\u0000\u0000\u00b4\u00b3\u0001\u0000\u0000\u0000\u00b5\u00bb"+
		"\u0001\u0000\u0000\u0000\u00b6\u00b9\u0005\u001f\u0000\u0000\u00b7\u00ba"+
		"\u0003\u0014\n\u0000\u00b8\u00ba\u0003\u0012\t\u0000\u00b9\u00b7\u0001"+
		"\u0000\u0000\u0000\u00b9\u00b8\u0001\u0000\u0000\u0000\u00ba\u00bc\u0001"+
		"\u0000\u0000\u0000\u00bb\u00b6\u0001\u0000\u0000\u0000\u00bb\u00bc\u0001"+
		"\u0000\u0000\u0000\u00bc\u001f\u0001\u0000\u0000\u0000\u00bd\u00be\u0005"+
		" \u0000\u0000\u00be\u00c3\u00034\u001a\u0000\u00bf\u00c0\u00050\u0000"+
		"\u0000\u00c0\u00c2\u00034\u001a\u0000\u00c1\u00bf\u0001\u0000\u0000\u0000"+
		"\u00c2\u00c5\u0001\u0000\u0000\u0000\u00c3\u00c1\u0001\u0000\u0000\u0000"+
		"\u00c3\u00c4\u0001\u0000\u0000\u0000\u00c4\u00c6\u0001\u0000\u0000\u0000"+
		"\u00c5\u00c3\u0001\u0000\u0000\u0000\u00c6\u00c7\u0005\t\u0000\u0000\u00c7"+
		"\u00c8\u0003(\u0014\u0000\u00c8\u00c9\u0003\u0012\t\u0000\u00c9!\u0001"+
		"\u0000\u0000\u0000\u00ca\u00cb\u0005\"\u0000\u0000\u00cb\u00cc\u0005*"+
		"\u0000\u0000\u00cc\u00cd\u0003(\u0014\u0000\u00cd\u00ce\u0005+\u0000\u0000"+
		"\u00ce\u00cf\u0003\u0012\t\u0000\u00cf#\u0001\u0000\u0000\u0000\u00d0"+
		"\u00d1\u0007\u0000\u0000\u0000\u00d1\u00d4\u00034\u001a\u0000\u00d2\u00d3"+
		"\u0005\u0007\u0000\u0000\u00d3\u00d5\u0003,\u0016\u0000\u00d4\u00d2\u0001"+
		"\u0000\u0000\u0000\u00d4\u00d5\u0001\u0000\u0000\u0000\u00d5\u00d8\u0001"+
		"\u0000\u0000\u0000\u00d6\u00d7\u0005C\u0000\u0000\u00d7\u00d9\u0003(\u0014"+
		"\u0000\u00d8\u00d6\u0001\u0000\u0000\u0000\u00d8\u00d9\u0001\u0000\u0000"+
		"\u0000\u00d9\u00da\u0001\u0000\u0000\u0000\u00da\u00db\u00052\u0000\u0000"+
		"\u00db%\u0001\u0000\u0000\u0000\u00dc\u00dd\u0003(\u0014\u0000\u00dd\u00de"+
		"\u00052\u0000\u0000\u00de\'\u0001\u0000\u0000\u0000\u00df\u00e0\u0006"+
		"\u0014\uffff\uffff\u0000\u00e0\u00e1\u0005\u0006\u0000\u0000\u00e1\u00e3"+
		"\u0005*\u0000\u0000\u00e2\u00e4\u0003\n\u0005\u0000\u00e3\u00e2\u0001"+
		"\u0000\u0000\u0000\u00e3\u00e4\u0001\u0000\u0000\u0000\u00e4\u00e9\u0001"+
		"\u0000\u0000\u0000\u00e5\u00e6\u00050\u0000\u0000\u00e6\u00e8\u0003\n"+
		"\u0005\u0000\u00e7\u00e5\u0001\u0000\u0000\u0000\u00e8\u00eb\u0001\u0000"+
		"\u0000\u0000\u00e9\u00e7\u0001\u0000\u0000\u0000\u00e9\u00ea\u0001\u0000"+
		"\u0000\u0000\u00ea\u00ec\u0001\u0000\u0000\u0000\u00eb\u00e9\u0001\u0000"+
		"\u0000\u0000\u00ec\u00ef\u0005+\u0000\u0000\u00ed\u00ee\u0005\u0007\u0000"+
		"\u0000\u00ee\u00f0\u0003,\u0016\u0000\u00ef\u00ed\u0001\u0000\u0000\u0000"+
		"\u00ef\u00f0\u0001\u0000\u0000\u0000\u00f0\u00f1\u0001\u0000\u0000\u0000"+
		"\u00f1\u0125\u0003\u0012\t\u0000\u00f2\u00f3\u0007\u0001\u0000\u0000\u00f3"+
		"\u0125\u0003(\u0014\u0010\u00f4\u00fb\u0005:\u0000\u0000\u00f5\u00f7\b"+
		"\u0002\u0000\u0000\u00f6\u00f8\u0005=\u0000\u0000\u00f7\u00f6\u0001\u0000"+
		"\u0000\u0000\u00f7\u00f8\u0001\u0000\u0000\u0000\u00f8\u00fa\u0001\u0000"+
		"\u0000\u0000\u00f9\u00f5\u0001\u0000\u0000\u0000\u00fa\u00fd\u0001\u0000"+
		"\u0000\u0000\u00fb\u00f9\u0001\u0000\u0000\u0000\u00fb\u00fc\u0001\u0000"+
		"\u0000\u0000\u00fc\u00fe\u0001\u0000\u0000\u0000\u00fd\u00fb\u0001\u0000"+
		"\u0000\u0000\u00fe\u0125\u0005;\u0000\u0000\u00ff\u0125\u0005\f\u0000"+
		"\u0000\u0100\u0102\u0005,\u0000\u0000\u0101\u0103\u0003(\u0014\u0000\u0102"+
		"\u0101\u0001\u0000\u0000\u0000\u0102\u0103\u0001\u0000\u0000\u0000\u0103"+
		"\u0108\u0001\u0000\u0000\u0000\u0104\u0105\u00050\u0000\u0000\u0105\u0107"+
		"\u0003(\u0014\u0000\u0106\u0104\u0001\u0000\u0000\u0000\u0107\u010a\u0001"+
		"\u0000\u0000\u0000\u0108\u0106\u0001\u0000\u0000\u0000\u0108\u0109\u0001"+
		"\u0000\u0000\u0000\u0109\u010c\u0001\u0000\u0000\u0000\u010a\u0108\u0001"+
		"\u0000\u0000\u0000\u010b\u010d\u00050\u0000\u0000\u010c\u010b\u0001\u0000"+
		"\u0000\u0000\u010c\u010d\u0001\u0000\u0000\u0000\u010d\u010e\u0001\u0000"+
		"\u0000\u0000\u010e\u0125\u0005-\u0000\u0000\u010f\u0111\u0005.\u0000\u0000"+
		"\u0110\u0112\u0003*\u0015\u0000\u0111\u0110\u0001\u0000\u0000\u0000\u0111"+
		"\u0112\u0001\u0000\u0000\u0000\u0112\u0117\u0001\u0000\u0000\u0000\u0113"+
		"\u0114\u00050\u0000\u0000\u0114\u0116\u0003*\u0015\u0000\u0115\u0113\u0001"+
		"\u0000\u0000\u0000\u0116\u0119\u0001\u0000\u0000\u0000\u0117\u0115\u0001"+
		"\u0000\u0000\u0000\u0117\u0118\u0001\u0000\u0000\u0000\u0118\u011b\u0001"+
		"\u0000\u0000\u0000\u0119\u0117\u0001\u0000\u0000\u0000\u011a\u011c\u0005"+
		"0\u0000\u0000\u011b\u011a\u0001\u0000\u0000\u0000\u011b\u011c\u0001\u0000"+
		"\u0000\u0000\u011c\u011d\u0001\u0000\u0000\u0000\u011d\u0125\u0005/\u0000"+
		"\u0000\u011e\u0125\u00032\u0019\u0000\u011f\u0120\u0005*\u0000\u0000\u0120"+
		"\u0121\u0003(\u0014\u0000\u0121\u0122\u0005+\u0000\u0000\u0122\u0125\u0001"+
		"\u0000\u0000\u0000\u0123\u0125\u00034\u001a\u0000\u0124\u00df\u0001\u0000"+
		"\u0000\u0000\u0124\u00f2\u0001\u0000\u0000\u0000\u0124\u00f4\u0001\u0000"+
		"\u0000\u0000\u0124\u00ff\u0001\u0000\u0000\u0000\u0124\u0100\u0001\u0000"+
		"\u0000\u0000\u0124\u010f\u0001\u0000\u0000\u0000\u0124\u011e\u0001\u0000"+
		"\u0000\u0000\u0124\u011f\u0001\u0000\u0000\u0000\u0124\u0123\u0001\u0000"+
		"\u0000\u0000\u0125\u015b\u0001\u0000\u0000\u0000\u0126\u0127\n\u000f\u0000"+
		"\u0000\u0127\u0128\u0007\u0003\u0000\u0000\u0128\u015a\u0003(\u0014\u0010"+
		"\u0129\u012a\n\u000e\u0000\u0000\u012a\u012b\u0007\u0004\u0000\u0000\u012b"+
		"\u015a\u0003(\u0014\u000f\u012c\u012d\n\r\u0000\u0000\u012d\u012e\u0005"+
		"\u000b\u0000\u0000\u012e\u015a\u0003(\u0014\u000e\u012f\u0130\n\f\u0000"+
		"\u0000\u0130\u0131\u0007\u0005\u0000\u0000\u0131\u015a\u0003(\u0014\r"+
		"\u0132\u0133\n\u000b\u0000\u0000\u0133\u0134\u0007\u0006\u0000\u0000\u0134"+
		"\u015a\u0003(\u0014\f\u0135\u0136\n\n\u0000\u0000\u0136\u0137\u0005>\u0000"+
		"\u0000\u0137\u0138\u0003(\u0014\u0000\u0138\u0139\u0005=\u0000\u0000\u0139"+
		"\u013a\u0003(\u0014\n\u013a\u015a\u0001\u0000\u0000\u0000\u013b\u013c"+
		"\n\t\u0000\u0000\u013c\u013d\u0007\u0007\u0000\u0000\u013d\u015a\u0003"+
		"(\u0014\t\u013e\u013f\n\u0007\u0000\u0000\u013f\u0140\u0007\b\u0000\u0000"+
		"\u0140\u015a\u0003(\u0014\b\u0141\u0142\n\u0014\u0000\u0000\u0142\u0144"+
		"\u0005*\u0000\u0000\u0143\u0145\u0003(\u0014\u0000\u0144\u0143\u0001\u0000"+
		"\u0000\u0000\u0144\u0145\u0001\u0000\u0000\u0000\u0145\u014a\u0001\u0000"+
		"\u0000\u0000\u0146\u0147\u00050\u0000\u0000\u0147\u0149\u0003(\u0014\u0000"+
		"\u0148\u0146\u0001\u0000\u0000\u0000\u0149\u014c\u0001\u0000\u0000\u0000"+
		"\u014a\u0148\u0001\u0000\u0000\u0000\u014a\u014b\u0001\u0000\u0000\u0000"+
		"\u014b\u014d\u0001\u0000\u0000\u0000\u014c\u014a\u0001\u0000\u0000\u0000"+
		"\u014d\u015a\u0005+\u0000\u0000\u014e\u014f\n\u0013\u0000\u0000\u014f"+
		"\u0150\u00051\u0000\u0000\u0150\u015a\u00034\u001a\u0000\u0151\u0152\n"+
		"\u0012\u0000\u0000\u0152\u0153\u0005,\u0000\u0000\u0153\u0154\u0003(\u0014"+
		"\u0000\u0154\u0155\u0005-\u0000\u0000\u0155\u015a\u0001\u0000\u0000\u0000"+
		"\u0156\u0157\n\u0011\u0000\u0000\u0157\u0158\u0005\u0007\u0000\u0000\u0158"+
		"\u015a\u0003,\u0016\u0000\u0159\u0126\u0001\u0000\u0000\u0000\u0159\u0129"+
		"\u0001\u0000\u0000\u0000\u0159\u012c\u0001\u0000\u0000\u0000\u0159\u012f"+
		"\u0001\u0000\u0000\u0000\u0159\u0132\u0001\u0000\u0000\u0000\u0159\u0135"+
		"\u0001\u0000\u0000\u0000\u0159\u013b\u0001\u0000\u0000\u0000\u0159\u013e"+
		"\u0001\u0000\u0000\u0000\u0159\u0141\u0001\u0000\u0000\u0000\u0159\u014e"+
		"\u0001\u0000\u0000\u0000\u0159\u0151\u0001\u0000\u0000\u0000\u0159\u0156"+
		"\u0001\u0000\u0000\u0000\u015a\u015d\u0001\u0000\u0000\u0000\u015b\u0159"+
		"\u0001\u0000\u0000\u0000\u015b\u015c\u0001\u0000\u0000\u0000\u015c)\u0001"+
		"\u0000\u0000\u0000\u015d\u015b\u0001\u0000\u0000\u0000\u015e\u015f\u0003"+
		"(\u0014\u0000\u015f\u0160\u0005=\u0000\u0000\u0160\u0161\u0003(\u0014"+
		"\u0000\u0161+\u0001\u0000\u0000\u0000\u0162\u0163\u0006\u0016\uffff\uffff"+
		"\u0000\u0163\u0171\u0003.\u0017\u0000\u0164\u0171\u0003\u0004\u0002\u0000"+
		"\u0165\u0166\u0005\u0006\u0000\u0000\u0166\u0168\u0005*\u0000\u0000\u0167"+
		"\u0169\u00030\u0018\u0000\u0168\u0167\u0001\u0000\u0000\u0000\u0168\u0169"+
		"\u0001\u0000\u0000\u0000\u0169\u016a\u0001\u0000\u0000\u0000\u016a\u016b"+
		"\u0005+\u0000\u0000\u016b\u0171\u0003,\u0016\u0004\u016c\u016d\u0005,"+
		"\u0000\u0000\u016d\u016e\u0003,\u0016\u0000\u016e\u016f\u0005-\u0000\u0000"+
		"\u016f\u0171\u0001\u0000\u0000\u0000\u0170\u0162\u0001\u0000\u0000\u0000"+
		"\u0170\u0164\u0001\u0000\u0000\u0000\u0170\u0165\u0001\u0000\u0000\u0000"+
		"\u0170\u016c\u0001\u0000\u0000\u0000\u0171\u017c\u0001\u0000\u0000\u0000"+
		"\u0172\u0173\n\u0002\u0000\u0000\u0173\u0174\u0005,\u0000\u0000\u0174"+
		"\u017b\u0005-\u0000\u0000\u0175\u0176\n\u0001\u0000\u0000\u0176\u0177"+
		"\u0005,\u0000\u0000\u0177\u0178\u0003,\u0016\u0000\u0178\u0179\u0005-"+
		"\u0000\u0000\u0179\u017b\u0001\u0000\u0000\u0000\u017a\u0172\u0001\u0000"+
		"\u0000\u0000\u017a\u0175\u0001\u0000\u0000\u0000\u017b\u017e\u0001\u0000"+
		"\u0000\u0000\u017c\u017a\u0001\u0000\u0000\u0000\u017c\u017d\u0001\u0000"+
		"\u0000\u0000\u017d-\u0001\u0000\u0000\u0000\u017e\u017c\u0001\u0000\u0000"+
		"\u0000\u017f\u0180\u0007\t\u0000\u0000\u0180/\u0001\u0000\u0000\u0000"+
		"\u0181\u0186\u0003,\u0016\u0000\u0182\u0183\u00050\u0000\u0000\u0183\u0185"+
		"\u0003,\u0016\u0000\u0184\u0182\u0001\u0000\u0000\u0000\u0185\u0188\u0001"+
		"\u0000\u0000\u0000\u0186\u0184\u0001\u0000\u0000\u0000\u0186\u0187\u0001"+
		"\u0000\u0000\u0000\u01871\u0001\u0000\u0000\u0000\u0188\u0186\u0001\u0000"+
		"\u0000\u0000\u0189\u018a\u0007\n\u0000\u0000\u018a3\u0001\u0000\u0000"+
		"\u0000\u018b\u018c\u0007\u000b\u0000\u0000\u018c5\u0001\u0000\u0000\u0000"+
		"+:<DMW]cjmxz\u0082\u0088\u0092\u00a0\u00a6\u00b4\u00b9\u00bb\u00c3\u00d4"+
		"\u00d8\u00e3\u00e9\u00ef\u00f7\u00fb\u0102\u0108\u010c\u0111\u0117\u011b"+
		"\u0124\u0144\u014a\u0159\u015b\u0168\u0170\u017a\u017c\u0186";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}