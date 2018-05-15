package fx;

public interface ICommand {
	public static final String UNDO = "undo";
	public static final String REDO = "redo";
	
	
	// execute command
	public void execute(String command);

}
