package ifce.ppd.utils;

public class AreaUtils {
	public static final int[][] AREA_1 = 
			new int[][] {
							{0, 6},
							{1, 5},
							{1, 6},
							{2, 5},
							{2, 6},
							{2, 7},
							{3, 4},
							{3, 5},
							{3, 6},
							{3, 7}
						};
						
	public static final int[][] AREA_4 = 
			new int[][] {
							{16, 6},
							{15, 5},
							{15, 6},
							{14, 5},
							{14, 6},
							{14, 7},
							{13, 4},
							{13, 5},
							{13, 6},
							{13, 7}
						};

	public static int[][] getArea(int area) {
		if (area == 1) {			
			return AREA_1;
		} else {
			return AREA_4;
		} 
	}
}
