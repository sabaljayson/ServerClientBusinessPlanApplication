package fx.test.SceneTest;

import static org.testfx.assertions.api.Assertions.assertThat;

import org.junit.Test;
import org.testfx.api.FxRobotException;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import fx.test.TextFXBase;

public class LoginSceneTest extends TextFXBase {

	
	//Login Screen ID's
	final String LoginBtn_ID = "#LoginConfirm";
	final String UserID = "#UsernameBox";
	final String PassID = "#PasswordBox";
	final String CancelID = "#cancelLogin";
	
	//Homescreen Scene ID's
	final String YearSelection_ID = "#YearCombo";
	final String StatusBTN_ID = "#SetStatus";
	final String EditViewBTN_ID = "#EditView";
	final String DeleteBTN_ID = "#Delete";
	final String CloneNewBTN_ID = "#NewPlan";
	final String AddUserBTN_ID = "#NewUserBtn";
	final String LogoutBTN_ID = "#Logout";
	final String planTable_ID = "#planTable";
	
	//Add User ID's
	final String newUser_ID= "#newUserField";
	final String newPass_ID = "#newPassField";
	final String newDepartment_ID = "#newDepartmentField";
	final String adminSelect_ID = "#adminBox";
	final String addUser_ID = "#addBTN";
	final String back_ID = "#backBTN";
	
	//Set Status Scene ID's
	final String statusCombo_ID = "#statusCombo";
	final String statusOk_ID = "#statusOk";
	final String statusCancel_ID = "#statusCancel";
	
	//Clone/New Plan ID's
	final String cloneYear_ID = "#cloneYearField";
	final String cloneStatus_ID = "#cloneStatusCombo";
	final String cloneOk_ID = "#cloneOK";
	final String cloneCancel_ID = "#cloneCancel";
	
	//EditPlan/ViewPlan ID's
	final String treeView_ID = "#treeView";
	final String viewTitle_ID = "#viewTitle";
	final String viewChangeTitle_ID = "#viewChangeTitle";
	final String viewInfoBox_ID = "#viewInfoBox";
	final String viewBack_ID = "#viewBack";
	final String viewAddComp_ID = "#viewAddComp";
	final String viewRemoveComp_ID = "#viewRemoveComp";
	final String viewSave_ID = "#viewSave";
	
	
	
