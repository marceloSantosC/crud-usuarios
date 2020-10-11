package application;

import java.io.IOException;

import application.views.Controller;
import application.views.util.Alerts;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
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
		} catch(IOException e) {
			Alerts.showAlert("Erro fatal", "Não foi possível carregar a view principal", e.getMessage(), AlertType.ERROR);
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}