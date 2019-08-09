package ifce.ppd;

import ifce.ppd.controllers.GameController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		GameController gameController = new GameController();
		
		primaryStage.setTitle("Chinese Checkers");
		primaryStage.setScene(gameController.createScene());
		primaryStage.show();
	}
	
	public static void main(String[] args) {		
		launch(args);
	}

}
