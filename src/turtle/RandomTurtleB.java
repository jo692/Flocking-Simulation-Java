package turtle;

import Geometry.CartesianCoordinate;
import drawing.Canvas;
import utilities.Utils;

public class RandomTurtleB extends DynamicTurtle {
	
	public RandomTurtleB(Canvas canvas) {
		super(canvas);
		draw();
	}
	public RandomTurtleB(Canvas canvas, CartesianCoordinate startPosition) {
		super(canvas, startPosition);
		//draw();
	}
	public void update(double time) {
		//1/10 chance for the turtle to change direction (by a random value between 0-100)
		if ((int)(Utils.randomDouble() * 10) == 1) {
			double newDirec = (Utils.randomDouble() * 100);
			//Add a relatively small chance to make the new direction much greater, resulting in a sharp change in direction
			if(Utils.randomDouble() * 10 >= 7) {
				newDirec = newDirec + (Utils.randomDouble() * 200);
			}
			direction = (int)newDirec;
		}
		double distance = getSpeed() * (time/1000);
		move(distance);
	}
}