package ghost.utils.notifications;

import ghost.utils.Timer;

public class Notification {
	
	public Timer timer = new Timer();
	
	public long ms;
	public String name;
	public String description;
	public NotifType notifType;
	public Timer animTimer = new Timer();
	public Timer animTimer1 = new Timer();
	
	public float xAdd = 30;
	public float yAdd = 30;
	
	public Notification(String name, String description, long ms, NotifType notifType) {
		timer.reset();
		this.name = name;
		this.description = description;
		this.ms = ms;
		this.notifType = notifType;
	}

}
