import javafx.application.Application;
import javafx.stage.Stage;


public class JavaFXTemplate extends Application  {
	public static void main(String args[]){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		GameController launch = new GameController(primaryStage);
	}
}