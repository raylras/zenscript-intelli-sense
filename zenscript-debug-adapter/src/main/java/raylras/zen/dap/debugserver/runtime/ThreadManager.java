package raylras.zen.dap.debugserver.runtime;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VirtualMachine;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ThreadManager {

    private final ConcurrentHashMap<Long, ThreadReference> cachedThreads = new ConcurrentHashMap<>();
    private final ObjectIdMap<Long> threadIdMap = new ObjectIdMap<>();

    private final Set<Long> pausedThreads = Collections.synchronizedSet(new HashSet<>());


    public Collection<ThreadReference> allThreads() {
        return cachedThreads.values();
    }

    public Collection<ThreadReference> pausedThreads() {
        return pausedThreads.stream().map(cachedThreads::get).filter(Objects::nonNull).toList();
    }

    public int getThreadId(ThreadReference thread) {
        return threadIdMap.tryGet(thread.uniqueID());
    }


    public int threadStarted(ThreadReference threadReference) {
        int id = threadIdMap.getOrPut(threadReference.uniqueID());
        cachedThreads.put(threadReference.uniqueID(), threadReference);
        return id;
    }

    public void threadPaused(ThreadReference threadReference) {
        pausedThreads.add(threadReference.uniqueID());
    }

    public void threadResumed(ThreadReference threadReference) {
        pausedThreads.remove(threadReference.uniqueID());
    }

    public int threadStopped(ThreadReference threadReference) {
        cachedThreads.remove(threadReference.uniqueID());
        pausedThreads.remove(threadReference.uniqueID());
        return threadIdMap.remove(threadReference.uniqueID());
    }

    public int tryGetId(ThreadReference reference) {
        return threadIdMap.tryGet(reference.uniqueID());
    }

    public boolean resumeThread(ThreadReference reference) {
        if (reference == null || !reference.isSuspended()) {
            return false;
        }
        threadResumed(reference);
        reference.resume();
        return true;
    }

    public boolean pauseThread(ThreadReference reference) {
        if (reference == null || reference.isSuspended()) {
            return false;
        }
        reference.suspend();
        threadPaused(reference);
        return true;
    }


    public ThreadReference getById(int threadId) {
        Long id = threadIdMap.getById(threadId);
        return cachedThreads.get(id);
    }


    public void reloadThreads(VirtualMachine vm) {
        List<ThreadReference> threadReferences = vm.allThreads();

        Set<Long> currentIds = threadReferences.stream().map(ObjectReference::uniqueID).collect(Collectors.toSet());

        for (Long threadUniqueId : cachedThreads.keySet()) {
            if (!currentIds.contains(threadUniqueId)) {
                threadIdMap.remove(threadUniqueId);
                pausedThreads.remove(threadUniqueId);
            }
        }

        cachedThreads.clear();

        for (ThreadReference threadReference : threadReferences) {
            threadStarted(threadReference);
        }

    }

}
