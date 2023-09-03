package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.MarkupKind;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.resolve.SymbolResolver;
import raylras.zen.code.symbol.*;
import raylras.zen.code.type.ClassType;
import raylras.zen.rpc.RpcClient;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.MapUtils;
import raylras.zen.util.Position;
import raylras.zen.util.Range;

import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HoverProvider {

    public static Hover hover(CompilationUnit unit, HoverParams params) {
        Position cursor = Position.of(params.getPosition());
        Deque<ParseTree> cstStack = CSTNodes.getCstStackAtPosition(unit.getParseTree(), cursor);
        Hover hover = declarationHover(unit, cstStack);
        if (hover == null) {
            hover = bracketHover(unit, cstStack);
        }
        if (hover == null) {
            hover = definationHover(unit, cstStack.getFirst());
        }
        return hover;
    }

    private static Hover declarationHover(CompilationUnit unit, Deque<ParseTree> cstStack) {
        for (ParseTree cst : cstStack) {
            if (unit.getScope(cst) != null) {
                break;
            }
            Symbol symbol = unit.getSymbol(cst);
            if (symbol instanceof Locatable locatable && locatable.getSelectionRange().contains(Range.of(cstStack.getFirst()))) {
                return createSymbolHover(symbol);
            }
        }
        return null;
    }

    private static Hover definationHover(CompilationUnit unit, ParseTree cursorCst) {

        Collection<Symbol> symbols = SymbolResolver.lookupSymbol(cursorCst, unit);

        if (symbols.isEmpty()) {
            return null;
        }
        return createSymbolHover(symbols);
    }

    private static Hover bracketHover(CompilationUnit unit, Deque<ParseTree> cstStack) {
        for (ParseTree cst : cstStack) {
            if (cst instanceof ZenScriptParser.BracketHandlerExprContext bracket) {
                return createBracketHover(bracket.raw().getText(), Range.of(bracket));
            }
            if (cst instanceof ZenScriptParser.StatementContext || cst instanceof ZenScriptParser.InitializerContext || cst instanceof ZenScriptParser.DefaultValueContext) {
                break;
            }
        }
        return null;
    }

    private static Hover createSymbolHover(Collection<Symbol> symbols) {
        if (symbols.isEmpty()) {
            return null;
        }

        if (symbols.size() == 1) {
            return createSymbolHover(symbols.stream().findAny().get());
        }
        StringBuilder builder = new StringBuilder();

        builder.append("Mutiple candidates found: ").append("\n");

        Map<Symbol.Kind, List<Symbol>> groupedSymbols = symbols.stream().collect(Collectors.groupingBy(Symbol::getKind));

        for (Map.Entry<Symbol.Kind, List<Symbol>> entry : groupedSymbols.entrySet()) {
            builder.append('\n').append(entry.getKey()).append(": ").append("\n");
            for (Symbol symbol : entry.getValue()) {
                builder.append("- ").append(symbolIcon(symbol)).append(symbolIdentityString(symbol)).append("\n");
            }
        }

        return toHover(builder.toString());

    }

    private static String symbolIdentityString(Symbol symbol) {
        switch (symbol.getKind()) {
            case PACKAGE -> {
                if (symbol instanceof PackageSymbol packageSymbol) {
                    return packageSymbol.getQualifiedName();
                }
            }
            case VARIABLE, PARAMETER, CLASS -> {
                return symbol.getName();
            }
            case FUNCTION -> {
                if (symbol instanceof Executable executable) {
                    return symbol.getName() + parameterString(executable, true);
                }
                return symbol.getName() + "(...)";
            }
        }
        return "";
    }

    private static String parameterString(Executable executable, boolean isSimple) {
        List<ParameterSymbol> params = executable.getParameterList();
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for (int i = 0; i < params.size(); i++) {
            if (i != 0) {
                builder.append(", ");
            }
            ParameterSymbol parameterSymbol = params.get(i);
            if (!isSimple) {
                if (parameterSymbol.isVararg()) {
                    builder.append("...");
                }
                builder.append(parameterSymbol.getName());
                builder.append(" as ");
            }
            builder.append(parameterSymbol.getType());
        }
        builder.append(")");
        if (!(executable instanceof FunctionSymbol func && "zenConstructor".equals(func.getName()))) {
            builder.append(" as ");
            builder.append(executable.getReturnType().toString());
        }
        return builder.toString();
    }

    private String kindString(Symbol.Kind kind) {
        return switch (kind) {

            case IMPORT, NONE -> null;
            case PACKAGE -> "Package";
            case CLASS -> "Class";
            case VARIABLE, PARAMETER -> "Variable";
            case FUNCTION -> "Function";
        };
    }

    private static Hover createSymbolHover(Symbol symbol) {
        if (symbol instanceof ImportSymbol importSymbol) {
            return createSymbolHover(importSymbol.getTargets());
        }
        if (symbol instanceof PackageSymbol packageSymbol) {
            return createPackageSymbolHover(packageSymbol);
        }
        if (symbol instanceof ClassSymbol classSymbol) {
            return createClassSymbolHover(classSymbol);
        }
        if (symbol instanceof VariableSymbol || symbol instanceof ParameterSymbol) {
            return createVariableSymbolHover(symbol);
        }
        if (symbol instanceof Executable executable) {
            return createExecutableSymbolHover(executable);
        }

        return null;
    }

    private static Hover createExecutableSymbolHover(Executable executable) {
        StringBuilder builder = new StringBuilder();
        if (executable instanceof Locatable locatable && locatable.getCst() != null) {
            ParseTree parent = locatable.getCst().getParent();
            if (parent instanceof ClassSymbol classSymbol) {
                builder.append(symbolIdentityString(classSymbol)).append("\n");
            } else {
                String uri = locatable.getUri();
                // TODO: get package of top level functions
            }
        }

        builder.append("```zenscript").append("\n");
        boolean isCtor = false;
        if (executable instanceof FunctionSymbol functionSymbol) {
            if (functionSymbol.getName().equals("zenConstructor")) {
                builder.append("zenConstructor");
                isCtor = true;
            }
            builder.append("function ").append(functionSymbol.getName());
        } else if (executable instanceof ExpandFunctionSymbol expandFunctionSymbol) {
            builder.append("$expand ").append(expandFunctionSymbol.getOwner()).append("$").append(expandFunctionSymbol.getName());
        } else if (executable instanceof OperatorFunctionSymbol operatorFunctionSymbol) {
            builder.append("operator ").append(operatorFunctionSymbol.getOperator().getLiteral());
        }

        builder.append(parameterString(executable, false));
        builder.append("\n```\n");

        return toHover(builder.toString());
    }

    private static Hover createVariableSymbolHover(Symbol symbol) {
        StringBuilder builder = new StringBuilder();

        switch (symbol.getModifier()) {

            case VAR -> builder.append("var ");
            case VAL -> builder.append("val ");
            case STATIC -> builder.append("static ");
            case GLOBAL -> builder.append("global ");
        }
        if (symbol instanceof ParameterSymbol parameterSymbol && parameterSymbol.isVararg()) {
            builder.append("...");
        }
        builder.append(symbol.getName());
        builder.append(" as ");
        builder.append(symbol.getType());

        if (symbol instanceof Locatable locatable) {
            ParseTree defaultValue = null;
            if (locatable.getCst() instanceof ZenScriptParser.VariableDeclarationContext ctx) {
                defaultValue = ctx.initializer();
            }
            if (locatable.getCst() instanceof ZenScriptParser.FormalParameterContext ctx) {
                defaultValue = ctx.defaultValue();
            }
            if (defaultValue != null) {
                String defaultValueStr = defaultValue.getText();
                if (defaultValueStr.length() > 20) {
                    defaultValueStr = defaultValueStr.substring(0, 18) + "...";
                }
                builder.append(" = ");
                builder.append(defaultValueStr);
            }
        }

        return toHover(toCodeBlock(builder.toString()));
    }

    private static Hover createClassSymbolHover(ClassSymbol classSymbol) {
        StringBuilder builder = new StringBuilder();
        String qualifiedName = classSymbol.getQualifiedName();
        String packageName = qualifiedName.substring(0, qualifiedName.lastIndexOf("."));

        builder.append(packageName).append("\n");

        builder.append("```zenscript").append("\n");
        builder.append("zenClass ").append(classSymbol.getName());
        List<ClassType> interfaces = classSymbol.getInterfaces();
        for (int i = 0; i < interfaces.size(); i++) {
            if (i == 0) {
                builder.append(" extends ");
            } else {
                builder.append(", ");
            }
            builder.append(interfaces.get(i).getSymbol().getName());
        }
        builder.append(" {...}");
        builder.append("\n```\n");

        return toHover(builder.toString());
    }

    private static Hover createPackageSymbolHover(PackageSymbol packageSymbol) {
        StringBuilder builder = new StringBuilder();
        builder.append(packageSymbol.getQualifiedName()).append("\n");
        builder.append("### Package Classes: \n");
        for (Symbol symbol : packageSymbol.getSymbols()) {
            if (symbol.getKind() == Symbol.Kind.CLASS) {
                builder.append("- ").append(symbolIcon(symbol)).append(symbolIdentityString(symbol)).append("\n");
            }
        }
        builder.append("### Sub Pakcages: \n");
        for (Symbol symbol : packageSymbol.getSymbols()) {
            if (symbol.getKind() == Symbol.Kind.PACKAGE) {
                builder.append("- ").append(symbolIcon(symbol)).append(symbolIdentityString(symbol)).append("\n");
            }
        }

        return toHover(builder.toString());
    }


    private static Hover createBracketHover(String text, Range range) {
        Map<String, String> properties = RpcClient.queryBracketHandler(text);
        StringBuilder builder = new StringBuilder();
        MapUtils.ifPresent(properties, "name", name -> {
            builder.append("#### ");
            builder.append(name);
            builder.append("\n\n");
        });
        MapUtils.ifPresent(properties, "icon", icon -> {
            String img = "![img](data:image/png;base64," + icon + ")";
            builder.append(img);
            builder.append("\n\n");
        });
        Hover hover = toHover(builder.toString());
        hover.setRange(range.toLspRange());
        return hover;
    }

    private static Hover toHover(String text) {
        return new Hover(new MarkupContent(MarkupKind.MARKDOWN, text));
    }

    private static String toCodeBlock(String text) {
        return String.format("```zenscript\n%s\n```\n", text);
    }


    private static String symbolIcon(Symbol symbol) {
        // TODO: replace this to actual icon later
        return switch (symbol.getKind()) {
            case IMPORT, NONE -> "";
            case PACKAGE -> "[M]";
            case CLASS -> "[C]";
            case VARIABLE -> "[V]";
            case PARAMETER -> "[P]";
            case FUNCTION -> "[F]";
        };
    }

}
