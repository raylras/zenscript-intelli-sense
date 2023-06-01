package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Declarator;
import raylras.zen.code.annotation.Annotation;
import raylras.zen.code.resolve.AnnotationResolver;
import raylras.zen.code.resolve.DeclaratorResolver;
import raylras.zen.code.resolve.DeclaredNameResolver;
import raylras.zen.code.type.Type;

import java.util.List;

public abstract class Symbol {

    protected ParseTree owner;
    protected CompilationUnit unit;

    public Symbol(ParseTree owner, CompilationUnit unit) {
        this.owner = owner;
        this.unit = unit;
    }

    public abstract Type getType();

    public abstract Kind getKind();

    public String getDeclaredName() {
        return new DeclaredNameResolver().resolve(owner);
    }

    public Declarator getDeclarator() {
        return new DeclaratorResolver().resolve(owner);
    }

    public boolean isDeclaredBy(Declarator declarator) {
        return declarator == getDeclarator();
    }

    public Annotation getDeclaredAnnotation(String header) {
        return getDeclaredAnnotations().stream()
                .filter(anno -> anno.isAnnotation(header))
                .findFirst()
                .orElse(null);
    }

    public List<Annotation> getDeclaredAnnotations() {
        return new AnnotationResolver(unit).resolve(owner);
    }

    public ParseTree getOwner() {
        return owner;
    }

    public CompilationUnit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return getDeclaredName();
    }

    public enum Kind {
        CLASS, VARIABLE, FUNCTION, NONE
    }

}
