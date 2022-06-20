package raylras.zen.lsp.provider;

import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
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
                    .collect(Collectors.toList());
        }

        @Override
        public DocumentSymbol visit(ConstructorDeclaration ctorDecl) {
            DocumentSymbol symbol = new DocumentSymbol("zenConstructor", SymbolKind.Constructor, toLSPRange(ctorDecl.getRange()), toLSPRange(ctorDecl.getRange()));
            List<DocumentSymbol> children = ctorDecl.getChildren().stream()
                    .map(node -> node.accept(this))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            symbol.setChildren(children);
            return symbol;
        }

        @Override
        public DocumentSymbol visit(FunctionDeclaration funcDecl) {
            DocumentSymbol symbol = new DocumentSymbol(funcDecl.getName(), SymbolKind.Function, toModifiedRange(funcDecl.getRange()), toLSPRange(funcDecl.getIdRange()));
            List<DocumentSymbol> children = funcDecl.getBlock().getStatements().stream()
                    .map(node -> node.accept(this))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            symbol.setChildren(children);
            return symbol;
        }

        @Override
        public DocumentSymbol visit(ZenClassDeclaration classDecl) {
            DocumentSymbol symbol = new DocumentSymbol(classDecl.getName(), SymbolKind.Class, toModifiedRange(classDecl.getRange()), toLSPRange(classDecl.getIdRange()));
            List<DocumentSymbol> children = classDecl.getChildren().stream()
                    .map(node -> node.accept(this))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            symbol.setChildren(children);
            return symbol;
        }

        @Override
        public DocumentSymbol visit(VariableDeclStatement varDeclStmt) {
            return new DocumentSymbol(varDeclStmt.getName(), SymbolKind.Variable, toModifiedRange(varDeclStmt.getRange()), toLSPRange(varDeclStmt.getIdRange()));
        }
    }

    public List<Either<SymbolInformation, DocumentSymbol>> provideDocumentSymbol(ScriptNode scriptNode) {
        if (scriptNode == null) return Collections.emptyList();
        List<DocumentSymbol> documentSymbols = new DocumentSymbolVisitor().getDocumentSymbols(scriptNode);
        return documentSymbols.stream().map(Either::<SymbolInformation, DocumentSymbol>forRight).collect(Collectors.toList());
    }

    private static org.eclipse.lsp4j.Range toModifiedRange(Range range) {
        org.eclipse.lsp4j.Range lspRange = toLSPRange(range);
        lspRange.getEnd().setCharacter(lspRange.getEnd().getCharacter() - 1); // columns that without last token, such as ';' or '}'
        return lspRange;
    }

}
