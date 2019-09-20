package turtle;

import Geometry.CartesianCoordinate;
import drawing.Canvas;
import utilities.Utils;
//Turtle which moves randomly around the canvas following arcs of differing size, (best one)
public class RandomTurtleC extends DynamicTurtle {
	//private int angularVelocity;
	private int updateCounter = 0;
	private int counterHolder;
	private double theta;
	private double newDirec;
	public RandomTurtleC(Canvas canvas) {
		super(canvas);
		draw();
	}
	public RandomTurtleC(Canvas canvas, CartesianCoordinate startPosition) {
		super(canvas, startPosition);
		//Commented because turtle button error
		//draw();
	}

	public void update(double time) {
		//Update counter will be a random value between 20 and 50
		if (updateCounter == 0) {
			updateCounter = (int)((Utils.randomDouble()*30) + 20);
			counterHolder = updateCounter;
		}
		
		//For loop to run only when a new arc is begun
		if (updateCounter == counterHolder) {
			//Assign a start direction (0-360) & end direction
			newDirec = (Utils.randomDouble() * 360);
			double endDirec = (Utils.randomDouble() * 360);
			//Calculate the angle which turtle will be turned during an arc
			theta = (endDirec - newDirec)/updateCounter;
		}
		//direction = (int)newDirec;
		//Change the angular velocity every so often (0-200)
		/*if ((Utils.randomDouble() * 10) >= 8) {
			angularVelocity = (int)(Utils.randomDouble() * 200);
		}*/
		
		turn((int)theta);
		double distance = getSpeed() * (time/1000);
		move(distance);
		updateCounter--;
	}
}
