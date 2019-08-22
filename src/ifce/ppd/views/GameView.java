package ifce.ppd.views;

import java.net.URISyntaxException;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameView {

	private Stage stage;
	private Scene gameScene;
	private TextArea chatTextArea;
	private TextArea messageTextArea;
	private VBox chatArea;
	private StackPane boardPane;
	private VBox boardArea;
	private Button endTurnButton;
	private Button giveUpButton;
	private Button sendMessageButton;
	private Button restartButton;
	private Pane transparentPane;
	private Pane waitingPane;
	private StackPane mainGamePane;
	
	private BorderPane victoryPane;
	private BorderPane defeatPane;
	private BorderPane givenUpPane;
	private BorderPane turnPane;
	private Label turnLabel;
	
	private HBox buttonsHBox;
	private String playerColor;
	private String oponentColor;
	
	private Pane board;
	
	public GameView(Stage stage) {
		this.stage = stage;
		this.mainGamePane = new StackPane();
		this.transparentPane = new Pane();
		this.transparentPane.getStyleClass().add("transparent");		
		this.waitingPane = createWaitingPane();
		this.boardPane = new StackPane();
		this.boardArea = new VBox();
		this.buttonsHBox = new HBox();

		initPanes();
	}
		
	private void initPanes() {
		createVictoryPane();
		createDefeatPane();
		createGivenUpPane();
	}

	private void createGivenUpPane() {
		this.givenUpPane = new BorderPane();
		this.givenUpPane.getStyleClass().add("waiting-panel");
		Image image;
		try {
			image = new Image(getClass().getResource("/ifce/ppd/images/give_up.jpg").toURI().toString());		
			ImageView imageView = new ImageView(image);			
			this.givenUpPane.setCenter(imageView);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void createDefeatPane() {
		this.defeatPane = new BorderPane(); 
		this.defeatPane.getStyleClass().add("waiting-panel");
		Image image;
		try {
			image = new Image(getClass().getResource("/ifce/ppd/images/game_over.jpg").toURI().toString());
			ImageView imageView = new ImageView(image);
			this.defeatPane.setCenter(imageView);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void createVictoryPane() {
		this.victoryPane = new BorderPane();
		this.victoryPane.getStyleClass().add("waiting-panel");
		Image image;
		try {
			image = new Image(getClass().getResource("/ifce/ppd/images/victory.jpg").toURI().toString());
			ImageView imageView = new ImageView(image);
			this.victoryPane.setCenter(imageView);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public void createChatArea() {
		chatArea = new VBox();		
		this.chatTextArea = new TextArea();
//		chatTextArea.setMouseTransparent(true);
		chatTextArea.setFocusTraversable(false);
		chatTextArea.getStyleClass().addAll("chat-text-area", "no-focus-effect");
		
		
		HBox messageHBox = new HBox();
		
		turnPane = new BorderPane();		
		turnLabel = new Label();
		turnPane.setCenter(turnLabel);

		this.messageTextArea = new TextArea();
		messageTextArea.getStyleClass().addAll("chat-text-input", "no-focus-effect");
		messageTextArea.setPromptText("Type your message here...");
		this.sendMessageButton = new Button();
		this.sendMessageButton.setText("->");
		this.sendMessageButton.getStyleClass().add("chat-send-button");
		
		messageHBox.getChildren().addAll(messageTextArea, sendMessageButton);
		
		buttonsHBox.getStyleClass().add("button-box");
		
		this.endTurnButton = new Button("Finalizar Turno");
		this.endTurnButton.getStyleClass().add("custom-button");
		this.giveUpButton = new Button("Desistir");
		this.giveUpButton.getStyleClass().add("custom-button");
		
		buttonsHBox.getChildren().addAll(endTurnButton, giveUpButton);
		chatArea.getChildren().addAll(turnPane, chatTextArea, messageHBox, buttonsHBox);
	}
	
	public BorderPane createWaitingPane() {
		BorderPane pane = new BorderPane();
		pane.getStyleClass().add("waiting-panel");
		
		Label label = new Label("Esperando jogador...");
		
		pane.setCenter(label);
		return pane;
	}

	public void createGameScene() {
		GridPane gridPane = new GridPane();
		gridPane.getColumnConstraints().addAll(new ColumnConstraints(800), 
												new ColumnConstraints(400));
		gridPane.add(this.boardPane, 0, 0);
		gridPane.add(this.chatArea, 1, 0);							
		
		this.mainGamePane.getChildren().add(gridPane);
		gameScene = new Scene(mainGamePane, 1200, 600);
		gameScene.getStylesheets().add("ifce/ppd/styles/game.css");
		this.stage.setScene(this.gameScene);
	}
	
	public Scene getScene() {
		return this.gameScene;
	}

	public TextArea getChatTextArea() {
		return chatTextArea;
	}

	public TextArea getMessageTextArea() {
		return messageTextArea;
	}

	public VBox getChatArea() {
		return chatArea;
	}

	public void setBoardArea(StackPane boardArea) {
		this.boardPane = boardArea;
	}
	
	public Pane getBoardPane() {
		return boardPane;
	}

	public Button getEndTurnButton() {
		return endTurnButton;
	}
	
	public Button getGiveUpButton() {
		return giveUpButton;
	}

	public Button getSendMessageButton() {
		return this.sendMessageButton;
	}
	
	public void addBoard(Pane board) {
		this.board = board;
		this.boardPane.getChildren().add(board);		
	}
	
	public void addClickPreventionPane() {
		this.boardPane.getChildren().add(this.transparentPane);		
	}
	
	public void removeClickPreventionPane() {
		this.boardPane.getChildren().remove(this.transparentPane);
	}
	
	public void showWaitingScene() {
		this.mainGamePane.getChildren().add(this.waitingPane);
	}

	public void removeWaitingPane() {
		Platform.runLater(() -> {
			this.mainGamePane.getChildren().remove(this.waitingPane);
		}); 
	}
	
	public void showVictoryPane() {
		this.boardPane.getChildren().add(this.victoryPane);
	}
	
	public void showDefeatPane() {
		Platform.runLater(() -> {
			this.boardPane.getChildren().add(this.defeatPane);			
		});
	}
	
	public void showGivenUpPane() {
		this.boardPane.getChildren().add(this.givenUpPane);
	}
		
	public VBox getBoardArea() {
		return this.boardArea;
	}
	
	public void setPlayerColor(String playerColor) {
		this.playerColor = playerColor;
	}

	public void setOponentColor(String oponentColor) {
		this.oponentColor = oponentColor;
	}

	public void setGameScene(Scene gameScene) {
		this.gameScene = gameScene;
	}

	public void setChatTextArea(TextArea chatTextArea) {
		this.chatTextArea = chatTextArea;
	}

	public void setMessageTextArea(TextArea messageTextArea) {
		this.messageTextArea = messageTextArea;
	}

	public void setChatArea(VBox chatArea) {
		this.chatArea = chatArea;
	}

	public void setBoardPane(StackPane boardPane) {
		this.boardPane = boardPane;
	}

	public void setBoardArea(VBox boardArea) {
		this.boardArea = boardArea;
	}

	public void setEndTurnButton(Button endTurnButton) {
		this.endTurnButton = endTurnButton;
	}

	public void setGiveUpButton(Button giveUpButton) {
		this.giveUpButton = giveUpButton;
	}

	public void setMainGamePane(StackPane mainGamePane) {
		this.mainGamePane = mainGamePane;
	}

	public void setDefeatPane(BorderPane defeatPane) {
		this.defeatPane = defeatPane;
	}

	public void setGivenUpPane(BorderPane givenUpPane) {
		this.givenUpPane = givenUpPane;
	}

	public void setButtonsHBox(HBox buttonsHBox) {
		this.buttonsHBox = buttonsHBox;
	}

	public void showPlayerTurn() {
		this.turnLabel.setText("Sua Vez");
		this.turnPane.setStyle("-fx-background-color: " + playerColor + "");
	}
	
	public void showOponentTurn() {
		this.turnLabel.setText("Vez do Oponente");
		this.turnPane.setStyle("-fx-background-color: " + oponentColor + "");
	}
	
	public Button getRestartButton() {
		return this.restartButton;
	}
	
	public void setRestartButton(Button restartButton) {
		this.restartButton = restartButton;
	}
	
	public void showResetButton() {
		this.buttonsHBox.getChildren().clear();
		this.buttonsHBox.getChildren().add(this.restartButton);
	}
	
	public void resetBoard() {
		this.boardPane.getChildren().clear();
		this.boardPane.getChildren().add(board);
	}

	public void showControlButtons() {
		this.buttonsHBox.getChildren().clear();
		buttonsHBox.getChildren().addAll(endTurnButton, giveUpButton);
	}
	
}
