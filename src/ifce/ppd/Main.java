package ifce.ppd;

import ifce.ppd.controllers.MenuViewController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		MenuViewController menuViewController = new MenuViewController(primaryStage);
		menuViewController.createScene();
	}
	
	public static void main(String[] args) {		
		launch(args);				
	}

}
