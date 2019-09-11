package ifce.ppd.controllers;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import ifce.ppd.bindings.ObservableStringBufferBiding;
import ifce.ppd.models.Board;
import ifce.ppd.models.Cell;
import ifce.ppd.models.Player;
import ifce.ppd.threads.CommunicationImpl;
import ifce.ppd.utils.AreaUtils;
import ifce.ppd.views.GameView;
import javafx.scene.control.Button;
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
	private boolean hasMoved;
	private boolean hasJumped;
	private ObservableStringBufferBiding buffer;	
	private CommunicationImpl serverCommunicationImpl;	
	private ICommunication oponentCommunication;
	private Registry registry;
	
	public GameViewController(Stage stage, Player player, Player oponent) {
		view = new GameView(stage);
		view.setPlayerColor(player.getColor());
		view.setOponentColor(oponent.getColor());
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
		this.hasMoved = false;
		
		// Inicializa a 'lado servidor' do jogador.
		initServerCommunication();
		if (this.player.getPlayerId() == 2) {
			initClientCommunication();
		}
	}
	
	private void closeGame() {
		try {
			this.registry.unbind("Communication" + player.getPlayerId());			
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	private void initClientCommunication() {
		try {
			this.oponentCommunication = (ICommunication) LocateRegistry.getRegistry(3000).lookup("Communication" + oponent.getPlayerId());
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	private void initServerCommunication() {
		try {
			this.serverCommunicationImpl = new CommunicationImpl(this.board, this.buffer, this.view, () -> {initClientCommunication();});
			this.serverCommunicationImpl.setResetFunction(() -> resetState());
			ICommunication stub = (ICommunication) UnicastRemoteObject.exportObject(serverCommunicationImpl, 0);			
			if (this.player.getPlayerId() == 1)
				registry = LocateRegistry.createRegistry(3000);
			else 
				registry = LocateRegistry.getRegistry(3000);
			registry.rebind("Communication" + player.getPlayerId(), stub);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
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
		this.hasMoved = true;
		this.sendMoveCommand(from, to);
		
		if (this.board.testVictoryOfPlayer(player)) {
			try {
				this.oponentCommunication.victory(player);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			this.view.showVictoryPane();
			this.view.showResetButton();
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
		this.hasMoved = true;
		this.sendMoveCommand(from, to);
		
		if (this.board.testVictoryOfPlayer(player)) {
			try {
				this.oponentCommunication.victory(player);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			this.view.showVictoryPane();
			this.view.showResetButton();
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
	
	private void initRestartButton( ) {
		Button restartButton = new Button("Reiniciar Jogo");
		restartButton.getStyleClass().addAll("custom-button", "full-button");
		restartButton.setOnMouseClicked(e -> restartGame());
		this.view.setRestartButton(restartButton);
	}
	
	public void createGameScene() {
		initRestartButton();
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
		TurnController.turn++;		
	}
	
	private void sendMessage() {
		String text = this.view.getMessageTextArea().getText();
		if (text != null && !text.isEmpty()) {
			this.buffer.append("Voc�: " + text);
			this.view.getMessageTextArea().clear();
			try {
				this.oponentCommunication.sendMessage(player, text);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	private void endTurn() {
		if (hasMoved) {
			this.clearHighlightedCells();
			this.movingPiece = null;
			this.canMove = true;
			this.hasJumped = false;
			this.hasMoved = false;
			this.view.addClickPreventionPane();
			this.view.showOponentTurn();
			TurnController.turn++;
			try {
				this.oponentCommunication.endTurn(TurnController.turn);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void giveUp() {
		this.clearHighlightedCells();
		try {
			this.oponentCommunication.giveup();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.view.showGivenUpPane();
		this.view.showResetButton();
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
		try {
			this.oponentCommunication.move(from.getMatrixIndexRow(), from.getMatrixIndexColumn(), to.getMatrixIndexRow(), to.getMatrixIndexColumn(), player);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void startGame() {
		createGameScene();
		if (this.player.getPlayerId() == 1) {
			this.view.showWaitingScene();
			this.view.showPlayerTurn();
		}
		else {
			try {
				this.oponentCommunication.startGame();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			this.view.showOponentTurn();
		}
	}	
	
	private void restartGame() {
		try {
			this.oponentCommunication.restartGame();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		resetState();
	}
	
	private void resetState() {
		resetBoard();
		resetStatesFlag();
		initPlayerArea();
		initOponentsArea(oponent);
		
		this.view.showControlButtons();
		this.view.resetBoard();
		if (player.getPlayerId() == 2) {
			this.view.addClickPreventionPane();
			this.view.showOponentTurn();
		} else {
			this.view.showPlayerTurn();			
		}
		TurnController.turn = 1;
	}
	
	private void resetBoard() {
		for (int i = 0; i < Board.BOARD_HEIGHT; i++) {
			for (int j = 0; j < Board.BOARD_WIDTH; j++) {
				Cell cell = board.getBoardMatrix()[i][j];
				if (cell != null) {
					cell.reset();			
				}
			}
		}
	}
	
	private void resetStatesFlag() {
		this.canMove = true;
		this.hasJumped = false;
		this.hasMoved = false;
	}
}
