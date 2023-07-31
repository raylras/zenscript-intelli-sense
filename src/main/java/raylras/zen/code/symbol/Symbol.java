package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.annotation.Annotation;
import raylras.zen.code.resolve.AnnotationResolver;
import raylras.zen.code.resolve.ModifierResolver;
import raylras.zen.code.resolve.DeclaredNameResolver;
import raylras.zen.code.type.Type;

import java.util.List;
import java.util.Optional;

public abstract class Symbol {

    protected final ParseTree cst;
    protected final CompilationUnit unit;

    public Symbol(ParseTree cst, CompilationUnit unit) {
        this.unit = unit;
        this.cst = cst;
    }

    public abstract Type getType();

    public abstract Kind getKind();

    public abstract String getQualifiedName();

    public String getSimpleName() {
        return DeclaredNameResolver.getDeclaredName(cst);
    }

    public Modifier getModifier() {
        return ModifierResolver.getModifier(cst);
    }

    public boolean isModifiedBy(Modifier modifier) {
        return modifier == getModifier();
    }

    public Optional<Annotation> getDeclaredAnnotation(String header) {
        return getDeclaredAnnotations().stream()
                .filter(anno -> anno.getHeader().equals(header))
                .findFirst();
    }

    public List<Annotation> getDeclaredAnnotations() {
        return AnnotationResolver.getAnnotations(cst, unit.getTokenStream());
    }

    public ParseTree getCst() {
        return cst;
    }

    public CompilationUnit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return getSimpleName();
    }

    public enum Kind {
        IMPORT, CLASS, VARIABLE, FUNCTION, BUILT_IN, NONE
    }

    public enum Modifier {
        VAR, VAL, STATIC, GLOBAL, EXPAND, NONE
    }

}
