package ghost.events;

public class Event {
	public EventType type;
	public boolean cancelled = false;
	
	public Event setType(EventType type) {
		this.type = type;
		return this;
	}
	
	public EventType getType() {
		return type;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
}
