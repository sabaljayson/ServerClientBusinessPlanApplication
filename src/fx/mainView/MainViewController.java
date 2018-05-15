package fx.mainView;

import Server.Person;
import fx.Main;
import fx.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class MainViewController {

	Main main;
	
	private Model model;
	
	
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button cancelButton;
    
    @FXML
    private Button addpeoplebtn;

    @FXML
    private RadioButton localhostChoice;

    @FXML
    private RadioButton otherHostChoice;

    @FXML
    void accpetLogin(ActionEvent event) {
    	String errorMessage = "";
    	if(usernameField.getText().length() == 0) {
    		errorMessage += "\nUsername is a required field";
    	}
    	if(passwordField.getText().length() == 0) {
    		errorMessage += "\nPassword is a required field";
    	}
    	Person person = model.loginAction( usernameField.getText(), passwordField.getText(), true, true);
    	if(person == null) {
    		errorMessage += "\nLogin Failed. Please try again.";
    	}
    	if(errorMessage.length() != 0) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(errorMessage);
			alert.showAndWait();
    	}
    	else {
    		Scene homepage = main.homePage();
        	main.window.setScene(homepage);
    	}

    	
    }

    @FXML
    void cancelLogin(ActionEvent event) {

    }
    
    @FXML
    void addpeopleAction(ActionEvent event) {

    }

	public void setMain(Main main){
			this.main=main;
	}
	
	public void setModel(Model model)
	{
		this.model = model;
		localhostChoice.setSelected(true);
		
	}
	

}
