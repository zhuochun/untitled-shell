package sg.edu.nus.comp.cs4218.impl;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.IShell;
import sg.edu.nus.comp.cs4218.impl.WorkerRunnable;
import sg.edu.nus.comp.cs4218.impl.fileutils.PWDTool;

/**
 * The Shell is used to interpret and execute user's
 * commands. Following sequence explains how a basic
 * shell can be implemented in Java
 */
public class Shell implements IShell {

	private String argList;
	private String stdin;
	
	private static File workingDir;
	private static int[] exitCode;
	
	public Shell() {
		exitCode = new int[1];
		workingDir = new File(System.getProperty("user.dir"));
	}
	
	@Override
	public ITool parse(String commandline) {
		System.err.println("Cannot parse " + commandline);
		return null;
	}

	@Override
	public Runnable execute(ITool tool) {		
		WorkerRunnable worker = new WorkerRunnable(tool, workingDir, stdin, exitCode);
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
     * 4. Execute the command and its arguments on the newly created thread. Exit with the status code of the executed command
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
				} else {
					System.err.println("Invalid Ctrl-Z!");
				}
			} else {
				ITool newTool = shell.parse(commandLine);
				workingThread = new Thread(shell.execute(newTool));
				
				workingThread.start();
			}
		}
	}
}
