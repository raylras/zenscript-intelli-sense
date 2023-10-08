package raylras.zen.dap;


import com.sun.jdi.Location;
import org.eclipse.lsp4j.debug.Breakpoint;
import org.eclipse.lsp4j.debug.SourceBreakpoint;
import raylras.zen.dap.debugserver.DebugAdapterContext;
import raylras.zen.util.Position;

public final class DAPPositions {


    public static void fillDAPBreakpoint(Breakpoint breakpoint, Position position, DebugAdapterContext context) {
        breakpoint.setLine(toDAPLine(position, context));
        if (position.column() >= 0) {
            breakpoint.setColumn(toDAPColumn(position, context));
        }
    }

    public static Position fromDAPSourceBreakpoint(SourceBreakpoint sourceBreakpoint, DebugAdapterContext context) {
        int line = fromDAPLine(sourceBreakpoint.getLine(), context);
        int column = -1;
        if (sourceBreakpoint.getColumn() != null) {
            column = fromDAPColumn(sourceBreakpoint.getColumn(), context);
        }
        return Position.of(line, column);
    }

    public static int fromDAPLine(int dapLine, DebugAdapterContext context) {
        if (context.isLineStartAt1()) {
            return dapLine - 1;
        }
        return dapLine;
    }

    public static int fromDAPColumn(int dapColumn, DebugAdapterContext context) {
        if (context.isColumnStartAt1()) {
            return dapColumn - 1;
        }
        return dapColumn;
    }

    public static int toDAPLine(int zeroStartLine, DebugAdapterContext context) {
        if (context.isLineStartAt1()) {
            return zeroStartLine + 1;
        }
        return zeroStartLine;
    }

    public static int toDAPColumn(int zeroStartColumn, DebugAdapterContext context) {
        if (context.isColumnStartAt1()) {
            return zeroStartColumn + 1;
        }
        return zeroStartColumn;
    }

    public static int toDAPLine(Position position, DebugAdapterContext context) {
        return toDAPLine(position.line(), context);
    }

    public static int toDAPColumn(Position position, DebugAdapterContext context) {
        return toDAPColumn(position.column(), context);
    }

    public static int toJDILine(int zeroStartColumn) {
        return zeroStartColumn + 1;
    }

    public static int toJDIColumn(int zeroStartColumn) {
        return zeroStartColumn + 1;
    }
    public static int fromJDILine(int jdiLine) {
        return jdiLine - 1;
    }

    public static int fromJDIColumn(int jdiColumn) {
        return jdiColumn - 1;
    }

    public static int fromJDILine(Location location) {
        return fromJDILine(location.lineNumber());
    }


    public static int toJDILine(Position position) {
        return toJDILine(position.line());
    }

    public static int toJDIColumn(Position position) {
        return toJDILine(position.column());
    }

}
