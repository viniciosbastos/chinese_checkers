package ifce.ppd.controllers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import ifce.ppd.models.Command;
import ifce.ppd.models.StartGameCommand;
import ifce.ppd.threads.ReceiverThread;
import ifce.ppd.threads.SenderThread;

public class CommunicationController {
	private Socket socket;
	private ServerSocket serverSocket;
	private String host;
	private int port;
	
	// Defining objects of threads responsible for sending and receiving commands between the clients 
	private SenderThread senderThread;
	private ReceiverThread receiverThread;
	
	private List<Command> toSend;
	private List<Command> receivedCommands;
	
	private Object sendCommandLock;
	private Object updateViewLock;
	
	public CommunicationController(String host, int port) {
		this.sendCommandLock = new Object();
		this.updateViewLock = new Object();
		
		this.host = host;
		this.port = port;
		
		this.toSend = new ArrayList<Command>();
		this.receivedCommands = new ArrayList<Command>();
		this.senderThread = new SenderThread(this.toSend, this.sendCommandLock);
		this.receiverThread = new ReceiverThread(this.receivedCommands, this.updateViewLock);
	}
		
	public void createServer() throws IOException {
		this.serverSocket = new ServerSocket(this.port);
		this.socket = this.serverSocket.accept();
		
		this.senderThread.setSocket(socket);
		Thread sender = new Thread(this.senderThread);
		sender.start();
		
		this.receiverThread.setSocket(socket);
		Thread receiver = new Thread(this.receiverThread);
		receiver.start();
	}
	
	public void connect() throws UnknownHostException, IOException {
		this.socket = new Socket(this.host, this.port);
		this.senderThread.setSocket(socket);
		Thread sender = new Thread(this.senderThread);
		sender.start();
		
		this.receiverThread.setSocket(socket);
		Thread receiver = new Thread(this.receiverThread);
		receiver.start();
		
		this.addCommand(new StartGameCommand());
	}
	
	public void addCommand(Command c) {
		this.toSend.add(c);
		synchronized (sendCommandLock) {
			sendCommandLock.notify();
		}
	}
	
	public List<Command> getReceivedCommands() {
		return this.receivedCommands;
	}
	
	public Object getUpdateViewLock() {
		return this.updateViewLock;
	}

	public void stopCommunication() {
		this.senderThread.stop();
		this.receiverThread.stop();
		closeSocket();
		closeServer();
		
		relaseLock(this.sendCommandLock);
		relaseLock(this.updateViewLock);
	}

	private void relaseLock(Object lock) {
		synchronized (lock) {
			lock.notify();
		}
	}

	private void closeServer() {
		try {
			if (this.serverSocket != null)
				this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void closeSocket() {
		try {
			if (this.socket != null)
				this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
