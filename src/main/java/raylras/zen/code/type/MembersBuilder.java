package raylras.zen.code.type;

import raylras.zen.code.symbol.*;

import java.util.*;
import java.util.function.UnaryOperator;

public class MembersBuilder {
    private final Map<String, Symbol> fields = new HashMap<>();
    private final Map<MembersBuilder.ExecutableData, Symbol> executables = new HashMap<>();
    private OperatorFunctionSymbol caster = null;

    public static MembersBuilder of() {
        return new MembersBuilder();
    }

    public List<Symbol> build() {
        List<Symbol> symbols = new ArrayList<>(fields.size() + executables.size() + 1);
        symbols.addAll(fields.values());
        symbols.addAll(executables.values());
        if (caster != null) {
            symbols.add(caster);
        }
        return List.copyOf(symbols);
    }

    public MembersBuilder add(Symbol symbol) {
        if (symbol instanceof Executable executable) {
            if (symbol instanceof OperatorFunctionSymbol operator && operator.getOperator() == Operator.AS) {
                addCaster(operator);
            } else {
                addExecutable((Symbol & Executable) executable);
            }
        } else {
            addField(symbol);
        }
        return this;
    }

    public MembersBuilder addAll(Iterable<Symbol> other) {
        other.forEach(this::add);
        return this;
    }

    public MembersBuilder variable(String name, Type type, Symbol.Modifier modifier) {
        add(SymbolFactory.createVariableSymbol(name, type, modifier));
        return this;
    }

    public MembersBuilder function(String name, Type returnType, UnaryOperator<ParameterBuilder> paramsSupplier) {
        add(SymbolFactory.createFunctionSymbol(name, returnType, paramsSupplier.apply(new ParameterBuilder()).build()));
        return this;
    }

    public MembersBuilder operator(Operator operator, Type returnType, UnaryOperator<ParameterBuilder> paramsSupplier) {
        add(SymbolFactory.createOperatorFunctionSymbol(operator, returnType, paramsSupplier.apply(new ParameterBuilder()).build()));
        return this;
    }

    private void addField(Symbol symbol) {
        fields.putIfAbsent(symbol.getName(), symbol);
    }

    private <T extends Symbol & Executable> void addExecutable(T executableSymbol) {
        List<Type> parameterTypes = executableSymbol.getParameterList().stream().map(Symbol::getType).toList();
        executables.putIfAbsent(new MembersBuilder.ExecutableData(executableSymbol.getName(), parameterTypes, executableSymbol.getKind()), executableSymbol);
    }

    private void addCaster(OperatorFunctionSymbol operatorFunctionSymbol) {
        if (caster == null) {
            caster = operatorFunctionSymbol;
        } else {
            caster = SymbolFactory.createOperatorFunctionSymbol(
                    Operator.AS,
                    new IntersectionType(List.of(caster.getReturnType(), operatorFunctionSymbol.getReturnType())),
                    Collections.emptyList()
            );
        }
    }

    private record ExecutableData(String name, List<Type> parameters, Symbol.Kind kind) {
    }

    public static class ParameterBuilder {
        private final List<ParameterSymbol> parameters = new ArrayList<>();

        public ParameterBuilder parameter(String name, Type type, boolean optional, boolean vararg) {
            parameters.add(SymbolFactory.createParameterSymbol(name, type, optional, vararg));
            return this;
        }

        public ParameterBuilder parameter(String name, Type type) {
            return parameter(name, type, false, false);
        }

        public List<ParameterSymbol> build() {
            return parameters;
        }
    }
}
