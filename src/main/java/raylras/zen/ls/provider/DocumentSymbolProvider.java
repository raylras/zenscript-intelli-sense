package raylras.zen.ls.provider;

import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.CompileUnit;
import raylras.zen.ast.Range;
import raylras.zen.ast.ScriptNode;
import raylras.zen.ast.decl.ConstructorDeclaration;
import raylras.zen.ast.decl.FunctionDeclaration;
import raylras.zen.ast.decl.ZenClassDeclaration;
import raylras.zen.ast.stmt.VariableDeclStatement;
import raylras.zen.ast.visit.BaseVisitor;

import java.util.*;
import java.util.stream.Collectors;

import static raylras.zen.util.PosUtils.toLSPRange;

public class DocumentSymbolProvider {

    private static final class DocumentSymbolVisitor extends BaseVisitor<DocumentSymbol> {

        private final Deque<DocumentSymbol> symbolStack = new ArrayDeque<>();

        public List<DocumentSymbol> getDocumentSymbols(ScriptNode scriptNode) {
            return scriptNode.getChildren().stream()
                    .map(node -> node.accept(this))
                    .filter(Objects::nonNull)
                    .toList();
        }

        @Override
        public DocumentSymbol visit(ConstructorDeclaration ctorDecl) {
            DocumentSymbol symbol = new DocumentSymbol("zenConstructor", SymbolKind.Constructor, toLSPRange(ctorDecl.getRange()), toLSPRange(ctorDecl.getRange()));
            List<DocumentSymbol> children = ctorDecl.getChildren().stream()
                    .map(node -> node.accept(this))
                    .filter(Objects::nonNull)
                    .toList();
            symbol.setChildren(children);
            return symbol;
        }

        @Override
        public DocumentSymbol visit(FunctionDeclaration funcDecl) {
            DocumentSymbol symbol = new DocumentSymbol(funcDecl.getId().getName(), SymbolKind.Function, toModifiedRange(funcDecl.getRange()), toLSPRange(funcDecl.getId().getRange()));
            List<DocumentSymbol> children = funcDecl.getBlock().getStatements().stream()
                    .map(node -> node.accept(this))
                    .filter(Objects::nonNull)
                    .toList();
            symbol.setChildren(children);
            return symbol;
        }

        @Override
        public DocumentSymbol visit(ZenClassDeclaration classDecl) {
            DocumentSymbol symbol = new DocumentSymbol(classDecl.getId().getName(), SymbolKind.Class, toModifiedRange(classDecl.getRange()), toLSPRange(classDecl.getId().getRange()));
            List<DocumentSymbol> children = classDecl.getChildren().stream()
                    .map(node -> node.accept(this))
                    .filter(Objects::nonNull)
                    .toList();
            symbol.setChildren(children);
            return symbol;
        }

        @Override
        public DocumentSymbol visit(VariableDeclStatement varDeclStmt) {
            return new DocumentSymbol(varDeclStmt.getId().getName(), SymbolKind.Variable, toModifiedRange(varDeclStmt.getRange()), toLSPRange(varDeclStmt.getId().getRange()));
        }
    }

    public List<Either<SymbolInformation, DocumentSymbol>> provideDocumentSymbol(@NotNull DocumentSymbolParams params, @NotNull CompileUnit compileUnit) {
        ScriptNode scriptNode = compileUnit.getScriptNode(params.getTextDocument().getUri());
        List<DocumentSymbol> documentSymbols = new DocumentSymbolVisitor().getDocumentSymbols(scriptNode);
        return documentSymbols.stream().map(Either::<SymbolInformation, DocumentSymbol>forRight).toList();
    }

    private static org.eclipse.lsp4j.Range toModifiedRange(Range range) {
        org.eclipse.lsp4j.Range lspRange = toLSPRange(range);
        lspRange.getEnd().setCharacter(lspRange.getEnd().getCharacter() - 1); // ranges that without last token, such as ';' or '}'
        return lspRange;
    }

}
