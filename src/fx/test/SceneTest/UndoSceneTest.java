package fx.test.SceneTest;

import static org.testfx.assertions.api.Assertions.assertThat;

import org.junit.Test;

import fx.test.TextFXBase;
import javafx.scene.input.KeyCode;

public class UndoSceneTest extends TextFXBase{
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
			
		//EditPlan/ViewPlan ID's
		final String treeView_ID = "#treeView";
		final String viewTitle_ID = "#viewTitle";
		final String viewChangeTitle_ID = "#viewChangeTitle";
		final String viewInfoBox_ID = "#viewInfoBox";
		final String viewBack_ID = "#viewBack";
		final String viewAddComp_ID = "#viewAddComp";
		final String viewRemoveComp_ID = "#viewRemoveComp";
			
		//comment ID's
		final String commentAddBtn_ID = "#commentAddBtn";
		final String commentRemoveBtn_ID = "#commentRemoveBtn";
		final String commentListView_ID = "#commentListView";
		final String commentshow_ID = "#commentShowBox";
		final String commentInput_ID = "#commentInput";
		
		//undo button!!
		final String undo_ID = "#undoBtn";
		final String redo_ID = "#redoBtn";
		
		@Test
		public void textUndoComments()
		{
			//login as admin
			clickOn(UserID);
			clickOn(UserID);
			type(KeyCode.DELETE);
			write("q");
			clickOn(PassID);
			clickOn(PassID);
			type(KeyCode.DELETE);
			write("1");
			clickOn(LoginBtn_ID);
			
			//Some asserts to verify that we logged in and are on the homepage
			assertThat(lookup(DeleteBTN_ID).queryButton()).hasText("Delete");
			assertThat(lookup(AddUserBTN_ID).queryButton()).hasText("Add New User");
			//go to a plan page
			clickOn(planTable_ID);
			type(KeyCode.ENTER);
			clickOn(EditViewBTN_ID);
			
			//add the comment first
			//text input comment and add it
			clickOn(commentInput_ID);
			write("first comment");
			clickOn(commentAddBtn_ID); //add a comment
			
			
			clickOn(commentInput_ID);
			type(KeyCode.BACK_SPACE); //need to clear existing field
			type(KeyCode.BACK_SPACE); //need to clear existing field
			write("222222");
			clickOn(commentAddBtn_ID); //add a comment
			
			clickOn(commentListView_ID);
			sleep(100);
			clickOn(commentshow_ID);
			sleep(100);
			
			
			clickOn(undo_ID);
			sleep(100);
			type(KeyCode.ENTER);
			sleep(100);
			clickOn(redo_ID);
			sleep(100);
			type(KeyCode.ENTER);
			sleep(100);
			
			//text remove comment
			clickOn(commentRemoveBtn_ID);
			sleep(100);
			clickOn(undo_ID);
			sleep(100);
			clickOn(redo_ID);
			sleep(100);
			clickOn(undo_ID);
			sleep(100);

			
			//remove two comments
			clickOn(commentListView_ID);
			sleep(100);
			clickOn(commentInput_ID);
			write("test undo again ");
			clickOn(commentAddBtn_ID); //add a comment
			
			clickOn(commentListView_ID);
			sleep(100);
			clickOn(commentshow_ID);
			sleep(1000);
			
			clickOn(commentRemoveBtn_ID);
			sleep(100);
			clickOn(commentListView_ID);
			sleep(100);
			//add another comment
			clickOn(commentInput_ID);
			type(KeyCode.BACK_SPACE); 
			type(KeyCode.BACK_SPACE); 
			type(KeyCode.BACK_SPACE); 
			type(KeyCode.BACK_SPACE); 
			write("333333 ");
			clickOn(commentAddBtn_ID); //add a comment
			clickOn(commentListView_ID);
			sleep(100);
			clickOn(commentshow_ID);
			sleep(100);
			clickOn(commentRemoveBtn_ID);
			sleep(100);
			
			//test undo multiple times
			clickOn(undo_ID);
			sleep(500);
			clickOn(undo_ID);
			sleep(500);
			clickOn(redo_ID);
			sleep(500);
			clickOn(redo_ID);
			sleep(1000);
			
			//click on different nodes
			
			clickOn(treeView_ID);
			type(KeyCode.DOWN);
			type(KeyCode.DOWN);
			type(KeyCode.DOWN);
			type(KeyCode.DOWN);
			type(KeyCode.DOWN);
			sleep(1000);
			clickOn(viewRemoveComp_ID);
			sleep(1000);
			
			clickOn(undo_ID);
			sleep(500);
			
			clickOn(treeView_ID);
			type(KeyCode.DOWN);
			type(KeyCode.DOWN);
			type(KeyCode.DOWN);
			type(KeyCode.DOWN);
			type(KeyCode.DOWN);
			sleep(1000);
			clickOn(viewRemoveComp_ID);
			sleep(1000);
			
			clickOn(undo_ID);
			sleep(500);
			clickOn(undo_ID);
			sleep(500);
			clickOn(undo_ID);
			sleep(500);
			clickOn(undo_ID);
			sleep(500);
			type(KeyCode.ENTER); 
			clickOn(redo_ID);
			sleep(500);
			clickOn(redo_ID);
			sleep(1000);
			

			
		}
		
}