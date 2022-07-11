package raylras.zen.ast.stmt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.HasID;
import raylras.zen.ast.Node;
import raylras.zen.ast.decl.Declaration;
import raylras.zen.ast.IDNode;
import raylras.zen.ast.decl.TypeDeclaration;
import raylras.zen.ast.expr.Expression;
import raylras.zen.ast.type.Type;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.*;
import java.util.stream.Stream;

public final class VariableDeclStatement extends BaseNode implements Declaration, Statement, HasID {

    @NotNull
    private final IDNode id;
    @Nullable
    private final TypeDeclaration typeDecl;
    @Nullable
    private final Expression expr;
    private boolean isFinal;
    private boolean isStatic;
    private boolean isGlobal;

    public VariableDeclStatement(@NotNull IDNode id, @Nullable TypeDeclaration typeDecl, @Nullable Expression expr) {
        this.id = id;
        this.typeDecl = typeDecl;
        this.expr = expr;
    }

    @NotNull
    @Override
    public IDNode getId() {
        return id;
    }

    public Optional<TypeDeclaration> getTypeDecl() {
        return Optional.ofNullable(typeDecl);
    }

    public Optional<Expression> getExpr() {
        return Optional.ofNullable(expr);
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return Stream.of(id, typeDecl, expr).filter(Objects::nonNull).toList();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (isGlobal) {
            builder.append("global ");
        } else if (isStatic) {
            builder.append("static ");
        } else if (isFinal) {
            builder.append("val ");
        } else {
            builder.append("var ");
        }
        builder.append(id);
        Type type = getType();
        if (type != null) {
            builder.append(" as ").append(type);
        }
        if (expr != null) {
            builder.append(" = ").append(expr);
        }
        builder.append(";");
        return builder.toString();
    }

}
