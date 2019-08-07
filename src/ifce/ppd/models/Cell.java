package ifce.ppd.models;

import com.sun.prism.paint.Color;

import ifce.ppd.utils.TilesUtils;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

public class Cell {
	private boolean visible;
	
	private boolean empty;
	
	private Polygon tile;

	private Point center;
	

	public Cell(boolean visible, Point center) {
		this.visible = visible;
		this.center = center;
		createTile();
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

	private void createTile() {
		this.tile = new Polygon();
		this.tile.setFill(Paint.valueOf("white"));
		this.tile.setStroke(Paint.valueOf("black"));
		
		this.tile.getPoints().addAll(TilesUtils.calculateCoordinateOfHexByIndex(this.center, 0).asArray());		
		this.tile.getPoints().addAll(TilesUtils.calculateCoordinateOfHexByIndex(this.center, 1).asArray());		
		this.tile.getPoints().addAll(TilesUtils.calculateCoordinateOfHexByIndex(this.center, 2).asArray());		
		this.tile.getPoints().addAll(TilesUtils.calculateCoordinateOfHexByIndex(this.center, 3).asArray());		
		this.tile.getPoints().addAll(TilesUtils.calculateCoordinateOfHexByIndex(this.center, 4).asArray());		
		this.tile.getPoints().addAll(TilesUtils.calculateCoordinateOfHexByIndex(this.center, 5).asArray());		
		
	}
}
