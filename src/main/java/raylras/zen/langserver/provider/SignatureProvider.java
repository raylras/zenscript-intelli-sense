package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.lsp4j.*;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.VariableSymbol;
import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.resolve.ExpressionTypeResolver;
import raylras.zen.langserver.search.FunctionInvocationResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.FunctionSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.Type;
import raylras.zen.util.*;
import raylras.zen.util.Range;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class SignatureProvider {

    public static final SignatureHelp NOT_SUPPORTED = new SignatureHelp(Collections.emptyList(), -1, -1);
    private final CompilationUnit unit;

    public SignatureProvider(CompilationUnit unit) {
        this.unit = unit;
    }

    public static SignatureHelp signatureHelp(CompilationUnit unit, SignatureHelpParams params) {
        Range cursorPos = Ranges.from(params.getPosition());
        ZenScriptParser.CallExprContext callExpr = new FunctionInvocationResolver(cursorPos).resolve(unit.getParseTree());
        return new SignatureProvider(unit).signatureHelp(callExpr, cursorPos);
    }


    public SignatureHelp signatureHelp(ZenScriptParser.CallExprContext callExpr, Range cursorPos) {
        if (callExpr == null) {
            return NOT_SUPPORTED;
        }
        ZenScriptParser.ExpressionContext invoke = callExpr.Left;
        List<FunctionSymbol> overloads = new ArrayList<>();
        Symbol fallback = methodOverloads(invoke, overloads);
        List<SignatureInformation> signatures = new ArrayList<>();
        if (!overloads.isEmpty()) {
            for (FunctionSymbol symbol : overloads) {
                SignatureInformation info = makeSignatureInformation(symbol);
//            addSourceInfo(task, method, info);
                signatures.add(info);
            }
        } else {
            SignatureInformation info = makeSignatureInformation(fallback);
            if (info != null) {
                signatures.add(info);
            }
        }


        int activeSignature = 0;
        if (!overloads.isEmpty()) {
            activeSignature = findActiveSignature(overloads, callExpr);
        }
        int activeParameter = findActiveParameter(callExpr.COMMA(), cursorPos);
        return new SignatureHelp(signatures, activeSignature, activeParameter);

    }


    public Symbol methodOverloads(ZenScriptParser.ExpressionContext invoke, List<FunctionSymbol> output) {

        if (invoke instanceof ZenScriptParser.LocalAccessExprContext) {
            return simpleMethodOverloads((ZenScriptParser.LocalAccessExprContext) invoke, output);
        }

        if (invoke instanceof ZenScriptParser.MemberAccessExprContext) {
            ZenScriptParser.MemberAccessExprContext memberAccessExpr = (ZenScriptParser.MemberAccessExprContext) invoke;
            return memberMethodOverloads(memberAccessExpr.Left, memberAccessExpr.simpleName().getText(), output);

        }

        throw new IllegalArgumentException(invoke.toString());


    }

    private Symbol memberMethodOverloads(ZenScriptParser.ExpressionContext qualifierExpression, String name, List<FunctionSymbol> output) {

        Tuple<Boolean, Type> qualifierType = MemberUtils.resolveQualifierTarget(unit, qualifierExpression);

        if (!TypeUtils.isValidType(qualifierType.second)) {
            return null;
        }
        AtomicReference<Symbol> fallbackSymbol = new AtomicReference<>();

        MemberUtils.iterateMembers(unit.environment(), qualifierType.second, qualifierType.first, member -> {

            if (!member.getName().equals(name)) return;
            if (member.getKind().isFunction()) {
                output.add((FunctionSymbol) member);

            } else if (member.getKind().isVariable()) {
                fallbackSymbol.set(member);
            }
        });

        return fallbackSymbol.get();

    }

    private Symbol simpleMethodOverloads(ZenScriptParser.LocalAccessExprContext expr, List<FunctionSymbol> output) {
        String methodName = expr.simpleName().getText();
        Scope localScope = unit.lookupScope(expr);
        List<FunctionSymbol> result = unit.lookupLocalSymbols(FunctionSymbol.class, localScope,
            it -> Objects.equals(methodName, it.getName()));

        if (!result.isEmpty()) {
            output.addAll(result);
            return null;
        }

        Symbol symbol = unit.lookupSymbol(Symbol.class, localScope, methodName, true);
        if (symbol instanceof ClassSymbol) {

            output.addAll(((ClassSymbol) symbol).getConstructors());
            return null;
        }

        if (symbol instanceof FunctionSymbol) {
            output.add((FunctionSymbol) symbol);
            return null;
        }

        if (symbol instanceof VariableSymbol) {
            return symbol;
        }
        return null;
    }


    private SignatureInformation makeSignatureInformation(String name, FunctionType type) {
        SignatureInformation info = new SignatureInformation();
        List<ParameterInformation> params = makeSignatureParameters(type);
        info.setParameters(params);
        info.setLabel(makeFunctionLabel(params, name));
        return info;
    }


    private SignatureInformation makeSignatureInformation(Symbol symbol) {
        if (symbol instanceof FunctionSymbol) {
            String name = symbol.getName();
            if (((FunctionSymbol) symbol).isConstructor()) {
                ZenScriptParser.ClassDeclarationContext clazz = (ZenScriptParser.ClassDeclarationContext) symbol.getOwner().getParent();
                String className = clazz.qualifiedName().getText();
                name = StringUtils.getSimpleName(className);
            }
            return makeSignatureInformation(name, (FunctionType) symbol.getType());
        }

        if (symbol instanceof VariableSymbol) {
            Type symbolType = symbol.getType();
            FunctionType functionType = TypeUtils.extractFunctionType(symbolType);
            return makeSignatureInformation(symbol.getName(), functionType);
        }

        return null;
    }

    private List<ParameterInformation> makeSignatureParameters(FunctionType method) {
        List<ParameterInformation> list = new ArrayList<>();
        for (Type p : method.paramTypes) {
            ParameterInformation info = new ParameterInformation();
            info.setLabel(p.toString());
            list.add(info);
        }
        return list;
    }

    private String makeFunctionLabel(List<ParameterInformation> parameterInfos, String name) {
        StringJoiner join = new StringJoiner(", ");
        for (ParameterInformation p : parameterInfos) {
            join.add(p.getLabel().getLeft());
        }
        return name + "(" + join + ")";
    }

    private int findActiveParameter(List<TerminalNode> commas, Range cursorPos) {
        for (int i = 0; i < commas.size(); i++) {
            Token comma = commas.get(i).getSymbol();

            int commaLine = comma.getLine() - 1;
            int commaColumn = comma.getCharPositionInLine() + 1;

            // if cursor is before comma

            if (cursorPos.startLine < commaLine || (cursorPos.startLine == commaLine && cursorPos.startColumn <= commaColumn)) {
                return i;
            }

        }
        return commas.size();
    }

    private int findActiveSignature(List<FunctionSymbol> overloads, ZenScriptParser.CallExprContext callExpr) {

        List<Type> argumentTypes = TypeUtils.getArgumentTypes(new ExpressionTypeResolver(unit), callExpr);

        int index = MemberUtils.selectFunction(unit.environment().typeService(), overloads, argumentTypes, true);

        if (index >= 0) {
            return index;
        }
        return 0;
    }
}
