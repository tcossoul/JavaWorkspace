import processing.core.PApplet;

public class Game extends PApplet {
	Cactus spiney;
	float cs = 0;

	public void setup() {
		size(800, 400);
		spiney = new Cactus(this, 400, 200);
	}

	public void draw() {
		background(200);
		line(0, 200, 800, 200);
		spiney.draw();
		spiney.moveLeft(5);
	}

	cs = spiney.getX();
	
	ce - spiney.getwidth() + spiney.getX();
	
	public boolean areIntervalsTouching(float cs, float ce, float ts, float te, float ce1) {
		if ( te > cs || ts > ce1 ) {
			return false;
		}
		else
			return true;
	}
}