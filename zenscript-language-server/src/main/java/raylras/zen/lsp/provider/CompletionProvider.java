package raylras.zen.lsp.provider;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import raylras.zen.bracket.BracketHandlerEntry;
import raylras.zen.bracket.BracketHandlerService;
import raylras.zen.lsp.provider.data.Keywords;
import raylras.zen.lsp.provider.data.Snippet;
import raylras.zen.model.CompilationUnit;
import raylras.zen.model.Compilations;
import raylras.zen.model.Visitor;
import raylras.zen.model.parser.ZenScriptParser;
import raylras.zen.model.parser.ZenScriptParser.*;
import raylras.zen.model.resolve.TypeResolver;
import raylras.zen.model.scope.Scope;
import raylras.zen.model.symbol.Executable;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.symbol.SymbolProvider;
import raylras.zen.model.type.Type;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.Position;
import raylras.zen.util.Range;
import raylras.zen.util.Ranges;
import raylras.zen.util.l10n.L10N;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class CompletionProvider {

    private CompletionProvider() {
    }

    public static Optional<Either<List<CompletionItem>, CompletionList>> completion(CompilationUnit unit, CompletionParams params) {
        CompletionVisitor visitor = new CompletionVisitor(unit, params);
        unit.accept(visitor);
        if (visitor.completionList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(Either.forLeft(visitor.completionList));
        }
    }

    private static final class CompletionVisitor extends Visitor<Void> {
        final Position cursor;
        final ParseTree tailing;
        final TerminalNode leading;
        final String text;
        final CompilationUnit unit;
        final List<CompletionItem> completionList = new ArrayList<>();

        CompletionVisitor(CompilationUnit unit, CompletionParams params) {
            this.cursor = Position.of(params.getPosition());
            this.tailing = CSTNodes.getCstAtPosition(unit.getParseTree(), cursor);
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
                completeImports();
                return null;
            }

            // import foo.text|
            //           ^____
            if (containsLeading(ctx.qualifiedName().DOT())) {
                String text = getTextUntilCursor(ctx.qualifiedName());
                completeImports();
                return null;
            }

            // import foo.|bar
            //        ^^^_
            if (containsTailing(ctx.qualifiedName().DOT())) {
                String text = getTextUntilCursor(ctx.qualifiedName());
                completeImports();
                return null;
            }

            // import foo.bar text|
            //            ^^^ ____
            if (!containsTailing(ctx.qualifiedName())) {
                completeKeywords(Keywords.AS);
                return null;
            }

            return null;
        }

        @Override
        public Void visitFormalParameter(FormalParameterContext ctx) {
            // name text|
            // ^^^^ ____
            if (containsLeading(ctx.simpleName())) {
                completeKeywords(Keywords.AS);
                return null;
            }

            // name as text|
            //      ^^ ____
            if (containsLeading(ctx.AS())) {
                completeTypes();
                return null;
            }

            return null;
        }

        @Override
        public Void visitFunctionBody(FunctionBodyContext ctx) {
            // { text| }
            // ^ ____
            if (containsLeading(ctx.BRACE_OPEN())) {
                completeLocalSymbols();
                completeGlobalSymbols();
                completeKeywords(Keywords.STATEMENT);
                return null;
            }

            visitChildren(ctx);
            return null;
        }

        @Override
        public Void visitClassBody(ClassBodyContext ctx) {
            // { } text|
            //   ^ ____
            if (containsLeading(ctx.BRACE_CLOSE())) {
                completeLocalSymbols();
                completeGlobalSymbols();
                completeKeywords(Keywords.STATEMENT);
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
                completeKeywords(Keywords.AS);
                return null;
            }

            // var name; text|
            //         ^ ____
            if (containsLeading(ctx.SEMICOLON())) {
                completeLocalSymbols();
                completeGlobalSymbols();
                completeKeywords(Keywords.STATEMENT);
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
                completeLocalSymbols();
                completeGlobalSymbols();
                completeKeywords(Keywords.STATEMENT);
                return null;
            }

            // { } text|
            //   ^ ____
            if (containsLeading(ctx.BRACE_CLOSE())) {
                completeLocalSymbols();
                completeGlobalSymbols();
                completeKeywords(Keywords.STATEMENT);
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
                completeLocalSymbols();
                completeGlobalSymbols();
                return null;
            }

            // return; text|
            //       ^ ____
            if (containsLeading(ctx.SEMICOLON())) {
                completeLocalSymbols();
                completeGlobalSymbols();
                completeKeywords(Keywords.STATEMENT);
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
                completeLocalSymbols();
                completeGlobalSymbols();
                return null;
            }

            visitChildren(ctx);
            return null;
        }

        @Override
        public Void visitForeachBody(ForeachBodyContext ctx) {
            // { text| }
            // ^ ____
            if (containsLeading(ctx.BRACE_OPEN())) {
                completeLocalSymbols();
                completeGlobalSymbols();
                completeKeywords(Keywords.STATEMENT);
                return null;
            }

            // { } text|
            //   ^ ____
            if (containsLeading(ctx.BRACE_CLOSE())) {
                completeLocalSymbols();
                completeGlobalSymbols();
                completeKeywords(Keywords.STATEMENT);
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
                completeLocalSymbols();
                completeGlobalSymbols();
                return null;
            }

            // while (text|)
            //       ^____
            if (containsLeading(ctx.PAREN_OPEN())) {
                completeLocalSymbols();
                completeGlobalSymbols();
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
                completeLocalSymbols();
                completeGlobalSymbols();
                completeKeywords(Keywords.STATEMENT);
                return null;
            }

            // expr; text|
            //     ^ ____
            if (containsLeading(ctx.SEMICOLON())) {
                completeLocalSymbols();
                completeGlobalSymbols();
                completeKeywords(Keywords.STATEMENT);
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
                completeLocalSymbols();
                completeGlobalSymbols();
            }

            // expr =|
            // ^^^^ _
            if (!(ctx.left instanceof MemberAccessExprContext) && containsLeading(ctx.left)) {
                completeLocalSymbols();
                completeGlobalSymbols();
            }

            visitChildren(ctx);
            return null;
        }

        @Override
        public Void visitBinaryExpr(BinaryExprContext ctx) {
            // expr + text|
            //      ^ ____
            if (containsLeading(ctx.op)) {
                completeLocalSymbols();
                completeGlobalSymbols();
            }
            return null;
        }

        @Override
        public Void visitParensExpr(ParensExprContext ctx) {
            // (text|)
            // ^____
            if (containsLeading(ctx.PAREN_OPEN())) {
                completeLocalSymbols();
                completeGlobalSymbols();
                return null;
            }

            visitChildren(ctx);
            return null;
        }

        @Override
        public Void visitBracketHandlerExpr(BracketHandlerExprContext ctx) {
            completeBracketHandlers();
            return null;
        }

        @Override
        public Void visitUnaryExpr(UnaryExprContext ctx) {
            // !text|
            // ^____
            if (containsLeading(ctx.op)) {
                completeLocalSymbols();
                completeGlobalSymbols();
                return null;
            }

            return null;
        }

        @Override
        public Void visitMemberAccessExpr(MemberAccessExprContext ctx) {
            ExpressionContext expr = ctx.expression();

            // expr.text|
            //     ^____
            if (containsLeading(ctx.DOT())) {
                TypeResolver.getType(expr, unit).ifPresent(type -> {
                    completeMembers(type);
                    completeMemberAccessSnippets(type, ctx);
                });
                return null;
            }

            // expr.|
            // ^^^^_
            if (containsLeading(expr)) {
                TypeResolver.getType(expr, unit).ifPresent(type -> {
                    completeMembers(type);
                    completeMemberAccessSnippets(type, ctx);
                });
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
                completeLocalSymbols();
                completeGlobalSymbols();
                return null;
            }

            // expr(expr,|)
            //          ^
            if (leading instanceof ErrorNode) {
                completeLocalSymbols();
                completeGlobalSymbols();
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
                completeLocalSymbols();
                completeGlobalSymbols();
                return null;
            }

            // expr,|
            // ^^^^_
            if (containsTailing(ctx.COMMA())) {
                completeLocalSymbols();
                completeGlobalSymbols();
                return null;
            }

            visitChildren(ctx);
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

        boolean containsLeading(Token token) {
            return Ranges.contains(token, leading);
        }

        boolean containsLeading(ParseTree cst) {
            return Ranges.contains(cst, leading);
        }

        boolean containsLeading(List<? extends ParseTree> cstList) {
            for (ParseTree cst : cstList) {
                if (Ranges.contains(cst, leading)) {
                    return true;
                }
            }
            return false;
        }

        boolean containsTailing(ParseTree cst) {
            return Ranges.contains(cst, tailing);
        }

        boolean containsTailing(List<? extends ParseTree> cstList) {
            for (ParseTree cst : cstList) {
                if (Ranges.contains(cst, tailing)) {
                    return true;
                }
            }
            return false;
        }

        String getTextUntilCursor(ParseTree cst) {
            Range range = Range.of(cst);
            if (range.start().line() != cursor.line()) {
                return "";
            }
            int length = cursor.column() - range.start().column();
            String text = cst.getText();
            if (length > 0) {
                return text.substring(0, length);
            }
            return "";
        }

        void completeImports() {
            // FIXME: completeImports
            //            PackageTree<ClassType> tree = PackageTree.of(".", unit.getEnv().getClassTypeMap());
            //            tree.complete(text).forEach((key, subTree) -> {
            //                CompletionItem item = new CompletionItem(key);
            //                item.setKind(subTree.hasElement() ? CompletionItemKind.Class : CompletionItemKind.Module);
            //                addToCompletionList(item);
            //            });
        }

        void completeLocalSymbols() {
            Scope scope = Compilations.lookupScope(unit, tailing).orElse(null);
            while (scope != null) {
                scope.getSymbols().stream()
                        .map(this::createCompletionItem)
                        .forEach(this::addToCompletionList);
                scope = scope.getParent();
            }
        }

        void completeGlobalSymbols() {
            unit.getEnv().getGlobals()
                    .map(this::createCompletionItem)
                    .forEach(this::addToCompletionList);
        }

        void completeMembers(Type type) {
            if (type instanceof SymbolProvider provider) {
                provider.withExpands(unit.getEnv()).getSymbols().stream()
                        .filter(this::shouldCreateCompletionItem)
                        .map(this::createCompletionItem)
                        .forEach(this::addToCompletionList);
            }
        }

        void completeTypes() {
            unit.getImports().stream()
                    .map(this::createCompletionItem)
                    .forEach(this::addToCompletionList);
        }

        void completeKeywords(String... keywords) {
            for (String keyword : keywords) {
                addToCompletionList(createCompletionItem(keyword));
            }
        }

        void completeBracketHandlers() {
            BracketHandlerService bracketService = unit.getEnv().getBracketHandlerService();
            bracketService.getEntriesLocal().stream()
                    .map(this::createCompletionItem)
                    .forEach(this::addToCompletionList);
        }

        void completeMemberAccessSnippets(Type type, MemberAccessExprContext ctx) {
            completeSnippet(Snippet.dotFor(type, unit.getEnv(), ctx));
            completeSnippet(Snippet.dotForI(type, unit.getEnv(), ctx));
            completeSnippet(Snippet.dotIfNull(type, ctx));
            completeSnippet(Snippet.dotIfNotNull(type, ctx));
            completeSnippet(Snippet.dotVal(ctx));
            completeSnippet(Snippet.dotVar(ctx));
        }

        void completeSnippet(Snippet snippet) {
            CompletionItem item = snippet.get();
            if (item != null) {
                item.setKind(CompletionItemKind.Snippet);
                addToCompletionList(item);
            }
        }

        boolean shouldCreateCompletionItem(Symbol symbol) {
            return switch (symbol.getKind()) {
                case FUNCTION, VARIABLE, PARAMETER -> true;
                default -> false;
            };
        }

        CompletionItem createCompletionItem(Symbol symbol) {
            CompletionItem item = new CompletionItem(symbol.getName());
            item.setKind(toCompletionKind(symbol));
            item.setLabelDetails(createLabelDetails(symbol));
            if (symbol instanceof Executable executable) {
                item.setInsertTextFormat(InsertTextFormat.Snippet);
                if (executable.getParameterList().isEmpty()) {
                    item.setInsertText(item.getLabel() + "()");
                } else {
                    item.setInsertText(item.getLabel() + "($1)");
                }
            }
            return item;
        }

        CompletionItem createCompletionItem(String keyword) {
            CompletionItem item = new CompletionItem(keyword);
            item.setDetail(L10N.getString("completion.keyword"));
            item.setKind(CompletionItemKind.Keyword);
            return item;
        }

        CompletionItem createCompletionItem(BracketHandlerEntry entry) {
            CompletionItem item = new CompletionItem(entry.getFirst("_id").orElse(null));
            item.setKind(CompletionItemKind.Value);
            CompletionItemLabelDetails labelDetails = new CompletionItemLabelDetails();
            labelDetails.setDescription(entry.getFirst("_name").orElse(""));
            item.setLabelDetails(labelDetails);
            item.setSortText(labelDetails.getDescription());
            return item;
        }

        CompletionItemLabelDetails createLabelDetails(Symbol symbol) {
            if (symbol instanceof Executable executable) {
                CompletionItemLabelDetails labelDetails = new CompletionItemLabelDetails();
                String parameterList = executable.getParameterList().stream()
                        .map(param -> param.getName() + " as " + param.getType().getSimpleTypeName())
                        .collect(Collectors.joining(", ", "(", ")"));
                String returnType = executable.getReturnType().getSimpleTypeName();
                labelDetails.setDetail(parameterList);
                labelDetails.setDescription(returnType);
                return labelDetails;
            } else {
                CompletionItemLabelDetails labelDetails = new CompletionItemLabelDetails();
                String type = symbol.getType().getSimpleTypeName();
                labelDetails.setDescription(type);
                return labelDetails;
            }
        }

        CompletionItemKind toCompletionKind(Symbol symbol) {
            return switch (symbol.getKind()) {
                case IMPORT, CLASS -> CompletionItemKind.Class;
                case FUNCTION -> CompletionItemKind.Function;
                case VARIABLE, PARAMETER -> CompletionItemKind.Variable;
                default -> null;
            };
        }

        void addToCompletionList(CompletionItem item) {
            completionList.add(item);
        }
    }

}
