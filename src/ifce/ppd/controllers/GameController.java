package ifce.ppd.controllers;

import java.util.ArrayList;
import java.util.List;

import ifce.ppd.bindings.ObservableStringBufferBiding;
import ifce.ppd.models.Board;
import ifce.ppd.models.Cell;
import ifce.ppd.models.Player;
import ifce.ppd.utils.AreaUtils;
import ifce.ppd.views.GameView;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GameController {
	private GameView view;
	
	private Board board;
	
	private Player player;
	
	private Player oponent;
	
	private List<Cell> possibleMoves;
	
	private Cell movingPiece;
	
	private boolean canMove;
	
	private boolean hasJumped;
	
	private ObservableStringBufferBiding buffer;

	public GameController() {
		view = new GameView();
		board = new Board();
		board.createBoard();
		oponent= new Player("red", 1, 1);
		player= new Player("blue", 4, 1);
		this.possibleMoves = new ArrayList<Cell>();
		this.buffer = new ObservableStringBufferBiding();
		this.canMove = true;
		this.hasJumped = false;
	}
	
	private void selectPieceToMove(Cell cell) {
		if (!this.possibleMoves.isEmpty())
			this.clearHighlightedCells();
		if (canMove) {
			if (this.hasJumped && !cell.equals(this.movingPiece))
				return;
			
			if (this.movingPiece != null) this.movingPiece.deselectCell();
			this.movingPiece = cell;
			cell.selectCell();
			this.highlightPossibleMoves(cell);
		}
		
	}
	
	private void clearHighlightedCells() {
		for (Cell cell : this.possibleMoves) {		
			if (cell != null && cell.isEmpty()) {
				cell.reset();
			}
		}
		this.possibleMoves.clear();
	}
	
	private void moveToNeighbor(Cell from, Cell to) {
		this.movingPiece = to;
		this.clearHighlightedCells();
		to.setOwner(player);
		to.getTile().setOnMouseClicked(e -> selectPieceToMove(to));
		from.reset();
		this.canMove = false;
	}
	
	private void jumpToCell(Cell from, Cell to) {		
		this.clearHighlightedCells();
		this.movingPiece = to;
		to.setOwner(player);
		to.getTile().setOnMouseClicked(e -> selectPieceToMove(to));
		from.reset();
		this.canMove = true;
		this.hasJumped = true;
	}
	
	private void highlightNeighborMoves(Cell cell) {
		for (Cell possibleMove : this.possibleMoves) {
			if (possibleMove.isEmpty()) {
				possibleMove.setMovable(true);
				possibleMove.getTile().getStyleClass().add("hex-highlight");
				possibleMove.getTile().setOnMouseClicked(e -> moveToNeighbor(cell, possibleMove));
			}
		}
	}
	
	
	private void highlightJumpMoves(Cell cellToJump, Cell originCell) {		
		for (Cell possibleMove : this.board.getAdjacentTo(cellToJump)) {
			if (possibleMove.isEmpty() && !possibleMove.isMovable() ) {
				possibleMove.getTile().getStyleClass().add("hex-highlight");
				possibleMove.getTile().setOnMouseClicked(e -> jumpToCell(originCell, possibleMove));
				this.possibleMoves.add(possibleMove);
			}
		}
	}
	
	public void highlightPossibleMoves(Cell cell) {
		// Get and highlight neighbor cells
		List<Cell> neighborCells = this.board.getAdjacentTo(cell);
		if (!hasJumped) {
			this.possibleMoves.addAll(neighborCells);
			highlightNeighborMoves(cell);
		}
		
		// Get and highlight first jump
		for (Cell neighborCell : neighborCells) {
			if (!neighborCell.isEmpty()) {
				highlightJumpMoves(neighborCell, cell);
			}
		}
	}
	
	
	
	public Scene createScene() {
		this.view.createChatArea();	
		this.view.getChatTextArea().textProperty().bind(buffer);
		this.view.getEndTurnButton().setOnMouseClicked(e -> endTurn());
		this.view.getSendMessageButton().setOnMouseClicked(e -> sendMessage());
		this.view.setBoardArea(createBoard(board));
		this.initPlayerArea();
		this.initOponentsArea(oponent);		
		return this.view.createScene();
	}
	
	private void sendMessage() {
		String text = this.view.getMessageTextArea().getText();
		if (text != null && !text.isEmpty()) {
			// Send message to opponent player
			// after response does:
			this.buffer.append("Player: " + text);
			this.view.getMessageTextArea().clear();
		}
	}

	private void endTurn() {
		// Send info ending turn to opponent player
		// after response does:
		this.clearHighlightedCells();
		this.movingPiece = null;
		this.canMove = true;
		this.hasJumped = false;
		
		this.buffer.append("Next Turn");
	}

	private Pane createBoard(Board board) {
		Pane boardPane = new Pane();
		
		for (int i = 0; i < Board.BOARD_HEIGHT; i++) {
			for (int j = 0; j < Board.BOARD_WIDTH; j++) {
				Cell cell = board.getBoardMatrix()[i][j];
				if (cell != null) {
					boardPane.getChildren().add(cell.getTile());			
				}
			}
		}
		
		return boardPane;
	}
	
	private void initOponentsArea(Player player) {		
		for (int[] position : AreaUtils.getArea(player.getPlayerArea())) {
			Cell cell = this.board.getBoardMatrix()[position[0]][position[1]];
			cell.setOwner(player);
		}
	}
	
	private void initPlayerArea() {		
		for (int[] position : AreaUtils.getArea(player.getPlayerArea())) {
			Cell cell = this.board.getBoardMatrix()[position[0]][position[1]];
			cell.setOwner(player);
			cell.getTile().setOnMouseClicked(e -> selectPieceToMove(cell));
		}
	}
}
