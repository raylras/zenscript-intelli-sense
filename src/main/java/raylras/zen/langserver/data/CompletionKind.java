package raylras.zen.langserver.data;

public enum CompletionKind {
    // 普通标识符
    IDENTIFIER,
    // import 语句
    IMPORT,
    // 成员访问，即 MemberAccessExpr 或者 ArrayIndexExpr
    MEMBER_ACCESS,
    // 尖括号表达式
    BRACKET_HANDLER,
    // 默认
    DEFAULT,
    // 不补全
    NONE,
}
