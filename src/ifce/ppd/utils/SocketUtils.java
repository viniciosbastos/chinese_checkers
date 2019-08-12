package ifce.ppd.utils;

public class SocketUtils {
	
	private static SocketUtils instance;
	
	public static SocketUtils getInstance() {
		if (instance == null)
			instance = new SocketUtils();
		return instance;
	}

	public static void send(Object obj) {
		
	}
	
	public static void receive() {
		
	}
}
