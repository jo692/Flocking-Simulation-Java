package turtle;

import drawing.Canvas;
import Geometry.CartesianCoordinate;

public class Turtle {
	private Canvas myCanvas;
	public int direction; //Angle from 0 being north
	public int isPenDown; //1 = pen down 0 = pen up
	public double xPosition;
	public double yPosition;

	public Turtle(Canvas myCanvas) {
		this.myCanvas = myCanvas;
		isPenDown = 0;
		direction = 0;
		//draw();
		//undraw();
	}

	/**
	 * The turtle is moved in its current direction for the given number of pixels. 
	 * If the pen is down when the robot moves, a line will be drawn on the floor.
	 * 
	 * @param i The number of pixels to move.
	 */
	public void move(double i) {

		//undraw();
		CartesianCoordinate p1 = new CartesianCoordinate(xPosition, yPosition);

		xPosition = xPosition - (i*Math.sin(direction * Math.PI/180));
		yPosition = yPosition + (i*Math.cos(direction * Math.PI/180));
		
		CartesianCoordinate p2 = new CartesianCoordinate(xPosition, yPosition);
		
		if(isPenDown == 1){
			myCanvas.drawLineBetweenPoints(p1, p2);
		}
		//draw();
	}

	/**
	 * Rotates the turtle clockwise by the specified angle in degrees.
	 * 
	 * @param i The number of degrees to turn.
	 */
	public void turn(int i) {
		// TODO Auto-generated method stub
		//undraw();
		this.direction = this.direction - i;
		//draw();
	}

	/**
	 * Moves the pen off the canvas so that the turtle’s route isn’t drawn for any subsequent movements.
	 */
	public void putPenUp() {
		// TODO Auto-generated method stub
		isPenDown = 0;
		
	}

	/**
	 * Lowers the pen onto the canvas so that the turtle’s route is drawn.
	 */
	public void putPenDown() {
		// TODO Auto-generated method stub
		isPenDown = 1;
		
	}
	
	//Function used to draw the turtle on the canvas, indicating current position
	public void draw() {
		isPenDown = 1;
		turn(150);
		move(20);
		turn(120);
		move(20);
		turn(120);
		move(20);
		//Turn back to starting direction and take pen off
		turn(-30);
		isPenDown = 0;
	}
	
	//Function used to erase the drawn turtle
	public void undraw() {
		for (int i = 0; i < 3; i++) {
			myCanvas.removeMostRecentLine();
		}		
	}
	//Function to move the turtle to a specific coordinate
	public void moveTo(CartesianCoordinate position) {
		if (isPenDown == 1) {
			putPenUp();
		}
		direction = 0;
		move(position.yPosition - yPosition);
		if (xPosition < position.xPosition) {
			turn(90);
		}
		else {
			turn(-90);
		}
		move(position.xPosition - xPosition);
		direction = 0;
	}
	
	//Method used for getting the position of the turtle
	public double getXPosition() {
		return xPosition;
	}
	public double getYPosition() {
		return yPosition;
	}
	public void setXPosition(double value) {
		xPosition = value;
	}
	public void setYPosition(double value) {
		yPosition = value;
	}
	
	//Method used to make the turtle's movement toroidal 
	public void wrapPosition(int maxX, int maxY) {
		if (xPosition >= maxX) {
			setXPosition(1);
		}
		if (xPosition <= 0) {
			setXPosition(maxX - 1);
		}
		if (yPosition >= maxY) {
			setYPosition(1);
		}
		if (yPosition <= 0) {
			setYPosition(maxY - 1);
		}
	}
}
