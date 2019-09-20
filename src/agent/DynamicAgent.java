package agent;

import Geometry.CartesianCoordinate;
import drawing.Canvas;

public class DynamicAgent extends BaseAgent {
	
	private int speed;
	
	public DynamicAgent(Canvas canvas) {
		super(canvas);
		draw();
	}
	
	//Constructor with given start position
	public DynamicAgent(Canvas canvas, CartesianCoordinate startPosition) {
		super(canvas);
		moveTo(startPosition);
		speed = 100;
		draw();
	}
	
	//Methods to get and set speed 
	public int getSpeed(){
		return speed;
	}
	public void setSpeed(int newSpeed){
		speed = newSpeed;
	}
	
	//Function to move the agent to a specific coordinate
	private void moveTo(CartesianCoordinate position) {
		if (isPenDown == true) {
			putPenUp();
		}
		setXPosition(position.xPosition);
		setYPosition(position.yPosition);
	}
	
	//Method for updating the agent based on when it was last updated
	public void update(double time, int angleToTurn) {
		double distance = speed * (time/1000);
		turn((int)angleToTurn);
		move(distance);
	}
}
