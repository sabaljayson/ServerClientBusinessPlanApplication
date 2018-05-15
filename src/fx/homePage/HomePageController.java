package fx.homePage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Client.Pair;
import Server.BP_Node;
import fx.Main;
import fx.model.Model;
import fx.newOrClonePlanPage.newOrClonePlanController;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.text.Text;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class HomePageController {

	Main main;
	Model model;
	LinkedList<String> people_prev; //prev
	LinkedList<String> people_curr; //curr
	
    @FXML
    private ComboBox<Integer> setYearChoice; //plans

    @FXML
    private Button setStatusBtn;

    @FXML
    private Button editViewBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button cloneMakeBtn;

    @FXML
    private Button logoutbtn;
    
    @FXML
    private Button addpeoplebtn;
    
    @FXML
    private TableView<BP_Node> plan_table;

    @FXML
    private TableColumn<BP_Node, String> plantitlecolumn;

    @FXML
    private TableColumn<BP_Node, String> yearcolumn;

    @FXML
    private TableColumn<BP_Node, String> editstatuscolumn;
    
    @FXML
    private Text username;

    @FXML
    private Text departmentname;
    //ArrayList<BP_Node> dep_plans;

    
    //when the user click the add people btn
    @FXML
    void addpeopleAction(ActionEvent event) {
    	if(model.isAdmin()) {
    		Scene addPagescene = main.addUserPage();
    		main.window.setScene(addPagescene);
    	}
    	else {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("You don't have the permission to add new user");
			alert.showAndWait();
    	}
    }
  
    //when click the clone plan btn change to the new scene and get the info of curr plan
    @FXML
    void clonePlanAction(ActionEvent event) {
		int clone_year = -1;
		if (setYearChoice.getValue() == null) // if they didn't use the dropdown
		{
			clone_year = onEdit(); // get the highlighted table row
		} else // otherwise, get the dropdown value
		{
			clone_year = setYearChoice.getValue();
		}

		
		Pair<Scene, newOrClonePlanController> p = main.clonePlanPage();
		Scene clonePlanscene = p.a;
		newOrClonePlanController cont = p.b;
		main.window.setScene(clonePlanscene);
		
		//set the designed year to the new clone controller
		cont.curryear = clone_year;
		cont.currDepartment = model.getDepartment();
		cont.currPlan = main.dep_plans;
		// make a new plan if no plan was selected, or clone the plan selected
//		int new_year = clonePlan(clone_year, model.getDepartment(), dep_plans);  ////////// --------------> go to clone plan scene also return a new year
//		
    }

    //get the info of the selected and delete it from the server
    @FXML
    synchronized void deletePlanAction(ActionEvent event) throws Exception{
    	
		int delete_year = -1;
		if (setYearChoice.getValue() == null)
		{
			delete_year = onEdit();
		} else
		{
			delete_year = setYearChoice.getValue();
		}

		if (delete_year != -1)
		{
			List<BP_Node> allPlans = model.getBP();
			int index = 0;		
			try {
				for (BP_Node node : allPlans) 
				{
					if (node.getDepartment().equalsIgnoreCase(model.getDepartment())
							&& node.year == delete_year)
					{
						deletePlan(delete_year, main.dep_plans);
						setYearChoice.getItems().remove(index);
						main.dep_plans.remove(index);
					} else if (node.getDepartment().equalsIgnoreCase(model.getDepartment()))
					{
						index++;
					}
				}
			} catch (java.util.ConcurrentModificationException exception) {
	            // Catch ConcurrentModificationExceptions.
			}
				
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Your selection is empty");
			alert.showAndWait();
		}
    	
    }
    
    //actually delect the plan from the server and notify the delection is successful
    private synchronized void deletePlan(int ye, ArrayList<BP_Node> dep_plans)
	{
		for (BP_Node plan : dep_plans)
		{
			if (plan.year == ye)
			{
				model.deleteBP(plan);
			}
		}
		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText("You successfully delete the plan");
		alert.showAndWait();
		
		//refresh the homepage again
		Scene homepage = main.homePage();
		main.window.setScene(homepage);
	}

    //go to the edit/viewing plan scene!!!!!
    @FXML
    void editViewPlanAction(ActionEvent event) {
//    	Scene viewscene = main.viewPlanPage();
//    	main.window.setScene(viewscene);
    	
    	
//    	
    	if(plan_table.getSelectionModel().isEmpty()) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("You did not selected any plan yet");
			alert.showAndWait();
    	}
    	else {
	    	int year = -1;
			if (setYearChoice.getValue() == null) {
				year = onEdit(); //call the onedit function to get the curr selected year
			} else {
				year = setYearChoice.getValue();
			}
	
			if (year != -1) {
				main.editPlan(year, main.dep_plans);
			}
    	}
    }

    //go to the login scene
    @FXML
    void logoutAction(ActionEvent event) {
//    	model.logout();
    	Scene loginpage = main.backtoLogin();
    	main.window.setScene(loginpage);
//    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
//		alert.setHeaderText("You successfully log out");
//		alert.showAndWait();
//    	
    }
    
    //change the plans status to editable or not editable
    @FXML
    void setStatusAction(ActionEvent event) {
    	if(model.isAdmin()) {
        	if(main.dep_plans == null) {
    			Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setHeaderText("You did not have any plan yet");
    			alert.showAndWait();
        	}
        	if(plan_table.getSelectionModel().isEmpty()) {
        		Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setHeaderText("You did not selected any plan yet");
    			alert.showAndWait();
        	}
        	else {
        		//get the data from the selected plan
        		int year = -1;
        		String depart = null;
        		if (plan_table.getSelectionModel().getSelectedItem() != null)
    			{
    				BP_Node selected_plan = plan_table.getSelectionModel().getSelectedItem();
    				year = selected_plan.getYear();
    				depart = selected_plan.getDepartment();
    				
    				Scene editStatus = main.editStatus(selected_plan);
    				main.window.setScene(editStatus);
    				
    				/*editStatusController cont = new editStatusController();
    				cont.getPlan(selected_plan);
    				
    				if (cont.isComboSet()==true) {
    					cont.confirmEdit(event);
    				}
    				else {
    					;
    				}*/		
    				

    			}
        	}	
    	}
    	else {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("You don't have the permission to set the plan's status");
			alert.showAndWait();
    	}
    }
    
    //set the plan table with the data from server's read disk
	public void setModel(Model model) {
		this.model = model;	
		//select year for plans // for combo box
		main.dep_plans = new ArrayList<BP_Node>();
		getLists(setYearChoice, main.dep_plans);
		departmentname.setText(model.getDepartment());
		username.setText(model.getUsername());
		plan_table.getColumns().clear();
		ObservableList<BP_Node> business_plans = FXCollections.observableArrayList(main.dep_plans);
		plan_table.setItems(business_plans);
		// set columns
		plantitlecolumn.setCellValueFactory(new Callback<CellDataFeatures<BP_Node, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<BP_Node, String> n){
				return new ReadOnlyStringWrapper(n.getValue().getEntity().getEntityTitle());
			}
		});
		yearcolumn.setCellValueFactory(new Callback<CellDataFeatures<BP_Node, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<BP_Node, String> n) {
				return new ReadOnlyStringWrapper(String.valueOf(n.getValue().getYear()));
			}
		});
		editstatuscolumn.setCellValueFactory(new Callback<CellDataFeatures<BP_Node, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<BP_Node, String> n) {
				String toReturn = "view only";
				if (n.getValue().isEditable()) {
					toReturn = "edit/view";
				}
				return new ReadOnlyStringWrapper(toReturn);
			}
		});
		plan_table.getColumns().addAll(plantitlecolumn, yearcolumn, editstatuscolumn);
		
	}
	
	
	
	
	
	//get the selection from the user about what specific year of plan they want to edit
	//provide the info to the next edit plan scene
	private int onEdit()
	{	
		if(plan_table == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("You did not select any plan yet");
			alert.showAndWait();
		}
		
		else {
			int year = -1;
			// check the table's selected item and get selected item
			if (plan_table.getSelectionModel().getSelectedItem() != null)
			{
				BP_Node selected_plan = plan_table.getSelectionModel().getSelectedItem();
				year = selected_plan.getYear();
			}
			return year;
		}
		return 0; // null return 0 for now
	}
	
	//get all the department's plan in a list from the server
	private void getLists(ComboBox<Integer> plans, ArrayList<BP_Node> dep_plans)
	{
		List<BP_Node> allPlans = model.getBP();
		for (BP_Node node : allPlans)
		{
			if (node.department.equals(model.getDepartment()))
			{
				plans.getItems().add(node.year);
				dep_plans.add(node);
			}
		}
	}
	
	
	public void setMain(Main main){
		this.main=main;
	}
	

}
