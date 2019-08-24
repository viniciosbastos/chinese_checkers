package ifce.ppd.threads;

import java.util.List;

import ifce.ppd.bindings.ObservableStringBufferBiding;
import ifce.ppd.controllers.TurnController;
import ifce.ppd.models.Board;
import ifce.ppd.models.Cell;
import ifce.ppd.models.Command;
import ifce.ppd.models.EndTurnCommand;
import ifce.ppd.models.GiveUpCommand;
import ifce.ppd.models.MessageCommand;
import ifce.ppd.models.MoveCommand;
import ifce.ppd.models.RestartCommand;
import ifce.ppd.models.StartGameCommand;
import ifce.ppd.models.VictoryCommand;
import ifce.ppd.views.GameView;
import javafx.application.Platform;

public class GameViewUpdaterThread implements Runnable{
	
	private GameView view;
	private Board board;
	private ObservableStringBufferBiding chatTextAreaBinBiding;		
	private List<Command> receivedCommands;
	private Object lock;
	private boolean isRunning;
	private Runnable resetRunnable;

	public GameViewUpdaterThread(Board board, ObservableStringBufferBiding chatTextAreaBinBiding, List<Command> receivedCommands, Object lock, GameView view) {
		this.board = board;
		this.chatTextAreaBinBiding = chatTextAreaBinBiding;
		this.receivedCommands = receivedCommands;
		this.lock = lock;
		this.isRunning = true;
		this.view = view;
	}
	
	@Override
	public void run() {
		while (isRunning) {
			if (!receivedCommands.isEmpty()) {				
				handleCommand(this.receivedCommands.remove(0));
			} else {
				blockThread();
			}
		}
	}

	private void handleCommand(Command command) {
		if (command instanceof StartGameCommand) {
			startGame();
		} else if (command instanceof MoveCommand) {
			moveOponentPiece((MoveCommand) command);
		} else if (command instanceof MessageCommand) {
			addMessageToChat((MessageCommand) command);
		} else if (command instanceof EndTurnCommand) {
			changeTurn((EndTurnCommand) command);
		} else if (command instanceof VictoryCommand) {
			testVictoryOfOponent((VictoryCommand) command);
		} else if (command instanceof GiveUpCommand) {
			showVictory();
		} else if(command instanceof RestartCommand) {
			restartGame();
		}
		
	}

	private void restartGame() {
		Platform.runLater(() -> this.resetRunnable.run());
	}

	private void showVictory() {
		Platform.runLater(() -> {
			this.view.showVictoryPane();
			this.view.showResetButton();	
		});
	}

	private void startGame() {		
		this.view.removeWaitingPane();
	}

	private void testVictoryOfOponent(VictoryCommand command) {
		if (this.board.testVictoryOfPlayer(command.getVictoriousPlayer())) {
			this.chatTextAreaBinBiding.append("Voc� perdeu.");
			this.view.showDefeatPane();
			Platform.runLater(() -> this.view.showResetButton());
		}
	}

	private void changeTurn(EndTurnCommand command) {
		Platform.runLater(() -> {
			TurnController.turn++;
			this.view.removeClickPreventionPane();
			this.view.showPlayerTurn();			
		});
	}

	private void addMessageToChat(MessageCommand messageCommand) {		
		this.chatTextAreaBinBiding.append(messageCommand.getSender().getName() + ": "  + messageCommand.getText());
	}

	private void moveOponentPiece(MoveCommand command) {
		Cell fromCell = this.board.getBoardMatrix()[command.getFromRowIndex()][command.getFromColumnIndex()];
		fromCell.reset();
		
		Cell toCell = this.board.getBoardMatrix()[command.getToRowIndex()][command.getToColumnIndex()];
		toCell.setOwner(command.getPlayer());
	}

	private void blockThread() {
		synchronized (lock) {
			try {
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stop() {
		this.isRunning = false;
	}
	
	public void setResetFunction(Runnable resetRunnable) {
		this.resetRunnable = resetRunnable;
	}

}
