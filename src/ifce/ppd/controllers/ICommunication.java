package ifce.ppd.controllers;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ifce.ppd.models.Cell;
import ifce.ppd.models.Player;

public interface ICommunication extends Remote{

	void sendMessage(Player player, String message) throws RemoteException;
	void move(int fromx, int fromy, int tox, int toy, Player player) throws RemoteException;
	void startGame() throws RemoteException;
	void victory(Player player) throws RemoteException;
	void restartGame() throws RemoteException;
	void endTurn(int turn) throws RemoteException;
	void giveup() throws RemoteException;
	
}
