package ifce.ppd.models;

import java.io.Serializable;

import ifce.ppd.utils.TilesUtils;
import javafx.scene.shape.Polygon;

public class Cell implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 543476319849982814L;

	private boolean movable;
	
	private boolean empty;
	
	private Player player;
	
	private Polygon tile;

	private Point center;
	
	private int matrixIndexRow;
	
	private int matrixIndexColumn;
	

	public Cell(Point center, int i, int j) {		
		this.center = center;
		createTile();
		this.matrixIndexRow = i;
		this.matrixIndexColumn = j;
		this.reset();
	}

	public boolean isMovable() {
		return movable;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}
	
	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public Polygon getTile() {
		return tile;
	}

	public void setTile(Polygon tile) {
		this.tile = tile;
	}	
	
	public Point getCenter() {
		return center;
	}	

	public int getMatrixIndexRow() {
		return matrixIndexRow;
	}

	public int getMatrixIndexColumn() {
		return matrixIndexColumn;
	}

	public Player getPlayer() {
		return player;
	}

	private void createTile() {
		this.tile = new Polygon();
		for (int i = 0; i < 6; i++) {
			this.tile.getPoints().addAll(TilesUtils.calculateCoordinateOfHexByIndex(this.center, i).asArray());
		}
	}

	public void setOwner(Player player) {
		this.player = player;
		this.getTile().getStyleClass().add(player.getCellStyle());
		this.setEmpty(false);
	}

	public void reset() {
		this.player = null;
		this.setMovable(false);
		this.getTile().getStyleClass().clear();
		this.getTile().getStyleClass().add("hex");
		this.getTile().setOnMouseClicked(null);
		this.setEmpty(true);
	}
	
	public void selectCell() {
		getTile().getStyleClass().clear();		
		getTile().getStyleClass().add(player.getCellActiveStyle());		
	}
	
	public void deselectCell() {
		getTile().getStyleClass().clear();		
		getTile().getStyleClass().add(player.getCellStyle());		
	}
	
	
	@Override
	public boolean equals(Object obj) {
		Cell cellObj = (Cell) obj;
		if (obj == null)
			return false;
		else
			return this.center.equals(cellObj.getCenter());
	}

}
