package raylras.zen.ast;

import raylras.zen.antlr.ZenScriptParser;
import raylras.zen.antlr.ZenScriptParserBaseVisitor;
import raylras.zen.ast.decl.FunctionDeclaration;
import raylras.zen.ast.decl.ImportDeclaration;
import raylras.zen.ast.decl.ZenClassDeclaration;
import raylras.zen.ast.stmt.Statement;
import raylras.zen.ast.visit.DeclarationVisitor;
import raylras.zen.ast.visit.ExpressionVisitor;
import raylras.zen.ast.visit.StatementVisitor;
import raylras.zen.ast.visit.TypeVisitor;

import java.net.URI;
import java.util.*;

public final class ASTBuilder extends ZenScriptParserBaseVisitor<ScriptNode> {

    private static final class SymbolTable {

        private final Deque<Map<String, Symbol>> symbolScopes = new ArrayDeque<>();

        public SymbolTable() {
            enter();
//            loadBuiltIns();
        }

        public void enter() {
            symbolScopes.push(new HashMap<>());
        }

        public void exit() {
            symbolScopes.pop();
        }

        public Symbol add(String name, Node node, URI uri) {
            Map<String, Symbol> currentScope = symbolScopes.peek();
            if (currentScope == null) return null;

            Symbol same = currentScope.get(name);
            if (same != null) {
                // errorCollector.add(new DuplicateSymbolError(name, uri, same.getUri(), same.getLine(), same.getColumn()));
                return null;
            }

            Symbol symbol = Symbol.create(name, node, uri);
            currentScope.put(name, symbol);
            return symbol;
        }

        private Optional<Symbol> find(String name) {
            Symbol symbol = null;
            for (Map<String, Symbol> currentScope : symbolScopes) {
                symbol = currentScope.get(name);
                if (symbol != null) break;
            }
            return Optional.ofNullable(symbol);
        }

//        private void loadBuiltIns() {
//            List<String> builtIns = Arrays.asList("print", "isNull", "max", "min", "pow", "totalActions", "enableDebug", "brewing", "client", "events", "format", "furnace", "game", "itemUtils", "loadedMods", "logger", "oreDict", "recipes", "server", "vanilla");
//            Map<String, Symbol> currentScope = symbolScopes.peek();
//            if (currentScope == null) return;
//            for (String builtIn : builtIns) {
//                currentScope.put(builtIn, new Symbol(builtIn, null, null));
//            }
//            currentScope.put("mods", new Symbol("mods", null, null));
//        }

    }

    private final DeclarationVisitor declVisitor = new DeclarationVisitor(this);
    private final StatementVisitor stmtVisitor = new StatementVisitor(this);
    private final ExpressionVisitor exprVisitor = new ExpressionVisitor(this);
    private final TypeVisitor typeVisitor = new TypeVisitor();
    private final SymbolTable symbolTable = new SymbolTable();

    private URI currentURI;

    public ScriptNode lower(URI uri, ZenScriptParser.ScriptUnitContext ctx) {
        currentURI = uri;
        ScriptNode scriptNode = visitScriptUnit(ctx);
        scriptNode.setUri(uri);
        return scriptNode;
    }

    public void pushScope() {
        symbolTable.enter();
    }

    public void popScope() {
        symbolTable.exit();
    }

    public void addSymbolToCurrentScope(String name, Node node) {
        symbolTable.add(name, node, currentURI);
    }

    public Optional<Symbol> findSymbolInCurrentScope(String name) {
        return symbolTable.find(name);
    }

    public DeclarationVisitor getDeclVisitor() {
        return declVisitor;
    }

    public StatementVisitor getStmtVisitor() {
        return stmtVisitor;
    }

    public ExpressionVisitor getExprVisitor() {
        return exprVisitor;
    }

    public TypeVisitor getTypeVisitor() {
        return typeVisitor;
    }

    @Override
    public ScriptNode visitScriptUnit(ZenScriptParser.ScriptUnitContext ctx) {
        if (ctx == null) return null;

        pushScope();
        List<ImportDeclaration> imports = ctx.importDeclaration().stream().map(declVisitor::visitImportDeclaration).toList();
        List<FunctionDeclaration> functions = ctx.functionDeclaration().stream().map(declVisitor::visitFunctionDeclaration).toList();
        List<ZenClassDeclaration> zenClasses = ctx.zenClassDeclaration().stream().map(declVisitor::visitZenClassDeclaration).toList();
        List<Statement> statements = ctx.statement().stream().map(stmtVisitor::visitStatement).toList();
        popScope();

        ScriptNode script = new ScriptNode(imports, functions, zenClasses, statements);
        script.setRange(Range.of(ctx));

        return script;
    }

}
