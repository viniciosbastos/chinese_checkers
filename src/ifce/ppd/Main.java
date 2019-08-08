package ifce.ppd;

import ifce.ppd.models.Board;
import ifce.ppd.models.Cell;
import ifce.ppd.models.Point;
import ifce.ppd.utils.TilesUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		GridPane gridPane = new GridPane();
		gridPane.getColumnConstraints().addAll(new ColumnConstraints(800), 
												new ColumnConstraints(400));
		
		
		VBox chatBox = new VBox();		
		TextArea chatTextArea = new TextArea();
		chatTextArea.setPrefHeight(500);
		chatTextArea.setMouseTransparent(true);
		chatTextArea.setFocusTraversable(false);
		chatTextArea.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
		
		
		TextArea messageTextArea = new TextArea();
		messageTextArea.setPrefHeight(100);
		messageTextArea.setPromptText("Type your message here...");
		
		chatBox.getChildren().addAll(chatTextArea, messageTextArea);
		
		gridPane.add(chatBox, 1, 0);
					
		Pane board = new Pane();
		Point inicio = new Point(130, 50);
				
		Board boardObj = new Board();
		boardObj.createBoard();
		for (int i = 0; i < Board.BOARD_HEIGHT; i++) {
			for (int j = 0; j < Board.BOARD_WIDTH; j++) {
				Cell cell = boardObj.getBoardMatrix()[i][j];
				if (cell != null)
					board.getChildren().add(cell.getTile());			
			}
		}
		
//		for (int i = 0; i < 17; i++) {
//			for (int j = 0; j < 13; j++) {
//				Cell cell = new Cell(true, new Point(inicio.getX()+(j*TilesUtils.TILE_WIDTH), inicio.getY()));
//				board.getChildren().add(cell.getTile());			
//			}
//			if (i % 2 == 0) {
//				inicio.setX(inicio.getX() + (TilesUtils.TILE_WIDTH/2));
//			} else {
//				inicio.setX(inicio.getX() - (TilesUtils.TILE_WIDTH/2));
//			}
//			inicio.setY(inicio.getY() + (TilesUtils.TILE_HEIGHT*3/4));
//		}
		
		gridPane.add(board, 0, 0);
		
		Scene scene = new Scene(gridPane, 1200, 600);
		
		primaryStage.setTitle("Chinese Checkers");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {		
		launch(args);
	}

}
