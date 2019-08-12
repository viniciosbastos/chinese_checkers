package ifce.ppd.models;

import java.io.Serializable;

public class MoveCommand implements Command, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3245742364335330894L;

	private int fromRowIndex;
	private int fromColumnIndex;
	private int toRowIndex;
	private int toColumnIndex;
	private Player player;
	
	public MoveCommand(Cell from, Cell to, Player player) {	
		this.fromRowIndex = from.getMatrixIndexRow();
		this.fromColumnIndex = from.getMatrixIndexColumn();
		this.toRowIndex = to.getMatrixIndexRow();
		this.toColumnIndex = to.getMatrixIndexColumn();
		this.player = player;
	}

	public int getFromRowIndex() {
		return fromRowIndex;
	}

	public void setFromRowIndex(int fromRowIndex) {
		this.fromRowIndex = fromRowIndex;
	}

	public int getFromColumnIndex() {
		return fromColumnIndex;
	}

	public void setFromColumnIndex(int fromColumnIndex) {
		this.fromColumnIndex = fromColumnIndex;
	}

	public int getToRowIndex() {
		return toRowIndex;
	}

	public void setToRowIndex(int toRowIndex) {
		this.toRowIndex = toRowIndex;
	}

	public int getToColumnIndex() {
		return toColumnIndex;
	}

	public void setToColumnIndex(int toColumnIndex) {
		this.toColumnIndex = toColumnIndex;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
