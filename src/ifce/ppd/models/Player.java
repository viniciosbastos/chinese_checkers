package ifce.ppd.models;

public class Player {
	private String color;
	
	private int playerArea;
	
	private int playerId;
	
	public Player(String color, int playerArea, int playerId) {
		this.color = color;
		this.playerArea = playerArea;
		this.playerId = playerId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getPlayerArea() {
		return playerArea;
	}

	public void setPlayerArea(int playerArea) {
		this.playerArea = playerArea;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	public String getCellStyle() {
		return "hex-" + this.color;
	}

	public String getCellActiveStyle() {
		return "hex-active-" + this.color;
	}
}
