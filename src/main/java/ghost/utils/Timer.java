package ghost.utils;

public class Timer {
	
	public long lastMs;
	
	public Timer() {
		this.lastMs = System.currentTimeMillis();
	}
	
	public void reset() {
		this.lastMs = System.currentTimeMillis();
	}
	
	public boolean hasTimeElapsed(long ms, boolean reset) {
		if((System.currentTimeMillis()-lastMs) >= ms) {
			if(reset) {
				reset();
			}
			return true;
		}
		return false;
	}

}
