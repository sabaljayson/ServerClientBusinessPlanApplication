package fx;


import java.util.ArrayList;

// an invoker to execute the command 
//use the singleton pattern so that it can only be initialized by calling the get instance method
public class Invoker {
	private ArrayList<ICommand> undoStack = new ArrayList<ICommand>();
	private ArrayList<ICommand> redoStack = new ArrayList<ICommand>();
	
	
	private static Invoker invokerInstance = null;

	//private constructor
	private Invoker() {
	}
	
	//return the instance for reference
	public static Invoker getInstance() {
		if(invokerInstance == null) {
			invokerInstance = new Invoker();
		}
		return invokerInstance;
	}
	
	//excute the corresponding undo command
		//return the command to be added to the stack
		public ICommand undo() {
			if (undoStack.size() > 0) {
				ICommand command = undoStack.remove(undoStack.size()-1);
				command.execute(ICommand.UNDO);
				return command;
				
			}
			return null;
			
		}
		
		//execute the redo command
		//remove the command to the redo stack and added it to the undo stack
		public ICommand redo() {
			if (redoStack.size() > 0) {
				ICommand command = redoStack.remove(redoStack.size()-1);
				command.execute(ICommand.REDO);
				return command;
			}
			return null;
		}
	
	
	
	//check if the undo stack is empty
	public boolean isUndoEmpty() {
		return undoStack.isEmpty();
	}
	
	//check if the redo stack is empty
	public boolean isRedoEmpty() {
		return redoStack.isEmpty();
	}
	
	
	//add to the stack
	public void addToUndoStack(ICommand command) {
		undoStack.add(command);
	}
	
	//add to the stack
	public void addToRedoStack(ICommand command) {
		redoStack.add(command);
	}
}
