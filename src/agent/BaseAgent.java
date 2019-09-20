package agent;

import Geometry.CartesianCoordinate;
import drawing.Canvas;

public class BaseAgent {
	private Canvas canvas;
	//Angle agent is facing, from 0 degrees being south
	public int direction;
	public int angleToBeTurned;
	public boolean isPenDown;
	
	
	//Coordinate locations of agent
	public double xPosition;
	public double yPosition;
	
	//Flocking constants used to alter the behaviour of the agent
	public double Kc; //Cohesion
	public double Ks; //Separation
	public double Ka; //Alignment
	
	//Agent constructor
	public BaseAgent(Canvas canvas) {
		this.canvas = canvas;
		isPenDown = false;
		direction = 0;
		//Remember to initialise flocking constants
		Kc = 0.01;
		Ks = 0.02;
		Ka = 0.97;
	}
	
	//Method to move the agent in its current direction by an integer i
	public void move(double i) {
		CartesianCoordinate p1 = new CartesianCoordinate(xPosition, yPosition);
		
		xPosition = xPosition + (i*Math.cos(direction * Math.PI/180));
		yPosition = yPosition - (i*Math.sin(direction * Math.PI/180));
		
		CartesianCoordinate p2 = new CartesianCoordinate(xPosition, yPosition);
		
		if(isPenDown == true){
			canvas.drawLineBetweenPoints(p1, p2);
		}
	}
	
	//Method to adjust the agents direction clockwise by an integer angle i (degrees)
	public void turn(int i) {
		direction = direction + i;
		if (direction < 0) {
			direction = direction + 360;
		}
		else if (direction >= 360) {
			direction = direction - 360;
		}
	}
	
	//Method for retracting the pen, agents movement isn't illustrated
	public void putPenUp() {
		isPenDown = false;
	}
	
	//Method to put the pen down, used for drawing the agents position
	public void putPenDown() {
		isPenDown = true;
	}
	
	//Method used to draw the agent (currently standard triangle)
	public void draw() {
		isPenDown = true;
		turn(150);
		move(20);
		turn(120);
		move(20);
		turn(120);
		move(20);
		//Turn back to starting direction and take pen off
		turn(-30);
		isPenDown = false;
	}
	
	//Method to erase the previous agent position
	public void undraw() {
		for (int i = 0; i < 3; i++) {
			canvas.removeMostRecentLine();
		}		
	}
	
	//Methods used for getting the position vectors of the agent
	public double getXPosition() {
		return xPosition;
	}
	public double getYPosition() {
		return yPosition;
	}
	//Setter functions used in position wrapping
	public void setXPosition(double value) {
		xPosition = value;
	}
	public void setYPosition(double value) {
		yPosition = value;
	}
	
	//Method used to make the agents movement toroidal 
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
	
	//Method for returning the distance between agent and another
	public double checkDistance(CartesianCoordinate agentPosition) {
		double xDist = xPosition - agentPosition.xPosition;
		double yDist = yPosition - agentPosition.yPosition;
		return Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
	}
	
	//Method to return the angle of the centre of mass relative to the agent
	private double centreOfMassAngle(CartesianCoordinate centreOfMass) {
		double xDiff = centreOfMass.xPosition - xPosition;
		double yDiff = yPosition - centreOfMass.yPosition;
		
		//Centre of mass angle from current position
		double comAngleRadians = Math.atan2(yDiff, xDiff);
		double comAngleDegrees = Math.toDegrees(comAngleRadians);
		
		return comAngleDegrees;
	}
	
	//Method for exhibiting cohesive behaviour between agents
	//Returns angle to be turned if we want agent to move closer to neighbours
	public double cohesion(CartesianCoordinate centreOfMass) {
		double comAngle = centreOfMassAngle(centreOfMass);

		//Angle which must be turned to face centre of mass
		double cohesionAngle = comAngle - direction;
		
		if (cohesionAngle > 180) {
			cohesionAngle = cohesionAngle - 360;
		}
		if (cohesionAngle < - 180) {
			cohesionAngle = cohesionAngle + 360;
		}
		
		//System.out.println("cohesionAngle = " + cohesionAngle);
		
		//Return the angle when Kc = 1 times by the cohesion constant 
		// to get the final cohesion angle
		return cohesionAngle * Kc;
	}
	
	//Method for exhibiting separation behaviour between agents
	//Returns the angle to be turned if agent is to move away from neighbours
	public double separation(CartesianCoordinate centreOfMass) {
		double comAngle = centreOfMassAngle(centreOfMass);
		
		//Angle which must be turned to face away from centre of mass
		double separationAngle = (comAngle + 180) - direction;
		if (separationAngle > 180) {
			separationAngle = separationAngle - 360;
		}
		if (separationAngle < - 180) {
			separationAngle = separationAngle + 360;
		}
		
		//System.out.println("separationAngle = " + separationAngle);
		
		//Return angle to face away from com * separation constant
		return separationAngle * Ks;
	}
	
	//Method for exhibiting alignment behaviour among members of the flock
	//Returns angle to be turned towards average neighbourhood direction
	public double alignment(double averageDirection) {
		double alignmentAngle = averageDirection - direction;
		
		//System.out.println("alignmentAngle = " + alignmentAngle);
		
		return alignmentAngle * Ka;
	}
}
