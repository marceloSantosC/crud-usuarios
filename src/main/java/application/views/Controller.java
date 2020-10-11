package application.views;

import javafx.stage.Stage;

public abstract class Controller {
	private Stage stage;
	
	public Stage getStage() {
		return stage;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
