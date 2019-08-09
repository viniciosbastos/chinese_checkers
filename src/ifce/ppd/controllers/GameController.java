package ifce.ppd.controllers;

import ifce.ppd.models.Board;
import ifce.ppd.models.Cell;
import ifce.ppd.views.GameView;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GameController {
	private GameView view;
	
	private Board board;

	public GameController() {
		view = new GameView();
		board = new Board();
		board.createBoard();
	}
	
	private void tileSelected(Cell cell) {
		cell.getTile().getStyleClass().add("hex-active");
		this.highlightPossibleMoves(cell);
	}
	
	public void highlightPossibleMoves(Cell cell) {
		for (Cell c : this.board.getAdjacentTo(cell)) {
			if (c != null)
				c.getTile().getStyleClass().add("hex-highlight");
		}
	}
	
	public Scene createScene() {
		this.view.createChatArea();		
		this.view.setBoardArea(createBoard(board));
		
		return this.view.createScene();
	}
	
	private Pane createBoard(Board board) {
		Pane boardPane = new Pane();
		
		for (int i = 0; i < Board.BOARD_HEIGHT; i++) {
			for (int j = 0; j < Board.BOARD_WIDTH; j++) {
				Cell cell = board.getBoardMatrix()[i][j];
				if (cell != null) {
					cell.getTile().setOnMouseClicked(e -> tileSelected(cell));					
					boardPane.getChildren().add(cell.getTile());			
				}
			}
		}
		
		return boardPane;
	}
	
}
