package ifce.ppd.utils;

import ifce.ppd.models.Point;

public class TilesUtils {
	
	public static final double TILE_SIZE = 20;
	
	public static final double TILE_WIDTH = Math.sqrt(3) * TILE_SIZE;
	
	public static final double TILE_HEIGHT = TILE_SIZE * 2;
	
	public static final double TILE_OFFSET = TILE_WIDTH / 2;
	
	public static final double CENTER_X = 400;
	
	public static final double CENTER_Y = 270;
	

	public static Point calculateCoordinateOfHexByIndex(Point center,  int i) {
	    double angle_deg = 60 * i - 30;
	    double angle_rad = Math.PI / 180 * angle_deg;
	    	    
	    return new Point(center.getX() + TILE_SIZE * Math.cos(angle_rad),
	                 center.getY() + TILE_SIZE * Math.sin(angle_rad));
		
	}
}
