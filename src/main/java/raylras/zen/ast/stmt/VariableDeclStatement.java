package raylras.zen.ast.stmt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import raylras.zen.ast.BaseNode;
import raylras.zen.ast.LocatableID;
import raylras.zen.ast.Node;
import raylras.zen.ast.Range;
import raylras.zen.ast.decl.Declaration;
import raylras.zen.ast.decl.TypeDeclaration;
import raylras.zen.ast.expr.Expression;
import raylras.zen.ast.type.Type;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class VariableDeclStatement extends BaseNode implements Declaration, Statement, LocatableID {

    @NotNull
    private final String name;
    @Nullable
    private final TypeDeclaration typeDecl;
    @Nullable
    private final Expression expr;

    private Range idRange;
    private boolean isFinal;
    private boolean isStatic;
    private boolean isGlobal;

    public VariableDeclStatement(@NotNull String name, @Nullable TypeDeclaration typeDecl, @Nullable Expression expr) {
        this.name = name;
        this.typeDecl = typeDecl;
        this.expr = expr;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public Optional<TypeDeclaration> getTypeDecl() {
        return Optional.ofNullable(typeDecl);
    }

    public Optional<Expression> getExpr() {
        return Optional.ofNullable(expr);
    }

    @Override
    public Range getIdRange() {
        return idRange;
    }

    public void setIdRange(Range idRange) {
        this.idRange = idRange;
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
    public List<Node> getChildren() {
        if (typeDecl == null && expr == null) {
            return Collections.emptyList();
        }
        List<Node> children = new ArrayList<>(2);
        if (typeDecl != null) children.add(typeDecl);
        if (expr != null) children.add(expr);
        return Collections.unmodifiableList(children);
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
        builder.append(name);
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
