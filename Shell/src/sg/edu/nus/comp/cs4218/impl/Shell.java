package sg.edu.nus.comp.cs4218.impl;

import java.util.ArrayList;

import sg.edu.nus.comp.cs4218.IShell;
import sg.edu.nus.comp.cs4218.ITool;

/**
 * The Shell is used to interpret and execute user's
 * commands. Commands typed in the console will be discarded if the shell is
 * running another command. "Ctrl-Z" will only be entertained when there is
 * a command running at background. In this case, the command will be
 * terminated, i.e. output of the command will not be available.
 */
public class Shell implements IShell {

	private static ToolRunnable runnable;
	
	public Shell() {
	}
	
	@Override
	public ITool parse(String commandline) {
		if (commandline.trim().isEmpty()) { return null; }

		ArrayList<String> params = new ArrayList<String>();
		String cmd = ArgList.split(commandline.trim(), params);
		
		ITool tool = CommandInterpreter.cmdToITool(cmd, params.toArray(new String[0]));
		
		if (tool == null) {
			System.err.println("Command not found: " + cmd);
		}
		
		return tool;
	}

	@Override
	public Runnable execute(ITool tool) {
		return null;
	}

	@Override
	public void stop(Runnable toolExecution) {
		return ;
	}
	
	/**
	 * Do Forever
     * 1. Wait for a user input
     * 2. Parse the user input. Separate the command and its arguments
     * 3. Create a new thread to execute the command
     * 4. Execute the command and its arguments on the newly created thread.
	 *	  Exit with the status code of the executed command
     * 5. In the shell, wait for the thread to complete execution
     * 6. Report the exit status of the command to the user
	 */
	public static void main(String[] args){	
		Shell shell = new Shell();
		InputRunnable input = new InputRunnable();
		input.start();

		System.out.print("[" + Directory.get().toString() + "] $ ");
		
		while (true) {
			String commandLine = input.get();
			
			if (commandLine == null && runnable != null) {
				if (runnable.isFinished()) {
					runnable = null;
					System.out.print("[" + Directory.get().toString() + "] $ ");
				}
			}
			
			if (commandLine != null) {
				if (runnable == null) {
					ITool tool = shell.parse(commandLine);

					if (tool != null) {
						runnable = new ToolRunnable(tool, "");
						runnable.start();
					} else {
						System.out.print("[" + Directory.get().toString() + "] $ ");
					}
				} else {
					if (commandLine.equalsIgnoreCase("Ctrl-Z")) {
						runnable.stop();
						runnable = null;
						System.out.print("[" + Directory.get().toString() + "] $ ");
					}
				}
			}

		}
	}
}
