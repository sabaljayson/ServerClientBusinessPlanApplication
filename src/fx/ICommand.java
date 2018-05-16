package fx;

//a command interface using the undo and redo to determine the corresponding actions
public interface ICommand {
	public static final String UNDO = "undo";
	public static final String REDO = "redo";
	
	
	// execute command
	public void execute(String command);

}
