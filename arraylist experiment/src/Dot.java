import processing.core.PApplet;

public class Dot {
	float x, y, speed;
	PApplet gameWindow;

	// Constructor
	public Dot(PApplet gw, float x, float y, float speed) {
		this.gameWindow = gw;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	public void moveLeft(float speed) {
		x -= speed;
		if (x < 0)        { x = 800; }
		else if (x > 800) { x = 0;   }
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void draw() {
		gameWindow.ellipse(x, y, 2, 2);
	}
}
