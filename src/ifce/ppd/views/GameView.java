package ifce.ppd.views;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GameView {

	private Scene scene;
	
	private TextArea chatTextArea;
	
	private TextArea messageTextArea;
	
	private VBox chatArea;
	
	private Pane boardArea;
		
	public void createChatArea() {
		chatArea = new VBox();		
		this.chatTextArea = new TextArea();
		chatTextArea.setMouseTransparent(true);
		chatTextArea.setFocusTraversable(false);
		chatTextArea.getStyleClass().addAll("chat-text-area", "no-focus-effect");
		
		
		this.messageTextArea = new TextArea();
		messageTextArea.setPrefHeight(100);
		messageTextArea.setPromptText("Type your message here...");
		
		chatArea.getChildren().addAll(chatTextArea, messageTextArea);
	}
	
	public Scene createScene() {
		GridPane gridPane = new GridPane();
		gridPane.getColumnConstraints().addAll(new ColumnConstraints(800), 
												new ColumnConstraints(400));				
		gridPane.add(this.chatArea, 1, 0);							
		gridPane.add(this.boardArea, 0, 0);
		
		this.scene = new Scene(gridPane, 1200, 600);
		this.scene.getStylesheets().add("ifce/ppd/styles/game.css");
		
		return this.scene;
	}
	
	public Scene getScene() {
		return this.scene;
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

	public void setBoardArea(Pane boardArea) {
		this.boardArea = boardArea;
	}
	
	public Pane getBoardArea() {
		return boardArea;
	}
		
}
