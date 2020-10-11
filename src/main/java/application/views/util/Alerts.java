package application.views.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Alerts {
	public static void showAlert(String tittle, String header, String content, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(tittle);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}
	
	public static void showAlert(Alert alert) {
		alert.show();
	}
	
	public static boolean showConfirm(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
		alert.setHeaderText("");
		alert.setTitle("");
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
		    return true;
		} else {
			return false;
		}
	}
	
	public static Alert getAlert(String tittle, String header, String content, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(tittle);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert;
	}
}
