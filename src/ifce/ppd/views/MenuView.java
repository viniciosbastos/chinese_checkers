package ifce.ppd.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MenuView {
	
	private Scene scene;
	private StackPane root;
	
	private TextArea nameTextArea;
	private TextArea ipAddressTextArea;
	private TextArea portTextArea;
	
	private Button createServerButton;
	private Button connectToServerButton;
	private Button startGameButton;

	public MenuView() {
		this.root = new StackPane();
	}
	
	public void createFirstScreen() {
		VBox firstScreen = new VBox();		
		
//		firstScreen.setAlignment(Pos.CENTER);
//		firstScreen.setStyle("-fx-background-color: white");
		firstScreen.getStyleClass().add("box");
		
		createServerButton = new Button("Criar Servidor");
		createServerButton.getStyleClass().add("custom-button");
		connectToServerButton = new Button("Conectar");
		connectToServerButton.getStyleClass().add("custom-button");
		
		firstScreen.getChildren().addAll(createServerButton, connectToServerButton);		
		this.root.getChildren().add(firstScreen);
	}
	
	public void createLoginScene() {
		GridPane grid = new GridPane();
		grid.getStyleClass().add("grid");
		
		grid.add(createNameTextField(), 0, 0, 2, 1);
		grid.add(createIPTextField(), 0, 1);		
		grid.add(createPortTextField(), 1, 1);		
		grid.add(createStartButton(), 0, 3, 2, 1);
		
		this.root.getChildren().add(grid);
	}
		
	public void createScene() {
		this.scene = new Scene(root, 400, 300);
		this.scene.getStylesheets().add("ifce/ppd/styles/menu.css");
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	private TextArea createNameTextField() {
		nameTextArea = new TextArea();
		nameTextArea.setPromptText("Nome do Jogador");
		nameTextArea.getStyleClass().add("input-text-area");
		return nameTextArea;
	}
	
	private TextArea createIPTextField() {
		ipAddressTextArea = new TextArea();
		ipAddressTextArea.setPromptText("Endereço IP");
		ipAddressTextArea.getStyleClass().add("input-text-area");
		return ipAddressTextArea;
	}
	
	private TextArea createPortTextField() {
		portTextArea = new TextArea();
		portTextArea.setPromptText("Porta");
		portTextArea.getStyleClass().add("input-text-area");
		return portTextArea;
	}
	
	private Button createStartButton() {
		startGameButton = new Button("Iniciar Jogo");
		startGameButton.getStyleClass().add("custom-button");
		return startGameButton;
	}

	public TextArea getNameTextArea() {
		return nameTextArea;
	}

	public TextArea getIpAddressTextArea() {
		return ipAddressTextArea;
	}

	public TextArea getPortTextArea() {
		return portTextArea;
	}

	public Button getCreateServerButton() {
		return createServerButton;
	}

	public Button getConnectToServerButton() {
		return connectToServerButton;
	}

	public Button getStartGameButton() {
		return startGameButton;
	}
}
