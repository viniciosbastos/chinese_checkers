package ifce.ppd.threads;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import ifce.ppd.models.Command;

public class SenderThread implements Runnable{

	private ObjectOutputStream outputStream;
	private List<Command> toSend;
	
	private Object lock;
	
	private boolean isRunning;
	
	public SenderThread(List<Command> toSend, Object lock) {
		this.toSend = toSend;
		this.lock = lock;
		this.isRunning = true;
	}
	
	public void setSocket(Socket socket) throws IOException {
		this.outputStream = new ObjectOutputStream(socket.getOutputStream()) ;
	}
	
	@Override
	public void run() {
		while (isRunning) {
			if (!this.toSend.isEmpty()) {
				Command c = this.toSend.remove(0);
				sendObject(c);
			} else {
				blockThread();
			}
		}
	}
	
	private void sendObject(Command c) {
		try {
			outputStream.writeObject(c);
		} catch (IOException e) {
			e.printStackTrace();
			stop();
		}
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
