package fx.viewPlanPage;

import Server.BusinessEntity;

//import java.util.ArrayList;

import fx.ICommand;
import javafx.scene.control.TreeItem;

public class ComponentCommand implements ICommand{
	public BusinessEntity plan;
	PlanViewController cont;
	TreeItem<BusinessEntity> selection;
	
	//initialize the command with a list of infos
	public ComponentCommand(PlanViewController cont, TreeItem<BusinessEntity> selection) {
		//this.commentinfo = commentinfo;
		this.cont = cont;
		this.plan = cont.currPlan;
		this.selection = selection;
	}

	@Override
	public void execute(String command) {
		if(command.equals("undo")) {
			System.out.println("-----within command----");
			System.out.println(selection.toString());
			this.cont.addSectionFcn(this.plan, this.selection);
			this.cont.refreshscene();
		}
		else {
			//redo
			System.out.println("I am redo the component");
			this.cont.delectSecFcn(this.plan);
			this.cont.refreshscene();
		}
		

		
	}

}
