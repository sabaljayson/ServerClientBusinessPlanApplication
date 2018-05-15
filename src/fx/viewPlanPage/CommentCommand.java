package fx.viewPlanPage;

//import java.util.ArrayList;

import fx.ICommand;

public class CommentCommand implements ICommand{
	public String commentinfo;
	PlanViewController cont;
	
	//initialize the command with a list of infos
	public CommentCommand(PlanViewController cont) {
		//this.commentinfo = commentinfo;
		this.cont = cont;
		this.commentinfo = cont.commentshow.getText();
	}

	@Override
	public void execute(String command) {
		if(command.equals("undo")) {
//			System.out.println(commentinfo);
			this.cont.addCommentfcn(this.commentinfo);
			this.cont.refreshscene();
		}
		else {
			//redo
			System.out.println("I am redo the comment");
			this.cont.removeCommentfcn(commentinfo);
			this.cont.refreshscene();
		}
		

		
	}

}
