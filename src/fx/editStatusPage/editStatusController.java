package fx.editStatusPage;

import java.util.ArrayList;

import Server.BP_Node;
import fx.Main;
import fx.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;



public class editStatusController {

	Main main;
	Model model;
	BP_Node current_plan;
	
	ArrayList<BP_Node> dep_plans;

	public void setModel(Model model) 
    {
    	this.model = model;
    	
    }
	
	public void setMain(Main main){
		this.main=main;
    }
    
	//set up the content in the box
    @FXML
    private ComboBox<String> editComboBox;
    
    public void setCBox() {
    editComboBox.setPromptText("edit status...");
	editComboBox.getItems().addAll("editable", "not editable");

	//set default selection
	editComboBox.getSelectionModel().selectFirst();


    }
    
    //get the plan 
    public void getPlan(BP_Node Plan) {
    	current_plan = Plan;
    }
    
    
    //go to the homepage
    @FXML
    void confirmCancel(ActionEvent event) {
    	Scene homepage = main.homePage();
    	main.window.setScene(homepage);
    }

    //edit the status and save to the server
    @FXML
   public void confirmEdit(ActionEvent event) {
    	ArrayList<String> data= new ArrayList<String>();
    	data.add(editComboBox.getValue()); //getting the string value of the combobox
    	String edit = data.get(0);
    	
  
    	boolean editable = false;
		if (edit == "editable")
		{
			editable = true;
		}
		model.setBPStatus(current_plan, editable);
		confirmCancel(event);
		
    }
}

