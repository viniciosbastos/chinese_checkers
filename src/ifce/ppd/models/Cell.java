package ifce.ppd.models;

import ifce.ppd.utils.TilesUtils;
import javafx.scene.shape.Polygon;

public class Cell {
	private boolean visible;
	
	private boolean empty;
	
	private Polygon tile;

	private Point center;
	
	private int matrixIndiceRow;
	
	private int matrixIndiceColumn;
	

	public Cell(boolean visible, Point center, int i, int j) {
		this.visible = visible;
		this.center = center;
		createTile();
		this.matrixIndiceRow = i;
		this.matrixIndiceColumn = j;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
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

	public int getMatrixIndiceRow() {
		return matrixIndiceRow;
	}

	public int getMatrixIndiceColumn() {
		return matrixIndiceColumn;
	}

	private void createTile() {
		this.tile = new Polygon();
		this.tile.getStyleClass().add("hex");
	
		for (int i = 0; i < 6; i++) {
			this.tile.getPoints().addAll(TilesUtils.calculateCoordinateOfHexByIndex(this.center, i).asArray());
		}
	}
}
