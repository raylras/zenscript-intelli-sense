package raylras.zen.model.type;

import raylras.zen.model.symbol.*;

import java.util.*;

public class MemberValidator implements Iterable<Symbol> {
    private final Map<String, Symbol> fields = new HashMap<>();
    private final Map<MemberValidator.ExecutableData, Symbol> executables = new HashMap<>();
    private OperatorFunctionSymbol caster = null;

    public void add(Symbol symbol) {
        if (symbol instanceof Executable executable) {
            if (symbol instanceof OperatorFunctionSymbol operator && operator.getOperator() == Operator.AS) {
                addCaster(operator);
            } else {
                addExecutable((Symbol & Executable) executable);
            }
        } else {
            addField(symbol);
        }
    }

    public void addAll(Iterable<? extends Symbol> other) {
        other.forEach(this::add);
    }

    public Collection<Symbol> getMembers() {
        List<Symbol> symbols = new ArrayList<>(fields.size() + executables.size() + 1);
        symbols.addAll(fields.values());
        symbols.addAll(executables.values());
        if (caster != null) {
            symbols.add(caster);
        }
        return List.copyOf(symbols);
    }

    private void addField(Symbol symbol) {
        fields.putIfAbsent(symbol.getName(), symbol);
    }

    private <T extends Symbol & Executable> void addExecutable(T executableSymbol) {
        List<Type> parameterTypes = executableSymbol.getParameterList().stream().map(Symbol::getType).toList();
        executables.putIfAbsent(new MemberValidator.ExecutableData(executableSymbol.getName(), executableSymbol.getKind(), parameterTypes), executableSymbol);
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

    @Override
    public Iterator<Symbol> iterator() {
        return getMembers().iterator();
    }

    private record ExecutableData(String name, Symbol.Kind kind, List<Type> parameters) {}
}
