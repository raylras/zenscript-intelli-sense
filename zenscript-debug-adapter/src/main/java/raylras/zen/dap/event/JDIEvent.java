package raylras.zen.dap.event;

import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventSet;

public class JDIEvent {

    private final Event event;
    private final EventSet eventSet;
    private boolean shouldResume = true;

    public JDIEvent(Event event, EventSet eventSet) {
        this.event = event;
        this.eventSet = eventSet;
    }

    public Event getEvent() {
        return event;
    }

    public EventSet getEventSet() {
        return eventSet;
    }

    public boolean shouldResume() {
        return shouldResume;
    }

    public void setResume(boolean shouldResume) {
        this.shouldResume = shouldResume;
    }
}
