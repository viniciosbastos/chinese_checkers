package ifce.ppd.threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

import ifce.ppd.models.Command;

public class ReceiverThread implements Runnable{

	private ObjectInputStream inputStream;	
	private List<Command> receivedCommands;
	private Object lock;
	
	private boolean isRunning;
	
	public ReceiverThread(List<Command> receivedCommands, Object lock) {
		this.receivedCommands = receivedCommands;
		this.lock = lock;
		this.isRunning = true;
	}
	
	public void setSocket(Socket socket) throws IOException {
		this.inputStream = new ObjectInputStream(socket.getInputStream());
	}
	
	@Override
	public void run() {
		while (isRunning) {
			try {
				Command c = (Command) this.inputStream.readObject();
				this.receivedCommands.add(c);
				releaseUpdateThread();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				stop();
			}
		}
	}

	private void releaseUpdateThread() {
		synchronized (lock) {
			lock.notify();
		}
	}
	
	public void stop() {
		this.isRunning = false;
	}

}
