package turtle;

import Geometry.CartesianCoordinate;
import drawing.Canvas;

public class DynamicTurtle extends Turtle {
	private int speed;
	
	public DynamicTurtle(Canvas canvas) {
		super(canvas);
		draw();
	}
	//Constructor with given start position
	public DynamicTurtle(Canvas canvas, CartesianCoordinate startPosition) {
		super(canvas);
		//CartesianCoordinate startPosition = new CartesianCoordinate(xPosition, yPosition);
		moveTo(startPosition);
		draw();
		speed = 100;
	}
	
	public int getSpeed(){
		return speed;
	}
	public void setSpeed(int newSpeed){
		speed = newSpeed;
	}
	
	//Method for updating the turtle based on when we last updated it
	public void update(double time){
		//undraw();
		//Time is in milliseconds so divide by 1000
		double distance = speed * (time/1000);
		move(distance);
		//draw();
	}
}
