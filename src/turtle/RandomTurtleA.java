package turtle;

import Geometry.CartesianCoordinate;
import drawing.Canvas;
import utilities.Utils;

public class RandomTurtleA extends DynamicTurtle {
	
	public RandomTurtleA(Canvas canvas) {
		super(canvas);
		draw();
	}
	public RandomTurtleA(Canvas canvas, CartesianCoordinate startPosition) {
		super(canvas, startPosition);
		draw();
	}
	public void update(double time) {
		direction = (int)(Utils.randomDouble() * 100);
		double distance = getSpeed() * (time/1000);
		move(distance);
	}
}
