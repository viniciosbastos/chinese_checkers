package ifce.ppd;

import ifce.ppd.controllers.MenuViewController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
//		initPlayer1();
//		initPlayer2();
		MenuViewController menuViewController = new MenuViewController(primaryStage);
		menuViewController.createScene();
	}
	
//	public void initPlayer1() {
//		Stage primaryStage = new Stage();
//		Player oponent = new Player("red", 1, 2);
//		Player player = new Player("blue", 4, 1);		
//		GameViewController gameController = new GameViewController(player, oponent);		
//		primaryStage.setTitle("Chinese Checkers - 1");
//		
//		primaryStage.setScene(gameController.createScene());
//		primaryStage.show();
//		primaryStage.setOnCloseRequest(e -> closeGame(gameController));
//		
//		gameController.createServer();
//		
//	}
//	
//	public void initPlayer2() {
//		Stage primaryStage = new Stage();
//		Player oponent = new Player("blue", 4, 1);
//		Player player = new Player("red", 1, 2);		
//		GameViewController gameController = new GameViewController(player, oponent);		
//		primaryStage.setTitle("Chinese Checkers - 2");
//		
//		primaryStage.setScene(gameController.createScene());
//		primaryStage.show();
//		primaryStage.setOnCloseRequest(e -> closeGame(gameController));
//		
//		gameController.connect();
//	}
	
	public static void main(String[] args) {		
		launch(args);				
	}

}
