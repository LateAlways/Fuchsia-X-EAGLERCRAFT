package ghost.utils;

public class MathHelp {

	public static float Magnitude2D(float[] first, float[] second) {
		float x = first[0] - second[0];
		float y = first[1] - second[1];
		
		return (float) Math.sqrt(x * x + y * y);
	}
	
}
