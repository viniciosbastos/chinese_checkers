package ifce.ppd.models;

import java.io.Serializable;

public class VictoryCommand implements Command, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -195570066577394073L;
	private Player victoriousPlayer;

	public VictoryCommand(Player victoriousPlayer) {
		this.victoriousPlayer = victoriousPlayer;
	}

	public Player getVictoriousPlayer() {
		return victoriousPlayer;
	}

	public void setVictoriousPlayer(Player victoriousPlayer) {
		this.victoriousPlayer = victoriousPlayer;
	}
	
	
}
