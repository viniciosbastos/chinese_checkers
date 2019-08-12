package ifce.ppd.threads;

import java.util.List;

import ifce.ppd.bindings.ObservableStringBufferBiding;
import ifce.ppd.models.Board;
import ifce.ppd.models.Cell;
import ifce.ppd.models.Command;
import ifce.ppd.models.MessageCommand;
import ifce.ppd.models.MoveCommand;

public class GameViewUpdaterThread implements Runnable{
	
	private Board board;
	
	private ObservableStringBufferBiding chatTextAreaBinBiding;
	
	private List<Command> receivedCommands;
	
	private Object lock;

	private boolean isRunning;

	public GameViewUpdaterThread(Board board, ObservableStringBufferBiding chatTextAreaBinBiding, List<Command> receivedCommands, Object lock) {
		this.board = board;
		this.chatTextAreaBinBiding = chatTextAreaBinBiding;
		this.receivedCommands = receivedCommands;
		this.lock = lock;
		this.isRunning = true;
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
		if (command instanceof MoveCommand) {
			moveOponentPiece((MoveCommand) command);
		} else if (command instanceof MessageCommand) {
			addMessageToChat((MessageCommand) command);
		} 
		
	}

	private void addMessageToChat(MessageCommand messageCommand) {		
		this.chatTextAreaBinBiding.append("Oponente: "  + messageCommand.getText());
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

}