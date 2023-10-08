package raylras.zen.dap.debugserver.handler;

import com.sun.jdi.ObjectCollectedException;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VMDisconnectedException;
import org.eclipse.lsp4j.debug.Thread;
import org.eclipse.lsp4j.debug.ThreadsResponse;
import raylras.zen.dap.debugserver.DebugAdapterContext;

import java.util.ArrayList;

public final class ThreadListHandler {


    public static ThreadsResponse visibleThreads(DebugAdapterContext context) {
        ArrayList<Thread> threads = new ArrayList<>();
        try {

            context.getThreadManager().reloadThreads(context.getDebugSession().getVM());
            for (ThreadReference threadReference : context.getThreadManager().allThreads()) {


                String jdiName = threadReference.name();
                String name = jdiName.isBlank() ? String.valueOf(threadReference.uniqueID()) : jdiName;
                Thread thread = new Thread();
                thread.setId(context.getThreadManager().tryGetId(threadReference));
                thread.setName("Thread [" + name + "]");
                threads.add(thread);
            }
        } catch (ObjectCollectedException | VMDisconnectedException ex) {
            // allThreads may throw VMDisconnectedException when VM terminates and thread.name() may throw ObjectCollectedException
            // when the thread is exiting.
        }
        ThreadsResponse response = new ThreadsResponse();
        response.setThreads(threads.toArray(Thread[]::new));
        return response;
    }



}
