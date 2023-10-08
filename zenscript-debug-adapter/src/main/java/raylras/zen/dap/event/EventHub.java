package raylras.zen.dap.event;

import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.*;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventHub {

    private static final Logger logger = LoggerFactory.getLogger(EventHub.class);
    private final PublishSubject<JDIEvent> subject = PublishSubject.create();


    private Thread workingThread = null;
    private boolean isClosed = false;


    public Observable<JDIEvent> allEvents() {
        return subject;
    }

    public Observable<JDIEvent> classPrepareEvents() {
        return subject.filter(it -> it.getEvent() instanceof ClassPrepareEvent);
    }

    public Observable<JDIEvent> breakpointEvents() {
        return subject.filter(it -> it.getEvent() instanceof BreakpointEvent);
    }

    public void start(VirtualMachine vm) {
        if (isClosed) {
            throw new IllegalStateException("This event hub is already closed.");
        }

        workingThread = new Thread(() -> {
            EventQueue queue = vm.eventQueue();
            while (true) {
                try {
                    if (Thread.interrupted()) {
                        subject.onComplete();
                        return;
                    }

                    EventSet set = queue.remove();

                    boolean shouldResume = true;
                    for (Event event : set) {
                        JDIEvent dbgEvent = new JDIEvent(event, set);
                        subject.onNext(dbgEvent);
                        if (!dbgEvent.shouldResume()) {
                            try {
                                logger.info("Paused at JDI Event: {}", event);
                            } catch (VMDisconnectedException e) {
                                // do nothing
                            }
                        }
                        shouldResume &= dbgEvent.shouldResume();
                    }

                    if (shouldResume) {
                        set.resume();
                    }
                } catch (InterruptedException | VMDisconnectedException e) {
                    isClosed = true;
                    subject.onComplete();
                    return;
                }
            }
        }, "Event Hub");

        workingThread.start();
    }

    public void close() {
        if (isClosed) {
            return;
        }
        workingThread.interrupt();
        workingThread = null;
        isClosed = true;
    }
}
