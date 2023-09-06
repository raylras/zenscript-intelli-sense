package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.misc.Predicate;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.ReferenceParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Listener;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.resolve.SymbolResolver;
import raylras.zen.code.symbol.ParseTreeLocatable;
import raylras.zen.code.symbol.OperatorFunctionSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.langserver.Document;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.Position;
import raylras.zen.util.Ranges;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ReferencesProvider {
    private static final Logger logger = LoggerFactory.getLogger(ReferencesProvider.class);


    /**
     * find references has four steps:
     * 1. get a symbol under cursor
     * 2. get a searching rule related to the symbol ( for most symbols, it matches name, but for some special symbols like operators are different)
     * 3. search the terminal node at all documents
     * 4. get the cst at every searched node, and resolve its symbol, filtering those containing the current symbol.
     */
    public static CompletableFuture<List<? extends Location>> references(Document doc, ReferenceParams params) {

        return doc.getUnit().map(unit -> CompletableFuture.<List<? extends Location>>supplyAsync(() -> {
            Position cursor = Position.of(params.getPosition());
            Symbol symbol = getSymbolOnCursor(unit, cursor);
            if (symbol == null) {
                logger.warn("Could not get symbol at ({}, {}), skipping find usages", params.getPosition().getLine(), params.getPosition().getCharacter());
                return null;
            }
            logger.info("Finding usages for {}", symbol.getName());
            Instant searchStart = Instant.now();
            Predicate<TerminalNode> searchRule = getSymbolSearchRule(symbol);
            if (searchRule == null) {
                logger.warn("Could not get symbol search rule at ({}, {}), for symbol {}", params.getPosition().getLine(), params.getPosition().getCharacter(), symbol);
                return null;
            }


            List<Location> result = getSearchingScope(symbol, unit).stream().parallel().flatMap(cu -> {
                        String uri = cu.getPath().toUri().toString();
                        return searchPossible(searchRule, cu.getParseTree())
                                .stream().parallel().filter(cst -> {
                                    Collection<Symbol> symbols = SymbolResolver.lookupSymbol(cst, unit);
                                    return symbols.stream().anyMatch(it -> Objects.equals(it, symbol));
                                })
                                .map(it -> toLocation(uri, it));
                    }
            ).toList();

            logger.info("Found {} references for {} ms", result.size(), Duration.between(searchStart, Instant.now()).toMillis());
            return result;
        })).orElseGet(ReferencesProvider::empty);
    }


    public static CompletableFuture<List<? extends Location>> empty() {
        return CompletableFuture.completedFuture(null);
    }

    private static Location toLocation(String uri, ParseTree cst) {
        Location location = new Location();
        location.setUri(uri);
        location.setRange(Ranges.toLspRange(cst));
        return location;
    }

    private static Collection<CompilationUnit> getSearchingScope(Symbol symbol, CompilationUnit symbolUnit) {
        if (isGloballyAccessibleSymbol(symbol)) {
            return symbolUnit.getEnv().getUnits();
        }

        return Collections.singletonList(symbolUnit);

    }

    private static List<ParseTree> searchPossible(Predicate<TerminalNode> search, ParseTree searchingScope) {
        List<ParseTree> result = new ArrayList<>();
        ParseTreeWalker.DEFAULT.walk(new Listener() {
            @Override
            public void visitTerminal(TerminalNode node) {
                if (search.test(node)) {
                    result.add(node);
                }
            }
        }, searchingScope);
        return result;
    }

    private static Predicate<TerminalNode> getSymbolSearchRule(Symbol symbol) {
        if (symbol instanceof OperatorFunctionSymbol operator) {
            String opName = operator.getName();
            return switch (operator.getOperator()) {
                case INDEX_GET, INDEX_SET ->
                        (node) -> node.getSymbol().getType() == ZenScriptParser.BRACK_OPEN && node.getParent() instanceof ZenScriptParser.MemberIndexExprContext;
                case RANGE ->
                        (node) -> (node.getSymbol().getType() == ZenScriptParser.TO || node.getSymbol().getType() == ZenScriptParser.DOT_DOT) && node.getParent() instanceof ZenScriptParser.IntRangeExprContext;
                case HAS ->
                        (node) -> (node.getSymbol().getType() == ZenScriptParser.HAS || node.getSymbol().getType() == ZenScriptParser.IN) && node.getParent() instanceof ZenScriptParser.BinaryExprContext;
                case COMPARE, EQUALS -> (node) -> node.getParent() instanceof ZenScriptParser.CompareExprContext;
                case MEMBER_GET, MEMBER_SET ->
                        (node) -> (node.getSymbol().getType() == ZenScriptParser.DOT && node.getParent() instanceof ZenScriptParser.MemberAccessExprContext);
                case AS ->
                        (node) -> (node.getSymbol().getType() == ZenScriptParser.AS && node.getParent() instanceof ZenScriptParser.TypeCastExprContext)
                                || (node.getSymbol().getType() == ZenScriptParser.INSTANCEOF && node.getParent() instanceof ZenScriptParser.InstanceOfExprContext);
                case ITERATOR ->
                        (node) -> node.getSymbol().getType() == ZenScriptParser.IN && node.getParent() instanceof ZenScriptParser.ForeachStatementContext;
                case ERROR -> null;
                default -> (node) -> {
                    if (node.getParent() instanceof ZenScriptParser.AssignmentExprContext) {
                        return Objects.equals(node.getSymbol().getText(), opName + "=");
                    }
                    return node.getParent() instanceof ZenScriptParser.ExpressionContext && Objects.equals(node.getSymbol().getText(), opName);
                };
            };
        }

        String symbolName = symbol.getName();
        return (node) -> Objects.equals(node.getSymbol().getText(), symbolName);

    }

    private static boolean isGloballyAccessibleSymbol(Symbol symbol) {
        // parameters and import can never be accessed by other units.
        if (symbol.getKind() == Symbol.Kind.PARAMETER || symbol.getKind() == Symbol.Kind.IMPORT || symbol.getKind() == Symbol.Kind.NONE) {
            return false;
        }

        // classes and packages can never be accessed by other units.
        if (symbol.getKind() == Symbol.Kind.CLASS || symbol.getKind() == Symbol.Kind.PACKAGE) {
            return true;
        }

        if (symbol.getKind() == Symbol.Kind.VARIABLE && symbol instanceof ParseTreeLocatable locatable) {
            ParseTree parent = CSTNodes.findParentOfTypes(locatable.getCst(), ZenScriptParser.ClassDeclarationContext.class, ZenScriptParser.BlockStatementContext.class);
            // variables and functions in classes are accessible by other units.
            if (parent instanceof ZenScriptParser.ClassDeclarationContext) {
                return true;
            }

            // variables in block statement could never be accessible by other units.
            if (parent instanceof ZenScriptParser.TopLevelElementContext) {
                return false;
            }
        }

        return symbol.isGlobal() || symbol.isStatic();
    }

    private static Symbol getSymbolOnCursor(CompilationUnit unit, Position cursor) {
        Deque<ParseTree> cstStack = CSTNodes.getCstStackAtPosition(unit.getParseTree(), cursor);

        for (ParseTree cst : cstStack) {
            if (cst instanceof CompilationUnit) {
                return null;
            }
            Symbol symbol = unit.getSymbol(cst);
            if (symbol != null) {
                return symbol;
            }
            if (unit.getScope(cst) != null) {
                // if found parent scope,stop searching
                break;
            }
        }
        return null;
    }

}
