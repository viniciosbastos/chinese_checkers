package ifce.ppd.models;

public class Address {

	private String ipAddress;
	private int port;
	
	public Address(String ipAddress, int port) {
		super();
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	
}
