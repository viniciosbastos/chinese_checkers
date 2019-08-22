package ifce.ppd.controllers;

import ifce.ppd.models.Address;
import ifce.ppd.models.Player;
import ifce.ppd.views.MenuView;
import javafx.stage.Stage;

public class MenuViewController {

	private Stage stage;
	private MenuView view;
	
	public MenuViewController(Stage stage) {
		this.view = new MenuView();
		this.stage = stage;
	}

	public void createScene() {		
		this.view.createScene();
		this.view.createFirstScreen();
		this.view.getCreateServerButton().setOnMouseClicked(e -> openCreateServerPane());
		this.view.getConnectToServerButton().setOnMouseClicked(e -> openConnectToServerPane());
				
		this.stage.setTitle("Chinese Checkers");		
		this.stage.setScene(this.view.getScene());
		this.stage.show();
	}
	
	private void openCreateServerPane() {
		this.view.createLoginScene();
		this.view.getIpAddressTextArea().setText("127.0.0.1");
		this.view.getIpAddressTextArea().setEditable(false);
		this.view.getStartGameButton().setOnMouseClicked(e -> startGame());
	}
	
	private void openConnectToServerPane() {
		this.view.createLoginScene();
		this.view.getStartGameButton().setOnMouseClicked(e -> connectToGame());
	}
	
	private void connectToGame() {
		Player oponent = new Player("blue", 4, 1, "");
		Player player = new Player("red", 1, 2, this.view.getNameTextArea().getText());
		createGameController(player, oponent);
	}

	private void startGame() {
		Player oponent = new Player("red", 1, 2, "");
		Player player = new Player("blue", 4, 1, this.view.getNameTextArea().getText());
		createGameController(player, oponent);
	}
	
	private void createGameController(Player player, Player oponent) {
		String ipAddress = this.view.getIpAddressTextArea().getText();
		int port = Integer.parseInt(this.view.getPortTextArea().getText());
		GameViewController gameController = new GameViewController(this.stage, player, oponent, new Address(ipAddress, port));
		gameController.startGame();
	}
}
