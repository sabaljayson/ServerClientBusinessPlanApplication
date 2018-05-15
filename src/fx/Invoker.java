package fx;


import java.util.ArrayList;

public class Invoker {
	private ArrayList<ICommand> undoStack = new ArrayList<ICommand>();
	private ArrayList<ICommand> redoStack = new ArrayList<ICommand>();
	
	
	private static Invoker invokerInstance = null;

	
	private Invoker() {
	}
	
	public static Invoker getInstance() {
		if(invokerInstance == null) {
			invokerInstance = new Invoker();
		}
		return invokerInstance;
	}
	
	public boolean isUndoEmpty() {
		return undoStack.isEmpty();
	}
	
	public boolean isRedoEmpty() {
		return redoStack.isEmpty();
	}
	
	public ICommand undo() {
		if (undoStack.size() > 0) {
			ICommand command = undoStack.remove(undoStack.size() - 1);
			command.execute(ICommand.UNDO);
			return command;
			
		}
		return null;
		

	}
	
	public ICommand redo() {
		if (redoStack.size() > 0) {
			ICommand command = redoStack.remove(redoStack.size() - 1);
			command.execute(ICommand.REDO);
			return command;
		}
		return null;
	}
	
	public void addToUndoStack(ICommand command) {
		undoStack.add(command);
	}
	
	public void addToRedoStack(ICommand command) {
		redoStack.add(command);
	}
}
