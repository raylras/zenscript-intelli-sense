package raylras.zen.semantic;

import raylras.zen.cst.ZenScriptLexer;
import raylras.zen.cst.ZenScriptParser;
import raylras.zen.cst.ZenScriptParserBaseListener;
import raylras.zen.semantic.symbol.Symbol;
import raylras.zen.semantic.type.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The second scan, records all types to annotated tree.
 */
public class TypeResolver extends ZenScriptParserBaseListener {

    private final AnnotatedTree annotatedTree;

    public TypeResolver(AnnotatedTree annotatedTree) {
        this.annotatedTree = annotatedTree;
    }

    @Override
    public void exitFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        List<Type> paramTypes = ctx.parameterList().parameter().stream().map(annotatedTree::getTypeOfNode).collect(Collectors.toList());
        Type returnType = annotatedTree.getTypeOfNode(ctx.typeAnnotation());
        if (returnType == null) {
            returnType = PrimitiveType.ANY;
        }
        Type type = new FunctionType(paramTypes, returnType);
        Symbol symbol = annotatedTree.findSymbolOfNode(ctx.IDENTIFIER(), ctx.IDENTIFIER().getText());
        if (symbol != null) {
            symbol.setType(type);
        }
        annotatedTree.bindNodeToType(ctx.IDENTIFIER(), type);
    }

    @Override
    public void exitParameter(ZenScriptParser.ParameterContext ctx) {
        Type type = annotatedTree.getTypeOfNode(ctx.typeAnnotation());
        if (type == null) {
            type = PrimitiveType.ANY;
        }
        Symbol symbol = annotatedTree.findSymbolOfNode(ctx.IDENTIFIER(), ctx.IDENTIFIER().getText());
        if (symbol != null) {
            symbol.setType(type);
        }
        annotatedTree.bindNodeToType(ctx, type);
        annotatedTree.bindNodeToType(ctx.IDENTIFIER(), type);
    }

    @Override
    public void exitDefaultValue(ZenScriptParser.DefaultValueContext ctx) {
        Type type = annotatedTree.getTypeOfNode(ctx.expression());
        if (type == null) {
            type = PrimitiveType.ANY;
        }
        annotatedTree.bindNodeToType(ctx, type);
    }

    @Override
    public void exitTypeAnnotation(ZenScriptParser.TypeAnnotationContext ctx) {
        Type type = annotatedTree.getTypeOfNode(ctx.typeName());
        if (type == null) {
            type = PrimitiveType.ANY;
        }
        annotatedTree.bindNodeToType(ctx, type);
    }

    @Override
    public void exitClassDeclaration(ZenScriptParser.ClassDeclarationContext ctx) {
        annotatedTree.bindNodeToType(ctx.IDENTIFIER(), new ClassType(ctx.IDENTIFIER().getText()));
    }

    @Override
    public void exitFieldDeclaration(ZenScriptParser.FieldDeclarationContext ctx) {
        Type type = annotatedTree.getTypeOfNode(ctx.typeAnnotation());
        if (type == null) {
            type = PrimitiveType.ANY;
        }
        Symbol symbol = annotatedTree.findSymbolOfNode(ctx.IDENTIFIER(), ctx.IDENTIFIER().getText());
        if (symbol != null) {
            symbol.setType(type);
        }
        annotatedTree.bindNodeToType(ctx.IDENTIFIER(), type);

    }

    @Override
    public void exitConstructorDeclaration(ZenScriptParser.ConstructorDeclarationContext ctx) {
        List<Type> paramTypes = ctx.parameterList().parameter().stream().map(annotatedTree::getTypeOfNode).collect(Collectors.toList());
        annotatedTree.bindNodeToType(ctx.ZEN_CONSTRUCTOR(), new FunctionType(paramTypes, PrimitiveType.VOID));
    }

    @Override
    public void exitMethodDeclaration(ZenScriptParser.MethodDeclarationContext ctx) {
        List<Type> paramTypes = ctx.parameterList().parameter().stream().map(annotatedTree::getTypeOfNode).collect(Collectors.toList());
        Type returnType = annotatedTree.getTypeOfNode(ctx.typeAnnotation());
        annotatedTree.bindNodeToType(ctx.IDENTIFIER(), new FunctionType(paramTypes, returnType));
    }

    @Override
    public void exitVariableDeclaration(ZenScriptParser.VariableDeclarationContext ctx) {
        Type type = annotatedTree.getTypeOfNode(ctx.typeAnnotation());
        if (type == null) {
            type = annotatedTree.getTypeOfNode(ctx.initializer());
        }
        if (type == null) {
            type = PrimitiveType.ANY;
        }
        Symbol symbol = annotatedTree.findSymbolOfNode(ctx.IDENTIFIER(), ctx.IDENTIFIER().getText());
        if (symbol != null) {
            symbol.setType(type);
        }
        annotatedTree.bindNodeToType(ctx.IDENTIFIER(), type);
    }

    @Override
    public void exitTypeAssertionExpression(ZenScriptParser.TypeAssertionExpressionContext ctx) {
        Type type = annotatedTree.getTypeOfNode(ctx.typeName());
        if (type == null) {
            type = PrimitiveType.ANY;
        }
        annotatedTree.bindNodeToType(ctx, type);
    }

    @Override
    public void exitInitializer(ZenScriptParser.InitializerContext ctx) {
        Type type = annotatedTree.getTypeOfNode(ctx.expression());
        if (type == null) {
            type = PrimitiveType.ANY;
        }
        annotatedTree.bindNodeToType(ctx, type);
    }

    @Override
    public void exitFunctionExpression(ZenScriptParser.FunctionExpressionContext ctx) {
        List<Type> paramTypes = ctx.parameterList().parameter().stream().map(annotatedTree::getTypeOfNode).collect(Collectors.toList());
        Type returnType = annotatedTree.getTypeOfNode(ctx.typeAnnotation());
        if (returnType == null) {
            returnType = PrimitiveType.ANY;
        }
        Type type = new FunctionType(paramTypes, returnType);
        annotatedTree.bindNodeToType(ctx, type);
    }

    @Override
    public void exitArrayType(ZenScriptParser.ArrayTypeContext ctx) {
        Type elementType = annotatedTree.getTypeOfNode(ctx.BaseType);
        annotatedTree.bindNodeToType(ctx, new ArrayType(elementType));
    }

    @Override
    public void exitFunctionType(ZenScriptParser.FunctionTypeContext ctx) {
        List<Type> paramTypes = ctx.typeList().typeName().stream().map(annotatedTree::getTypeOfNode).collect(Collectors.toList());
        Type returnType = annotatedTree.getTypeOfNode(ctx.ReturnType);
        annotatedTree.bindNodeToType(ctx, new FunctionType(paramTypes, returnType));
    }

    @Override
    public void exitListType(ZenScriptParser.ListTypeContext ctx) {
        Type elementType = annotatedTree.getTypeOfNode(ctx.BaseType);
        annotatedTree.bindNodeToType(ctx, new ListType(elementType));
    }

    @Override
    public void exitPrimitiveType(ZenScriptParser.PrimitiveTypeContext ctx) {
        Type type;
        switch (ctx.getStart().getType()) {
            case ZenScriptLexer.BYTE:
                type = PrimitiveType.BYTE;
                break;
            case ZenScriptLexer.SHORT:
                type = PrimitiveType.SHORT;
                break;
            case ZenScriptLexer.INT:
                type = PrimitiveType.INT;
                break;
            case ZenScriptLexer.LONG:
                type = PrimitiveType.LONG;
                break;
            case ZenScriptLexer.FLOAT:
                type = PrimitiveType.FLOAT;
                break;
            case ZenScriptLexer.DOUBLE:
                type = PrimitiveType.DOUBLE;
                break;
            case ZenScriptLexer.BOOL:
                type = PrimitiveType.BOOL;
                break;
            case ZenScriptLexer.STRING:
                type = PrimitiveType.STRING;
                break;
            case ZenScriptLexer.VOID:
                type = PrimitiveType.VOID;
                break;
            case ZenScriptLexer.ANY:
            default:
                type = PrimitiveType.ANY;
        }
        annotatedTree.bindNodeToType(ctx, type);
    }

    @Override
    public void exitClassType(ZenScriptParser.ClassTypeContext ctx) {
        annotatedTree.bindNodeToType(ctx, new ClassType(ctx.packageName().getText()));
    }

    @Override
    public void exitMapType(ZenScriptParser.MapTypeContext ctx) {
        Type keyType = annotatedTree.getTypeOfNode(ctx.KeyType);
        Type valueType = annotatedTree.getTypeOfNode(ctx.ValueType);
        annotatedTree.bindNodeToType(ctx, new MapType(keyType, valueType));
    }

    @Override
    public void exitLiteral(ZenScriptParser.LiteralContext ctx) {
        Type type;
        switch (ctx.getStart().getType()) {
            case ZenScriptLexer.INT_LITERAL:
                type = PrimitiveType.INT;
                break;
            case ZenScriptLexer.LONG_LITERAL:
                type = PrimitiveType.LONG;
                break;
            case ZenScriptLexer.FLOAT_LITERAL:
                type = PrimitiveType.FLOAT;
                break;
            case ZenScriptLexer.DOUBLE_LITERAL:
                type = PrimitiveType.DOUBLE;
                break;
            case ZenScriptLexer.STRING_LITERAL:
                type = PrimitiveType.STRING;
                break;
            case ZenScriptLexer.BOOL_LITERAL:
                type = PrimitiveType.BOOL;
                break;
            case ZenScriptLexer.NULL_LITERAL:
            default:
                type = PrimitiveType.ANY;
        }
        annotatedTree.bindNodeToType(ctx, type);
    }

}
