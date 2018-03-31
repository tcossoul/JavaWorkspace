import processing.core.PApplet;
import processing.core.PImage;

public class Cactus {
	// Fields
	float x, y;
	PImage img;
	PApplet gameWindow;

	// Constructor
	public Cactus(PApplet gw, int x, int y) {
		this.gameWindow = gw;
		this.x = x;
		this.y = y;
		img = gameWindow.loadImage("../assets/cactus1.png");
	}

	public void draw() {
		gameWindow.image(img, x, y);
	}

	public void moveRight(float moveSpeed) {
		this.x = x + moveSpeed;
	}

	public void moveLeft(float moveSpeed) {
		this.x = x - moveSpeed;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean isOffScreenRight() {
		if (this.x < 0) {
			return true;
		} else {
			return false;
		}
	}

	public float getX() {
		return this.x;
	}
	
	public int getWidth() {
		return (img.width);
	}
}