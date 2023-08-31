package raylras.zen.code;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.code.parser.ZenScriptParserBaseVisitor;

public abstract class NonRecursionVisitor<T> extends ZenScriptParserBaseVisitor<T> {

    public T visitDefault(ParseTree tree) {
        return defaultResult();
    }

    @Override
    public final T visitChildren(RuleNode node) {
        return visitDefault(node);
    }
    @Override
    public T visitTerminal(TerminalNode node) {
        return visitDefault(node);
    }

    @Override
    public T visitErrorNode(ErrorNode node) {
        return visitDefault(node);
    }
    @Override
    protected  final T aggregateResult(T aggregate, T nextResult) {
        throw new IllegalStateException("do not support");
    }

    @Override
    protected final boolean shouldVisitNextChild(RuleNode node, T currentResult) {
        throw new IllegalStateException("do not support");
    }
}
