package raylras.zen.dap.debugserver.breakpoint;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequest;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import raylras.zen.dap.DAPPositions;
import raylras.zen.dap.event.EventHub;
import raylras.zen.dap.jdi.ObservableUtils;
import raylras.zen.util.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static raylras.zen.dap.jdi.ObservableUtils.safeFilter;

public class Breakpoint {
    private final Position position;
    private final String sourcePath;
    private boolean isVerified = false;
    private final VirtualMachine vm;
    private final EventHub eventHub;
    private int id;


    public Breakpoint(Position position, String sourcePath, VirtualMachine vm, EventHub eventHub) {
        this.position = position;
        this.sourcePath = sourcePath;
        this.vm = vm;
        this.eventHub = eventHub;
    }

    private final List<EventRequest> requests = Collections.synchronizedList(new ArrayList<>());
    private final List<Disposable> subscriptions = new ArrayList<>();

    public Position getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<EventRequest> getRequests() {
        return requests;
    }

    public List<Disposable> getSubscriptions() {
        return subscriptions;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public boolean hasRequest(EventRequest request) {
        return getRequests().stream().anyMatch(it -> Objects.equals(request, it));
    }

    public CompletableFuture<Breakpoint> install() {
        CompletableFuture<Breakpoint> future = new CompletableFuture<>();
        Disposable subscribe = Observable.concat(
                        Observable.fromIterable(vm.allClasses()),
                        eventHub.classPrepareEvents()
                                .map(it -> (ClassPrepareEvent) it.getEvent())
                                .map(ClassPrepareEvent::referenceType)
                )
                .filter(safeFilter(it -> it.sourceName().endsWith(".zs")))
                .flatMapMaybe(referenceType -> Observable.fromIterable(referenceType.locationsOfLine(DAPPositions.toJDILine(position)))
                        .filter(safeFilter(it -> Objects.equals(sourcePath, it.sourcePath())))
                        .filter(location -> requests.stream().filter(it -> it instanceof BreakpointRequest)
                                .map(it -> (BreakpointRequest) it)
                                .map(BreakpointRequest::location)
                                .noneMatch(it -> Objects.equals(location, it))
                        )
                        .map(it -> {
                            BreakpointRequest breakpointRequest = vm.eventRequestManager().createBreakpointRequest(it);
                            breakpointRequest.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
                            return breakpointRequest;
                        })
                        .doOnEach(it -> {
                            if (it.isOnNext()) {
                                it.getValue().enable();
                                requests.add(it.getValue());
                            }
                        })
                        .count()
                        .filter(it -> it > 0)
                        .doOnSuccess(it -> {
                            this.isVerified = true;
                            future.complete(this);
                        })
                ).subscribe();
        subscriptions.add(subscribe);

        return future;
    }


    public void close() {
        try {
            vm.eventRequestManager().deleteEventRequests(getRequests());
        } catch (VMDisconnectedException ex) {
            // ignore since removing breakpoints is meaningless when JVM is terminated.
        }
        getSubscriptions().forEach(Disposable::dispose);
        requests.clear();
        subscriptions.clear();
    }
}
