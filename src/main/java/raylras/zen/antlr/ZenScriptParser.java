// Generated from D:/Projects/Forge/1.12.2/ZenServer/src/main/antlr\ZenScriptParser.g4 by ANTLR 4.10.1
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
		FRIGGIN_CONSTRUCTOR=39, ZEN_CLASS=40, ZEN_CONSTRUCTOR=41, SCRIPT=42, PAREN_OPEN=43, 
		PAREN_CLOSE=44, BRACK_OPEN=45, BRACK_CLOSE=46, BRACE_OPEN=47, BRACE_CLOSE=48, 
		COMMA=49, DOT=50, SEMICOLON=51, ADD=52, SUB=53, MUL=54, DIV=55, MOD=56, 
		CAT=57, NOT=58, LESS=59, GREATER=60, XOR=61, COLON=62, QUEST=63, BACKTICK=64, 
		DOLLAR=65, AND=66, OR=67, ASSIGN=68, AND_AND=69, OR_OR=70, EQUAL=71, NOT_EQUAL=72, 
		LESS_EQUAL=73, GREATER_EQUAL=74, PLUS_ASSIGN=75, MINUS_ASSIGN=76, STAR_ASSIGN=77, 
		DIV_ASSIGN=78, MOD_ASSIGN=79, XOR_ASSIGN=80, AND_ASSIGN=81, OR_ASSIGN=82, 
		CAT_ASSIGN=83, DOT_DOT=84, DECIMAL_LITERAL=85, HEX_LITERAL=86, FLOATING_LITERAL=87, 
		BOOLEAN_LITERAL=88, STRING_LITERAL=89, NULL_LITERAL=90, IDENTIFIER=91, 
		WHITE_SPACE=92, BLOCK_COMMENT=93, LINE_COMMENT=94, Preprocessor=95;
	public static final int
		RULE_scriptUnit = 0, RULE_importDeclaration = 1, RULE_className = 2, RULE_crossScriptReference = 3, 
		RULE_alias = 4, RULE_functionDeclaration = 5, RULE_formalParameter = 6, 
		RULE_defaultValue = 7, RULE_zenClassDeclaration = 8, RULE_constructorDeclaration = 9, 
		RULE_fieldDeclaration = 10, RULE_block = 11, RULE_statement = 12, RULE_blockStatement = 13, 
		RULE_returnStatement = 14, RULE_breakStatement = 15, RULE_continueStatement = 16, 
		RULE_ifStatement = 17, RULE_foreachStatement = 18, RULE_whileStatement = 19, 
		RULE_variableDeclarationStatement = 20, RULE_expressionStatement = 21, 
		RULE_expression = 22, RULE_mapEntry = 23, RULE_type = 24, RULE_builtin = 25, 
		RULE_argumentTypeList = 26, RULE_literal = 27, RULE_identifier = 28;
	private static String[] makeRuleNames() {
		return new String[] {
			"scriptUnit", "importDeclaration", "className", "crossScriptReference", 
			"alias", "functionDeclaration", "formalParameter", "defaultValue", "zenClassDeclaration", 
			"constructorDeclaration", "fieldDeclaration", "block", "statement", "blockStatement", 
			"returnStatement", "breakStatement", "continueStatement", "ifStatement", 
			"foreachStatement", "whileStatement", "variableDeclarationStatement", 
			"expressionStatement", "expression", "mapEntry", "type", "builtin", "argumentTypeList", 
			"literal", "identifier"
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
			"'zenConstructor'", "'script'", "'('", "')'", "'['", "']'", "'{'", "'}'", 
			"','", "'.'", "';'", "'+'", "'-'", "'*'", "'/'", "'%'", "'~'", "'!'", 
			"'<'", "'>'", "'^'", "':'", "'?'", "'`'", "'$'", "'&'", "'|'", "'='", 
			"'&&'", "'||'", "'=='", "'!='", "'<='", "'>='", "'+='", "'-='", "'*='", 
			"'/='", "'%='", "'^='", "'&='", "'|='", "'~='", "'..'", null, null, null, 
			null, null, "'null'"
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
			"FRIGGIN_CONSTRUCTOR", "ZEN_CLASS", "ZEN_CONSTRUCTOR", "SCRIPT", "PAREN_OPEN", 
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
			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC) | (1L << IMPORT) | (1L << FUNCTION) | (1L << TO) | (1L << THIS) | (1L << IF) | (1L << FOR) | (1L << WHILE) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << ZEN_CLASS) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 85)) & ~0x3f) == 0 && ((1L << (_la - 85)) & ((1L << (DECIMAL_LITERAL - 85)) | (1L << (HEX_LITERAL - 85)) | (1L << (FLOATING_LITERAL - 85)) | (1L << (BOOLEAN_LITERAL - 85)) | (1L << (STRING_LITERAL - 85)) | (1L << (NULL_LITERAL - 85)) | (1L << (IDENTIFIER - 85)))) != 0)) {
				{
				setState(62);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(58);
					importDeclaration();
					}
					break;
				case 2:
					{
					setState(59);
					functionDeclaration();
					}
					break;
				case 3:
					{
					setState(60);
					zenClassDeclaration();
					}
					break;
				case 4:
					{
					setState(61);
					statement();
					}
					break;
				}
				}
				setState(66);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(67);
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
		public ClassNameContext className() {
			return getRuleContext(ClassNameContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }
		public TerminalNode AS() { return getToken(ZenScriptParser.AS, 0); }
		public AliasContext alias() {
			return getRuleContext(AliasContext.class,0);
		}
		public CrossScriptReferenceContext crossScriptReference() {
			return getRuleContext(CrossScriptReferenceContext.class,0);
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
			setState(81);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(69);
				match(IMPORT);
				setState(70);
				className();
				setState(73);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(71);
					match(AS);
					setState(72);
					alias();
					}
				}

				setState(75);
				match(SEMICOLON);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(77);
				match(IMPORT);
				setState(78);
				crossScriptReference();
				setState(79);
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

	public static class ClassNameContext extends ParserRuleContext {
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
		public ClassNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_className; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitClassName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassNameContext className() throws RecognitionException {
		ClassNameContext _localctx = new ClassNameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_className);
		try {
			int _alt;
			setState(95);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(83);
				identifier();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(84);
				identifier();
				setState(89);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(85);
						match(DOT);
						setState(86);
						identifier();
						}
						} 
					}
					setState(91);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
				}
				}
				setState(92);
				match(DOT);
				setState(93);
				identifier();
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

	public static class CrossScriptReferenceContext extends ParserRuleContext {
		public TerminalNode SCRIPT() { return getToken(ZenScriptParser.SCRIPT, 0); }
		public List<TerminalNode> DOT() { return getTokens(ZenScriptParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(ZenScriptParser.DOT, i);
		}
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public CrossScriptReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_crossScriptReference; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitCrossScriptReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CrossScriptReferenceContext crossScriptReference() throws RecognitionException {
		CrossScriptReferenceContext _localctx = new CrossScriptReferenceContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_crossScriptReference);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			match(SCRIPT);
			setState(98);
			match(DOT);
			setState(99);
			identifier();
			setState(104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(100);
				match(DOT);
				setState(101);
				identifier();
				}
				}
				setState(106);
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

	public static class AliasContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public AliasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alias; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitAlias(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AliasContext alias() throws RecognitionException {
		AliasContext _localctx = new AliasContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_alias);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
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
		enterRule(_localctx, 10, RULE_functionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			match(FUNCTION);
			setState(110);
			identifier();
			setState(111);
			match(PAREN_OPEN);
			setState(113);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TO || _la==IDENTIFIER) {
				{
				setState(112);
				formalParameter();
				}
			}

			setState(119);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(115);
				match(COMMA);
				setState(116);
				formalParameter();
				}
				}
				setState(121);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(122);
			match(PAREN_CLOSE);
			setState(125);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(123);
				match(AS);
				setState(124);
				type(0);
				}
			}

			setState(127);
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
		enterRule(_localctx, 12, RULE_formalParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			identifier();
			setState(132);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(130);
				match(AS);
				setState(131);
				type(0);
				}
			}

			setState(135);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(134);
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
		enterRule(_localctx, 14, RULE_defaultValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			match(ASSIGN);
			setState(138);
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
		public List<FieldDeclarationContext> fieldDeclaration() {
			return getRuleContexts(FieldDeclarationContext.class);
		}
		public FieldDeclarationContext fieldDeclaration(int i) {
			return getRuleContext(FieldDeclarationContext.class,i);
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
		enterRule(_localctx, 16, RULE_zenClassDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			match(ZEN_CLASS);
			setState(141);
			identifier();
			setState(142);
			match(BRACE_OPEN);
			setState(148);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << FUNCTION) | (1L << ZEN_CONSTRUCTOR))) != 0)) {
				{
				setState(146);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case VAR:
				case VAL:
					{
					setState(143);
					fieldDeclaration();
					}
					break;
				case ZEN_CONSTRUCTOR:
					{
					setState(144);
					constructorDeclaration();
					}
					break;
				case FUNCTION:
					{
					setState(145);
					functionDeclaration();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(151);
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
		enterRule(_localctx, 18, RULE_constructorDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			match(ZEN_CONSTRUCTOR);
			setState(154);
			match(PAREN_OPEN);
			setState(156);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TO || _la==IDENTIFIER) {
				{
				setState(155);
				formalParameter();
				}
			}

			setState(162);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(158);
				match(COMMA);
				setState(159);
				formalParameter();
				}
				}
				setState(164);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(165);
			match(PAREN_CLOSE);
			setState(166);
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

	public static class FieldDeclarationContext extends ParserRuleContext {
		public Token Modifier;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ZenScriptParser.SEMICOLON, 0); }
		public TerminalNode VAR() { return getToken(ZenScriptParser.VAR, 0); }
		public TerminalNode VAL() { return getToken(ZenScriptParser.VAL, 0); }
		public TerminalNode AS() { return getToken(ZenScriptParser.AS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(ZenScriptParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FieldDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitFieldDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldDeclarationContext fieldDeclaration() throws RecognitionException {
		FieldDeclarationContext _localctx = new FieldDeclarationContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_fieldDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			((FieldDeclarationContext)_localctx).Modifier = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==VAR || _la==VAL) ) {
				((FieldDeclarationContext)_localctx).Modifier = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(169);
			identifier();
			{
			setState(170);
			match(AS);
			setState(171);
			type(0);
			}
			setState(175);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(173);
				match(ASSIGN);
				setState(174);
				expression(0);
				}
			}

			setState(177);
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
		enterRule(_localctx, 22, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			match(BRACE_OPEN);
			setState(183);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC) | (1L << FUNCTION) | (1L << TO) | (1L << THIS) | (1L << IF) | (1L << FOR) | (1L << WHILE) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 85)) & ~0x3f) == 0 && ((1L << (_la - 85)) & ((1L << (DECIMAL_LITERAL - 85)) | (1L << (HEX_LITERAL - 85)) | (1L << (FLOATING_LITERAL - 85)) | (1L << (BOOLEAN_LITERAL - 85)) | (1L << (STRING_LITERAL - 85)) | (1L << (NULL_LITERAL - 85)) | (1L << (IDENTIFIER - 85)))) != 0)) {
				{
				{
				setState(180);
				statement();
				}
				}
				setState(185);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(186);
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
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public ForeachStatementContext foreachStatement() {
			return getRuleContext(ForeachStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public VariableDeclarationStatementContext variableDeclarationStatement() {
			return getRuleContext(VariableDeclarationStatementContext.class,0);
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
		enterRule(_localctx, 24, RULE_statement);
		try {
			setState(197);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(188);
				blockStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(189);
				returnStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(190);
				breakStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(191);
				continueStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(192);
				ifStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(193);
				foreachStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(194);
				whileStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(195);
				variableDeclarationStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(196);
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
		enterRule(_localctx, 26, RULE_blockStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
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
		enterRule(_localctx, 28, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			match(RETURN);
			setState(203);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << TO) | (1L << THIS) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 85)) & ~0x3f) == 0 && ((1L << (_la - 85)) & ((1L << (DECIMAL_LITERAL - 85)) | (1L << (HEX_LITERAL - 85)) | (1L << (FLOATING_LITERAL - 85)) | (1L << (BOOLEAN_LITERAL - 85)) | (1L << (STRING_LITERAL - 85)) | (1L << (NULL_LITERAL - 85)) | (1L << (IDENTIFIER - 85)))) != 0)) {
				{
				setState(202);
				expression(0);
				}
			}

			setState(205);
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
		enterRule(_localctx, 30, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			match(BREAK);
			setState(208);
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
		enterRule(_localctx, 32, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			match(CONTINUE);
			setState(211);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			match(IF);
			setState(214);
			expression(0);
			setState(217);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				{
				setState(215);
				statement();
				}
				break;
			case 2:
				{
				setState(216);
				block();
				}
				break;
			}
			setState(224);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(219);
				match(ELSE);
				setState(222);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
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
		enterRule(_localctx, 36, RULE_foreachStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(226);
			match(FOR);
			setState(227);
			identifier();
			setState(232);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(228);
				match(COMMA);
				setState(229);
				identifier();
				}
				}
				setState(234);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(235);
			match(IN);
			setState(236);
			expression(0);
			setState(237);
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
		enterRule(_localctx, 38, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			match(WHILE);
			setState(240);
			match(PAREN_OPEN);
			setState(241);
			expression(0);
			setState(242);
			match(PAREN_CLOSE);
			setState(243);
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

	public static class VariableDeclarationStatementContext extends ParserRuleContext {
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
		public VariableDeclarationStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclarationStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitVariableDeclarationStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationStatementContext variableDeclarationStatement() throws RecognitionException {
		VariableDeclarationStatementContext _localctx = new VariableDeclarationStatementContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_variableDeclarationStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(245);
			((VariableDeclarationStatementContext)_localctx).Modifier = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC))) != 0)) ) {
				((VariableDeclarationStatementContext)_localctx).Modifier = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(246);
			identifier();
			setState(249);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(247);
				match(AS);
				setState(248);
				type(0);
				}
			}

			setState(253);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(251);
				match(ASSIGN);
				setState(252);
				expression(0);
				}
			}

			setState(255);
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
		enterRule(_localctx, 42, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(257);
			expression(0);
			setState(258);
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
		public ExpressionContext Right;
		public TerminalNode DOT() { return getToken(ZenScriptParser.DOT, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
	public static class IdentifierExpressionContext extends ExpressionContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public IdentifierExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ZenScriptParserVisitor ) return ((ZenScriptParserVisitor<? extends T>)visitor).visitIdentifierExpression(this);
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
		int _startState = 44;
		enterRecursionRule(_localctx, 44, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(329);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FUNCTION:
				{
				_localctx = new FunctionExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(261);
				match(FUNCTION);
				setState(262);
				match(PAREN_OPEN);
				setState(264);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TO || _la==IDENTIFIER) {
					{
					setState(263);
					formalParameter();
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
					formalParameter();
					}
					}
					setState(272);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(273);
				match(PAREN_CLOSE);
				setState(276);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(274);
					match(AS);
					setState(275);
					type(0);
					}
				}

				setState(278);
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
				setState(279);
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
				setState(280);
				expression(17);
				}
				break;
			case LESS:
				{
				_localctx = new BracketHandlerExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(281);
				match(LESS);
				setState(288);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VAR) | (1L << VAL) | (1L << GLOBAL) | (1L << STATIC) | (1L << IMPORT) | (1L << FUNCTION) | (1L << AS) | (1L << TO) | (1L << IN) | (1L << HAS) | (1L << INSTANCEOF) | (1L << THIS) | (1L << ANY) | (1L << BYTE) | (1L << BYTE_OBJ) | (1L << SHORT) | (1L << SHORT_OBJ) | (1L << INT) | (1L << INT_OBJ) | (1L << LONG) | (1L << LONG_OBJ) | (1L << FLOAT) | (1L << FLOAT_OBJ) | (1L << DOUBLE) | (1L << DOUBLE_OBJ) | (1L << BOOL) | (1L << BOOL_OBJ) | (1L << VOID) | (1L << STRING) | (1L << IF) | (1L << ELSE) | (1L << FOR) | (1L << DO) | (1L << WHILE) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << FRIGGIN_CLASS) | (1L << FRIGGIN_CONSTRUCTOR) | (1L << ZEN_CLASS) | (1L << ZEN_CONSTRUCTOR) | (1L << SCRIPT) | (1L << PAREN_OPEN) | (1L << PAREN_CLOSE) | (1L << BRACK_OPEN) | (1L << BRACK_CLOSE) | (1L << BRACE_OPEN) | (1L << BRACE_CLOSE) | (1L << COMMA) | (1L << DOT) | (1L << SEMICOLON) | (1L << ADD) | (1L << SUB) | (1L << MUL) | (1L << DIV) | (1L << MOD) | (1L << CAT) | (1L << NOT) | (1L << LESS) | (1L << XOR) | (1L << COLON) | (1L << QUEST))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BACKTICK - 64)) | (1L << (DOLLAR - 64)) | (1L << (AND - 64)) | (1L << (OR - 64)) | (1L << (ASSIGN - 64)) | (1L << (AND_AND - 64)) | (1L << (OR_OR - 64)) | (1L << (EQUAL - 64)) | (1L << (NOT_EQUAL - 64)) | (1L << (LESS_EQUAL - 64)) | (1L << (GREATER_EQUAL - 64)) | (1L << (PLUS_ASSIGN - 64)) | (1L << (MINUS_ASSIGN - 64)) | (1L << (STAR_ASSIGN - 64)) | (1L << (DIV_ASSIGN - 64)) | (1L << (MOD_ASSIGN - 64)) | (1L << (XOR_ASSIGN - 64)) | (1L << (AND_ASSIGN - 64)) | (1L << (OR_ASSIGN - 64)) | (1L << (CAT_ASSIGN - 64)) | (1L << (DOT_DOT - 64)) | (1L << (DECIMAL_LITERAL - 64)) | (1L << (HEX_LITERAL - 64)) | (1L << (FLOATING_LITERAL - 64)) | (1L << (BOOLEAN_LITERAL - 64)) | (1L << (STRING_LITERAL - 64)) | (1L << (NULL_LITERAL - 64)) | (1L << (IDENTIFIER - 64)) | (1L << (WHITE_SPACE - 64)) | (1L << (BLOCK_COMMENT - 64)) | (1L << (LINE_COMMENT - 64)) | (1L << (Preprocessor - 64)))) != 0)) {
					{
					{
					setState(282);
					_la = _input.LA(1);
					if ( _la <= 0 || (_la==GREATER) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(284);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
					case 1:
						{
						setState(283);
						match(COLON);
						}
						break;
					}
					}
					}
					setState(290);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(291);
				match(GREATER);
				}
				break;
			case THIS:
				{
				_localctx = new ThisExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(292);
				match(THIS);
				}
				break;
			case BRACK_OPEN:
				{
				_localctx = new ArrayLiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(293);
				match(BRACK_OPEN);
				setState(295);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << TO) | (1L << THIS) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 85)) & ~0x3f) == 0 && ((1L << (_la - 85)) & ((1L << (DECIMAL_LITERAL - 85)) | (1L << (HEX_LITERAL - 85)) | (1L << (FLOATING_LITERAL - 85)) | (1L << (BOOLEAN_LITERAL - 85)) | (1L << (STRING_LITERAL - 85)) | (1L << (NULL_LITERAL - 85)) | (1L << (IDENTIFIER - 85)))) != 0)) {
					{
					setState(294);
					expression(0);
					}
				}

				setState(301);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(297);
						match(COMMA);
						setState(298);
						expression(0);
						}
						} 
					}
					setState(303);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				}
				setState(305);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(304);
					match(COMMA);
					}
				}

				setState(307);
				match(BRACK_CLOSE);
				}
				break;
			case BRACE_OPEN:
				{
				_localctx = new MapLiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(308);
				match(BRACE_OPEN);
				setState(310);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << TO) | (1L << THIS) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 85)) & ~0x3f) == 0 && ((1L << (_la - 85)) & ((1L << (DECIMAL_LITERAL - 85)) | (1L << (HEX_LITERAL - 85)) | (1L << (FLOATING_LITERAL - 85)) | (1L << (BOOLEAN_LITERAL - 85)) | (1L << (STRING_LITERAL - 85)) | (1L << (NULL_LITERAL - 85)) | (1L << (IDENTIFIER - 85)))) != 0)) {
					{
					setState(309);
					mapEntry();
					}
				}

				setState(316);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(312);
						match(COMMA);
						setState(313);
						mapEntry();
						}
						} 
					}
					setState(318);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
				}
				setState(320);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(319);
					match(COMMA);
					}
				}

				setState(322);
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
				setState(323);
				literal();
				}
				break;
			case PAREN_OPEN:
				{
				_localctx = new ParensExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(324);
				match(PAREN_OPEN);
				setState(325);
				expression(0);
				setState(326);
				match(PAREN_CLOSE);
				}
				break;
			case TO:
			case IDENTIFIER:
				{
				_localctx = new IdentifierExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(328);
				identifier();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(384);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(382);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
					case 1:
						{
						_localctx = new MemberAccessExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((MemberAccessExpressionContext)_localctx).Left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(331);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(332);
						match(DOT);
						setState(333);
						((MemberAccessExpressionContext)_localctx).Right = expression(20);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((BinaryExpressionContext)_localctx).Left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(334);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(335);
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
						setState(336);
						((BinaryExpressionContext)_localctx).Right = expression(17);
						}
						break;
					case 3:
						{
						_localctx = new BinaryExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((BinaryExpressionContext)_localctx).Left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(337);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(338);
						((BinaryExpressionContext)_localctx).Operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 59)) & ~0x3f) == 0 && ((1L << (_la - 59)) & ((1L << (LESS - 59)) | (1L << (GREATER - 59)) | (1L << (EQUAL - 59)) | (1L << (NOT_EQUAL - 59)) | (1L << (LESS_EQUAL - 59)) | (1L << (GREATER_EQUAL - 59)))) != 0)) ) {
							((BinaryExpressionContext)_localctx).Operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(339);
						((BinaryExpressionContext)_localctx).Right = expression(16);
						}
						break;
					case 4:
						{
						_localctx = new BinaryExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((BinaryExpressionContext)_localctx).Left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(340);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(341);
						((BinaryExpressionContext)_localctx).Operator = match(INSTANCEOF);
						setState(342);
						((BinaryExpressionContext)_localctx).Right = expression(15);
						}
						break;
					case 5:
						{
						_localctx = new BinaryExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((BinaryExpressionContext)_localctx).Left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(343);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(344);
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
						setState(345);
						((BinaryExpressionContext)_localctx).Right = expression(14);
						}
						break;
					case 6:
						{
						_localctx = new BinaryExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((BinaryExpressionContext)_localctx).Left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(346);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(347);
						((BinaryExpressionContext)_localctx).Operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 61)) & ~0x3f) == 0 && ((1L << (_la - 61)) & ((1L << (XOR - 61)) | (1L << (AND - 61)) | (1L << (OR - 61)) | (1L << (AND_AND - 61)) | (1L << (OR_OR - 61)))) != 0)) ) {
							((BinaryExpressionContext)_localctx).Operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(348);
						((BinaryExpressionContext)_localctx).Right = expression(13);
						}
						break;
					case 7:
						{
						_localctx = new TrinaryExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((TrinaryExpressionContext)_localctx).Condition = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(349);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(350);
						match(QUEST);
						setState(351);
						((TrinaryExpressionContext)_localctx).Then = expression(0);
						setState(352);
						match(COLON);
						setState(353);
						((TrinaryExpressionContext)_localctx).Else = expression(11);
						}
						break;
					case 8:
						{
						_localctx = new AssignmentExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((AssignmentExpressionContext)_localctx).Left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(355);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(356);
						((AssignmentExpressionContext)_localctx).Operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (ASSIGN - 68)) | (1L << (PLUS_ASSIGN - 68)) | (1L << (MINUS_ASSIGN - 68)) | (1L << (STAR_ASSIGN - 68)) | (1L << (DIV_ASSIGN - 68)) | (1L << (MOD_ASSIGN - 68)) | (1L << (XOR_ASSIGN - 68)) | (1L << (AND_ASSIGN - 68)) | (1L << (OR_ASSIGN - 68)) | (1L << (CAT_ASSIGN - 68)))) != 0)) ) {
							((AssignmentExpressionContext)_localctx).Operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(357);
						((AssignmentExpressionContext)_localctx).Right = expression(10);
						}
						break;
					case 9:
						{
						_localctx = new RangeExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((RangeExpressionContext)_localctx).From = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(358);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(359);
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
						setState(360);
						((RangeExpressionContext)_localctx).To = expression(9);
						}
						break;
					case 10:
						{
						_localctx = new ArgumentsExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((ArgumentsExpressionContext)_localctx).Left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(361);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(362);
						match(PAREN_OPEN);
						setState(364);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << TO) | (1L << THIS) | (1L << PAREN_OPEN) | (1L << BRACK_OPEN) | (1L << BRACE_OPEN) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << LESS))) != 0) || ((((_la - 85)) & ~0x3f) == 0 && ((1L << (_la - 85)) & ((1L << (DECIMAL_LITERAL - 85)) | (1L << (HEX_LITERAL - 85)) | (1L << (FLOATING_LITERAL - 85)) | (1L << (BOOLEAN_LITERAL - 85)) | (1L << (STRING_LITERAL - 85)) | (1L << (NULL_LITERAL - 85)) | (1L << (IDENTIFIER - 85)))) != 0)) {
							{
							setState(363);
							expression(0);
							}
						}

						setState(370);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==COMMA) {
							{
							{
							setState(366);
							match(COMMA);
							setState(367);
							expression(0);
							}
							}
							setState(372);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(373);
						match(PAREN_CLOSE);
						}
						break;
					case 11:
						{
						_localctx = new MemberIndexExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((MemberIndexExpressionContext)_localctx).Left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(374);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(375);
						match(BRACK_OPEN);
						setState(376);
						((MemberIndexExpressionContext)_localctx).Index = expression(0);
						setState(377);
						match(BRACK_CLOSE);
						}
						break;
					case 12:
						{
						_localctx = new TypeCastExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(379);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(380);
						match(AS);
						setState(381);
						type(0);
						}
						break;
					}
					} 
				}
				setState(386);
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
		enterRule(_localctx, 46, RULE_mapEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(387);
			((MapEntryContext)_localctx).Key = expression(0);
			setState(388);
			match(COLON);
			setState(389);
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
		public ClassNameContext className() {
			return getRuleContext(ClassNameContext.class,0);
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
		int _startState = 48;
		enterRecursionRule(_localctx, 48, RULE_type, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(405);
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

				setState(392);
				builtin();
				}
				break;
			case TO:
			case IDENTIFIER:
				{
				_localctx = new ReferenceTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(393);
				className();
				}
				break;
			case FUNCTION:
				{
				_localctx = new FunctionTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(394);
				match(FUNCTION);
				setState(395);
				match(PAREN_OPEN);
				setState(397);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FUNCTION) | (1L << TO) | (1L << BYTE) | (1L << BYTE_OBJ) | (1L << SHORT) | (1L << SHORT_OBJ) | (1L << INT) | (1L << INT_OBJ) | (1L << LONG) | (1L << LONG_OBJ) | (1L << FLOAT) | (1L << FLOAT_OBJ) | (1L << DOUBLE) | (1L << DOUBLE_OBJ) | (1L << BOOL) | (1L << BOOL_OBJ) | (1L << VOID) | (1L << STRING) | (1L << BRACK_OPEN))) != 0) || _la==IDENTIFIER) {
					{
					setState(396);
					argumentTypeList();
					}
				}

				setState(399);
				match(PAREN_CLOSE);
				setState(400);
				((FunctionTypeContext)_localctx).ResultType = type(4);
				}
				break;
			case BRACK_OPEN:
				{
				_localctx = new ListTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(401);
				match(BRACK_OPEN);
				setState(402);
				((ListTypeContext)_localctx).BaseType = type(0);
				setState(403);
				match(BRACK_CLOSE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(417);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,45,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(415);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,44,_ctx) ) {
					case 1:
						{
						_localctx = new ArrayTypeContext(new TypeContext(_parentctx, _parentState));
						((ArrayTypeContext)_localctx).BaseType = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(407);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(408);
						match(BRACK_OPEN);
						setState(409);
						match(BRACK_CLOSE);
						}
						break;
					case 2:
						{
						_localctx = new MapTypeContext(new TypeContext(_parentctx, _parentState));
						((MapTypeContext)_localctx).ValueType = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(410);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(411);
						match(BRACK_OPEN);
						setState(412);
						((MapTypeContext)_localctx).KeyType = type(0);
						setState(413);
						match(BRACK_CLOSE);
						}
						break;
					}
					} 
				}
				setState(419);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,45,_ctx);
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
		enterRule(_localctx, 50, RULE_builtin);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(420);
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
		enterRule(_localctx, 52, RULE_argumentTypeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(422);
			type(0);
			setState(427);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(423);
				match(COMMA);
				setState(424);
				type(0);
				}
				}
				setState(429);
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
		enterRule(_localctx, 54, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(430);
			_la = _input.LA(1);
			if ( !(((((_la - 85)) & ~0x3f) == 0 && ((1L << (_la - 85)) & ((1L << (DECIMAL_LITERAL - 85)) | (1L << (HEX_LITERAL - 85)) | (1L << (FLOATING_LITERAL - 85)) | (1L << (BOOLEAN_LITERAL - 85)) | (1L << (STRING_LITERAL - 85)) | (1L << (NULL_LITERAL - 85)))) != 0)) ) {
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
		enterRule(_localctx, 56, RULE_identifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(432);
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
		case 22:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		case 24:
			return type_sempred((TypeContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 19);
		case 1:
			return precpred(_ctx, 16);
		case 2:
			return precpred(_ctx, 15);
		case 3:
			return precpred(_ctx, 14);
		case 4:
			return precpred(_ctx, 13);
		case 5:
			return precpred(_ctx, 12);
		case 6:
			return precpred(_ctx, 11);
		case 7:
			return precpred(_ctx, 10);
		case 8:
			return precpred(_ctx, 8);
		case 9:
			return precpred(_ctx, 20);
		case 10:
			return precpred(_ctx, 18);
		case 11:
			return precpred(_ctx, 6);
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
		"\u0004\u0001_\u01b3\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0005\u0000?\b\u0000\n\u0000\f\u0000B\t\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001J\b\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0003\u0001R\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0005\u0002X\b\u0002\n\u0002\f\u0002[\t\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0003\u0002`\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0005\u0003g\b\u0003\n\u0003\f\u0003j\t\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0003\u0005r\b\u0005\u0001\u0005\u0001\u0005\u0005\u0005v\b\u0005\n\u0005"+
		"\f\u0005y\t\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005~\b\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006"+
		"\u0085\b\u0006\u0001\u0006\u0003\u0006\u0088\b\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0005"+
		"\b\u0093\b\b\n\b\f\b\u0096\t\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t"+
		"\u0003\t\u009d\b\t\u0001\t\u0001\t\u0005\t\u00a1\b\t\n\t\f\t\u00a4\t\t"+
		"\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0003\n\u00b0\b\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0005"+
		"\u000b\u00b6\b\u000b\n\u000b\f\u000b\u00b9\t\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0003\f\u00c6\b\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0003\u000e"+
		"\u00cc\b\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0003\u0011\u00da\b\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0003\u0011\u00df\b\u0011\u0003\u0011\u00e1\b\u0011\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0005\u0012\u00e7\b\u0012\n\u0012\f\u0012"+
		"\u00ea\t\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0003\u0014\u00fa\b\u0014\u0001\u0014"+
		"\u0001\u0014\u0003\u0014\u00fe\b\u0014\u0001\u0014\u0001\u0014\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0003\u0016\u0109\b\u0016\u0001\u0016\u0001\u0016\u0005\u0016\u010d\b"+
		"\u0016\n\u0016\f\u0016\u0110\t\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0003\u0016\u0115\b\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0003\u0016\u011d\b\u0016\u0005\u0016\u011f\b"+
		"\u0016\n\u0016\f\u0016\u0122\t\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0003\u0016\u0128\b\u0016\u0001\u0016\u0001\u0016\u0005\u0016"+
		"\u012c\b\u0016\n\u0016\f\u0016\u012f\t\u0016\u0001\u0016\u0003\u0016\u0132"+
		"\b\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0003\u0016\u0137\b\u0016"+
		"\u0001\u0016\u0001\u0016\u0005\u0016\u013b\b\u0016\n\u0016\f\u0016\u013e"+
		"\t\u0016\u0001\u0016\u0003\u0016\u0141\b\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0003\u0016"+
		"\u014a\b\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0003\u0016\u016d\b\u0016"+
		"\u0001\u0016\u0001\u0016\u0005\u0016\u0171\b\u0016\n\u0016\f\u0016\u0174"+
		"\t\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0005\u0016\u017f\b\u0016\n"+
		"\u0016\f\u0016\u0182\t\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001"+
		"\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0003\u0018\u018e\b\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0003\u0018\u0196\b\u0018\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0005\u0018\u01a0\b\u0018\n\u0018\f\u0018\u01a3\t\u0018\u0001\u0019"+
		"\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0005\u001a\u01aa\b\u001a"+
		"\n\u001a\f\u001a\u01ad\t\u001a\u0001\u001b\u0001\u001b\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0000\u0002,0\u001d\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468\u0000\r"+
		"\u0001\u0000\u0001\u0002\u0001\u0000\u0001\u0004\u0002\u000045::\u0001"+
		"\u0000<<\u0001\u000049\u0002\u0000;<GJ\u0001\u0000\t\n\u0003\u0000==B"+
		"CEF\u0002\u0000DDKS\u0002\u0000\b\bTT\u0001\u0000\u000e\u001d\u0001\u0000"+
		"UZ\u0002\u0000\b\b[[\u01e1\u0000@\u0001\u0000\u0000\u0000\u0002Q\u0001"+
		"\u0000\u0000\u0000\u0004_\u0001\u0000\u0000\u0000\u0006a\u0001\u0000\u0000"+
		"\u0000\bk\u0001\u0000\u0000\u0000\nm\u0001\u0000\u0000\u0000\f\u0081\u0001"+
		"\u0000\u0000\u0000\u000e\u0089\u0001\u0000\u0000\u0000\u0010\u008c\u0001"+
		"\u0000\u0000\u0000\u0012\u0099\u0001\u0000\u0000\u0000\u0014\u00a8\u0001"+
		"\u0000\u0000\u0000\u0016\u00b3\u0001\u0000\u0000\u0000\u0018\u00c5\u0001"+
		"\u0000\u0000\u0000\u001a\u00c7\u0001\u0000\u0000\u0000\u001c\u00c9\u0001"+
		"\u0000\u0000\u0000\u001e\u00cf\u0001\u0000\u0000\u0000 \u00d2\u0001\u0000"+
		"\u0000\u0000\"\u00d5\u0001\u0000\u0000\u0000$\u00e2\u0001\u0000\u0000"+
		"\u0000&\u00ef\u0001\u0000\u0000\u0000(\u00f5\u0001\u0000\u0000\u0000*"+
		"\u0101\u0001\u0000\u0000\u0000,\u0149\u0001\u0000\u0000\u0000.\u0183\u0001"+
		"\u0000\u0000\u00000\u0195\u0001\u0000\u0000\u00002\u01a4\u0001\u0000\u0000"+
		"\u00004\u01a6\u0001\u0000\u0000\u00006\u01ae\u0001\u0000\u0000\u00008"+
		"\u01b0\u0001\u0000\u0000\u0000:?\u0003\u0002\u0001\u0000;?\u0003\n\u0005"+
		"\u0000<?\u0003\u0010\b\u0000=?\u0003\u0018\f\u0000>:\u0001\u0000\u0000"+
		"\u0000>;\u0001\u0000\u0000\u0000><\u0001\u0000\u0000\u0000>=\u0001\u0000"+
		"\u0000\u0000?B\u0001\u0000\u0000\u0000@>\u0001\u0000\u0000\u0000@A\u0001"+
		"\u0000\u0000\u0000AC\u0001\u0000\u0000\u0000B@\u0001\u0000\u0000\u0000"+
		"CD\u0005\u0000\u0000\u0001D\u0001\u0001\u0000\u0000\u0000EF\u0005\u0005"+
		"\u0000\u0000FI\u0003\u0004\u0002\u0000GH\u0005\u0007\u0000\u0000HJ\u0003"+
		"\b\u0004\u0000IG\u0001\u0000\u0000\u0000IJ\u0001\u0000\u0000\u0000JK\u0001"+
		"\u0000\u0000\u0000KL\u00053\u0000\u0000LR\u0001\u0000\u0000\u0000MN\u0005"+
		"\u0005\u0000\u0000NO\u0003\u0006\u0003\u0000OP\u00053\u0000\u0000PR\u0001"+
		"\u0000\u0000\u0000QE\u0001\u0000\u0000\u0000QM\u0001\u0000\u0000\u0000"+
		"R\u0003\u0001\u0000\u0000\u0000S`\u00038\u001c\u0000TY\u00038\u001c\u0000"+
		"UV\u00052\u0000\u0000VX\u00038\u001c\u0000WU\u0001\u0000\u0000\u0000X"+
		"[\u0001\u0000\u0000\u0000YW\u0001\u0000\u0000\u0000YZ\u0001\u0000\u0000"+
		"\u0000Z\\\u0001\u0000\u0000\u0000[Y\u0001\u0000\u0000\u0000\\]\u00052"+
		"\u0000\u0000]^\u00038\u001c\u0000^`\u0001\u0000\u0000\u0000_S\u0001\u0000"+
		"\u0000\u0000_T\u0001\u0000\u0000\u0000`\u0005\u0001\u0000\u0000\u0000"+
		"ab\u0005*\u0000\u0000bc\u00052\u0000\u0000ch\u00038\u001c\u0000de\u0005"+
		"2\u0000\u0000eg\u00038\u001c\u0000fd\u0001\u0000\u0000\u0000gj\u0001\u0000"+
		"\u0000\u0000hf\u0001\u0000\u0000\u0000hi\u0001\u0000\u0000\u0000i\u0007"+
		"\u0001\u0000\u0000\u0000jh\u0001\u0000\u0000\u0000kl\u00038\u001c\u0000"+
		"l\t\u0001\u0000\u0000\u0000mn\u0005\u0006\u0000\u0000no\u00038\u001c\u0000"+
		"oq\u0005+\u0000\u0000pr\u0003\f\u0006\u0000qp\u0001\u0000\u0000\u0000"+
		"qr\u0001\u0000\u0000\u0000rw\u0001\u0000\u0000\u0000st\u00051\u0000\u0000"+
		"tv\u0003\f\u0006\u0000us\u0001\u0000\u0000\u0000vy\u0001\u0000\u0000\u0000"+
		"wu\u0001\u0000\u0000\u0000wx\u0001\u0000\u0000\u0000xz\u0001\u0000\u0000"+
		"\u0000yw\u0001\u0000\u0000\u0000z}\u0005,\u0000\u0000{|\u0005\u0007\u0000"+
		"\u0000|~\u00030\u0018\u0000}{\u0001\u0000\u0000\u0000}~\u0001\u0000\u0000"+
		"\u0000~\u007f\u0001\u0000\u0000\u0000\u007f\u0080\u0003\u0016\u000b\u0000"+
		"\u0080\u000b\u0001\u0000\u0000\u0000\u0081\u0084\u00038\u001c\u0000\u0082"+
		"\u0083\u0005\u0007\u0000\u0000\u0083\u0085\u00030\u0018\u0000\u0084\u0082"+
		"\u0001\u0000\u0000\u0000\u0084\u0085\u0001\u0000\u0000\u0000\u0085\u0087"+
		"\u0001\u0000\u0000\u0000\u0086\u0088\u0003\u000e\u0007\u0000\u0087\u0086"+
		"\u0001\u0000\u0000\u0000\u0087\u0088\u0001\u0000\u0000\u0000\u0088\r\u0001"+
		"\u0000\u0000\u0000\u0089\u008a\u0005D\u0000\u0000\u008a\u008b\u0003,\u0016"+
		"\u0000\u008b\u000f\u0001\u0000\u0000\u0000\u008c\u008d\u0005(\u0000\u0000"+
		"\u008d\u008e\u00038\u001c\u0000\u008e\u0094\u0005/\u0000\u0000\u008f\u0093"+
		"\u0003\u0014\n\u0000\u0090\u0093\u0003\u0012\t\u0000\u0091\u0093\u0003"+
		"\n\u0005\u0000\u0092\u008f\u0001\u0000\u0000\u0000\u0092\u0090\u0001\u0000"+
		"\u0000\u0000\u0092\u0091\u0001\u0000\u0000\u0000\u0093\u0096\u0001\u0000"+
		"\u0000\u0000\u0094\u0092\u0001\u0000\u0000\u0000\u0094\u0095\u0001\u0000"+
		"\u0000\u0000\u0095\u0097\u0001\u0000\u0000\u0000\u0096\u0094\u0001\u0000"+
		"\u0000\u0000\u0097\u0098\u00050\u0000\u0000\u0098\u0011\u0001\u0000\u0000"+
		"\u0000\u0099\u009a\u0005)\u0000\u0000\u009a\u009c\u0005+\u0000\u0000\u009b"+
		"\u009d\u0003\f\u0006\u0000\u009c\u009b\u0001\u0000\u0000\u0000\u009c\u009d"+
		"\u0001\u0000\u0000\u0000\u009d\u00a2\u0001\u0000\u0000\u0000\u009e\u009f"+
		"\u00051\u0000\u0000\u009f\u00a1\u0003\f\u0006\u0000\u00a0\u009e\u0001"+
		"\u0000\u0000\u0000\u00a1\u00a4\u0001\u0000\u0000\u0000\u00a2\u00a0\u0001"+
		"\u0000\u0000\u0000\u00a2\u00a3\u0001\u0000\u0000\u0000\u00a3\u00a5\u0001"+
		"\u0000\u0000\u0000\u00a4\u00a2\u0001\u0000\u0000\u0000\u00a5\u00a6\u0005"+
		",\u0000\u0000\u00a6\u00a7\u0003\u0016\u000b\u0000\u00a7\u0013\u0001\u0000"+
		"\u0000\u0000\u00a8\u00a9\u0007\u0000\u0000\u0000\u00a9\u00aa\u00038\u001c"+
		"\u0000\u00aa\u00ab\u0005\u0007\u0000\u0000\u00ab\u00ac\u00030\u0018\u0000"+
		"\u00ac\u00af\u0001\u0000\u0000\u0000\u00ad\u00ae\u0005D\u0000\u0000\u00ae"+
		"\u00b0\u0003,\u0016\u0000\u00af\u00ad\u0001\u0000\u0000\u0000\u00af\u00b0"+
		"\u0001\u0000\u0000\u0000\u00b0\u00b1\u0001\u0000\u0000\u0000\u00b1\u00b2"+
		"\u00053\u0000\u0000\u00b2\u0015\u0001\u0000\u0000\u0000\u00b3\u00b7\u0005"+
		"/\u0000\u0000\u00b4\u00b6\u0003\u0018\f\u0000\u00b5\u00b4\u0001\u0000"+
		"\u0000\u0000\u00b6\u00b9\u0001\u0000\u0000\u0000\u00b7\u00b5\u0001\u0000"+
		"\u0000\u0000\u00b7\u00b8\u0001\u0000\u0000\u0000\u00b8\u00ba\u0001\u0000"+
		"\u0000\u0000\u00b9\u00b7\u0001\u0000\u0000\u0000\u00ba\u00bb\u00050\u0000"+
		"\u0000\u00bb\u0017\u0001\u0000\u0000\u0000\u00bc\u00c6\u0003\u001a\r\u0000"+
		"\u00bd\u00c6\u0003\u001c\u000e\u0000\u00be\u00c6\u0003\u001e\u000f\u0000"+
		"\u00bf\u00c6\u0003 \u0010\u0000\u00c0\u00c6\u0003\"\u0011\u0000\u00c1"+
		"\u00c6\u0003$\u0012\u0000\u00c2\u00c6\u0003&\u0013\u0000\u00c3\u00c6\u0003"+
		"(\u0014\u0000\u00c4\u00c6\u0003*\u0015\u0000\u00c5\u00bc\u0001\u0000\u0000"+
		"\u0000\u00c5\u00bd\u0001\u0000\u0000\u0000\u00c5\u00be\u0001\u0000\u0000"+
		"\u0000\u00c5\u00bf\u0001\u0000\u0000\u0000\u00c5\u00c0\u0001\u0000\u0000"+
		"\u0000\u00c5\u00c1\u0001\u0000\u0000\u0000\u00c5\u00c2\u0001\u0000\u0000"+
		"\u0000\u00c5\u00c3\u0001\u0000\u0000\u0000\u00c5\u00c4\u0001\u0000\u0000"+
		"\u0000\u00c6\u0019\u0001\u0000\u0000\u0000\u00c7\u00c8\u0003\u0016\u000b"+
		"\u0000\u00c8\u001b\u0001\u0000\u0000\u0000\u00c9\u00cb\u0005%\u0000\u0000"+
		"\u00ca\u00cc\u0003,\u0016\u0000\u00cb\u00ca\u0001\u0000\u0000\u0000\u00cb"+
		"\u00cc\u0001\u0000\u0000\u0000\u00cc\u00cd\u0001\u0000\u0000\u0000\u00cd"+
		"\u00ce\u00053\u0000\u0000\u00ce\u001d\u0001\u0000\u0000\u0000\u00cf\u00d0"+
		"\u0005#\u0000\u0000\u00d0\u00d1\u00053\u0000\u0000\u00d1\u001f\u0001\u0000"+
		"\u0000\u0000\u00d2\u00d3\u0005$\u0000\u0000\u00d3\u00d4\u00053\u0000\u0000"+
		"\u00d4!\u0001\u0000\u0000\u0000\u00d5\u00d6\u0005\u001e\u0000\u0000\u00d6"+
		"\u00d9\u0003,\u0016\u0000\u00d7\u00da\u0003\u0018\f\u0000\u00d8\u00da"+
		"\u0003\u0016\u000b\u0000\u00d9\u00d7\u0001\u0000\u0000\u0000\u00d9\u00d8"+
		"\u0001\u0000\u0000\u0000\u00da\u00e0\u0001\u0000\u0000\u0000\u00db\u00de"+
		"\u0005\u001f\u0000\u0000\u00dc\u00df\u0003\u0018\f\u0000\u00dd\u00df\u0003"+
		"\u0016\u000b\u0000\u00de\u00dc\u0001\u0000\u0000\u0000\u00de\u00dd\u0001"+
		"\u0000\u0000\u0000\u00df\u00e1\u0001\u0000\u0000\u0000\u00e0\u00db\u0001"+
		"\u0000\u0000\u0000\u00e0\u00e1\u0001\u0000\u0000\u0000\u00e1#\u0001\u0000"+
		"\u0000\u0000\u00e2\u00e3\u0005 \u0000\u0000\u00e3\u00e8\u00038\u001c\u0000"+
		"\u00e4\u00e5\u00051\u0000\u0000\u00e5\u00e7\u00038\u001c\u0000\u00e6\u00e4"+
		"\u0001\u0000\u0000\u0000\u00e7\u00ea\u0001\u0000\u0000\u0000\u00e8\u00e6"+
		"\u0001\u0000\u0000\u0000\u00e8\u00e9\u0001\u0000\u0000\u0000\u00e9\u00eb"+
		"\u0001\u0000\u0000\u0000\u00ea\u00e8\u0001\u0000\u0000\u0000\u00eb\u00ec"+
		"\u0005\t\u0000\u0000\u00ec\u00ed\u0003,\u0016\u0000\u00ed\u00ee\u0003"+
		"\u0016\u000b\u0000\u00ee%\u0001\u0000\u0000\u0000\u00ef\u00f0\u0005\""+
		"\u0000\u0000\u00f0\u00f1\u0005+\u0000\u0000\u00f1\u00f2\u0003,\u0016\u0000"+
		"\u00f2\u00f3\u0005,\u0000\u0000\u00f3\u00f4\u0003\u0016\u000b\u0000\u00f4"+
		"\'\u0001\u0000\u0000\u0000\u00f5\u00f6\u0007\u0001\u0000\u0000\u00f6\u00f9"+
		"\u00038\u001c\u0000\u00f7\u00f8\u0005\u0007\u0000\u0000\u00f8\u00fa\u0003"+
		"0\u0018\u0000\u00f9\u00f7\u0001\u0000\u0000\u0000\u00f9\u00fa\u0001\u0000"+
		"\u0000\u0000\u00fa\u00fd\u0001\u0000\u0000\u0000\u00fb\u00fc\u0005D\u0000"+
		"\u0000\u00fc\u00fe\u0003,\u0016\u0000\u00fd\u00fb\u0001\u0000\u0000\u0000"+
		"\u00fd\u00fe\u0001\u0000\u0000\u0000\u00fe\u00ff\u0001\u0000\u0000\u0000"+
		"\u00ff\u0100\u00053\u0000\u0000\u0100)\u0001\u0000\u0000\u0000\u0101\u0102"+
		"\u0003,\u0016\u0000\u0102\u0103\u00053\u0000\u0000\u0103+\u0001\u0000"+
		"\u0000\u0000\u0104\u0105\u0006\u0016\uffff\uffff\u0000\u0105\u0106\u0005"+
		"\u0006\u0000\u0000\u0106\u0108\u0005+\u0000\u0000\u0107\u0109\u0003\f"+
		"\u0006\u0000\u0108\u0107\u0001\u0000\u0000\u0000\u0108\u0109\u0001\u0000"+
		"\u0000\u0000\u0109\u010e\u0001\u0000\u0000\u0000\u010a\u010b\u00051\u0000"+
		"\u0000\u010b\u010d\u0003\f\u0006\u0000\u010c\u010a\u0001\u0000\u0000\u0000"+
		"\u010d\u0110\u0001\u0000\u0000\u0000\u010e\u010c\u0001\u0000\u0000\u0000"+
		"\u010e\u010f\u0001\u0000\u0000\u0000\u010f\u0111\u0001\u0000\u0000\u0000"+
		"\u0110\u010e\u0001\u0000\u0000\u0000\u0111\u0114\u0005,\u0000\u0000\u0112"+
		"\u0113\u0005\u0007\u0000\u0000\u0113\u0115\u00030\u0018\u0000\u0114\u0112"+
		"\u0001\u0000\u0000\u0000\u0114\u0115\u0001\u0000\u0000\u0000\u0115\u0116"+
		"\u0001\u0000\u0000\u0000\u0116\u014a\u0003\u0016\u000b\u0000\u0117\u0118"+
		"\u0007\u0002\u0000\u0000\u0118\u014a\u0003,\u0016\u0011\u0119\u0120\u0005"+
		";\u0000\u0000\u011a\u011c\b\u0003\u0000\u0000\u011b\u011d\u0005>\u0000"+
		"\u0000\u011c\u011b\u0001\u0000\u0000\u0000\u011c\u011d\u0001\u0000\u0000"+
		"\u0000\u011d\u011f\u0001\u0000\u0000\u0000\u011e\u011a\u0001\u0000\u0000"+
		"\u0000\u011f\u0122\u0001\u0000\u0000\u0000\u0120\u011e\u0001\u0000\u0000"+
		"\u0000\u0120\u0121\u0001\u0000\u0000\u0000\u0121\u0123\u0001\u0000\u0000"+
		"\u0000\u0122\u0120\u0001\u0000\u0000\u0000\u0123\u014a\u0005<\u0000\u0000"+
		"\u0124\u014a\u0005\f\u0000\u0000\u0125\u0127\u0005-\u0000\u0000\u0126"+
		"\u0128\u0003,\u0016\u0000\u0127\u0126\u0001\u0000\u0000\u0000\u0127\u0128"+
		"\u0001\u0000\u0000\u0000\u0128\u012d\u0001\u0000\u0000\u0000\u0129\u012a"+
		"\u00051\u0000\u0000\u012a\u012c\u0003,\u0016\u0000\u012b\u0129\u0001\u0000"+
		"\u0000\u0000\u012c\u012f\u0001\u0000\u0000\u0000\u012d\u012b\u0001\u0000"+
		"\u0000\u0000\u012d\u012e\u0001\u0000\u0000\u0000\u012e\u0131\u0001\u0000"+
		"\u0000\u0000\u012f\u012d\u0001\u0000\u0000\u0000\u0130\u0132\u00051\u0000"+
		"\u0000\u0131\u0130\u0001\u0000\u0000\u0000\u0131\u0132\u0001\u0000\u0000"+
		"\u0000\u0132\u0133\u0001\u0000\u0000\u0000\u0133\u014a\u0005.\u0000\u0000"+
		"\u0134\u0136\u0005/\u0000\u0000\u0135\u0137\u0003.\u0017\u0000\u0136\u0135"+
		"\u0001\u0000\u0000\u0000\u0136\u0137\u0001\u0000\u0000\u0000\u0137\u013c"+
		"\u0001\u0000\u0000\u0000\u0138\u0139\u00051\u0000\u0000\u0139\u013b\u0003"+
		".\u0017\u0000\u013a\u0138\u0001\u0000\u0000\u0000\u013b\u013e\u0001\u0000"+
		"\u0000\u0000\u013c\u013a\u0001\u0000\u0000\u0000\u013c\u013d\u0001\u0000"+
		"\u0000\u0000\u013d\u0140\u0001\u0000\u0000\u0000\u013e\u013c\u0001\u0000"+
		"\u0000\u0000\u013f\u0141\u00051\u0000\u0000\u0140\u013f\u0001\u0000\u0000"+
		"\u0000\u0140\u0141\u0001\u0000\u0000\u0000\u0141\u0142\u0001\u0000\u0000"+
		"\u0000\u0142\u014a\u00050\u0000\u0000\u0143\u014a\u00036\u001b\u0000\u0144"+
		"\u0145\u0005+\u0000\u0000\u0145\u0146\u0003,\u0016\u0000\u0146\u0147\u0005"+
		",\u0000\u0000\u0147\u014a\u0001\u0000\u0000\u0000\u0148\u014a\u00038\u001c"+
		"\u0000\u0149\u0104\u0001\u0000\u0000\u0000\u0149\u0117\u0001\u0000\u0000"+
		"\u0000\u0149\u0119\u0001\u0000\u0000\u0000\u0149\u0124\u0001\u0000\u0000"+
		"\u0000\u0149\u0125\u0001\u0000\u0000\u0000\u0149\u0134\u0001\u0000\u0000"+
		"\u0000\u0149\u0143\u0001\u0000\u0000\u0000\u0149\u0144\u0001\u0000\u0000"+
		"\u0000\u0149\u0148\u0001\u0000\u0000\u0000\u014a\u0180\u0001\u0000\u0000"+
		"\u0000\u014b\u014c\n\u0013\u0000\u0000\u014c\u014d\u00052\u0000\u0000"+
		"\u014d\u017f\u0003,\u0016\u0014\u014e\u014f\n\u0010\u0000\u0000\u014f"+
		"\u0150\u0007\u0004\u0000\u0000\u0150\u017f\u0003,\u0016\u0011\u0151\u0152"+
		"\n\u000f\u0000\u0000\u0152\u0153\u0007\u0005\u0000\u0000\u0153\u017f\u0003"+
		",\u0016\u0010\u0154\u0155\n\u000e\u0000\u0000\u0155\u0156\u0005\u000b"+
		"\u0000\u0000\u0156\u017f\u0003,\u0016\u000f\u0157\u0158\n\r\u0000\u0000"+
		"\u0158\u0159\u0007\u0006\u0000\u0000\u0159\u017f\u0003,\u0016\u000e\u015a"+
		"\u015b\n\f\u0000\u0000\u015b\u015c\u0007\u0007\u0000\u0000\u015c\u017f"+
		"\u0003,\u0016\r\u015d\u015e\n\u000b\u0000\u0000\u015e\u015f\u0005?\u0000"+
		"\u0000\u015f\u0160\u0003,\u0016\u0000\u0160\u0161\u0005>\u0000\u0000\u0161"+
		"\u0162\u0003,\u0016\u000b\u0162\u017f\u0001\u0000\u0000\u0000\u0163\u0164"+
		"\n\n\u0000\u0000\u0164\u0165\u0007\b\u0000\u0000\u0165\u017f\u0003,\u0016"+
		"\n\u0166\u0167\n\b\u0000\u0000\u0167\u0168\u0007\t\u0000\u0000\u0168\u017f"+
		"\u0003,\u0016\t\u0169\u016a\n\u0014\u0000\u0000\u016a\u016c\u0005+\u0000"+
		"\u0000\u016b\u016d\u0003,\u0016\u0000\u016c\u016b\u0001\u0000\u0000\u0000"+
		"\u016c\u016d\u0001\u0000\u0000\u0000\u016d\u0172\u0001\u0000\u0000\u0000"+
		"\u016e\u016f\u00051\u0000\u0000\u016f\u0171\u0003,\u0016\u0000\u0170\u016e"+
		"\u0001\u0000\u0000\u0000\u0171\u0174\u0001\u0000\u0000\u0000\u0172\u0170"+
		"\u0001\u0000\u0000\u0000\u0172\u0173\u0001\u0000\u0000\u0000\u0173\u0175"+
		"\u0001\u0000\u0000\u0000\u0174\u0172\u0001\u0000\u0000\u0000\u0175\u017f"+
		"\u0005,\u0000\u0000\u0176\u0177\n\u0012\u0000\u0000\u0177\u0178\u0005"+
		"-\u0000\u0000\u0178\u0179\u0003,\u0016\u0000\u0179\u017a\u0005.\u0000"+
		"\u0000\u017a\u017f\u0001\u0000\u0000\u0000\u017b\u017c\n\u0006\u0000\u0000"+
		"\u017c\u017d\u0005\u0007\u0000\u0000\u017d\u017f\u00030\u0018\u0000\u017e"+
		"\u014b\u0001\u0000\u0000\u0000\u017e\u014e\u0001\u0000\u0000\u0000\u017e"+
		"\u0151\u0001\u0000\u0000\u0000\u017e\u0154\u0001\u0000\u0000\u0000\u017e"+
		"\u0157\u0001\u0000\u0000\u0000\u017e\u015a\u0001\u0000\u0000\u0000\u017e"+
		"\u015d\u0001\u0000\u0000\u0000\u017e\u0163\u0001\u0000\u0000\u0000\u017e"+
		"\u0166\u0001\u0000\u0000\u0000\u017e\u0169\u0001\u0000\u0000\u0000\u017e"+
		"\u0176\u0001\u0000\u0000\u0000\u017e\u017b\u0001\u0000\u0000\u0000\u017f"+
		"\u0182\u0001\u0000\u0000\u0000\u0180\u017e\u0001\u0000\u0000\u0000\u0180"+
		"\u0181\u0001\u0000\u0000\u0000\u0181-\u0001\u0000\u0000\u0000\u0182\u0180"+
		"\u0001\u0000\u0000\u0000\u0183\u0184\u0003,\u0016\u0000\u0184\u0185\u0005"+
		">\u0000\u0000\u0185\u0186\u0003,\u0016\u0000\u0186/\u0001\u0000\u0000"+
		"\u0000\u0187\u0188\u0006\u0018\uffff\uffff\u0000\u0188\u0196\u00032\u0019"+
		"\u0000\u0189\u0196\u0003\u0004\u0002\u0000\u018a\u018b\u0005\u0006\u0000"+
		"\u0000\u018b\u018d\u0005+\u0000\u0000\u018c\u018e\u00034\u001a\u0000\u018d"+
		"\u018c\u0001\u0000\u0000\u0000\u018d\u018e\u0001\u0000\u0000\u0000\u018e"+
		"\u018f\u0001\u0000\u0000\u0000\u018f\u0190\u0005,\u0000\u0000\u0190\u0196"+
		"\u00030\u0018\u0004\u0191\u0192\u0005-\u0000\u0000\u0192\u0193\u00030"+
		"\u0018\u0000\u0193\u0194\u0005.\u0000\u0000\u0194\u0196\u0001\u0000\u0000"+
		"\u0000\u0195\u0187\u0001\u0000\u0000\u0000\u0195\u0189\u0001\u0000\u0000"+
		"\u0000\u0195\u018a\u0001\u0000\u0000\u0000\u0195\u0191\u0001\u0000\u0000"+
		"\u0000\u0196\u01a1\u0001\u0000\u0000\u0000\u0197\u0198\n\u0002\u0000\u0000"+
		"\u0198\u0199\u0005-\u0000\u0000\u0199\u01a0\u0005.\u0000\u0000\u019a\u019b"+
		"\n\u0001\u0000\u0000\u019b\u019c\u0005-\u0000\u0000\u019c\u019d\u0003"+
		"0\u0018\u0000\u019d\u019e\u0005.\u0000\u0000\u019e\u01a0\u0001\u0000\u0000"+
		"\u0000\u019f\u0197\u0001\u0000\u0000\u0000\u019f\u019a\u0001\u0000\u0000"+
		"\u0000\u01a0\u01a3\u0001\u0000\u0000\u0000\u01a1\u019f\u0001\u0000\u0000"+
		"\u0000\u01a1\u01a2\u0001\u0000\u0000\u0000\u01a21\u0001\u0000\u0000\u0000"+
		"\u01a3\u01a1\u0001\u0000\u0000\u0000\u01a4\u01a5\u0007\n\u0000\u0000\u01a5"+
		"3\u0001\u0000\u0000\u0000\u01a6\u01ab\u00030\u0018\u0000\u01a7\u01a8\u0005"+
		"1\u0000\u0000\u01a8\u01aa\u00030\u0018\u0000\u01a9\u01a7\u0001\u0000\u0000"+
		"\u0000\u01aa\u01ad\u0001\u0000\u0000\u0000\u01ab\u01a9\u0001\u0000\u0000"+
		"\u0000\u01ab\u01ac\u0001\u0000\u0000\u0000\u01ac5\u0001\u0000\u0000\u0000"+
		"\u01ad\u01ab\u0001\u0000\u0000\u0000\u01ae\u01af\u0007\u000b\u0000\u0000"+
		"\u01af7\u0001\u0000\u0000\u0000\u01b0\u01b1\u0007\f\u0000\u0000\u01b1"+
		"9\u0001\u0000\u0000\u0000/>@IQY_hqw}\u0084\u0087\u0092\u0094\u009c\u00a2"+
		"\u00af\u00b7\u00c5\u00cb\u00d9\u00de\u00e0\u00e8\u00f9\u00fd\u0108\u010e"+
		"\u0114\u011c\u0120\u0127\u012d\u0131\u0136\u013c\u0140\u0149\u016c\u0172"+
		"\u017e\u0180\u018d\u0195\u019f\u01a1\u01ab";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}