package raylras.zen.dap.debugserver.breakpoint;

import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.EventRequest;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.dap.DAPPositions;
import raylras.zen.dap.event.EventHub;
import raylras.zen.util.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static raylras.zen.dap.jdi.ObservableUtils.safeFilter;

public class Breakpoint {
    private static final Logger logger = LoggerFactory.getLogger(Breakpoint.class);
    private final String sourcePath;

    private Position position;
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
        logger.info("Start install breakpoint at {}({}:{})", this.sourcePath, this.position.line(), this.position.column());
        CompletableFuture<Breakpoint> future = new CompletableFuture<>();
        int jdiLineNumber = DAPPositions.toJDILine(position);
        Disposable subscribe = Observable.merge(
                        Observable.fromIterable(vm.allClasses()),
                        eventHub.classPrepareEvents()
                                .map(it -> (ClassPrepareEvent) it.getEvent())
                                .map(ClassPrepareEvent::referenceType)
                )
                .filter(safeFilter(it -> Objects.equals(sourcePath, it.sourceName())))
                .doOnComplete(() -> {
                    logger.warn("Stopped observing breakpoint at {}({}:{})", this.sourcePath, this.position.line(), this.position.column());
                })
                .doOnNext(it -> {
                    logger.info("Loading breakpoints for {}({}) ...", it.name(), sourcePath);
                })
                .flatMapMaybe(referenceType -> Observable.fromIterable(referenceType.methods())
                        .flatMap(method -> Observable.fromSupplier(() -> method.locationsOfLine(jdiLineNumber))
                                .flatMap(Observable::fromIterable)
                                .onErrorComplete()
                        )
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
                        .doOnNext(it -> {
                            it.enable();
                            requests.add(it);
                        })
                        .count()
                        .filter(it -> it > 0)
                        .doOnSuccess(it -> {
                            logger.info("Added {} breakpoints at {}({})", it, this.sourcePath, this.position.line());
                            this.isVerified = true;
                            future.complete(this);
                        })
                )
                .doOnError(e -> {
                    logger.error("Exception occurred when installing breakpoint  at {}({}:{})", this.sourcePath, this.position.line(), this.position.column(), e);
                })
                .subscribe();
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
