package application;

import application.views.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	@Override
	public void start(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(getClass().getResource("views/MainView.fxml"));
		    Parent parent = loader.load();
		    Scene scene = new Scene(parent);
		    Controller controller = loader.getController();
		    controller.setStage(stage);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}