package raylras.zen.langserver.provider;

import org.eclipse.lsp4j.*;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.data.Declarator;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.resolve.ExpressionSymbolResolver;
import raylras.zen.code.resolve.ExpressionTypeResolver;
import raylras.zen.code.resolve.FunctionInvocationResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.FunctionSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.VariableSymbol;
import raylras.zen.code.symbol.ZenSymbolKind;
import raylras.zen.code.type.Type;
import raylras.zen.util.Range;
import raylras.zen.util.Ranges;

import java.util.*;

public class SignatureProvider {

    public static final SignatureHelp NOT_SUPPORTED = new SignatureHelp(Collections.emptyList(), -1, -1);
    private final CompilationUnit unit;

    public SignatureProvider(CompilationUnit unit) {
        this.unit = unit;
    }

    public static SignatureHelp signatureHelp(CompilationUnit unit, SignatureHelpParams params) {
        Range cursorPos = Ranges.from(params.getPosition());
        ZenScriptParser.CallExprContext callExpr = new FunctionInvocationResolver(cursorPos).resolve(unit.parseTree);
        return new SignatureProvider(unit).signatureHelp(callExpr, cursorPos);
    }


    public SignatureHelp signatureHelp(ZenScriptParser.CallExprContext callExpr, Range cursorPos) {
        if (callExpr == null) {
            return NOT_SUPPORTED;
        }
        ZenScriptParser.ExpressionContext invoke = callExpr.Left;
        List<FunctionSymbol> overloads = methodOverloads(invoke);
        List<SignatureInformation> signatures = new ArrayList<>();
        for (FunctionSymbol function : overloads) {
            SignatureInformation info = makeSignatureInformation(function);
//            addSourceInfo(task, method, info);
            signatures.add(info);
        }


        List<ZenScriptParser.ExpressionContext> allExpressions = callExpr.expression();
        List<ZenScriptParser.ExpressionContext> argumentExpression = allExpressions.subList(1, allExpressions.size());
        int activeSignature = findActiveSignature(overloads, argumentExpression);
        int activeParameter = findActiveParameter(argumentExpression, cursorPos);
        return new SignatureHelp(signatures, activeSignature, activeParameter);

    }


    public List<FunctionSymbol> methodOverloads(ZenScriptParser.ExpressionContext invoke) {

        if (invoke instanceof ZenScriptParser.LocalAccessExprContext) {
            return scopeMethodOverloads((ZenScriptParser.LocalAccessExprContext) invoke);
        }

        if (invoke instanceof ZenScriptParser.MemberAccessExprContext) {
            ZenScriptParser.MemberAccessExprContext memberAccessExpr = (ZenScriptParser.MemberAccessExprContext) invoke;
            return memberMethodOverloads(memberAccessExpr.Left, memberAccessExpr.simpleName().getText());

        }

        // TODO: Other type of method?
        throw new IllegalArgumentException(invoke.toString());


    }

    private List<FunctionSymbol> memberMethodOverloads(ZenScriptParser.ExpressionContext qualifierExpression, String name) {
        Symbol target = new ExpressionSymbolResolver(unit).resolve(qualifierExpression);
        Type type;
        if (target != null) {
            type = target.getType();
        } else {
            type = new ExpressionTypeResolver(unit).resolve(qualifierExpression);
        }
        if (type == null) return Collections.emptyList();

        Symbol typeSymbol = type.lookupSymbol(unit);
        if (typeSymbol == null) {
            return Collections.emptyList();
        }

        boolean isStatic = target != null && target.getKind().isClass();

        List<FunctionSymbol> list = new ArrayList<>();
        for (Symbol member : type.lookupSymbol(unit).getMembers()) {
            if (member.getKind() != ZenSymbolKind.FUNCTION) continue;
            if (!member.getName().equals(name)) continue;
            if (isStatic != member.isDeclaredBy(Declarator.STATIC)) continue;
            list.add((FunctionSymbol) member);
        }
        return list;

    }

