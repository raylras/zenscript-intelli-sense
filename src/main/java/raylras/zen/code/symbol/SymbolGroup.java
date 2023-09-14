package raylras.zen.code.symbol;

import raylras.zen.code.type.IntersectionType;
import raylras.zen.code.type.Type;

import java.util.*;
import java.util.stream.Stream;

public class SymbolGroup implements Iterable<Symbol> {
    private final Map<String, Symbol> fields = new HashMap<>();
    private final Map<ExecutableWrapper, Symbol> executables = new HashMap<>();
    private OperatorFunctionSymbol caster = null;

    public static SymbolGroup of(Iterable<Symbol> symbols) {
        SymbolGroup group = new SymbolGroup();
        group.addAll(symbols);
        return group;
    }

    public List<Symbol> toList() {
        List<Symbol> symbols = new ArrayList<>(fields.size() + executables.size() + 1);
        symbols.addAll(fields.values());
        symbols.addAll(executables.values());
        if (caster != null) {
            symbols.add(caster);
        }
        return List.copyOf(symbols);
    }

    public SymbolGroup add(Symbol symbol) {
        if (symbol instanceof Executable executable) {
            if (symbol instanceof OperatorFunctionSymbol operator && operator.getOperator() == Operator.AS) {
                addCaster(operator);
            } else {
                addExecutable(symbol, executable);
            }
        } else {
            addField(symbol);
        }
        return this;
    }

    public SymbolGroup addAll(Iterable<Symbol> other) {
        other.forEach(this::add);
        return this;
    }

    public Stream<Symbol> stream() {
        return toList().stream();
    }

    @Override
    public Iterator<Symbol> iterator() {
        return toList().iterator();
    }

    public int size() {
        return toList().size();
    }

    public boolean isEmpty() {
        return toList().isEmpty();
    }

    private void addField(Symbol symbol) {
        fields.putIfAbsent(symbol.getName(), symbol);
    }

    private void addExecutable(Symbol symbol, Executable executable) {
        List<Type> parameterTypes = executable.getParameterList().stream().map(Symbol::getType).toList();
        executables.putIfAbsent(new ExecutableWrapper(symbol.getName(), parameterTypes, symbol.getKind()), symbol);
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

    private record ExecutableWrapper(String name, List<Type> parameters, Symbol.Kind kind) {
    }
}
