package ghost.utils;

import java.util.ArrayList;
import java.util.List;

public class HitRatio {

	public List<Hit> hits = new ArrayList<Hit>();
	
	public HitRatio add(Hit hit) {
		this.hits.add(hit);
		return this;
	}
	
	public HitRatio update() {
		List<Integer> toBeRemoved = new ArrayList<Integer>();
		for(int i = 0; i < hits.size(); i++) {
			if(hits.get(i).elapsed.hasTimeElapsed(1000, false)) {
				toBeRemoved.add(i);
			}
		}
		for(int i = 0; i < toBeRemoved.size(); i++) {
			hits.remove(toBeRemoved.get(i));
		}
		return this;
	}
	
	public float getCPS() {
		update();
		return hits.size();
	}
}
