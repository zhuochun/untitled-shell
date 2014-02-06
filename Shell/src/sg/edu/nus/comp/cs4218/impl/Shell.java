package sg.edu.nus.comp.cs4218.impl;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.IShell;
import sg.edu.nus.comp.cs4218.impl.WorkerRunnable;

import sg.edu.nus.comp.cs4218.impl.CommandInterpreter;
import sg.edu.nus.comp.cs4218.impl.ArgList;;

/**
 * The Shell is used to interpret and execute user's
 * commands. Commands typed in the console will be discarded if the shell is
 * running another command. "Ctrl-Z" will only be entertained when there is
 * a command running at background. In this case, the command will be
 * terminated, i.e. output of the command will not be available.
 */
public class Shell implements IShell {

	private String stdin;
	
	private static File workingDir;
	private static int[] exitCode;
	
	public Shell() {
		exitCode = new int[1];
		workingDir = new File(System.getProperty("user.dir"));
	}
	
	@Override
	public ITool parse(String commandline) {
		//TODO: confirm with Zhuochun if this is the correct usage
		String[] argList = ArgList.split(commandline);
		String[] args = Arrays.copyOfRange(argList, 1, argList.length);
		ITool tool = CommandInterpreter.cmdToITool(argList[0], args); 
		
		if (tool == null) {
			System.err.println("Cannot parse " + commandline);
		}
		
		return tool;
	}

	@Override
	public Runnable execute(ITool tool) {		
		WorkerRunnable worker = new WorkerRunnable(tool, workingDir, stdin, 
												   exitCode);
		return worker;
	}

	@Override
	public void stop(Runnable toolExecution) {
		Thread worker = (Thread) toolExecution;
		worker.stop();
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
		
		System.out.print("[" + workingDir + "]$ ");

		// Take in the user input
		Scanner scanner = new Scanner(System.in);
		Thread workingThread = null;
		
		while (true) {
			// If no thread is working, we should print the working
			// directory
			if (!workingThread.isAlive()) {
				System.out.print("[" + workingDir + "]$ ");
			}
			
			String commandLine = scanner.nextLine();
			
			if (commandLine.equals("Ctrl-Z")) {
				if (workingThread.isAlive()) {
					shell.stop(workingThread);
				}
			} else {
				ITool newTool = shell.parse(commandLine);
				workingThread = new Thread(shell.execute(newTool));
				
				workingThread.start();
			}
		}
	}
}
