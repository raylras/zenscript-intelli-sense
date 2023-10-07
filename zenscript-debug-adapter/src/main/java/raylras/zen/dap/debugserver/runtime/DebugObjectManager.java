package raylras.zen.dap.debugserver.runtime;

import com.sun.jdi.ThreadReference;
import raylras.zen.dap.debugserver.variable.VariableProxy;
import raylras.zen.dap.debugserver.variable.VariableProxyFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DebugObjectManager {
    private final VariableProxyFactory factory = new VariableProxyFactory(this);
    private final ObjectIdMap<VariableProxy> debugObjectIds = new ObjectIdMap<>();
    private final Map<Long, Set<Integer>> debugObjectsByThread = new ConcurrentHashMap<>();
    private final Map<Integer, Long> debugObjectThread = new ConcurrentHashMap<>();
    private final ThreadManager threadManager;

    public DebugObjectManager(ThreadManager threadManager) {
        this.threadManager = threadManager;
    }


    public ThreadReference getOwnerThread(VariableProxy proxy) {
        int id = getId(proxy);
        long threadId = debugObjectThread.get(id);
        return threadManager.getByUniqueId(threadId);
    }

    public VariableProxyFactory getVariableFactory() {
        return factory;
    }


    public void reset() {
        debugObjectIds.reset();
        debugObjectsByThread.clear();
        debugObjectThread.clear();
    }

    public void removeByThread(long threadUUID) {
        Set<Integer> toRemove = debugObjectsByThread.remove(threadUUID);
        if (toRemove == null || toRemove.isEmpty()) {
            return;
        }
        if (debugObjectsByThread.isEmpty()) {
            reset();
            return;
        }
        for (Integer id : toRemove) {
            debugObjectIds.removeById(id);
            debugObjectThread.remove(id);
        }

    }

    public <T extends VariableProxy> T put(T obj, ThreadReference thread) {
        int id = debugObjectIds.getOrPut(obj);

        if (id >= 0) {
            debugObjectsByThread.computeIfAbsent(thread.uniqueID(), it -> new HashSet<>()).add(id);
            debugObjectThread.put(id, thread.uniqueID());
        }

        return obj;
    }

    public int getId(VariableProxy obj) {
        return debugObjectIds.tryGet(obj);
    }

    public VariableProxy getById(int id) {
        return debugObjectIds.getById(id);
    }
}
