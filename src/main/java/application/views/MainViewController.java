package application.views;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.model.dao.UserDaoMySql;
import application.model.entities.User;
import application.model.services.UserServiceInterface;
import application.model.services.UserServices;
import application.views.listerners.EventListener;
import application.views.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainViewController extends Controller implements Initializable, EventListener {
	private UserServiceInterface userService;
	private ObservableList<User> usersList;
	
	/* Componentes da TableView */
	@FXML
	private TableView<User> tableViewUsers;
	@FXML
	private TableColumn<User, Integer> tableColumnId;
	@FXML
	private TableColumn<User, String> tableColumnName;
	@FXML
	private TableColumn<User, String> tableColumnUser;
	@FXML
	private TableColumn<User, String> tableColumnEmail;
	
	/* Componentes da ButtonBar */
	@FXML
	private Button btExcluir;
	@FXML
	private Button btEditar;
	@FXML
	private Button btNovo;
	@FXML
	private Button btOpenPropertiesConfig;
	
	@FXML
	public void onBtExcluirAction() {
		boolean userConfirm = Alerts.showConfirm("Deletar o usuário selecionado?");
		if (userConfirm) {
			User user = tableViewUsers.getSelectionModel().getSelectedItem();
			Object serviceReturn = userService.deleteUsingId(user.getIduser());
			
			if(serviceReturn instanceof Alert) {
				Alerts.showAlert((Alert) serviceReturn);
			} else {
				this.updateUsersList();
			}
		}
	}
	
	@FXML
	public void onBtEditarAction() {
		User user = tableViewUsers.getSelectionModel().getSelectedItem();
		if (user != null) {
			this.openUserEditForm(user, "Editar um usuário");
		} else {
			Alerts.showAlert("Informação!", "", "É necessário selecionar uma linha para editar.", AlertType.INFORMATION);
		}
	}

	
	@FXML
	public void onBtNovoAction() {
		this.openUserEditForm(null, "Cadastrar um novo usuário");
	}
	
	@FXML
	public void onBtOpenPropertiesConfig() {
		this.openConnectionPropertiesForm();
	}
	
	public void setUserService (UserServiceInterface userService) {
		this.userService = userService;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		this.openConnectionPropertiesForm();

		tableColumnId.setCellValueFactory(new PropertyValueFactory<User, Integer>("iduser"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		tableColumnUser.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		
		this.setUserService(new UserServices(new UserDaoMySql()));
		
		this.updateUsersList();
	}
	
	// Abre a view para editar ou cadastrar um usuário
	private void openUserEditForm(User user, String tittle) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UserEditForm.fxml"));
		Pane pane;
		try {
			pane = loader.load();
		} catch (IOException e) {
			Alerts.showAlert("Erro", "Não foi possível carregar a tela", e.getMessage(), AlertType.ERROR);
			return;
		}
		UserEditFormController controller = loader.getController();
		controller.subscribeListener(this);
		if (user != null) {
			controller.setUser(user);
			controller.setUserInForm();
		} else {
			controller.setUser(new User());
		}
		this.showViewAsDialogWindow(pane, this.getStage(), tittle, controller);
	}
	
	public void showViewAsDialogWindow(Pane pane, Stage parent, String title, Controller controller) {
		Stage dialogStage = new Stage();
		controller.setStage(dialogStage);
		dialogStage.setTitle(title);
		dialogStage.setScene(new Scene(pane));
		dialogStage.setResizable(false);
		dialogStage.initOwner(parent);
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.showAndWait();
	}
	
	private void openConnectionPropertiesForm () {
		String name = "ConnectionPropertiesForm.fxml";
		FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
		Pane pane;
		try {
			pane = loader.load();
		} catch (IOException e) {
			Alerts.showAlert("Erro", "Não foi possível carregar a tela", e.getMessage(), AlertType.ERROR);
			return;
		}
		this.showViewAsDialogWindow(pane, this.getStage(), "Configurar conexão", (Controller) loader.getController());
	}
	
	private void updateUsersList() {
		Object serviceReturn = userService.getAll();
		
		if(serviceReturn instanceof Alert) {
			((Alert) serviceReturn).initOwner(this.getStage());
			Alerts.showAlert((Alert) serviceReturn);
		} else {
			@SuppressWarnings("unchecked")
			List<User> users = (List<User>) serviceReturn;
			this.usersList = FXCollections.observableArrayList(users);
			tableViewUsers.setItems(this.usersList);
			tableViewUsers.refresh();
		}
	}

	@Override
	public void onChange() {
		this.updateUsersList();
	}
}
