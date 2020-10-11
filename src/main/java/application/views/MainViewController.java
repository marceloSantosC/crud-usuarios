package application.views;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.model.UserDaoMySql;
import application.model.entities.User;
import application.model.services.UserServices;
import application.model.services.interfaces.UserServiceInterface;
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
			String name = "UserEditForm.fxml";
			this.createDialogStage(name, this.getStage(), "Editar um usuário", user);
		} else {
			Alerts.showAlert("Informação!", "", "É necessário selecionar uma linha para editar.", AlertType.INFORMATION);
		}
	}

	
	@FXML
	public void onBtNovoAction() {
		String name = "UserEditForm.fxml";
		this.createDialogStage(name, this.getStage(), "Cadastrar um novo usuário", new User());
	}
	
	@FXML
	public void onBtOpenPropertiesConfig() {
		this.openSetConnectionForm();
	}
	
	public void setUserService (UserServiceInterface userService) {
		this.userService = userService;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		this.openSetConnectionForm();

		tableColumnId.setCellValueFactory(new PropertyValueFactory<User, Integer>("iduser"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		tableColumnUser.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		
		this.setUserService(new UserServices(new UserDaoMySql()));
		
		this.updateUsersList();
	}
	
	private void openSetConnectionForm () {
		String name = "ConnectionPropertiesForm.fxml";
		this.createDialogStage(name, this.getStage(), "Configurar conexão");
	}
	
	private void openEditUserForm() {
		String name = "UserEditForm.fxml";
		this.createDialogStage(name, this.getStage(), "Editar um usuário");
	}
	
	public void createDialogStage(String name, Stage parent, String title, User user) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
			Pane pane = loader.load();
			
			UserEditFormController controller = loader.getController();
			controller.subscribeListener(this);
			controller.setUser(user);
			controller.setUserInForm();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle(title);
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parent);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("Erro", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	public void createDialogStage(String name, Stage parent, String title) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
			Pane pane = loader.load();
			
			Controller controller = loader.getController();
			controller.setStage(this.getStage());
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle(title);
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parent);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("Erro", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
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
		}
	}

	@Override
	public void onChange() {
		this.updateUsersList();
	}
}
