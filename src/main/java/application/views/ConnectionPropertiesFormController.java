package application.views;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import application.db.DBProperties;
import application.views.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ConnectionPropertiesFormController extends Controller implements Initializable{
	
	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField pwfPassword;
	@FXML
	private TextField txtHostname;
	@FXML 
	private TextField txtPorta;
	@FXML
	private TextField txtDbName;
	@FXML
	private Label lblError;
	@FXML
	private Button btEdit;
	@FXML
	private Button btContinue;
	
	@FXML
	public void onBtEditAction() {
		if (this.checkFormData()) {
			try {
				DBProperties.setProperties(txtUsername.getText(), pwfPassword.getText(),
						txtHostname.getText(), txtPorta.getText(), txtDbName.getText());
			} catch (IOException e) {
				Alerts.showAlert("Erro", "Ocorreu um erro ao salvar db.properties, tente alterar o arquivo manualmente", e.getMessage(), AlertType.ERROR);
			}
			this.close();
		} else {
			this.lblError.setText("Por favor preencha os dados.");
		}
	}
	
	@FXML
	public void onBtContinuelAction() {
		this.close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		try {
			Properties props = DBProperties.loadProperties();
			this.txtUsername.setText((String) props.get("user"));
			this.pwfPassword.setText((String) props.get("password"));
			this.txtDbName.setText((String) props.getProperty("dbname"));
			this.txtHostname.setText((String) props.getProperty("host"));
			this.txtPorta.setText((String) props.getProperty("port"));
			
		} catch (IOException e) {
			Alerts.showAlert("Erro", "Ocorreu um erro ao abrir db.properties, tente alterar o arquivo manualmente", e.getMessage(), AlertType.ERROR);
		}
	}
	
	private boolean checkFormData() {
		if (this.txtUsername.getText().isBlank()|| this.txtHostname.getText().isBlank() ||
				this.txtPorta.getText().isBlank() || this.txtDbName.getText().isBlank()) {
			return false;
		}
		return true;
	}
	
	private void close() {
		this.getStage().close();
	}

}
