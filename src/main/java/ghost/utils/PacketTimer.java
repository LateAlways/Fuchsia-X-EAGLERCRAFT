package ghost.utils;

import net.minecraft.src.Packet;

public class PacketTimer {
	
	public Packet packet;
	public Timer timer;
	public long ms;
	
	public PacketTimer(Packet packet, long ms) {
		this.timer = new Timer();
		this.packet = packet;
		this.ms = ms;
	}

}
