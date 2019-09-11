package ifce.ppd.views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MenuView {
	
	private Scene scene;
	private StackPane root;
	
	private TextArea nameTextArea;
	
	private Button createServerButton;
	private Button connectToServerButton;
	private Button startGameButton;

	public MenuView() {
		this.root = new StackPane();
	}
	
	public void createFirstScreen() {
		VBox firstScreen = new VBox();				
		firstScreen.getStyleClass().add("box");
		
		Label label = new Label("Chinese Checkers");
		label.getStyleClass().add("title-label");
		createServerButton = new Button("Criar Servidor");
		createServerButton.getStyleClass().add("custom-button");
		connectToServerButton = new Button("Conectar");
		connectToServerButton.getStyleClass().add("custom-button");
		
		firstScreen.getChildren().addAll(label, createServerButton, connectToServerButton);		
		this.root.getChildren().add(firstScreen);
	}
	
	public void createLoginScene() {
		GridPane grid = new GridPane();
		grid.getStyleClass().add("grid");
		
		Label label = new Label("Chinese Checkers");
		label.getStyleClass().add("title-label");
		
		grid.add(label, 0, 0, 2, 1);
		grid.add(createNameTextField(), 0, 1, 2, 1);
		grid.add(createStartButton(), 0, 2, 2, 1);
		
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
	
	private Button createStartButton() {
		startGameButton = new Button("Iniciar Jogo");
		startGameButton.getStyleClass().add("custom-button");
		return startGameButton;
	}

	public TextArea getNameTextArea() {
		return nameTextArea;
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
