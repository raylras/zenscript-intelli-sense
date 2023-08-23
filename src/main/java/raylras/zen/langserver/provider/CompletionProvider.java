package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionItemLabelDetails;
import org.eclipse.lsp4j.CompletionParams;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.code.resolve.TypeResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.symbol.FunctionSymbol;
import raylras.zen.code.symbol.ImportSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.ClassType;
import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.Type;
import raylras.zen.langserver.provider.data.Keywords;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.PackageTree;
import raylras.zen.util.Range;
import raylras.zen.util.Ranges;
import raylras.zen.util.l10n.L10N;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class CompletionProvider {

    private CompletionProvider() {
    }

    public static List<CompletionItem> completion(CompilationUnit unit, CompletionParams params) {
        CompletionVisitor visitor = new CompletionVisitor(unit, params);
        unit.accept(visitor);
        return visitor.getCompletionList();
    }

    private static final class CompletionVisitor extends Visitor<Void> {
        private final Range cursor;
        private final ParseTree tailing;
        private final TerminalNode leading;
        private final String text;
        private final CompilationUnit unit;
        private final List<CompletionItem> completionList = new ArrayList<>();

        private CompletionVisitor(CompilationUnit unit, CompletionParams params) {
            this.cursor = Ranges.of(params.getPosition());
            this.tailing = CSTNodes.getCstAtLineAndColumn(unit.getParseTree(), cursor.startLine, cursor.startColumn);
            this.leading = CSTNodes.getPrevTerminal(unit.getTokenStream(), tailing);
            this.text = tailing.getText();
            this.unit = unit;
        }

        /*
            | represents the cursor
            ^ represents the leading cst node
            _ represents the tailing cst node
         */

        @Override
        public Void visitImportDeclaration(ZenScriptParser.ImportDeclarationContext ctx) {
            // import text|
            // ^^^^^^ ____
            if (containsLeading(ctx.IMPORT())) {
                completeImports(text);
                return null;
            }

            // import foo.text|
            //           ^___
            if (containsLeading(ctx.qualifiedName().DOT())) {
                String text = getTextUntilCursor(ctx.qualifiedName());
                completeImports(text);
                return null;
            }

            // import foo.|bar
            //        ^^^_
            if (containsTailing(ctx.qualifiedName().DOT())) {
                String text = getTextUntilCursor(ctx.qualifiedName());
                completeImports(text);
                return null;
            }

            // import foo.bar text|
            //            ^^^ ____
            if (!containsTailing(ctx.qualifiedName())) {
                completeKeywords(text, Keywords.AS);
                return null;
            }

            return null;
        }

        @Override
        public Void visitFormalParameter(FormalParameterContext ctx) {
            // name text|
            // ^^^^ ____
            if (containsLeading(ctx.simpleName())) {
                completeKeywords(text, Keywords.AS);
                return null;
            }

            // name as text|
            //      ^^ ____
            if (containsLeading(ctx.AS())) {
                completeTypeSymbols(text);
                return null;
            }

            return null;
        }

        @Override
        public Void visitFunctionBody(FunctionBodyContext ctx) {
            // { text| }
            // ^ ____
            if (containsLeading(ctx.BRACE_OPEN())) {
                completeLocalSymbols(text);
                completeGlobalSymbols(text);
                completeKeywords(text, Keywords.STATEMENT);
                return null;
            }

            visitChildren(ctx);
            return null;
        }

        @Override
        public Void visitVariableDeclaration(VariableDeclarationContext ctx) {
            // var name text|
            //     ^^^^ ____
            if (containsLeading(ctx.simpleName())) {
                completeKeywords(text, Keywords.AS);
                return null;
            }

            // var name; text|
            //         ^ ____
            if (containsLeading(ctx.SEMICOLON())) {
                completeLocalSymbols(text);
                completeGlobalSymbols(text);
                completeKeywords(text, Keywords.STATEMENT);
                return null;
            }

            visitChildren(ctx);
            return null;
        }

        @Override
        public Void visitBlockStatement(BlockStatementContext ctx) {
            // { text| }
            // ^ ____
            if (containsLeading(ctx.BRACE_OPEN())) {
                completeLocalSymbols(text);
                completeGlobalSymbols(text);
                completeKeywords(text, Keywords.STATEMENT);
                return null;
            }

            // { } text|
            //   ^ ____
            if (containsLeading(ctx.BRACE_CLOSE())) {
                completeLocalSymbols(text);
                completeGlobalSymbols(text);
                completeKeywords(text, Keywords.STATEMENT);
                return null;
            }

            visitChildren(ctx);
            return null;
        }

        @Override
        public Void visitReturnStatement(ReturnStatementContext ctx) {
            // return text|
            // ^^^^^^ ____
            if (containsLeading(ctx.RETURN())) {
                completeLocalSymbols(text);
                completeGlobalSymbols(text);
                return null;
            }

            // return; text|
            //       ^ ____
            if (containsLeading(ctx.SEMICOLON())) {
                completeLocalSymbols(text);
                completeGlobalSymbols(text);
                completeKeywords(text, Keywords.STATEMENT);
                return null;
            }

            visitChildren(ctx);
            return null;
        }

        @Override
        public Void visitIfStatement(IfStatementContext ctx) {
            // if text|
            // ^^ ____
            if (containsLeading(ctx.IF())) {
                completeLocalSymbols(text);
                completeGlobalSymbols(text);
                return null;
            }

            visitChildren(ctx);
            return null;
        }


        @Override
        public Void visitWhileStatement(WhileStatementContext ctx) {
            // while (|)
            // ^^^^^ _
            if (containsLeading(ctx.WHILE())) {
                completeLocalSymbols("");
                completeLocalSymbols("");
                return null;
            }

            // while (text|)
            //       ^____
            if (containsLeading(ctx.PAREN_OPEN())) {
                completeLocalSymbols(text);
                completeLocalSymbols(text);
                return null;
            }

            visitChildren(ctx);
            return null;
        }

        @Override
        public Void visitExpressionStatement(ExpressionStatementContext ctx) {
            // text|
            // ____
            if (ctx.expression() instanceof SimpleNameExprContext && containsTailing(ctx.expression())) {
                completeLocalSymbols(text);
                completeGlobalSymbols(text);
                completeKeywords(text, Keywords.STATEMENT);
                return null;
            }

            // expr; text|
            //     ^ ____
            if (containsLeading(ctx.SEMICOLON())) {
                completeLocalSymbols(text);
                completeGlobalSymbols(text);
                completeKeywords(text, Keywords.STATEMENT);
                return null;
            }

            visitChildren(ctx);
            return null;
        }

        @Override
        public Void visitAssignmentExpr(AssignmentExprContext ctx) {
            // expr = text|
            //      ^ ____
            if (containsLeading(ctx.op)) {
                completeLocalSymbols(text);
                completeGlobalSymbols(text);
            }

            // expr =|
            // ^^^^ _
            if (!(ctx.left instanceof MemberAccessExprContext) && containsLeading(ctx.left)) {
                completeLocalSymbols("");
                completeGlobalSymbols("");
            }

            visitChildren(ctx);
            return null;
        }

        @Override
        public Void visitBinaryExpr(BinaryExprContext ctx) {
            // expr + text|
            //      ^ ____
            if (containsLeading(ctx.op)) {
                completeLocalSymbols(text);
                completeGlobalSymbols(text);
            }
            return null;
        }

        @Override
        public Void visitParensExpr(ParensExprContext ctx) {
            // (text|)
            // ^____
            if (containsLeading(ctx.PAREN_OPEN())) {
                completeLocalSymbols(text);
                completeGlobalSymbols(text);
                return null;
            }

            visitChildren(ctx);
            return null;
        }

        @Override
        public Void visitUnaryExpr(UnaryExprContext ctx) {
            // !text|
            // ^____
            if (containsLeading(ctx.op)) {
                completeLocalSymbols(text);
                completeGlobalSymbols(text);
                return null;
            }

            return null;
        }

        @Override
        public Void visitMemberAccessExpr(MemberAccessExprContext ctx) {
            // expr.text|
            //     ^____
            if (containsLeading(ctx.DOT())) {
                Type type = TypeResolver.getType(ctx.expression(), unit);
                completeMemberSymbols(text, type);
                return null;
            }

            // expr.|
            // ^^^^_
            if (containsLeading(ctx.expression())) {
                Type type = TypeResolver.getType(ctx.expression(), unit);
                completeMemberSymbols("", type);
                return null;
            }

            visitChildren(ctx);
            return null;
        }

        @Override
        public Void visitCallExpr(CallExprContext ctx) {
            // expr(text|)
            //     ^____
            if (containsLeading(ctx.PAREN_OPEN())) {
                completeLocalSymbols(text);
                completeGlobalSymbols(text);
                return null;
            }

            // expr(expr,|)
            //          ^
            if (leading instanceof ErrorNode) {
                completeLocalSymbols("");
                completeGlobalSymbols("");
                return null;
            }

            visitChildren(ctx);
            return null;
        }

        @Override
        public Void visitExpressionList(ZenScriptParser.ExpressionListContext ctx) {
            // expr, text|
            //     ^ ____
            if (containsLeading(ctx.COMMA())) {
                completeLocalSymbols(text);
                completeGlobalSymbols(text);
                return null;
            }

            // expr,|
            // ^^^^_
            if (containsTailing(ctx.COMMA())) {
                completeLocalSymbols("");
                completeGlobalSymbols("");
                return null;
            }

            visitChildren(ctx);
            return null;
        }

        @Override
        public Void visitBracketHandlerExpr(BracketHandlerExprContext ctx) {
            unit.getEnv().getBracketHandlerManager().complete(ctx.raw().getText(), completionList);
            return null;
        }

        @Override
        public Void visitChildren(RuleNode node) {
            for (int i = 0; i < node.getChildCount(); i++) {
                ParseTree child = node.getChild(i);
                if (containsLeading(child)) {
                    child.accept(this);
                    break;
                }
                if (containsTailing(child)) {
                    child.accept(this);
                    break;
                }
            }
            return null;
        }

        private boolean containsLeading(Token token) {
            return Ranges.contains(token, leading);
        }

        private boolean containsLeading(ParseTree cst) {
            return Ranges.contains(cst, leading);
        }

        private boolean containsLeading(List<? extends ParseTree> cstList) {
            for (ParseTree cst : cstList) {
                if (Ranges.contains(cst, leading)) {
                    return true;
                }
            }
            return false;
        }

        private boolean containsTailing(ParseTree cst) {
            return Ranges.contains(cst, tailing);
        }

        private boolean containsTailing(List<? extends ParseTree> cstList) {
            for (ParseTree cst : cstList) {
                if (Ranges.contains(cst, tailing)) {
                    return true;
                }
            }
            return false;
        }

        private String getTextUntilCursor(ParseTree cst) {
            Range range = Ranges.of(cst);
            if (range.startLine != cursor.startLine) {
                return "";
            }
            int length = cursor.startColumn - range.startColumn;
            String text = cst.getText();
            if (length > 0) {
                return text.substring(0, length);
            }
            return "";
        }

        private void completeImports(String text) {
            PackageTree<ClassType> tree = PackageTree.of(".", unit.getEnv().getClassTypeMap());
            tree.complete(text).forEach((key, subTree) -> {
                CompletionItem completionItem = new CompletionItem(key);
                completionItem.setKind(subTree.hasElement() ? CompletionItemKind.Class : CompletionItemKind.Module);
                completionList.add(completionItem);
            });
        }

        private void completeLocalSymbols(String text) {
            Scope scope = unit.lookupScope(tailing);
            while (scope != null) {
                for (Symbol symbol : scope.getSymbols()) {
                    if (symbol.getName().startsWith(text)) {
                        addToCompletionList(symbol);
                    }
                }
                scope = scope.getParent();
            }
        }

        private void completeGlobalSymbols(String text) {
            for (Symbol symbol : unit.getEnv().getGlobalSymbols()) {
                if (symbol.getName().startsWith(text)) {
                    addToCompletionList(symbol);
                }
            }
        }

        private void completeMemberSymbols(String text, Type type) {
            for (Symbol member : type.getMembers()) {
                if (member.getName().startsWith(text)) {
                    addToCompletionList(member);
                }
            }
        }

        private void completeTypeSymbols(String text) {
            for (Symbol topLevelSymbol : unit.getTopLevelSymbols()) {
                if (topLevelSymbol instanceof ImportSymbol && topLevelSymbol.getName().startsWith(text)) {
                    addToCompletionList(topLevelSymbol);
                }
            }
        }

        private void completeKeywords(String text, String... keywords) {
            for (String keyword : keywords) {
                if (keyword.startsWith(text)) {
                    addToCompletionList(keyword);
                }
            }
        }

        private void addToCompletionList(Symbol symbol) {
            CompletionItem item = new CompletionItem(symbol.getName());
            item.setKind(toCompletionKind(symbol));
            item.setLabelDetails(getLabelDetails(symbol));
            completionList.add(item);
        }

        /**
         * @deprecated Use {@link #addToCompletionList(Symbol)} instead.
         */
        @Deprecated
        private void addToCompletionList(Symbol symbol, String detail) {
            CompletionItem item = new CompletionItem(symbol.getName());
            item.setKind(toCompletionKind(symbol));
            item.setDetail(detail);
            completionList.add(item);
        }

        private void addToCompletionList(String keyword) {
            CompletionItem item = new CompletionItem(keyword);
            item.setDetail(L10N.getString("completion.keyword"));
            item.setKind(CompletionItemKind.Keyword);
            completionList.add(item);
        }

        private CompletionItemKind toCompletionKind(Symbol symbol) {
            switch (symbol.getKind()) {
                case IMPORT:
                case CLASS:
                    return CompletionItemKind.Class;
                case FUNCTION:
                    return CompletionItemKind.Function;
                case VARIABLE:
                case PARAMETER:
                    return CompletionItemKind.Variable;
                case NONE:
                default:
                    return null;
            }
        }

        private CompletionItemKind toCompletionKind(Type type) {
            if (type instanceof ClassType) {
                return CompletionItemKind.Class;
            }
            if (type instanceof FunctionType) {
                return CompletionItemKind.Function;
            }
            return CompletionItemKind.Variable;
        }

        private CompletionItemLabelDetails getLabelDetails(Symbol symbol) {
            if (symbol.getType() instanceof FunctionType) {
                CompletionItemLabelDetails labelDetails = new CompletionItemLabelDetails();
                String parameterList = ((FunctionSymbol) symbol).getParameterList().stream()
                        .map(param -> param.getName() + " as " + param.getType())
                        .collect(Collectors.joining(", ", "(", ")"));
                String returnType = ((FunctionType) symbol.getType()).getReturnType().toString();
                labelDetails.setDetail(parameterList);
                labelDetails.setDescription(returnType);
                return labelDetails;
            } else {
                CompletionItemLabelDetails labelDetails = new CompletionItemLabelDetails();
                String type = symbol.getType().toString();
                labelDetails.setDescription(type);
                return labelDetails;
            }
        }

        private List<CompletionItem> getCompletionList() {
            return completionList;
        }
    }

}
