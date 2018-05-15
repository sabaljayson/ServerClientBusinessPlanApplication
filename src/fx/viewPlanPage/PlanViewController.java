package fx.viewPlanPage;

import fx.model.Model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.ButtonBar.ButtonData;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import Server.BP_Node;
import Server.BusinessEntity;
import Server.CentrePlanFactory;
import fx.ICommand;
import fx.Invoker;
import fx.Main;

import java.util.ArrayList;

import java.util.Optional;


import Server.Person;


public class PlanViewController {
	Main main;
	Model model;
	String filledtext; //plan.getStatement(0).getStatement()
	CentrePlanFactory centreHead1 = new CentrePlanFactory();
	TreeItem<BusinessEntity> newValueref;
	BusinessEntity currPlan;
	ArrayList<String> currcomments;
	Person currPerson;
	Invoker invoker = Invoker.getInstance();
	String commandComments;
	TreeItem<BusinessEntity> treeItemSelection;
	
    @FXML
    private Button backButton;
    
    @FXML
    private Pane pane;

    
    @FXML
    private Text titletext;
    

    @FXML
    private Button addSecBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private TextArea bigboxshow;


    @FXML
    private TextField titleChange;

    @FXML
    private Button changeBtn;
    
    @FXML
    private TreeView<String> textView;
    
    @FXML
    private TreeView<BusinessEntity> treeview;
    
    @FXML
    private ListView<String> commentList;

    @FXML
    public TextArea commentshow;

    @FXML
    private Button addComment;
    
    @FXML
    private Button removeComment;
    
    @FXML
    private TextField commentinput;
    
    @FXML
    private Button redobtn;

    @FXML
    private Button undobtn;
    
    
    @FXML
    void undoAction(ActionEvent event) {
    	if(this.invoker.isUndoEmpty()) {
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    		alert.setHeaderText("you did not have anything to undo");
    		alert.showAndWait();
    	}
    	else {
    		ICommand command = invoker.undo();
    		invoker.addToRedoStack(command);

    	}
    }

    
    
    @FXML
    void redoAction(ActionEvent event) {
    	if(this.invoker.isRedoEmpty()) {
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    		alert.setHeaderText("you did not have anything to redo");
    		alert.showAndWait();
    	}
    	else {
    		ICommand command = invoker.redo();
    		invoker.addToUndoStack(command);
    	}
    }
    
    public void removeCommentfcn(String comment) {
    	for(int i=0; i<this.currcomments.size();i++) {
			if(this.currcomments.get(i).equals(comment)) {
				this.currcomments.remove(i);
			}
		}
    	// upload
    	model.uploadBP(model.getBusiness());
    	// write to server and read
    	model.writeDisk();
    	model.readDisk();
    	

//		Alert alert = new Alert(Alert.AlertType.INFORMATION);
//		alert.setHeaderText("you successfully removed the comment");
//		alert.showAndWait();
    }
    
    public void refreshscene() {
    	Scene refresh = main.viewPlanPage();
		main.window.setScene(refresh);
    }
    
    
    