	@Test
	public void enterInfo()
	{
		//Unsuccessful Login
		clickOn(UserID);
		write("notarealuser");
		clickOn(PassID);
		write("fake");
		sleep(50);
		clickOn(LoginBtn_ID);
		clickOn("OK");
		sleep(50);
		
		//Succseful Login
		clickOn(UserID);
		clickOn(UserID);
		type(KeyCode.DELETE);
		write("q");
		clickOn(PassID);
		clickOn(PassID);
		type(KeyCode.DELETE);
		write("1");
		sleep(50);
		clickOn(LoginBtn_ID);
		sleep(50);
		
		
		//Some asserts to verify that we logged in and are on the homepage
		assertThat(lookup(DeleteBTN_ID).queryButton()).hasText("Delete");
		assertThat(lookup(AddUserBTN_ID).queryButton()).hasText("Add New User");
		
	
		
		//Add a User and login as it
		clickOn(AddUserBTN_ID);
		sleep(100);
		
		assertThat(lookup(addUser_ID).queryButton()).hasText("Add"); //Assert to make sure have moved to the add user page
		
		clickOn(newUser_ID);
		write("testuser");
		clickOn(newPass_ID);
		write("123");
		clickOn(newDepartment_ID);
		write("Math");
		sleep(100);
		clickOn(adminSelect_ID);
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		clickOn(addUser_ID);
		
		assertThat(lookup(DeleteBTN_ID).queryButton()).hasText("Delete");  //Assertions for homepage
		assertThat(lookup(AddUserBTN_ID).queryButton()).hasText("Add New User");
		
		clickOn(LogoutBTN_ID);
		
		assertThat(lookup(LoginBtn_ID).queryButton()).hasText("Login"); //Assertions for login scene
		
		clickOn(UserID);
		clickOn(UserID);
		type(KeyCode.BACK_SPACE); //need to clear existing username
		write("testuser");
		clickOn(PassID);
		clickOn(PassID);
		type(KeyCode.BACK_SPACE);
		write("123");
		sleep(100);
		clickOn(LoginBtn_ID);
		
		assertThat(lookup(DeleteBTN_ID).queryButton()).hasText("Delete");
		assertThat(lookup(AddUserBTN_ID).queryButton()).hasText("Add New User"); //Assertions for homepage
		
		sleep(100);
		
		//test the back button in add user
		clickOn(AddUserBTN_ID);
		
		assertThat(lookup(addUser_ID).queryButton()).hasText("Add"); //Assertion for add user canceling
		
		clickOn(newUser_ID);
		write("canceluser");
		clickOn(newPass_ID);
		write("goingback");
		clickOn(newDepartment_ID);
		write("English");
		sleep(100);
		clickOn(adminSelect_ID);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		clickOn(back_ID);
		
		assertThat(lookup(DeleteBTN_ID).queryButton()).hasText("Delete");
		assertThat(lookup(AddUserBTN_ID).queryButton()).hasText("Add New User"); //Assertions for homepage
							
		//Set plan status
		//testing the cancel
		clickOn(planTable_ID);
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		clickOn(StatusBTN_ID);
		sleep(100);
		
		assertThat(lookup(statusOk_ID).queryButton()).hasText("Ok");
		assertThat(lookup(statusCancel_ID).queryButton()).hasText("Cancel"); //Assertions for set status page
		
		
		clickOn(statusCombo_ID);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		sleep(100);
		clickOn(statusCancel_ID);
		sleep(100);
		
		assertThat(lookup(DeleteBTN_ID).queryButton()).hasText("Delete");
		assertThat(lookup(AddUserBTN_ID).queryButton()).hasText("Add New User"); //Assertions for homepage
		
		//actual status set
		clickOn(planTable_ID);
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		clickOn(StatusBTN_ID);
		
		assertThat(lookup(statusOk_ID).queryButton()).hasText("Ok");
		assertThat(lookup(statusCancel_ID).queryButton()).hasText("Cancel"); //Assertions for set status page
		
		sleep(100);
		clickOn(statusCombo_ID);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		sleep(1000);
		clickOn(statusOk_ID);
		sleep(100);
		
		assertThat(lookup(DeleteBTN_ID).queryButton()).hasText("Delete");
		assertThat(lookup(AddUserBTN_ID).queryButton()).hasText("Add New User"); //Assertions for homepage
		
		//Clone a plan
		//testing cancel button
		clickOn(planTable_ID);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		clickOn(CloneNewBTN_ID);
		sleep(100);
		
		assertThat(lookup(cloneOk_ID).queryButton()).hasText("Ok");
		assertThat(lookup(cloneCancel_ID).queryButton()).hasText("Cancel"); //Assertions for clone scene
		
		clickOn(cloneYear_ID);
		write("2001");
		clickOn(cloneStatus_ID);
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		sleep(100);
		clickOn(cloneCancel_ID);
		sleep(100);

		assertThat(lookup(DeleteBTN_ID).queryButton()).hasText("Delete");
		assertThat(lookup(AddUserBTN_ID).queryButton()).hasText("Add New User"); //Assertions for homepage

		clickOn(planTable_ID);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		clickOn(CloneNewBTN_ID);
		sleep(100);
		
		assertThat(lookup(cloneOk_ID).queryButton()).hasText("Ok");
		assertThat(lookup(cloneCancel_ID).queryButton()).hasText("Cancel"); //Assertions for clone scene
		
		clickOn(cloneYear_ID);
		write("2001");
		clickOn(cloneStatus_ID);
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		sleep(100);
		clickOn(cloneOk_ID);
		sleep(100);

    	clickOn("OK");
    	
    	//Try to clone a plan that already has been cloned
    	clickOn(planTable_ID);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		clickOn(CloneNewBTN_ID);
		sleep(100);
		
		clickOn(cloneYear_ID);
		write("2001");
		clickOn(cloneStatus_ID);
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		sleep(100);
		clickOn(cloneOk_ID);
		sleep(100);

    	clickOn("OK");
    	sleep(100);
    	
    	assertThat(lookup(cloneOk_ID).queryButton()).hasText("Ok");
		assertThat(lookup(cloneCancel_ID).queryButton()).hasText("Cancel"); //Assertions for clone scene
		
		clickOn(cloneCancel_ID);
		
		assertThat(lookup(DeleteBTN_ID).queryButton()).hasText("Delete");
		assertThat(lookup(AddUserBTN_ID).queryButton()).hasText("Add New User"); //Assertions for homepage
		
		sleep(100);  	
		
		//Now Delete that Plan
		clickOn(planTable_ID);
		type(KeyCode.UP);
		type(KeyCode.ENTER);
		sleep(100);
		clickOn(DeleteBTN_ID);
		sleep(100);
		clickOn("OK");
		sleep(100);
		
		
		//Edit the plan
		
		//change the plan name
		clickOn(planTable_ID);
		type(KeyCode.ENTER);
		sleep(100);
		clickOn(EditViewBTN_ID);
		clickOn(viewTitle_ID);
		clickOn(viewTitle_ID);
		type(KeyCode.DELETE);
		clickOn(viewTitle_ID);
		write("Testing Plan Name");
		clickOn(viewChangeTitle_ID);
		sleep(100);
		clickOn(viewBack_ID);
		clickOn("Yes");
		sleep(100);
	
		assertThat(lookup(DeleteBTN_ID).queryButton()).hasText("Delete");
		assertThat(lookup(AddUserBTN_ID).queryButton()).hasText("Add New User"); //Assertions for homepage
		
		//changing a plan component
		clickOn(planTable_ID);
		type(KeyCode.ENTER);
		sleep(1000);
		clickOn(EditViewBTN_ID);
		clickOn(treeView_ID);
		type(KeyCode.UP);
		type(KeyCode.UP);
		type(KeyCode.ENTER);
		clickOn(viewInfoBox_ID);
		clickOn(viewInfoBox_ID);
		type(KeyCode.BACK_SPACE);
		clickOn(viewInfoBox_ID);
		clickOn(viewInfoBox_ID);
		type(KeyCode.BACK_SPACE);
		clickOn(viewInfoBox_ID);
		clickOn(viewInfoBox_ID);
		type(KeyCode.BACK_SPACE);
		write("hi, i am a robot. I am writing a testing component for a business plan.");
		clickOn(viewSave_ID);
		clickOn("OK");
		sleep(100);
		clickOn(viewBack_ID);
		clickOn("Yes");
		
		assertThat(lookup(DeleteBTN_ID).queryButton()).hasText("Delete");
		assertThat(lookup(AddUserBTN_ID).queryButton()).hasText("Add New User"); //Assertions for homepage
		
		sleep(100);
		
		clickOn(planTable_ID);
		type(KeyCode.ENTER);
		sleep(1000);
		clickOn(EditViewBTN_ID);
		clickOn(treeView_ID);
		type(KeyCode.UP);
		type(KeyCode.UP);
		type(KeyCode.ENTER);
		clickOn(viewInfoBox_ID);
		sleep(100);
		
		//Add a new component
		
		clickOn(treeView_ID);
		sleep(100);
		type(KeyCode.UP);
		type(KeyCode.UP);
		type(KeyCode.UP);
		type(KeyCode.UP);
		type(KeyCode.ENTER);
		sleep(100);
		clickOn(viewAddComp_ID);
		clickOn(treeView_ID);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		sleep(100);
		clickOn(viewInfoBox_ID);
		clickOn(viewInfoBox_ID);
		type(KeyCode.BACK_SPACE);
		clickOn(viewInfoBox_ID);
		clickOn(viewInfoBox_ID);
		type(KeyCode.BACK_SPACE);
		clickOn(viewInfoBox_ID);
		clickOn(viewInfoBox_ID);
		type(KeyCode.BACK_SPACE);
		write("Look, I've just added a new component");
		clickOn(viewSave_ID);
		sleep(100);
		clickOn("OK");
		sleep(100);
		clickOn(viewBack_ID);
		clickOn("Yes");
		
		assertThat(lookup(DeleteBTN_ID).queryButton()).hasText("Delete");
		assertThat(lookup(AddUserBTN_ID).queryButton()).hasText("Add New User"); //Assertions for homepage
		
		clickOn(planTable_ID);
		type(KeyCode.ENTER);
		sleep(100);
		clickOn(EditViewBTN_ID);
		clickOn(treeView_ID);
		type(KeyCode.DOWN);
		type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		clickOn(viewInfoBox_ID);
		clickOn(viewInfoBox_ID);
		type(KeyCode.BACK_SPACE);
		clickOn(viewInfoBox_ID);
		clickOn(viewInfoBox_ID);
		type(KeyCode.BACK_SPACE);
		clickOn(viewInfoBox_ID);
		clickOn(viewInfoBox_ID);
		type(KeyCode.BACK_SPACE);
		write("I am going to demonstrate deleting this component I just added now");
		sleep(100);
		clickOn(viewRemoveComp_ID);
		sleep(100);
		clickOn(viewBack_ID);
		clickOn("Yes");
		sleep(1000);
		
		
		
		
	
		
		
	}
	
}
