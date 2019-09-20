package Geometry;
import java.lang.Math;

public class LineSegment {

	private CartesianCoordinate startPoint;
	private CartesianCoordinate endPoint;
	
	//Constructor
	public LineSegment(CartesianCoordinate coordA, CartesianCoordinate coordB) {
		this.startPoint = coordA;
		this.endPoint = coordB;
	}
	
	//Getters
	public CartesianCoordinate getStartPoint() {
		return startPoint;
	}
	public CartesianCoordinate getEndPoint() {
		return endPoint;
	}
	
	//Method to find length of line segment
	public double length() {
		return (Math.sqrt(Math.pow(endPoint.xPosition - startPoint.xPosition, 2.00) + Math.pow(endPoint.yPosition - startPoint.yPosition, 2.00)));
	}
	
}
