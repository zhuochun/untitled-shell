package sg.edu.nus.comp.cs4218.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.IShell;
import sg.edu.nus.comp.cs4218.impl.WorkerCallable;
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
	private static String[] output;
	private static boolean isCd;
	private static Callable<File> callable;
	private static Future<File> future;
	
	public Shell() {
		exitCode = new int[1];
		workingDir = new File(System.getProperty("user.dir"));
		output = new String[1];
	}
	
	@Override
	public ITool parse(String commandline) {
		ArrayList<String> params = new ArrayList<String>();
		String cmd = ArgList.split(commandline, params);
		
		ITool tool = CommandInterpreter.cmdToITool(cmd, params.toArray(new String[0]));
		
		callable = new WorkerCallable(tool, workingDir, "", exitCode, cmd);
		
		if (tool == null) {
			System.err.println("Cannot parse " + commandline);
		}
		
		return tool;
	}

	@Override
	public Runnable execute(ITool tool) {
		return null;
	}

	@Override
	public void stop(Runnable toolExecution) {
		return;
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
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		System.out.print("[" + workingDir.toString() + "]$ ");

		// Take in the user input
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			// If no thread is working, we should print the working
			// directory
			if (future != null && future.isDone()) {
				try {
					workingDir = future.get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.print("[" + workingDir.toString() + "]$ ");
			}
			
			String commandLine = scanner.nextLine();
			
			if (commandLine.equals("Ctrl-Z")) {
				if (future != null && !future.isDone()) {
					future.cancel(true);
				}
			} else {
				if (future == null || (future != null && future.isDone())) {
					shell.parse(commandLine);
					future = executor.submit(callable);
				}
			}
		}
	}
}