    @FXML
    void removeCommentAction(ActionEvent event) {
    	if(commentList.getSelectionModel().isEmpty()) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setHeaderText("you did not select anything");
    		alert.showAndWait();
    	}
    	else {
    		ICommand commentCommand = new CommentCommand(this);
    		invoker.addToUndoStack(commentCommand);
    		String commentinfo = commentshow.getText();
    		this.removeCommentfcn(commentinfo);

    		//refresh the scene
    		this.refreshscene();
    		
    	}
    }
    
    public boolean checkDuplicateComments(String commentinfo) {
    	boolean isDuplicate = false;
    	for(int i = 0; i< this.currcomments.size(); i++) {
    		if(this.currcomments.get(i).equals(commentinfo)) {
    			isDuplicate = true;
    		}
    	}
    	return isDuplicate;
    }
    
    public void addCommentfcn(String info) {
    	boolean isDuplicate = this.checkDuplicateComments(info);
    	if(isDuplicate) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setHeaderText("the same comment already exist! please change to another one");
    		alert.showAndWait();
    	}
    	else {
	    	this.currcomments.add(info);
	    	String title = this.newValueref.getValue().getEntityTitle();
	    	System.out.println(this.newValueref.getValue().comments);
			this.newValueref.getValue().setComments(this.currcomments);
			model.uploadBP(model.getBusiness());
	    	// write to server and read
	    	model.writeDisk();
	    	model.readDisk();
	    	if(this.currcomments != null) {
				commentList.getItems().clear();
	    		for(int i =0; i< this.currcomments.size(); i++) {
	    			commentList.getItems().add(this.currcomments.get(i));
	    			commentList.getSelectionModel().select(0);
	    		}
			}
	    	
//	    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
//	    	alert.setHeaderText("you successfully add a comment");
//	    	alert.showAndWait();
    	}
    }
    
    
    
    @FXML
    void comfirmaddcomment(ActionEvent event) {

    	if(commentinput.getText().length() == 0) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setHeaderText("please enter your comment");
    		alert.showAndWait();
    	}
    	else {
    		String commentinfo = commentinput.getText();	
    		this.addCommentfcn(commentinfo); 	

    	}
    }
    
    @FXML
    void addSection(ActionEvent event) { 
    	// print out the name of the component you are selecting
    	if(this.newValueref.getValue().getEntityTitle().equals("Tool")){
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setHeaderText("you reached the bottom of the tree! this node can not have any children");
    		alert.showAndWait();
    	}
    	
    	else {
    		this.addSectionFcn(this.newValueref.getValue(),this.newValueref ); //a business enetity you are selecting
//    		System.out.println(this.newValueref.getValue().getClass().getName());
    		
//		    BusinessEntity plan = model.getEntity(); 
//		   	BusinessEntity new_plan = this.newValueref.getValue().createNewSubentity();
//			// set the factory to be the same depth as the factory in centreplanfactory
//			new_plan.setEntityFactory(centreHead1.getFactoryFromIndex(new_plan.getTree_level() + 1));
//			/// new tree view
//			treeview = new TreeView<BusinessEntity>(showPlan(new_plan, newValueref));
//			//refresh the scene
//			this.refreshscene();
//			model.setEntity(getHead(plan));
    	}	
    }


    public void addSectionFcn(BusinessEntity currSelection, TreeItem<BusinessEntity> selection) {
    		System.out.println("undo adding component");
    	 	BusinessEntity plan = model.getEntity(); 
    	 	System.out.println("current tree item selection: "+selection);
    	 	System.out.println("current selection business enetity: "+currSelection);
//		   	BusinessEntity new_plan = currSelection.createNewSubentity();
		   	BusinessEntity new_plan = selection.getValue().createNewSubentity();
		   	System.out.println("create new component: ");
		   	System.out.println(new_plan.getEntityTitle());
			// set the factory to be the same depth as the factory in centreplanfactory
			new_plan.setEntityFactory(centreHead1.getFactoryFromIndex(new_plan.getTree_level() + 1));
			/// new tree view
			treeview = new TreeView<BusinessEntity>(showPlan(new_plan, selection));
			//refresh the scene
			this.refreshscene();
			model.setEntity(getHead(plan));
    }

    @FXML
    void changeTitle(ActionEvent event) {
    	BusinessEntity plan = model.getEntity();
		String newEntityTitle;
    	// new title
    	newEntityTitle = titleChange.getText();
    	if(newEntityTitle.length() == 0) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setHeaderText("Please enter something");
    		alert.showAndWait();
    	}
    	else {
	    	plan.setEntityTitle(newEntityTitle);
	    	System.out.println(plan.getEntityTitle());
	    	/// need the root of the tree
	    	BusinessEntity head = getHead(plan);
	    	model.setEntity(head);
	    	model.uploadBP(model.getBusiness());
			// write to server and read
			model.writeDisk();
			model.readDisk();
	    	//this.saveAction(this.currPlan, plan);
	    	/// redraw the scene
	    	Scene refresh = main.viewPlanPage();
	    	main.window.setScene(refresh);
    	}
    }

	private BusinessEntity getHead(BusinessEntity plan) {
		if (plan.getParentEntity() == null)
		{
			return plan;
		}
		return getHead(plan.getParentEntity());
	}

	
	public void delectSecFcn(BusinessEntity plan) {
		System.out.println("I am delecting..."+plan.getEntityTitle());
		if (plan.getParentEntity() != null)
		{
			this.newValueref = this.treeItemSelection;
			/// remove current entity from parents children
			System.out.println("before: "+plan.getParentEntity().getSubentities());
			ArrayList<BusinessEntity> entities = plan.getParentEntity().getSubentities();
			entities.remove(plan);
			
			

			// get root
			BusinessEntity head = getHead(plan);
			model.setEntity(head);
			
			// set parent to not have the current node as a child
			plan.getParentEntity().setSubentities(entities);
			
			plan.getParentEntity().getSubentities().clear(); //has to clear again somehow
			System.out.println("after: "+plan.getParentEntity().getSubentities());
			// new retrevie without the node
			this.treeview.setRoot(showPlan(plan, new TreeItem<BusinessEntity>()));

			
			/// redraw the scene
	    	this.refreshscene();
		}
	}
	
	
	@FXML
    void deleteSec(ActionEvent event) {
		//System.out.println("clicked delect");
		// takes component out of parent entities subentities
		//this.currPlan = model.getEntity();
		BusinessEntity plan = this.currPlan;
		System.out.println(plan.getEntityTitle());
		if (this.currPlan.getParentEntity() != null)
		{
			
//			System.out.println(this.treeItemSelection.getValue().getEntityTitle());

			
			ICommand componentCommand = new ComponentCommand(this, this.treeItemSelection);
    		invoker.addToUndoStack(componentCommand);
    		this.delectSecFcn(plan);

		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("you should have at least one plan component or you could delete the whole plan");
			alert.showAndWait();
		}
    }


	
	@FXML
    void save(ActionEvent event) {
		// set the statement equal to the changed statement
		this.currPlan.getStatement(0).setStatement(bigboxshow.getText());
		if(bigboxshow.getText().length()==0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Please do not leave it blank");
			alert.showAndWait();
		}
		else {
			// upload
			model.uploadBP(model.getBusiness());
			// write to server and read
			model.writeDisk();
			model.readDisk();
			
			//notify the clients viewing
			this.model.signalChange();
			
			
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("you successfully saved the plan");
			alert.showAndWait();
			
			Scene refresh = main.viewPlanPage();
	    	main.window.setScene(refresh);
			//this.back(event);
		}
    }
    
    @FXML
    void back(ActionEvent event) {	
		// dialog box to ask if you want those changes saved
		Dialog<?> sure = new Dialog<>();
		sure.setTitle("Cancel");
		sure.setContentText("Do you want to save changes?");
		ButtonType yes = new ButtonType("Yes");
		ButtonType no = new ButtonType("No");
		sure.getDialogPane().getButtonTypes().addAll(yes, no);
		// close button
		sure.getDialogPane().getButtonTypes().add(new ButtonType("Close", ButtonData.CANCEL_CLOSE));
		sure.setOnCloseRequest(e -> sure.close());
		// wait for answer
		Optional<ButtonType> result = (Optional<ButtonType>) sure.showAndWait();
		// answer
		if (result.get() == yes) {
			/// if yes save the plan
			currPlan = model.returnEntity();
			saveAction(currPlan, bigboxshow.getText());
			model.uploadBP(model.getBusiness());
			model.writeDisk();
			model.readDisk();
			Scene homepage = main.homePage();
        	main.window.setScene(homepage);
		} else if (result.get() == no){
			// if no just change the scene to home
			Scene refresh = main.viewPlanPage();
	    	main.window.setScene(refresh);
		}
		
		this.model.removeClient();

    }

	
    public void setMain(Main main) {
    		this.main = main;
    }
    
    public void alertPop(String message) {
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(message);
		alert.showAndWait();
    }
    
    public void setModel(Model model) {
    		this.model = model;
    		
    		//set the command
    		ICommand notifycommand = new NotifyCommand(this);
    		this.model.setCommand(notifycommand);
    		
    		
    		bigboxshow.setWrapText(true);
    		BusinessEntity plan = model.getEntity();
    		BP_Node currentNode = model.getBusiness();
    		titletext.setText(plan.getEntityTitle());
    		TreeItem<BusinessEntity> first = new TreeItem<BusinessEntity>(plan);
    		first.setExpanded(true);
    		TreeItem<BusinessEntity> item = showPlan(plan.getSubentity(0), first);
    		//tree(item)
    		treeview.setRoot(item);
    		
    		try {
    			
    			treeview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,
    				newValue) -> clickedOn(newValue, getEntity(newValue, plan), pane, item, currentNode.editable));
    		}catch(NullPointerException e ){
    		      //doSOmethingAboutIt();
    		}
    		//fill the changable title with the default title
    		titleChange.setText(plan.getEntityTitle());
    		treeview.getSelectionModel().select(0);

    		//update person
    		this.currPerson = model.getPerson();
    		
    		
    		this.model.addClient();
    		
    		
    		
    		
    }
    

	// EDIT PLAN METHODS
    private void clickedOn(TreeItem<BusinessEntity> newValue, BusinessEntity plan, Pane edits,
			TreeItem<BusinessEntity> item, boolean editable)
	{
    	if (newValue == null) {
    		newValue = this.newValueref;
    	}
    	
    	else {
	    	if (newValue.getParent() != null) {
	    		System.out.println("parent: "+newValue.getParent().getValue().getEntityTitle());
	    		this.treeItemSelection = newValue.getParent();
	    	}
	    	
	    	this.newValueref = newValue;
			if (plan != null)
			{
				this.filledtext = plan.getStatement(0).getStatement();
				setUpForSelect(newValue, plan, edits, treeview, editable);
			}
			
			//update the currcomment attribute
			if(this.newValueref != null) {
				this.currcomments = newValueref.getValue().getComments();
				if(this.currcomments != null) {
					commentList.getItems().clear();
		    		for(int i =0; i< this.currcomments.size(); i++) {
		    			commentList.getItems().add(this.currcomments.get(i));
		    		}
				}
				commentshow.setEditable(false); //not editable for comments
				commentshow.setStyle("-fx-opacity: 1.0;");
				commentList.setOnMouseClicked(new ListEventHandeler(){
			        @Override
			        public void handle(javafx.scene.input.MouseEvent event) {	
			        	commentshow.setText(commentList.getSelectionModel().getSelectedItem());
			        }
				});
				
			}
    	}
		
		

	}
    

    
    
  /// this sets up the buttons on each choice
  	private void setUpForSelect(TreeItem<BusinessEntity> newValue, BusinessEntity plan, Pane edits,
  			TreeView<BusinessEntity> planparts, boolean editable)
  	{
  		this.filledtext = plan.getStatement(0).getStatement();
  		this.newValueref = newValue;
  		this.currPlan = plan;
  		bigboxshow.setText(filledtext);

  		if (editable == false)
  		{
  			// if not editable, no saving
  			addSecBtn.setDisable(true);
  			deleteBtn.setDisable(true);
  			changeBtn.setDisable(true);
  			saveBtn.setDisable(true);
  			//cancel.setOnAction(e -> goBack());
  			//something shows it cannot edit
//  		Alert alert = new Alert(Alert.AlertType.ERROR);
//			alert.setHeaderText("It is not editable");
//			alert.showAndWait();

  		}

  	}
  	
  	
  	private void saveAction(BusinessEntity plan, String newStatement)
	{
		// set the statement equal to the changed statement
		plan.getStatement(0).setStatement(newStatement);
		// upload
		model.uploadBP(model.getBusiness());
		// write to server and read
		model.writeDisk();
		model.readDisk();
		//makeEditScene();
	}

  	
  	
  /// returns the businessEntity being edited, to know which entity to make
  	/// changes to
  	private BusinessEntity getEntity(TreeItem<BusinessEntity> newValue, BusinessEntity plan)
  	{
  		this.newValueref = newValue; // keep updating the reference
  		// Finds when the entity titles are equal
  		if (plan != null && newValue != null)
  		{
  			// each treeitem id is a unique identifier of each BusinessEntity
  			if (plan.getTreeItemID() == newValue.getValue().getTreeItemID())
  			{
  				return plan;
  			}
  			ArrayList<BusinessEntity> children = plan.getSubentities();
  			BusinessEntity res = null;
  			/// recursive call the subentities
  			for (int i = 0; res == null && i < children.size(); i++)
  			{
  				res = getEntity(newValue, children.get(i));
  			}
  			return res;
  		}
  		return null;
  	}
    
	/// this takes a tree view and a business entity and recursively goes through
	/// the plan until it
	// has all components
	private TreeItem<BusinessEntity> showPlan(BusinessEntity plan, TreeItem<BusinessEntity> treeItem)
	{
		TreeItem<BusinessEntity> smallest = new TreeItem<BusinessEntity>(plan);
		smallest.setExpanded(true);
		treeItem.getChildren().add(smallest);
		if (smallest.getValue() != null && smallest.getValue().getSubentities().size() > 0)
		{
			for (BusinessEntity subs : smallest.getValue().getSubentities())
			{
				showPlan(subs, smallest);
			}
		}
		return treeItem;
	}
}
