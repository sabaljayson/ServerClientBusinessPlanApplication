package fx.viewPlanPage;

import java.io.Serializable;

import fx.ICommand;

public class NotifyCommand implements ICommand{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PlanViewController cont;
	
	public NotifyCommand(PlanViewController cont) {
		//this.commentinfo = commentinfo;
		this.cont = cont;
	}
	
	
	@Override
	public void execute(String command) {

		this.cont.alertPop(command);

		
	}

}
