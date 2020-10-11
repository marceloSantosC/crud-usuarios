package application.views;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.model.dao.UserDaoMySql;
import application.model.entities.User;
import application.model.services.UserServiceInterface;
import application.model.services.UserServices;
import application.views.listerners.EventListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserEditFormController extends Controller implements Initializable {
	UserServiceInterface userService = new UserServices(new UserDaoMySql());
	private User user;
	List<EventListener> listeners = new ArrayList<>();

	@FXML
	private TextField txtName;
	@FXML
	private TextField txtUsername;
	@FXML
	private TextField txtEmail;
	@FXML
	private Label lblError;
	@FXML
	private Button btSave;
	@FXML
	private Button btCancel;

	@FXML
	public void onBtSaveAction() {
		if (this.checkFormData()) {
			this.getFormData();
			Object serviceResponse = userService.saveOrUpdate(this.user);
			if (serviceResponse instanceof Alert) {
				this.lblError.setText(((Alert) serviceResponse).getContentText());
			} else {
				this.notifyListeners();
				this.close();
			}
		} else {
			this.lblError.setText("Preencha os campos em branco.");
		}
		
	}

	@FXML
	public void onBtCancelAction() {
		this.close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		this.setUserInForm();

	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}

	public void setUserInForm() {
		if (this.user != null) {
			this.txtName.setText(user.getName());
			this.txtEmail.setText(user.getEmail());
			this.txtUsername.setText(user.getUsername());
		}
	}

	public void subscribeListener(EventListener listener) {
		listeners.add(listener);
	}

	public void notifyListeners() {
		for (EventListener listener : listeners) {
			listener.onChange();
		}
	}

	private void getFormData() {
		this.user.setName(this.txtName.getText());
		this.user.setEmail(this.txtEmail.getText());
		this.user.setUsername(this.txtUsername.getText());
	}

	private boolean checkFormData() {
		if (this.txtName.getText().isBlank() || this.txtUsername.getText().isBlank() || this.txtEmail.getText().isBlank()) {
			return false;
		}
		return true;
	}

	private void close() {
		this.getStage().close();
	}
}
