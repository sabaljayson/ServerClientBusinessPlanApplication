package fx.viewPlanPage;

import fx.ICommand;

public class NotifyCommand implements ICommand{

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
