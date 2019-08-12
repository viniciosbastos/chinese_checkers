package ifce.ppd.models;

import java.io.Serializable;

public class Point implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1033752391684401590L;

	private double x;
	
	private double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public Double[] asArray() {
		return new Double[] { this.getX(), this.getY() };
	}
	
	@Override
	public String toString() {
		return String.format("(%.2f, %.2f)", this.x, this.y);
	}
	
	@Override
	public boolean equals(Object o) {
		Point point = (Point) o;
		if (this.x != point.getX() || this.y != point.getY()) 
			return false;
		else
			return true;
	}
}
