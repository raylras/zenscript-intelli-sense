package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.misc.Predicate;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.ReferenceParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Listener;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.resolve.SymbolResolver;
import raylras.zen.code.symbol.ImportSymbol;
import raylras.zen.code.symbol.OperatorFunctionSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.Position;
import raylras.zen.util.Ranges;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class ReferencesProvider {
    private static final Logger logger = LoggerFactory.getLogger(ReferencesProvider.class);


    /**
     * find references has four steps:
     * 1. get a symbol under cursor
     * 2. get a searching rule related to the symbol ( for most symbols, it matches name, but for some special symbols like operators are different)
     * 3. search the terminal node at all documents
     * 4. get the cst at every searched node, and resolve its symbol, filtering those containing the current symbol.
     */
    public static List<Location> references(CompilationUnit unit, ReferenceParams params) {
        Position cursor = Position.of(params.getPosition());
        Symbol symbol = getSymbolOnCursor(unit, cursor);
        if (symbol == null) {
            logger.warn("Could not get symbol at ({}, {}), skipping find usages", params.getPosition().getLine(), params.getPosition().getCharacter());
            return Collections.emptyList();
        }
        logger.info("Finding usages for {}", symbol.getName());

        Predicate<TerminalNode> search = getSymbolSearchRule(symbol);

        if (search == null) {
            return Collections.emptyList();
        }
        Instant searchStart = Instant.now();

        boolean isLocalSymbol = isLocalSymbol(symbol);
        Map<Path, List<ParseTree>> possibleRange = searchPossible(unit, search, isLocalSymbol);
        Instant filterStart = Instant.now();
        logger.info("Found {} possible usages for {} ms, filtering", possibleRange.size(), Duration.between(searchStart, filterStart).toMillis());
        Map<Path, List<ParseTree>> usages = filterActual(unit.getEnv(), possibleRange, symbol);
        logger.info("Found {} usages for {} ms", usages.size(), Duration.between(filterStart, Instant.now()).toMillis());
        return usages.entrySet().stream().flatMap(pair -> {
            String uri = pair.getKey().toUri().toString();
            return pair.getValue().stream()
                    .map(it -> {
                        Location location = new Location();
                        location.setUri(uri);
                        location.setRange(Ranges.toLspRange(it));
                        return location;
                    });
        }).collect(Collectors.toList());

    }

    private static Map<Path, List<ParseTree>> filterActual(CompilationEnvironment env, Map<Path, List<ParseTree>> possibleRanges, Symbol targetSymbol) {

        Map<Path, List<ParseTree>> result = new HashMap<>();

        for (Map.Entry<Path, List<ParseTree>> entry : possibleRanges.entrySet()) {
            List<ParseTree> usages = new ArrayList<>();
            CompilationUnit unit = env.getUnit(entry.getKey());
            if (unit == null) {
                continue;
            }
            filterToActualUsage(unit, entry.getValue(), targetSymbol, usages);

            if (!usages.isEmpty()) {
                result.put(entry.getKey(), usages);
            }
        }

        return result;
    }

    private static Map<Path, List<ParseTree>> searchPossible(CompilationUnit symbolUnit, Predicate<TerminalNode> search, boolean isLocalSymbol) {
        Map<Path, List<ParseTree>> result = new HashMap<>();
        if (isLocalSymbol) {
            doSearchPossible(search, symbolUnit, result);
        } else {
            for (CompilationUnit cu : symbolUnit.getEnv().getUnits()) {
                doSearchPossible(search, cu, result);
            }
        }
        return result;
    }

    private static void doSearchPossible(Predicate<TerminalNode> search, CompilationUnit cu, Map<Path, List<ParseTree>> result) {
        List<ParseTree> nodes = new ArrayList<>();
        ParseTreeWalker.DEFAULT.walk(new Listener() {
            @Override
            public void visitTerminal(TerminalNode node) {
                if (search.test(node)) {
                    nodes.add(node);
                }
            }
        }, cu.getParseTree());

        if (!nodes.isEmpty()) {
            result.put(cu.getPath(), nodes);
        }
    }

    private static void filterToActualUsage(CompilationUnit unit, List<ParseTree> possibleUsage, Symbol targetSymbol, List<ParseTree> result) {
        possibleUsage.stream().filter(cst -> {
            Collection<Symbol> symbols = SymbolResolver.lookupSymbol(cst, unit);
            if (targetSymbol.getKind() == Symbol.Kind.IMPORT) {
                return symbols.stream().anyMatch(it -> {
                    return Objects.equals(it, targetSymbol);
                });
            } else {
                return symbols.stream().anyMatch(it -> {
                    if (it instanceof ImportSymbol importSymbol) {
                        return importSymbol.getTargets().stream().anyMatch(imported -> Objects.equals(imported, targetSymbol));
                    }
                    return Objects.equals(it, targetSymbol);
                });
            }
        }).forEach(result::add);
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

    private static boolean isLocalSymbol(Symbol symbol) {
        if (symbol.getKind() == Symbol.Kind.PARAMETER || symbol.getKind() == Symbol.Kind.IMPORT) {
            return true;
        }


        return symbol.getKind() == Symbol.Kind.VARIABLE && !(symbol.isGlobal() || symbol.isStatic());
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
