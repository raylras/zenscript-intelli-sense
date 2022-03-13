package raylras.zen.ast;

public class ImportNode extends ASTNode {

    private ClassNode classNode;
    private ImportNode.Alias alias;

    public ImportNode(ClassNode classNode) {
        this.classNode = classNode;
    }

    public ClassNode getClassNode() {
        return classNode;
    }

    public void setClassNode(ClassNode classNode) {
        this.classNode = classNode;
    }

    public ImportNode.Alias getAlias() {
        return alias;
    }

    public ImportNode.Alias setAlias(String name) {
        ImportNode.Alias alias = new Alias(name);
        this.alias = alias;
        return alias;
    }

    @Override
    public String toString() {
        if (alias == null) {
            return classNode.getClassName();
        } else {
            return classNode.getClassName() + " as " + alias.name;
        }
    }

    static class Alias extends ASTNode {
        private final String name;

        public Alias(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }


}
