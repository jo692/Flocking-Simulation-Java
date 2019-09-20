package Geometry;

public class CartesianCoordinate {
	public double xPosition;
	public double yPosition;
	
	public CartesianCoordinate(double x, double y) {
	 xPosition = x;
	 yPosition = y;
	 } 

	//Getter for x
	public double getX() {
		return xPosition;
	}
	//Getter for y
	public double getY() {
		return yPosition;
	}
	
	//Setters
	public void setX(double value) {
		xPosition = value;
	}
	public void setY(double value) {
		yPosition = value;
	}

	
}