package sg.edu.nus.comp.cs4218.impl;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.IShell;
import sg.edu.nus.comp.cs4218.impl.fileutils.PWDTool;

/**
 * The Shell is used to interpret and execute user's
 * commands. Following sequence explains how a basic
 * shell can be implemented in Java
 */
public class Shell implements IShell {

	@Override
	public ITool parse(String commandline) {
		if(commandline.startsWith("pwd")){
			return new PWDTool();
		} else {
			//TODO Implement all other tools
			System.err.println("Cannot parse " + commandline);
			return null;
		}
	}

	@Override
	public Runnable execute(ITool tool) {
		// TODO Implement
		return null;
	}

	@Override
	public void stop(Runnable toolExecution) {
		//TODO Implement
	}
	
	/**
	 * Do Forever
     * 1. Wait for a user input
     * 2. Parse the user input. Separate the command and its arguments
     * 3. Create a new thread to execute the command
     * 4. Execute the command and its arguments on the newly created thread. Exit with the status code of the executed command
     * 5. In the shell, wait for the thread to complete execution
     * 6. Report the exit status of the command to the user
	 */
	public static void main(String[] args){
		//TODO Implement
	}
	
}
