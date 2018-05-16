package fx.viewPlanPage;

import java.io.Serializable;

import fx.ICommand;

public class NotifyCommand implements ICommand, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PlanViewController cont;
	
	public NotifyCommand(PlanViewController cont) {
		//this.commentinfo = commentinfo;
		this.cont = cont;
	}
	
	//call the alert pop window
	@Override
	public void execute(String command) {
		if(command != null) {
			System.out.println("command notify!!");
			this.cont.alertPop(command);
		}
		
	}

}
