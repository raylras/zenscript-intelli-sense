package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.lsp4j.*;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.type.resolve.ExpressionTypeResolver;
import raylras.zen.langserver.search.FunctionInvocationResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.FunctionSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.Type;
import raylras.zen.util.*;
import raylras.zen.util.Range;

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
        int activeParameter = findActiveParameter(callExpr.COMMA() , cursorPos);
        return new SignatureHelp(signatures, activeSignature, activeParameter);

    }


    public List<FunctionSymbol> methodOverloads(ZenScriptParser.ExpressionContext invoke) {

        if (invoke instanceof ZenScriptParser.LocalAccessExprContext) {
            return simpleMethodOverloads((ZenScriptParser.LocalAccessExprContext) invoke);
        }

        if (invoke instanceof ZenScriptParser.MemberAccessExprContext) {
            ZenScriptParser.MemberAccessExprContext memberAccessExpr = (ZenScriptParser.MemberAccessExprContext) invoke;
            return memberMethodOverloads(memberAccessExpr.Left, memberAccessExpr.simpleName().getText());

        }

        // TODO: Other type of method?
        throw new IllegalArgumentException(invoke.toString());


    }

    private List<FunctionSymbol> memberMethodOverloads(ZenScriptParser.ExpressionContext qualifierExpression, String name) {

        Tuple<Boolean, Type> qualifierType = MemberUtils.resolveQualifierTarget(unit, qualifierExpression);

        if (!TypeUtils.isValidType(qualifierType.second)) {
            return Collections.emptyList();
        }
        List<FunctionSymbol> list = new ArrayList<>();

        MemberUtils.iterateMembers(unit.environment(), qualifierType.second, qualifierType.first, member -> {

            if (!member.getKind().isFunction()) return;
            if (!member.getName().equals(name)) return;
            list.add((FunctionSymbol) member);
        });

        return list;

    }

    private List<FunctionSymbol> simpleMethodOverloads(ZenScriptParser.LocalAccessExprContext expr) {
        String methodName = expr.simpleName().getText();
        Scope localScope = unit.lookupScope(expr);
        List<FunctionSymbol> result = unit.lookupLocalSymbols(FunctionSymbol.class, localScope,
            it -> Objects.equals(methodName, it.getName()));

        if (result.isEmpty()) {
            return Collections.singletonList(
                unit.environment().findSymbol(FunctionSymbol.class, methodName)
            );
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
        for (Type p : method.getType().paramTypes) {
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

    private int findActiveSignature(List<FunctionSymbol> overloads, List<ZenScriptParser.ExpressionContext> arguments) {
        //TODO: 应该实际分析一下具体的重载逻辑，这里先简单写个遍历代替

        methodLoop:
        for (int i = 0; i < overloads.size(); i++) {
            FunctionSymbol method = overloads.get(i);

            List<Type> parameterTypes = method.getType().paramTypes;
            // TODO: 可变长方法？
            if (arguments.size() > parameterTypes.size()) {
                continue;
            }

            for (int j = 0; j < arguments.size(); j++) {
                ZenScriptParser.ExpressionContext arguementExpr = arguments.get(j);
                Type parameterType = parameterTypes.get(j);

                Type argumentType = findExprType(arguementExpr);

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
