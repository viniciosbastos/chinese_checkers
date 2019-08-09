package ifce.ppd.models;

import java.util.ArrayList;
import java.util.List;

import ifce.ppd.utils.TilesUtils;

public class Board {
	public static final int BOARD_WIDTH = 13;
	
	public static final int BOARD_HEIGHT = 17;
	
	private Cell[][] boardMatrix;
	
	private int[][] possibleNeighborPositions;
	
	
	public Board() {
		this.boardMatrix = new Cell[BOARD_HEIGHT][BOARD_WIDTH];
		this.possibleNeighborPositions = new int[][] {{-1, -1},
													 {-1, 0},
													 {0, -1},
													 {0, +1},
													 {1, -1},
													 {1, 0}};
	}
	
	public Cell[][] getBoardMatrix() {
		return boardMatrix;
	}

	public void createBoard() {
		createUpTriangle();
		createDownTriangle();
	}
	
	private void createUpTriangle() {
		int quantity = 13;
		int offset = -1;
		
		double baseX = TilesUtils.FIRST_TILE_X;
		double baseY = TilesUtils.FIRST_TILE_Y + (9 * TilesUtils.TILE_HEIGHT);
		
		for (int i = 12; i >= 0 ; i--) {
			if (i%2 == 0) 
				offset++;
			else
				quantity--;
			
			for (int j = offset; j < quantity; j++) {
				Cell tileCell = new Cell(true, new Point(baseX+(j*TilesUtils.TILE_WIDTH), baseY - ((12-i)*TilesUtils.TILE_HEIGHT*3/4)), i, j);
				this.boardMatrix[i][j] = tileCell;
			}			
			
			if (i%2 == 0) {
				baseX = baseX + TilesUtils.TILE_OFFSET;
			} else {
				baseX = baseX - TilesUtils.TILE_OFFSET;
			}
		}
		
	}
	
	private void createDownTriangle() {
		int quantity = 13;
		int offset = -1;
		
		double baseX = TilesUtils.FIRST_TILE_X;
		double baseY = TilesUtils.FIRST_TILE_Y + (3 * TilesUtils.TILE_HEIGHT);
		for (int i = 4; i <= 16 ; i++) {
			if (i%2 == 0) 
				offset++;
			else
				quantity--;
			
			for (int j = offset; j < quantity; j++) {
				if (this.boardMatrix[i][j] == null) {
					Cell tileCell = new Cell(true, new Point(baseX+(j*TilesUtils.TILE_WIDTH), baseY + ((i-4)*TilesUtils.TILE_HEIGHT*3/4)), i, j);
					this.boardMatrix[i][j] = tileCell;					
				}
			}			
			
			if (i%2 == 0) {
				baseX = baseX + TilesUtils.TILE_OFFSET;
			} else {
				baseX = baseX - TilesUtils.TILE_OFFSET;
			}
		}
		
	}

	public Cell[] getAdjacentTo(Cell cell) {
		return getAdjacentToInEvenRow(cell);
	}

	private Cell[] getAdjacentToInEvenRow(Cell cell) {		
		List<Cell> cells = new ArrayList<Cell>();
		
		for (int i = 0; i < 6; i++) {
			try {
				if (cell.getMatrixIndiceRow()%2 == 0) 
					cells.add(this.boardMatrix[cell.getMatrixIndiceRow() + this.possibleNeighborPositions[i][0]]
											  [cell.getMatrixIndiceColumn() + this.possibleNeighborPositions[i][1]]);
				else if (i < 2 || i > 3)
					cells.add(this.boardMatrix[cell.getMatrixIndiceRow() + this.possibleNeighborPositions[i][0]]
											  [cell.getMatrixIndiceColumn() + this.possibleNeighborPositions[i][1]+1]);
				else
					cells.add(this.boardMatrix[cell.getMatrixIndiceRow() + this.possibleNeighborPositions[i][0]]
							  [cell.getMatrixIndiceColumn() + this.possibleNeighborPositions[i][1]]);
			} catch (ArrayIndexOutOfBoundsException e) {}
		}
		return cells.toArray(new Cell[6]);
	}
	
}
