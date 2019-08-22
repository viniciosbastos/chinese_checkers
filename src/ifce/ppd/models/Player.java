package ifce.ppd.models;

import java.io.Serializable;

public class Player implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1195521006996317269L;

	private String color;
	private String name;
	private int playerArea;
	private int playerId;
	
	public Player(String color, int playerArea, int playerId, String name) {
		this.color = color;
		this.playerArea = playerArea;
		this.playerId = playerId;
		this.name = name;
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
	
	@Override
	public boolean equals(Object other) {
		Player otherPlayer = (Player) other;
		return this.playerId == otherPlayer.getPlayerId();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
