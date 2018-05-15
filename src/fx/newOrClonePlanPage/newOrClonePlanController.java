package fx.newOrClonePlanPage;

import java.util.ArrayList;

import Server.BP_Node;
import Server.BusinessEntity;
import fx.Main;
import fx.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class newOrClonePlanController {
	
	Main main;
	Model model;
	public int curryear;
	public String currDepartment;
	public ArrayList<BP_Node> currPlan;
	boolean editable; 
	
    @FXML
    public ComboBox<String> statusChoice;

    @FXML
    public Button okbtn;

    @FXML
    public Button cancelbtn;

    @FXML
    public TextField yearInput;

    @FXML
    public void cancelAction(ActionEvent event) {
    	//back to homepage
    	Scene homepage = main.backtoHomePage();
    	main.window.setScene(homepage);
    }
    
    public void locateplan() {
		//get the new year iterate the plan list and match the plan
		String edit = "";
		int new_year;
		if (main.data == null){
			new_year = -1;
		} 
		else{
			try {
				new_year = Integer.parseInt(main.data.get(0));
				edit = main.data.get(1);
			}
			catch (Exception e){
				new_year=-1;
			}
		}
		if (new_year<1819 || new_year>2050){
			new_year=-1;
		} 
		if (curryear != new_year && new_year != -1){
			if (curryear != -1) {
				for (BP_Node plan : currPlan) {
					if (plan.year == curryear) {
						boolean set_editable = false;
						if (edit == "editable" || edit == null) {
							set_editable = true;
						}
						model.make_CloneBP(curryear, model.getDepartment(), set_editable, new_year);
					}
				}
			}
			else{
				boolean set_editable = false;
				if (edit == "editable" || edit == null){
					set_editable = true;
				}
				model.make_BlankBP(new_year, model.getDepartment(), set_editable);
			}
		}
		
		int index = 0;
		boolean found = false;
		while (index < currPlan.size() && !found){
			if (currPlan.get(index).getYear() == new_year){
				found = true;
		} else{
				index++;
			}
		}
		
		if (statusChoice.getValue().toLowerCase().equals("editable")){
			this.editable = true;
		}
		else {
			this.editable = false;
		}


		// if the new year doesn't exist and was actually inputed by the user
		if (!found && new_year != -1){
			// edit the plan
			model.requestBusinessPlan(new_year);
			currPlan.add(model.getBusiness());
			main.dep_plans.add(model.getBusiness());
			
			//System.out.println("this edit view action active in new controller");
			main.editPlan(new_year, currPlan); // call the edit plan in homepagecontroller
		} 
		else {
			BusinessEntity newen = new BusinessEntity();
			BP_Node newplan = new BP_Node(newen, Integer.parseInt(yearInput.getText()), model.getDepartment(), this.editable); //start a blank plan
			currPlan.add(newplan);
			main.dep_plans.add(newplan);
			main.editPlan(new_year, currPlan);
			
			
//			Alert alert = new Alert(Alert.AlertType.INFORMATION);
//			alert.setHeaderText("Error cloning plan"+"Year: "+ curryear);
//			alert.showAndWait();
		}
    }

   

    @FXML
    public void confirmcloneBtn(ActionEvent event) {

    	String errorMessage = "";
    	if(yearInput.getText().length() == 0) {
    		errorMessage += "\nYear is a required field";
    	}
    	if(!yearInput.getText().matches("-?\\d+")) {
    		errorMessage += "\nYou have to input a number";
    	}
    	if(statusChoice.getValue() == null) {
    		errorMessage += "\nPlan status is a required field";
    	}
    	if(errorMessage.length() != 0) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(errorMessage);
			alert.showAndWait();
    	}
    	else if(Integer.parseInt(yearInput.getText())<=1819 | Integer.parseInt(yearInput.getText())>=2050) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("you have to input a reasonable year!");
			alert.showAndWait();
		}
    	else {
    		
    		if(main.checkDuplicate(Integer.parseInt(yearInput.getText()))) {
    			//alert it is duplicate
    			Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Please enter another year. The year you entered already matched a existing plan.");
				alert.showAndWait();
    		}
    		else {
	    		
	    		//determine the combobox
	    		if (statusChoice.getValue().toLowerCase().equals("editable")){
	    			this.editable = true;
	    		}
	    		else {
	    			this.editable = false;
	    		}
	    				
	    		//clone or make plans	
	    		main.data = getCloneData(yearInput, statusChoice); //set the data in main here!! in case null pointer
	    		//System.out.println(main.data);
	    		this.locateplan();
	    		
	    		Scene homepage = main.homePage();
		    	main.window.setScene(homepage);
		    	
	    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("You successfully clone/add the plan");
				alert.showAndWait();
				//go back to home page

    		}
    		
    	}
    }
    
    public void setMain(Main main){
		this.main=main;
	}
    
    public void setModel(Model model){
		this.model=model;
		statusChoice.setPromptText("edit status...");
		statusChoice.getItems().addAll("editable", "not editable");

		statusChoice.getSelectionModel().selectFirst();


	}
    
    //return the new plan year and if it's editable
    private ArrayList<String> getCloneData(TextField new_year, ComboBox<String> editable)
	{
		ArrayList<String> data = new ArrayList<String>();
		data.add(new_year.getText());
		data.add(editable.getValue());
		return data;
	}
    

}
