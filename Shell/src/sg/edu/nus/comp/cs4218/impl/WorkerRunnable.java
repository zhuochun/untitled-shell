package sg.edu.nus.comp.cs4218.impl;

import java.io.File;

import sg.edu.nus.comp.cs4218.ITool;

public class WorkerRunnable implements Runnable {
	
	private ITool rTool;
	private File rWorkingDir;
	private String rStdin;
	private int[] rExitCode;
	
	public WorkerRunnable(ITool tool, File workingDir, String stdin, int[] exitCode) {
		rTool = tool;
		rWorkingDir = workingDir;
		rStdin = stdin;
	}

	@Override
	public void run() {
		String output = rTool.execute(rWorkingDir, rStdin);
		rExitCode[0] = rTool.getStatusCode();
		
		System.out.println(output);
		
		return;
	}
}
