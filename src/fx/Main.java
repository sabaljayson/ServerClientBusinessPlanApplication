package fx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Client.Pair;
import Server.BP_Node;
import fx.mainView.MainViewController;
import fx.addUserPage.addUserController;
import fx.editStatusPage.editStatusController;
import fx.viewPlanPage.PlanViewController;
import fx.homePage.HomePageController;
import fx.model.Model;
import fx.newOrClonePlanPage.newOrClonePlanController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	Model model = new Model();
	public Stage window;
	Pane mainView;
	Scene loginscene;
	Scene homepagescene, adduserscene, editplanscene, cloneplanscene, editstatusScene, viewplanscene;
	newOrClonePlanController neworclonecont;
	public ArrayList<BP_Node> dep_plans;
	
	// Clone Data for if the client wants to clone a plan
	public ArrayList<String> data = new ArrayList<String>();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.window = primaryStage;
		window.setTitle("Business Plan App");
		
     try {         
    	 	FXMLLoader loader = new FXMLLoader();
    	 	loader.setLocation(Main.class.getResource("mainView/mainView.fxml"));
    	 	mainView = loader.load();
    	 	
    	 	MainViewController cont = loader.getController();
    		cont.setMain(this);
    		cont.setModel(model);
    		loginscene = new Scene(mainView);
    		primaryStage.setScene(loginscene);
    		primaryStage.show();
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
	
	}
	
	public Scene backtoLogin() {
		return loginscene;
	}

	public Scene backtoHomePage() {
		return homepagescene;
	}

	
	public Scene homePage()  {
	     try {
	    	 	FXMLLoader loader = new FXMLLoader();
	 			loader.setLocation(Main.class.getResource("homePage/Homepage.fxml"));
	 			try {
	 				Pane homeview = loader.load();
	 				homepagescene = new Scene(homeview);
	 				HomePageController cont = loader.getController();
	 				cont.setMain(this);
	 				cont.setModel(model);
	 			} catch (IOException e) {
	 				
	 				e.printStackTrace();
	 			}
	    	 


	        } catch (Exception ex) {
	            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	        }
	     
	     return homepagescene;
		
		
	}

	public Scene addUserPage()  {
	     try {
	    	 	FXMLLoader loader = new FXMLLoader();
	 			loader.setLocation(Main.class.getResource("addUserPage/Adduser.fxml"));
	 			try {
	 				Pane adduserview = loader.load();
	 				adduserscene = new Scene(adduserview);
	 				addUserController cont = loader.getController();
	 				cont.setMain(this);
	 				cont.setModel(model);
	 			} catch (IOException e) {
	 				
	 				e.printStackTrace();
	 			}
	        } catch (Exception ex) {
	            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	        }     
	     return adduserscene;	
	}
	
	
	public Scene viewPlanPage()  {
	     try {
	    	 	FXMLLoader loader = new FXMLLoader();
	 			loader.setLocation(Main.class.getResource("viewPlanPage/viewPlan.fxml"));
	 			try {
	 				Pane viewplanview = loader.load();
	 				viewplanscene = new Scene(viewplanview);
	 				PlanViewController cont = loader.getController();
	 				cont.setMain(this);
	 				cont.setModel(model);
	 			} catch (IOException e) {
	 				
	 				e.printStackTrace();
	 			}
	        } catch (Exception ex) {
	            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	        }
	     
	     return viewplanscene;	
	}
	
	//same as the neworClonePLanController 
	public void editPlan(int ye, ArrayList<BP_Node> dep_plans)
	{
			//alert if null pointer
			if(dep_plans == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("You did not select any plan yet");
				alert.showAndWait();
			}
			else {
				for (BP_Node plan : dep_plans)
				{
					if (plan.year == ye)
					{
						model.setBusiness(plan);
					}
				}
				//change to edit scene!!
				Scene newscene = this.viewPlanPage();
				this.window.setScene(newscene);
				System.out.println("change to edit scene");
				
			}

			System.out.println("prepare ! edit scene");
		}
	
	
	
	
	
	
	public Pair<Scene, newOrClonePlanController> clonePlanPage()  {
	     try {
	    	 	FXMLLoader loader = new FXMLLoader();
	 			loader.setLocation(Main.class.getResource("newOrClonePlanPage/newOrClonePlan.fxml"));
	 			try {
	 				Pane cloneplanview = loader.load();
	 				cloneplanscene = new Scene(cloneplanview);
	 				neworclonecont = loader.getController();
	 				neworclonecont.setMain(this);
	 				neworclonecont.setModel(model);
	 			} catch (IOException e) {		
	 				e.printStackTrace();
	 			}
	        } catch (Exception ex) {
	            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	        }
	     
	     Pair<Scene, newOrClonePlanController> p = new Pair<Scene, newOrClonePlanController>(cloneplanscene, neworclonecont);
	     return p;	
	}
	
	 public boolean checkDuplicate(int year) {
	    	for(int i = 0; i< this.dep_plans.size(); i ++) {
	    		System.out.println(this.dep_plans.get(i).year);
	    		if(this.dep_plans.get(i).year == year) {
	    			System.out.println("matched");
	    			return true;
	    		}
	    	}
	    	return false;
	    }
	
	public Scene editStatus(BP_Node Plan) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("editStatusPage/editStatus.fxml"));
			
			try {
				Pane editstatusview = loader.load();
				editstatusScene = new Scene(editstatusview);
				editStatusController cont = loader.getController();
				cont.setCBox();
				cont.setMain(this);
				cont.getPlan(Plan);
				cont.setModel(model);
			}
			catch (IOException e) {
 				
 				e.printStackTrace();
 			}
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
			
		return editstatusScene;
		
		}	
	
	public static void main(String[] args) {
		
        Application.launch(Main.class, (java.lang.String[])null);
    }

}
