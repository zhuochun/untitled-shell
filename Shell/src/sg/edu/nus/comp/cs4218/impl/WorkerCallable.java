package sg.edu.nus.comp.cs4218.impl;

import java.io.File;
import java.util.concurrent.Callable;

import sg.edu.nus.comp.cs4218.ITool;

public class WorkerCallable implements Callable<File> {
	
	private ITool rTool;
	private File rWorkingDir;
	private String rStdin;
	private int[] rExitCode;
	private String rCmd;
	
	public WorkerCallable(ITool tool, File workingDir, String stdin, int[] exitCode, String cmd) {
		rTool = tool;
		rWorkingDir = workingDir;
		rStdin = stdin;
		rCmd = cmd;
	}

	@Override
	public File call() {
		String output = rTool.execute(rWorkingDir, rStdin);
		rExitCode[0] = rTool.getStatusCode();
		
		if (!rCmd.equals("cd")) {
			System.out.println(output);
		} else {
			if (rExitCode[0] == '0') {
				System.out.println(output);
			}
		}
		
		return rWorkingDir;
	}
}
