package ifce.ppd.threads;

import java.rmi.RemoteException;

import ifce.ppd.bindings.ObservableStringBufferBiding;
import ifce.ppd.controllers.ICommunication;
import ifce.ppd.controllers.TurnController;
import ifce.ppd.models.Board;
import ifce.ppd.models.Cell;
import ifce.ppd.models.Player;
import ifce.ppd.views.GameView;
import javafx.application.Platform;

public class CommunicationImpl implements ICommunication{
	
	private GameView view;
	private Board board;
	private ObservableStringBufferBiding chatTextAreaBinBiding;		
	private Runnable resetFunction;
	private Runnable clientInitFunction;

	public CommunicationImpl(Board board, ObservableStringBufferBiding chatTextAreaBinBiding, GameView view, Runnable clientInitFunction) throws RemoteException{
		this.board = board;
		this.chatTextAreaBinBiding = chatTextAreaBinBiding;
		this.view = view;
		this.clientInitFunction = clientInitFunction;
	}
	
	public void setResetFunction(Runnable resetFunction) {
		this.resetFunction = resetFunction;
	}


	@Override
	public void sendMessage(Player player, String message) {
		this.chatTextAreaBinBiding.append(player.getName() + ": "  + message);
	}


	@Override
	public void move(int fromx, int fromy, int tox, int toy, Player player) {
		Cell fromCell = this.board.getBoardMatrix()[fromx][fromy];
		fromCell.reset();
		
		Cell toCell = this.board.getBoardMatrix()[tox][toy];
		toCell.setOwner(player);
	}


	@Override
	public void startGame() {
		this.view.removeWaitingPane();
		clientInitFunction.run();
	}


	@Override
	public void victory(Player player) {
		if (this.board.testVictoryOfPlayer(player)) {
			this.chatTextAreaBinBiding.append("Voc� perdeu.");
			this.view.showDefeatPane();
			Platform.runLater(() -> this.view.showResetButton());
		}
	}


	@Override
	public void restartGame() {
		Platform.runLater(() -> this.resetFunction.run());
	}


	@Override
	public void endTurn(int turn) {
		Platform.runLater(() -> {
			TurnController.turn++;
			this.view.removeClickPreventionPane();
			this.view.showPlayerTurn();			
		});
	}


	@Override
	public void giveup() {
		Platform.runLater(() -> {
			this.view.showVictoryPane();
			this.view.showResetButton();	
		});
	}

}