    private List<FunctionSymbol> scopeMethodOverloads(ZenScriptParser.LocalAccessExprContext expr) {
        List<FunctionSymbol> result = new ArrayList<>();
        String methodName = expr.simpleName().getText();
        // TODO: merge this code with those in CompletionProvider
        Scope localScope = unit.lookupScope(expr);
        for (Symbol symbol : localScope.symbols) {
            if (symbol.getKind() != ZenSymbolKind.FUNCTION) {
                continue;
            }
            if (!symbol.getName().equals(methodName)) {
                continue;
            }
            result.add((FunctionSymbol) symbol);
        }

        for (Symbol symbol : unit.context.getGlobals()) {
            if (symbol.getKind() != ZenSymbolKind.FUNCTION) {
                continue;
            }
            if (!symbol.getName().equals(methodName)) {
                continue;
            }
            result.add((FunctionSymbol) symbol);
        }


        return result;
    }

    private SignatureInformation makeSignatureInformation(FunctionSymbol method) {
        SignatureInformation info = new SignatureInformation();
        String name = method.getName();
        if (method.isConstructor()) {
            ZenScriptParser.ClassDeclarationContext clazz = (ZenScriptParser.ClassDeclarationContext) method.getOwner().getParent();
            String className = clazz.qualifiedName().getText();
            int dotIndex = className.lastIndexOf(".");
            if (dotIndex >= 0) {
                className = className.substring(dotIndex + 1);
            }
            name = className;
        }
        List<ParameterInformation> params = makeSignatureParameters(method);
        info.setParameters(params);
        info.setLabel(makeFunctionLabel(params, name));
        return info;
    }

    private List<ParameterInformation> makeSignatureParameters(FunctionSymbol method) {
        List<ParameterInformation> list = new ArrayList<>();
        for (VariableSymbol p : method.getParams()) {
            ParameterInformation info = new ParameterInformation();
            info.setLabel(p.getType().toString());
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

    private int findActiveParameter(List<ZenScriptParser.ExpressionContext> arguments, Range cursorPos) {
        for (int i = 0; i < arguments.size(); i++) {
            ZenScriptParser.ExpressionContext expr = arguments.get(i);

            int exprEndLine = expr.stop.getLine() - 1;
            int exprEndColumn = expr.stop.getCharPositionInLine() + expr.stop.getText().length();

            // if cursor is before expr

            if (cursorPos.startLine < exprEndLine || (cursorPos.startLine == exprEndLine && cursorPos.endColumn <= exprEndColumn)) {
                return i;
            }

        }
        return arguments.size();
    }

    private int findActiveSignature(List<FunctionSymbol> overloads, List<ZenScriptParser.ExpressionContext> arguments) {
        //TODO: 应该实际分析一下具体的重载逻辑，这里先简单写个遍历代替

        methodLoop:
        for (int i = 0; i < overloads.size(); i++) {
            FunctionSymbol method = overloads.get(i);

            List<VariableSymbol> parameters = method.getParams();
            // TODO: 可变长方法？
            if (arguments.size() > parameters.size()) {
                continue;
            }

            for (int j = 0; j < arguments.size(); j++) {
                ZenScriptParser.ExpressionContext arguementExpr = arguments.get(j);
                VariableSymbol parameterSymbol = parameters.get(j);

                Type argumentType = findExprType(arguementExpr);
                Type parameterType = parameterSymbol.getType();

                if (!isTypeCompatible(argumentType, parameterType)) {
                    continue methodLoop;
                }
            }

            return i;
        }
        return 0;
    }

    private boolean isTypeCompatible(Type argument, Type parameter) {
        //TODO: 类型兼容处理，需要仔细做，暂时先简单比较相等（
        return Objects.equals(argument, parameter);
    }

    private Type findExprType(ZenScriptParser.ExpressionContext expr) {
        return new ExpressionTypeResolver(unit).resolve(expr);
    }
}
