import processing.core.PApplet;

public class star {
	float x;
	float y;
	float speed;
	int size;
    PApplet window;

	public star(float x, float y, float speed, int size, PApplet window) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.size = size;
	}

	public  void moveLeft() {
		x -= speed;
	}
}