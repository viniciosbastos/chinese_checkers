package ifce.ppd.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ifce.ppd.bindings.ObservableStringBufferBiding;
import ifce.ppd.models.Address;
import ifce.ppd.models.Board;
import ifce.ppd.models.Cell;
import ifce.ppd.models.EndTurnCommand;
import ifce.ppd.models.GiveUpCommand;
import ifce.ppd.models.MessageCommand;
import ifce.ppd.models.MoveCommand;
import ifce.ppd.models.Player;
import ifce.ppd.models.VictoryCommand;
import ifce.ppd.threads.GameViewUpdaterThread;
import ifce.ppd.utils.AreaUtils;
import ifce.ppd.views.GameView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameViewController {
	
	private GameView view;
	private Board board;
	private Player player;
	private Player oponent;
	private List<Cell> possibleMoves;
	private Cell movingPiece;
	private boolean canMove;
	private boolean hasJumped;
	private ObservableStringBufferBiding buffer;
	private CommunicationController communicationController;
	private GameViewUpdaterThread gameViewUpdaterThread;
	private int turn;

	
	public GameViewController(Stage stage, Player player, Player oponent, Address address) {
		this.turn = 1;
		view = new GameView(stage);
		stage.setOnCloseRequest(e -> closeGame());
		stage.setTitle("Chinese Checkers - " + player.getPlayerId());
		board = new Board();
		board.createBoard();
		this.oponent = oponent;
		this.player = player;
		this.possibleMoves = new ArrayList<Cell>();
		this.buffer = new ObservableStringBufferBiding();
		this.canMove = true;
		this.hasJumped = false;
		
		this.communicationController = new CommunicationController(address.getIpAddress(), address.getPort());
		initUpdaterThread();
	}
	
	private void initUpdaterThread() {
		this.gameViewUpdaterThread = new GameViewUpdaterThread(this.board, this.buffer, this.communicationController.getReceivedCommands(), this.communicationController.getUpdateViewLock(), this.view);
		Thread updater = new Thread(this.gameViewUpdaterThread);
		updater.start();
		
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
		this.sendMoveCommand(from, to);
		
		if (this.board.testVictoryOfPlayer(player)) {
			this.communicationController.addCommand(new VictoryCommand(this.player));
			this.view.showVictoryPane();
		}
	}
	
	private void jumpToCell(Cell from, Cell to) {		
		this.clearHighlightedCells();
		this.movingPiece = to;
		to.setOwner(player);
		to.getTile().setOnMouseClicked(e -> selectPieceToMove(to));
		from.reset();
		this.canMove = true;
		this.hasJumped = true;
		this.sendMoveCommand(from, to);
		
		if (this.board.testVictoryOfPlayer(player)) {
			this.communicationController.addCommand(new VictoryCommand(this.player));
			this.view.showVictoryPane();
		}
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
	

	
	public void createGameScene() {
		this.view.createChatArea();	
		this.view.getChatTextArea().textProperty().bind(buffer);
		this.buffer.addListener(listener -> {
			this.view.getChatTextArea().selectPositionCaret(this.view.getChatTextArea().getLength());
			this.view.getChatTextArea().deselect();
		});
		
		this.view.getEndTurnButton().setOnMouseClicked(e -> endTurn());
		this.view.getGiveUpButton().setOnMouseClicked(e -> giveUp());
		this.view.getSendMessageButton().setOnMouseClicked(e -> sendMessage());
		this.view.addBoard(createBoard(board));
		if (this.player.getPlayerId() == 2)
			this.view.addClickPreventionPane();
		this.initPlayerArea();
		this.initOponentsArea(oponent);
		this.view.createGameScene();
	}
	
	private void sendMessage() {
		String text = this.view.getMessageTextArea().getText();
		if (text != null && !text.isEmpty()) {
			// Send message to opponent player
			// after response does:
			this.buffer.append("Você: " + text);
			this.view.getMessageTextArea().clear();
			this.communicationController.addCommand(new MessageCommand(text));
		}
	}

	private void endTurn() {
		// Send info ending turn to opponent player
		// after response does:
		this.clearHighlightedCells();
		this.movingPiece = null;
		this.canMove = true;
		this.hasJumped = false;
		this.view.addClickPreventionPane();
		this.turn++;
		this.communicationController.addCommand(new EndTurnCommand(this.turn));
	}
	
	private void giveUp() {
		this.clearHighlightedCells();
		this.communicationController.addCommand(new GiveUpCommand());
		this.view.showGivenUpPane();
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
	
	private void initOponentsArea(Player oponent) {		
		for (int[] position : AreaUtils.getArea(oponent.getPlayerArea())) {
			Cell cell = this.board.getBoardMatrix()[position[0]][position[1]];
			cell.setOwner(oponent);
		}
	}
	
	private void initPlayerArea() {		
		for (int[] position : AreaUtils.getArea(player.getPlayerArea())) {
			Cell cell = this.board.getBoardMatrix()[position[0]][position[1]];
			cell.setOwner(player);
			cell.getTile().setOnMouseClicked(e -> selectPieceToMove(cell));
		}
	}
	
	private void sendMoveCommand(Cell from, Cell to) {
		this.communicationController.addCommand(new MoveCommand(from, to, this.player));		
	}
	
	public void createServer() {
		try {
			this.communicationController.createServer();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void connect() {
		try {
			this.communicationController.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeGame() {
		if (this.gameViewUpdaterThread != null)
			this.gameViewUpdaterThread.stop();	
		this.communicationController.stopCommunication();
	}

	public void startGame() {
		createGameScene();
		if (this.player.getPlayerId() == 1) {
			this.view.showWaitingScene();
			new Thread(() -> createServer()).start();
		}
		else {
			connect(); 
		}
	}	
}
