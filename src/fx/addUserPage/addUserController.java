package fx.addUserPage;

import fx.Main;
import fx.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

public class addUserController {

	Main main;
	Model model;
    @FXML
    private TextField usernameInput;

    @FXML
    private TextField passwordInput;

    @FXML
    private TextField departmentInput;

    @FXML
    private ComboBox<String> adminChoice;
	

    @FXML
    private Button confrimnewuserbtn;

    @FXML
    private Button backbtn;

    @FXML
    void adduserbackAction(ActionEvent event) {
    	//System.out.println("back!");
    	Scene homepage = main.homePage();
    	main.window.setScene(homepage);
    }
    
    @FXML
    void confirmnewuserAction(ActionEvent event) {
    	System.out.println("confirm");
    	
    	String errorMessage = "";
    	if(usernameInput.getText().length() == 0) {
    		errorMessage += "\nUsername is a required field";
    	}
    	if(passwordInput.getText().length() == 0) {
    		errorMessage += "\nPassword is a required field";
    	}
    	if(departmentInput.getText().length() == 0) {
    		errorMessage += "\nDepartment is a required field";
    	}
    	if(adminChoice.getValue().length() == 0) {
    		errorMessage += "\nMember status is a required field";
    	}
    	if(errorMessage.length() != 0) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(errorMessage);
			alert.showAndWait();
    	}
    	else {
    		boolean isAdmin;
    		if (adminChoice.getValue().toLowerCase().equals("admin") || adminChoice.getValue().toLowerCase().equals("y"))
    		{
    			isAdmin = true;
    			model.addPeople(usernameInput.getText(), passwordInput.getText(), departmentInput.getText(), isAdmin);
    			this.adduserbackAction(event);
    		} else if (adminChoice.getValue().toLowerCase().equals("general member"))
    		{
    			isAdmin = false;
    			model.addPeople(usernameInput.getText(), passwordInput.getText(), departmentInput.getText(), isAdmin);
    			this.adduserbackAction(event);
    		}
    	}

    }
    
    public void setMain(Main main){
		this.main=main;
    }

	public void setModel(Model model)
	{
		this.model = model;
		adminChoice.setPromptText("Member Status...");
		
		adminChoice.getItems().add("General Member");
		adminChoice.getItems().add("Admin");

		adminChoice.getSelectionModel().selectFirst();


		
	}

}
